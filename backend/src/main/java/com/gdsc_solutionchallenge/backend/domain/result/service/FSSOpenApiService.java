package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public String  dataParsing(String responseData) throws ParseException, JsonProcessingException {
        /*JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData);
        // 가장 큰 JSON 객체 response 가져오기
        JSONObject jsonResponse = (JSONObject) jsonObject.get("reponse");
        // 다음 result 가져오기
        JSONArray jsonResult = (JSONArray) jsonResponse.get("result");
        List<OpenApiDto> result = new ArrayList<>();
        // atchfileUrl 만 포함해서 리스트 만들기
        for (Object o : jsonResult) {
            JSONObject atchfileUrl = (JSONObject) o;
            result.add(makeFFSDto(atchfileUrl));
        }

        return result;*/

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseData);

        // "reponse" 객체 가져오기
        JsonNode jsonResponse = jsonNode.get("reponse");

        // "result" 배열 가져오기
        JsonNode jsonResult = jsonResponse.get("result");

        // "result" 배열에서 마지막 요소 가져오기
        JsonNode lastResult = jsonResult.get(jsonResult.size() - 2);

        // 마지막 요소에서 "atchfileUrl" 가져오기
        JsonNode lastAtchfileUrlNode = lastResult.get("atchfileUrl");

        return lastAtchfileUrlNode.asText();

    }

    // 콘텐츠 정보 JSON을 DTO로 변환
    private OpenApiDto makeFFSDto(JSONObject atchfileUrl) {
        OpenApiDto dto = OpenApiDto.builder().
                atchfileUrl((String) atchfileUrl.get("atchfileUrl")).
                build();
        return dto;
    }
}
