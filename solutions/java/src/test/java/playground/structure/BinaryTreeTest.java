package playground.structure;

import org.junit.jupiter.api.Test;

class BinaryTreeTest {

    private TreeInterface<Integer> binaryTree = new BinaryTree();

    @Test
    void case00() {
        Integer[] array = {200, 16, 3, 4, 5, 7, 6, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25,
                216, 223, 224, 225, 227, 226, 228, 229, 210, 211, 212, 213, 214, 215, 217, 218, 219, 220, 221, 222, 323, 324, 325};

        System.out.println(binaryTree.build(array).graphTree());
    }

}
