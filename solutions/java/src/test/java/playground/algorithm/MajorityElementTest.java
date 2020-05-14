package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MajorityElementTest {

    private MajorityElement solution = new MajorityElement();

    @Test
    public void case_1() {
        assertEquals(3, solution.majorityElement(new int[]{3, 2, 3}));
    }

    @Test
    public void case_2() {
        assertEquals(2, solution.majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2}));
    }
}