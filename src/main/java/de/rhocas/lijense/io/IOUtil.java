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

package de.rhocas.lijense.io;

import static de.rhocas.lijense.Constants.LICENSE_ENCODING_CHARSET;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Objects;

/**
 * This is an internal helper util class to handle some IO issues.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class IOUtil {

	private IOUtil( ) {
		throw new AssertionError( "This util class must not be initialized." );
	}

	/**
	 * This method reads all bytes from the given stream. The stream is not closed.
	 *
	 * @param aStream
	 *            The stream to read from. Must not be {@code null}.
	 *
	 * @return All bytes from the stream.
	 *
	 * @throws IOException
	 *             If something went wrong while reading from the stream.
	 * @throws NullPointerException
	 * 	           If the given stream is {@code null}.
	 *
	 * @since 1.0.0
	 */
	public static byte[] readAllBytes( final InputStream aStream ) throws IOException {
		Objects.requireNonNull( aStream, "The stream must not be null." );

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

	/**
	 * This method converts the given binary data to an ASCII string by using Base64 encoding.
	 *
	 * @param aBinary
	 *            The binary array. Must not be {@code null}.
	 *
	 * @return The Base64 encoded data.
	 *
	 * @throws NullPointerException
	 * 	           If the given array is {@code null}.
	 *
	 * @since 2.0.0
	 */
	public static String binaryToString( final byte[] aBinary ) {
		Objects.requireNonNull( aBinary, "The array must not be null." );

		final byte[] stringBytes = binaryToBinaryString( aBinary );
		return new String( stringBytes, LICENSE_ENCODING_CHARSET );
	}

	/**
	 * This method converts the given ASCII string to binary data by using Base64 decoding.
	 *
	 * @param aString
	 *            The Base64 encoded data. Must not be {@code null}.
	 *
	 * @return The binary data.
	 *
	 * @throws NullPointerException
	 * 	           If the given string is {@code null}.
	 *
	 * @since 2.0.0
	 */
	public static byte[] stringToBinary( final String aString ) {
		Objects.requireNonNull( aString, "The string must not be null." );

		final Decoder decoder = Base64.getDecoder( );
		final byte[] stringBytes = aString.getBytes( LICENSE_ENCODING_CHARSET );
		return decoder.decode( stringBytes );
	}

	/**
	 * This method converts the given ASCII string (given in binary format) to binary data by using Base64 decoding.
	 *
	 * @param aString
	 *            The Base64 encoded data. Must not be {@code null}.
	 *
	 * @return The binary data.
	 *
	 * @throws NullPointerException
	 * 	           If the given string is {@code null}.
	 *
	 * @since 2.0.0
	 */
	public static byte[] binaryStringToBinary( final byte[] aString ) {
		Objects.requireNonNull( aString, "The string must not be null." );

		final String base64EncodedString = new String( aString, LICENSE_ENCODING_CHARSET );
		return stringToBinary( base64EncodedString );
	}

	/**
	 * This method converts the given binary data to an ASCII string (given in binary format) by using Base64 encoding.
	 *
	 * @param aString
	 *            The binary array. Must not be {@code null}.
	 *
	 * @return The Base64 encoded data.
	 *
	 * @throws NullPointerException
	 * 	           If the given string is {@code null}.
	 *
	 * @since 2.0.0
	 */
	public static byte[] binaryToBinaryString( final byte[] aString ) {
		Objects.requireNonNull( aString, "The string must not be null." );

		final Encoder encoder = Base64.getEncoder( );
		return encoder.encode( aString );
	}

}
