package com.Server.service.config;

import com.Server.exception.OurException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Service
public class AwsS3Config {
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.access.key}")
    private String awsS3AccessKey;

    @Value("${aws.s3.secret.key}")
    private String awsS3SecreteKey;

    private String bucketUrl;

    private AmazonS3 s3Client;

    public AwsS3Config() {
    }

    // Phương thức này sẽ được gọi sau khi các thuộc tính được inject
    @PostConstruct
    public void initializeS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecreteKey);

        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();

        bucketUrl = "https://" + bucketName + ".s3.amazonaws.com/";
    }

    public String saveImageToS3(MultipartFile photo) {
        try {
            String s3FileName = UUID.randomUUID().toString() + "-"
                    + photo.getOriginalFilename().replaceAll("\\s+", "_");

            InputStream inputStream = photo.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();

            // Xác định content type
            String contentType = null;
            if (s3FileName != null) {
                if (s3FileName.endsWith(".png")) {
                    contentType = "image/png";
                } else if (s3FileName.endsWith(".jpg") || s3FileName.endsWith(".jpeg")) {
                    contentType = "image/jpeg";
                }
            }
            if (contentType == null) {
                throw new OurException("Only accept files with format JPG, JPEG, or PNG");
            }
            metadata.setContentType(contentType);

            // 🚀 **Fix: Thêm content length**
            metadata.setContentLength(photo.getSize()); // 📌 Đây là bước quan trọng

            // Upload file lên S3
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, metadata);
            s3Client.putObject(putObjectRequest);

            return bucketUrl + s3FileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OurException(e.getMessage());
        }
    }

    public void deleteImageFromS3(String fileUrl) {
        try {
            String fileName = fileUrl.replace(bucketUrl, "");

            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, fileName);
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OurException("Error occurred while deleting the image: " + e.getMessage());
        }
    }
}
