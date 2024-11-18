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
        var tmpDir = System.getenv("TMP");
        var extractLib = new File(tmpDir, name);
        try (var in = IMEFix.class.getResourceAsStream("/" + name)) {
            var libData = in.readAllBytes();
            var valid = false;
            if (extractLib.exists()) {
                try (var libFile = new FileInputStream(extractLib)) {
                    valid = Arrays.equals(libData, libFile.readNBytes(libData.length));
                }
            }
            if (!valid) {
                try (var out = new FileOutputStream(extractLib)) {
                    out.write(libData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.load(extractLib.getPath());
    }
}
