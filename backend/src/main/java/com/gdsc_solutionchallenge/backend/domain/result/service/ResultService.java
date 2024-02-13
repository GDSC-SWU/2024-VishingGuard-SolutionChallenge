package com.gdsc_solutionchallenge.backend.domain.result.service;

import com.gdsc_solutionchallenge.backend.domain.auth.domain.User;
import com.gdsc_solutionchallenge.backend.domain.auth.domain.UserRepository;
import com.gdsc_solutionchallenge.backend.domain.report.domain.VishingKeyword;
import com.gdsc_solutionchallenge.backend.domain.report.domain.VishingKeywordRepository;
import com.gdsc_solutionchallenge.backend.domain.result.domain.Smishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.SmishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.domain.Vishing;
import com.gdsc_solutionchallenge.backend.domain.result.domain.VishingRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.VishingReqDto;
import com.gdsc_solutionchallenge.backend.domain.result.domain.db.SmishingKeyword;
import com.gdsc_solutionchallenge.backend.domain.result.domain.db.SmishingKeywordRepository;
import com.gdsc_solutionchallenge.backend.domain.result.dto.SmishingReqDto;
import com.gdsc_solutionchallenge.backend.global.error.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResultService {
    private final SmishingRepository smishingRepository;
    private final SmishingKeywordRepository smishingKeywordRepository;
    private final UserRepository userRepository;
    private final VishingRepository vishingRepository;
    private final VishingKeywordRepository vishingKeywordRepository;

    /**
     * Check if the received message is Smishing and save the script if it contains risky keywords.
     *
     * @param userId            User ID for checking Smishing.
     * @param smishingReqDto    Smishing request data containing the script and timestamp.
     * @return Boolean indicating if the message is Smishing.
     * @throws Exception if the user is not found or an error occurs during keyword check.
     */
    public Boolean isSmishing(Long userId, SmishingReqDto smishingReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));
        boolean keywordResult = false; // Keyword result
        String keywordComment = null;

        List<SmishingKeyword> smishingKeywordList = smishingKeywordRepository.getAllSmishingKeyword();

        // Keyword check
        for (SmishingKeyword keyword : smishingKeywordList) {
            if (keyword != null && removeSpaces(smishingReqDto.getSmishingScript())
                    .contains(removeSpaces(keyword.getSmishing_keyword()))) {
                keywordResult = true; // Set to true if any keyword matches
                keywordComment = "Contains a risk keyword related to smishing. (" + keyword.getSmishing_keyword() + ")";
                break; // Break the loop if a match is found
            }
        }

        if (keywordResult) {
            // Create SmishingScript Entity
            Smishing smishing = Smishing.builder()
                    .user_id(userId)
                    .script(smishingReqDto.getSmishingScript())
                    .phone(smishingReqDto.getPhone())
                    .keyword_comment(keywordComment)
                    .build();

            smishingRepository.save(smishing); // Save the script
        }

        // Return result (T/F)
        return keywordResult;
    }

    /**
     * Save Vishing (voice phishing) script when a Vishing call is received.
     *
     * @param userId          User ID for saving Vishing script.
     * @param vishingReqDto   Vishing request data containing the script and timestamp.
     * @throws Exception if the user is not found or an error occurs during keyword check.
     */
    public void saveVishing(Long userId, VishingReqDto vishingReqDto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "User not found"));

        String keywordComment = null;
        List<VishingKeyword> vishingKeywordList = vishingKeywordRepository.getAllVishingKeyword();

        // Keyword check
        for (VishingKeyword keyword : vishingKeywordList) {
            if (keyword != null && removeSpaces(vishingReqDto.getVishingScript())
                    .contains(removeSpaces(keyword.getVishing_keyword()))) {
                keywordComment = "Contains a risk keyword related to voice phishing. (" + keyword.getVishing_keyword() + ")";
                break; // Break the loop if a match is found
            } else {
                keywordComment = "Contains a risk keyword related to voice phishing.";
            }
        }

        // Create Vishing Entity
        Vishing vishing = Vishing.builder()
                .script(vishingReqDto.getVishingScript())
                .phone(vishingReqDto.getPhone())
                .user_id(userId)
                .keyword_comment(keywordComment)
                .build();

        vishingRepository.save(vishing); // Save the script
    }

    /**
     * Remove spaces from the given input string.
     *
     * @param input Input string.
     * @return String with removed spaces and converted to lowercase.
     */
    private String removeSpaces(String input) {
        return input.replaceAll("\\s", "").toLowerCase();
    }
}
