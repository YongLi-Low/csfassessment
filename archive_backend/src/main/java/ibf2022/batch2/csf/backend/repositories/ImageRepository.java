package ibf2022.batch2.csf.backend.repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name

	@Autowired
    private AmazonS3 s3Client;

    // @Value("${DO_STORAGE_BUCKETNAME}")
    // private String bucketName;
    private String bucketName = "yongli033";

    public String upload(File file, String bundleId) throws IOException {
        
        // Metadata to be associated with the object
        Map<String, String> userData = new HashMap<>();
        userData.put("uploadDateTime", LocalDateTime.now().toString());
        userData.put("originalFileName", file.getName());

        // Set the media type of the object
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(Files.probeContentType(file.toPath()));
        metadata.setContentLength(file.length());
        // Associate the userdata with the object
        metadata.setUserMetadata(userData);

        // Create a put request with the bucket's name, the key name, input stream and the metadata
        PutObjectRequest putRequest = new PutObjectRequest(bucketName,
                                        "%s-%s".formatted(bundleId, file.getName()),
                                        new FileInputStream(file),
                                        metadata);

        putRequest = putRequest.withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(putRequest);

        return "%s-%s".formatted(bundleId, file.getName());
	}
}