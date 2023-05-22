package com.example.codelabsvc.controller;


import com.example.codelabsvc.controller.request.ChallengeDTO;
import com.example.codelabsvc.entity.Challenge;
import com.example.codelabsvc.entity.TestCase;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.impl.ChallengeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class ChallengeController {

    @Autowired
    private ChallengeServiceImpl challengeService;

    @PostMapping(value = "/challenge", produces = "application/json")
    public ApiResponse<Challenge> addChallenge(@RequestBody ChallengeDTO challengeDTO){
        Challenge challenge = challengeService.addChallenge(challengeDTO);
        return ApiResponse.successWithResult(challenge);
    }

    @PostMapping(value = "/submitCode", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ApiResponse<List<TestCase>> submitCode(@RequestPart MultipartFile inputFile, @RequestPart MultipartFile expectedOutput, @RequestPart MultipartFile sourceCode) {
        return ApiResponse.successWithResult(challengeService.submitCode(inputFile, expectedOutput, sourceCode));
    }

    @GetMapping(value = "/{challengeId}", produces = "application/json")
    public ApiResponse<Challenge> getChallenge(@PathVariable("challengeId") String id) throws CustomException {
        return ApiResponse.successWithResult(challengeService.getChallengeById(id));
    }

}
