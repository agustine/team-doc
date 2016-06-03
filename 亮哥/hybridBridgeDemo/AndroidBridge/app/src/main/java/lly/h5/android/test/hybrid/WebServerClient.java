package lly.h5.android.test.hybrid;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import lly.h5.android.test.DroidH5Activity;
import lly.h5.android.test.hybrid.plugin.base.IPlugin;

/**
 * Created by leon on 16/5/11.
 * https://developer.android.com/reference/android/webkit/WebViewClient.html
 */
public class WebServerClient extends WebViewClient {

    private DroidH5Activity hybridActivity;
    public WebServerClient(DroidH5Activity hybridActivity){
        this.hybridActivity = hybridActivity;
    }

    private int threadIdCounter = 0;

    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    //交互请求ID，防止异步调用的回调顺序被打乱
    private  String requestId;
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("llyhybrid")) {
            try {
                //获取本次调用的cmd 和 args
                JSONObject msg = new JSONObject(AppJavaInterface.getRequestMessageOnce());

                requestId = msg.getString(IPlugin.REQUESTID);
                String service = msg.getString(IPlugin.SERVICE);
                String action = msg.getString(IPlugin.ACTION);
                JSONObject arg = msg.getJSONObject(IPlugin.ARGS);

                return startAsyncServiceHandler(arg, service, action);
            } catch (JSONException e1) {
                e1.printStackTrace();
                return false;
            }
        }
        view.loadUrl(url);
        return true;
    }

    private boolean startAsyncServiceHandler(JSONObject arg, String service, String action) {
        try {
            AsynServiceHandler asyn = new AsynServiceHandlerImpl(hybridActivity.mPluginManager);
            asyn.setService(service);
            asyn.setAction(action);
            asyn.setArgs(arg);
            asyn.setWebView(hybridActivity.mWebView);
            asyn.setMessageHandler(myHandler);
            asyn.setRequestId(requestId);
            Thread thread = new Thread(asyn, "asyn_"
                    + (threadIdCounter++));
            thread.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

}
