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

import static de.rhocas.lijense.Constants.LICENSE_ENCODING_CHARSET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.rhocas.lijense.io.IOUtil;
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

	@Test
	@SuppressWarnings( "deprecation" )
	public void testSaveAndLoadLicense( ) throws KeyException, LicenseException, IOException, ParseException {
		final File targetFile = ivTemporaryFolder.newFile( );

		// Generate a new key pair
		final KeyPair keyPair = KeyUtil.generateNewKeyPair( );
		final PrivateKey privateKey = keyPair.getPrivate( );
		final PublicKey publicKey = keyPair.getPublic( );

		// Create and sign the license file
		final ModifiableLicense modifiableLicense = new ModifiableLicense( );
		modifiableLicense.setProperty( "myFeature.active", "true" );
		modifiableLicense.setExpirationDate( new Date( 2000 - 1900, 2 - 1, 1 ) );
		LicenseUtil.saveLicenseFile( modifiableLicense, privateKey, targetFile );

		// Load and verify the license file (without fingerprint for the public key)
		final UnmodifiableLicense unmodifiableLicense = LicenseUtil.loadLicenseFile( publicKey, targetFile, Optional.<byte[]>empty( ) );
		assertThat( unmodifiableLicense.getValue( "myFeature.active" ) ).isEqualTo( "true" );
		assertThat( unmodifiableLicense.isExpired( ) ).isTrue( );
	}

	@Test
	@SuppressWarnings( "deprecation" )
	public void testSaveAndLoadLicenseWithoutValidation( ) throws KeyException, LicenseException, IOException, ParseException {
		final File targetFile = ivTemporaryFolder.newFile( );

		// Generate a new key pair
		final KeyPair keyPair = KeyUtil.generateNewKeyPair( );
		final PrivateKey privateKey = keyPair.getPrivate( );

		// Create and sign the license file
		final ModifiableLicense modifiableLicense = new ModifiableLicense( );
		modifiableLicense.setProperty( "myFeature.active", "true" );
		modifiableLicense.setExpirationDate( new Date( 2000 - 1900, 2 - 1, 1 ) );
		LicenseUtil.saveLicenseFile( modifiableLicense, privateKey, targetFile );

		// Load the license file
		final UnmodifiableLicense unmodifiableLicense = LicenseUtil.loadLicenseFileWithoutValidation( targetFile );
		assertThat( unmodifiableLicense.getValue( "myFeature.active" ) ).isEqualTo( "true" );
		assertThat( unmodifiableLicense.isExpired( ) ).isTrue( );
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
		assertThat( license.getValue( "myFeature.active" ) ).isEqualTo( "true" );
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

		assertThatExceptionOfType( LicenseException.class )
				.isThrownBy( ( ) -> LicenseUtil.loadLicenseFileFromInputStream( publicKey, licenseInputStream, Optional.of( expectedFingerprint ) ) )
				.withMessage( "The license is not valid" );
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

		assertThatExceptionOfType( LicenseException.class )
				.isThrownBy( ( ) -> LicenseUtil.loadLicenseFileFromInputStream( publicKey, licenseInputStream, Optional.of( expectedFingerprint ) ) )
				.withMessage( "The actual fingerprint of the public key does not match the expected fingerprint." );
	}

	@Test
	public void testLoadLicenseFromString( ) throws KeyException, LicenseException, IOException {
		final InputStream keyInputStream = loadResourceAsStream( "key.public" );
		final PublicKey publicKey = KeyUtil.loadPublicKeyFromStream( keyInputStream );

		final InputStream licenseInputStream = loadResourceAsStream( "valid.license" );
		final byte[] licenseArray = IOUtil.readAllBytes( licenseInputStream );
		final String licenseString = new String( licenseArray, LICENSE_ENCODING_CHARSET );

		final UnmodifiableLicense license = LicenseUtil.loadLicenseFileFromString( publicKey, licenseString, Optional.<byte[]>empty( ) );
		assertThat( license.getValue( "myFeature.active" ) ).isEqualTo( "true" );
	}

	@Test
	public void testLoadLicenseWithoutValidationFromString( ) throws LicenseException, IOException {
		final InputStream licenseInputStream = loadResourceAsStream( "invalid.license" );
		final byte[] licenseArray = IOUtil.readAllBytes( licenseInputStream );
		final String licenseString = new String( licenseArray, LICENSE_ENCODING_CHARSET );

		final UnmodifiableLicense license = LicenseUtil.loadLicenseFileWithoutValidationFromString( licenseString );
		assertThat( license.getValue( "myFeature.active" ) ).isEqualTo( "false" );
	}

	@Test
	public void testLoadLicenseFromEmptyInputStream( ) throws LicenseException, IOException {
		final InputStream licenseInputStream = new ByteArrayInputStream( new byte[0] );
		final byte[] licenseArray = IOUtil.readAllBytes( licenseInputStream );
		final String licenseString = new String( licenseArray, LICENSE_ENCODING_CHARSET );

		assertThatExceptionOfType( LicenseException.class )
				.isThrownBy( ( ) -> LicenseUtil.loadLicenseFileWithoutValidationFromString( licenseString ) )
				.withMessage( "Could not load the license" );
	}

	@Test
	public void testLoadLicenseFileWithInvalidFile( ) throws KeyException, LicenseException {
		final InputStream keyInputStream = loadResourceAsStream( "key.public" );
		final PublicKey publicKey = KeyUtil.loadPublicKeyFromStream( keyInputStream );
		final File nonExistingFile = new File( "notExisting" );

		assertThatExceptionOfType( LicenseException.class )
				.isThrownBy( ( ) -> LicenseUtil.loadLicenseFile( publicKey, nonExistingFile, Optional.empty( ) ) )
				.withCauseInstanceOf( FileNotFoundException.class )
				.withMessage( "Could not load the license" );
	}

	@Test
	public void loadLicenseFileWithoutValidationWithInvalidFile( ) throws LicenseException {
		final File nonExistingFile = new File( "notExisting" );

		assertThatExceptionOfType( LicenseException.class )
				.isThrownBy( ( ) -> LicenseUtil.loadLicenseFileWithoutValidation( nonExistingFile ) )
				.withCauseInstanceOf( FileNotFoundException.class )
				.withMessage( "Could not load the license" );
	}

	@Test
	public void testSaveLicenseFileWithInvalidFile( ) throws KeyException, LicenseException {
		final InputStream keyInputStream = loadResourceAsStream( "key.private" );
		final PrivateKey privateKey = KeyUtil.loadPrivateKeyFromStream( keyInputStream );

		assertThatExceptionOfType( LicenseException.class )
				.isThrownBy( ( ) -> LicenseUtil.saveLicenseFile( new ModifiableLicense( ), privateKey, new File( "" ) ) )
				.withCauseInstanceOf( IOException.class )
				.withMessage( "Could not save the license" );
	}

	@Test
	public void testConstructor( ) throws ReflectiveOperationException {
		final Constructor<LicenseUtil> constructor = LicenseUtil.class.getDeclaredConstructor( );
		constructor.setAccessible( true );

		assertThatExceptionOfType( InvocationTargetException.class )
				.isThrownBy( constructor::newInstance )
				.withCauseInstanceOf( AssertionError.class );
	}

	@Test
	public void testCreateLicenseFileWithInvalidKey( ) throws LicenseException, IOException {
		assertThatExceptionOfType( LicenseException.class )
				.isThrownBy( ( ) -> LicenseUtil.createLicenseFile( new ModifiableLicense( ), mock( PrivateKey.class ) ) )
				.withCauseInstanceOf( InvalidKeyException.class )
				.withMessage( "Could not create the license" );
	}

	private InputStream loadResourceAsStream( final String aResourceName ) {
		return KeyUtilTest.class.getClassLoader( ).getResourceAsStream( aResourceName );
	}

}
