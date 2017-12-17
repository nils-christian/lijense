package de.rhocas.nce.lijense.key;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Unit test for {@link KeyUtil}.
 *
 * @author Nils Christian Ehmke
 */
public final class KeyUtilTest {

	@Rule
	public TemporaryFolder ivTemporaryFolder = new TemporaryFolder( );

	@Test
	public void testKeyGeneration( ) throws KeyException {
		final KeyPair keyPair = KeyUtil.generateNewKeyPair( );

		// Check the private key
		final PrivateKey privateKey = keyPair.getPrivate( );
		assertThat( privateKey.getAlgorithm( ), is( "RSA" ) );
		assertThat( privateKey.getFormat( ), is( "PKCS#8" ) );
		assertThat( privateKey.getEncoded( ), is( notNullValue( ) ) );

		// Check the public key
		final PublicKey publicKey = keyPair.getPublic( );
		assertThat( publicKey.getAlgorithm( ), is( "RSA" ) );
		assertThat( publicKey.getFormat( ), is( "X.509" ) );
		assertThat( publicKey.getEncoded( ), is( notNullValue( ) ) );
	}

	@Test
	public void testLoadPrivateKeyFromStream( ) throws KeyException {
		final InputStream inputStream = loadResourceAsStream( "key.private" );
		final PrivateKey privateKey = KeyUtil.loadPrivateKeyFromStream( inputStream );

		// Check the private key
		assertThat( privateKey.getAlgorithm( ), is( "RSA" ) );
		assertThat( privateKey.getFormat( ), is( "PKCS#8" ) );
		assertThat( privateKey.getEncoded( ), is( notNullValue( ) ) );
	}

	@Test
	public void testLoadPublicKeyFromStream( ) throws KeyException {
		final InputStream inputStream = loadResourceAsStream( "key.public" );
		final PublicKey publicKey = KeyUtil.loadPublicKeyFromStream( inputStream );

		// Check the public key
		assertThat( publicKey.getAlgorithm( ), is( "RSA" ) );
		assertThat( publicKey.getFormat( ), is( "X.509" ) );
		assertThat( publicKey.getEncoded( ), is( notNullValue( ) ) );
	}

	@Test
	public void testCalculateFingerprint( ) throws KeyException {
		final InputStream inputStream = loadResourceAsStream( "key.public" );
		final PublicKey publicKey = KeyUtil.loadPublicKeyFromStream( inputStream );

		final byte[] actualFingerprint = KeyUtil.calculateFingerprint( publicKey );
		final byte[] expectedFingerprint = new byte[] { //
				90, 80, -62, 120, -97, 48, -117, 53, //
				113, -28, 12, 31, -19, 124, -104, 9, //
				77, 123, 82, -91, 18, 17, -42, -57, //
				-87, -86, -119, -55, 65, 79, -112, -89, //
				86, 18, 114, -74, 89, 113, -75, 46, //
				51, -64, 88, -13, -103, -99, 44, 38, //
				62, -19, 17, 73, 66, 11, 52, 21, //
				76, 32, 35, -90, 55, 64, -63, -61 };

		assertThat( actualFingerprint, is( expectedFingerprint ) );
	}

	@Test
	public void testIsFingerprintValidPositive( ) throws KeyException {
		final InputStream inputStream = loadResourceAsStream( "key.public" );
		final PublicKey publicKey = KeyUtil.loadPublicKeyFromStream( inputStream );

		final byte[] expectedFingerprint = new byte[] { //
				90, 80, -62, 120, -97, 48, -117, 53, //
				113, -28, 12, 31, -19, 124, -104, 9, //
				77, 123, 82, -91, 18, 17, -42, -57, //
				-87, -86, -119, -55, 65, 79, -112, -89, //
				86, 18, 114, -74, 89, 113, -75, 46, //
				51, -64, 88, -13, -103, -99, 44, 38, //
				62, -19, 17, 73, 66, 11, 52, 21, //
				76, 32, 35, -90, 55, 64, -63, -61 };

		assertTrue( KeyUtil.isFingerprintValid( publicKey, expectedFingerprint ) );
	}

	@Test
	public void testIsFingerprintValidNegative( ) throws KeyException {
		final InputStream inputStream = loadResourceAsStream( "key.public" );
		final PublicKey publicKey = KeyUtil.loadPublicKeyFromStream( inputStream );

		final byte[] expectedFingerprint = new byte[] { //
				90, 80, -62, 120, -97, 48, -117, 53, //
				113, -28, 12, 31, -19, 124, -104, 9, //
				77, 123, 82, -91, 18, 17, -42, -57, //
				-87, -86, -119, -55, 65, 79, -112, -89, //
				86, 18, 114, -74, 89, 113, -75, 46, //
				51, -64, 88, -13, -103, -99, 44, 38, //
				62, -19, 17, 73, 66, 11, 52, 21, //
				76, 32, 35, -90, 55, 64, -63, -60 };

		assertFalse( KeyUtil.isFingerprintValid( publicKey, expectedFingerprint ) );
	}

	@Test
	public void testLoadAndSavePrivateKey( ) throws KeyException, IOException {
		final File targetFile = ivTemporaryFolder.newFile( );

		// Generate and save the key
		final KeyPair keyPair = KeyUtil.generateNewKeyPair( );
		final PrivateKey originalPrivateKey = keyPair.getPrivate( );
		KeyUtil.saveKeyToFile( originalPrivateKey, targetFile );

		// Load the key
		final PrivateKey loadedPrivateKey = KeyUtil.loadPrivateKeyFromFile( targetFile );

		// Compare the keys
		assertThat( originalPrivateKey.getEncoded( ), is( loadedPrivateKey.getEncoded( ) ) );
	}

	@Test
	public void testLoadAndSavePublicKey( ) throws KeyException, IOException {
		final File targetFile = ivTemporaryFolder.newFile( );

		// Generate and save the key
		final KeyPair keyPair = KeyUtil.generateNewKeyPair( );
		final PublicKey originalPublicKey = keyPair.getPublic( );
		KeyUtil.saveKeyToFile( originalPublicKey, targetFile );

		// Load the key
		final PublicKey loadedPublicKey = KeyUtil.loadPublicKeyFromFile( targetFile );

		// Compare the keys
		assertThat( originalPublicKey.getEncoded( ), is( loadedPublicKey.getEncoded( ) ) );
	}

	private InputStream loadResourceAsStream( final String aResourceName ) {
		return KeyUtilTest.class.getClassLoader( ).getResourceAsStream( aResourceName );
	}

}
