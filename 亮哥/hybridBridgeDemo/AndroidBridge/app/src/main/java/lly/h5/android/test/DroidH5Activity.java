package lly.h5.android.test;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import lly.h5.android.test.hybrid.AppJavaInterface;
import lly.h5.android.test.hybrid.WebServerChromeClient;
import lly.h5.android.test.hybrid.WebServerClient;
import lly.h5.android.test.hybrid.plugin.base.PluginManager;

public class DroidH5Activity extends FragmentActivity {

    public WebView mWebView;
    public PluginManager mPluginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContentView();

        mPluginManager = new PluginManager(this);
        mPluginManager.loadPlugin();
        mPluginManager.onCreate(savedInstanceState);

    }

    private void initContentView() {
        setContentView(R.layout.activity_main);

        mWebView = (WebView) this.findViewById(R.id.my_webView);

        mWebView.clearCache(true);
        mWebView.addJavascriptInterface(new AppJavaInterface(), AppJavaInterface.HYBRID_KEY);

        mWebView.setWebChromeClient(new WebServerChromeClient(this));
        mWebView.setWebViewClient(new WebServerClient(this));

        //支持chrome调试
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        //允许本地JS跨域(file://)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        settings.setJavaScriptEnabled(true);

        mWebView.loadUrl("http://10.100.9.11:1111/");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPluginManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
