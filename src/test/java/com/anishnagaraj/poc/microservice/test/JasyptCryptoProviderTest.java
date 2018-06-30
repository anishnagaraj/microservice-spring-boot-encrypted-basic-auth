package com.anishnagaraj.poc.microservice.test;

import org.junit.Before;
import org.junit.Test;

import com.anishnagaraj.poc.microservice.security.CryptoProvider;
import com.anishnagaraj.poc.microservice.security.JasyptCryptoProviderImpl;

import static org.junit.Assert.*;

import org.jasypt.registry.AlgorithmRegistry;

/**
 * Tests for {@link JasyptCryptoProviderImpl}.
 *
 * @author anishnagaraj
 */
public class JasyptCryptoProviderTest {
	
	//clear text pwd
    private static final String CLEAR_TEXT_VALUE = "ExampleClearTextValue";
    private static final String PASSWORD = "Password123!";
    private static final String ENCRYPTED_VALUE = "3sDxlREg9fRgZe7+dfjzEAtOo2frHqqH/m/blqY=";
    private static final String SALT = "ThisIsAPoorSaltValue";
    private static final String HASHED_VALUE = "jRpUWTvMFMeDV+q5q6EdFjcSTwg=";
    private static final int HASH_ITERATIONS = 50000;

    /**
     * This is an insecure algorithm used for testing; DO NOT USE IT in production.
     */
    private static final String DECRYPTOR_ALGORITHM = "PBEWITHSHA1ANDRC4_40";

    /**
     * This is an insecure algorithm used for testing DO NOT USE IT in production.
     */
    private static final String HASH_ALGORITHM = "SHA-1";

    /**
     * The unit under test.
     */
    private JasyptCryptoProviderImpl cryptoProvider;

    @Before
    public void setUp() throws Exception {
        cryptoProvider = new JasyptCryptoProviderImpl();
    }

    /**
     * Expect that with a correct password, the decrypted value matches the expected cleartext value.
     *
     * @throws Exception
     */
    @Test
    public void testDecrypt_Success() throws Exception {
        // set up
        cryptoProvider.initialiseDecryptor(DECRYPTOR_ALGORITHM, PASSWORD);

        // test
        final String actual = cryptoProvider.decrypt(ENCRYPTED_VALUE);

        // assertions
        assertEquals(CLEAR_TEXT_VALUE, actual);
    }

    /**
     * Expect that with an incorrect password, the decrypted value does not match the expected cleartext value.
     *
     * @throws Exception
     */
    @Test
    public void testDecrypt_Fail() throws Exception {
        // set up
        final String incorrectPassword = "incorrect password";
        cryptoProvider.initialiseDecryptor(DECRYPTOR_ALGORITHM, incorrectPassword);

        // test
        final String actual = cryptoProvider.decrypt(ENCRYPTED_VALUE);

        // assertions
        assertNotEquals(CLEAR_TEXT_VALUE, actual);
    }

    /**
     * Expect that an {@link IllegalStateException} is thrown when
     * {@link CryptoProvider#initialiseDecryptor(String, String)} has not been called first.
     *
     * @throws Exception
     */
    @Test(expected = IllegalStateException.class)
    public void testDecrypt_ExceptionNotInitialised() throws Exception {
        // set up
        // not calling cryptoProvider.initialiseDecryptor(...)

        // test
        cryptoProvider.decrypt(ENCRYPTED_VALUE);

        fail(IllegalStateException.class + " expected");
    }

    /**
     * Expect that the correct cleartext value and salt matches the hash.
     *
     * @throws Exception
     */
    @Test
    public void testMatchesHash_Success() throws Exception {
        // set up
        cryptoProvider.initialiseDigester(HASH_ALGORITHM, HASH_ITERATIONS);

        // test
        final boolean actual = cryptoProvider.matchesHash(HASHED_VALUE, CLEAR_TEXT_VALUE, SALT);

        // assertions
        assertTrue(actual);
    }

    /**
     * Expect that the incorrect cleartext value and salt does not match the hash.
     *
     * @throws Exception
     */
    @Test
    public void testMatchesHash_Fail() throws Exception {
        // set up
        cryptoProvider.initialiseDigester(HASH_ALGORITHM, HASH_ITERATIONS);

        // test
        final boolean actual = cryptoProvider.matchesHash(HASHED_VALUE, "incorrect value", SALT);

        // assertions
        assertFalse(actual);
    }

    /**
     * Expect that an {@link IllegalStateException} is thrown when
     * {@link CryptoProvider#initialiseDigester(String, int)} has not been called first.
     *
     * @throws Exception
     */
    @Test(expected = IllegalStateException.class)
    public void testMatchesHash_ExceptionNotInitialised() throws Exception {
        // set up
        // not calling cryptoProvider.initialiseDecryptor(...)

        // test
        cryptoProvider.matchesHash(HASHED_VALUE, CLEAR_TEXT_VALUE, SALT);

        fail(IllegalStateException.class + " expected");
    }
}
