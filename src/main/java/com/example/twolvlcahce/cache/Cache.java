package com.example.twolvlcahce.cache;

import com.example.twolvlcahce.web.controller.ClientController;
import com.example.twolvlcahce.web.pojo.ClientsPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

public class Cache {
    private final static int cacheSize1lvl = 1;
    private final static int cacheSize2lvl = 1;

    private static int requestCount;
    private final static int requestsForSerialize = 1;
    private static final Logger logger = LoggerFactory.getLogger(Cache.class);

    public static void addData(String key, ClientsPojo value) {
        if (ClientHolder.size() >= cacheSize1lvl) {
            recache();
        }
        if (ClientHolder.size() < cacheSize1lvl) {
            ClientHolder.addToCache(key, value);
        }
    }

    public static void recache() {
        HashSet<String> keySet = new HashSet<>(ClientHolder.getFrequencyHolderKeys());
        Integer boundFrecquency = Integer.MAX_VALUE;

        for (String key : keySet) {
            Integer keyFreq = ClientHolder.getFrecquencyByKey(key);
            if (keyFreq != null && boundFrecquency.intValue() > keyFreq.intValue()) {
                boundFrecquency = keyFreq;
            }
        }
        //если кэш заполнен - нужно отчистить
        if (FileHolder.size() >= cacheSize2lvl) {
            logger.error("Объем для кэша на жестком диске переполнен, обратитесь к администратору");
            return;
        }
        for (String key : keySet) {
            Integer keyFreq = ClientHolder.getFrecquencyByKey(key);
            if (keyFreq != null && keyFreq.intValue() == boundFrecquency.intValue()) {
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
            if (requestCount > requestsForSerialize) {
                recache();
                requestCount = 0;
            }
            return client;
        }
        client = FileHolder.getDataFromHolderByID(key);
        if (client != null) {
            requestCount++;
            if (requestCount > requestsForSerialize) {
                recache();
                requestCount = 0;
            }
            return client;
        }
        return null;
    }
}
