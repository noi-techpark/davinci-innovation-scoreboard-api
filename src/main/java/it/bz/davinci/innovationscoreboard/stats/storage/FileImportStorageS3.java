package it.bz.davinci.innovationscoreboard.stats.storage;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;

@Service
public class FileImportStorageS3 {

    private final String bucket;
    private final S3Client s3Client;

    public FileImportStorageS3(@Value("${aws.bucket.fileImport}") String bucket, S3Client s3Client) {
        this.bucket = bucket;
        this.s3Client = s3Client;
    }

    String upload(String fileName) {
        File file = new File(fileName);

        final String s3FileKey = file.getName();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3FileKey)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));

        return s3FileKey;
    }
}
