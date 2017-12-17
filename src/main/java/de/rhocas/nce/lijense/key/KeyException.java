package de.rhocas.nce.lijense.key;

/**
 * This exception indicates that something went wrong while generating, saving,
 * or loading a key.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class KeyException extends Exception {

	private static final long serialVersionUID = -1841845934432427692L;

	public KeyException(final String aMessage, final Throwable aCause) {
		super(aMessage, aCause);
	}

}
