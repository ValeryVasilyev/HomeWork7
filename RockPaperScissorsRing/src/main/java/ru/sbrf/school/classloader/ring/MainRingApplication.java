package ru.sbrf.school.classloader.ring;

import ru.sbrf.school.classloader.api.PlayableRockPaperScissors;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MainRingApplication {
    public static void main(String[] args) {

    }

    /**
     *
     * @return Получаем весь список плагинов-классов
     */
    private PlayableRockPaperScissors getPlugins() {
        File[] jars = getPluginFiles();

        Class[] pluginClasses = new Class[jars.length];

        for (int i = 0; i < jars.length; i++) {
            try {
                URL jarURL = jars[i].toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
                // TODO возможно, classLoader и классы будут удалены GC т.к. на classLoader не будет ссылок
                //  (у любого объекта есть ссылка на definition classLoader)
                pluginClasses[i] = classLoader.loadClass("com.test.HelloPlugin");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pluginClasses;
    }


    /**
     *
     * @return список файлов-плагинов из папки для плагинов
     */
    private File[] getPluginFiles() {
        File pluginDir = new File("plugins");

        return pluginDir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".jar");
            }
        });
    }

}
