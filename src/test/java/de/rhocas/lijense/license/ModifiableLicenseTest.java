package de.rhocas.lijense.license;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
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
	public void testSetValueDate( ) {
		final ModifiableLicense license = new ModifiableLicense( );
		final Calendar calendar = new GregorianCalendar( );
		calendar.set( 2000, 11, 1 );

		license.setValue( "key", calendar.getTime( ) );

		assertThat( license.get( "key" ), is( "2000-12-01" ) );
	}

}
