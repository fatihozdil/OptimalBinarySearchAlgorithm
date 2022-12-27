# OptimalBinarySearchAlgorithm
We are given a sorted array keys[0 . . . n − 1] of n search keys to be stored in a binay search tree (BST) and an
array f req[0 . . . n − 1] of frequency counts where f req[i] is the expected number of times of search for keys[i].
In order to do an efficient search for these keys, we want to construct P an optimal binary search tree of all keys
that minimizes the total cost of all the searches (i.e. T otal cost = i (depth(i) + 1) × f req(i)).  

(a)  A Java implementation for optimal BST problem using Dynamic Programming is given here
https://www.geeksforgeeks.org/optimal-binary-search-tree-dp-24/. Given n keys along with their frequen-
cies, the optimalSearchTree method returns the total cost of the optimal BST. In that solution, only the
optimal cost when the is computed. However, we also want to construct the BST from n keys and their
frequencies.  

Modify the code so that the optimalSearchTree method returns, in the SAME table, the key of the root
node as well as the cost of the optimal tree for every subproblem. You may add new classes/methods.
Hint: Consider a table holding objects instead of numbers.  

(b) You will use the BinarySearchTree class here https://www.geeksforgeeks.org/binary-search-
tree-set-1-search-and-insertion/. Your goal is to write a method constructOptimalSearchTree which
constructs an optimal BST given a table holding root node information, as returned by the above opti-
malSearchTree method.  

(c) Write a separate test class. First, create one-hundred-thousand distinct integer keys between
0 and 99999 and randomly assign their frequency counts as integers between 1 and 100. Call opti-
malSearchTree method to return a table holding the root node information for an optimal BST. Then,
create two instances of the BinarySearchTree class to construct two different BSTs of those one-hundred-
thousand keys by calling insert method for each key in a random order. You will also create a one last
BinarySearchTree by calling the constructOptimalSearchTree method. Finally, randomly select one-
hundred keys and in each BST, sequentially call search method to search for each of these keys a number
of times equal to its frequency. Compute and compare the average physical running times of search over
all keys for each tree.
