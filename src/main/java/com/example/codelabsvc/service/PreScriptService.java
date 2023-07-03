package com.example.codelabsvc.service;

import com.example.codelabsvc.controller.request.prescript.PrescriptDTO;
import com.example.codelabsvc.entity.PreScript;
import com.example.codelabsvc.exception.CustomException;

import java.util.List;

public interface PreScriptService {

    List<PreScript> getAllPrescripts();

    PreScript createPrescript(PrescriptDTO prescriptDTO) throws CustomException;
    PreScript deletePrescript(String id) throws CustomException;
    PreScript updatePrescript(PrescriptDTO prescriptDTO) throws CustomException;
}
