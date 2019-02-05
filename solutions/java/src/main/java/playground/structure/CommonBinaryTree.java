package playground.structure;

import playground.util.GraphvizUtils;

@SuppressWarnings("unchecked")
abstract class CommonBinaryTree<T extends Comparable<T>> implements Tree<T> {

    boolean contains(T i, BinaryNode<T> root) {
        if (root == null) return false;

        int compareResult = i.compareTo(root.e);

        if (compareResult < 0) return contains(i, root.l);

        else if (compareResult > 0) return contains(i, root.r);

        else return true;
    }

    T findMin(BinaryNode<T> node) {
        if (node == null) return null;
        if (node.l != null) return findMin(node.l);
        return node.e;
    }

    T finMax(BinaryNode<T> node) {
        if (node == null) return null;

        BinaryNode<T> t = node;
        while (t.r != null) {
            t = t.r;
        }

        return t.e;
    }

    int deep(BinaryNode<T> node) {
        if (node == null) return 0;
        int r = deep(node.r);
        int l = deep(node.l);
        return Math.max(r, l);
    }

    String graphTree(BinaryNode<T> node) {
        StringBuffer stringBuffer = new StringBuffer("digraph " + this.getClass().getSimpleName() + " { \n");
        stringBuffer.append("node [shape=\"record\", height=.1] \n");
        buildNode(node, stringBuffer);
        buildGraph(node, stringBuffer);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void show() {
        GraphvizUtils.show(graphTree());
    }

    private void buildNode(BinaryNode<T> node, StringBuffer stringBuffer) {
        if (node == null) return;
        stringBuffer.append("node").append(node.e).append("[label=\"<f0> | <f1> ").append(node.e).append(" | <f2>\"];\n");

        if (node.l != null) {
            buildNode(node.l, stringBuffer);
        }

        if (node.r != null) {
            buildNode(node.r, stringBuffer);
        }
    }

    private void buildGraph(BinaryNode<T> node, StringBuffer stringBuffer) {
        if (node == null) return;

        if (node.l != null) {
            stringBuffer.append("node").append(node.e).append(":f0").append(" -> ").append("node").append(node.l.e).append(":f1;\n");
            buildGraph(node.l, stringBuffer);
        }

        if (node.r != null) {
            stringBuffer.append("node").append(node.e).append(":f2").append(" -> ").append("node").append(node.r.e).append(":f1;\n");
            buildGraph(node.r, stringBuffer);
        }
    }

    class BinaryNode<T> {

        BinaryNode(T e) {
            this(e, null, null);
        }

        BinaryNode(T e, BinaryNode r, BinaryNode l) {
            this.e = e;
            this.r = r;
            this.l = l;
        }

        T e;

        BinaryNode<T> r;

        BinaryNode<T> l;

    }


}
