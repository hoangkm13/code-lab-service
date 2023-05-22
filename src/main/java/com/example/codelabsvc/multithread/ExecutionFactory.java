package com.example.codelabsvc.multithread;

import com.example.codelabsvc.constant.Language;
import com.example.codelabsvc.entity.TestCase;
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

//    private final int index;
//
//    private final PreScript preScript;
//
//    private final TestCase testCase;
//
//    private final String challengeName;

//    public ExecutionFactory(int index, PreScript preScript, TestCase testCase, String challengeName) {
//        this.index = index;
//        this.preScript = preScript;
//        this.testCase = testCase;
//        this.challengeName = challengeName;
//    }

    private final MultipartFile inputFile;

    private final MultipartFile expectedOutput;

    private final MultipartFile sourceCode;

    public ExecutionFactory(MultipartFile inputFile, MultipartFile expectedOutput, MultipartFile sourceCode) {
        this.inputFile = inputFile;
        this.expectedOutput = expectedOutput;
        this.sourceCode = sourceCode;
    }

    @Override
    public TestCase call() throws Exception {
        Random random = new Random();
        Thread.sleep(random.nextInt(10) * 100);

        RestTemplate restTemplate = new RestTemplate();
        String compileUrl = "http://ec2-35-91-216-85.us-west-2.compute.amazonaws.com:8080/api/compile";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(WellKnowParams.LANGUAGE, "JAVA");
        body.add(WellKnowParams.TIME_LIMIT, 10);
        body.add(WellKnowParams.MEMORY_LIMIT, 10 * 100);
        body.add(WellKnowParams.EXPECTED_OUTPUTS,new InputStreamResource(expectedOutput.getInputStream(), expectedOutput.getOriginalFilename()));
        body.add(WellKnowParams.INPUTS, new InputStreamResource(expectedOutput.getInputStream(), inputFile.getOriginalFilename()));
        body.add(WellKnowParams.SOURCE_CODE, new InputStreamResource(expectedOutput.getInputStream(), sourceCode.getOriginalFilename()));

//        body.add(WellKnowParams.EXPECTED_OUTPUTS, getFile(
//                "expected-outputs",
//                challengeName + "-" + index,
//                "txt",
//                testCase.getExpectedOutput()));
//        body.add(WellKnowParams.INPUTS, getFile(
//                "inputs",
//                challengeName + "-" + index,
//                "txt",
//                testCase.getInput()));
//        body.add(WellKnowParams.SOURCE_CODE, getFile("Solution","", preScript.getLanguage().value(), preScript.getPreContent()));


        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                compileUrl,
                HttpMethod.POST,
                requestEntity,
                Object.class);

        System.out.println(response);

        return (TestCase) response.getBody();
    }


    public static Resource getFile(String folder, String fileName, String suffix, String content) throws IOException {
        Path filePath = Paths.get("/loadtests/"+folder+"/"+fileName+"/"+suffix);
//        Files.write(filePath, content.getBytes());
        return new FileSystemResource(filePath.toFile());
    }
}
