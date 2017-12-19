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

import static de.rhocas.lijense.Constants.DATE_FORMAT;
import static de.rhocas.lijense.Constants.LICENSE_KEY_EXPIRATION_DATE;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit test for {@link UnmodifiableLicense}.
 *
 * @author Nils Christian Ehmke
 */
public class UnmodifiableLicenseTest {

	@Test
	public void testGetValueByte( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsByte( "key", (byte) 0 ), is( (byte) 42 ) );
	}

	@Test
	public void testGetValueByteDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsByte( "key", (byte) 1 ), is( (byte) 1 ) );
	}

	@Test
	public void testGetValueShort( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsShort( "key", (short) 0 ), is( (short) 42 ) );
	}

	@Test
	public void testGetValueShortDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsShort( "key", (short) 1 ), is( (short) 1 ) );
	}

	@Test
	public void testGetValueInt( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsInt( "key", 0 ), is( 42 ) );
	}

	@Test
	public void testGetValueIntDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsInt( "key", 1 ), is( 1 ) );
	}

	@Test
	public void testGetValueLong( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsLong( "key", 0L ), is( 42L ) );
	}

	@Test
	public void testGetValueLongDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsLong( "key", 1L ), is( 1L ) );
	}

	@Test
	public void testGetValueFloat( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42.0" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsFloat( "key", 0.0f ), is( 42.0f ) );
	}

	@Test
	public void testGetValueFloatDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsFloat( "key", 1.0f ), is( 1.0f ) );
	}

	@Test
	public void testGetValueDouble( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42.0" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsDouble( "key", 0.0 ), is( 42.0 ) );
	}

	@Test
	public void testGetValueDoubleDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsDouble( "key", 1.0 ), is( 1.0 ) );
	}

	@Test
	public void testGetValueBoolean( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "false" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsBoolean( "key", true ), is( false ) );
	}

	@Test
	public void testGetValueBooleanDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsBoolean( "key", true ), is( true ) );
	}

	@Test
	public void testGetValueChar( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "A" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsChar( "key", 'B' ), is( 'A' ) );
	}

	@Test
	public void testGetValueCharDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsChar( "key", 'B' ), is( 'B' ) );
	}

	@Test
	@SuppressWarnings ( "deprecation" )
	public void testGetValueDate( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "2000-12-01" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		final Date date = license.getValueAsDate( "key", null );

		assertThat( date.getYear( ), is( 2000 - 1900 ) );
		assertThat( date.getMonth( ), is( 12 - 1 ) );
		assertThat( date.getDate( ), is( 1 ) );
	}

	@Test
	@SuppressWarnings ( "deprecation" )
	public void testGetValueDateDefault( ) throws ParseException {
		final Date defaultDate = new Date( );
		defaultDate.setYear( 2000 - 1900 );
		defaultDate.setMonth( 12 - 1 );
		defaultDate.setDate( 1 );

		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		final Date date = license.getValueAsDate( "key", defaultDate );

		assertThat( date.getYear( ), is( 2000 - 1900 ) );
		assertThat( date.getMonth( ), is( 12 - 1 ) );
		assertThat( date.getDate( ), is( 1 ) );
	}

	@Test
	public void testImmutability( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "value1" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		map.put( "key", "value2" );

		assertThat( map.get( "key" ), is( "value2" ) );
		assertThat( license.getValue( "key" ), is( "value1" ) );
	}

	@Test
	public void testIsExpired1( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( LICENSE_KEY_EXPIRATION_DATE, "2000-12-01" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );

		assertTrue( license.isExpired( ) );
	}

	@Test
	public void testIsExpired2( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( LICENSE_KEY_EXPIRATION_DATE, "3000-12-01" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertFalse( license.isExpired( ) );
	}

	@Test
	public void testIsExpired3( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( LICENSE_KEY_EXPIRATION_DATE, DATE_FORMAT.format( new Date( ) ) );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertFalse( license.isExpired( ) );
	}

	@Test
	public void testIsExpired4( ) throws ParseException {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertFalse( license.isExpired( ) );
	}

	@Test
	public void testIsFeatureActive1( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( "feature", "true" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertTrue( license.isFeatureActive( "feature" ) );
	}

	@Test
	public void testIsFeatureActive2( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( "feature", "false" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertFalse( license.isFeatureActive( "feature" ) );
	}

	@Test
	public void testIsFeatureActive3( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( "feature", "" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertFalse( license.isFeatureActive( "feature" ) );
	}

	@Test
	public void testIsFeatureActive4( ) throws ParseException {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertFalse( license.isFeatureActive( "feature" ) );
	}

}
