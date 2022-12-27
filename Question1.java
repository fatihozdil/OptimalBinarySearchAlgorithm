import java.util.Random;

public class Question1 {
    public static void main(String[] args) {
        // create one hundred thousand distinct integer keys between 0 and 99999
        int[] keys = new int[100000];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = i;
        }

        // randomly assign frequency counts to the keys as integers between 1 and 100
        Random random = new Random();
        int[] freq = new int[keys.length];
        for (int i = 0; i < freq.length; i++) {
            freq[i] = random.nextInt(100) + 1;
        }

        // compute the root node information for an optimal BST
        Result[][] dp = optimalSearchTree(keys, freq, keys.length);

        // create two instances of the BinarySearchTree class to construct two different BSTs of the keys
        BinarySearchTree bst1 = new BinarySearchTree(0);
        BinarySearchTree bst2 = new BinarySearchTree(0);

        // insert the keys into the two random BSTs in a random order
        for (int i = 0; i < keys.length; i++) {
            int index = random.nextInt(keys.length);
            bst1.insert(keys[index]);
            index = random.nextInt(keys.length);
            bst2.insert(keys[index]);
        }

        // create a third BST by calling the constructOptimalSearchTree method
        BinarySearchTree bst3 = BinarySearchTree.constructOptimalSearchTree(dp, 0, keys.length - 1);

        // randomly select one hundred keys
        int[] searchKeys = new int[100];
        for (int i = 0; i < searchKeys.length; i++) {
            searchKeys[i] = random.nextInt(keys.length);
        }

        // measure the average physical running time of search over all keys for each BST
        long startTime, endTime, totalTime1 = 0, totalTime2 = 0, totalTime3 = 0;
        for (int i = 0; i < searchKeys.length; i++) {
            // search for each key in the first random BST a number of times equal to its frequency
            startTime = System.nanoTime();
            for (int j = 0; j < freq[searchKeys[i]]; j++) {
                bst1.search(searchKeys[i]);
            }
            endTime = System.nanoTime();
            totalTime1 += endTime - startTime;

            // search for each key in the second random BST a number of times equal to its frequency
            startTime = System.nanoTime();
            for (int j = 0; j < freq[searchKeys[i]]; j++) {
                bst2.search(searchKeys[i]);
            }
            endTime = System.nanoTime();
            totalTime2 += endTime - startTime;

            // search for each key in the optimal BST a number of times equal to its frequency
            startTime = System.nanoTime();
            for (int j = 0; j < freq[searchKeys[i]]; j++) {
                bst3.search(searchKeys[i]);
            }
            endTime = System.nanoTime();
            totalTime3 += endTime - startTime;
        }

        // compute the average running time for each BST
        double avgTime1 = (double) totalTime1 / searchKeys.length;
        double avgTime2 = (double) totalTime2 / searchKeys.length;
        double avgTime3 = (double) totalTime3 / searchKeys.length;
        System.out.println("Average running time for random BST 1: " + avgTime1 + " nanoseconds");
        System.out.println("Average running time for random BST 2: " + avgTime2 + " nanoseconds");
        System.out.println("Average running time for optimal BST: " + avgTime3 + " nanoseconds");

        // compare the average running times of the three BSTs
        if (avgTime3 < avgTime1 && avgTime3 < avgTime2) {
            System.out.println("The optimal BST has the lowest average running time.");
        } else {
            System.out.println("The optimal BST does not have the lowest average running time.");
        }
    }

    public static Result[][] optimalSearchTree(int[] keys, int[] freq, int n) {
        // create a two-dimensional array of Result objects
            Result[][] dp = new Result[n][n];

        // fill the array with the optimal BST root node information for each subproblem
        for (int i = 0; i < n; i++) {
            dp[i][i] = new Result(keys[i], freq[i], i, i);
        }
        for (int L = 2; L <= n; L++) {
            for (int i = 0; i <= n - L + 1; i++) {
                int j = i + L - 1;
                dp[i][j] = new Result();
                int sum = getSum(freq, i, j);
                for (int r = i; r <= j; r++) {
                    int c = sum;
                    if (r > i) {
                        c += dp[i][r - 1].cost;
                    }
                    if (r < j) {
                        c += dp[r + 1][j].cost;
                    }
                    if (dp[i][j].cost == 0 || c < dp[i][j].cost) {
                        dp[i][j].cost = c;
                        dp[i][j].root = r;
                    }
                }
            }
        }
        // return the two-dimensional array of Result objects
        return dp;
    }

    private static int getSum(int[] freq, int i, int j) {
        int sum = 0;
        for (int k = i; k <= j; k++) {
            sum += freq[k];
        }
        return sum;
    }


    public static void shuffle(int[] arr) {
        Random random = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }


}

class Result {
    int cost;
    int root; // added field to hold the key of the root node
    int i, j;

    public Result() {
        cost = 0;
        root = 0;
        i = 0;
        j = 0;
    }

    public Result(int root, int cost, int i, int j) {
        this.root = root;
        this.cost = cost;
        this.i = i;
        this.j = j;
    }
}

class BinarySearchTree {
    int key;
    BinarySearchTree left, right;

    public BinarySearchTree(int key) {
        this.key = key;
        left = right = null;
    }

    public static BinarySearchTree constructOptimalSearchTree(Result[][] dp, int i, int j) {
        // base case: if i and j are the same, return a leaf node
        if (i == j) {
            return new BinarySearchTree(dp[i][j].root);
        }

        // recursively construct the left and right subtrees
        BinarySearchTree left = constructOptimalSearchTree(dp, i, dp[i][j].root - 1);
        BinarySearchTree right = constructOptimalSearchTree(dp, dp[i][j].root + 1, j);

        // create a new BST with the root node from the table
        BinarySearchTree root = new BinarySearchTree(dp[i][j].root);
        root.left = left;
        root.right = right;

        // return the constructed BST
        return root;
    }

    public void insert(int key) {
        // recursive case: insert the key into the left or right subtree, depending on its value
        if (key < this.key) {
            if (left == null) {
                left = new BinarySearchTree(key);
            } else {
                left.insert(key);
            }
        } else {
            if (right == null) {
                right = new BinarySearchTree(key);
            } else {
                right.insert(key);
            }
        }
    }

    public BinarySearchTree search(int key) {
        // base case: if the current node has the key, return it
        if (this.key == key) {
            return this;
        }

        // recursive case: search the left or right subtree, depending on the key
        if (key < this.key) {
            if (left == null) {
                return null;
            } else {
                return left.search(key);
            }
        } else {
            if (right == null) {
                return null;
            } else {
                return right.search(key);
            }
        }
    }
}


 class Test {
 }