package whu.entity;

/**
 * 日K线信息
 */
public class SingleDayKLine {
    private String date;
    private float open;
    private float close;
    private float lowest;
    private float highest;
    private long volume;

    public SingleDayKLine(){

    }

    public SingleDayKLine(String date, float open, float close,
                          float lowest, float highest, long volume) {
        this.date = date;
        this.open = open;
        this.close = close;
        this.lowest = lowest;
        this.highest = highest;
        this.volume = volume;
    }

    /**
     * 重新封装对象为了向ECharts传递不带Key的JSON数据
     * @return
     */
    public Object[] formatForJson(){
        Object[] o = new Object[6];
        o[0] = date;//时间
        o[1] = open;//开盘
        o[2] = close;//收盘
        o[3] = lowest;//最低
        o[4] = highest;//最高
        o[5] = volume;//成交量
        return o;
    }

    /**
     * 传入一个数组还原成SingleDayKLine对象
     * 传入对象应符合时间、开盘、收盘、最低、最高、成交量的顺序
     * @param o
     * @return
     */
    public static SingleDayKLine fromObjectArr(Object[] o){
        if(o.length == 6){
            return new SingleDayKLine(o[0].toString(),
                    Float.valueOf(o[1].toString()),
                    Float.valueOf(o[2].toString()),
                    Float.valueOf(o[3].toString()),
                    Float.valueOf(o[4].toString()),
                    Long.valueOf(o[5].toString()));
        }else{
            try {
                throw new Exception("错误的Object数组");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public String toString() {
        return "SingleDayKLine{" +
                "date='" + date + '\'' +
                ", open=" + open +
                ", close=" + close +
                ", lowest=" + lowest +
                ", highest=" + highest +
                ", volume=" + volume +
                '}';
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getLowest() {
        return lowest;
    }

    public void setLowest(float lowest) {
        this.lowest = lowest;
    }

    public float getHighest() {
        return highest;
    }

    public void setHighest(float highest) {
        this.highest = highest;
    }
}
