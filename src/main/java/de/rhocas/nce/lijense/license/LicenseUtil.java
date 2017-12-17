package de.rhocas.nce.lijense.license;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import de.rhocas.nce.lijense.Constants;
import de.rhocas.nce.lijense.io.IOUtil;
import de.rhocas.nce.lijense.key.KeyException;
import de.rhocas.nce.lijense.key.KeyUtil;

/**
 * This is a util class to save and load license files within liJense.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public class LicenseUtil {

	private LicenseUtil( ) {
		// Avoid instantiation
	}

	/**
	 * This method creates a new license file. The internal properties file is filled with the given {@link ModifiableLicense} and is signed by using the given
	 * private key.
	 *
	 * @param aLicense
	 *            The license to be saved.
	 * @param aPrivateKey
	 *            The private key for the signature.
	 *
	 * @return The new license file as binary content.
	 *
	 * @throws LicenseException
	 *             If something went wrong while creating the license. This indicated usually that the algorithms are not provided by the underlying Java
	 *             runtime environment or that an IO error occurred.
	 *
	 * @since 1.0.0
	 */
	public static byte[] createLicenseFile( final ModifiableLicense aLicense, final PrivateKey aPrivateKey ) throws LicenseException {
		try {
			// Store the license as binary content
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream( );
			aLicense.storeToXML( byteArrayOutputStream, "liJense", Constants.LICENSE_ENCODING );
			final byte[] binaryLicenseContent = byteArrayOutputStream.toByteArray( );

			// Now calculate the signature
			final Signature signature = Signature.getInstance( Constants.SIGNATURE_ALGORITHM );
			signature.initSign( aPrivateKey );
			signature.update( binaryLicenseContent );
			final byte[] binarySignature = signature.sign( );

			// Now we can store both in an archive
			byteArrayOutputStream.reset( );
			try ( final ZipOutputStream zipOutputStream = new ZipOutputStream( byteArrayOutputStream ) ) {
				final ZipEntry licenseEntry = new ZipEntry( "license" );
				zipOutputStream.putNextEntry( licenseEntry );
				zipOutputStream.write( binaryLicenseContent );
				zipOutputStream.closeEntry( );

				final ZipEntry signatureEntry = new ZipEntry( "signature" );
				zipOutputStream.putNextEntry( signatureEntry );
				zipOutputStream.write( binarySignature );
				zipOutputStream.closeEntry( );
			}

			return byteArrayOutputStream.toByteArray( );
		} catch ( final NoSuchAlgorithmException | IOException | InvalidKeyException | SignatureException ex ) {
			throw new LicenseException( "Could not create the license", ex );
		}
	}

	/**
	 * This method creates a new license file and stores it in the given file. The internal properties file is filled with the given {@link ModifiableLicense}
	 * and is signed by using the given private key.
	 *
	 * @param aLicense
	 *            The license to be saved.
	 * @param aPrivateKey
	 *            The private key for the signature.
	 * @param aFile
	 *            The target file for the license file.
	 *
	 * @throws LicenseException
	 *             If something went wrong while creating the license. This indicated usually that the algorithms are not provided by the underlying Java
	 *             runtime environment or that an IO error occurred.
	 *
	 * @since 1.0.0
	 */
	public static void saveLicenseFile( final ModifiableLicense aLicense, final PrivateKey aPrivateKey, final File aFile ) throws LicenseException {
		try {
			final byte[] binaryContent = createLicenseFile( aLicense, aPrivateKey );
			Files.write( aFile.toPath( ), binaryContent, StandardOpenOption.CREATE );
		} catch ( final IOException ex ) {
			throw new LicenseException( "Could not save the license", ex );
		}
	}

	/**
	 * This method loads the license from the given file and verifies the digital signature with the public key. Optionally, one can also verify the public key
	 * with the fingerprint. It is strongly recommended to use this fingerprint in production environment.
	 *
	 * @param aPublicKey
	 *            The public key, which is used to check the digital signature.
	 * @param aFile
	 *            The license file.
	 * @param aFingerprint
	 *            The expected fingerprint of the public key.
	 *
	 * @return The license content.
	 *
	 * @throws LicenseException
	 *             If the license could not be loaded. This could indicate that the algorithms are not provided by the underlying Java runtime environment, that
	 *             an IO error occurred, that the actual fingerprint of the public key does not match the expected fingerprint or that the digital signature of
	 *             the license file is not valid.
	 *
	 * @since 1.0.0
	 */
	public static UnmodifiableLicense loadLicenseFile( final PublicKey aPublicKey, final File aFile, final Optional<byte[]> aFingerprint ) throws LicenseException {
		try {
			return loadLicenseFileFromInputStream( aPublicKey, new FileInputStream( aFile ), aFingerprint );
		} catch ( final FileNotFoundException ex ) {
			throw new LicenseException( "Could not load the license", ex );
		}
	}

	/**
	 * This method loads the license from the given input stream and verifies the digital signature with the public key. Optionally, one can also verify the
	 * public key with the fingerprint. It is strongly recommended to use this fingerprint in production environment. The stream is closed afterwards.
	 *
	 * @param aPublicKey
	 *            The public key, which is used to check the digital signature.
	 * @param aStream
	 *            The input stream.
	 * @param aFingerprint
	 *            The expected fingerprint of the public key.
	 *
	 * @return The license content.
	 *
	 * @throws LicenseException
	 *             If the license could not be loaded. This could indicate that the algorithms are not provided by the underlying Java runtime environment, that
	 *             an IO error occurred, that the actual fingerprint of the public key does not match the expected fingerprint or that the digital signature of
	 *             the license file is not valid.
	 *
	 * @since 1.0.0
	 */
	public static UnmodifiableLicense loadLicenseFileFromInputStream( final PublicKey aPublicKey, final InputStream aStream, final Optional<byte[]> aFingerprint )
			throws LicenseException {
		try {
			// Check the fingerprint of the public key - if necessary
			if ( aFingerprint.isPresent( ) ) {
				if ( !KeyUtil.isFingerprintValid( aPublicKey, aFingerprint.get( ) ) ) {
					throw new LicenseException( "The actual fingerprint of the public key does not match the expected fingerprint." );
				}
			}

			// Now we should load both files in the archive
			final byte[] binaryLicense;
			final byte[] binarySignature;

			try ( final ZipInputStream zipInputStream = new ZipInputStream( aStream ) ) {
				zipInputStream.getNextEntry( );
				binaryLicense = IOUtil.readAllBytes( zipInputStream );
				zipInputStream.closeEntry( );

				zipInputStream.getNextEntry( );
				binarySignature = IOUtil.readAllBytes( zipInputStream );
				zipInputStream.closeEntry( );
			}

			// Now we can verify the signature
			final Signature signature = Signature.getInstance( Constants.SIGNATURE_ALGORITHM );
			signature.initVerify( aPublicKey );
			signature.update( binaryLicense );
			final boolean licenseValid = signature.verify( binarySignature );

			if ( !licenseValid ) {
				throw new LicenseException( "The license is not valid" );
			}

			// If everything is valid, we can load the properties file
			final Properties properties = new Properties( );
			properties.loadFromXML( new ByteArrayInputStream( binaryLicense ) );

			final Map<String, String> map = new HashMap<>( );
			for ( final String key : properties.stringPropertyNames( ) ) {
				map.put( key, properties.getProperty( key ) );
			}

			// Finally we can create the license with an unmodifiable map
			return new UnmodifiableLicense( Collections.unmodifiableMap( map ) );
		} catch ( final KeyException | IOException | NoSuchAlgorithmException | InvalidKeyException | SignatureException ex ) {
			throw new LicenseException( "Could not load the license", ex );
		}
	}

}
