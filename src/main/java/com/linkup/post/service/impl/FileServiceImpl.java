package com.linkup.post.service.impl;

import com.linkup.post.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // get file name
        String name = file.getOriginalFilename();

        // generate random file name
        String randomID = UUID.randomUUID().toString();
        String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
        String filePath = path + File.separator + fileName;

        // create folder if needed
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        // copy file
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }

    @Override
    public boolean deleteFile(String path, String fileName) throws IOException {
        path = path.endsWith(File.separator) ? path : path + File.separator;

        File file = new File(path + fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
