package de.rhocas.nce.lijense.license;

import static de.rhocas.nce.lijense.Constants.DATE_FORMAT;
import static de.rhocas.nce.lijense.Constants.LICENSE_KEY_EXPIRATION_DATE;

import java.util.Date;
import java.util.Properties;

/**
 * This is a license container which can still be modified. It is usually used to create a new license file.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class ModifiableLicense extends Properties {

	private static final long serialVersionUID = -8676543638086345999L;

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 */
	public void setValue( final String aKey, final String aValue ) {
		setProperty( aKey, aValue );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 */
	public void setValue( final String aKey, final byte aValue ) {
		setValue( aKey, Byte.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 */
	public void setValue( final String aKey, final short aValue ) {
		setValue( aKey, Short.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 */
	public void setValue( final String aKey, final int aValue ) {
		setValue( aKey, Integer.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 */
	public void setValue( final String aKey, final long aValue ) {
		setValue( aKey, Long.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 */
	public void setValue( final String aKey, final float aValue ) {
		setValue( aKey, Float.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 */
	public void setValue( final String aKey, final double aValue ) {
		setValue( aKey, Double.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 */
	public void setValue( final String aKey, final char aValue ) {
		setValue( aKey, Character.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 */
	public void setValue( final String aKey, final boolean aValue ) {
		setValue( aKey, Boolean.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 */
	public void setValue( final String aKey, final Date aValue ) {
		final String value;
		if ( aValue != null ) {
			value = DATE_FORMAT.format( aValue );
		} else {
			value = null;
		}

		setValue( aKey, value );
	}

	/**
	 * Sets the expiration date of the license.
	 *
	 * @param aDate
	 *            The expiration date.
	 */
	public void setExpirationDate( final Date aDate ) {
		setValue( LICENSE_KEY_EXPIRATION_DATE, aDate );
	}

}
