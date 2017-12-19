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
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * Unit test for {@link ModifiableLicense}.
 *
 * @author Nils Christian Ehmke
 */
public final class ModifiableLicenseTest {

	@Test
	public void testSetValueByte( ) {
		final ModifiableLicense license = new ModifiableLicense( );
		license.setValue( "key", (byte) 42 );

		assertThat( license.get( "key" ), is( "42" ) );
	}

	@Test
	public void testSetValueShort( ) {
		final ModifiableLicense license = new ModifiableLicense( );
		license.setValue( "key", (short) 42 );

		assertThat( license.get( "key" ), is( "42" ) );
	}

	@Test
	public void testSetValueInt( ) {
		final ModifiableLicense license = new ModifiableLicense( );
		license.setValue( "key", 42 );

		assertThat( license.get( "key" ), is( "42" ) );
	}

	@Test
	public void testSetValueLong( ) {
		final ModifiableLicense license = new ModifiableLicense( );
		license.setValue( "key", 42L );

		assertThat( license.get( "key" ), is( "42" ) );
	}

	@Test
	public void testSetValueFloat( ) {
		final ModifiableLicense license = new ModifiableLicense( );
		license.setValue( "key", 42.0f );

		assertThat( license.get( "key" ), is( "42.0" ) );
	}

	@Test
	public void testSetValueDouble( ) {
		final ModifiableLicense license = new ModifiableLicense( );
		license.setValue( "key", 42.0 );

		assertThat( license.get( "key" ), is( "42.0" ) );
	}

	@Test
	public void testSetValueBoolean( ) {
		final ModifiableLicense license = new ModifiableLicense( );
		license.setValue( "key", false );

		assertThat( license.get( "key" ), is( "false" ) );
	}

	@Test
	public void testSetValueChar( ) {
		final ModifiableLicense license = new ModifiableLicense( );
		license.setValue( "key", 'A' );

		assertThat( license.get( "key" ), is( "A" ) );
	}

	@Test
	public void testSetValueDate1( ) {
		final ModifiableLicense license = new ModifiableLicense( );
		final Calendar calendar = new GregorianCalendar( );
		calendar.set( 2000, 11, 1 );

		license.setValue( "key", calendar.getTime( ) );

		assertThat( license.get( "key" ), is( "2000-12-01" ) );
	}

	@Test
	public void testSetValueDate2( ) {
		final ModifiableLicense license = new ModifiableLicense( );

		license.setValue( "key", (Date) null );

		assertThat( license.get( "key" ), is( nullValue( ) ) );
	}

}
