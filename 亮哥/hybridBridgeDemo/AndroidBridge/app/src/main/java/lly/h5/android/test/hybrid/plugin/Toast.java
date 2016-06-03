package lly.h5.android.test.hybrid.plugin;

import org.json.JSONException;
import org.json.JSONObject;

import lly.h5.android.test.hybrid.JSAction;
import lly.h5.android.test.hybrid.plugin.base.Plugin;
import lly.h5.android.test.hybrid.plugin.base.PluginResult;

/**
 * Created by leon on 16/5/11.
 */
public class Toast extends Plugin {

    @JSAction("show")
    public PluginResult makeText(JSONObject args) {
        try {
            String text = args.getString("text");
            int duration = args.getInt("duration");
            android.widget.Toast.makeText(this.context, text, android.widget.Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            return PluginResult.newErrorPluginResult(e.getMessage());
        }
        return PluginResult.newEmptyPluginResult();
    }
}
