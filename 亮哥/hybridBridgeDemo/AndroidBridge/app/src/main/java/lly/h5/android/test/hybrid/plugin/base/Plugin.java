package lly.h5.android.test.hybrid.plugin.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by leon on 16/5/11.
 */
public class Plugin implements IPlugin {

    protected Activity context;

    @Override
    public void setContext(Activity context) {
        this.context = context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onRestart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
