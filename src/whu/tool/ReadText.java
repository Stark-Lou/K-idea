package whu.tool;

import whu.entity.KLineGraph;
import whu.entity.SingleDayKLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadText {
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ReadText(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 将文件夹路径下的所有txt读取成KLineGraph
     * @return
     * @throws Exception
     */
    public KLineGraph read() throws Exception {
        if(filePath != null && !filePath.isEmpty()) {
            List<Object[]> list = new ArrayList<>();
            File file = new File(filePath);
            KLineGraph kLineGraph = new KLineGraph();
            boolean firstLine = true;   //第一行标记
            float maxOpen = 0;
            float minOpen = 0;
            boolean initMaxMin = true;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
                String s = null;
                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    if(firstLine){
                        String[] content = s.split("\t");   //Tab划分
                        if(content.length <= 2){
                            content = s.split(" ");         //否则空格划分
                        }
                        kLineGraph.setId(Integer.valueOf(content[0]));
                        kLineGraph.setName(content[1]);
                        System.out.println("reading:" + content[1]);
                        firstLine = false;
                    }
                    if(isContainsChinese(s))//如果包含中文则读下一行;
                    {
                        s = br.readLine();
                        continue;
                    }
                    String data[] = s.split("\\t");
                    if(data.length == 7){
                        SingleDayKLine singleDayKLine = new SingleDayKLine();
                        singleDayKLine.setDate(data[0]);
                        float open = Float.valueOf(data[1]);
                        if(initMaxMin){
                            maxOpen = open;
                            minOpen = open;
                            initMaxMin = false;
                        }else if(open > maxOpen)
                            maxOpen = open;
                        else if(open < minOpen)
                            minOpen = open;
                        singleDayKLine.setOpen(open);
                        singleDayKLine.setHighest(Float.valueOf(data[2]));
                        singleDayKLine.setLowest(Float.valueOf(data[3]));
                        singleDayKLine.setClose(Float.valueOf(data[4]));
                        singleDayKLine.setVolume(Long.valueOf(data[5]));
                        list.add(singleDayKLine.formatForJson());
//                        System.out.println("date:" + data[0] + "; KP:" + data[1] + "; ZG:" + data[2]+ "; ZD:" + data[3]
//                                + "; SP:" + data[4] + "; CJL:" + data[5] + "; KP:" + data[6]);
                    }else
                        System.out.println("格式错误！");
                }
                kLineGraph.setContent(list);
                kLineGraph.setMaxOpen(maxOpen);
                kLineGraph.setMinOpen(minOpen);
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return kLineGraph;
        }else
            throw new Exception("No file!");
    }

    /**
     * 从文件路径中读出K线图文件信息
     * @return
     * @throws Exception
     */
    public StockInfo readStockInfo() throws Exception {
        if(filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            boolean firstLine = true;   //第一行标记
            StockInfo fi = new StockInfo();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
                String s = null;
                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    if(firstLine){
                        String[] content = s.split("\t");   //Tab划分
                        if(content.length <= 2){
                            content = s.split(" ");         //否则空格划分
                        }
                        firstLine = false;
                        fi.setFileName(file.getName());
                        fi.setStockName(content[1]);
                    }
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fi;
        }else
            throw new Exception("No file!");
    }

    public class StockInfo {
        String fileName;
        String stockName;

        public StockInfo() {
        }

        public StockInfo(String fileName, String stockName) {
            this.fileName = fileName;
            this.stockName = stockName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getStockName() {
            return stockName;
        }

        public void setStockName(String stockName) {
            this.stockName = stockName;
        }
    }

    private String regEx = "[\u4e00-\u9fa5]";
    private Pattern pat = Pattern.compile(regEx);

    private boolean isContainsChinese(String str)
    {
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())    {
            flg = true;
        }
        return flg;
    }

}
