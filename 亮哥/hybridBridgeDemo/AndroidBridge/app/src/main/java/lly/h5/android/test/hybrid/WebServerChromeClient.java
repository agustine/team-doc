package lly.h5.android.test.hybrid;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;

import lly.h5.android.test.DroidH5Activity;
import lly.h5.android.test.hybrid.plugin.base.IPlugin;
import lly.h5.android.test.hybrid.plugin.base.PluginNotFoundException;
import lly.h5.android.test.hybrid.plugin.base.PluginResult;

/**
 * Created by leon on 16/5/11.
 * https://developer.android.com/reference/android/webkit/WebChromeClient.html
 */
public class WebServerChromeClient extends WebChromeClient {

    private DroidH5Activity hybridActivity;

    public WebServerChromeClient(DroidH5Activity hybridActivity) {
        this.hybridActivity = hybridActivity;
    }

    /**
     * 拦截js confirm，实现同步交互
     *
     * @param view
     * @param url
     * @param message
     * @param defaultValue
     * @param result
     * @return
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message,
                              String defaultValue, JsPromptResult result) {

        JSONObject args = null;
        JSONObject head = null;
        try {
            head = new JSONObject(message);
            if (defaultValue != null && !defaultValue.equals("")) {
                try {
                    args = new JSONObject(defaultValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            final String service = head.getString(IPlugin.SERVICE);
            final String action = head.getString(IPlugin.ACTION);

            String execResult = hybridActivity.mPluginManager.exec(service, action, args);
            result.confirm(execResult);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            result.confirm(PluginResult.getErrorJSON(e));
            return true;
        } catch (PluginNotFoundException e) {
            e.printStackTrace();
            result.confirm(PluginResult.getErrorJSON(e));
            return true;
        }

    }

    /**
     * 拦截 js console.log
     *
     * @param consoleMessage
     * @return
     */
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.i("Lly JSLog ==>", MessageFormat.format("message:{0}\n source:{1} \n line{2}", consoleMessage.message() +
                "\n source:" + consoleMessage.sourceId() +
                "\n line:" + consoleMessage.lineNumber()));
        return true;
    }
}
