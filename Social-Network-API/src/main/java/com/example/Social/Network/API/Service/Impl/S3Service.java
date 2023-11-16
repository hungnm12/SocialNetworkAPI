package com.example.Social.Network.API.Service.Impl;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.Social.Network.API.Service.FileServiceI;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service implements FileServiceI {
    public static final String BUCKET_NAME="bedan";
    private final AmazonS3Client amazonS3Client;

    public S3Service() {
        this.amazonS3Client = new AmazonS3Client(new BasicAWSCredentials("AKIARNRISJ27AESQ336H", "HskN0NcnfXtLnyaw2jM1+04od/avJg784iGuAMJy"));
        this.amazonS3Client.setRegion(Region.getRegion(Regions.US_EAST_1));
    }

    @Override
    public String uploadFile(MultipartFile file) {
        var fileNameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String key = UUID.randomUUID() +"."+fileNameExtension;
        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try{
            amazonS3Client.putObject(BUCKET_NAME,key,file.getInputStream(),metadata);

        } catch ( IOException e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An exception occurred while uploading the file");
        }

        amazonS3Client.setObjectAcl(BUCKET_NAME,key, CannedAccessControlList.PublicRead);
        return amazonS3Client.getResourceUrl(BUCKET_NAME,key);
    }
}
