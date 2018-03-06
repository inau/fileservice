package com.example.fileservice.db;

import com.mongodb.gridfs.GridFSDBFile;

import java.util.ArrayList;
import java.util.List;

public class GridfileMeta {
    static class VersionMeta {
        public final String date;
        public final Integer v;
        public final String md5;

        public VersionMeta(String d, int v, String md5) {
            date = d;
            this.v = v;
            this.md5 = md5;
        }
    }

    public final String filename, contentType;
//    public final int min_version, max_version;
    public List<VersionMeta> versions = new ArrayList<>();

    public GridfileMeta(List<GridFSDBFile> files) {
        this(files.get(0).getFilename(), files.get(0).getContentType(), files);
    }

    public GridfileMeta(String filename, String contentType, List<GridFSDBFile> files) {
        this.filename = filename;
        this.contentType = contentType;

        int i = 1;
        for(GridFSDBFile f : files) {
            versions.add( new VersionMeta(f.getUploadDate().toString(), i++, f.getMD5()) );
        }
    }
}
