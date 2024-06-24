package plugins;


import athlonix.Plugin;

public class AthlonixPlugin implements Plugin {

    private final String NAME = "plugin teeest";
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

    @Override
    public String getName() {
        return NAME;
    }

     @Override
    public String getScene() {

    }
}
