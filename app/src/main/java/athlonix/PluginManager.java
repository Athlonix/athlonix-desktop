package athlonix;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginManager {
    public static void runPlugins() {
        try {
            File pluginsDir = new File("app/plugins");

            File[] plugins = pluginsDir.listFiles();


            for (File plugin_jar : plugins) {
                try {
                    URL url = plugin_jar.toURI().toURL();
                    URL[] urls = new URL[] { url };

                    ClassLoader cl = new URLClassLoader(urls);
                    Class<Plugin> cls = (Class<Plugin>) cl.loadClass("plugins.AthlonixPlugin");
                    Plugin circle = cls.getDeclaredConstructor().newInstance();
                    circle.run();
                } catch (Exception e) {
                    System.out.println("Error while adding plugin " + plugin_jar.getName());
                }
            }
        } catch (Exception e) {
            System.out.println("Adding Plugins Failed Due To : " + e.toString());
        }

    }
}
