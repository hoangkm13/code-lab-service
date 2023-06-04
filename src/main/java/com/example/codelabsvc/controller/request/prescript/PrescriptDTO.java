package com.example.codelabsvc.controller.request.prescript;

import com.example.codelabsvc.constant.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptDTO {

    private String id;

    private String challengeId;

    private String preContent;

    private Language language;
}
