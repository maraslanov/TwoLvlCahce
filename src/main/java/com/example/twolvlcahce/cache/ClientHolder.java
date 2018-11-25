package com.example.twolvlcahce.cache;

import com.example.twolvlcahce.web.pojo.ClientsPojo;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientHolder {

    private static HashMap<String, ClientsPojo> holder = new HashMap<>();

    private static HashMap<String, Integer> frequencyHolder = new HashMap<String, Integer>();

    public static void addToCache(String key, ClientsPojo value) {
        if (!holder.containsValue(value)) {
            frequencyHolder.put(key, 1);
            holder.put(key, value);
        }
    }

    public static ClientsPojo getDataFromHolderByID(String key) {
        if (StringUtils.hasText(key)) {
            if (holder.containsKey(key)) {
                int frequency = frequencyHolder.get(key);
                frequencyHolder.put(key, ++frequency);
                return holder.get(key);
            }
        }
        return null;
    }

    public static void deleteDataByKey(String key) {
        if (holder.containsKey(key)) {
            holder.remove(key);
            frequencyHolder.remove(key);
        }
    }

    public static void clearHolder() {
        holder.clear();
        frequencyHolder.clear();
    }

    public static boolean contains(String key) {
        return holder.containsKey(key);
    }

    public static int getFrecquencyByKey(String key) {
        if (holder.containsKey(key)) {
            return frequencyHolder.get(key);
        }
        return 0;
    }

    public static List<ClientsPojo> getDataList() {
        return new ArrayList<ClientsPojo>(holder.values());
    }
}