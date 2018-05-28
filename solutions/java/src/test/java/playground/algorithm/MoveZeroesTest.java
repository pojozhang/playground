package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MoveZeroesTest {

    MoveZeroes moveZeroes = new MoveZeroes();

    @Test
    public void case00() {
        int[] nums = {0, 2, 3, 1, 0};
        moveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{2, 3, 1, 0, 0}, nums);
    }

    @Test
    public void case01() {
        int[] nums = {0, 0, 0, 0, 0};
        moveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{0, 0, 0, 0, 0}, nums);
    }

    @Test
    public void case02() {
        int[] nums = {1, 2, 3, 4, 5};
        moveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, nums);
    }

    @Test
    public void case03() {
        int[] nums = {1, 2, 3, 0, 0};
        moveZeroes.moveZeroes(nums);
        assertArrayEquals(new int[]{1, 2, 3, 0, 0}, nums);
    }


}
