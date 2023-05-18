package org.example.meditime.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploaderService {

    void uploadCsvFile(MultipartFile file) throws Exception;

}
