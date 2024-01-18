import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EncryptedClassloader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassloader(String key, File dir) {
        super();
        this.key = key;
        this.dir = dir;
    }

    protected Class<?> findClass(String name, String key, File dir) throws ClassNotFoundException {
        Class result;
        try {
            byte[] classBytes= loadFileAsBytes(dir);
            result = defineClass(name, classBytes, Integer.parseInt(key),
                    classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(
                    "Cannot load class " + name + ": " + e);
        } catch (ClassFormatError e) {
            throw new ClassNotFoundException(
                    "Format of class file incorrect for class "
                            + name + " : " + e);
        }
        return result;
    }
    public static byte[] loadFileAsBytes(File file) throws IOException {
        byte[] result = new byte[(int)file.length()];
        FileInputStream f = new FileInputStream(file);
        try {
            f.read(result,0,result.length);
        } finally {
            try {
                f.close();
            } catch (Exception e) {

            }
        }
        return result;
    }
}
