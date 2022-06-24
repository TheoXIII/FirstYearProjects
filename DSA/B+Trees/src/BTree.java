import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BTree {
    private static class RecordTooLargeException extends Throwable {}
    private static class ChildNumberMismatch extends Throwable {}

    private final int INNER_MAX;
    private final int LEAF_MAX;

    private Inner root;

    private abstract class Node {
        protected final List<Integer> values;
        protected List<Node> children;
        protected int size;
        protected final int MAX;
        protected final boolean first;

        public List<Integer> getValues() {
            return values;
        }

        public abstract TempNode addElem(int elem) throws ChildNumberMismatch, RecordTooLargeException;
        public abstract TempNode delElem(int i);
        public abstract void setFrontPtr(Leaf frontPtr);
        public abstract void setBackPtr(Leaf backPtr);
        public abstract void printAll();
        public abstract boolean isPresent(int elem);
        public abstract List<Integer> range(int lower, int upper);

        public Node(int MAX) {
            this.MAX = MAX;
            this.values = new LinkedList<>();
            first = false;
        }

        public Node(int MAX, List<Integer> values, boolean first) throws RecordTooLargeException {
            this.values = new LinkedList<>(values);
            this.MAX = MAX;
            size = values.size();
            if(values.size() > MAX) {
                throw new RecordTooLargeException();
            }
            this.first = first;
        }
    }

    private class TempNode {
        private final List<Integer> values;
        private final List<Node> children;

        public List<Integer> getValues() {
            return values;
        }

        public List<Node> getChildren() {
            return children;
        }

        public TempNode(List<Integer> values) {
            this.values = values;
            this.children = null;
        }

        public TempNode(List<Integer> values, List<Node> children) {
            this.values = values;
            this.children = children;
        }
    }

    private class Leaf extends Node {
        private Leaf backPtr;
        private Leaf frontPtr;

        public Leaf getBackPtr() {
            return backPtr;
        }

        public void setBackPtr(Leaf backPtr) {
            this.backPtr = backPtr;
        }

        @Override
        public void printAll() {
            for(int v: values)
                System.out.printf("%d ",v);
            System.out.printf("%n");
            if (getFrontPtr() != null)
                getFrontPtr().printAll();
        }

        public Leaf getFrontPtr() {
            return frontPtr;
        }

        public void setFrontPtr(Leaf frontPtr) {
            this.frontPtr = frontPtr;
        }

        public TempNode addElem(int elem) {
            TempNode temp;
            boolean split = false;
            if (size == MAX) {
                temp = new TempNode(new LinkedList<>(values));
                split = true;
            } else
                temp = new TempNode(values);
            boolean notAdded = true;
            for (int i=0; i<size; i++) {
                if(elem < temp.getValues().get(i)) {
                    temp.getValues().add(i,elem);
                    notAdded = false;
                    break;
                }
            }
            if(notAdded)
                temp.getValues().add(elem);
            if(split)
                return temp;
            else {
                size++;
                return null;
            }
        }

        public TempNode delElem(int i) {
            TempNode temp;
            boolean join = false;
            if (size == MAX/2 && !first) {
                temp = new TempNode(new LinkedList<>(values));
                join = true;
            } else
                temp = new TempNode(values);
            temp.getValues().remove(Integer.valueOf(i));

            if (join)
                return temp;
            else {
                size--;
                return null;
            }
        }

        public boolean isPresent(int elem) {
            for (int e: values)
                if(e == elem)
                    return true;
            return false;
        }

        @Override
        public List<Integer> range(int lower, int upper) {
            List<Integer> returnList = new LinkedList<>();
            for (int e: values) {
                if (e > upper)
                    return returnList;
                if (e >= lower)
                    returnList.add(e);
            }
            if (frontPtr != null)
                returnList.addAll(frontPtr.range(lower,upper));
            return returnList;
        }

        public Leaf(int MAX, List<Integer> values) throws RecordTooLargeException {
            super(MAX, values, false);
        }

        public Leaf(int MAX, List<Integer> values,boolean first) throws RecordTooLargeException {
            super(MAX, values,first);
        }
    }

    private class Inner extends Node {
        public Inner(int MAX) {
            super(MAX);
            children = new LinkedList<>();
        }

        public TempNode addElem(int elem) throws ChildNumberMismatch, RecordTooLargeException {
            TempNode success = null;
            int i;
            boolean notAdded = true;
            for (i=0; i<size; i++) {
                if (elem < values.get(i)) {
                    success = children.get(i).addElem(elem);
                    notAdded = false;
                    break;
                }
            }
            if (notAdded)
                success = children.get(size).addElem(elem);
            if(success == null)
                return null;
            TempNode temp;
            boolean split = false;
            if(size == MAX) {
                temp = new TempNode(new LinkedList<>(values),new LinkedList<>(children));
                split = true;
            } else
                temp = new TempNode(values,children);
            temp.getValues().add(i,success.getValues().get(halfSize(success.getValues().size())));
            if (success.getChildren() != null) { //Child is an inner node
                success.getValues().remove(halfSize(success.getValues().size()));
                temp.getChildren().set(i, new Inner(INNER_MAX, success.getValues().subList(0, halfSize(success.getValues().size())), success.getChildren().subList(0,halfSize(success.getChildren().size()))));
                temp.getChildren().add(i+1, new Inner(INNER_MAX, success.getValues().subList(halfSize(success.getValues().size()), success.getValues().size()), success.getChildren().subList(halfSize(success.getChildren().size()),success.getChildren().size())));
            } else { //Child is a leaf node
                List<Node> children = temp.getChildren();
                children.set(i, new Leaf(LEAF_MAX, success.getValues().subList(0, halfSize(success.getValues().size()))));
                children.add(i+1, new Leaf(LEAF_MAX, success.getValues().subList(halfSize(success.getValues().size()), success.getValues().size())));
                children.get(i).setFrontPtr((Leaf) children.get(i+1));
                children.get(i+1).setBackPtr((Leaf) children.get(i));
                if (i != 0) {
                    children.get(i-1).setFrontPtr((Leaf) children.get(i));
                    children.get(i).setBackPtr((Leaf) children.get(i-1));
                } if (i+2 != children.size()) {
                    children.get(i+2).setBackPtr((Leaf) children.get(i+1));
                    children.get(i+1).setFrontPtr((Leaf) children.get(i+2));
                }
            }
            if(split)
                return temp;
            size++;
            return null;
        }

        @Override
        public TempNode delElem(int elem) {
            TempNode success = null;
            int i;
            boolean notAdded = true;
            for (i=0; i<size; i++) {
                if (elem < values.get(i)) {
                    success = children.get(i).delElem(elem);
                    notAdded = false;
                    break;
                }
            }
            if (notAdded)
                success = children.get(size).delElem(elem);
            if(success == null)
                return null;
            TempNode temp;
            boolean split = false;
            if(size == MAX/2 && !first) {
                temp = new TempNode(new LinkedList<>(values),new LinkedList<>(children));
                split = true;
            } else
                temp = new TempNode(values,children);

            if (split)
                return temp;
            size++;
            return null;
        }

        @Override
        public void setFrontPtr(Leaf frontPtr) {

        }

        @Override
        public void setBackPtr(Leaf backPtr) {

        }

        @Override
        public void printAll() {
            children.get(0).printAll();
        }

        @Override
        public boolean isPresent(int elem) {
            for (int i=0; i<size; i++) {
                if (elem < values.get(i))
                    return children.get(i).isPresent(elem);
            }
            return children.get(size).isPresent(elem);
        }

        @Override
        public List<Integer> range(int lower, int upper) {
            for (int i=0; i<size; i++) {
                if (lower < values.get(i))
                    return children.get(i).range(lower,upper);
            }
            return children.get(size).range(lower,upper);
        }

        public Inner(int MAX, List<Integer> values, List<Node> children) throws RecordTooLargeException, ChildNumberMismatch {
            super(MAX, values, false);
            if (children.size() != values.size() + 1) {
                throw new ChildNumberMismatch();
            }
            this.children = new LinkedList<>(children);
        }

        public Inner(int MAX, List<Integer> values, List<Node> children, boolean first) throws RecordTooLargeException, ChildNumberMismatch {
            super(MAX, values, first);
            if (children.size() != values.size() + 1) {
                throw new ChildNumberMismatch();
            }
            this.children = new LinkedList<>(children);
        }
    }

    public void add(int elem) {
        try {
            TempNode success = root.addElem(elem);
            if (success != null) { //New root node required.
                LinkedList<Integer> newVals = new LinkedList<>(Collections.singleton(success.getValues().get(halfSize(success.getValues().size()))));
                LinkedList<Node> newChildren;
                if (success.getChildren() != null) { //Child is an inner node
                    success.getValues().remove(halfSize(success.getValues().size()));
                    Inner node1 = new Inner(INNER_MAX, success.getValues().subList(0, halfSize(success.getValues().size())), success.getChildren().subList(0,halfSize(success.getChildren().size())));
                    Inner node2 = new Inner(INNER_MAX, success.getValues().subList(halfSize(success.getValues().size()), success.getValues().size()), success.getChildren().subList(halfSize(success.getChildren().size()),success.getChildren().size()));
                    newChildren = new LinkedList<>(List.of(node1,node2));
                } else { //Child is a leaf node
                    newChildren = new LinkedList<>(List.of(new Leaf(LEAF_MAX, success.getValues().subList(0, halfSize(success.getValues().size()))),new Leaf(LEAF_MAX, success.getValues().subList(halfSize(success.getValues().size()), success.getValues().size()))));
                    newChildren.get(0).setFrontPtr((Leaf) newChildren.get(1));
                    newChildren.get(1).setBackPtr((Leaf) newChildren.get(0));
                }
                root = new Inner(INNER_MAX,newVals,newChildren);
            }
        } catch (ChildNumberMismatch | RecordTooLargeException e) {
            e.printStackTrace();
        }
    }

    public boolean isPresent(int elem) {
        return root.isPresent(elem);
    }

    public List<Integer> range(int lower, int upper) {
        return root.range(lower,upper);
    }

    public void printAll() {
        root.printAll();
    }

    public int halfSize(int size) {
       if (size % 2 == 0 || INNER_MAX % 2 == 0) {
            return size/2;
        } else {
            return size/2+1;
        }

        //return size/2;
    }

    public BTree(int INNER_MAX, int LEAF_MAX, int[] items) {
        this.INNER_MAX = INNER_MAX;
        this.LEAF_MAX = LEAF_MAX;
        List<Node> list1 = new LinkedList<>();
        List<Node> list2 = new LinkedList<>();
        List<Integer> listInt = new LinkedList<>();
        List<Integer> listInt2 = new LinkedList<>();
        for (int i=0; i < items.length; i+=LEAF_MAX) {
            List<Integer> keys = new LinkedList<>();
            for (int j=0; j< LEAF_MAX; j++) {
                if (i+j >= items.length)
                    break;
                keys.add(items[i+j]);
            }
            try {
                Leaf addedNode = new Leaf(LEAF_MAX, keys);
                if (list1.size() > 0) {
                    list1.get(list1.size()-1).setFrontPtr(addedNode);
                    addedNode.setBackPtr((Leaf) list1.get(list1.size()-1));
                }
                list1.add(addedNode);

            } catch (RecordTooLargeException e) {e.printStackTrace();}
            listInt.add(keys.get(0));
        }
        while (list1.size() > 1) {
            listInt.remove(0);
            while (listInt.size() > 0) {
                List<Integer> keys = new LinkedList<>();
                List<Node> pointers = new LinkedList<>();
                pointers.add(list1.get(0));
                list1.remove(0);
                for (int j = 0; j < INNER_MAX; j++) {
                    if (listInt.size() == 0)
                        break;
                    keys.add(listInt.get(0));
                    listInt.remove(0);
                    if (list1.size() == 0)
                        break;
                    pointers.add(list1.get(0));
                    list1.remove(0);
                }
                try {
                    list2.add(new Inner(INNER_MAX, keys, pointers));
                } catch (RecordTooLargeException | ChildNumberMismatch e) {
                    e.printStackTrace();
                }
                listInt2.add(keys.get(0));
            }
            list1 = list2;
            listInt = listInt2;
            list2 = new LinkedList<>();
            listInt2 = new LinkedList<>();
        }
        this.root = (Inner) list1.get(0);
    }

    public BTree(int INNER_MAX, int LEAF_MAX, int v1, int v2) {
        this.INNER_MAX = INNER_MAX;
        this.LEAF_MAX = LEAF_MAX;
        LinkedList<Integer> values1 = new LinkedList<>(Collections.singleton(v1));
        LinkedList<Integer> values2 = new LinkedList<>(Collections.singleton(v2));
        try {
            Leaf node1 = new Leaf(LEAF_MAX, values1, true);
            Leaf node2 = new Leaf(LEAF_MAX, values2, true);
            node1.setFrontPtr(node2);
            node2.setBackPtr(node1);
            this.root = new Inner(INNER_MAX, values2, new LinkedList<>(List.of(node1, node2)));
        } catch (ChildNumberMismatch| RecordTooLargeException ignored) {}
    }
}
