package com.mombusapp.mombusapp.busInfo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BusApiController {

    @Value("${stationId.sema}")
    private String busStationToSema;

    @Value("${stationId.osan}")
    private String busStationToOsan;

    private BusApi busApi = new BusApi();

    //@GetMapping("/")
    public String getInfo(Model model) {
        List<BusInfoDTO> busToOsanArrivalInfo = busApi.getArrivalInfo(busStationToOsan);
        List<BusInfoDTO> busToSemaArrivalInfo = busApi.getArrivalInfo(busStationToSema);
        model.addAttribute("busToOsanInfo",busToOsanArrivalInfo);
        model.addAttribute("busToSemaInfo",busToSemaArrivalInfo);
        return "index";
    }




    @ResponseBody
    @GetMapping("/json")
    public List<BusInfoDTO> getInfoTest(){
        List<BusInfoDTO> busArrivalInfo = busApi.getArrivalInfo(busStationToSema);
        return busArrivalInfo;
    }

}
