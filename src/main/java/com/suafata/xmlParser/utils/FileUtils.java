package com.suafata.xmlParser.utils;

import org.springframework.util.StringUtils;
import java.io.File;

public class FileUtils {

    public FileUtils(){
    }

    public static File[] loadFiles(String dir){
        if(!StringUtils.isEmpty(dir)){
            File fileDir = new File(dir);
            return fileDir.listFiles((d, name) -> name.endsWith(".xml"));
        }
        return null;
    }

    public static  File[] splitArray(File[] array, int start, int end) {
        int length = end - start;
        File[] part = new File[length];
        for ( int i = start; i < end; i++ ) {
            part[i-start] = array[i];
        }
        return part;
    }

}
