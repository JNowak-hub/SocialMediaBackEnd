package pl.surf.web.demo.google.service;


import com.google.api.services.drive.model.File;

public interface GoogleDriveService {

    public File uoLoadFile(String fileName, String filePath, String mimeType);
}
