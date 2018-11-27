package com.example.twolvlcahce.cache;

import com.example.twolvlcahce.CacheProperties;
import com.example.twolvlcahce.web.pojo.ClientsPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;


public class FileHolder {

    private static HashMap<String, String> holder = new HashMap<>();
    private static HashMap<String, Integer> frequencyHolder = new HashMap<String, Integer>();

    private static final Logger logger = LoggerFactory.getLogger(FileHolder.class);

    public static void addToCache(String key, ClientsPojo value) {
        File fileFolder = new File(CacheProperties.filePath);
        String pathname = fileFolder.getAbsolutePath() + "\\" + UUID.randomUUID().toString();
        frequencyHolder.put(key, 1);
        holder.put(key, pathname);
        FileOutputStream fileStream = null;
        try {
            fileStream = new FileOutputStream(pathname);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(value);
            objectStream.close();
            fileStream.close();
        } catch (FileNotFoundException e) {
            logger.error("FileHolder problem with wrong path during adding to cache");
        } catch (IOException e) {
            logger.error("FileHolder problem with creating stream during adding to cache");
        }
    }

    public static ClientsPojo getDataFromHolderByID(String key) {
        String pathToObject = holder.get(key);
        if (StringUtils.hasText(pathToObject)) {
            try {
                FileInputStream fileStream = new FileInputStream(pathToObject);
                ObjectInputStream objectStream = new ObjectInputStream(fileStream);
                int frecquency = frequencyHolder.remove(key);
                frequencyHolder.put(key, ++frecquency);
                ClientsPojo deserialized = (ClientsPojo) objectStream.readObject();

                fileStream.close();
                objectStream.close();
                return deserialized;
            } catch (FileNotFoundException e) {
                logger.error("FileHolder problem with wrong path during getting Data from cache");
                return null;
            } catch (IOException e) {
                logger.error("FileHolder problem with creating stream during getting Data from cache");
                return null;
            } catch (ClassNotFoundException e) {
                logger.error("FileHolder problem with reading object during getting Data from cache");
                return null;
            }
        }
        return null;
    }

    public static void clearCache() {
        for (String key : holder.keySet()) {
            File deletingFile = new File(holder.get(key));
            deletingFile.delete();
        }
        holder.clear();
        frequencyHolder.clear();
    }

    public static void deleteObject(String key) {
        if (holder.containsKey(key)) {
            File deletingFile = new File(holder.remove(key));
            frequencyHolder.remove(key);
            deletingFile.delete();
        }
    }

    public static int size() {
        return holder.size();
    }
}

