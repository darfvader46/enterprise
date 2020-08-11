package demo.gen;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class Generator {
    
    public static final int SALT_LENGTH = 10;
    
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[SALT_LENGTH];
        random.nextBytes(bytes);
        return bytes;
    }
    
    public static byte[] hashWithSalt(String password, byte[] salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] hash = null;
        byte[] bytesOfMessage = password.getBytes("UTF-8");
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-512");
        md.reset();
        md.update(salt);
        md.update(bytesOfMessage);
        hash = md.digest();
        return hash;
    }
}
