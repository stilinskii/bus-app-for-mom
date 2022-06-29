package com.mombusapp.mombusapp.busInfo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
public class BusApiController {

    private BusApi busApi = new BusApi();

    @GetMapping("/")
    public String getInfo() throws IOException, JAXBException {
        return busApi.getArrivalInfo();
    }

    @GetMapping("/busnum")
    public String getBusNum() throws JAXBException, IOException {
        return busApi.getBusNum(busApi.getArrivalInfo());
    }

}
