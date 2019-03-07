package util.generator;

import java.util.Random;

public class IdGenerator {

    private static final IdGenerator INSTANCE = new IdGenerator();

    public static IdGenerator getInstance() {
        return INSTANCE;
    }

    private IdGenerator() {}

    public long generateId() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }
}
