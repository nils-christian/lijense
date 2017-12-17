package de.rhocas.nce.lijense.license;

/**
 * This exception indicates that something went wrong while generating, saving, or loading a license.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class LicenseException extends Exception {

	private static final long serialVersionUID = 577141289233795406L;

	public LicenseException( final String aMessage ) {
		super( aMessage );
	}

	public LicenseException( final String aMessage, final Throwable aCause ) {
		super( aMessage, aCause );
	}

}
