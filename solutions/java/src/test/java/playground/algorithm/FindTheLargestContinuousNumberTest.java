package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindTheLargestContinuousNumberTest {

    private FindTheLargestContinuousNumber solution = new FindTheLargestContinuousNumber();

    @Test
    void case_1() {
        double result = solution.findTheLargestContinuousNumber("364.2345.34567.865");

        assertEquals(34567.865, result);
    }
}