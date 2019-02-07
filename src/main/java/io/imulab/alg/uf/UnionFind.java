package io.imulab.alg.uf;

/**
 * A data structure which can dynamically determine whether two points are connected.
 */
public interface UnionFind {

    /**
     * Add connection between two points.
     *
     * @param p index for a point, must be between 0 and N-1 inclusive.
     * @param q index for another point, must be between 0 and N-1 inclusive.
     */
    void union(int p, int q);

    /**
     * Queries whether the two points are connected.
     *
     * @param p index for a point, must be between 0 and N-1 inclusive.
     * @param q index for another point, must be between 0 and N-1 inclusive.
     * @return  true when two points are connected; false otherwise.
     */
    boolean connected(int p, int q);

    /**
     * Get the component identifier for a point.
     *
     * @param p index for a point, must be between 0 and N-1 inclusive.
     * @return  the component identifier for the point.
     */
    int find(int p);

    /**
     * Get the total number of components.
     *
     * @return  the number of components, non-negative.
     */
    int count();
}
