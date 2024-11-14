package loli.ball.imefix;

import java.io.*;
import java.util.Arrays;

public final class IMEFix {

    public static void main(String[] args) {
        new IMEFix();
    }

    public IMEFix()  {
        this.loadJarDll("imefix.dll");
    }

    public void loadJarDll(String name)  {
        var appData = System.getenv("APPDATA");
        var lib = new File(appData, name);
        try (var in = IMEFix.class.getResourceAsStream("/" + name)) {
            var data = in.readAllBytes();
            var valid = false;
            if (lib.exists()) {
                try (var libFile = new FileInputStream(lib)) {
                    valid = Arrays.equals(data, libFile.readAllBytes());
                }
            }
            if (!valid) {
                try (var out = new FileOutputStream(lib)) {
                    out.write(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.load(lib.getPath());
    }
}
