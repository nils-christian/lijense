package de.rhocas.nce.lijense.key;

import static de.rhocas.nce.lijense.Constants.KEY_ALGORITHM;
import static de.rhocas.nce.lijense.Constants.KEY_SIZE;
import static de.rhocas.nce.lijense.Constants.RANDOM_ALGORITHM;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * This is a util class to generate, save, and load private and public RSA keys
 * for the usage within liJense.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class KeyUtil {

	private KeyUtil() {
		// Avoid instantiation
	}

	/**
	 * This method generates a new pair of private and public keys.
	 *
	 * @return A new key pair.
	 *
	 * @throws KeyException
	 *             If something went wrong while creating the key pair. This happens
	 *             usually only if the RSA or random algorithms are not provided by
	 *             the underlying Java runtime environment.
	 *
	 * @since 1.0.0
	 */
	public static KeyPair generateNewKeyPair() throws KeyException {
		try {
			final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

			final SecureRandom random = SecureRandom.getInstance(RANDOM_ALGORITHM);
			keyGen.initialize(KEY_SIZE, random);

			return keyGen.generateKeyPair();
		} catch (final NoSuchAlgorithmException ex) {
			throw new KeyException("Could not create a new key pair", ex);
		}
	}

	/**
	 * This method saves the given key (either a private or a public key) in binary
	 * format in the given file. If the file does not exist, it will be created.
	 * Private keys are saved in the PKCS format, public keys in the X.509 format.
	 *
	 * @param aKey
	 *            The key to be saved in the file.
	 * @param aFile
	 *            The target file for the key.
	 *
	 * @throws KeyException
	 *             If something went wrong while saving the key. This happens
	 *             usually only if an IO error occurs.
	 *
	 * @since 1.0.0
	 */
	public static void saveKeyToFile(final Key aKey, final File aFile) throws KeyException {
		try {
			Files.write(aFile.toPath(), aKey.getEncoded(), StandardOpenOption.CREATE);
		} catch (final IOException ex) {
			throw new KeyException("Could not save the key", ex);
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
	 *             If something went wrong while loading the key. This usually
	 *             indicates an IO error or that the given file contains no valid
	 *             private key.
	 *
	 * @since 1.0.0
	 */
	public static PrivateKey loadPrivateKeyFromFile(final File aFile) throws KeyException {
		try {
			final byte[] binaryContent = Files.readAllBytes(aFile.toPath());
			return loadPrivateKeyFromArray(binaryContent);
		} catch (final IOException ex) {
			throw new KeyException("Could not load the key", ex);
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
	 *             If something went wrong while loading the key. This usually
	 *             indicates that the given array contains no valid private key.
	 *
	 * @since 1.0.0
	 */
	private static PrivateKey loadPrivateKeyFromArray(final byte[] aArray) throws KeyException {
		try {
			final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(aArray);
			final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

			return keyFactory.generatePrivate(keySpec);
		} catch (final NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new KeyException("Could not load the key", ex);
		}
	}

}
