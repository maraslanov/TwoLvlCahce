package com.example.twolvlcahce;


import com.example.twolvlcahce.cache.Cache;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class CacheProperties {

    private static final Logger logger = LoggerFactory.getLogger(Cache.class);

    public static int cacheSize1lvl;

    public static int cacheSize2lvl;

    public static int requestsForSerialize;

    public static String filePath;

    public int getCacheSize1lvl() {
        return cacheSize1lvl;
    }

    @Value("${cacheSize1lvl:2}")
    public void setCacheSize1lvl(int value) {
        this.cacheSize1lvl = value;
    }

    public int getCacheSize2lvl() {
        return cacheSize2lvl;
    }

    @Value("${cacheSize2lvl:1000}")
    public void setCacheSize2lvl(int value) {
        this.cacheSize2lvl = value;
    }

    public int getRequestsForSerialize() {
        return requestsForSerialize;
    }

    @Value("${requestsForSerialize:2}")
    public void setRequestsForSerialize(int value) {
        this.requestsForSerialize = value;
    }

    public String getFilePath() {
        return filePath;
    }

    @Value("${filePath:temp\\}")
    public void setFilePath(String value) {
        this.filePath = value;
    }

    @PostConstruct
    private void clearDir() {
        File directory = new File(CacheProperties.filePath);
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            logger.error("Error during clearing FileHolder directory");
        }

    }

}