public class QuadTree {
    private Node root;

    private static class Node {
        private final boolean isValue;

        public boolean isValue() {
            return isValue;
        }

        public Node(boolean isValue) {
            this.isValue = isValue;
        }
    }

    private static class Value extends Node {
        private final int value;

        public int getValue() {
            return value;
        }

        public Value(int value) {
            super(true);
            this.value =  value;
        }
    }

    private static class Internal extends Node {
        private final Node lu; private final Node ru; private final Node ll; private final Node rl;

        public Node getLu() {
            return lu;
        }

        public Node getRu() {
            return ru;
        }

        public Node getLl() {
            return ll;
        }

        public Node getRl() {
            return rl;
        }

        public Internal(Node lu, Node ru, Node ll, Node rl) {
            super(false);
            this.lu = lu;
            this.ru = ru;
            this.ll = ll;
            this.rl = rl;
        }
    }

    private Node rotate(Node n) {
        if (n.isValue())
            return n;
        Internal in = (Internal) n;
        return new Internal(rotate(in.getRl()),rotate(in.getLl()),rotate(in.getLu()),rotate(in.getRu()));
    }
}
