package com.anishnagaraj.poc.microservice.security;

import com.google.common.base.Strings;

import javax.annotation.PostConstruct;

import org.jasypt.digest.StandardStringDigester;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * A {@link CryptoProvider} implementation that uses Jasypt.
 *
 * @author anishnagaraj
 */
@Service("jasyptCryptoProviderImpl")
public class JasyptCryptoProviderImpl implements CryptoProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JasyptCryptoProviderImpl.class);
    private static final String DIGEST_SALT_CHARSET = "UTF-8";

    /**
     * Used for its decryption capabilities only.
     */
    private StandardPBEStringEncryptor decryptor;

    private String digesterAlgorithm;
    private int digesterIterations;

    @Override
    public void initialiseDecryptor(String algorithm, String password) {
        // do not log secrets, obviously
        LOGGER.info("Initialising Decryptor");

        decryptor = new StandardPBEStringEncryptor();
        decryptor.setPasswordCharArray(password.toCharArray());
        decryptor.setAlgorithm(algorithm);

    }

    @Override
    public void initialiseDigester(String algorithm, int iterations) {
        LOGGER.info("Initialising Digester");

        digesterAlgorithm = algorithm;
        digesterIterations = iterations;
    }

    @Override
    public String decrypt(String encrypted) {
        if (null == decryptor) {
            throw new IllegalStateException("Decryptor is not initialised. Call initialiseDecryptor() first.");
        }

        return decryptor.decrypt(encrypted);
    }
    
    @Override   
    public String encrypt(String plaintext) {
        return decryptor.encrypt(plaintext);
    }
    
    public String digest(String password, String salt){
        final StandardStringDigester digester = new StandardStringDigester();
        digester.setAlgorithm(digesterAlgorithm);
        digester.setIterations(digesterIterations);
        digester.setSaltGenerator(new StringFixedSaltGenerator(salt, DIGEST_SALT_CHARSET));
        return digester.digest(password);
    }

    @Override
    public boolean matchesHash(String hashedValue, String inputValue, String salt) {
        if (Strings.isNullOrEmpty(digesterAlgorithm) || 0 == digesterIterations) {
            throw new IllegalStateException("Digester is not initialised. Call initialiseDigester() first.");
        }

        final StandardStringDigester digester = new StandardStringDigester();
        digester.setAlgorithm(digesterAlgorithm);
        digester.setIterations(digesterIterations);
        digester.setSaltGenerator(new StringFixedSaltGenerator(salt, DIGEST_SALT_CHARSET));
        
        return digester.matches(inputValue, hashedValue);
    }

}
