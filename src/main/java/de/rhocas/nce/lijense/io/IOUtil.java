package de.rhocas.nce.lijense.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is an internal helper util class to handle some IO issues.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public class IOUtil {

	private IOUtil( ) {
		// Avoid instantiation
	}

	/**
	 * This method reads all bytes from the given stream. The stream is not closed.
	 *
	 * @param aStream
	 *            The stream to read from.
	 *
	 * @return All bytes from the stream.
	 *
	 * @throws IOException
	 *             If something went wrong while reading from the stream.
	 *
	 * @since 1.0.0
	 */
	public static byte[] readAllBytes( final InputStream aStream ) throws IOException {
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );

		final byte[] buffer = new byte[1024];

		int read;
		do {
			read = aStream.read( buffer );

			if ( read > 0 ) {
				outputStream.write( buffer, 0, read );
			}
		} while ( read > 0 );

		return outputStream.toByteArray( );
	}

}
