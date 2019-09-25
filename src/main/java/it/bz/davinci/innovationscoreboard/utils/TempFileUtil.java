package it.bz.davinci.innovationscoreboard.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class TempFileUtil {

    public static String saveMultipartFileToTempFile(MultipartFile file, String prefix, String suffix) throws IOException {
        final File tempFile = File.createTempFile(prefix, suffix);
        file.transferTo(tempFile);

        return tempFile.getAbsolutePath();
    }

}
