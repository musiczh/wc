
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class wc {

    public static void main(String[] args) {


        //如果只有一个字符串则直接返回
        if (args.length <= 1 ) {
            System.out.println("the command is invalid");
            return;
        }


        List<String> filesList = new ArrayList<>(); //记录所有符合的文件
        String command;                             //记录统计命令
        FileWorker fileWorker = new FileWorker();   //处理文件类


        //根据是-s还是普通指令来分别获取要处理的文件
        if (args.length == 2){
            if (fileWorker.correctFile(args[1],null)){
                filesList.add(args[1]);
                command = args[0];
            }else{
                System.out.println("the file is unsupported type");
                return;
            }
        }else if (args[0].equals("-s")){
                command = args[1];
                String regex = args[2];
                regex = regex.replace(".","\\w*\\.");
                System.out.println(regex);
                filesList = fileWorker.getFiles(new File(System.getProperty("user.dir")),regex);
        }else{
            System.out.println("the file is unsupported type");
            return;
        }


        //把所有文件遍历输出
        if (filesList.size()==0){
            System.out.println("Not found file");
        }else{
            for (String str:filesList){
                System.out.println("\n"+str+":");
                outPutData(str,command);
            }
        }


    }


    //输出数据
    private static void outPutData(String fileName,String command){
        WcCountWorker countWorker;

        //创建计算类；创建失败则文件不存在
        try {
            countWorker = new WcCountWorker(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("the file is not exist");
            return;
        }

        //根据指令执行输出操作
        try{
            switch (command){
                case "-c":
                    System.out.println("Chars num: "+countWorker.getCharsCount());
                    break;

                case "-w":
                    System.out.println("Words num: "+countWorker.getWordsCount());
                    break;

                case "-l":
                    System.out.println("Lines num: "+countWorker.getLinesCount());
                    break;

                case "-a":
                    HashMap<String,Integer> hashMap = countWorker.getMoreCount();
                    System.out.println("blank lines num: "+hashMap.get(WcCountWorker.BLANK_LINES));
                    System.out.println("code lines num: "+hashMap.get(WcCountWorker.CODE_LINES));
                    System.out.println("note lines num: "+hashMap.get(WcCountWorker.NOTE_LINES));
                    break;

                default:
                    System.out.println("the command\""+command+ "\"is invalid");
                    break;
            }
        }catch (IOException e) {
            System.out.println("Sorry.An IOException has happened");
        }
    }
}
