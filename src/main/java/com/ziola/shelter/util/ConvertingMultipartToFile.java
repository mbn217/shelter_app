package com.ziola.shelter.util;

import com.ziola.shelter.animals.Animal;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class ConvertingMultipartToFile {

    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public String generateFileName(Animal animal) {
        return  animal.getCity() + animal.getName() + animal.getAge();
    }
}
