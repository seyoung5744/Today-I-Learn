package chap19._19_2;

public class Tree {

    private String key;
    private int value;
    private Tree left, right;

    public Tree(String key, int value, Tree left, Tree right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public static int lookup(String key, int defaultValue, Tree tree) {
        if (tree == null) {
            return defaultValue;
        }
        if (key.equals(tree.key)) {
            return tree.value;
        }
        return lookup(key, defaultValue, key.compareTo(tree.key) < 0 ? tree.left : tree.right);
    }

    public static Tree update(String key, int newValue, Tree tree) {
        if (tree == null) {
            tree = new Tree(key, newValue, null, null);
        } else if (key.equals(tree.key)) {
            tree.value = newValue;
        } else if (key.compareTo(tree.key) < 0) {
            tree.left = update(key, newValue, tree.left);
        } else {
            tree.right = update(key, newValue, tree.right);
        }
        return tree;
    }

    public static Tree fupdate(String key, int newValue, Tree tree) {
        return (tree == null) ?
            new Tree(key, newValue, null, null) :
            key.equals(tree.key) ?
                new Tree(key, newValue, tree.left, tree.right) :
                key.compareTo(tree.key) < 0 ?
                    new Tree(tree.key, tree.value, fupdate(key, newValue, tree.left), tree.right) :
                    new Tree(tree.key, tree.value, tree.left, fupdate(key, newValue, tree.right));
    }
}
