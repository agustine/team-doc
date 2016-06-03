package lly.h5.android.test.hybrid.plugin.base;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leon on 16/5/11.
 */
public class PluginResult {
    static final String TAG = PluginResult.class.getSimpleName();

    private Object message;
    private Status status = Status.OK;

    public PluginResult(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public PluginResult(String message) {
        super();
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getJSONString() {
        JSONObject jsonObject = new JSONObject();
        String json = "{}";
        try {
            jsonObject.put("message", message);
            jsonObject.put("status", status.ordinal());
            json = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 获取异常JSON
     *
     * @param e
     * @return
     */
    public static String getErrorJSON(Throwable e) {

        String msg = e.getMessage();
        return new PluginResult(msg == null ? "" : msg, Status.ERROR)
                .getJSONString();
    }

    public static PluginResult newEmptyPluginResult() {
        return new PluginResult("");
    }

    public static PluginResult newErrorPluginResult(String message) {
        return new PluginResult(message, Status.ERROR);
    }

    public static PluginResult newErrorPluginResult(Exception e) {
        return PluginResult.newErrorPluginResult(e.getMessage());
    }


    public static enum Status {
        OK(0), ERROR(1), CANCEL(2);

        Status(int i) {

        }
    }
}
