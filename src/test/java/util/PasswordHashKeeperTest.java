package util;

import org.testng.annotations.Test;
import util.hasher.PasswordHashKeeper;

public class PasswordHashKeeperTest {

    private PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();

    @Test
    public void test() {
        String result = keeper.generateHash("alexandra234", "ghjkl9876");
//        byte[] result = keeper.generateHash("helloworld");
        System.out.println(result);
//        for (byte b : result) {
//            System.out.println(b);
//        }
    }
}