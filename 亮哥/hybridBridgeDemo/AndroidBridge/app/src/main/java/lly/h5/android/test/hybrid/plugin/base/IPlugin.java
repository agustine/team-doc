package lly.h5.android.test.hybrid.plugin.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

/**
 * Created by leon on 16/5/11.
 */
public interface IPlugin {
    public static final String REQUESTID = "requestId";
    public static final String SERVICE = "service";
    public static final String ACTION = "action";
    public static final String ARGS = "args";

    /**
     * 设置Hybrid Context
     *
     * @param context
     */
    public void setContext(Activity context);

    public void onActivityResult(int requestCode, int resultCode, Intent data);

    public void onCreate(Bundle savedInstanceState);

    public void onDestroy();

    public void onPause();

    public void onRestart();

    public void onResume();

    public void onSaveInstanceState(Bundle outState);

    public void onStart();

    public void onStop();
}
