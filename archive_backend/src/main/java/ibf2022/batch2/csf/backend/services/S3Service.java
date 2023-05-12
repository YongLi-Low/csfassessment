package ibf2022.batch2.csf.backend.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    
    @Autowired
    private AmazonS3 s3Client;

    // @Value("${DO_STORAGE_BUCKETNAME}")
    // private String bucketName;
    private String bucketName = "yongli033";

    public String upload(MultipartFile multipartFile, String bundleId) throws IOException {
        
        // Metadata to be associated with the object
        Map<String, String> userData = new HashMap<>();
        userData.put("uploadDateTime", LocalDateTime.now().toString());
        userData.put("originalFileName", multipartFile.getOriginalFilename());

        // Set the media type of the object
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        // Associate the userdata with the object
        metadata.setUserMetadata(userData);

        // Create a put request with the bucket's name, the key name, input stream and the metadata
        PutObjectRequest putRequest = new PutObjectRequest(bucketName,
                                        "%s-%s".formatted(bundleId, multipartFile.getOriginalFilename()),
                                        multipartFile.getInputStream(),
                                        metadata);

        putRequest = putRequest.withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(putRequest);

        return "%s-%s".formatted(bundleId, multipartFile.getOriginalFilename());
    }
}
