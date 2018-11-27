package com.example.twolvlcahce.cache;

import com.example.twolvlcahce.CacheProperties;
import com.example.twolvlcahce.web.pojo.ClientsPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

public class Cache {

    private static int requestCount;

    private static final Logger logger = LoggerFactory.getLogger(Cache.class);

    public static void addData(String key, ClientsPojo value) {
        if (ClientHolder.size() >= CacheProperties.cacheSize1lvl) {
            recache();
        }
        if (ClientHolder.size() < CacheProperties.cacheSize1lvl) {
            ClientHolder.addToCache(key, value);
        }
    }

    public static void recache() {
        HashSet<String> keySet = new HashSet<>(ClientHolder.getFrequencyHolderKeys());
        Integer minFrequency = Integer.MAX_VALUE;

        for (String key : keySet) {
            Integer keyFreq = ClientHolder.getFrecquencyByKey(key);
            if (keyFreq != null && minFrequency.intValue() > keyFreq.intValue()) {
                minFrequency = keyFreq;
            }
        }
        if (FileHolder.size() >= CacheProperties.cacheSize2lvl) {
            logger.error("Объем для кэша на жестком диске переполнен, обратитесь к администратору");
            return;
        }
        for (String key : keySet) {
            Integer keyFreq = ClientHolder.getFrecquencyByKey(key);
            if (keyFreq != null && keyFreq.intValue() == minFrequency.intValue()) {
                ClientsPojo client = ClientHolder.removeDataByKey(key);
                if (client != null) {
                    FileHolder.addToCache(client.getId(), client);
                }
            }
        }

    }

    public static ClientsPojo getData(String key) {
        ClientsPojo client = ClientHolder.getDataFromHolderByID(key);
        if (client != null) {
            requestCount++;
            if (requestCount > CacheProperties.requestsForSerialize) {
                recache();
                requestCount = 0;
            }
            return client;
        }
        client = FileHolder.getDataFromHolderByID(key);
        if (client != null) {
            requestCount++;
            if (requestCount >  CacheProperties.requestsForSerialize) {
                recache();
                requestCount = 0;
            }
            return client;
        }
        return null;
    }
}
