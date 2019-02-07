package io.imulab.alg;

import com.google.gson.Gson;
import io.imulab.alg.uf.QuickFind;
import io.imulab.alg.uf.QuickUnion;
import io.imulab.alg.uf.UnionFind;
import io.imulab.alg.uf.WeightedQuickUnion;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UnionFindTest {

    private static class Data {
        int total = 0;
        List<Pair> data = new ArrayList<>();

        private static class Pair {
            int p;
            int q;
        }
    }

    private static Data testData;

    @BeforeClass
    public static void loadData() {
        Gson gson = new Gson();
        String tinyUnionFindJson = "{\n" +
                "  \"total\": 10,\n" +
                "  \"data\": [\n" +
                "    {\"p\": 4, \"q\": 3},\n" +
                "    {\"p\": 3, \"q\": 8},\n" +
                "    {\"p\": 6, \"q\": 5},\n" +
                "    {\"p\": 9, \"q\": 4},\n" +
                "    {\"p\": 2, \"q\": 1},\n" +
                "    {\"p\": 8, \"q\": 9},\n" +
                "    {\"p\": 5, \"q\": 0},\n" +
                "    {\"p\": 7, \"q\": 2},\n" +
                "    {\"p\": 6, \"q\": 1},\n" +
                "    {\"p\": 1, \"q\": 0},\n" +
                "    {\"p\": 6, \"q\": 7}\n" +
                "  ]\n" +
                "}";
        testData = gson.fromJson(tinyUnionFindJson, Data.class);
        Assert.assertEquals(10, testData.total);
        Assert.assertEquals(11, testData.data.size());
    }

    @Test
    public void testQuickFind() {
        UnionFind qf = new QuickFind(testData.total);
        testData.data.forEach(pair -> qf.union(pair.p, pair.q));
        doAssert(qf);
    }

    @Test
    public void testQuickUnion() {
        UnionFind qf = new QuickUnion(testData.total);
        testData.data.forEach(pair -> qf.union(pair.p, pair.q));
        doAssert(qf);
    }

    @Test
    public void testWeightedQuickUnion() {
        UnionFind qf = new WeightedQuickUnion(testData.total);
        testData.data.forEach(pair -> qf.union(pair.p, pair.q));
        doAssert(qf);
    }

    private void doAssert(UnionFind uf) {
        // group 4-3-8-9
        Assert.assertTrue(uf.connected(4, 3));
        Assert.assertTrue(uf.connected(4, 8));
        Assert.assertTrue(uf.connected(4, 9));
        Assert.assertTrue(uf.connected(3, 8));
        Assert.assertTrue(uf.connected(3, 9));
        Assert.assertTrue(uf.connected(8, 9));

        // group 0-1-2-5-6-7
        Assert.assertTrue(uf.connected(0, 1));
        Assert.assertTrue(uf.connected(0, 2));
        Assert.assertTrue(uf.connected(0, 5));
        Assert.assertTrue(uf.connected(0, 6));
        Assert.assertTrue(uf.connected(0, 7));
        Assert.assertTrue(uf.connected(1, 2));
        Assert.assertTrue(uf.connected(1, 5));
        Assert.assertTrue(uf.connected(1, 6));
        Assert.assertTrue(uf.connected(1, 7));
        Assert.assertTrue(uf.connected(2, 5));
        Assert.assertTrue(uf.connected(2, 6));
        Assert.assertTrue(uf.connected(2, 7));
        Assert.assertTrue(uf.connected(5, 6));
        Assert.assertTrue(uf.connected(5, 7));
        Assert.assertTrue(uf.connected(6, 7));

        // not connected
        Assert.assertFalse(uf.connected(4, 5));
    }
}
