package ibf2022.batch2.csf.backend.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archive;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import ibf2022.batch2.csf.backend.services.Uuid;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping(path = "/api")
public class UploadController {

	@Autowired
	private Uuid uuidSvc;

	@Autowired
	private ImageRepository imageRepo;

	@Autowired
	private ArchiveRepository archiveRepo;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> upload(@RequestPart MultipartFile zipFile, @RequestPart String name,
		@RequestPart String title, @RequestPart String comments) throws IOException {
			
			Archive archive = new Archive();
			List<String> urlsList = new LinkedList<>();

			archive.setBundleId(uuidSvc.generateBundleId());
			archive.setDate(LocalDateTime.now().toString());
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
				byte[] fileContent = zipIS.readAllBytes();
				File unzippedFile = new File(fileName);
				FileOutputStream fos = new FileOutputStream(unzippedFile);
				fos.write(fileContent);
				fos.close();
				
				System.out.printf(">>> File Name: %s\n", fileName);
				// System.out.printf(">>> Content Type: %s\n", contentType);

				// to do for each file, upload to S3
				String key = "";
				try {
					key = imageRepo.upload(unzippedFile, archive.getBundleId());
				}
				catch (IOException e){
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
				}
				String imageUrl = "https://yongli033.sgp1.digitaloceanspaces.com/" 
									+ archive.getBundleId() 
									+ "-"
									+ fileName.replaceAll(" ", "%20");
				
				urlsList.add(imageUrl);

				zipIS.closeEntry();
				entry = zipIS.getNextEntry();
			}

			// Set the urls
			archive.setUrls(urlsList);

			Archive res = archiveRepo.recordBundle(archive);

			JsonObject obj = Json.createObjectBuilder()
									.add("bundleId", res.getBundleId())
									.build();

			return ResponseEntity.status(HttpStatus.OK).body(obj.toString());
		}
	
		// https://yongli033.sgp1.digitaloceanspaces.com/d80d4739-Michael%20Jacksons%20Moonwalker.png

	// TODO: Task 5
	

	// TODO: Task 6

}
