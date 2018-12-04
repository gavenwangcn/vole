package com.github.vole.passport.common.tokenstore;

import com.github.vole.passport.common.auth.PassportAuthentication;
import com.github.vole.passport.common.token.PassportToken;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultTokenKeyGenerator  implements TokenKeyGenerator{

    private static final String USER_ID = "user_id";

    private static final String USER_NAME = "user_name";

    @Override
    public String extractKey(PassportToken token) {
        Map<String, String> values = new LinkedHashMap<String, String>();
        PassportAuthentication authentication = (PassportAuthentication)token.getValue();
        values.put(USER_ID, authentication.getUserId().toString());
        values.put(USER_NAME, authentication.getName());
        return generateKey(values);
    }

    protected String generateKey(Map<String, String> values) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException nsae) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", nsae);
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", uee);
        }
    }
}
