package whu.entity;

import whu.tool.AlgImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * K线图
 */
public class KLineGraph {
    private String name;            //股票名称
    private int id;                 //股票代码
    private float similarity;       //相似度
    private List<Object[]> content;       //股票数据

    private float maxOpen;          //开盘最高值
    private float minOpen;          //开盘最低值

    public KLineGraph() {
    }

    /**
     * 欧氏距离法 Euclidean Distance
     * @param a KLineGraph
     * @param b KLineGraph
     * @return 相似度,值越小相似度越高
     */
    public static float calculateSimilarity(KLineGraph a, KLineGraph b,
                                            String algorithmWeight,String contrastContent){
        if((a.maxOpen - a.minOpen) == 0)
            return 100;
        int[] algWeight = getAlgWeight(algorithmWeight);
        //对比内容，依次序为开盘价、收盘价、成交量、最高价、最低价
        int[] conContents = getConContent(contrastContent);
        float radio = ((b.maxOpen - b.minOpen)/(a.maxOpen - a.minOpen));
        System.out.println(a.name + " VS " + b.name + ". radio:" + radio);
        List<SingleDayKLine> listA = new ArrayList<>();//对象A
        List<SingleDayKLine> listB = new ArrayList<>();//对象B
        for (Object[] o : a.content) {
            listA.add(SingleDayKLine.fromObjectArr(o));
        }
        for (Object[] o : b.content) {
            listB.add(SingleDayKLine.fromObjectArr(o));
        }
        //TODO 截取相同的时间段进行比较，现在挑选的数据时间段完全一致
        int a_idx = 0;  //记录listA的位置
        int count = 0;
        float sumED = 0;    //欧式距离总和
        float sumCS = 0;    //余弦相似度总和
        for(SingleDayKLine sdk:listB){
            //当listA遍历完成则跳过循环
            if(a_idx >= listA.size())
                break;
            //找到同一日期的数据进行比较
            int aDate = Integer.valueOf(listA.get(a_idx).getDate().replaceAll("/",""));
            int bDate = Integer.valueOf(sdk.getDate().replaceAll("/",""));
            if(aDate != bDate){
                //如果B中目标日期大于A当前目标的日期，而且A没有超出下标范围
                while(bDate > aDate && a_idx<listA.size() - 1){
                    a_idx++;
                    aDate = Integer.valueOf(listA.get(a_idx).getDate().replaceAll("/",""));
                }
                //如果B中目标时间小于A当前目标时间，则跳过当前目标
                if (bDate < aDate) break;
            }
            //然后进行距离计算
            if(algWeight[0] > 0)
                sumED += Math.abs((sdk.getOpen() - listA.get(a_idx).getOpen() * radio) * conContents[0])
                        + Math.abs((sdk.getClose() - listA.get(a_idx).getClose() * radio) * conContents[1])
                        + Math.abs((sdk.getVolume() - listA.get(a_idx).getVolume()) * conContents[2])
                        + Math.abs((sdk.getHighest() - listA.get(a_idx).getHighest() * radio) * conContents[3])
                        + Math.abs((sdk.getLowest() - listA.get(a_idx).getLowest() * radio) * conContents[4]);
            //余弦相似度计算
            if(algWeight[1]>0)
                sumCS += getSumCS(conContents, radio, listA.get(a_idx), sdk);
            count ++;
        }
        if(count == 0)
            return 100;
        return (sumED * algWeight[0] + sumCS * algWeight[1])/(algWeight[0] + algWeight[1] + algWeight[2]) /count;
    }

    /**
     * 计算余弦相似度，越接近1(最大值为1)越相似，为了与距离法(值越小代表越相似)保持一致,故返回1-sumCS
     * @param conContents
     * @param radio
     * @param sdk
     * @return
     */
    private static float getSumCS(int[] conContents,float radio, SingleDayKLine a, SingleDayKLine sdk) {
        float[] sdkFs = {0,0,0,0,0};
        float[] aFs = {0,0,0,0,0};
        float result = 0;
        if(conContents[0] > 0){
            sdkFs[0] = sdk.getOpen();
            aFs[0] = a.getOpen() * radio;
        }
        if(conContents[1] > 0){
            sdkFs[1] = sdk.getClose();
            aFs[1] = a.getClose() * radio;
        }
        if(conContents[2] > 0){
            sdkFs[2] = sdk.getVolume();
            aFs[2] = a.getVolume();
        }
        if(conContents[3] > 0){
            sdkFs[3] = sdk.getHighest();
            aFs[3] = a.getHighest() * radio;
        }
        if(conContents[4] > 0){
            sdkFs[4] = sdk.getLowest();
            aFs[4] = a.getLowest() * radio;
        }
        try {
            result = AlgImp.calCosineSimilarity(sdkFs,aFs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1 - result;
    }

    private static int[] getConContent(String contrastContent) {
        int[] conContents ={1,0,0,0,0}; //默认对比内容
        if(contrastContent.contains("open"))
            conContents[0] = 1;
        else
            conContents[0] = 0;
        if(contrastContent.contains("close"))
            conContents[1] = 1;
        else
            conContents[1] = 0;
        if(contrastContent.contains("volume"))
            conContents[2] = 1;
        else
            conContents[2] = 0;
        if(contrastContent.contains("highest"))
            conContents[3] = 1;
        else
            conContents[3] = 0;
        if(contrastContent.contains("lowest"))
            conContents[4] = 1;
        else
            conContents[4] = 0;
        return conContents;
    }

    private static int[] getAlgWeight(String algorithmWeight) {
        String[] algWeights = algorithmWeight.split(",");
        //各个算法的权重
        int[] algWeight = {1,0,0};  //默认权重分配
        for(int i=0;i<3 && i<algWeights.length;i++){
            if(!algWeights[i].isEmpty()){
                algWeight[i] = Integer.valueOf(algWeights[i]);
            }
        }
        //如果算法权重全被设为0，则修正为1，0，0
        if (algWeight[0] == 0 && algWeight[1] == 0 && algWeight[2] == 0) {
            algWeight[0] = 1;
        }
        return algWeight;
    }

    /**
     * 对数据进行排序
     * @param list
     * @return
     */
    public static List<KLineGraph> sortBySimilarity(List<KLineGraph> list){
        Collections.sort(list, (o1, o2) -> {
            float sub = o2.getSimilarity() - o1.getSimilarity();
            int result = 0;
            if(sub>0)  result = 1;
            else if(sub<0) result = -1;
            else
                result = o2.getName().compareTo(o1.getName());
            return result;
        });
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    public List<Object[]> getContent() {
        return content;
    }

    public void setContent(List<Object[]> content) {
        this.content = content;
    }

    public float getMaxOpen() {
        return maxOpen;
    }

    public void setMaxOpen(float maxOpen) {
        this.maxOpen = maxOpen;
    }

    public float getMinOpen() {
        return minOpen;
    }

    public void setMinOpen(float minOpen) {
        this.minOpen = minOpen;
    }
}
