package lly.h5.android.test.hybrid.plugin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONObject;

import lly.h5.android.test.hybrid.JSAction;
import lly.h5.android.test.hybrid.plugin.base.Plugin;
import lly.h5.android.test.hybrid.plugin.base.PluginResult;

/**
 * Created by leon on 16/5/11.
 */
public class Device extends Plugin {

    private static final int NET_3G = 1, NET_WIFI = 2, NET_OTHER = -1;

    /**
     * 获取网络类型
     */
    @JSAction("getNetType")
    public PluginResult getNetType(final JSONObject args) {
        switch (GetCurrentNetType(context)) {
            case NET_3G:
                return new PluginResult("monet");
            case NET_WIFI:
                return new PluginResult("wifi");
            default:
                return new PluginResult("unline");
        }
    }

    public static int GetCurrentNetType(Context context) {

        ConnectivityManager m_ConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (m_ConnectivityManager != null) {
            NetworkInfo networkInfo = m_ConnectivityManager
                    .getActiveNetworkInfo();
            if (networkInfo != null) {
                networkInfo.getType();

                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return NET_3G;
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return NET_WIFI;
                }
            }
        }
        return NET_OTHER;

    }

}
