package Package1;

import com.hankcs.hanlp.HanLP;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main{
    //合并关键词
    private static List<String> mergeList(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<String>();
        result.addAll(list1);
        result.addAll(list2);
        //去掉重复元素
        for(int i = 0; i < result.size() - 1; i++){
            for(int j = result.size() - 1; j > i; j--){
                if(result.get(j).equals(result.get(i))){
                    result.remove(j);
                }
            }
        }
        return result;
    }
    //统计词频
    private static int[] Frequency(List<String> allWords, List<String> sentWords) {
        int[] result = new int[allWords.size()];
        for (int i = 0; i < allWords.size(); i++) {
            result[i] = Collections.frequency(sentWords, allWords.get(i));
        }
        return result;
    }
    //计算余弦值（相似度）
    public static String getCosine(int[] statistic1, int[] statistic2) {
        double dividend = 0;
        double divisor1 = 0;
        double divisor2 = 0;
        for (int i = 0; i < statistic1.length; i++) {
            dividend += statistic1[i] * statistic2[i];
            divisor1 += Math.pow(statistic1[i], 2);
            divisor2 += Math.pow(statistic2[i], 2);
        }
        //计算余弦值
        double cosine = dividend / (Math.sqrt(divisor1) * Math.sqrt(divisor2));
        //转换为百分比
        DecimalFormat df = new DecimalFormat("0.00%");//小数点后保留两位
        return df.format(cosine);
    }
    public static String Similarity(String path1,String path2) throws IOException {
        //读取文件路径
        String content1 = Files.readString(Paths.get(path1));
        String content2 = Files.readString(Paths.get(path2));
        //获取关键词
        List<String> keywordList1 = HanLP.extractKeyword(content1, 200);
        List<String> keywordList2 = HanLP.extractKeyword(content2, 200);
        //合并关键词
        List<String> total = mergeList(keywordList1, keywordList2);
        //计算词频
        int[] list1 = Frequency(total, keywordList1);
        int[] list2 = Frequency(total, keywordList2);
        //计算余弦值（相似度）
        return getCosine(list1, list2);
    }
    public static void main(String[] args)throws IOException{
        //由输入路径获取文件名
        File tempFile1 =new File( args[0].trim());
        File tempFile2 =new File( args[1].trim());
        String fileName1 = tempFile1.getName();
        String fileName2 = tempFile2.getName();
        //查重结果形式
        String result = fileName1 + "和" + fileName2 + "的相似度：" + Similarity(args[0],args[1]) + "\r\n";
        System.out.println(fileName1 + "和" + fileName2 + "的相似度：" + Similarity(args[0],args[1]));
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[2],true)));
        out.write(result);//将查重结果写入答案文件中
        out.close();//关闭文件
    }
}