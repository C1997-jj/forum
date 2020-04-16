package com.forum.utils;

public class FileUtil {
    private static final String[] images = {"jpg","jpeg","png"};
    public static boolean isFileAllowed(String fileExt) {
        for (String image : images) {
            if (fileExt.equals(image)){
                return true;
            }
        }
        return false;
    }
}
