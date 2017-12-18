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

import de.rhocas.lijense.key.KeyException;
import de.rhocas.lijense.key.KeyUtil;
import de.rhocas.lijense.key.KeyUtilTest;

/**
 * Unit test for {@link LicenseUtil}.
 *
 * @author Nils Christian Ehmke
 */
public final class LicenseUtilTest {

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
