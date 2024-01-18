import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;

import java.io.File;

public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public File load(String pluginName, String pluginClassName) {
        File f;
        f = new File((new File(pluginRootDirectory)).getPath()
                + File.separatorChar
                + pluginName.replace('/',
                File.separatorChar));
        if (f.exists())
            return f;
        return null;
    }
}
