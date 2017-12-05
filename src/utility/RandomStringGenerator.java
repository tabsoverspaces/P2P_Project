package utility;

import java.util.Random;

public class RandomStringGenerator {

    private static Random randomizer = new Random();
    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public RandomStringGenerator() {

    }

    public String getRandomString() {
        StringBuilder inter = new StringBuilder();

        int length = this.getRandomLength();

        for (int i = 0; i < length; i++) {
            char c = alphabet.charAt(randomizer.nextInt(alphabet.length()));

            inter.append(c);
        }

        inter.append(" (randomly generated)");
        return inter.toString();
    }

    private int getRandomLength() {
        int length;

        length = randomizer.nextInt(10);

        return length;
    }
}
