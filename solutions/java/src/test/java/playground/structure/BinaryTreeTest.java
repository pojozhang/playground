package playground.structure;

import org.junit.jupiter.api.Test;

class BinaryTreeTest {

    BinaryTree binaryTree = new BinaryTree();

    @Test
    void case00() {
        Integer[] array = {23, 6, 9, 3, 5, 7, 1, 25};
        System.out.println(binaryTree.build(array).printTree());
    }


}
