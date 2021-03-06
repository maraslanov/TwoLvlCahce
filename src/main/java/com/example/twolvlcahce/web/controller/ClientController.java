package com.example.twolvlcahce.web.controller;

import com.example.twolvlcahce.cache.Cache;
import com.example.twolvlcahce.web.pojo.ClientsPojo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Api(description = "Операции с кэшем")
public class ClientController {

    @ApiOperation(value = "Добавляет данные в кэш")
    @RequestMapping(value = "/clients/add", method = RequestMethod.POST)
    public ResponseEntity<String> addClient(@RequestParam("fio") String fio, @RequestParam("address") String adress) {
        ClientsPojo newClient = new ClientsPojo(fio,adress);
        Cache.addData(newClient.getId(),newClient);
        return new ResponseEntity<>(newClient.getId(),HttpStatus.OK);
    }

    @ApiOperation(value = "Получает данные из кэша по параметру")
    @RequestMapping(value = "/clients/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<ClientsPojo> getClient(@PathVariable("id") String id) {
        return new ResponseEntity<>(Cache.getData(id), HttpStatus.OK);
    }
}
