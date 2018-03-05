package com.example.fileservice.db;

import com.mongodb.gridfs.GridFSDBFile;

public class GridfileMeta {
    public final String filename, contentType;
    public final int min_version, max_version;

    public GridfileMeta(GridFSDBFile file, int max) {
        this(file.getFilename(), file.getContentType(), 1, max);
    }

    public GridfileMeta(String filename, String contentType, int min_version, int max_version) {
        this.filename = filename;
        this.min_version = min_version;
        this.max_version = max_version;
        this.contentType = contentType;
    }
}
