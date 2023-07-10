package com.example.codelabsvc.controller;

import com.example.codelabsvc.controller.request.prescript.PrescriptDTO;
import com.example.codelabsvc.entity.PreScript;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.PreScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RequestMapping("/v1/prescript")
public class PrescriptController {
    @Autowired
    private PreScriptService preScriptService;

    @GetMapping(value = "", produces = "application/json")
    public ApiResponse<List<PreScript>> getAllPrescripts() {
        return ApiResponse.successWithResult(preScriptService.getAllPrescripts());
    }
    

    @PostMapping(value = "", produces = "application/json")
    public ApiResponse<PreScript> createPrescript(@RequestBody PrescriptDTO prescriptDTO) throws CustomException {
        PreScript preScript = preScriptService.createPrescript(prescriptDTO);
        return ApiResponse.successWithResult(preScript);
    }


    @PutMapping(value = "", produces = "application/json")
    public ApiResponse<PreScript> updatePrescript(@RequestBody PrescriptDTO prescriptDTO) throws CustomException {
        PreScript preScript = preScriptService.updatePrescript(prescriptDTO);
        return ApiResponse.successWithResult(preScript);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ApiResponse<PreScript> deletePrescript(@PathVariable String id) throws CustomException {
        PreScript preScript = preScriptService.deletePrescript(id);
        return ApiResponse.successWithResult(preScript);
    }
}
