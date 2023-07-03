package com.example.codelabsvc.controller;


import com.example.codelabsvc.controller.request.challenge.CreateChallengeDTO;
import com.example.codelabsvc.controller.request.challenge.FilterChallengeRequest;
import com.example.codelabsvc.controller.request.challenge.TestCaseSubmitJson;
import com.example.codelabsvc.controller.request.challenge.UpdateChallengeDTO;
import com.example.codelabsvc.controller.response.challenge.ChallengeResponseDTO;
import com.example.codelabsvc.controller.response.testCase.TestCaseJsonResponse;
import com.example.codelabsvc.entity.BookmarkedChallenge;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/challenge")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @PostMapping(value = "", produces = "application/json")
    public ApiResponse<Challenge> createChallenge(@Valid @RequestBody CreateChallengeDTO createChallengeDTO) throws CustomException {
        Challenge challenge = challengeService.createChallenge(createChallengeDTO);
        return ApiResponse.successWithResult(challenge);
    }

    @PostMapping(value = "search-challenge/{challengeName}", produces = "application/json")
    public ApiResponse<List<Challenge>> searchChallenge(@Valid @PathVariable String challengeName) throws CustomException {
        List<Challenge> challenges = challengeService.searchChallenge(challengeName);
        return ApiResponse.successWithResult(challenges);
    }

    @PutMapping(value = "", produces = "application/json")
    public ApiResponse<Challenge> updateChallenge(@Valid @RequestBody UpdateChallengeDTO updateChallengeDTO) throws CustomException {
        Challenge challenge = challengeService.updateChallenge(updateChallengeDTO);
        return ApiResponse.successWithResult(challenge);
    }

    @DeleteMapping(value = "/{challengeId}", produces = "application/json")
    public ApiResponse<Challenge> deleteChallenge(@Valid @PathVariable String challengeId) throws CustomException {
        Challenge challenge = challengeService.deleteChallenge(challengeId);
        return ApiResponse.successWithResult(challenge);
    }

//    @PostMapping(value = "/submit-code/{challengeId}", produces = "application/json")
//    public ApiResponse<List<TestCase>> submitCode(@RequestParam String language,
//                                                  @PathVariable("challengeId") String challengeId,
//                                                  @RequestParam(value = WellKnownParam.SOURCE_CODE) MultipartFile sourceCode) throws CustomException {
//        return ApiResponse.successWithResult(challengeService.submitCode(language, challengeId, sourceCode));
//    }

    @PostMapping(value = "/submit-code-json/{testCaseId}", produces = "application/json")
    public ApiResponse<TestCaseJsonResponse> submitCodeJson(@PathVariable("testCaseId") String testCaseId,
                                          @RequestBody @Valid TestCaseSubmitJson testCaseSubmitJson) throws CustomException, ExecutionException, InterruptedException {
        return ApiResponse.successWithResult(challengeService.submitCodeJson(testCaseId, testCaseSubmitJson));
    }

    @GetMapping(value = "/{challengeId}", produces = "application/json")
    public ApiResponse<Challenge> getChallenge(@Valid @PathVariable("challengeId") String id) throws CustomException {
        return ApiResponse.successWithResult(challengeService.getChallengeById(id));
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public ApiResponse<Page<ChallengeResponseDTO>> filterChallenges(@RequestBody FilterChallengeRequest filterChallengeRequest) {
        return ApiResponse.successWithResult(challengeService.filterChallenge(filterChallengeRequest));
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
    public ApiResponse<Page<ChallengeResponseDTO>> listAllChallengeByTopic(@PathVariable String topicId, @RequestParam(defaultValue = "1", required = false) int page,
                                                                @RequestParam(defaultValue = "5", required = false) int size) throws CustomException {
        Page<ChallengeResponseDTO> challenges = challengeService.getAllChallengesByTopic(topicId, page, size);
        return ApiResponse.successWithResult(challenges);
    }
}
