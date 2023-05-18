package org.example.meditime.controller;

import org.example.meditime.service.UploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UploaderController {

    @Autowired
    UploaderService uploaderService;

    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws Exception {
        uploaderService.uploadCsvFile(file);
    }

}
