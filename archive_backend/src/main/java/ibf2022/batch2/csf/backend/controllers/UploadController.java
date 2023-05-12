package ibf2022.batch2.csf.backend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archive;
import ibf2022.batch2.csf.backend.services.S3Service;
import ibf2022.batch2.csf.backend.services.Uuid;
import ibf2022.batch2.csf.backend.utils.ContentTypeDetector;
import ibf2022.batch2.csf.backend.utils.CustomMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping(path = "/")
public class UploadController {

	@Autowired
	private Uuid uuidSvc;

	@Autowired
	private S3Service s3Svc;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> upload(@RequestPart MultipartFile zipFile, @RequestPart String name,
		@RequestPart String title, @RequestPart String comments) throws IOException {
			
			Archive archive = new Archive();

			archive.setBundleId(uuidSvc.generateBundleId());
			archive.setName(name);
			archive.setTitle(title);
			archive.setComments(comments);

			// Unzipping the file
			// Create a ZipInputStream from the uploaded zip file
			InputStream is = zipFile.getInputStream();
			ZipInputStream zipIS = new ZipInputStream(is);

			// interate over each entry in the zip file
			ZipEntry entry = zipIS.getNextEntry();
			while (entry != null) {
				String fileName = entry.getName();
				byte[] bytes = new byte[(int) entry.getSize()];

				// convert back to multipart file
				File file = new File(fileName);
				String contentType = ContentTypeDetector.getContentType(file);
				MultipartFile multipartFile = new CustomMultipartFile(name, fileName, contentType, bytes);
				
				// to do for each file, upload to S3?
				System.out.printf(">>> File Name: %s\n", fileName);
				System.out.printf(">>> Content Type: %s\n", contentType);

				String key = "";
				try {
					key = s3Svc.upload(multipartFile, archive.getBundleId());
				}
				catch (IOException e){
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
				}

				zipIS.closeEntry();
				entry = zipIS.getNextEntry();
			}

			return ResponseEntity.status(HttpStatus.OK).body("success");
		}

	// TODO: Task 5
	

	// TODO: Task 6

}
