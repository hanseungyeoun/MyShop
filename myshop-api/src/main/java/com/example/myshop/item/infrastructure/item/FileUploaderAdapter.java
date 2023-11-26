package com.example.myshop.item.infrastructure.item;

import com.example.myshop.item.domain.item.FileUploadService;
import com.example.myshop.uploader.LocalFileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class FileUploaderAdapter implements FileUploadService {

    private final LocalFileUploader localFileUploader;

    @Override
    public List<String> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        return localFileUploader.storeFiles(multipartFiles);
    }

    @Override
    public String storeFile(MultipartFile multipartFile) throws IOException{
        return localFileUploader.storeFile(multipartFile);
    }

    @Override
    public void deleteFile(String fileName) throws IOException{
        localFileUploader.deleteFile(fileName);
    }
}
