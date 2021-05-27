/*
 * CasaPasswordEncoder.java
 * 
 * Version 17.07.2015
 * 
 * Copyright CIBEK technology & trading GmbH © 2015. All Rights Reserved.
 * 
 * Author H.Hain
 * Author Adem Vural
 */
package de.cibek.mscasa.common.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This Class is used to hash passwords.
 *
 */
@Slf4j
public final class CasaPasswordEncoder implements PasswordEncoder {

    private static final int ROUNDS = 10000;
    private static final int KEY_LENGTH = 512; //512bits = 64 bytes
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA512";
    private final SecureRandom SECURE_RANDOM_INSTANCE = new SecureRandom();

    public CasaPasswordEncoder() {
    }

    /**
     * creates a new password hash
     *
     * @param rawPassword readable password
     * @return Hashed password
     */
    @Override
    public String encode(CharSequence rawPassword) {
        final byte[] salt = getSalt();
        final byte[] hash = generateHash(rawPassword.toString(), salt, ROUNDS);

        return toHex(salt) + ":" + toHex(hash);
    }

    private byte[] generateHash(String password, byte[] salt, int rounds) {
        try {
            final char[] chars = password.toCharArray();
            final PBEKeySpec spec = new PBEKeySpec(chars, salt, rounds, KEY_LENGTH);
            final SecretKeyFactory skf = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NullPointerException ex) {
            log.error("generating hash failed", ex);
        }
        return new byte[0];
    }

    /**
     * getSalt
     *
     * @return salt String
     * @throws NoSuchAlgorithmException if the algorithm does not exist
     */
    private byte[] getSalt() {
        final SecureRandom sr = new SecureRandom();
        final byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * toHex translates a byte-Array to a Hex-String.
     *
     * @param array the byte-Array
     * @return the Hex-String
     */
    private String toHex(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    /**
     * fromHex translates a hex String into a bytes-Array.
     *
     * @param hex the string
     * @return the byte-Array
     */
    private byte[] fromHex(String hex) {
        return DatatypeConverter.parseHexBinary(hex);
    }

    /**
     * Method to validate the uer password. The password has to fulfill certain
     * conditions.
     *
     * @param rawPassword readable password
     * @param storedHash storedHash
     * @return true if pw equals the saved one
     */
    @Override
    public boolean matches(CharSequence rawPassword, String storedHash) {
        if (storedHash != null && !storedHash.isEmpty()) {
            final String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }
            final byte[] salt = fromHex(parts[0]);
            final byte[] hash = fromHex(parts[1]);

            //checks if the hash from the db is the same as the new hash created now
            final byte[] currentHash = generateHash(rawPassword.toString(), salt, ROUNDS);
            return Arrays.equals(currentHash, hash);
        } else {
            return false;
        }
    }

    public String generateNewToken() {
        byte[] randomBytes = new byte[128];
        SECURE_RANDOM_INSTANCE.nextBytes(randomBytes);
        return toHex(randomBytes);
    }
}
