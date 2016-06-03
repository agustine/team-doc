package lly.h5.android.test.hybrid.plugin;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import lly.h5.android.test.hybrid.JSAction;
import lly.h5.android.test.hybrid.plugin.base.Plugin;
import lly.h5.android.test.hybrid.plugin.base.PluginResult;
import lly.h5.android.test.hybrid.plugin.base.ResultCode;

/**
 * Created by leon on 16/5/12.
 */
public class Payment extends Plugin {
    private static final int REQUEST_CODE_PAYMENT = 1;
    private CountDownLatch latch;
    private PluginResult payResult;

    @SuppressLint("NewApi")
    @JSAction("pay")
    public PluginResult pay(JSONObject args) {
        try {
            Iterator<String> keys = args.keys();
            Bundle bundle = new Bundle();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = args.getString(key);
                bundle.putString(key, value);
            }

            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName componentName = new ComponentName(
                    "lly.h5.android.test",
                    "lly.h5.android.test.PaymentActivity");
            intent.setComponent(componentName);
            intent.putExtras(bundle);

            context.startActivityForResult(intent, REQUEST_CODE_PAYMENT);

            payResult = new PluginResult("failuer", PluginResult.Status.ERROR);
            waitingForResult();

        } catch (JSONException e) {
            e.printStackTrace();
            return new PluginResult("failure", PluginResult.Status.ERROR);
        }
        return payResult;
    }

    private void waitingForResult(){
        latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        PluginResult.Status resultStatus = PluginResult.Status.values()[resultCode];
        payResult.setStatus(resultStatus);

        if (requestCode == REQUEST_CODE_PAYMENT) {
            if(resultCode == ResultCode.ERROR){
                payResult.setMessage(data.getStringExtra("errmsg"));
            }
            latch.countDown();
        }
    }
}
