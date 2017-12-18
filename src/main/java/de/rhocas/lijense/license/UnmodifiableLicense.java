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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a license container which can no longer be modified. It is usually used in the application after the license file has been verified and loaded.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class UnmodifiableLicense {

	private final Map<String, String> ivInternalMap;

	/**
	 * Creates a new instance of this class.
	 *
	 * @param aInternalMap
	 *            The mapping used within this license. The map is copied and made unmodifiable. A deep copy is not performed.
	 *
	 * @since 1.0.0
	 */
	public UnmodifiableLicense( final Map<String, String> aInternalMap ) {
		// Copy the given map and make it immutable
		final Map<String, String> newMap = new HashMap<>( aInternalMap );
		ivInternalMap = Collections.unmodifiableMap( newMap );
	}

	/**
	 * Returns the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 *
	 * @return The value for the key.
	 *
	 * @since 1.0.0
	 */
	public String getValue( final String aKey ) {
		return ivInternalMap.get( aKey );
	}

	/**
	 * This method checks whether a given feature is active or not. If the given key is not available or the value is {@code null}, empty or {@code false}, the
	 * method returns {@code false}. Otherwise {@code true} is returned.
	 *
	 * @param aKey
	 *            The key.
	 *
	 * @return true if and only if the given feature is active.
	 *
	 * @since 1.0.0
	 */
	public boolean isFeatureActive( final String aKey ) {
		final String value = getValue( aKey );
		if ( value != null && !value.isEmpty( ) ) {
			return Boolean.parseBoolean( value );
		} else {
			return false;
		}
	}

	/**
	 * Returns the value for the given key as byte. If the key is not available or the value is {@code null} or empty, the given default value will be returned
	 * instead.
	 *
	 * @param aKey
	 *            The key.
	 * @param aDefault
	 *            The default value.
	 *
	 * @return The value for the key or the default value.
	 *
	 * @since 1.0.0
	 */
	public byte getValueAsByte( final String aKey, final byte aDefault ) {
		final String value = getValue( aKey );

		if ( value != null && !value.isEmpty( ) ) {
			return Byte.valueOf( value );
		} else {
			return aDefault;
		}
	}

	/**
	 * Returns the value for the given key as short. If the key is not available or the value is {@code null} or empty, the given default value will be returned
	 * instead.
	 *
	 * @param aKey
	 *            The key.
	 * @param aDefault
	 *            The default value.
	 *
	 * @return The value for the key or the default value.
	 *
	 * @since 1.0.0
	 */
	public short getValueAsShort( final String aKey, final short aDefault ) {
		final String value = getValue( aKey );

		if ( value != null && !value.isEmpty( ) ) {
			return Short.valueOf( value );
		} else {
			return aDefault;
		}
	}

	/**
	 * Returns the value for the given key as integer. If the key is not available or the value is {@code null} or empty, the given default value will be
	 * returned instead.
	 *
	 * @param aKey
	 *            The key.
	 * @param aDefault
	 *            The default value.
	 *
	 * @return The value for the key or the default value.
	 *
	 * @since 1.0.0
	 */
	public int getValueAsInt( final String aKey, final int aDefault ) {
		final String value = getValue( aKey );

		if ( value != null && !value.isEmpty( ) ) {
			return Integer.valueOf( value );
		} else {
			return aDefault;
		}
	}

	/**
	 * Returns the value for the given key as long. If the key is not available or the value is {@code null} or empty, the given default value will be returned
	 * instead.
	 *
	 * @param aKey
	 *            The key.
	 * @param aDefault
	 *            The default value.
	 *
	 * @return The value for the key or the default value.
	 *
	 * @since 1.0.0
	 */
	public long getValueAsLong( final String aKey, final long aDefault ) {
		final String value = getValue( aKey );

		if ( value != null && !value.isEmpty( ) ) {
			return Long.valueOf( value );
		} else {
			return aDefault;
		}
	}

	/**
	 * Returns the value for the given key as float. If the key is not available or the value is {@code null} or empty, the given default value will be returned
	 * instead.
	 *
	 * @param aKey
	 *            The key.
	 * @param aDefault
	 *            The default value.
	 *
	 * @return The value for the key or the default value.
	 *
	 * @since 1.0.0
	 */
	public float getValueAsFloat( final String aKey, final float aDefault ) {
		final String value = getValue( aKey );

		if ( value != null && !value.isEmpty( ) ) {
			return Float.valueOf( value );
		} else {
			return aDefault;
		}
	}

	/**
	 * Returns the value for the given key as double. If the key is not available or the value is {@code null} or empty, the given default value will be
	 * returned instead.
	 *
	 * @param aKey
	 *            The key.
	 * @param aDefault
	 *            The default value.
	 *
	 * @return The value for the key or the default value.
	 *
	 * @since 1.0.0
	 */
	public double getValueAsDouble( final String aKey, final double aDefault ) {
		final String value = getValue( aKey );

		if ( value != null && !value.isEmpty( ) ) {
			return Double.valueOf( value );
		} else {
			return aDefault;
		}
	}

	/**
	 * Returns the value for the given key as boolean. If the key is not available or the value is {@code null} or empty, the given default value will be
	 * returned instead.
	 *
	 * @param aKey
	 *            The key.
	 * @param aDefault
	 *            The default value.
	 *
	 * @return The value for the key or the default value.
	 *
	 * @since 1.0.0
	 */
	public boolean getValueAsBoolean( final String aKey, final boolean aDefault ) {
		final String value = getValue( aKey );

		if ( value != null && !value.isEmpty( ) ) {
			return Boolean.valueOf( value );
		} else {
			return aDefault;
		}
	}

	/**
	 * Returns the value for the given key as char. If the key is not available or the value is {@code null} or empty, the given default value will be returned
	 * instead.
	 *
	 * @param aKey
	 *            The key.
	 * @param aDefault
	 *            The default value.
	 *
	 * @return The value for the key or the default value.
	 *
	 * @since 1.0.0
	 */
	public char getValueAsChar( final String aKey, final char aDefault ) {
		final String value = getValue( aKey );

		if ( value != null && !value.isEmpty( ) ) {
			return value.charAt( 0 );
		} else {
			return aDefault;
		}
	}

	/**
	 * Returns the value for the given key as date. If the key is not available or the value is {@code null} or empty, the given default value will be returned
	 * instead.
	 *
	 * @param aKey
	 *            The key.
	 * @param aDefault
	 *            The default value.
	 *
	 * @return The value for the key or the default value.
	 *
	 * @throws ParseException
	 *             If the date could not be parsed.
	 *
	 * @since 1.0.0
	 */
	public Date getValueAsDate( final String aKey, final Date aDefault ) throws ParseException {
		final String value = getValue( aKey );

		if ( value != null && !value.isEmpty( ) ) {
			return DATE_FORMAT.parse( value );
		} else {
			return aDefault;
		}
	}

	/**
	 * This method checks whether the license is expired or not. If the expiration value is not set, {@code null} or empty, the license is not expired.
	 * Otherwise the expiration date will be checked against the current date.
	 *
	 * @return true if and only if the license is expired.
	 *
	 * @throws ParseException
	 *             If the date could not be parsed.
	 *
	 * @since 1.0.0
	 */
	public boolean isExpired( ) throws ParseException {
		final Date expirationDate = getValueAsDate( LICENSE_KEY_EXPIRATION_DATE, null );

		if ( expirationDate != null ) {
			// Make sure that we ignore the time when we compare the dates
			final GregorianCalendar expiration = new GregorianCalendar( );
			expiration.setTime( expirationDate );
			clearTimeFromCalendar( expiration );

			final GregorianCalendar now = new GregorianCalendar( );
			clearTimeFromCalendar( now );

			if ( now.after( expiration ) ) {
				return true;
			}
		}

		return false;
	}

	private void clearTimeFromCalendar( final GregorianCalendar aCalendar ) {
		aCalendar.set( Calendar.HOUR_OF_DAY, 0 );
		aCalendar.set( Calendar.MINUTE, 0 );
		aCalendar.set( Calendar.SECOND, 0 );
		aCalendar.set( Calendar.MILLISECOND, 0 );
	}

}
