
/*
 * A node for a Binary Search Tree (BST)
 */
public class BSTNode <T> {

	T key; /* This node's key */
	BSTNode<T> left;  /* Node to the left of this node */
	BSTNode<T> right; /* Node to the right of this node */
	public int rightSize;
	
	BSTNode (T key, BSTNode<T> left, BSTNode<T> right, int rightSize) {
		this.key = key;
		this.left = left;
		this.right = right;
	}
}