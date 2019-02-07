package io.imulab.alg.uf;

import io.imulab.alg.doc.TimeComplexity;

/**
 * An implementation of {@link UnionFind} that serves as an improvement of {@link QuickUnion}. In {@link QuickUnion},
 * performance degrades to O(N) when trees get tall. This implementation tries to alleviate the problem by tracking
 * the sizes of each tree and only attach smaller tree to larger trees so they can be balanced.
 *
 * Another improvement is <b>path compression</b>. When performing {@link #find(int)}, we traverse through many nodes
 * in the tree. The parent of those nodes can all be set to the root, thus resulting in a very flat tree.
 *
 * Performance characteristics:
 * <ol>
 *     <li>{@link #union(int, int)} is O(lgN) and includes finding</li>
 *     <li>{@link #find(int)} is O(lgN)</li>
 *     <li>{@link #connected(int, int)} is O(lgN)</li>
 * </ol>
 */
public class WeightedQuickUnion implements UnionFind {

    private final int[] parent;
    private final int[] size;
    private int count;

    @TimeComplexity(
            op = "initialize",
            value = "O(N)"
    )
    public WeightedQuickUnion(int N) {
        assert N > 0;

        /*
        Similar initialization.
        Every point is its own tree of size 1 from the start.
         */
        parent = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        count = N;
    }

    @TimeComplexity(
            op = "union",
            value = "O(lgN)"
    )
    @SuppressWarnings("Duplicates")
    @Override
    public void union(int p, int q) {
        checkIndex(p);
        checkIndex(q);

        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;

        if (size[rootP] < size[rootQ]) {    // tree of q is larger, attach p to q.
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {                            // tree of p is larger (or same), attach q to p.
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }

        count--;
    }

    @TimeComplexity(
            op = "union",
            value = "O(lgN)"
    )
    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @TimeComplexity(
            op = "union",
            value = "O(lgN)"
    )
    @Override
    public int find(int p) {
        checkIndex(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];  // path compression, reduce the height of the tree.
            p = parent[p];
        }
        return p;
    }

    @Override
    public int count() {
        return count;
    }

    private void checkIndex(int i) {
        if (i < 0 || i > parent.length - 1)
            throw new IllegalArgumentException("index " + i + " is out of bounds.");
    }
}
