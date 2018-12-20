////////////////////////////////////////////////////////////////////////////////////
//                                                                                //
// MIT License                                                                    //
//                                                                                //
// Copyright (c) 2017 Nils Christian Ehmke                                        //
//                                                                                //
// Permission is hereby granted, free of charge, to any person obtaining a copy   //
// of this software and associated documentation files (the "Software"), to deal  //
// in the Software without restriction, including without limitation the rights   //
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell      //
// copies of the Software, and to permit persons to whom the Software is          //
// furnished to do so, subject to the following conditions:                       //
//                                                                                //
// The above copyright notice and this permission notice shall be included in all //
// copies or substantial portions of the Software.                                //
//                                                                                //
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR     //
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,       //
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE    //
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER         //
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,  //
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  //
// SOFTWARE.                                                                      //
//                                                                                //
////////////////////////////////////////////////////////////////////////////////////

package de.rhocas.lijense.license;

import static de.rhocas.lijense.Constants.LICENSE_ENCODING;
import static de.rhocas.lijense.Constants.LICENSE_ENCODING_CHARSET;

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
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import de.rhocas.lijense.Constants;
import de.rhocas.lijense.io.IOUtil;
import de.rhocas.lijense.key.KeyException;
import de.rhocas.lijense.key.KeyUtil;

