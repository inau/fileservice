package com.example.fileservice.db;

import com.mongodb.gridfs.GridFSDBFile;

import java.util.HashMap;
import java.util.List;

public class FilesMeta {
    public final HashMap<String, Integer> files = new HashMap<>();

    public FilesMeta(List<String> filenames) {
        for(String label : filenames) {
            Integer v = files.putIfAbsent(label, 1);
            if( v != null ) files.put(label, v+1);
        }
    }
}
