package athlonix;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {

    public static List<Plugin> plugins = new ArrayList<Plugin>();

    public static void loadPlugins() {
        try {
            File pluginsDir = new File("app/plugins");

            File[] plugins = pluginsDir.listFiles();


            for (File plugin_jar : plugins) {
                try {
                    URL url = plugin_jar.toURI().toURL();
                    URL[] urls = new URL[] { url };

                    ClassLoader cl = new URLClassLoader(urls);
                    Class<Plugin> cls = (Class<Plugin>) cl.loadClass("plugins.AthlonixPlugin");
                    Plugin plugin = cls.getDeclaredConstructor().newInstance();
                    plugin.load();
                    PluginManager.plugins.add(plugin);
                } catch (Exception e) {
                    System.out.println("Error while adding plugin " + plugin_jar.getName());
                }
            }
        } catch (Exception e) {
            System.out.println("Adding Plugins Failed Due To : " + e.toString());
        }

    }

}
