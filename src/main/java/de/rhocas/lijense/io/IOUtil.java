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
