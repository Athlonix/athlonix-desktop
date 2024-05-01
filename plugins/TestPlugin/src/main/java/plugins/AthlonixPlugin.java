package plugins;


import athlonix.Plugin;

public class AthlonixPlugin implements Plugin {

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public void run() {
        System.out.println("hello from test plugin !!");
    }
}