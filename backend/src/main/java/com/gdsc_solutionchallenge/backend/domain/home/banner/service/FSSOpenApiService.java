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

    /**
     * Parses the response data to extract the "atchfileUrl".
     *
     * @param responseData The response data from the FSS API.
     * @return The "atchfileUrl" extracted from the response.
     * @throws ParseException           If there is an error in parsing JSON.
     * @throws JsonProcessingException   If there is an error in processing JSON.
     */
    public String fileParsing(String responseData) throws ParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseData);

        // Get the "reponse" object
        JsonNode jsonResponse = jsonNode.get("reponse");

        // Get the "result" array
        JsonNode jsonResult = jsonResponse.get("result");

        // Get the last element from the "result" array
        JsonNode lastResult = jsonResult.get(jsonResult.size() - 2);

        // Get the "atchfileUrl" from the last element
        JsonNode atchfileUrl = lastResult.get("atchfileUrl");

        return atchfileUrl.asText();
    }

    /**
     * Parses the response data to extract the "atchfileNm".
     *
     * @param responseData The response data from the FSS API.
     * @return The "atchfileNm" extracted from the response.
     * @throws ParseException           If there is an error in parsing JSON.
     * @throws JsonProcessingException   If there is an error in processing JSON.
     */
    public String fileNameParsing(String responseData) throws ParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseData);

        // Get the "reponse" object
        JsonNode jsonResponse = jsonNode.get("reponse");

        // Get the "result" array
        JsonNode jsonResult = jsonResponse.get("result");

        // Get the last element from the "result" array
        JsonNode lastResult = jsonResult.get(jsonResult.size() - 2);

        // Get the "atchfileNm" from the last element
        JsonNode atchfileNm = lastResult.get("atchfileNm");

        return atchfileNm.asText();
    }
}
