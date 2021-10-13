package com.multimoney.storage.Storage.service;
import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.multimoney.storage.Storage.model.File;
import com.multimoney.storage.Storage.repository.FileRepository;


@Service
public class FileStorageService {
    
    @Autowired
    private FileRepository fileRepository; 

    public File store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File File = new File(fileName, file.getContentType(), file.getBytes());
    
        return fileRepository.save(File);
      }

      public File getFile(String id) {
        return fileRepository.findById(id).get();
      }
      
      public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
      }

}
