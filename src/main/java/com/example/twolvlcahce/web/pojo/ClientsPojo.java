package com.example.twolvlcahce.web.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ClientsPojo implements Serializable {
    private String id;
    private String fio;
    private String address;

    public ClientsPojo(String fio, String address, String id) {
        this.id = id;
        this.fio = fio;
        this.address = address;
    }

    public ClientsPojo(String fio, String address) {
        this.id = UUID.randomUUID().toString();
        this.fio = fio;
        this.address = address;
    }
}