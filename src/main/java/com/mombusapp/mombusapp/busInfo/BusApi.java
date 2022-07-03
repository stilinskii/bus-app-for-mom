package com.mombusapp.mombusapp.busInfo;

import com.google.gson.stream.MalformedJsonException;
import com.mombusapp.mombusapp.xmlUnmarshal.BusArrivalListTag;
import com.mombusapp.mombusapp.xmlUnmarshal.MsgBodyTag;
import com.mombusapp.mombusapp.xmlUnmarshal.XmlResponseTag;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BusApi {

    public BusApi() {
    }

    //버스들의 도착예정정보가 담긴 어레이 리턴
    public List<BusInfoDTO> getArrivalInfo(String stationId){
        //뷰에 넘길 array
        List<BusInfoDTO> storeBusInfo = new ArrayList<>();
        BufferedReader rd;
        HttpURLConnection conn = null;
        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=YHl9nW394M7v47pQqImVXKdls5fjMA5tKRCD%2BZjjEFfHIWc%2BD6QKWxxmpManad2uIcE1b0Icw1AIhQcxDOUf7A%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("stationId", "UTF-8") + "=" + URLEncoder.encode(stationId, "UTF-8")); /*정류소ID*/
            URL url = new URL(urlBuilder.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());

            //제대로 요청이 처리가 됐을때만 값 불러오기
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            //최종 버스들의 도착예정 정보들이 담긴 어레이
            BusArrivalListTag[] busArrivalListTags = xmlUnmarshallingForBusAPI(url);
            //최종 버스들의 도착예정정보에서 원하는 정보만 dto에 담아 뷰에 넘길 어레이에 저장
            for (BusArrivalListTag busInfo : busArrivalListTags) {
                log.info("predictTime2={}",busInfo.getPredictTime2());
                BusInfoDTO busInfoDTO = new BusInfoDTO(getBusNum(busInfo.getRouteId()), busInfo.getPredictTime1(), busInfo.getPredictTime2());
                storeBusInfo.add(busInfoDTO);
            }
        rd.close();
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            conn.disconnect();
        }




        return storeBusInfo;
    }

    //xml unmarshalling
    private BusArrivalListTag[] xmlUnmarshallingForBusAPI(URL url){
        BusArrivalListTag[] busArrivalListTags = null;
        try{
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlResponseTag.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        XmlResponseTag xmlResponseTag = (XmlResponseTag) unmarshaller.unmarshal(url);
        MsgBodyTag msgBodyTags = xmlResponseTag.getMsgBodyTags();
        busArrivalListTags = msgBodyTags.getBusArrivalListTags();
        }catch (JAXBException e){
            e.printStackTrace();
        }
        return busArrivalListTags;
    }

    public String getBusNum(String routeId){
        String busNum ="";
        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6410000/busrouteservice/getBusRouteInfoItem"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=YHl9nW394M7v47pQqImVXKdls5fjMA5tKRCD%2BZjjEFfHIWc%2BD6QKWxxmpManad2uIcE1b0Icw1AIhQcxDOUf7A%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("routeId", "UTF-8") + "=" + URLEncoder.encode(routeId, "UTF-8")); /*노선ID*/
            URL url = new URL(urlBuilder.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            log.info("result={}", sb.toString());

            //버스 번호만 불러오기
            busNum = sb.toString().substring(sb.indexOf("<routeName>")+11, sb.indexOf("</routeName>"));

            rd.close();
            conn.disconnect();
        }catch (MalformedJsonException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return busNum;
    }
}
