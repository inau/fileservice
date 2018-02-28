package com.example.fileservice.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/apks")
public class FilesController {

    @Autowired
    private GridFsOperations files;

    @GetMapping("/{bucket}")
    public String byBucketIdentifier(@PathVariable("bucket") String bucket) {
        return "GET " + bucket;
    }

    @GetMapping("/")
    public String byBucketIdentifier() {
        return "GET ALL";
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public String create(@RequestParam("bucket") String bucket, @RequestParam("file") String file) {
        return "POST " + bucket + ", \n" + file;
    }
}
