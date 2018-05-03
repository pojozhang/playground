package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchInsertPositionTest {

    private SearchInsertPosition solution = new SearchInsertPosition();

    @Test
    void case_1() {
        assertEquals(2, solution.searchInsert(new int[]{1, 3, 5, 6}, 5));
    }

    @Test
    void case_2() {
        assertEquals(1, solution.searchInsert(new int[]{1, 3, 5, 6}, 2));
    }

    @Test
    void case_3() {
        assertEquals(4, solution.searchInsert(new int[]{1, 3, 5, 6}, 7));
    }

    @Test
    void case_4() {
        assertEquals(0, solution.searchInsert(new int[]{1, 3, 5, 6}, 0));
    }
}