package playground.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BinaryTreeTest {

    private Tree<Integer> binaryTree = new BinaryTree();

    @Test
    void case00() {
        Integer[] array = {10, 3, 2, 5, 4, 11, 16, 14, 15, 13};

        String graph = binaryTree.build(array).graphTree();

        Assertions.assertEquals("digraph BinaryTree { \n" +
                "node [shape=\"record\", height=.1] \n" +
                "node10[label=\"<f0> | <f1> 10 | <f2>\"];\n" +
                "node3[label=\"<f0> | <f1> 3 | <f2>\"];\n" +
                "node2[label=\"<f0> | <f1> 2 | <f2>\"];\n" +
                "node5[label=\"<f0> | <f1> 5 | <f2>\"];\n" +
                "node4[label=\"<f0> | <f1> 4 | <f2>\"];\n" +
                "node11[label=\"<f0> | <f1> 11 | <f2>\"];\n" +
                "node16[label=\"<f0> | <f1> 16 | <f2>\"];\n" +
                "node14[label=\"<f0> | <f1> 14 | <f2>\"];\n" +
                "node13[label=\"<f0> | <f1> 13 | <f2>\"];\n" +
                "node15[label=\"<f0> | <f1> 15 | <f2>\"];\n" +
                "node10:f0 -> node3:f1;\n" +
                "node3:f0 -> node2:f1;\n" +
                "node3:f2 -> node5:f1;\n" +
                "node5:f0 -> node4:f1;\n" +
                "node10:f2 -> node11:f1;\n" +
                "node11:f2 -> node16:f1;\n" +
                "node16:f0 -> node14:f1;\n" +
                "node14:f0 -> node13:f1;\n" +
                "node14:f2 -> node15:f1;\n" +
                "}", graph);
    }

    @Test
    void case01() {
        Integer[] array = {10, 3, 2, 5, 4, 11, 16, 14, 13};

        binaryTree.build(array);
        binaryTree.insert(15);
        String graph = binaryTree.graphTree();
        Assertions.assertEquals("digraph BinaryTree { \n" +
                "node [shape=\"record\", height=.1] \n" +
                "node10[label=\"<f0> | <f1> 10 | <f2>\"];\n" +
                "node3[label=\"<f0> | <f1> 3 | <f2>\"];\n" +
                "node2[label=\"<f0> | <f1> 2 | <f2>\"];\n" +
                "node5[label=\"<f0> | <f1> 5 | <f2>\"];\n" +
                "node4[label=\"<f0> | <f1> 4 | <f2>\"];\n" +
                "node11[label=\"<f0> | <f1> 11 | <f2>\"];\n" +
                "node16[label=\"<f0> | <f1> 16 | <f2>\"];\n" +
                "node14[label=\"<f0> | <f1> 14 | <f2>\"];\n" +
                "node13[label=\"<f0> | <f1> 13 | <f2>\"];\n" +
                "node15[label=\"<f0> | <f1> 15 | <f2>\"];\n" +
                "node10:f0 -> node3:f1;\n" +
                "node3:f0 -> node2:f1;\n" +
                "node3:f2 -> node5:f1;\n" +
                "node5:f0 -> node4:f1;\n" +
                "node10:f2 -> node11:f1;\n" +
                "node11:f2 -> node16:f1;\n" +
                "node16:f0 -> node14:f1;\n" +
                "node14:f0 -> node13:f1;\n" +
                "node14:f2 -> node15:f1;\n" +
                "}", graph);
    }

    /**
     * 删除有两个子节点的节点
     */
    @Test
    void case02() {
        Integer[] array = {10, 3, 2, 5, 4, 11, 16, 14, 15, 13};

        binaryTree.build(array);
        binaryTree.remove(3);

        String graph = binaryTree.graphTree();
        Assertions.assertEquals("digraph BinaryTree { \n" +
                "node [shape=\"record\", height=.1] \n" +
                "node10[label=\"<f0> | <f1> 10 | <f2>\"];\n" +
                "node4[label=\"<f0> | <f1> 4 | <f2>\"];\n" +
                "node2[label=\"<f0> | <f1> 2 | <f2>\"];\n" +
                "node5[label=\"<f0> | <f1> 5 | <f2>\"];\n" +
                "node11[label=\"<f0> | <f1> 11 | <f2>\"];\n" +
                "node16[label=\"<f0> | <f1> 16 | <f2>\"];\n" +
                "node14[label=\"<f0> | <f1> 14 | <f2>\"];\n" +
                "node13[label=\"<f0> | <f1> 13 | <f2>\"];\n" +
                "node15[label=\"<f0> | <f1> 15 | <f2>\"];\n" +
                "node10:f0 -> node4:f1;\n" +
                "node4:f0 -> node2:f1;\n" +
                "node4:f2 -> node5:f1;\n" +
                "node10:f2 -> node11:f1;\n" +
                "node11:f2 -> node16:f1;\n" +
                "node16:f0 -> node14:f1;\n" +
                "node14:f0 -> node13:f1;\n" +
                "node14:f2 -> node15:f1;\n" +
                "}", graph);
    }

    /**
     * 删除有一个子节点的节点
     */
    @Test
    void case03() {
        Integer[] array = {10, 3, 2, 5, 4, 11, 16, 14, 15, 13};

        binaryTree.build(array);
        binaryTree.remove(5);

        String graph = binaryTree.graphTree();
        Assertions.assertEquals("digraph BinaryTree { \n" +
                "node [shape=\"record\", height=.1] \n" +
                "node10[label=\"<f0> | <f1> 10 | <f2>\"];\n" +
                "node3[label=\"<f0> | <f1> 3 | <f2>\"];\n" +
                "node2[label=\"<f0> | <f1> 2 | <f2>\"];\n" +
                "node4[label=\"<f0> | <f1> 4 | <f2>\"];\n" +
                "node11[label=\"<f0> | <f1> 11 | <f2>\"];\n" +
                "node16[label=\"<f0> | <f1> 16 | <f2>\"];\n" +
                "node14[label=\"<f0> | <f1> 14 | <f2>\"];\n" +
                "node13[label=\"<f0> | <f1> 13 | <f2>\"];\n" +
                "node15[label=\"<f0> | <f1> 15 | <f2>\"];\n" +
                "node10:f0 -> node3:f1;\n" +
                "node3:f0 -> node2:f1;\n" +
                "node3:f2 -> node4:f1;\n" +
                "node10:f2 -> node11:f1;\n" +
                "node11:f2 -> node16:f1;\n" +
                "node16:f0 -> node14:f1;\n" +
                "node14:f0 -> node13:f1;\n" +
                "node14:f2 -> node15:f1;\n" +
                "}", graph);
    }

    /**
     * 删除有一个子节点的节点
     */
    @Test
    void case04() {
        Integer[] array = {10, 3, 2, 5, 4, 11, 16, 14, 15, 13};

        binaryTree.build(array);
        binaryTree.remove(4);
        String graph = binaryTree.graphTree();
        Assertions.assertEquals("digraph BinaryTree { \n" +
                "node [shape=\"record\", height=.1] \n" +
                "node10[label=\"<f0> | <f1> 10 | <f2>\"];\n" +
                "node3[label=\"<f0> | <f1> 3 | <f2>\"];\n" +
                "node2[label=\"<f0> | <f1> 2 | <f2>\"];\n" +
                "node5[label=\"<f0> | <f1> 5 | <f2>\"];\n" +
                "node11[label=\"<f0> | <f1> 11 | <f2>\"];\n" +
                "node16[label=\"<f0> | <f1> 16 | <f2>\"];\n" +
                "node14[label=\"<f0> | <f1> 14 | <f2>\"];\n" +
                "node13[label=\"<f0> | <f1> 13 | <f2>\"];\n" +
                "node15[label=\"<f0> | <f1> 15 | <f2>\"];\n" +
                "node10:f0 -> node3:f1;\n" +
                "node3:f0 -> node2:f1;\n" +
                "node3:f2 -> node5:f1;\n" +
                "node10:f2 -> node11:f1;\n" +
                "node11:f2 -> node16:f1;\n" +
                "node16:f0 -> node14:f1;\n" +
                "node14:f0 -> node13:f1;\n" +
                "node14:f2 -> node15:f1;\n" +
                "}", graph);
    }

}
