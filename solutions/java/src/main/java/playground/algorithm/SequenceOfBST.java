package playground.algorithm;

public class SequenceOfBST {

    public boolean verifyPostorder(int[] postorder) {
        return verifyPostorder(postorder, 0, postorder.length - 1);
    }

    private boolean verifyPostorder(int[] postorder, int start, int end) {
        if (start >= end) {
            return true;
        }

        int root = postorder[end];
        int i = start;
        for (; i < end && postorder[i] < root; i++) ;
        int middle = i;
        for (; i < end && postorder[i] > root; i++) ;
        if (i < end) {
            return false;
        }
        return verifyPostorder(postorder, start, middle - 1) &&
                verifyPostorder(postorder, middle, end - 1);
    }
}
