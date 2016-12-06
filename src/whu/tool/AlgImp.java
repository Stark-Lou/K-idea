package whu.tool;

/**
 * Created by Lou on 2016/12/4.
 */
public class AlgImp {
    public static float calCosineSimilarity(float[] a,float[] b) throws Exception {
        float result = 0;
        if(a.length != b.length)
            throw new Exception("两个数组长度不一致！");
        else {
            int length = a.length;
            float den = 0;//分母
            float fraA = 0;
            float fraB = 0;
            for(int i=0;i<length;i++){
                den += (a[i] * b[i]);
                fraA += Math.pow(a[i],2);
                fraB += Math.pow(b[i],2);
            }

            float fra = (float) (Math.pow(fraA,0.5) * Math.pow(fraB,0.5));
            System.out.println("den:" + den +";fra:"+fra);
            if(fra != 0)
                result = den/fra;
        }
        return result;
    }
}
