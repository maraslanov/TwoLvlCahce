package com.example.twolvlcahce;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class TwoLvlCahceApplication {

    public static void main(String[] args) throws IOException {
        File directory = new File("temp\\");
        FileUtils.cleanDirectory(directory);
        SpringApplication.run(TwoLvlCahceApplication.class, args);
    }
}
