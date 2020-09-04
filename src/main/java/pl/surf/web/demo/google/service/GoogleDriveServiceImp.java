package pl.surf.web.demo.google.service;

import java.net.URL;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;


@Service
public class GoogleDriveServiceImp implements GoogleDriveService{

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleDriveServiceImp.class);

    @Value("${google.service_account_email}")
    private String serviceAccountEmail;

    @Value("${google.application_name}")
    private String applicationName;

    @Value("${google.service_account_key}")
    private String serviceAccountKey;

    @Value("${google.folder_id}")
    private String folderID;

    public Drive getDriveService() {
        Drive service = null;
        try {

            URL resource = GoogleDriveServiceImp.class.getResource("/" + this.serviceAccountKey);
            java.io.File key = Paths.get(resource.toURI()).toFile();

            HttpTransport httpTransport = new NetHttpTransport();
            JacksonFactory jsonFactory = new JacksonFactory();

            GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
                    .setJsonFactory(jsonFactory).setServiceAccountId(serviceAccountEmail)
                    .setServiceAccountScopes(Collections.singleton(DriveScopes.DRIVE))
                    .setServiceAccountPrivateKeyFromP12File(key)
                    .build();
            service = new Drive.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName(applicationName)
                    .setHttpRequestInitializer(credential)
                    .build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());

        }

        return service;

    }

//    @Override
//    public File uoLoadFile(String fileName, String filePath, String mimeType) {
//        File file = new File();
//        try {
//            java.io.File fileUpload = new java.io.File(filePath);
//            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
//            fileMetadata.setMimeType(mimeType);
//            fileMetadata.setName(fileName);
//            fileMetadata.setParents(Collections.singletonList(folderID));
//            com.google.api.client.http.FileContent fileContent = new FileContent(mimeType, fileUpload);
//            file = getDriveService().files().create(fileMetadata, fileContent)
//                    .setFields("id,webContentLink,webViewLink").execute();
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        }
//        return file;
//    }
    @Override
    public File uoLoadFile(String fileName, String filePath, String mimeType) {
        File file = new File();
        try {
            java.io.File fileUpload = new java.io.File(filePath);
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setMimeType(mimeType);
            fileMetadata.setName(fileName);
            fileMetadata.setParents(Collections.singletonList(folderID));
            com.google.api.client.http.FileContent fileContent = new FileContent(mimeType, fileUpload);
            file = getDriveService().files().create(fileMetadata, fileContent)
                    .setFields("id,webContentLink,webViewLink").execute();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return file;
    }
//    @Override
//    public File uoLoadFile(MultipartFile mu) {
//        final String originalFilename = mu.getOriginalFilename();
//        File file = new File();
//        try {
//            java.io.File fileUpload = new java.io.File(originalFilename);
//            fileUpload.createNewFile();
//            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
//            fileMetadata.setMimeType(mu.getContentType());
//            fileMetadata.setName(mu.getName());
//            fileMetadata.setParents(Collections.singletonList(folderID));
//            com.google.api.client.http.FileContent fileContent = new FileContent(mu.getContentType(), fileUpload);
//            file = getDriveService().files().create(fileMetadata, fileContent)
//                    .setFields("id,webContentLink,webViewLink").execute();
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        }
//        return file;
//    }
}