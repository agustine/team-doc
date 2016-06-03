package lly.h5.android.test.hybrid;

import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.Hashtable;


/**
 * 与Javascript交互的对象
 * @author samland, 2013/11/11
 * @version 1.0
 */
public class AppJavaInterface implements java.io.Serializable {
    public static final String HYBRID_KEY  = "LyWebProxy";
    private static final long serialVersionUID = 1898316400768814976L;
    private static ArrayList<String> REQUEST_MESSAGES = new ArrayList<String>();

    /**
     * 从页面写入参数
     * @param cmds
     * @param id
     */
    @JavascriptInterface
    public void setRequestMessage(String msg) {
        REQUEST_MESSAGES.add(msg);
    }


    /**
     * 供WebView读取参数，只取一次即 清除
     * @param id
     * @return
     */
    public static String getRequestMessageOnce() {
        String result = REQUEST_MESSAGES.get(0);
        REQUEST_MESSAGES.remove(0);
        return result;
    }
}
