package com.example.codelabsvc.controller;


import com.example.codelabsvc.controller.request.challenge.ChallengeDTO;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.constant.WellKnownParam;
import com.example.codelabsvc.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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

}
