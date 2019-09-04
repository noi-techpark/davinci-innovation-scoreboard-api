package it.bz.davinci.innovationscoreboard.utils.rest;

import it.bz.davinci.innovationscoreboard.utils.io.InMemoryFile;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class RestResponseFactory {

    public ResponseEntity<Resource> createFileResponse(InMemoryFile file) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .contentLength(file.getContent().length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getContent(), file.getFilename()));
    }

    public ResponseEntity<Resource> createFileResponse(File file, String contentType) throws IOException {
        InputStream is = new FileInputStream(file);
        final byte[] bytes = IOUtils.toByteArray(is);

        return createFileResponse(new InMemoryFile(bytes, file.getName(), contentType));
    }
}
