package lly.h5.android.test.hybrid;

import android.os.Handler;
import android.webkit.WebView;

import org.json.JSONObject;

/**
 * Created by leon on 16/5/11.
 */
public interface AsynServiceHandler extends Runnable{
    public void setService(String service);
    public void setAction(String action);
    public void setArgs(JSONObject args);
    public void setWebView(WebView webView);
    public void setRequestId(String id);

    public void setMessageHandler(Handler handler);
}
