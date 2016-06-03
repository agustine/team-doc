package lly.h5.android.test.hybrid.plugin.base;

/**
 * Created by leon on 16/5/11.
 */
public class PluginNotFoundException extends Throwable {
    private String pluginName;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public PluginNotFoundException(String pluginName) {
        super();
        this.pluginName = pluginName;
    }

    @Override
    public String getMessage() {
        return "Plugin " + pluginName + "未找到,请检查plugins.xml是否配置";
    }
}
