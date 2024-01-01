package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc_solutionchallenge.backend.domain.result.dto.FSSOpenApiDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    // 콘텐츠 정보 JSON을 DTO로 변환
    private FSSOpenApiDto makeFFSDto(JsonNode atchfileNode) {
        String atchfileUrl = atchfileNode.get("atchfileUrl").asText();
        String atchfileNm = atchfileNode.get("atchfileNm").asText();
        return new FSSOpenApiDto(atchfileUrl, atchfileNm);
    }
}