/**
 * This is a util class to save and load license files within liJense.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class LicenseUtil {

	private LicenseUtil( ) {
		throw new AssertionError( "This util class must not be initialized." );
	}

	/**
	 * This method creates a new license file. The internal properties file is filled with the given {@link ModifiableLicense} and is signed by using the given
	 * private key.
	 *
	 * @param aLicense
	 *            The license to be saved. Must not be {@code null}.
	 * @param aPrivateKey
	 *            The private key for the signature. Must not be {@code null}.
	 *
	 * @return The new license file as Base64 encoded string.
	 *
	 * @throws LicenseException
	 *             If something went wrong while creating the license. This indicated usually that the algorithms are not provided by the underlying Java
	 *             runtime environment or that an IO error occurred.
	 * @throws NullPointerException
	 * 	           If the given license or the given key is {@code null}.
	 *
	 * @since 2.0.0
	 */
	public static String createLicenseFile( final ModifiableLicense aLicense, final PrivateKey aPrivateKey ) throws LicenseException {
		Objects.requireNonNull( aLicense, "The license must not be null." );
		Objects.requireNonNull( aPrivateKey, "The key must not be null." );

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

			final byte[] binaryResult = byteArrayOutputStream.toByteArray( );
			return IOUtil.binaryToString( binaryResult );
		} catch ( final NoSuchAlgorithmException | IOException | InvalidKeyException | SignatureException ex ) {
			throw new LicenseException( "Could not create the license", ex );
		}
	}

	/**
	 * This method creates a new license file and stores it in the given file. The internal properties file is filled with the given {@link ModifiableLicense}
	 * and is signed by using the given private key.
	 *
	 * @param aLicense
	 *            The license to be saved. Must not be {@code null}.
	 * @param aPrivateKey
	 *            The private key for the signature. Must not be {@code null}.
	 * @param aFile
	 *            The target file for the license file. Must not be {@code null}.
	 *
	 * @throws LicenseException
	 *             If something went wrong while creating the license. This indicated usually that the algorithms are not provided by the underlying Java
	 *             runtime environment or that an IO error occurred.
	 * @throws NullPointerException
	 * 	           If the given license, the given key, or the given file is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static void saveLicenseFile( final ModifiableLicense aLicense, final PrivateKey aPrivateKey, final File aFile ) throws LicenseException {
		Objects.requireNonNull( aLicense, "The license must not be null." );
		Objects.requireNonNull( aPrivateKey, "The key must not be null." );
		Objects.requireNonNull( aFile, "The file must not be null." );

		try {
			final String encodedLicense = createLicenseFile( aLicense, aPrivateKey );
			Files.write( aFile.toPath( ), encodedLicense.getBytes( LICENSE_ENCODING ), StandardOpenOption.CREATE );
		} catch ( final IOException ex ) {
			throw new LicenseException( "Could not save the license", ex );
		}
	}

	/**
	 * This method loads the license from the given file and verifies the digital signature with the public key. Optionally, one can also verify the public key
	 * with the fingerprint. It is strongly recommended to use this fingerprint in production environment.
	 *
	 * @param aPublicKey
	 *            The public key, which is used to check the digital signature. Must not be {@code null}.
	 * @param aFile
	 *            The license file. Must not be {@code null}.
	 * @param aFingerprint
	 *            The expected fingerprint of the public key. Must not be {@code null}.
	 *
	 * @return The license content.
	 *
	 * @throws LicenseException
	 *             If the license could not be loaded. This could indicate that the algorithms are not provided by the underlying Java runtime environment, that
	 *             an IO error occurred, that the actual fingerprint of the public key does not match the expected fingerprint or that the digital signature of
	 *             the license file is not valid.
	 * @throws NullPointerException
	 * 	           If the given key, the given file, or the given fingerprint is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static UnmodifiableLicense loadLicenseFile( final PublicKey aPublicKey, final File aFile, final Optional<byte[]> aFingerprint ) throws LicenseException {
		Objects.requireNonNull( aPublicKey, "The key must not be null." );
		Objects.requireNonNull( aFile, "The file must not be null." );
		Objects.requireNonNull( aFingerprint, "The fingerprint must not be null." );

		try {
			return loadLicenseFileFromInputStream( aPublicKey, new FileInputStream( aFile ), aFingerprint );
		} catch ( final FileNotFoundException ex ) {
			throw new LicenseException( "Could not load the license", ex );
		}
	}

	/**
	 * This method loads the license from the given file, but does not verify it. This method should usually not be used in production environment.
	 *
	 * @param aFile
	 *            The license file. Must not be {@code null}.
	 *
	 * @return The license content.
	 *
	 * @throws LicenseException
	 *             If the license could not be loaded. This could indicate that the algorithms are not provided by the underlying Java runtime environment or
	 *             that an IO error occurred.
	 * @throws NullPointerException
	 * 	           If the given file is {@code null}.
	 *
	 * @since 2.1.0
	 */
	public static UnmodifiableLicense loadLicenseFileWithoutValidation( final File aFile ) throws LicenseException {
		Objects.requireNonNull( aFile, "The file must not be null." );

		try {
			return loadLicenseFileWithoutValidationFromInputStream( new FileInputStream( aFile ) );
		} catch ( final FileNotFoundException ex ) {
			throw new LicenseException( "Could not load the license", ex );
		}
	}

	/**
	 * This method loads the license from the given Base64-encoded string and verifies the digital signature with the public key. Optionally, one can also
	 * verify the public key with the fingerprint. It is strongly recommended to use this fingerprint in production environment.
	 *
	 * @param aPublicKey
	 *            The public key, which is used to check the digital signature. Must not be {@code null}.
	 * @param aString
	 *            The string. Must not be {@code null}.
	 * @param aFingerprint
	 *            The expected fingerprint of the public key. Must not be {@code null}.
	 *
	 * @return The license content.
	 *
	 * @throws LicenseException
	 *             If the license could not be loaded. This could indicate that the algorithms are not provided by the underlying Java runtime environment, that
	 *             an IO error occurred, that the actual fingerprint of the public key does not match the expected fingerprint or that the digital signature of
	 *             the license file is not valid.
	 * @throws NullPointerException
	 * 	           If the given key, the given string, or the given fingerprint is {@code null}.
	 *
	 * @since 2.0.0
	 */
	public static UnmodifiableLicense loadLicenseFileFromString( final PublicKey aPublicKey, final String aString, final Optional<byte[]> aFingerprint ) throws LicenseException {
		Objects.requireNonNull( aPublicKey, "The key must not be null." );
		Objects.requireNonNull( aString, "The string must not be null." );
		Objects.requireNonNull( aFingerprint, "The fingerprint must not be null." );

		final byte[] stringBytes = aString.getBytes( LICENSE_ENCODING_CHARSET );
		return loadLicenseFileFromInputStream( aPublicKey, new ByteArrayInputStream( stringBytes ), aFingerprint );
	}

	/**
	 * This method loads the license from the given Base64-encoded string, but does not verify it. This method should usually not be used in production
	 * environment.
	 *
	 * @param aString
	 *            The string. Must not be {@code null}.
	 *
	 * @return The license content.
	 *
	 * @throws LicenseException
	 *             If the license could not be loaded. This could indicate that the algorithms are not provided by the underlying Java runtime environment or
	 *             that an IO error occurred.
	 * @throws NullPointerException
	 * 	           If the given string is {@code null}.
	 *
	 * @since 2.1.0
	 */
	public static UnmodifiableLicense loadLicenseFileWithoutValidationFromString( final String aString ) throws LicenseException {
		Objects.requireNonNull( aString, "The string must not be null." );

		final byte[] stringBytes = aString.getBytes( LICENSE_ENCODING_CHARSET );
		return loadLicenseFileWithoutValidationFromInputStream( new ByteArrayInputStream( stringBytes ) );
	}

	/**
	 * This method loads the license from the given input stream and verifies the digital signature with the public key. Optionally, one can also verify the
	 * public key with the fingerprint. It is strongly recommended to use this fingerprint in production environment. The stream is closed afterwards.
	 *
	 * @param aPublicKey
	 *            The public key, which is used to check the digital signature. Must not be {@code null}.
	 * @param aStream
	 *            The input stream. Must not be {@code null}.
	 * @param aFingerprint
	 *            The expected fingerprint of the public key. Must not be {@code null}.
	 *
	 * @return The license content.
	 *
	 * @throws LicenseException
	 *             If the license could not be loaded. This could indicate that the algorithms are not provided by the underlying Java runtime environment, that
	 *             an IO error occurred, that the actual fingerprint of the public key does not match the expected fingerprint or that the digital signature of
	 *             the license file is not valid.
	 * @throws NullPointerException
	 * 	           If the given key, the given stream, or the given fingerprint is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static UnmodifiableLicense loadLicenseFileFromInputStream( final PublicKey aPublicKey, final InputStream aStream, final Optional<byte[]> aFingerprint ) throws LicenseException {
		Objects.requireNonNull( aPublicKey, "The key must not be null." );
		Objects.requireNonNull( aStream, "The stream must not be null." );
		Objects.requireNonNull( aFingerprint, "The fingerprint must not be null." );

		return loadLicenseFileFromInputStream( Optional.of( aPublicKey ), aStream, aFingerprint, true );
	}

	/**
	 * This method loads the license from the given input stream, but does not verify it. This method should usually not be used in production environment. The
	 * stream is closed afterwards.
	 *
	 * @param aStream
	 *            The input stream. Must not be {@code null}.
	 *
	 * @return The license content.
	 *
	 * @throws LicenseException
	 *             If the license could not be loaded. This could indicate that the algorithms are not provided by the underlying Java runtime environment or
	 *             that an IO error occurred.
	 * @throws NullPointerException
	 * 	           If the given stream is {@code null}.
	 *
	 * @since 2.1.0
	 */
	public static UnmodifiableLicense loadLicenseFileWithoutValidationFromInputStream( final InputStream aStream ) throws LicenseException {
		Objects.requireNonNull( aStream, "The stream must not be null." );

		return loadLicenseFileFromInputStream( Optional.empty( ), aStream, Optional.empty( ), false );
	}

	private static UnmodifiableLicense loadLicenseFileFromInputStream( final Optional<PublicKey> aPublicKey, final InputStream aStream, final Optional<byte[]> aFingerprint, final boolean aCheckValidity ) throws LicenseException {
		try {
			// Check the fingerprint of the public key - if necessary
			if ( aFingerprint.isPresent( ) ) {
				if ( !KeyUtil.isFingerprintValid( aPublicKey.get( ), aFingerprint.get( ) ) ) {
					throw new LicenseException( "The actual fingerprint of the public key does not match the expected fingerprint." );
				}
			}

			// Base64 decoding
			final byte[] licenseData;
			try {
				final byte[] binaryData = IOUtil.readAllBytes( aStream );
				final String string = new String( binaryData, LICENSE_ENCODING_CHARSET );
				licenseData = IOUtil.stringToBinary( string );
			} finally {
				aStream.close( );
			}

			// Now we should load both files in the archive
			final byte[] binaryLicense;
			final byte[] binarySignature;

			try ( final ZipInputStream zipInputStream = new ZipInputStream( new ByteArrayInputStream( licenseData ) ) ) {
				zipInputStream.getNextEntry( );
				binaryLicense = IOUtil.readAllBytes( zipInputStream );
				zipInputStream.closeEntry( );

				zipInputStream.getNextEntry( );
				binarySignature = IOUtil.readAllBytes( zipInputStream );
				zipInputStream.closeEntry( );
			}

			// Now we can verify the signature
			if ( aCheckValidity ) {
				final Signature signature = Signature.getInstance( Constants.SIGNATURE_ALGORITHM );
				signature.initVerify( aPublicKey.get( ) );
				signature.update( binaryLicense );
				final boolean licenseValid = signature.verify( binarySignature );

				if ( !licenseValid ) {
					throw new LicenseException( "The license is not valid" );
				}
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
