//import BinarySearchTree.Node;

//interface to implement the dataType 'Relation'. Uses generic types X and Y for the key and value of each pair

import java.util.*;

public interface Relation<X extends Comparable<X>, Y extends Comparable<Y>> {

	// 1. method to test whether the Relation value contains a given pair
	public boolean search(X x, Y y);

	// 2. method to determine all y values, such that the relation contains
	// (x,y), given x
	public ArrayList<Y> getY(X x);

	// 3. method to determine all x values, such that the relation contains
	// (x,y) given y
	public ArrayList<X> getX(Y y);

	// 4. method to make a relation empty
	public void clearRelation();

	// 5. method to add a pair (x,y) to the relation
	public void addPair(X x, Y y);

	// 6. method to remove a given pair (x,y) from the relation
	public void removePair(X x, Y y);

	// 7. given x, remove pairs (x,y) from the relation
	public void removeAllXPairs(X x);

	// 8. given y, remove all (x,y) pairs from the relation
	public void removeAllYPairs(Y y);

	// 9. render the relations contents as a string in a suitable format.
	public String toString();

}
