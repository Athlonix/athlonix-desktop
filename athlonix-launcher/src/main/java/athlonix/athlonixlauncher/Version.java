package athlonix.athlonixlauncher;

import com.google.gson.annotations.Expose;

public class Version {
    @Expose
    public String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
