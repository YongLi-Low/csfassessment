package ibf2022.batch2.csf.backend.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;

public class ContentTypeDetector {
    
    public static String getContentType(File file) throws IOException {
        String contentType = null;
        String extension = getFileExtension(file.getName());
        if (extension != null) {
            contentType = URLConnection.guessContentTypeFromName(file.getName());
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return null;
        }
    }
}
