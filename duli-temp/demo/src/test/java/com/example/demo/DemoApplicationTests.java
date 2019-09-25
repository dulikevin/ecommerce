package com.example.demo;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Test
    public void contextLoads() throws FileNotFoundException {
        File file = new File("d://duli.jpg");
        String fileName = file.getName();
        System.out.println("fileName-->"+fileName);
        String extName = fileName.substring(fileName.lastIndexOf(".")+1);
        System.out.println("extName--->"+extName);
        FileInputStream fileInputStream = new FileInputStream(file);
        StorePath storePath = fastFileStorageClient.uploadFile(fileInputStream,file.length(),extName,null);
        System.out.println(storePath.getGroup());
        System.out.println(storePath.getPath());
        System.out.println(storePath.getFullPath());

    }
}
