import java.util.ArrayList;

public class BinarySearchTree<X extends Comparable<X>, Y extends Comparable<Y>>
		implements Relation<X, Y> {

	// Each BST object is a binary-search-tree header (created in the inner
	// class below)
	private BinarySearchTree.Node<X, Y> rootNode;

	// variable to print the contents of a BST as a string.
	private static String renderedString;

	// constructor to make an empty BST. RootNode will initially be empty/null;
	public BinarySearchTree() {
		rootNode = null;
	}

	// //////// Inner class for a BST Node //////////
	static class Node<X extends Comparable<X>, Y extends Comparable<Y>> {
		protected X elementX;
		protected Y elementY;
		protected Node<X, Y> left, right;
		/*
		 * each node contains a pair , x and y of type X and Y, as well as references
		 * to the right and left children
		 */
		protected Node(X elemX, Y elemY) {
			elementX = elemX;
			elementY = elemY;
			left = null;
			right = null;
		}

		//method to delete topmost node in tree
		public Node<X, Y> deleteTopmost() {
			if (this.left == null)
				return this.right;
			else if (this.right == null)
				return this.left;
			else { // this node has two children
				this.elementX = this.right.getXLeftmost();
				this.elementY = this.right.getYLeftMost();
				this.right = this.right.deleteLeftmost();
				return this;
			}
		}
		
		//method to return the X element in the leftmost node of the tree
		private X getXLeftmost() {
			Node<X, Y> curr = this;
			while (curr.left != null)
				curr = curr.left;
			return curr.elementX;
		}

		//method to return the Y element in the leftmost node of the tree
		private Y getYLeftMost() {
			Node<X, Y> curr = this;
			while (curr.left != null)
				curr = curr.left;
			return curr.elementY;
		}

		public Node<X, Y> deleteLeftmost() {
			if (this.left == null)
				return this.right;
			else {
				Node<X, Y> parent = this, curr = this.left;
				while (curr.left != null) {
					parent = curr;
					curr = curr.left;
				}
				parent.left = curr.right;
				return this;
			}
		}
		// end of the Node Inner class

	}

	// Operation 1: search method, takes in both x y and finds if there is an
	// element matching. Boolean to returns true/false if element = found/not found
	public boolean search(X elementX, Y elementY) {
		int directionX = 0;
		int directionY = 0;
		boolean found = false;
		BinarySearchTree.Node<X, Y> curr = rootNode;
		for (;;) {
			if (curr == null) {
				found = false;
				break;
			}
			// direction, compare the take in X to the X in the current element
			directionX = elementX.compareTo(curr.elementX);
			// if this returns zero, they are the same and we need to compare
			// the Y elements
			if (directionX == 0) {
				directionY = elementY.compareTo(curr.elementY);
				// if the Y elements are the same, then return the current
				// element as it matches what we are searching for
				if (directionY == 0) {
					found = true;
					break;
				}
				// else if it is -1 from compare to, the Y makes the pair
				// smaller so goes left
				else if (directionY < 0) {
					curr = curr.left;
				}
				// else if it is 1 in the compareTo, Y makes the pair greater so
				// goes to the right
				else if (directionY > 0) {
					curr = curr.right;
				}
				// else if the X is less, then all the above nested if is
				// skipped, and will jump to the left of the element
			} else if (directionX < 0) {
				curr = curr.left;
			} else if (directionX > 0) {
				curr = curr.right;
			}
		}
		return found;
	}// end of method
	
	/*
	 * Operation 2: To return all the X values that a given Y has, an ArrayList
	 * of these X values must be returned
	 */
	@Override
	public ArrayList<X> getX(Y y) {
		ArrayList<X> listCorrespondingXgivenY = new ArrayList<X>();
		getXHelper(rootNode, y, listCorrespondingXgivenY);
		return listCorrespondingXgivenY;
	}

	private <X extends Comparable<X>, Y extends Comparable<Y>> void getXHelper(
			BinarySearchTree.Node<X, Y> topNode, Y elementY,
			ArrayList<X> listCorrespondingXgivenY) {
		int target = 0;

		if (topNode != null) {
			target = elementY.compareTo(topNode.elementY);
			getXHelper(topNode.left, elementY, listCorrespondingXgivenY);

			if (target == 0) {
				//if Y is found, add its X partner to the ArrayList
				listCorrespondingXgivenY.add(topNode.elementX);
			}
			getXHelper(topNode.right, elementY, listCorrespondingXgivenY);
		}
	}//end of method

	/*
	 * Operation 3: To return all the Y values that a given X has, an ArrayList
	 * of these X values must be returned
	 */
	public ArrayList<Y> getY(X x) {
		ArrayList<Y> listCorrespondingYgivenX = new ArrayList<Y>();
		getYHelper(rootNode, x, listCorrespondingYgivenX);
		return listCorrespondingYgivenX;
	}

	private <X extends Comparable<X>, Y extends Comparable<Y>> void getYHelper(
			BinarySearchTree.Node<X, Y> topNode, X elementX,
			ArrayList<Y> listCorrespondingYgivenX) {
		int target = 0;

		if (topNode != null) {
			target = elementX.compareTo(topNode.elementX);
			getYHelper(topNode.left, elementX, listCorrespondingYgivenX);

			if (target == 0) {
				//if X found, add its Y partner to the ArrayList
				listCorrespondingYgivenX.add(topNode.elementY);
			}

			getYHelper(topNode.right, elementX, listCorrespondingYgivenX);

		}
	} //end of method

	/*
	 * Operation 4: Clears the whole relation by setting the rootNode to null
	 */
	@Override
	public void clearRelation() {
		rootNode = null;
	}
	
	/*
	 * Operation 5: Add a pair to the relation
	 */
	public void addPair(X elementX, Y elementY) {
		int directionY = 0;
		int directionX = 0;
		BinarySearchTree.Node<X, Y> parent = null, curr = rootNode;
		for (;;) {
			if (curr == null) {
				BinarySearchTree.Node<X, Y> ins = new BinarySearchTree.Node<X, Y>(
						elementX, elementY);
				if (rootNode == null) {
					rootNode = ins;
				}
				if (directionX == 0) {
					if (directionY == 0) {
						// if both zero, it is a duplicate.
						return;
					} else if (directionY < 0) {
						parent.left = ins;
					} else if (directionY > 0) {
						parent.right = ins;
					}
				} else if (directionX < 0) {
					parent.left = ins;

				} else if (directionX > 0) {
					parent.right = ins;
				}
				return;
			}
			directionX = elementX.compareTo(curr.elementX);
			directionY = elementY.compareTo(curr.elementY);
			if (directionX == 0) {
				// if X is the same, check Y is not the same
				if (directionY == 0) {
					// if still the same (0) terminate as the pair is a
					// duplicate.
					return;
				} else if (directionY < 0) {
					parent = curr;
					curr = curr.left;

				} else if (directionY > 0) {
					parent = curr;
					curr = curr.right;
				}
			}
			if (directionX < 0) {
				parent = curr;
				curr = curr.left;
			} else if (directionX > 0) {
				parent = curr;
				curr = curr.right;
			}
		}
	} //end of method

	/*
	 * Operation 6: Remove a pair from the relation
	 */
	public void removePair(X elementX, Y elementY) {
		int directionX = 0;
		int directionY = 0;
		BinarySearchTree.Node<X, Y> parent = null, curr = rootNode;
		for (;;) {
			if (curr == null) {// if rootNode is null, relation is empty
				return;
			}
			directionX = elementX.compareTo(curr.elementX);
			if (directionX == 0) {// if X is matching, we want to check if Y is
									// too
				BinarySearchTree.Node<X, Y> del = curr.deleteTopmost();
				directionY = elementY.compareTo(curr.elementY);
				if (directionY == 0) {
					if (curr == rootNode) {
						rootNode = del; // if current is the root, delete root
					} else if (curr == parent.left) {
						parent.left = del;
						return;
					} else {
						parent.right = del;
						return;
					}
				} else if (directionY < 0) {
					// if Y is different, then this is not the correct pair, so
					// return and keep searching
					return;
				} else if (directionY > 0) {
					// if Y is different, then this is not the correct pair, so
					// return and keep searching
					return;
				}
			}
			parent = curr;
			if (directionX < 0) {
				curr = parent.left;
			} else if (directionX > 0) {
				curr = parent.right;
			}
		}
	}

	// end of method//

	// Operation 7: Remove all pairs of a given X
	public void removeAllXPairs(X x) {
		removeAllXHelper(rootNode, x, null);
	}

	private void removeAllXHelper(BinarySearchTree.Node<X, Y> topNode,
			X elementX, BinarySearchTree.Node<X, Y> parent) {
		int target = 0;

		if (topNode == null) {
			return;
		}
		target = elementX.compareTo(topNode.elementX);
		removeAllXHelper(topNode.left, elementX, topNode);
		removeAllXHelper(topNode.right, elementX, topNode);

		// if the target is zero, we have hit a X-pair and want to delete it! :
		if (target == 0) {
			BinarySearchTree.Node<X, Y> del = topNode.deleteTopmost();
			if (topNode == rootNode) {
				rootNode = del;
			} else if (topNode == parent.left) {
				parent.left = del;
			} else {
				parent.right = del;
			}
		}
	}

	// end of method//

	// Operation 8: methods to remove all Y pairs with a given X
	public void removeAllYPairs(Y y) {
		removeAllYHelper(rootNode, y, null);

	}

	private void removeAllYHelper(BinarySearchTree.Node<X, Y> topNode,
			Y elementY, BinarySearchTree.Node<X, Y> parent) {
		int target = 0;

		if (topNode == null) {
			return;
		}
		target = elementY.compareTo(topNode.elementY);
		removeAllYHelper(topNode.left, elementY, topNode);
		removeAllYHelper(topNode.right, elementY, topNode);

		// if the target is zero, we have hit a Y-pair and want to delete it! :
		if (target == 0) {
			BinarySearchTree.Node<X, Y> del = topNode.deleteTopmost();
			if (topNode == rootNode) {
				rootNode = del;
			} else if (topNode == parent.left) {
				parent.left = del;
			} else {
				parent.right = del;
			}
		}
	}

	// end of method

	// Operation 9: methods to render the contents of the relation as a String
	public String printRenderAsString() {
		renderedString = "";
		String renderedString = printRenderAsAString(rootNode);
		System.out.println(renderedString);
		return renderedString;
	}

	private static <X extends Comparable<X>, Y extends Comparable<Y>> String printRenderAsAString(
			BinarySearchTree.Node<X, Y> top) {
		// Print, in ascending order, all the elements in the BST
		// subtree whose topmost node is top.
		if (top != null) {
			printRenderAsAString(top.left);
			renderedString += ("Element X = " + top.elementX+ "   Element Y = " + top.elementY + "\n");
			printRenderAsAString(top.right);
		}
		return renderedString;
	}

	// end of methods//

	// Main method will provide demonstrations of the operations of the
	// relation.
	public static void main(String[] args) {
		BinarySearchTree<String, Integer> animals = new BinarySearchTree<String, Integer>();

		animals.addPair("lion", 7);
		animals.addPair("fox", 8);
		animals.addPair("rat", 3);
		animals.addPair("cat", 7);
		animals.addPair("cat", 7);
		animals.addPair("cat", 8);
		animals.addPair("pig", 6);
		animals.addPair("dog", 1);
		animals.addPair("tiger", 1);
		animals.addPair("fox", 7);

		// firstly will print out the Full contents
		System.out.println("Full Tree contents ordered & rendered as a String:  "
						+ "\n");
		animals.printRenderAsString();

		// 1. Printed demonstration of the search function.
		System.out.println("1. Find a given pair: SEARCH FUNCTION DEMONSTRATION. I will search for the given pair 'lion' and 7, which exists, and 'lion' 2 which does not exist");
		System.out.println("Is is" + " " + animals.search("lion", 7)+ " that the pair is there" + "\n");
		System.out.println("Is is" + " " + animals.search("lion", 2)
				+ " that the pair is there" + "\n");

		// 2. demonstrates returning the list of X elements which contain a
		// given value Y. These elements are stored in an Arraylist
		System.out.println("2. Now I will return a list of the X elements when a Y is supplied. "+ "I am supplying the Y as 1 so I expected my list of X values to return Dog and Tiger"+ "\n");
		ArrayList<String> xList = animals.getX(1);
		for (String s : xList)
			System.out.println("x List:  " + s + "\n");

		// 3. demonstrates returning the list of Y elements which contain a
		// given value X. These elements are stored in an arraylist
		System.out.println("3. Now I will return a list of the Y elements when a X is supplied. "+ "I am supplying the X as cat so I expected my list of Y values to return 7 and 8"+ "\n");
		ArrayList<Integer> yList = animals.getY("cat");
		for (Integer numY : yList)
			System.out.println("y List:  " + numY + "\n");

		// 4. Make the relation empty.
		System.out.println("4. Demonstration of making the relation empty:");
		animals.clearRelation(); // clear the relation
		animals.printRenderAsString(); // print the relation. Should be empty as
										// have cleared it
		System.out.println("relation cleared as the print method above this returns nothing \n");

		// 5. Demonstrates adding a pair to the relation (which having been
		// cleared is empty, so adds the root element).
		System.out.println("5. Demonstration of adding a pair to the relation. I will add x as monkey  and y as 12."
						+ "The relation is initially empty to this should be the only element. \n First here is the initial relation ");
		animals.printRenderAsString();
		animals.addPair("monkey", 12);
		System.out.println("Now having added the pair, the relation : \n");
		animals.printRenderAsString();

		// //for the purpose of the remaining demonstrations i will demonstrate
		// further adding pairs.
		animals.addPair("lion", 7);
		animals.addPair("fox", 8);
		animals.addPair("rat", 3);
		animals.addPair("cat", 7);
		animals.addPair("cat", 7);
		animals.addPair("cat", 8);
		animals.addPair("pig", 6);
		animals.addPair("dog", 1);
		animals.addPair("tiger", 1);
		animals.addPair("fox", 7);

		// 6. removes a given pair from the relation (pig & 6)
		System.out.println("6. Demonstration of removing a given pair. I will remove pig and 6. \n First here is the relation before removing: \n");
		animals.printRenderAsString();
		animals.removePair("pig", 6);
		System.out.println("And the relation after removing pig and 6: \n");
		animals.printRenderAsString();

		// 7. Remove all pairs with a given X from the relation
		System.out.println("7. given X, remove all pairs of this X from the relation. X = 'cat' so pairs(cat, 7) & (cat,8) should be removed. Initial Relation: \n");
		animals.printRenderAsString();
		animals.removeAllXPairs("cat");
		System.out.println("Relation after pairs with 'cat' removed: \n");
		animals.printRenderAsString();

		// 8. Remove all pairs with a given Y from the relation
		System.out.println("8. Give y, remove all pairs of this Y from the relation. Y = 1 so pairs (dog,1) & (tiger,1) should be removed. Initial Relation: \n");
		animals.printRenderAsString();
		animals.removeAllYPairs(1);
		System.out.println("Relation after pairs with '1' removed: \n");
		animals.printRenderAsString();

		// 9. Render the relations contents as a String
		System.out.println("9. Print contents rendered as a String, although previously demonstrated: \n");
		animals.printRenderAsString();
		
	}
}