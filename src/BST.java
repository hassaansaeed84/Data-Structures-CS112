import java.util.ArrayList;
import java.util.NoSuchElementException;

/*
 * Binary Search Tree (BST)
 * Duplicates are not allowed for this example
 * 
 * Comparable (is an interface) requires T class to implement the
 * compareTo method
 */
public class BST <T extends Comparable<T>> {

	BSTNode<T> root;
	int size;
	
	BST () { // initialize BST to be an empty tree
		root = null; 
		size = 0;
	}
	/*
	 * Searches for key in the BST
	 * Returns the entire Node that contains the key
	 */
	public BSTNode<T> search (T target) {
		BSTNode<T> ptr = root;
		while ( ptr != null ) {
			int c = target.compareTo(ptr.key);
			if ( c == 0 ) {
				return ptr; // found target
			} else if ( c < 0 ) {
				ptr = ptr.left; // target is smaller than ptr.key
			} else {
				ptr = ptr.right; // target is greater than ptr.key
			}
		}
		return null; // target not found
	}
	/*
	 * BST does not allow duplicates
	 */
	public void insert (T key) {
		// 1. search to fail
		BSTNode<T> ptr = root;
		BSTNode<T> prev = null;
		int c = 0;
		while ( ptr != null ) {
			c = key.compareTo(ptr.key);
			if ( c == 0 ) { // key and ptr.key are equal
				throw new IllegalArgumentException(key + " already in BST");
			}
			prev = ptr;
			if ( c < 0 ) { // key is smaller than ptr.key
				ptr = ptr.left;
			} else {
				ptr = ptr.right;
			}
		}
		// 2. insert key as one of prev children
		BSTNode<T> node = new BSTNode<T>(key, null, null, 0);
		if ( prev == null ) {
			// empty tree
			root = node;
		} else if ( c < 0 ) {
			prev.left = node;
		} else {
			prev.right = node;
		}
		size += 1;
	}
	public void insertRecursion (T key, BSTNode<T> root) {
		// 1. search to fail
		BSTNode<T> ptr = root;
		BSTNode<T> prev = null;
		BSTNode<T> node=new BSTNode<T>(key, null, null, 0);
		int c =0;
		if (ptr==null) {
			root=node;
		}
		else if(ptr.left==null || ptr.right==null) {
			c=key.compareTo(ptr.key);
			if(c<0)
			{
				ptr.left=node;
			}
			else if(c>0) {
				ptr.right=node;
			}
		}
		if(c<0) {
			prev=ptr;
			insertRecursion(key, ptr.left);
		}
		else if(c>0) {
			prev=ptr;
			insertRecursion(key, ptr.right);
		}
		
		size += 1;
	}
	public void delete (T key) {
		// 1. find the node to delete: x
		BSTNode<T> x = this.root;
		BSTNode<T> p = null;
		int c = 0;
		while ( x != null ) {
			c = key.compareTo(x.key);
			if ( c == 0 ) {
				break; // found x
			}
			p = x;
			x = (c < 0) ? x.left : x.right; //if(c<0){x=x.left}else{x=x.right}
		}
		// 2. if x is not found
		if ( x == null ) {
			throw new NoSuchElementException(key + " not found.");
		}
		// 3. check case 3 where x has two children
		// Find x inorder predecessor (largest value in the left subtree)
		BSTNode<T> y = null; // inorder predecessor
		if ( x.left != null && x.right != null ) {
			y = x.left; // take a left
			p = x;
			while ( y.right != null ) { // go all the way to the right
				p = y;
				y = y.right;
			}
			// copy y's data and key into x
			x.key = y.key;
			
			// prepare to delete y
			x = y;
		}
		// 4. check if p is null, if so, x is the root
		// and has only one child or no children
		if ( p == null ) {
			root = (x.left != null) ? x.left : x.right;
			size -= 1;
			return;
		}
		// 5. handle case 1 and 2 in the same code
		// Also handling removing y from case 3
		BSTNode<T> tmp = (x.right != null) ? x.right : x.left;
		if ( x == p.left ) {
			p.left = tmp;
		} else {
			p.right = tmp;
		}
		size -= 1;
	}
	public static <T extends Comparable<T>> void 
	inorderTraversal (BSTNode<T> root) {
		
		if (root == null) {
			return;
		}
		inorderTraversal(root.left);
		System.out.print(root.key + " ");
		inorderTraversal(root.right);
	}
	// Accumulates, in a given array list, all entries in a BST whose keys are in a given range,
    // including both ends of the range - i.e. all entries x such that min <= x <= max. 
    // The accumulation array list, result, will be filled with node data entries that make the cut. 
    // The array list is already created (initially empty) when this method is first called.
	public static <T extends Comparable<T>> 
    void keysInRange(BSTNode<T> root, T min, T max, ArrayList<T> result) {
        if (root == null) { 
           return;
        }
        int c1 = min.compareTo(root.key);
        int c2 = root.key.compareTo(max);
        if (c1 <= 0 && c2 <= 0) {  // min <= root <= max) 
           result.add(root.key);
        }
        if (c1 < 0) {
           keysInRange(root.left, min, max, result);
        }
        if (c2 < 0) {
           keysInRange(root.right, min, max, result);
        }
    }
	public static <T extends Comparable<T>> 
    void reverseKeys(BSTNode<T> root) {
        /* COMPLETE THIS METHOD */
		if(root==null) {
			return;
		}
			reverseKeys(root.left);
			reverseKeys(root.right);
		BSTNode<T> temp=root.right;
		root.right=root.left;
		root.left=temp;
	}
	public static <T extends Comparable<T>> T kthLargest(BSTNode<T> root, int k) {
	       /* COMPLETE THIS METHOD */
		if (root.rightSize == (k-1)) {
	           return root.key;
	       }
	       if (root.rightSize >= k) {
	           return kthLargest(root.right, k);
	       }
	       return kthLargest(root.left, k-root.rightSize-1);
	    }
	       
	    
	public static void main (String[] args) {
		BST<Integer> bst = new BST<Integer>();
		bst.insert(20);
		bst.insert(10);
		bst.insert(30);
		bst.insert(5);
		bst.insert(35);
		ArrayList<Integer> o= new ArrayList<Integer>();
		int key = 15;
		BSTNode<Integer> node = bst.search(key);
		if (node == null) {
			System.out.println(key + " is not in BST");
		} else {
			System.out.println(key + " in BST");
		}
		System.out.println(kthLargest(bst.root, 6));
		inorderTraversal(bst.root);
		reverseKeys(bst.root);
		System.out.println();
		inorderTraversal(bst.root);
		bst.delete(35);
		System.out.println();
		inorderTraversal(bst.root);
	}
}