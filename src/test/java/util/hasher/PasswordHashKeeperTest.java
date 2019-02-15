package util.hasher;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PasswordHashKeeperTest {

    private PasswordHashKeeper keeper = PasswordHashKeeper.getInstance();

    @Test
    public void test() {
        String result = keeper.generateHash("ghjkl9876");
//        byte[] result = keeper.generateHash("helloworld");
        System.out.println(result);
//        for (byte b : result) {
//            System.out.println(b);
//        }
    }
}