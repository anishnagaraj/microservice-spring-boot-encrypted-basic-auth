package com.anishnagaraj.poc.microservice.security;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Cryptographic utility methods.
 *
 * @author anishnagaraj
 */
public final class CryptoUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtil.class);

    public static final String HANDLER_PATH_SECRET_INJECTOR = "/system/secrets";
    public static final String BOOTSTRAP_FILE_SYSTEM_PROPERTY = "com.anishnagaraj.microservice.poc.security.DecryptorBootstrapFile";
    public static final String SECRET_INJECTOR_SYSTEM_PROPERTY = "com.anishnagaraj.microservice.poc.security.EnableSecretInjector";

    private CryptoUtil() {
    }

    /**
     * Attempts to configure the given {@link CryptoProvider} from system properties. If the system
     * properties are not set, a log statement is emitted and no exception is thrown.
     *
     * @param cryptoProvider the Decryptor to configure
     */
    public static void configureDecryptorFromSystemProperties(CryptoProvider cryptoProvider) {
        final String bootstrapPath = System.getProperty(BOOTSTRAP_FILE_SYSTEM_PROPERTY, null);
        if (Strings.isNullOrEmpty(bootstrapPath)) {
            LOGGER.info("System property for Decryptor bootstrap file ({}) not set - skipping Decryptor initialisation",
                    BOOTSTRAP_FILE_SYSTEM_PROPERTY);
            return;
        }

        // read the bootstrap configuration and use it to configure the Decryptor
        //final JsonObject decryptorConfig = ConfigUtil.readJsonConfigFile(new File(bootstrapPath));
        //cryptoProvider.initialiseDecryptor(decryptorConfig.getString("algorithm"), decryptorConfig.getString("secret"));
    }

    /**
     * @return <code>true</code> if the Secret Injector Handler should be enabled, otherwise <code>false</code>
     */
    public static boolean isSecretInjectorHandlerEnabled() {
        return Boolean.valueOf(System.getProperty(SECRET_INJECTOR_SYSTEM_PROPERTY, Boolean.FALSE.toString()));
    }
}
