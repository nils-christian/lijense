package de.rhocas.nce.lijense;

/**
 * This class contains all constants used throughout the liJense framework.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class Constants {

	/**
	 * This is the algorithm used for the keys. This is currently RSA.
	 *
	 * @since 1.0.0
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * This is the size of the keys. This is currently 4096 bit.
	 *
	 * @since 1.0.0
	 */
	public static final int KEY_SIZE = 4096;

	/**
	 * This is the algorithm used for the random generator to create the keys. This is currently a pseudo random algorithm based on SHA-1.
	 *
	 * @since 1.0.0
	 */
	public static final String RANDOM_ALGORITHM = "SHA1PRNG";

	/**
	 * This is the algorithm used for calculating the fingerprint of a public key. This is currently SHA-512.
	 *
	 * @since 1.0.0
	 */
	public static final String FINGERPRINT_ALGORITHM = "SHA-512";

	private Constants( ) {
		// Avoid instantiation
	}

}
