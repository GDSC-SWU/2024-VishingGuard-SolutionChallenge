package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.result.dto.OpenApiDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FSSOpenApiService {
    public List<OpenApiDto> dataParsing(String responseData) throws ParseException {

        RestTemplate restTemplate = new RestTemplate();
        //String jsonString = restTemplate.getForObject(makeUrl(), String.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData);
        // 가장 큰 JSON 객체 response 가져오기
        JSONObject jsonResponse = (JSONObject) jsonObject.get("reponse");
        //JSONObject jsonResponse1 = (JSONObject) jsonResponse.get("response");

        // 그 다음 body 부분 파싱
       // JSONObject jsonResult = (JSONObject) jsonResponse.get("result");

        // 그 다음 위치 정보를 배열로 담은 items 파싱
        //JSONObject jsonURL = (JSONObject) jsonResult.get("atchfileUrl");
        JSONArray jsonResult = (JSONArray) jsonResponse.get("result");
        List<OpenApiDto> result = new ArrayList<>();
        for (Object o : jsonResult) {
            JSONObject item = (JSONObject) o;
            result.add(makeLocationDto(item));
        }

        /*// items는 JSON임, 이제 그걸 또 배열로 가져온다
        JSONArray jsonItemList = (JSONArray) jsonURL.get("item");

        List<OpenApiDto> result = new ArrayList<>();

        for (Object o : jsonItemList) {
            JSONObject item = (JSONObject) o;
            result.add(makeLocationDto(item));
        }*/
        return result;
       /* try {
           *//* ObjectMapper objectMapper = new ObjectMapper();

            // JSON 데이터를 YourDataClass 객체로 매핑
            YourDataClass yourData = objectMapper.readValue(responseData, YourDataClass.class);

            // result 배열에서 contentId가 "59532"인 객체를 찾기
            Optional<FinancialData> financialDataOptional = yourData.getResponse().getResult().stream()
                    .filter(data -> "59532".equals(data.getContentId()))
                    .findFirst();

            if (financialDataOptional.isPresent()) {
                FinancialData financialData = financialDataOptional.get();
                // 이제 financialData 객체를 이용하여 필요한 작업을 수행
                // 예: contentId, brmCode, brmTrans, subject 등을 가져오기
                String contentId = financialData.getContentId();
                String brmCode = financialData.getBrmCode();
                String brmTrans = financialData.getBrmTrans();
                String subject = financialData.getSubject();
                // ... 나머지 필요한 데이터 가져오기

                // 가져온 데이터를 가공하거나 원하는 형태로 변환
                String parsedData = "contentId: " + contentId + ", brmCode: " + brmCode + ", subject: " + subject;

                // 가공한 데이터 반환
                return parsedData;
            } else {
                // 해당 contentId를 가진 데이터가 없는 경우 처리
                return "Data with contentId '59532' not found";
            }*//*

        } catch (Exception e) {
            // 예외 처리
            return "Error during data parsing: " + e.getMessage();
        }*/
    }

    // 콘텐츠 정보 JSON을 DTO로 변환
    private OpenApiDto makeLocationDto(JSONObject item) {
        OpenApiDto dto = OpenApiDto.builder().
                atchfileUrl((String) item.get("atchfileUrl")).
                build();
        return dto;
    }
}
