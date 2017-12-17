package de.rhocas.nce.lijense.key;

import static de.rhocas.nce.lijense.Constants.FINGERPRINT_ALGORITHM;
import static de.rhocas.nce.lijense.Constants.KEY_ALGORITHM;
import static de.rhocas.nce.lijense.Constants.KEY_SIZE;
import static de.rhocas.nce.lijense.Constants.RANDOM_ALGORITHM;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import de.rhocas.nce.lijense.io.IOUtil;

/**
 * This is a util class to generate, save, and load private and public RSA keys for the usage within liJense.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class KeyUtil {

	private KeyUtil( ) {
		// Avoid instantiation
	}

	/**
	 * This method generates a new pair of private and public keys.
	 *
	 * @return A new key pair.
	 *
	 * @throws KeyException
	 *             If something went wrong while creating the key pair. This happens usually only if the RSA or random algorithms are not provided by the
	 *             underlying Java runtime environment.
	 *
	 * @since 1.0.0
	 */
	public static KeyPair generateNewKeyPair( ) throws KeyException {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance( KEY_ALGORITHM );

			final SecureRandom random = SecureRandom.getInstance( RANDOM_ALGORITHM );
			keyGen.initialize( KEY_SIZE, random );

			return keyGen.generateKeyPair( );
		} catch ( final NoSuchAlgorithmException ex ) {
			throw new KeyException( "Could not create a new key pair", ex );
		}
	}

	/**
	 * This method saves the given key (either a private or a public key) in binary format in the given file. If the file does not exist, it will be created.
	 * Private keys are saved in the PKCS format, public keys in the X.509 format.
	 *
	 * @param aKey
	 *            The key to be saved in the file.
	 * @param aFile
	 *            The target file for the key.
	 *
	 * @throws KeyException
	 *             If something went wrong while saving the key. This happens usually only if an IO error occurs.
	 *
	 * @since 1.0.0
	 */
	public static void saveKeyToFile( final Key aKey, final File aFile ) throws KeyException {
		try {
			Files.write( aFile.toPath( ), aKey.getEncoded( ), StandardOpenOption.CREATE );
		} catch ( final IOException ex ) {
			throw new KeyException( "Could not save the key", ex );
		}
	}

	/**
	 * This method loads a private key from the given binary file.
	 *
	 * @param aFile
	 *            The file which contains the private key.
	 *
	 * @return The private key in the file.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates an IO error or that the given file contains no valid private key.
	 *
	 * @since 1.0.0
	 */
	public static PrivateKey loadPrivateKeyFromFile( final File aFile ) throws KeyException {
		try {
			final byte[] binaryContent = Files.readAllBytes( aFile.toPath( ) );
			return loadPrivateKeyFromArray( binaryContent );
		} catch ( final IOException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method loads a private key from the given stream. The stream is not closed.
	 *
	 * @param aStream
	 *            The stream which contains the private key.
	 *
	 * @return The private key in the stream.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates an IO error or that the given stream contains no valid private key.
	 *
	 * @since 1.0.0
	 */
	public static PrivateKey loadPrivateKeyFromStream( final InputStream aStream ) throws KeyException {
		try {
			final byte[] binaryContent = IOUtil.readAllBytes( aStream );
			return loadPrivateKeyFromArray( binaryContent );
		} catch ( final IOException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method loads a private key from the given array.
	 *
	 * @param aArray
	 *            The array which contains the private key.
	 *
	 * @return The private key in the array.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates that the given array contains no valid private key.
	 *
	 * @since 1.0.0
	 */
	public static PrivateKey loadPrivateKeyFromArray( final byte[] aArray ) throws KeyException {
		try {
			final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec( aArray );
			final KeyFactory keyFactory = KeyFactory.getInstance( KEY_ALGORITHM );

			return keyFactory.generatePrivate( keySpec );
		} catch ( final NoSuchAlgorithmException | InvalidKeySpecException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method loads a public key from the given binary file.
	 *
	 * @param aFile
	 *            The file which contains the public key.
	 *
	 * @return The public key in the file.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates an IO error or that the given file contains no valid public key.
	 *
	 * @since 1.0.0
	 */
	public static PublicKey loadPublicKeyFromFile( final File aFile ) throws KeyException {
		try {
			final byte[] binaryContent = Files.readAllBytes( aFile.toPath( ) );
			return loadPublicKeyFromArray( binaryContent );
		} catch ( final IOException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method loads a public key from the given stream. The stream is not closed.
	 *
	 * @param aStream
	 *            The stream which contains the public key.
	 *
	 * @return The public key in the stream.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates an IO error or that the given stream contains no valid public key.
	 *
	 * @since 1.0.0
	 */
	public static PublicKey loadPublicKeyFromStream( final InputStream aStream ) throws KeyException {
		try {
			final byte[] binaryContent = IOUtil.readAllBytes( aStream );
			return loadPublicKeyFromArray( binaryContent );
		} catch ( final IOException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method loads a public key from the given array.
	 *
	 * @param aArray
	 *            The array which contains the public key.
	 *
	 * @return The public key in the array.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates that the given array contains no valid public key.
	 *
	 * @since 1.0.0
	 */
	public static PublicKey loadPublicKeyFromArray( final byte[] aArray ) throws KeyException {
		try {
			final X509EncodedKeySpec keySpec = new X509EncodedKeySpec( aArray );
			final KeyFactory keyFactory = KeyFactory.getInstance( KEY_ALGORITHM );

			return keyFactory.generatePublic( keySpec );
		} catch ( final NoSuchAlgorithmException | InvalidKeySpecException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method calculates the fingerprint for a given public key. This fingerprint can be used to check that a public key has not been tampered with.
	 *
	 * @param aPublicKey
	 *            The public key for which the fingerprint should be calculated.
	 * @return The fingerprint of the key.
	 *
	 * @throws KeyException
	 *             If something went wrong while calculating the fingerprint. This usually indicates that the hash algorithm is not provided by the underlying
	 *             Java runtime environment.
	 */
	public static byte[] calculateFingerprint( final PublicKey aPublicKey ) throws KeyException {
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance( FINGERPRINT_ALGORITHM );
			return messageDigest.digest( aPublicKey.getEncoded( ) );
		} catch ( final NoSuchAlgorithmException ex ) {
			throw new KeyException( "Could not calculate the fingerprint of the public key", ex );
		}
	}

	/**
	 * This method checks whether the given fingerprint is valid for the given public key.
	 *
	 * @param aPublicKey
	 *            The public key for which the fingerprint should be checked.
	 * @param aFingerprint
	 *            The expected fingerprint.
	 * @return true if and only if the expected and the actual fingerprint are matching.
	 *
	 * @throws KeyException
	 *             If something went wrong while calculating the fingerprint. This usually indicates that the hash algorithm is not provided by the underlying
	 *             Java runtime environment.
	 */
	public static boolean isFingerprintValid( final PublicKey aPublicKey, final byte[] aFingerprint ) throws KeyException {
		final byte[] actualFingerprint = calculateFingerprint( aPublicKey );
		return Arrays.equals( actualFingerprint, aFingerprint );
	}

}
