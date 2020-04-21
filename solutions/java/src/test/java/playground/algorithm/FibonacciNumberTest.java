package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciNumberTest {

    private FibonacciNumber solution = new FibonacciNumber();

    @Test
    void case_1() {
        assertEquals(1, solution.fib(2));
    }

    @Test
    void case_2() {
        assertEquals(2, solution.fib(3));
    }

    @Test
    void case_3() {
        assertEquals(1, solution.fib(1));
    }
}