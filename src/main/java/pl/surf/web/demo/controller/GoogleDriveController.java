package pl.surf.web.demo.controller;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.surf.web.demo.exceptions.IllegalImageFormatException;
import pl.surf.web.demo.exceptions.UserNotFoundException;
import pl.surf.web.demo.google.service.GoogleDriveServiceImp;
import pl.surf.web.demo.model.requests.FileRequest;
import pl.surf.web.demo.model.responses.Image;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.IllegalFormatCodePointException;

@RestController
@RequestMapping("api/upload")
public class GoogleDriveController {

    private GoogleDriveServiceImp googleDriveService;
    private Image image;

    public GoogleDriveController(GoogleDriveServiceImp googleDriveService, Image image) {
        this.googleDriveService = googleDriveService;
        this.image = image;
    }

    @PostMapping()
    public Image uploadImageToDrive(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        final String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (extension.equals("jpg") || extension.equals("png") || extension.equals("webp") || extension.equals("gif") || extension.equals("jpeg")) {
            File convFile = new File(multipartFile.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(multipartFile.getBytes());
            fos.close();
            com.google.api.services.drive.model.File file2 = googleDriveService.uoLoadFile(convFile.getName(), convFile.getAbsolutePath(), "image/jpg");
            image.setId(file2.getId());
            image.setWebContentLink(file2.getWebContentLink());
            image.setWebViewLink(file2.getWebViewLink());
            return image;
        }
        throw new IllegalImageFormatException("Illegal image type");
    }

}
