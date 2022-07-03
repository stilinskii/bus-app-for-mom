package com.mombusapp.mombusapp.busInfo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Comparator;
import java.util.List;

import static com.mombusapp.mombusapp.busInfo.BusStationIdConst.*;

@Slf4j
@Controller
public class BusInfoController {


    private BusApi busApi = new BusApi();

    @GetMapping("/")
    public String getInfo(Model model) {

        List<BusInfoDTO> busToOsanInfo = sortBusInfoByPredictTimeASC(busApi.getArrivalInfo(TO_OSAN_STATION_ID));
        List<BusInfoDTO> busToSemaInfo = sortBusInfoByPredictTimeASC(busApi.getArrivalInfo(TO_SEMA_STATION_ID));

        model.addAttribute("busToOsanInfo",busToOsanInfo);
        model.addAttribute("busToSemaInfo",busToSemaInfo);
        return "index";
    }

    public List<BusInfoDTO> sortBusInfoByPredictTimeASC(List<BusInfoDTO> busInfoDTO){
        if(busInfoDTO==null){
            return null;
        }
        busInfoDTO.sort((Comparator.comparingInt(o -> Integer.parseInt(o.getPredictTime1()))));
        return busInfoDTO;
    }


    @ResponseBody
    @GetMapping("/json")
    public List<BusInfoDTO> getInfoTest(){
        List<BusInfoDTO> busArrivalInfo = busApi.getArrivalInfo(TO_OSAN_STATION_ID);
        return busArrivalInfo;
    }

}
