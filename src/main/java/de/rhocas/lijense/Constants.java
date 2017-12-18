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

package de.rhocas.lijense;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

	/**
	 * This is the algorithm used for the digital signature. This is currently SHA-512 with a RSA key.
	 *
	 * @since 1.0.0
	 */
	public static final String SIGNATURE_ALGORITHM = "SHA512withRSA";

	/**
	 * This is the encoding of the internal properties file. This is currently UTF-8.
	 *
	 * @since 1.0.0
	 */
	public static final String LICENSE_ENCODING = "UTF-8";

	/**
	 * This is the date format used to encode and parse dates in the license file.
	 *
	 * @since 1.0.0
	 */
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat( "yyyy-MM-dd", Locale.ENGLISH );

	/**
	 * This is the key for the expiration date in the license file.
	 *
	 * @since 1.0.0
	 */
	public static final String LICENSE_KEY_EXPIRATION_DATE = "_EXPIRATION_DATE";

	private Constants( ) {
		// Avoid instantiation
	}

}
