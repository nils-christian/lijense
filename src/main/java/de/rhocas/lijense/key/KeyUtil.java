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

package de.rhocas.lijense.key;

import static de.rhocas.lijense.Constants.FINGERPRINT_ALGORITHM;
import static de.rhocas.lijense.Constants.KEY_ALGORITHM;
import static de.rhocas.lijense.Constants.KEY_SIZE;
import static de.rhocas.lijense.Constants.RANDOM_ALGORITHM;

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
import java.util.Objects;

import de.rhocas.lijense.io.IOUtil;

/**
 * This is a util class to generate, save, and load private and public RSA keys for the usage within liJense.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class KeyUtil {

	private KeyUtil( ) {
		throw new AssertionError( "This util class must not be initialized." );
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
	 * This method saves the given key (either a private or a public key) in Base64 encoded ASCII format in the given file. If the file does not exist, it will
	 * be created. Private keys are saved in the PKCS format, public keys in the X.509 format.
	 *
	 * @param aKey
	 *            The key to be saved in the file. Must not be {@code null}.
	 * @param aFile
	 *            The target file for the key. Must not be {@code null}.
	 *
	 * @throws KeyException
	 *             If something went wrong while saving the key. This happens usually only if an IO error occurs.
	 * @throws NullPointerException
	 * 	           If the given key or the given file is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static void saveKeyToFile( final Key aKey, final File aFile ) throws KeyException {
		Objects.requireNonNull( aKey, "The key must not be null." );
		Objects.requireNonNull( aFile, "The file must not be null." );

		try {
			final byte[] encodedKey = aKey.getEncoded( );
			final byte[] base64EncodedKey = IOUtil.binaryToBinaryString( encodedKey );

			Files.write( aFile.toPath( ), base64EncodedKey, StandardOpenOption.CREATE );
		} catch ( final IOException ex ) {
			throw new KeyException( "Could not save the key", ex );
		}
	}

	/**
	 * This method loads a private key from the given Base64 encoded ASCII file.
	 *
	 * @param aFile
	 *            The file which contains the private key. Must not be {@code null}.
	 *
	 * @return The private key in the file.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates an IO error or that the given file contains no valid private key.
	 * @throws NullPointerException
	 * 	           If the given file is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static PrivateKey loadPrivateKeyFromFile( final File aFile ) throws KeyException {
		Objects.requireNonNull( aFile, "The file must not be null." );

		try {
			final byte[] base64EncodedKey = Files.readAllBytes( aFile.toPath( ) );
			final byte[] encodedKey = IOUtil.binaryStringToBinary( base64EncodedKey );

			return loadPrivateKeyFromArray( encodedKey );
		} catch ( final IOException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method loads a private key from the given stream. It is assumed that the stream contains Base64 encoded ASCII data. The stream is not closed.
	 *
	 * @param aStream
	 *            The stream which contains the private key. Must not be {@code null}.
	 *
	 * @return The private key in the stream.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates an IO error or that the given stream contains no valid private key.
	 * @throws NullPointerException
	 * 	           If the given stream is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static PrivateKey loadPrivateKeyFromStream( final InputStream aStream ) throws KeyException {
		Objects.requireNonNull( aStream, "The stream must not be null." );

		try {
			final byte[] base64EncodedKey = IOUtil.readAllBytes( aStream );
			final byte[] encodedKey = IOUtil.binaryStringToBinary( base64EncodedKey );

			return loadPrivateKeyFromArray( encodedKey );
		} catch ( final IOException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method loads a private key from the given array. It is assumed that the key is available in binary (not Base64 encoded) data.
	 *
	 * @param aArray
	 *            The array which contains the private key. Must not be {@code null}.
	 *
	 * @return The private key in the array.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates that the given array contains no valid private key.
	 * @throws NullPointerException
	 * 	           If the given array is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static PrivateKey loadPrivateKeyFromArray( final byte[] aArray ) throws KeyException {
		Objects.requireNonNull( aArray, "The array must not be null." );

		try {
			final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec( aArray );
			final KeyFactory keyFactory = KeyFactory.getInstance( KEY_ALGORITHM );

			return keyFactory.generatePrivate( keySpec );
		} catch ( final NoSuchAlgorithmException | InvalidKeySpecException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method loads a public key from the given Base64 encoded ASCII file.
	 *
	 * @param aFile
	 *            The file which contains the public key. Must not be {@code null}.
	 *
	 * @return The public key in the file.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates an IO error or that the given file contains no valid public key.
	 * @throws NullPointerException
	 * 	           If the given file is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static PublicKey loadPublicKeyFromFile( final File aFile ) throws KeyException {
		Objects.requireNonNull( aFile, "The file must not be null." );

		try {
			final byte[] base64EncodedKey = Files.readAllBytes( aFile.toPath( ) );
			final byte[] encodedKey = IOUtil.binaryStringToBinary( base64EncodedKey );

			return loadPublicKeyFromArray( encodedKey );
		} catch ( final IOException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method loads a public key from the given stream. It is assumed that the stream contains Base64 encoded ASCII data. The stream is not closed.
	 *
	 * @param aStream
	 *            The stream which contains the public key. Must not be {@code null}.
	 *
	 * @return The public key in the stream.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates an IO error or that the given stream contains no valid public key.
	 * @throws NullPointerException
	 * 	           If the given stream is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static PublicKey loadPublicKeyFromStream( final InputStream aStream ) throws KeyException {
		Objects.requireNonNull( aStream, "The stream must not be null." );

		try {
			final byte[] base64EncodedKey = IOUtil.readAllBytes( aStream );
			final byte[] encodedKey = IOUtil.binaryStringToBinary( base64EncodedKey );

			return loadPublicKeyFromArray( encodedKey );
		} catch ( final IOException ex ) {
			throw new KeyException( "Could not load the key", ex );
		}
	}

	/**
	 * This method loads a public key from the given array. It is assumed that the key is available in binary (not Base64 encoded) data.
	 *
	 * @param aArray
	 *            The array which contains the public key. Must not be {@code null}.
	 *
	 * @return The public key in the array.
	 *
	 * @throws KeyException
	 *             If something went wrong while loading the key. This usually indicates that the given array contains no valid public key.
	 * @throws NullPointerException
	 * 	           If the given array is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static PublicKey loadPublicKeyFromArray( final byte[] aArray ) throws KeyException {
		Objects.requireNonNull( aArray, "The array must not be null." );

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
	 *            The public key for which the fingerprint should be calculated. Must not be {@code null}.
	 *
	 * @return The fingerprint of the key.
	 *
	 * @throws KeyException
	 *             If something went wrong while calculating the fingerprint. This usually indicates that the hash algorithm is not provided by the underlying
	 *             Java runtime environment.
	 * @throws NullPointerException
	 * 	           If the given key is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static byte[] calculateFingerprint( final PublicKey aPublicKey ) throws KeyException {
		Objects.requireNonNull( aPublicKey, "The key must not be null." );

		try {
			final MessageDigest messageDigest = MessageDigest.getInstance( FINGERPRINT_ALGORITHM );
			return messageDigest.digest( aPublicKey.getEncoded( ) );
		} catch ( final NoSuchAlgorithmException ex ) {
			throw new KeyException( "Could not calculate the fingerprint of the public key", ex );
		}
	}

	/**
	 * This method calculates the fingerprint for a given public key. This fingerprint can be used to check that a public key has not been tampered with.
	 *
	 * @param aPublicKey
	 *            The public key for which the fingerprint should be calculated. Must not be {@code null}.
	 *
	 * @return The fingerprint of the key.
	 *
	 * @throws KeyException
	 *             If something went wrong while calculating the fingerprint. This usually indicates that the hash algorithm is not provided by the underlying
	 *             Java runtime environment or that the given array does not contain a valid public key.
	 * @throws NullPointerException
	 * 	           If the given key is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static byte[] calculateFingerprint( final byte[] aPublicKey ) throws KeyException {
		Objects.requireNonNull( aPublicKey, "The key must not be null." );

		final PublicKey publicKey = loadPublicKeyFromArray( aPublicKey );
		return calculateFingerprint( publicKey );
	}

	/**
	 * This method checks whether the given fingerprint is valid for the given public key.
	 *
	 * @param aPublicKey
	 *            The public key for which the fingerprint should be checked. Must not be {@code null}.
	 * @param aFingerprint
	 *            The expected fingerprint. Must not be {@code null}.
	 *
	 * @return true if and only if the expected and the actual fingerprint are matching.
	 *
	 * @throws KeyException
	 *             If something went wrong while calculating the fingerprint. This usually indicates that the hash algorithm is not provided by the underlying
	 *             Java runtime environment.
	 * @throws NullPointerException
	 * 	           If the given key or the fingerprint is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static boolean isFingerprintValid( final PublicKey aPublicKey, final byte[] aFingerprint ) throws KeyException {
		Objects.requireNonNull( aPublicKey, "The key must not be null." );
		Objects.requireNonNull( aFingerprint, "The fingerprint must not be null." );

		final byte[] actualFingerprint = calculateFingerprint( aPublicKey );
		return Arrays.equals( actualFingerprint, aFingerprint );
	}

}
