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

package de.rhocas.lijense.license;

import static de.rhocas.lijense.Constants.DATE_FORMAT;
import static de.rhocas.lijense.Constants.LICENSE_KEY_EXPIRATION_DATE;

import java.util.Date;
import java.util.Properties;

/**
 * This is a license container which can still be modified. It is usually used to create a new license file.
 *
 * @author Nils Christian Ehmke
 *
 * @since 1.0.0
 */
public final class ModifiableLicense extends Properties {

	private static final long serialVersionUID = -8676543638086345999L;

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 *
	 * @since 1.0.0
	 */
	public void setValue( final String aKey, final String aValue ) {
		setProperty( aKey, aValue );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 *
	 * @since 1.0.0
	 */
	public void setValue( final String aKey, final byte aValue ) {
		setValue( aKey, Byte.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 *
	 * @since 1.0.0
	 */
	public void setValue( final String aKey, final short aValue ) {
		setValue( aKey, Short.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 *
	 * @since 1.0.0
	 */
	public void setValue( final String aKey, final int aValue ) {
		setValue( aKey, Integer.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 *
	 * @since 1.0.0
	 */
	public void setValue( final String aKey, final long aValue ) {
		setValue( aKey, Long.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 *
	 * @since 1.0.0
	 */
	public void setValue( final String aKey, final float aValue ) {
		setValue( aKey, Float.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 *
	 * @since 1.0.0
	 */
	public void setValue( final String aKey, final double aValue ) {
		setValue( aKey, Double.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 *
	 * @since 1.0.0
	 */
	public void setValue( final String aKey, final char aValue ) {
		setValue( aKey, Character.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 *
	 * @since 1.0.0
	 */
	public void setValue( final String aKey, final boolean aValue ) {
		setValue( aKey, Boolean.toString( aValue ) );
	}

	/**
	 * Sets the value for the given key.
	 *
	 * @param aKey
	 *            The key.
	 * @param aValue
	 *            The value.
	 *
	 * @since 1.0.0
	 */
	public void setValue( final String aKey, final Date aValue ) {
		final String value;
		if ( aValue != null ) {
			value = DATE_FORMAT.format( aValue );
		} else {
			value = null;
		}

		setValue( aKey, value );
	}

	/**
	 * Sets the expiration date of the license.
	 *
	 * @param aDate
	 *            The expiration date.
	 *
	 * @since 1.0.0
	 */
	public void setExpirationDate( final Date aDate ) {
		setValue( LICENSE_KEY_EXPIRATION_DATE, aDate );
	}

}
