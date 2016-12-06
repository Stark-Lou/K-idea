package whu.test;

import whu.ts.math.filter.MovingAverageFilter;
import whu.ts.math.ml.distance.DynamicTimeWarpingDistance;
import whu.ts.math.normalization.MinMaxNormalizer;

public class Test {
    public static void main(String[] args) {
        double[] d = new double[1];
        System.out.println(d[2]);
    }

    private static void test() {
        double[] v1 = {-0.710518, -1.18332, -1.372442, -1.593083, -1.467002, -1.372442, -1.08876, 0.045967, 0.928532, 1.086133, 1.275254, 0.960052, 0.61333, 0.014447, -0.647477, -0.269235, -0.206195, 0.61333, 1.369815, 1.464375, 1.054613, 0.58181, 0.172048, -0.269235};
        double[] v2 = {-0.993009, -1.426787, -1.579884, -1.605401, -1.630917, -1.375754, -1.018526, -0.355102, 0.716583, 1.201393, 1.124844, 1.048295, 0.793132, 0.46142, 0.486936, 0.563485, 0.614518, 0.308322, 0.257289, 1.099327, 1.048295, 0.691066, -0.048906, -0.380618};
        double[] v3 = {1.319067, 0.569774, 0.195128, -0.085856, -0.179518, -0.27318, -0.085856, -1.397118, -1.116134, -0.741487, 0.007805, -0.085856, 0.007805, -0.460503, -0.554164, -0.741487, -0.741487, -0.741487, -1.116134, -0.460503, 0.476113, 2.349344, 2.255683, 1.600052};

        //滤波
        MovingAverageFilter ma = new MovingAverageFilter(2);
        v1 = ma.filter(v1);
        v2 = ma.filter(v2);
        v3 = ma.filter(v3);

        double tolerance = 0.05; //5%
        MinMaxNormalizer normalizer = new MinMaxNormalizer();
        //动态时间规整
        DynamicTimeWarpingDistance dtw = new DynamicTimeWarpingDistance(tolerance, normalizer);

        System.out.println(dtw.compute(v1, v2));
        System.out.println(dtw.compute(v2, v3));
        System.out.println(dtw.compute(v1, v3));

        // => v1 and v2 are similar, v3 is different from the other two
    }
}
