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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

@Slf4j
public class BusApi {

    public BusApi() {
    }

    public String getArrivalInfo() throws IOException, JAXBException {
        log.info("getApi access ");
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=YHl9nW394M7v47pQqImVXKdls5fjMA5tKRCD%2BZjjEFfHIWc%2BD6QKWxxmpManad2uIcE1b0Icw1AIhQcxDOUf7A%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("stationId","UTF-8") + "=" + URLEncoder.encode("223000307", "UTF-8")); /*정류소ID*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(XmlResponseTag.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        XmlResponseTag xmlResponseTag = (XmlResponseTag) unmarshaller.unmarshal(url);
        MsgBodyTag[] msgBodyTags = xmlResponseTag.getMsgBodyTags();
        long count = Arrays.stream(msgBodyTags).count();
        BusArrivalListTag[] busArrivalListTags = msgBodyTags[0].getBusArrivalListTags();
        busArrivalListTags[0].getPredictTime1();
        log.info("result={}",count);

//        JsonParser parser = new JsonParser();
//        JsonElement element = parser.parse(line);

        rd.close();
        conn.disconnect();

        //log.info("getArrivalInfo={}",sb.toString());
        return busArrivalListTags[0].getRouteId();
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
            busNum = sb.toString().substring(sb.indexOf("<routeName>"), sb.indexOf("</routeName>"));

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
