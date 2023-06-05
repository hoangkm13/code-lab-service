package com.example.codelabsvc.controller;


import com.example.codelabsvc.constant.*;
import com.example.codelabsvc.controller.request.challenge.ChallengeDTO;
import com.example.codelabsvc.entity.BookmarkedChallenge;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/challenge")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @PostMapping(value = "", produces = "application/json")
    public ApiResponse<Challenge> createChallenge(@Valid @RequestBody ChallengeDTO challengeDTO) throws CustomException {
        Challenge challenge = challengeService.createChallenge(challengeDTO);
        return ApiResponse.successWithResult(challenge);
    }

    @PutMapping(value = "", produces = "application/json")
    public ApiResponse<Challenge> updateChallenge(@Valid @RequestBody ChallengeDTO challengeDTO) throws CustomException {
        Challenge challenge = challengeService.updateChallenge(challengeDTO);
        return ApiResponse.successWithResult(challenge);
    }

    @DeleteMapping(value = "/{challengeId}", produces = "application/json")
    public ApiResponse<Challenge> deleteChallenge(@Valid @PathVariable String challengeId) throws CustomException {
        Challenge challenge = challengeService.deleteChallenge(challengeId);
        return ApiResponse.successWithResult(challenge);
    }

    @PostMapping(value = "/submit-code/{challengeId}", produces = "application/json")
    public ApiResponse<List<TestCase>> submitCode(@RequestParam String language,
                                                  @PathVariable("challengeId") String challengeId,
                                                  @RequestParam(value = WellKnownParam.SOURCE_CODE) MultipartFile sourceCode) throws CustomException {
        return ApiResponse.successWithResult(challengeService.submitCode(language, challengeId, sourceCode));
    }

    @GetMapping(value = "/{challengeId}", produces = "application/json")
    public ApiResponse<Challenge> getChallenge(@Valid @PathVariable("challengeId") String id) throws CustomException {
        return ApiResponse.successWithResult(challengeService.getChallengeById(id));
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public ApiResponse<List<Challenge>> filterChallenges(@RequestBody Map<String, List<String>> fieldValues) {
        return ApiResponse.successWithResult(challengeService.filterChallenge(fieldValues));
    }

    @PostMapping(value = "/bookmarked", produces = "application/json")
    public ApiResponse<BookmarkedChallenge> changeBookmarkedChallenge(@RequestParam String challengeId) throws CustomException {
        BookmarkedChallenge bookmarkedChallenge = challengeService.changeBookmarkStatus(challengeId);
        return ApiResponse.successWithResult(bookmarkedChallenge);
    }

    @GetMapping(value = "/bookmarked", produces = "application/json")
    public ApiResponse<Page<Challenge>> listAllBookmarkedChallenges(@RequestParam(defaultValue = "0", required = false) int page,
                                                                    @RequestParam(defaultValue = "5", required = false) int size) throws CustomException {
        Page<Challenge> challenges = challengeService.listAllBookmarkChallenge(page, size);
        return ApiResponse.successWithResult(challenges);
    }


    @GetMapping(value = "/topic/{topicId}", produces = "application/json")
    public ApiResponse<Page<Challenge>> listAllChallengeByTopic(@PathVariable String topicId, @RequestParam(defaultValue = "0", required = false) int page,
                                                                    @RequestParam(defaultValue = "5", required = false) int size) throws CustomException {
        Page<Challenge> challenges = challengeService.getAllChallengesByTopic(topicId, page, size);
        return ApiResponse.successWithResult(challenges);
    }
}
