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
import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDate;
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
		assertThat( license.getValueAsByte( "key", ( byte ) 0 ) ).isEqualTo( ( byte ) 42 );
	}

	@Test
	public void testGetValueByteDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsByte( "key", ( byte ) 1 ) ).isEqualTo( ( byte ) 1 );
	}

	@Test
	public void testGetValueByteDefaultWhenEmpty( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsByte( "key", ( byte ) 42 ) ).isEqualTo( ( byte ) 42 );
	}

	@Test
	public void testGetValueShort( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsShort( "key", ( short ) 0 ) ).isEqualTo( ( short ) 42 );
	}

	@Test
	public void testGetValueShortDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsShort( "key", ( short ) 1 ) ).isEqualTo( ( short ) 1 );
	}

	@Test
	public void testGetValueShortDefaultWhenEmpty( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsShort( "key", ( short ) 1 ) ).isEqualTo( ( short ) 1 );
	}

	@Test
	public void testGetValueInt( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsInt( "key", 0 ) ).isEqualTo( 42 );
	}

	@Test
	public void testGetValueIntDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsInt( "key", 1 ) ).isEqualTo( 1 );
	}

	@Test
	public void testGetValueIntDefaultWhenEmpty( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsInt( "key", 1 ) ).isEqualTo( 1 );
	}

	@Test
	public void testGetValueLong( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsLong( "key", 0L ) ).isEqualTo( 42L );
	}

	@Test
	public void testGetValueLongDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsLong( "key", 1L ) ).isEqualTo( 1L );
	}

	@Test
	public void testGetValueLongDefaultWhenEmpty( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsLong( "key", 1L ) ).isEqualTo( 1L );
	}

	@Test
	public void testGetValueFloat( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42.0" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsFloat( "key", 0.0f ) ).isEqualTo( 42.0f );
	}

	@Test
	public void testGetValueFloatDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsFloat( "key", 1.0f ) ).isEqualTo( 1.0f );
	}

	@Test
	public void testGetValueFloatDefaultWhenEmpty( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsFloat( "key", 1.0f ) ).isEqualTo( 1.0f );
	}

	@Test
	public void testGetValueDouble( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "42.0" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsDouble( "key", 0.0 ) ).isEqualTo( 42.0 );
	}

	@Test
	public void testGetValueDoubleDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsDouble( "key", 1.0 ) ).isEqualTo( 1.0 );
	}

	@Test
	public void testGetValueDoubleDefaultWhenEmpty( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsDouble( "key", 1.0 ) ).isEqualTo( 1.0 );
	}

	@Test
	public void testGetValueBoolean( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "false" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsBoolean( "key", true ) ).isFalse( );
	}

	@Test
	public void testGetValueBooleanDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsBoolean( "key", true ) ).isTrue( );
	}

	@Test
	public void testGetValueBooleanDefaultWhenEmpty( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsBoolean( "key", true ) ).isTrue( );
	}

	@Test
	public void testGetValueChar( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "A" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsChar( "key", 'B' ) ).isEqualTo( 'A' );
	}

	@Test
	public void testGetValueCharDefault( ) {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.getValueAsChar( "key", 'B' ) ).isEqualTo( 'B' );
	}

	@Test
	public void testGetValueCharDefaultWhenEmpty( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.getValueAsChar( "key", 'B' ) ).isEqualTo( 'B' );
	}

	@Test
	@SuppressWarnings( "deprecation" )
	public void testGetValueDate( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "2000-12-01" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		final Date date = license.getValueAsDate( "key", null );

		assertThat( date.getYear( ) ).isEqualTo( 2000 - 1900 );
		assertThat( date.getMonth( ) ).isEqualTo( 12 - 1 );
		assertThat( date.getDate( ) ).isEqualTo( 1 );
	}

	@Test
	public void testGetValueLocalDate( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "2000-12-01" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		final LocalDate date = license.getValueAsLocalDate( "key", null );

		assertThat( date.getYear( ) ).isEqualTo( 2000 );
		assertThat( date.getMonthValue( ) ).isEqualTo( 12 );
		assertThat( date.getDayOfMonth( ) ).isEqualTo( 1 );
	}

	@Test
	@SuppressWarnings( "deprecation" )
	public void testGetValueDateDefault( ) throws ParseException {
		final Date defaultDate = new Date( );
		defaultDate.setYear( 2000 - 1900 );
		defaultDate.setMonth( 12 - 1 );
		defaultDate.setDate( 1 );

		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		final Date date = license.getValueAsDate( "key", defaultDate );

		assertThat( date.getYear( ) ).isEqualTo( 2000 - 1900 );
		assertThat( date.getMonth( ) ).isEqualTo( 12 - 1 );
		assertThat( date.getDate( ) ).isEqualTo( 1 );
	}

	@Test
	@SuppressWarnings( "deprecation" )
	public void testGetValueDateDefaultWhenEmpty( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "" );

		final Date defaultDate = new Date( );
		defaultDate.setYear( 2000 - 1900 );
		defaultDate.setMonth( 12 - 1 );
		defaultDate.setDate( 1 );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		final Date date = license.getValueAsDate( "key", defaultDate );

		assertThat( date.getYear( ) ).isEqualTo( 2000 - 1900 );
		assertThat( date.getMonth( ) ).isEqualTo( 12 - 1 );
		assertThat( date.getDate( ) ).isEqualTo( 1 );
	}

	@Test
	public void testGetValueLocalDateDefault( ) throws ParseException {
		final LocalDate defaultDate = LocalDate.of( 2000, 12, 1 );

		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		final LocalDate date = license.getValueAsLocalDate( "key", defaultDate );

		assertThat( date.getYear( ) ).isEqualTo( 2000 );
		assertThat( date.getMonthValue( ) ).isEqualTo( 12 );
		assertThat( date.getDayOfMonth( ) ).isEqualTo( 1 );
	}

	@Test
	public void testGetValueLocalDateDefaultWhenEmpty( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "" );

		final LocalDate defaultDate = LocalDate.of( 2000, 12, 1 );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		final LocalDate date = license.getValueAsLocalDate( "key", defaultDate );

		assertThat( date.getYear( ) ).isEqualTo( 2000 );
		assertThat( date.getMonthValue( ) ).isEqualTo( 12 );
		assertThat( date.getDayOfMonth( ) ).isEqualTo( 1 );
	}

	@Test
	public void testImmutability( ) {
		final Map<String, String> map = new HashMap<>( );
		map.put( "key", "value1" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		map.put( "key", "value2" );

		assertThat( map.get( "key" ) ).isEqualTo( "value2" );
		assertThat( license.getValue( "key" ) ).isEqualTo( "value1" );
	}

	@Test
	public void testIsExpired1( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( LICENSE_KEY_EXPIRATION_DATE, "2000-12-01" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );

		assertThat( license.isExpired( ) ).isTrue( );
	}

	@Test
	public void testIsExpired2( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( LICENSE_KEY_EXPIRATION_DATE, "3000-12-01" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.isExpired( ) ).isFalse( );
	}

	@Test
	public void testIsExpired3( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( LICENSE_KEY_EXPIRATION_DATE, DATE_FORMAT.format( new Date( ) ) );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.isExpired( ) ).isFalse( );
	}

	@Test
	public void testIsExpired4( ) throws ParseException {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.isExpired( ) ).isFalse( );
	}

	@Test
	public void testIsFeatureActive1( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( "feature", "true" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.isFeatureActive( "feature" ) ).isTrue( );
	}

	@Test
	public void testIsFeatureActive2( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( "feature", "false" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.isFeatureActive( "feature" ) ).isFalse( );
	}

	@Test
	public void testIsFeatureActive3( ) throws ParseException {
		final Map<String, String> map = new HashMap<>( );
		map.put( "feature", "" );

		final UnmodifiableLicense license = new UnmodifiableLicense( map );
		assertThat( license.isFeatureActive( "feature" ) ).isFalse( );
	}

	@Test
	public void testIsFeatureActive4( ) throws ParseException {
		final UnmodifiableLicense license = new UnmodifiableLicense( new HashMap<>( ) );
		assertThat( license.isFeatureActive( "feature" ) ).isFalse( );
	}

}
