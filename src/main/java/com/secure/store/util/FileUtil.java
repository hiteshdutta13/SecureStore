package com.secure.store.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static final String FORWARD_SLASH = "/";
    public static final String DOT = ".";

    public static void createDirectory(String directoryPath) {
        Path path = Paths.get(directoryPath);
        try {
            Files.createDirectories(path);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean write(byte[] file, String directoryPath, String fileName) {
        try {
            FileUtil.createDirectory(directoryPath);
            File serverFile = new File(directoryPath + FORWARD_SLASH + fileName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file);
            stream.close();
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean delete(String directoryPath, String fileName) {
        File file = new File(directoryPath+ FORWARD_SLASH +fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
    public static String getFileExtension(String originalFilename) {
        if (originalFilename != null && originalFilename.contains(DOT)) {
            return originalFilename.substring(originalFilename.lastIndexOf(DOT) + 1);
        } return "";
    }

    public static String docFilePath(String preFix, Long userId) {
        return docFilePath(preFix) + "/User_" + userId;
    }

    public static String docFilePath(String preFix) {
        return preFix + FORWARD_SLASH  + "/SecureStore/Users";
    }
}
