package playground.algorithm.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TreeNodeTest {

    @Test
    void build_tree_node() {
        TreeNode root = TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);

        assertEquals(6, root.val);
        assertEquals(2, root.left.val);
        assertEquals(8, root.right.val);
        assertEquals(0, root.left.left.val);
        assertEquals(4, root.left.right.val);
        assertEquals(7, root.right.left.val);
        assertEquals(9, root.right.right.val);
        assertNull(root.left.left.left);
        assertNull(root.left.left.right);
        assertEquals(3, root.left.right.left.val);
        assertEquals(5, root.left.right.right.val);
    }
}