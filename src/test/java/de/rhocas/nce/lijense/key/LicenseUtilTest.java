package de.rhocas.nce.lijense.key;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import de.rhocas.nce.lijense.license.LicenseException;
import de.rhocas.nce.lijense.license.LicenseUtil;
import de.rhocas.nce.lijense.license.ModifiableLicense;
import de.rhocas.nce.lijense.license.UnmodifiableLicense;

/**
 * Unit test for {@link LicenseUtil}.
 *
 * @author Nils Christian Ehmke
 */
public class LicenseUtilTest {

	@Rule
	public TemporaryFolder ivTemporaryFolder = new TemporaryFolder( );

	@Rule
	public ExpectedException ivExpectedException = ExpectedException.none( );

	@Test
	public void testSaveAndLoadLicense( ) throws KeyException, LicenseException, IOException {
		final File targetFile = ivTemporaryFolder.newFile( );

		// Generate a new key pair
		final KeyPair keyPair = KeyUtil.generateNewKeyPair( );
		final PrivateKey privateKey = keyPair.getPrivate( );
		final PublicKey publicKey = keyPair.getPublic( );

		// Create and sign the license file
		final ModifiableLicense modifiableLicense = new ModifiableLicense( );
		modifiableLicense.setProperty( "myFeature.active", "true" );
		LicenseUtil.saveLicenseFile( modifiableLicense, privateKey, targetFile );

		// Load and verify the license file (without fingerprint for the public key)
		final UnmodifiableLicense unmodifiableLicense = LicenseUtil.loadLicenseFile( publicKey, targetFile, Optional.<byte[]>empty( ) );
		assertThat( unmodifiableLicense.getValue( "myFeature.active" ), is( "true" ) );
	}

	@Test
	public void testLoadLicensePositive( ) throws KeyException, LicenseException {
		final InputStream keyInputStream = loadResourceAsStream( "key.public" );
		final PublicKey publicKey = KeyUtil.loadPublicKeyFromStream( keyInputStream );

		final byte[] expectedFingerprint = new byte[] { //
				-96, -95, 56, -80, 0, -5, 49, -82, //
				-34, 44, -112, -20, -110, -38, 21, 28, //
				72, 88, 96, 37, -24, 48, -122, 34, //
				-12, 46, -109, 40, -4, -46, 105, -49, //
				117, 59, 30, 124, 4, -67, -107, -90, //
				-62, 115, 110, -102, 127, -126, 119, 78, //
				-75, 46, 30, 101, -53, -49, 59, 71, //
				-97, 54, -58, -38, 31, 102, 58, -122 };

		final InputStream licenseInputStream = loadResourceAsStream( "valid.license" );

		final UnmodifiableLicense license = LicenseUtil.loadLicenseFileFromInputStream( publicKey, licenseInputStream, Optional.of( expectedFingerprint ) );
		assertThat( license.getValue( "myFeature.active" ), is( "true" ) );
	}

	@Test
	public void testLoadLicenseNegative( ) throws KeyException, LicenseException {
		final InputStream keyInputStream = loadResourceAsStream( "key.public" );
		final PublicKey publicKey = KeyUtil.loadPublicKeyFromStream( keyInputStream );

		final byte[] expectedFingerprint = new byte[] { //
				-96, -95, 56, -80, 0, -5, 49, -82, //
				-34, 44, -112, -20, -110, -38, 21, 28, //
				72, 88, 96, 37, -24, 48, -122, 34, //
				-12, 46, -109, 40, -4, -46, 105, -49, //
				117, 59, 30, 124, 4, -67, -107, -90, //
				-62, 115, 110, -102, 127, -126, 119, 78, //
				-75, 46, 30, 101, -53, -49, 59, 71, //
				-97, 54, -58, -38, 31, 102, 58, -122 };

		final InputStream licenseInputStream = loadResourceAsStream( "invalid.license" );

		ivExpectedException.expect( LicenseException.class );
		ivExpectedException.expectMessage( "The license is not valid" );
		LicenseUtil.loadLicenseFileFromInputStream( publicKey, licenseInputStream, Optional.of( expectedFingerprint ) );
	}

	@Test
	public void testLoadLicenseNegativeFingerprint( ) throws KeyException, LicenseException {
		final InputStream keyInputStream = loadResourceAsStream( "key.public" );
		final PublicKey publicKey = KeyUtil.loadPublicKeyFromStream( keyInputStream );

		final byte[] expectedFingerprint = new byte[] { //
				-95, -95, 56, -80, 0, -5, 49, -82, //
				-34, 44, -112, -20, -110, -38, 21, 28, //
				72, 88, 96, 37, -24, 48, -122, 34, //
				-12, 46, -109, 40, -4, -46, 105, -49, //
				117, 59, 30, 124, 4, -67, -107, -90, //
				-62, 115, 110, -102, 127, -126, 119, 78, //
				-75, 46, 30, 101, -53, -49, 59, 71, //
				-97, 54, -58, -38, 31, 102, 58, -122 };

		final InputStream licenseInputStream = loadResourceAsStream( "valid.license" );

		ivExpectedException.expect( LicenseException.class );
		ivExpectedException.expectMessage( "The actual fingerprint of the public key does not match the expected fingerprint." );
		LicenseUtil.loadLicenseFileFromInputStream( publicKey, licenseInputStream, Optional.of( expectedFingerprint ) );
	}

	private InputStream loadResourceAsStream( final String aResourceName ) {
		return KeyUtilTest.class.getClassLoader( ).getResourceAsStream( aResourceName );
	}

}
