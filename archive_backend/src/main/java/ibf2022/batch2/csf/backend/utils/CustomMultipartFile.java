package ibf2022.batch2.csf.backend.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFile implements MultipartFile {
    
    private final String name;
    private final String originalFileName;
    private final String contentType;
    private final byte[] bytes;

    public CustomMultipartFile(String name, String originalFileName, String contentType, byte[] bytes) {
        this.name = name;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.bytes = bytes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return bytes == null ||  bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes != null ? bytes.length : 0;
    }

    @Override
    public byte[] getBytes() throws IOException{
        return bytes;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (OutputStream os = new FileOutputStream(dest)) {
            os.write(bytes);
        }
    }
    
    
}
