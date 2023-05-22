package com.example.codelabsvc.controller;


import com.example.codelabsvc.controller.request.ChallengeDTO;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.PreScript;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.impl.ChallengeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class ChallengeController {

    @Autowired
    private ChallengeServiceImpl challengeService;

    @PostMapping(value = "/challenge", produces = "application/json")
    public ApiResponse<Challenge> createChallenge(@RequestBody ChallengeDTO challengeDTO){
        Challenge challenge = challengeService.createChallenge(challengeDTO);
        return ApiResponse.successWithResult(challenge);
    }

    @PostMapping(value = "/submitCode/{challengeId}", produces = "application/json")
    public ApiResponse<List<TestCase>> submitCode(@RequestBody PreScript preScript, @PathVariable("challengeId") String challengeId) {
        return ApiResponse.successWithResult(challengeService.submitCode(preScript, challengeId));
    }

    @GetMapping(value = "/{challengeId}", produces = "application/json")
    public ApiResponse<Challenge> getChallenge(@PathVariable("challengeId") String id) throws CustomException {
        return ApiResponse.successWithResult(challengeService.getChallengeById(id));
    }

}
