package inter;

import java.io.IOException;
import java.util.HashMap;

/**
 * 计算工具类的接口
 */
public interface WcUtilInter {
    int getLinesCount() throws IOException;
    int getWordsCount() throws IOException;
    int getCharsCount() throws IOException;
    HashMap<String,Integer> getMoreCount() throws IOException;

}
