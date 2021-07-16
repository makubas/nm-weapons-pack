package net.nm_weapons_pack.utils;

import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.config.NmConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class JarUtils {
    public static boolean copyFileFromJar(String string, String destination) {
        boolean succeess = true;
        InputStream source = NmConfig.class.getResourceAsStream(string);

        System.out.println("Copying ->" + source + "\n\tto ->" + destination);

        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            NmWeaponsPack.warnMsg("Error while copying file: " + ex.toString());
            succeess = false;
        }

        return succeess;

    }

    public static String getBasePathForClass(Class<?> classs) {
        File file;
        String basePath = "";
        boolean failed = false;

        try {
            file = new File(classs.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

            if (file.isFile() || file.getPath().endsWith(".jar") || file.getPath().endsWith(".zip")) {
                basePath = file.getParent();
            } else {
                basePath = file.getPath();
            }
        } catch (URISyntaxException ex) {
            failed = true;
            NmWeaponsPack.warnMsg("Cannot firgue out base path for class with way (1): " + ex);
        }

        if (failed) {
            try {
                file = new File(classs.getClassLoader().getResource("").toURI().getPath());
                basePath = file.getAbsolutePath();
            } catch (URISyntaxException ex) {
                NmWeaponsPack.warnMsg("Cannot firgue out base path for class with way (2): " + ex);
            }
        }

        if (basePath.endsWith(File.separator + "lib") || basePath.endsWith(File.separator + "bin")
                || basePath.endsWith("bin" + File.separator) || basePath.endsWith("lib" + File.separator)) {
            basePath = basePath.substring(0, basePath.length() - 4);
        }
        if (basePath.endsWith(File.separator + "build" + File.separator + "classes")) {
            basePath = basePath.substring(0, basePath.length() - 14);
        }
        if (!basePath.endsWith(File.separator)) {
            basePath = basePath + File.separator;
        }
        return basePath;
    }

    public static File getFileFromResource(String fileName) throws URISyntaxException{

        ClassLoader classLoader = NmConfig.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }

    }

    public static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = NmConfig.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
}
