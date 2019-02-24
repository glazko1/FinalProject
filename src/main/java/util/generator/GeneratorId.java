package util.generator;

import java.util.Random;

public class GeneratorId {

    private static final GeneratorId INSTANCE = new GeneratorId();

    public static GeneratorId getInstance() {
        return INSTANCE;
    }

    private GeneratorId() {}

    public long generateId() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }
}
