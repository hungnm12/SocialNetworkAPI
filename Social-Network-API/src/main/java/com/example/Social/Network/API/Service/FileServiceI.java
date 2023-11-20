package com.example.Social.Network.API.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileServiceI {
    Map<String,String> uploadFile(MultipartFile file);

    void deleteFile(String key);

}
