package com.example.codelabsvc.multithread;

import com.example.codelabsvc.constant.Language;
import com.example.codelabsvc.entity.PreScript;
import com.example.codelabsvc.entity.TestCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Callable;


public class ExecutionFactory implements Callable<TestCase> {

    @Value("${compile.url}")
    private String compileUrl;

    private final PreScript preScript;

    private final TestCase testCase;

    public ExecutionFactory(PreScript preScript, TestCase testCase) {
        this.preScript = preScript;
        this.testCase = testCase;
    }

    @Override
    public TestCase call() throws Exception {
        Random random = new Random();
        Thread.sleep(random.nextInt(10) * 10);

        RestTemplate restTemplate = new RestTemplate();

        /* Set header */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        /* Using for adding @RequestParam, @RequestPart,...*/
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(WellKnowParams.LANGUAGE, preScript.getLanguage());
        body.add(WellKnowParams.TIME_LIMIT, testCase.getTimeLimit());
        body.add(WellKnowParams.MEMORY_LIMIT, testCase.getMemoryLimit());


        /* Lay file input, expected output va source code de truyen vao

        body.add(WellKnowParams.EXPECTED_OUTPUTS,new ByteArrayResource(expectedOutput.getBytes()) {
            @Override
            public String getFilename() {
                return expectedOutput.getOriginalFilename();
            }
        };);
        body.add(WellKnowParams.INPUTS, new ByteArrayResource(inputFile.getBytes()) {
            @Override
            public String getFilename() {
                return inputFile.getOriginalFilename();
            }
        };);
        body.add(WellKnowParams.SOURCE_CODE,new ByteArrayResource(sourceCode.getBytes()) {
            @Override
            public String getFilename() {
                return sourceCode.getOriginalFilename();
            }
        });

        */

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                compileUrl,
                HttpMethod.POST,
                requestEntity,
                Object.class
        );

        System.out.println(response);

        //Đang tìm cách map object và test case lại
        return (TestCase) response.getBody();
    }


//    public static Resource getFile(String folder, String fileName, String suffix, String content) throws IOException {
//        Path filePath = Paths.get("/loadtests/"+folder+"/"+fileName+"/"+suffix);
//        Files.write(filePath, content.getBytes());
//        return new FileSystemResource(filePath.toFile());
//    }
}
