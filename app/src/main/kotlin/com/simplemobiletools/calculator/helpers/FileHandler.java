package com.simplemobiletools.calculator.helpers;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static com.simplemobiletools.calculator.helpers.CONSTANT.FILE;
import static com.simplemobiletools.calculator.helpers.CONSTANT.HISTORY_FILE;
import static com.simplemobiletools.calculator.helpers.CONSTANT.TEMP_FILE;

/**
 * Created by Marc-Andre Dragon on 2018-02-13.
 */

public class FileHandler {
    private HashMap<String, File> fileList = new HashMap<>();
    private Context con;


    public FileHandler(Context context) {
        this.con = context;
    }

    //Setter to add to the list of files created.
    public boolean addFile(File file, String path) {
        if (file != null) {
            fileList.put(path, file);
            return true;
        }
        else {
            return false;
        }
    }

    //Getter for the file in the list.
    public File getFile(String path) {
        return fileList.get(path);
    }

    //creates the temp files for the memory saved values
    private File createTempFile(String path) {
        File temp = null;
        try {
            temp = File.createTempFile(path,".tmp");
            addFile(temp, temp.getAbsolutePath());
            temp.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }

    //Private create file, not just the history file
    private File createFile(String path, boolean data) {
        File file;
        if (data)
        {
            file = new File(con.getFilesDir()+"/"+path);
        }
        else {
            file = new File(path);
        }
        addFile(file, file.getAbsolutePath());
        return file;
    }


    //Creational design pattern. Factory method is used to determine which file is needed where.
    public File chooseFileType(String type, String path) {
        switch (type) {
            case TEMP_FILE:
                return createTempFile(path);
            case HISTORY_FILE:
                return createFile(path, true);
            case FILE:
                return createFile(path, false);
            default:
                return null;
        }
    }


}
