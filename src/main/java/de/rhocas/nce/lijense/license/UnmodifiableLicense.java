package de.rhocas.nce.lijense.license;

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

	public UnmodifiableLicense( final Map<String, String> aInternalMap ) {
		ivInternalMap = aInternalMap;
	}

	/**
	 * Returns the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 *
	 * @return The value for the key.
	 */
	public String getValue( final String aKey ) {
		return ivInternalMap.get( aKey );
	}

}
