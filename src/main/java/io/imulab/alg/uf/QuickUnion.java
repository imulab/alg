package io.imulab.alg.uf;

import io.imulab.alg.doc.TimeComplexity;

/**
 * An {@link UnionFind} implementation that forms a tree of points when they are in the same group. Two points
 * are connected when they have the same root.
 *
 * Performance characteristics:
 *  * <ol>
 *  *     <li>{@link #find(int)} is slow: O(N)</li>
 *  *     <li>{@link #union(int, int)} is quick, but includes cost of {@link #find(int)}: O(N)</li>
 *  *     <li>Overall, it is slow to process N points, occurs cost of O(N^2)</li>
 *  * </ol>
 */
public class QuickUnion implements UnionFind {

    /**
     * Basic data structure for {@link QuickUnion}. The array is indexed by N points,
     * the value of {@code parent[k]} points to the <b>parent</b> of point {@code k}.
     */
    private final int[] parent;

    /**
     * Total number of components;
     */
    private int count;

    @TimeComplexity(
            op = "initialize",
            value = "O(N)"
    )
    public QuickUnion(int N) {
        assert N > 0;

        /*
        Initialize the component array.
        Each point is in this own group at the start.
         */
        this.parent = new int[N];
        for (int i = 0; i < N; i++)
            parent[i] = i;

        count = N;
    }

    @SuppressWarnings("Duplicates")
    @TimeComplexity(
            op = "union",
            value = "O(N)",
            comment = "includes the cost of finding the root"
    )
    @Override
    public void union(int p, int q) {
        checkIndex(p);
        checkIndex(q);

        /*
        Set root of p to the root of q. Effectively merging the two trees.
         */
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;
        parent[rootP] = rootQ;

        count--;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @TimeComplexity(
            op = "find",
            value = "O(N)",
            comment = "N is the worst case performance as trees can get tall, starting to resemble a list."
    )
    @Override
    public int find(int p) {
        checkIndex(p);

        /*
        Trace back through the parent until finding the root
        where parent of the root is itself.
         */
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    @Override
    public int count() {
        return this.count;
    }

    private void checkIndex(int i) {
        if (i < 0 || i > parent.length - 1)
            throw new IllegalArgumentException("index " + i + " is out of bounds.");
    }
}
