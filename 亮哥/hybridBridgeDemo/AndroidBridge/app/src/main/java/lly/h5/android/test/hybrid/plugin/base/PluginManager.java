package lly.h5.android.test.hybrid.plugin.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

import lly.h5.android.test.R;
import lly.h5.android.test.hybrid.ActionNotFoundException;
import lly.h5.android.test.hybrid.JSAction;

public class PluginManager {
    static final String TAG = PluginManager.class.getSimpleName();

    private Activity context;
    private HashMap<String, String> configs = new HashMap<String, String>();
    private HashMap<String, IPlugin> plugins = new HashMap<String, IPlugin>();

    public PluginManager(Activity context) {
        this.context = context;
        Log.e(TAG, "new PluginManager");
    }

    /**
     * 执行html5请求
     *
     * @param service
     * @param action
     * @param args
     * @return JSON 字符�?
     * @throws PluginNotFoundException
     */
    public String exec(String service, String action, JSONObject args) throws PluginNotFoundException {
        IPlugin plugin = getPlugin(service);
        try {
            final PluginResult result = execute(plugin, action, args);
            return result.getJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return PluginResult.getErrorJSON(e);
        } catch (ActionNotFoundException e) {
            e.printStackTrace();
            return PluginResult.getErrorJSON(e);
        }

    }

    /**
     * 调用目标接口的指定方法
     * @param target 目标对象
     * @param action 目标接口，通过注解设置（JSAction("action")）
     * @param json   接口参数
     * @return       交互结果
     * @throws ActionNotFoundException
     */
    private PluginResult execute (IPlugin target, String action, JSONObject json)
            throws ActionNotFoundException {
        final Class<?> pluginClass = target.getClass();
        final String pluginName = pluginClass.getSimpleName();
        final Method[] declaredMethods = pluginClass.getDeclaredMethods();
        for (Method m : declaredMethods) {
            if (!m.isAccessible()) {
                m.setAccessible(true);
            }
            final boolean isAnnotationPresent = m.isAnnotationPresent(JSAction.class);
            if (isAnnotationPresent) {
                final JSAction jsAction = m.getAnnotation(JSAction.class);
                final String actionName =  jsAction.value();
                if (actionName.equals(action)) {
                    try {
                        Log.i(pluginName, actionName + "-> 即将被调用。");
                        final Class<?>[] parameterTypes = m.getParameterTypes();
                        if (parameterTypes.length == 0) {
                            Log.i(pluginName, actionName + "-> 调用成功。");
                            return (PluginResult) m.invoke(target);
                        } else {
                            Log.i(pluginName, actionName + "-> 调用成功。");
                            return (PluginResult) m.invoke(target, json);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return PluginResult.newErrorPluginResult(e);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                        return PluginResult.newErrorPluginResult(e);
                    }
                }
            }
        }

        throw new ActionNotFoundException
                (pluginName, action + " [接口可能未找到，或者请检查目标方法是否标注 @JSAction()。]");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Collection<IPlugin> iPlugins = plugins.values();
        for (IPlugin iPlugin : iPlugins) {
            iPlugin.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 加载plugins.xml
     */
    public void loadPlugin() {
        XmlResourceParser xml = context.getResources().getXml(R.xml.plugins);
        try {

            int eventType = -1;
            while ((eventType = xml.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String name = xml.getName();
                    if ("plugin".equals(name)) {
                        String pluginName = xml.getAttributeValue(null, "name");
                        String className = xml.getAttributeValue(null, "class");
                        configs.put(pluginName, className);
                    }

                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取插件
     * @param pluginName
     * @return
     * @throws PluginNotFoundException
     */
    public IPlugin getPlugin(String pluginName) throws PluginNotFoundException {
        String className = configs.get(pluginName);
        if(className==null){
            throw new PluginNotFoundException(pluginName);
        }
        if (plugins.containsKey(className)) {
            return plugins.get(className);
        } else {
            return addPlugin(className);
        }
    }

    /**
     * 添加插件到插件容器中
     * @param className
     * @return
     */
    @SuppressWarnings("rawtypes")
    public IPlugin addPlugin(String className) {
        Log.d(TAG, "className:" + className);
        IPlugin plugin = null;
        try {
            Class c = getClassByName(className);

            if (isWebServerPluginClass(c)) {

                plugin = (IPlugin) c.newInstance();
                plugin.setContext(context);
                plugins.put(className, plugin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plugin;

    }

    @SuppressWarnings("rawtypes")
    private boolean isWebServerPluginClass(Class c) {
        return IPlugin.class.isAssignableFrom(c)
                || Plugin.class
                .isAssignableFrom(c);
    }

    @SuppressWarnings("rawtypes")
    private Class getClassByName(String className)
            throws ClassNotFoundException {
        if (className == null)
            return null;
        return Class.forName(className);
    }

    public void onCreate(Bundle savedInstanceState) {
        Collection<IPlugin> iPlugins = plugins.values();
        for (IPlugin iPlugin : iPlugins) {
            iPlugin.onCreate(savedInstanceState);
        }
    }
}
