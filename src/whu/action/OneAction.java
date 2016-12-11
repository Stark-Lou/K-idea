package whu.action;

import net.sf.json.JSONObject;
import whu.entity.KLineGraph;
import whu.entity.SystemInfo;
import whu.tool.ReadText;

import java.io.*;
import java.util.*;

/**
 * Created by Lou on 2016/11/15.
 */
public class OneAction {
    private static final String DATA_PATH = "D:\\Homework\\TestData\\";

    private JSONObject result;
    //分页查询
    private String rows;
    private String page;
    private String algorithmWeight;
    private String targetFile;
    private String contrastContent;

    /**
     * 获取K线图数据内容
     * @return
     */
    public String getKLineGraphJSON(){
        System.out.println(algorithmWeight +";"+targetFile+","+contrastContent);
        List<File> files = getAllFile(DATA_PATH);
        int total = files.size();
        List<KLineGraph> list = new ArrayList<>();
        ReadText readText = new ReadText(DATA_PATH + targetFile);
        int targetId = 600004;//默认以白云机场为测试用例
        try {
            KLineGraph targetK = readText.read();
            targetId = targetK.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int targetIdx = 0;
        for(int i=0;i<total;i++){
            readText = new ReadText(files.get(i).getAbsolutePath());
            try {
                KLineGraph k = readText.read();

                if(k.getId() == targetId){
                    targetIdx = i;
                }
                list.add(k);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        KLineGraph target = list.get(targetIdx);
        for(KLineGraph k:list){
            k.setSimilarity(100 - KLineGraph.calculateSimilarity(k,target
                ,algorithmWeight,contrastContent));
        }
        KLineGraph.sortBySimilarity(list);
        SystemInfo.setList(list);
        String content = target.getName() + " " + target.getId() + "\r\n";
        for(KLineGraph k:list){
            content += k.getId() +" " + k.getName() +" " + k.getSimilarity() + "\r\n";
        }
        try {
            resultToTxt(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<KLineGraph> newList = list.subList(0,6);
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("total", total);
        jsonMap.put("rows", newList);
        result = JSONObject.fromObject(jsonMap);
        return "success";
    }

    public String getKLineGraphInPage(){
        int intPage = Integer.parseInt((page == null || page.equals("0")) ? "1"
                : page);
        int number = Integer.parseInt((rows == null || rows.equals("0")) ? "6"
                : rows);
        List<KLineGraph> list = SystemInfo.getList();
        Map<String, Object> jsonMap = new HashMap<>();
        if(list != null){
            int total = list.size();
            int first = (intPage - 1) * number;
            int max = intPage * number < total ? number : total;
            jsonMap.put("total", total);
            jsonMap.put("rows", list.subList(first,first+max));
        }
        result = JSONObject.fromObject(jsonMap);
        return "success";
    }

    public String getStockInfoJSON(){
        List<File> files = getAllFile(DATA_PATH);
        List<ReadText.StockInfo> out = new ArrayList<>();
        for(File file:files){
            ReadText readText = new ReadText(file.getAbsolutePath());
            try {
                out.add(readText.readStockInfo());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("value", out);
        result = JSONObject.fromObject(jsonMap);
        return "success";
    }

    /**
     * 获得指定路径下的所有txt文件，不遍历子文件夹
     * @param path 指定的目录
     * @return 所有txt文件
     */
    public List<File> getAllFile(String path) {
        List<File> fileList = new ArrayList<>();
        File root = new File(path);
        //如果路径是一个目录而且子文件是txt格式的则接受
        if(root.isDirectory() && root.listFiles()!= null){
            //noinspection ConstantConditions
            File[] files = root.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".txt");
                }
            });
            fileList = Arrays.asList(files);
        }else {
            try {
                throw new Exception("指定路径不是一个目录, path=" + path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileList;
    }

    public static void resultToTxt(String content) throws IOException {

        String filePath = "D:\\Homework\\ResultRead_1.txt";
        int i = 1;
        File file = new File(filePath);
        //避免覆盖文件
        while(file.exists()) {
            file = new File("D:\\Homework\\ResultRead_"+i+".txt");
            i++;
        }
        file.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        Date date = new Date();
        System.out.println("writing to file:" + file.getAbsolutePath());
        bw.write(date.toString() + "\r\n" + content);
        bw.close();
    }

    public String getAlgorithmWeight() {
        return algorithmWeight;
    }

    public void setAlgorithmWeight(String algorithmWeight) {
        this.algorithmWeight = algorithmWeight;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public String getContrastContent() {
        return contrastContent;
    }

    public void setContrastContent(String contrastContent) {
        this.contrastContent = contrastContent;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
