package com.example.fileservice.controller;

import com.example.fileservice.db.FilesMeta;
import com.example.fileservice.db.GridfileMeta;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/files")
public class FilesController {

    private GridFsTemplate gridops;

    @Autowired
    public FilesController(GridFsTemplate gridops) {
        this.gridops = gridops;
    }

    @RequestMapping(path="/{bucket}", method=GET)
    @ResponseBody
    public ResponseEntity<GridfileMeta> byId(@PathVariable("bucket") String bucket) {
        List<GridFSDBFile> result = gridops.find( new Query().addCriteria(Criteria.where("filename").is(bucket)) );

        if( result == null || result.isEmpty() ) return ResponseEntity.status(404).build();
        else {
            GridfileMeta gfm = new GridfileMeta( result.get(0), result.size() );
            return ResponseEntity.ok().body( gfm );
        }
    }

    @RequestMapping(path="/{bucket}/{version}", method=GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> byIdAndVersion(@PathVariable("bucket") String bucket, @PathVariable("version") Integer version) {
        List<GridFSDBFile> result = gridops.find( new Query().addCriteria(Criteria.where("filename").is(bucket)) );

        if( result == null || result.isEmpty() ) return ResponseEntity.status(404).build();
        else if( version == 0 || result.size() < version ) return ResponseEntity.badRequest().build();//.status(404).body("bucket version not found - range is 1 to "+ result.size() ).build();
        else return ResponseEntity.ok()
                    .contentLength( result.get(version-1).getLength() )
                    .contentType( MediaType.parseMediaType( result.get(version-1).getContentType() ) )
                    .body(new InputStreamResource( result.get(version-1).getInputStream() ) );
    }

    @RequestMapping(path="", method = GET)
    public FilesMeta getAllFileIds() {
        List<GridFSDBFile> res = gridops.find(null);

        return new FilesMeta( res.stream().map( f -> f.getFilename() ).collect(Collectors.toList()) );
    }

    @RequestMapping(path="", method = POST)
    public ResponseEntity<String> storeFile(@RequestParam("bucket") String bucket, @RequestParam("file") MultipartFile file) {
        try {
            gridops.store(file.getInputStream(), bucket, file.getContentType() );
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}
