package com.gdsc_solutionchallenge.backend.domain.home.banner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FSSOpenApiService {
    public String fileParsing(String responseData) throws ParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseData);

        // "reponse" 객체 가져오기
        JsonNode jsonResponse = jsonNode.get("reponse");

        // "result" 배열 가져오기
        JsonNode jsonResult = jsonResponse.get("result");

        // "result" 배열에서 마지막 요소 가져오기
        JsonNode lastResult = jsonResult.get(jsonResult.size() - 2);

        // 마지막 요소에서 "atchfileUrl" 가져오기
        JsonNode atchfileUrl = lastResult.get("atchfileUrl");

        return atchfileUrl.asText();

    }
    public String fileNameParsing(String responseData) throws ParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseData);

        // "reponse" 객체 가져오기
        JsonNode jsonResponse = jsonNode.get("reponse");

        // "result" 배열 가져오기
        JsonNode jsonResult = jsonResponse.get("result");

        // "result" 배열에서 마지막 요소 가져오기
        JsonNode lastResult = jsonResult.get(jsonResult.size() - 2);

        // 마지막 요소에서 "atchfileNm" 가져오기
        JsonNode atchfileNm = lastResult.get("atchfileNm");

        return atchfileNm.asText();

    }

}
