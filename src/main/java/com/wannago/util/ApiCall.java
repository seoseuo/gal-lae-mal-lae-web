package com.wannago.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.wannago.dto.Cat1DTO;
import com.wannago.dto.Cat2DTO;
import com.wannago.dto.Cat3DTO;
import com.wannago.dto.TourSpotsDTO;
import com.wannago.dto.LocationDoDTO;
import com.wannago.dto.LocationSiDTO;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

@Component
public class ApiCall {
    @Value("${custom.openapi.key}")
    private String key;

    @Value("${custom.openapi.url}")
    private String url;

    @Value("${custom.openapi.OS}")
    private String OS;
    
    @Value("${spring.application.name}")
    private String appName;

    public String getCategoryData() {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder urlBuilder = new StringBuilder();
        int numOfRows = 100;
        urlBuilder.append(url)
                .append("/categoryCode1")
                .append("?MobileOS=")
                .append(OS)
                .append("&MobileApp=")
                .append(appName)
                .append("&serviceKey=")
                .append(key)
                .append("&numOfRows=")
                .append(numOfRows)
                .append("&_type=json");
        URI uri = URI.create(urlBuilder.toString());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String response = restTemplate.getForObject(uri, String.class);
        return response;
    }

    public String getCategoryData2(String c1_code) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder urlBuilder = new StringBuilder();
        int numOfRows = 100;
        urlBuilder.append(url)
                .append("/categoryCode1")
                .append("?MobileOS=")
                .append(OS)
                .append("&MobileApp=")
                .append(appName)
                .append("&serviceKey=")
                .append(key)
                .append("&numOfRows=")
                .append(numOfRows)
                .append("&cat1=")
                .append(c1_code)
                .append("&_type=json");
        URI uri = URI.create(urlBuilder.toString());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String response = restTemplate.getForObject(uri, String.class);
        return response;
    }

    public String getCategoryData3(String c1_code, String c2_code) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder urlBuilder = new StringBuilder();
        int numOfRows = 100;
        urlBuilder.append(url)
                .append("/categoryCode1")
                .append("?MobileOS=")
                .append(OS)
                .append("&MobileApp=")
                .append(appName)
                .append("&serviceKey=")
                .append(key)
                .append("&numOfRows=")
                .append(numOfRows)
                .append("&cat1=")
                .append(c1_code)
                .append("&cat2=")
                .append(c2_code)
                .append("&_type=json");
        URI uri = URI.create(urlBuilder.toString());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String response = restTemplate.getForObject(uri, String.class);
        return response;
    }
    public List<Cat1DTO> getCat1List() {
        List<Cat1DTO> cat1DTOList = new ArrayList<>();
        String data = getCategoryData();
        JSONObject jsonObject = new JSONObject(data);
        JSONObject response = jsonObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONArray items = body.getJSONObject("items").getJSONArray("item");
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            Cat1DTO cat1DTO = new Cat1DTO();
            cat1DTO.setC1Code(item.getString("code"));
            cat1DTO.setC1Name(item.getString("name"));
            cat1DTOList.add(cat1DTO);
        }
        return cat1DTOList;
    }

    public List<Cat2DTO> getCat2List(String c1_code) {
        List<Cat2DTO> cat2DTOList = new ArrayList<>();
        String data = getCategoryData2(c1_code);
        JSONObject jsonObject = new JSONObject(data);
        JSONObject response = jsonObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONArray items = body.getJSONObject("items").getJSONArray("item");
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            Cat2DTO cat2DTO = new Cat2DTO();
            cat2DTO.setC1Code(c1_code);
            cat2DTO.setC2Code(item.getString("code"));
            cat2DTO.setC2Name(item.getString("name"));
            cat2DTOList.add(cat2DTO);
        }
        return cat2DTOList;
    }

    public List<Cat3DTO> getCat3List(String c1_code, String c2_code) {
        List<Cat3DTO> cat3DTOList = new ArrayList<>();
        String data = getCategoryData3(c1_code, c2_code);
        JSONObject jsonObject = new JSONObject(data);
        JSONObject response = jsonObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONArray items = body.getJSONObject("items").getJSONArray("item");
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            Cat3DTO cat3DTO = new Cat3DTO();
            cat3DTO.setC2Code(c2_code);
            cat3DTO.setC3Code(item.getString("code"));
            cat3DTO.setC3Name(item.getString("name"));
            cat3DTOList.add(cat3DTO);
        }
        return cat3DTOList;
    }

    public String getAreaData1() {
        RestTemplate restTemplate = new RestTemplate();
        int numOfRows = 100;
        StringBuilder urlBuilder = new StringBuilder();
        //https://apis.data.go.kr/B551011/KorService1/areaCode1?numOfRows=100&MobileOS=WIN&MobileApp=wannago&areaCode=1&_type=json&serviceKey=JtURp4SNjOc0mc72R8o8uzI9afz%2FR0UknJeRaXOKeUclJm%2BukYcFsuNNAzd4ovPr3u32oihd%2BfolG4TmBAMq5A%3D%3D
        urlBuilder.append(url)
                .append("/areaCode1")
                .append("?MobileOS=")
                .append(OS)
                .append("&MobileApp=")
                .append(appName)
                .append("&serviceKey=")
                .append(key)
                .append("&numOfRows=")
                .append(numOfRows)
                .append("&_type=json");
        URI uri = URI.create(urlBuilder.toString());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String response = restTemplate.getForObject(uri, String.class);
        return response;
    }

    public String getAreaData2(int areaCode) {
        RestTemplate restTemplate = new RestTemplate();
        int numOfRows = 100;
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url)
                .append("/areaCode1")
                .append("?MobileOS=")
                .append(OS)
                .append("&MobileApp=")
                .append(appName)
                .append("&serviceKey=")
                .append(key)
                .append("&areaCode=")
                .append(areaCode)
                .append("&numOfRows=")
                .append(numOfRows)
                .append("&_type=json");
        URI uri = URI.create(urlBuilder.toString());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String response = restTemplate.getForObject(uri, String.class);
        return response;
    }
    
    public List<LocationDoDTO> getLocationList() {
        String data = getAreaData1();
        List<LocationDoDTO> locationDTOList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);
        JSONObject response = jsonObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONArray items = body.getJSONObject("items").getJSONArray("item");
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            LocationDoDTO locationDTO = new LocationDoDTO();
            int ldIdx = Integer.parseInt(item.getString("code"));
            String ldName = item.getString("name");
            locationDTO.setLdIdx(ldIdx);
            locationDTO.setLdName(ldName);
            locationDTOList.add(locationDTO);
        }
        return locationDTOList;
    }

    public List<LocationSiDTO> getLocationSiList(int areaCode) {
        String data = getAreaData2(areaCode);
        List<LocationSiDTO> locationDTOList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(data);
        JSONObject response = jsonObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONArray items = body.getJSONObject("items").getJSONArray("item");
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            LocationSiDTO locationDTO = new LocationSiDTO();
            int siIdx = Integer.parseInt(item.getString("code"));
            String siName = item.getString("name");
            locationDTO.setLsIdx(siIdx);
            locationDTO.setLsName(siName);
            locationDTO.setLdIdx(areaCode);
            locationDTOList.add(locationDTO);
        }
        return locationDTOList;
    }

    public String getTourSpotData(int pageNo, int numOfRows) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder urlBuilder = new StringBuilder();
        // https://apis.data.go.kr/B551011/KorService1/areaBasedList1?numOfRows=100&pageNo=1&MobileOS=WIN&MobileApp=wannago&_type=json&areaCode=1&sigunguCode=1&serviceKey=JtURp4SNjOc0mc72R8o8uzI9afz%2FR0UknJeRaXOKeUclJm%2BukYcFsuNNAzd4ovPr3u32oihd%2BfolG4TmBAMq5A%3D%3D
        urlBuilder.append(url)
                .append("/areaBasedList1")  
                .append("?MobileOS=")
                .append(OS)
                .append("&MobileApp=")
                .append(appName)
                .append("&serviceKey=")
                .append(key)
                .append("&numOfRows=")
                .append(numOfRows)
                .append("&pageNo=")
                .append(pageNo)
                .append("&_type=json");
        URI uri = URI.create(urlBuilder.toString());
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        String response = restTemplate.getForObject(uri, String.class);
        return response;
    }

    public List<TourSpotsDTO> getTourSpotList(int pageNo) {
        String prevData = getTourSpotData(pageNo, 1);
        List<TourSpotsDTO> tourSpotDTOList = new ArrayList<>();
        JSONObject prevJsonObject = new JSONObject(prevData);
        JSONObject prevResponse = prevJsonObject.getJSONObject("response");
        JSONObject prevBody = prevResponse.getJSONObject("body");
        int totalCount = prevBody.getInt("totalCount");
        int numOfRows = 150;
        int totalPage = totalCount % numOfRows > 0 ? totalCount / numOfRows + 1 : totalCount / numOfRows;
        int errorCount = 0;
        String data = "";
        for (int i = 1; i <= totalPage; i++) {
            try {
                data = getTourSpotData(i , numOfRows);
                JSONObject jsonObject = new JSONObject(data);
                JSONObject response = jsonObject.getJSONObject("response");
                JSONObject body = response.getJSONObject("body");
            JSONArray items = body.getJSONObject("items").getJSONArray("item");
            for (int j = 0; j < items.length(); j++) {
                JSONObject item = items.getJSONObject(j);
                TourSpotsDTO tourSpotDTO = new TourSpotsDTO();
                tourSpotDTO.setTsIdx(item.getInt("contentid"));
                tourSpotDTO.setTsName(item.getString("title"));
                tourSpotDTO.setTsFirstImage(item.getString("firstimage"));
                tourSpotDTO.setTsFirstImage2(item.getString("firstimage2"));
                tourSpotDTO.setTsMapx(Float.parseFloat(item.getString("mapx")));
                tourSpotDTO.setTsMapy(Float.parseFloat(item.getString("mapy")));
                String mlevel = item.getString("mlevel");
                tourSpotDTO.setTsMlevel(Byte.parseByte(mlevel.isEmpty() ? "6" : mlevel));
                tourSpotDTO.setTsAddr1(item.getString("addr1"));
                tourSpotDTO.setTsAddr2(item.getString("addr2"));
                tourSpotDTO.setTsZipcode(item.getString("zipcode"));
                String tel = item.getString("tel");
                if (tel.length() > 100) {
                    tel = tel.substring(0, 100);
                }
                tourSpotDTO.setTsTel(tel);
                tourSpotDTO.setTsContentTypeId(item.getString("contenttypeid"));
                tourSpotDTO.setTsBookTour(item.getString("booktour"));
                tourSpotDTO.setTsCpyrhtDivCd(item.getString("cpyrhtDivCd"));
                tourSpotDTO.setTsCreatedTime(item.getString("createdtime"));
                tourSpotDTO.setTsModifiedTime(item.getString("modifiedtime"));
                String ldIdx = item.getString("areacode");
                tourSpotDTO.setLdIdx(ldIdx.isEmpty() ? null : Integer.parseInt(ldIdx));
                String lsIdx = item.getString("sigungucode");
                tourSpotDTO.setLsIdx(lsIdx.isEmpty() ? null : Integer.parseInt(lsIdx));
                tourSpotDTO.setC1Code(item.getString("cat1"));
                tourSpotDTO.setC2Code(item.getString("cat2"));
                tourSpotDTO.setC3Code(item.getString("cat3"));
                tourSpotDTOList.add(tourSpotDTO);
            }
            } catch (Exception e) {
                System.err.println("pageNo: " + i);
                System.err.println("data: " + data);
                System.err.println("error: " + e.getMessage());
                errorCount++;
                if (errorCount > 5) {
                    break;
                }
                i--;
            }
        }
        return tourSpotDTOList;
    }
} 
