package com.mombusapp.mombusapp.busInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class BusInfoDTO {
    private String busnum;
    private String predictTime1; // 첫번째 버스도착 예정시간
    private String predictTime2; // 두번째 버스도착 예정시간

}
