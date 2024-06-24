package athlonix;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class PluginManager {

    public static List<Plugin> plugins = new ArrayList<Plugin>();
    private static final String PLUGIN_DIRECTORY = "app/plugins";

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


    public static HashSet<String> getInstalledPlugins() {
        File pluginPath = new File(PLUGIN_DIRECTORY);
        if(!pluginPath.isDirectory()) {
            throw new RuntimeException("Plugin directory does not exist");
        }

        HashSet<String> existingPlugins = new HashSet<String>();
        for(String file : Objects.requireNonNull(pluginPath.list())) {
            int dotIndex = file.lastIndexOf('.');
            if(dotIndex != -1) {
                String fileName = file.substring(0, dotIndex);
                existingPlugins.add(fileName);
            }
        }

        return existingPlugins;
    }

    public static void deletePlugin(String name) {
        String pluginPath = PLUGIN_DIRECTORY + "/" + name + ".jar";
        File pluginFile = new File(pluginPath);
        if(!pluginFile.exists()) {
            return;
        }

        if (pluginFile.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }
}
