package com.message.service.auth.token;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class TokenEncryptor {

    /**
     * The Constant KEY.
     */
    private static final String KEY = "";

    /**
     * Encrypt.
     *
     * @param text the text
     * @return the string
     */
    public String encrypt(final String text) {
        return Base64.encodeBase64String(this.xor(text.getBytes()));
    }

    /**
     * Decrypt.
     *
     * @param hash the hash
     * @return the string
     */
    public String decrypt(final String hash) {
        try {
            return new String(this.xor(Base64.decodeBase64(hash.getBytes())), StandardCharsets.UTF_8.name());
        } catch (java.io.UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Xor.
     *
     * @param input the input
     * @return the byte[]
     */
    private byte[] xor(final byte[] input) {
        final byte[] output = new byte[input.length];
        final byte[] secret = KEY.getBytes();
        int spos = 0;
        for (int pos = 0; pos < input.length; ++pos) {
            output[pos] = (byte) (input[pos] ^ secret[spos]);
            spos += 1;
            if (spos >= secret.length) {
                spos = 0;
            }
        }
        return output;
    }

}
