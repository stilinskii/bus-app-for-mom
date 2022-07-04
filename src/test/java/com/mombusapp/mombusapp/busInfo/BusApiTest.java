package com.mombusapp.mombusapp.busInfo;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mombusapp.mombusapp.busInfo.BusStationIdConst.TO_OSAN_STATION_ID;



class BusApiTest {

    BusApi busApi = new BusApi();

    @Test
    void getArrivalInfo() {
        //given

        //when
        List<BusInfoDTO> arrivalInfo = busApi.getArrivalInfo(TO_OSAN_STATION_ID);
        //then
        Assertions.assertThat(arrivalInfo).isNotNull();
    }

}