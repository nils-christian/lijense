package de.rhocas.nce.lijense.key;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Test;

/**
 * Unit test for {@link KeyUtil}.
 *
 * @author Nils Christian Ehmke
 */
public final class KeyUtilTest {

	@Test
	public void testKeyGeneration() throws KeyException {
		final KeyPair keyPair = KeyUtil.generateNewKeyPair();

		// Check the private key
		final PrivateKey privateKey = keyPair.getPrivate();
		assertThat(privateKey.getAlgorithm(), is("RSA"));
		assertThat(privateKey.getFormat(), is("PKCS#8"));
		assertThat(privateKey.getEncoded(), is(notNullValue()));

		// Check the public key
		final PublicKey publicKey = keyPair.getPublic();
		assertThat(publicKey.getAlgorithm(), is("RSA"));
		assertThat(publicKey.getFormat(), is("X.509"));
		assertThat(publicKey.getEncoded(), is(notNullValue()));
	}

	@Test
	public void testLoadPrivateKeyFromStream() throws KeyException {
		final InputStream inputStream = KeyUtilTest.class.getClassLoader().getResourceAsStream("key.private");
		final PrivateKey privateKey = KeyUtil.loadPrivateKeyFromStream(inputStream);

		// Check the private key
		assertThat(privateKey.getAlgorithm(), is("RSA"));
		assertThat(privateKey.getFormat(), is("PKCS#8"));
		assertThat(privateKey.getEncoded(), is(notNullValue()));
	}

	@Test
	public void testLoadPublicKeyFromStream() throws KeyException {
		final InputStream inputStream = KeyUtilTest.class.getClassLoader().getResourceAsStream("key.public");
		final PublicKey publicKey = KeyUtil.loadPublicKeyFromStream(inputStream);

		// Check the public key
		assertThat(publicKey.getAlgorithm(), is("RSA"));
		assertThat(publicKey.getFormat(), is("X.509"));
		assertThat(publicKey.getEncoded(), is(notNullValue()));
	}

}
