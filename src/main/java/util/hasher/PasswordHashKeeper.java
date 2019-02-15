package util.hasher;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class PasswordHashKeeper {

    private static final PasswordHashKeeper INSTANCE = new PasswordHashKeeper();

    public static PasswordHashKeeper getInstance() {
        return INSTANCE;
    }

    private PasswordHashKeeper() {}

    public String generateHash(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
//        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] encoded = factory.generateSecret(spec).getEncoded();
//            String result = new String(encoded, UTF_8);
//            return result;
            return Arrays.toString(encoded);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
