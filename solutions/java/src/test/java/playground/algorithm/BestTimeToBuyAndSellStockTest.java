package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestTimeToBuyAndSellStockTest {

    private BestTimeToBuyAndSellStock solution = new BestTimeToBuyAndSellStock();

    @Test
    void case_1() {
        assertEquals(5, solution.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }

    @Test
    void case_2() {
        assertEquals(0, solution.maxProfit(new int[]{7, 6, 4, 3, 1}));
    }
}