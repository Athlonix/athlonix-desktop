package athlonix;

import java.io.File;
import java.io.IOException;
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
            if(plugins == null) {
                return;
            }
            for (File pluginJar : plugins) {
                addPlugin(pluginJar);
            }
        } catch (Exception e) {
            System.out.println("Adding Plugins Failed Due To : " + e.toString());
        }

    }

    private static void addPlugin(File pluginJar) {
        try {
            URL url = pluginJar.toURI().toURL();
            URL[] urls = new URL[] { url };

            ClassLoader cl = new URLClassLoader(urls);
            Class<Plugin> cls = (Class<Plugin>) cl.loadClass("plugins.AthlonixPlugin");
            Plugin plugin = cls.getDeclaredConstructor().newInstance();
            plugin.load();
            PluginManager.plugins.add(plugin);
        } catch (Exception e) {
            System.out.println("Error while adding plugin " + pluginJar.getName());
        }
    }

    public static void laodOnePlugin(String name) {
        String pluginPath = PLUGIN_DIRECTORY + "/" + name + ".jar";
        File pluginFile = new File(pluginPath);
        if(!pluginFile.exists()) {
            System.out.println("fail to find plugin at " + pluginPath);
        }

        Plugin plugin = plugins.stream().filter(item -> item.getName().equals(name)).findFirst().orElse(null);
        if(plugin != null) {
            System.out.println("plugin is already loaded");
            return;
        }

        addPlugin(pluginFile);
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

    public static void deletePlugin(String name) throws IOException {
        unloadPlugin(name);

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

    public static void unloadPlugin(String name) throws IOException {
        Plugin plugin = plugins.stream().filter(item -> item.getName().equals(name)).findFirst().orElse(null);
        if(plugin == null) {
            return;
        }

        plugin.unload();
        plugins.remove(plugin);
        ClassLoader cl = plugin.getClass().getClassLoader();
        if (cl instanceof URLClassLoader) {
            ((URLClassLoader) cl).close();
        }
    }


}
