package it.bz.davinci.innovationscoreboard.stats.storage;

import it.bz.davinci.innovationscoreboard.utils.io.InMemoryFile;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
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

    InMemoryFile download(String s3FileKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(s3FileKey)
                .build();

        final ResponseBytes<GetObjectResponse> object = s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes());

        return InMemoryFile.builder()
                .content(object.asByteArray())
                .contentType("text/csv")
                .filename(s3FileKey)
                .build();
    }
}
