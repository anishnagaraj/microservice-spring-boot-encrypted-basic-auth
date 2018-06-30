package com.anishnagaraj.poc.microservice.security;

/**
 * Implementation-agnostic cryptographic utility methods.
 *
 * @author anishnagaraj
 */
public interface CryptoProvider {
    /**
     * Configure the encryption engine.
     *
     * @param algorithm      the encryption algorithm to use
     * @param configPassword the password to use for decryption
     */
    void initialiseDecryptor(String algorithm, String configPassword);

    /**
     * Configure the hashing engine.
     *
     * @param algorithm  the hashing algorithm to use
     * @param iterations the number of hashing iterations
     */
    void initialiseDigester(String algorithm, int iterations);

    /**
     * Decrypt the {@code encrypted} string.
     *
     * @param encrypted an encrypted string
     * @return the decrypted string
     */
    String decrypt(String encrypted);

    /**
     * Hash the {@code inputValue} and {@code salt} and determine if it matches the provided hash.
     *
     * @param hashedValue the hashed password
     * @param inputValue  the string to validate
     * @param salt        the salt for the hash
     * @return {@code true} if the hashed input matches the hash, otherwise {@code false}
     */
    boolean matchesHash(String hashedValue, String inputValue, String salt);
    

    String encrypt(String encrypted);
}
