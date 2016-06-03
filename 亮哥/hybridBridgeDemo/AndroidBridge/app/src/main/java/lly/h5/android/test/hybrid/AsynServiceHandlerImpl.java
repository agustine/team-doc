package lly.h5.android.test.hybrid;

import android.os.Handler;
import android.webkit.WebView;

import org.json.JSONObject;

import lly.h5.android.test.hybrid.plugin.base.PluginManager;
import lly.h5.android.test.hybrid.plugin.base.PluginNotFoundException;

/**
 * Created by leon on 16/5/11.
 */
public class AsynServiceHandlerImpl implements AsynServiceHandler {

    private String service;
    private String action;
    private JSONObject args;
    private WebView webView;
    private Handler handler;
    private String requestId;

    private PluginManager pluginManager;

    public AsynServiceHandlerImpl(PluginManager pluginManager){
        this.pluginManager = pluginManager;
    }

    @Override
    public void run() {

        try {
            final String responseBody = pluginManager.exec(
                    service,
                    action,
                    args);

            handler.post(new Runnable() {
                public void run() {
                    try {
                        String execJs = "javascript:llWebBridge.callBackJs('" + responseBody + "','" + requestId  + "', true)";
                        webView.loadUrl(execJs);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (PluginNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setService(String service) {
        this.service = service;
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void setArgs(JSONObject args) {
        this.args = args;
    }

    @Override
    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    @Override
    public void setMessageHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void setRequestId(String id) {
        this.requestId = id;
    }
}
