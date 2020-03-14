
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件处理类
 * 判断文件名是否合法
 * 递归获取所有合法文件路径
 */
public class FileWorker {

    private final String DEFAULT_REGEX =
            "\\w+\\.(java|c|[cC]pp|CPP|(py|PY)|txt|TXT|docx|cs|html|js)\\b";


    public List<String> getFiles(File file,String regex){
        List<String> list = new ArrayList<>();
        File[] files = file.listFiles();
        if (files == null){
            return list;
        }
        for (File iFile:files){
            if (iFile.isDirectory()){
                list.addAll(getFiles(iFile,regex));
            }
            else if (correctFile(iFile.getName(),regex)){
                list.add(iFile.getPath());
            }
        }
        return list;
    }


    public boolean correctFile(String fileName,String regex){
        String mRegex;
        if (regex == null) mRegex = DEFAULT_REGEX;
        else mRegex = regex;
        Pattern fileCorrectPattern = Pattern.compile(mRegex);
        Matcher fileCorrectMatcher = fileCorrectPattern.matcher(fileName);
        return fileCorrectMatcher.matches();
    }
}
