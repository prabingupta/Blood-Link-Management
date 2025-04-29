package com.blood.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for encrypting and decrypting passwords in the Blood Bank Management System.
 * Uses AES-GCM for secure password encryption with a user ID as the key derivation input.
 */
public class PasswordUtil {

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128; // Must be one of {128, 120, 112, 104, 96}
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * Generates a random nonce (IV or salt) of the specified length.
     *
     * @param numBytes the number of bytes for the nonce
     * @return a byte array containing the random nonce
     */
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * Generates an AES secret key of the specified size.
     *
     * @param keysize the size of the key in bits (e.g., 256)
     * @return the generated SecretKey
     * @throws NoSuchAlgorithmException if the AES algorithm is not available
     */
    public static SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    /**
     * Derives an AES 256-bit secret key from a user ID and salt using PBKDF2.
     *
     * @param userId the user ID used as the password for key derivation
     * @param salt   the salt used in key derivation
     * @return the derived SecretKey, or null if an error occurs
     */
    public static SecretKey getAESKeyFromUserId(char[] userId, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(userId, salt, 65536, 256); // 65536 iterations, 256-bit key
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            return secret;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Encrypts a password using AES-GCM with the user ID as the key derivation input.
     *
     * @param userId   the user ID used to derive the encryption key
     * @param password the password to encrypt
     * @return a Base64-encoded string containing the IV, salt, and encrypted password, or null if an error occurs
     */
    public static String encrypt(String userId, String password) {
        try {
            // Generate salt and IV
            byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
            byte[] iv = getRandomNonce(IV_LENGTH_BYTE);

            // Derive secret key from user ID
            SecretKey aesKeyFromUserId = getAESKeyFromUserId(userId.toCharArray(), salt);
            if (aesKeyFromUserId == null) {
                return null;
            }

            // Initialize cipher
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromUserId, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            // Encrypt password
            byte[] cipherText = cipher.doFinal(password.getBytes(UTF_8));

            // Combine IV, salt, and cipher text
            byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                    .put(iv)
                    .put(salt)
                    .put(cipherText)
                    .array();

            // Encode to Base64
            return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
        } catch (Exception ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Decrypts a Base64-encoded encrypted password using AES-GCM with the user ID as the key derivation input.
     *
     * @param encryptedPassword the Base64-encoded string containing the IV, salt, and encrypted password
     * @param userId            the user ID used to derive the decryption key
     * @return the decrypted password, or null if an error occurs
     */
    public static String decrypt(String encryptedPassword, String userId) {
        try {
            // Decode Base64
            byte[] decode = Base64.getDecoder().decode(encryptedPassword.getBytes(UTF_8));

            // Extract IV, salt, and cipher text
            ByteBuffer bb = ByteBuffer.wrap(decode);
            byte[] iv = new byte[IV_LENGTH_BYTE];
            bb.get(iv);
            byte[] salt = new byte[SALT_LENGTH_BYTE];
            bb.get(salt);
            byte[] cipherText = new byte[bb.remaining()];
            bb.get(cipherText);

            // Derive secret key from user ID
            SecretKey aesKeyFromUserId = getAESKeyFromUserId(userId.toCharArray(), salt);
            if (aesKeyFromUserId == null) {
                return null;
            }

            // Initialize cipher
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, aesKeyFromUserId, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            // Decrypt
            byte[] plainText = cipher.doFinal(cipherText);

            return new String(plainText, UTF_8);
        } catch (Exception ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}