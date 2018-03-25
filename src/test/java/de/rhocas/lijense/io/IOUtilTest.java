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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

/**
 * Unit test for {@link IOUtil}.
 *
 * @author Nils Christian Ehmke
 */
public final class IOUtilTest {

	@Test
	public void testReadAllBytes( ) throws IOException {
		final byte[] buffer = createAndFillBuffer( );
		final ByteArrayInputStream inputStream = new ByteArrayInputStream( buffer );

		final byte[] readBytes = IOUtil.readAllBytes( inputStream );
		assertThat( readBytes ).isEqualTo( buffer );
	}

	private byte[] createAndFillBuffer( ) {
		final byte[] buffer = new byte[2000];
		for ( int i = 0; i < buffer.length; i++ ) {
			buffer[i] = (byte) ( i % 256 );
		}
		return buffer;
	}

	@Test
	public void testBinaryToString( ) {
		assertThat( IOUtil.binaryToString( "liJense".getBytes( ) ) ).isEqualTo( "bGlKZW5zZQ==" );
	}

	@Test
	public void testStringToBinary( ) {
		assertThat( IOUtil.stringToBinary( "bGlKZW5zZQ==" ) ).isEqualTo( "liJense".getBytes( ) );
	}

	@Test
	public void testBinaryToBinaryString( ) {
		assertThat( IOUtil.binaryToBinaryString( "liJense".getBytes( ) ) ).isEqualTo( "bGlKZW5zZQ==".getBytes( ) );
	}

	@Test
	public void testBinaryStringToBinary( ) {
		assertThat( IOUtil.binaryStringToBinary( "bGlKZW5zZQ==".getBytes( ) ) ).isEqualTo( "liJense".getBytes( ) );
	}

}
