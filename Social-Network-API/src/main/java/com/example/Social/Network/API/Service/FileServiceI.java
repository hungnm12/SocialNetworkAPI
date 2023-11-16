package com.example.Social.Network.API.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceI {
    String uploadFile(MultipartFile file);

}
