import inter.WcUtilInter;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WcCountWorker implements WcUtilInter {
    private BufferedReader mBufferedReader;
    public static final String BLANK_LINES = "blank+lines";
    public static final String CODE_LINES = "code_lines";
    public static final String NOTE_LINES = "note_lines";

    public WcCountWorker(String file) throws FileNotFoundException {
        mBufferedReader = new BufferedReader(new FileReader(file));
    }

    /**
     * 获取行数的方法
     * @return int 行数
     * @throws IOException 读取文件过程中可能发生阻塞
     */
    @Override
    public int getLinesCount() throws IOException {
        int linesNum = 0;
        while (mBufferedReader.readLine()!=null) linesNum++;
        return linesNum;
    }

    /**
     * 获取单词个数方法
     * @return int 单词个数
     * @throws IOException 读取文件过程中可能发生阻塞
     */
    @Override
    public int getWordsCount() throws IOException {
        int wordsNum = 0;
        String str;
        while (( str = mBufferedReader.readLine())!=null){
            Pattern pattern = Pattern.compile("\\b[a-zA-Z]+\\b");
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) wordsNum++;
        }
        return wordsNum;
    }

    /**
     * 统计可显示字符个数
     * @return int 字符个数
     * @throws IOException 读取流过程中可能会发生阻塞
     */
    @Override
    public int getCharsCount() throws IOException {
        int charsNum = 0;
        String str;
        while (( str = mBufferedReader.readLine())!=null){

            Pattern pattern = Pattern.compile("\\S");
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) charsNum++;
        }
        return charsNum;
    }


    @Override
    public HashMap<String, Integer> getMoreCount() throws IOException {
        int blankLines = 0;
        int codeLines = 0;
        int noteLines = 0;
        boolean isNote = false;

        String str;
        while ((str = mBufferedReader.readLine())!=null){
            str = str.trim();
            if (str.length()>1){
                codeLines++;
                if (isNote) noteLines++;


                if (str.substring(0,2).equals("/*")){
                    isNote = true;
                    noteLines++;
                }else if(str.substring(str.length()-2).equals("*/")&&isNote){
                    isNote = false;
                }else if (str.contains("//")){
                    noteLines++;
                }

            } else blankLines++;

        }
        HashMap<String,Integer> hashMap = new HashMap<>();
        hashMap.put(BLANK_LINES,blankLines);
        hashMap.put(CODE_LINES,codeLines);
        hashMap.put(NOTE_LINES,noteLines);
        return hashMap;
    }
}
