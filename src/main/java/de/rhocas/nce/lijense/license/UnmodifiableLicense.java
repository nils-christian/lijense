package de.rhocas.nce.lijense.license;

import static de.rhocas.nce.lijense.Constants.DATE_FORMAT;
import static de.rhocas.nce.lijense.Constants.LICENSE_KEY_EXPIRATION_DATE;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
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
			final Date now = new Date( );
			if ( now.after( expirationDate ) ) {
				return true;
			}
		}

		return false;
	}

}
