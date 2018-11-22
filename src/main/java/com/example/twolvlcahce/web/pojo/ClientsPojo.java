package com.example.twolvlcahce.web.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClientsPojo {
    private String fio;
    private String adress;

    public ClientsPojo(String fio, String adress) {
        this.fio = fio;
        this.adress = adress;
    }
}