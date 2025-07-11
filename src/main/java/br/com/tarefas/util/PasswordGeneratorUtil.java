package br.com.tarefas.util;

import java.security.SecureRandom;
import java.util.Locale;

public class PasswordGeneratorUtil {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = UPPER.toLowerCase(Locale.ROOT);
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%&*()-_=+[]{}";

    private static final String ALL = UPPER + LOWER + DIGITS + SYMBOLS;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }

        StringBuilder password = new StringBuilder(length);

        // Garantir pelo menos 1 de cada tipo
        password.append(randomChar(UPPER));
        password.append(randomChar(LOWER));
        password.append(randomChar(DIGITS));
        password.append(randomChar(SYMBOLS));

        for (int i = 4; i < length; i++) {
            password.append(randomChar(ALL));
        }

        return shuffleString(password.toString());
    }

    private static char randomChar(String input) {
        return input.charAt(RANDOM.nextInt(input.length()));
    }

    private static String shuffleString(String input) {
        char[] a = input.toCharArray();

        for (int i = a.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }

        return new String(a);
    }
}

