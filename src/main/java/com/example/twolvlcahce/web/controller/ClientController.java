package com.example.twolvlcahce.web.controller;

import com.example.twolvlcahce.web.pojo.ClientsPojo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Api(description = "Операции с профилем")
public class ClientController {
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @ApiOperation(value = "Выводит данные о профиле")
    @RequestMapping(value = "/clients/add", method = RequestMethod.POST)
    public ResponseEntity addClient(@RequestParam("fio") String fio, @RequestParam("adress") String adress) {
        logger.info("add new Client:");
        ClientsPojo newClient = new ClientsPojo(fio,adress);
        logger.info(newClient.getFio()+ " "+ newClient.getAdress());
        //todo добавление в кэш
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
