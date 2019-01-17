package util;

public class TestUtils {

    public static void tryCatch(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable ignored) {

        }
    }
}
