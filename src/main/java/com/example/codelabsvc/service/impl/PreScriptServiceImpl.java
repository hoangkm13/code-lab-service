package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.controller.request.prescript.PrescriptDTO;
import com.example.codelabsvc.entity.PreScript;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.repository.PreScriptRepository;
import com.example.codelabsvc.service.ChallengeService;
import com.example.codelabsvc.service.PreScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PreScriptServiceImpl implements PreScriptService {

    @Autowired
    private PreScriptRepository preScriptRepository;

    @Autowired
    private ChallengeService challengeService;


    @Override
    public List<PreScript> getAllPrescripts() {
        return preScriptRepository.findAll();
    }

    @Override
    public PreScript createPrescript(PrescriptDTO prescriptDTO) throws CustomException {
        PreScript preScript = new PreScript();

        preScript.setId(UUID.randomUUID().toString());
        preScript.setLanguage(prescriptDTO.getLanguage());
        preScript.setPreContent(prescriptDTO.getPreContent());
        preScript.setChallengeId(challengeService.getChallengeById(prescriptDTO.getChallengeId()).getId());

        return preScriptRepository.save(preScript);
    }

    @Override
    public PreScript deletePrescript(String id) throws CustomException {
        PreScript preScript = preScriptRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.PRESCRIPT_NOT_EXIST));
        preScriptRepository.delete(preScript);
        return preScript;
    }

    @Override
    public PreScript updatePrescript(PrescriptDTO prescriptDTO) throws CustomException {
        PreScript preScript = preScriptRepository.findById(prescriptDTO.getId()).orElseThrow(() -> new CustomException(ErrorCode.PRESCRIPT_NOT_EXIST));

        preScript.setLanguage(prescriptDTO.getLanguage() != null ? prescriptDTO.getLanguage() : preScript.getLanguage());
        preScript.setPreContent(prescriptDTO.getPreContent() != null ? prescriptDTO.getPreContent() : preScript.getPreContent());
        preScript.setChallengeId(prescriptDTO.getChallengeId() != null ? challengeService.getChallengeById(prescriptDTO.getChallengeId()).getId() : preScript.getChallengeId());

        return preScriptRepository.save(preScript);
    }
}
