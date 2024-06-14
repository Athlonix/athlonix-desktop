package athlonix;

import javafx.scene.Scene;

public interface Plugin {
    public void load();
    public void unload();
    public void run();
    public Scene getScene();
    public String getName();
}
