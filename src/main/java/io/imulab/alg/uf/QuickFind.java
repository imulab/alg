package io.imulab.alg.uf;

import io.imulab.alg.doc.Stateful;
import io.imulab.alg.doc.TimeComplexity;

/**
 * An {@link UnionFind} implementation that ensures connected points share the same component id. It does so by
 * rewriting component ids when {@link QuickFind#union(int, int)} is invoked.
 *
 * Performance characteristics:
 * <ol>
 *     <li>{@link #find(int)} is quick: O(1)</li>
 *     <li>{@link #QuickFind(int)} is linear: O(N)</li>
 *     <li>{@link #union(int, int)} is linear: O(N)</li>
 *     <li>Overall, it is slow to process N points, occurs cost of O(N^2)</li>
 * </ol>
 */
@TimeComplexity(
        op = "process N points",
        value = "O(N^2)"
)
@Stateful
public class QuickFind implements UnionFind {

    /**
     * Data structure for {@link QuickFind}. This array is index by the points identifier,
     * and stores the component identifier.
     */
    private final int[] id;

    /**
     * The total number of components.
     */
    private int count;

    @TimeComplexity(
            op = "initialize",
            value = "O(N)"
    )
    public QuickFind(int N) {
        assert N > 0;

        /*
        Initialize the component id array.
        Every point is its own component at the start.
         */
        this.id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;

        this.count = N;
    }

    @TimeComplexity(
            op = "union",
            value = "O(N)"
    )
    @Override
    public void union(int p, int q) {
        int pid = find(p);
        int qid = find(q);

        /*
        Rewrite component id of the p group to
        that of the q group.
         */
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid)
                id[i] = qid;
        }

        count--;
    }

    @Override
    public boolean connected(int p, int q) {
        /*
        Two points are connected as long as
        they share the same component id.
         */
        return find(p) == find(q);
    }

    @TimeComplexity(
            op = "find",
            value = "O(1)"
    )
    @Override
    public int find(int p) {
        checkIndex(p);
        return id[p];
    }

    @Override
    public int count() {
        return this.count;
    }

    private void checkIndex(int i) {
        if (i < 0 || i > id.length - 1)
            throw new IllegalArgumentException("index " + i + " is out of bounds.");
    }
}
