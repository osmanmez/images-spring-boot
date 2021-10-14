package com.multimoney.storage.Storage.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.multimoney.storage.Storage.service.FileStorageService;
import com.multimoney.storage.Storage.message.ResponseMessage;

import com.multimoney.storage.Storage.message.ResponseFile;

import com.multimoney.storage.Storage.repository.FileRepository;

import com.multimoney.storage.Storage.model.File;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@CrossOrigin("http://localhost:8081")
public class FileController {
    @Autowired
    private FileStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
      String message = "";
      try {
        storageService.store(file);
  
        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
      File file = storageService.getFile(id);
  
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
          .body(file.getData());
    }

    
    @GetMapping("/show/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws Exception {
      File file = storageService.getFile(id);
      return ResponseEntity.ok().contentType(MediaType.valueOf(file.getType())).body(file.getData());
    }
    



}
