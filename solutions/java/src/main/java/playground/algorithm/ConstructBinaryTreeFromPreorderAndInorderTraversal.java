package playground.algorithm;

import playground.algorithm.common.TreeNode;

public class ConstructBinaryTreeFromPreorderAndInorderTraversal {

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTree(int[] preorder, int startPreorder, int endPreorder,
                               int[] inorder, int startInorder, int endInorder) {
        if (startInorder > endInorder) {
            return null;
        }
        // 前序中的第0个元素就是子树的根。
        TreeNode node = new TreeNode(preorder[startPreorder]);
        // 从中序中找到根所在的位置，从而计算出左子树和右子树的节点数量。
        int rootIndex = startInorder;
        while (rootIndex < endInorder && inorder[rootIndex] != preorder[startPreorder]) {
            rootIndex++;
        }
        // 递归以上过程。
        node.left = buildTree(preorder, startPreorder + 1, startPreorder + rootIndex - startInorder, inorder, startInorder, rootIndex - 1);
        node.right = buildTree(preorder, startPreorder + rootIndex - startInorder + 1, endPreorder, inorder, rootIndex + 1, endInorder);
        return node;
    }
}
