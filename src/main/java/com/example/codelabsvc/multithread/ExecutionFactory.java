//package com.example.codelabsvc.multithread;
//
//import com.example.codelabsvc.constant.WellKnownParam;
//import com.example.codelabsvc.entity.TestCase;
//
//import com.example.codelabsvc.repository.TestCaseRepository;
//import org.apache.commons.io.FileUtils;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.*;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.lang.reflect.Field;
//import java.util.LinkedHashMap;
//import java.util.Objects;
//import java.util.Random;
//import java.util.concurrent.Callable;
//
//public class ExecutionFactory implements Callable<TestCase> {
//    private final String compileUrl;
//    private final String language;
//    private final MultipartFile submittedSourceCode;
//    private final TestCase testCase;
//
//    private final TestCaseRepository testCaseRepository;
//    public ExecutionFactory(String compileUrl, String language, MultipartFile submittedSourceCode, TestCase testCase, TestCaseRepository testCaseRepository) {
//        this.compileUrl = compileUrl;
//        this.language = language;
//        this.submittedSourceCode = submittedSourceCode;
//        this.testCase = testCase;
//        this.testCaseRepository = testCaseRepository;
//    }
//
//    @Override
//    public TestCase call() throws Exception {
//        Random random = new Random();
//        Thread.sleep(random.nextInt(10) * 10);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        /* Set header */
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        /* Using for adding @RequestParam, @RequestPart,...*/
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add(WellKnownParam.LANGUAGE, language);
//        body.add(WellKnownParam.TIME_LIMIT, testCase.getTimeLimit());
//        body.add(WellKnownParam.MEMORY_LIMIT, testCase.getMemoryLimit());
//
//        //Lay file input, expected output va source code de truyen vao
//        File expectedOutputFile = new File(testCase.getExpectedOutputFilePath());
//        body.add(WellKnownParam.EXPECTED_OUTPUTS, new ByteArrayResource(FileUtils.readFileToByteArray(expectedOutputFile)) {
//            @Override
//            public String getFilename() {
//                return expectedOutputFile.getName();
//            }
//        });
//        File inputFile = new File(testCase.getInputFilePath());
//        body.add(WellKnownParam.INPUTS, new ByteArrayResource(FileUtils.readFileToByteArray(inputFile)) {
//            @Override
//            public String getFilename() {
//                return inputFile.getName();
//            }
//        });
//        body.add(WellKnownParam.SOURCE_CODE, new ByteArrayResource(submittedSourceCode.getBytes()) {
//            @Override
//            public String getFilename() {
//                return submittedSourceCode.getOriginalFilename();
//            }
//        });
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<Object> response = restTemplate.exchange(
//                Objects.requireNonNull(compileUrl),
//                HttpMethod.POST,
//                requestEntity,
//                Object.class
//        );
//
//        LinkedHashMap linkedHashMap = (LinkedHashMap) response.getBody();
//
//        Class<?> objClass = testCase.getClass();
//        Field[] fields = objClass.getDeclaredFields();
//
//        if (linkedHashMap != null) {
//            for (Field field : fields) {
//                field.setAccessible(true);
//                if (linkedHashMap.containsKey(field.getName())) {
//                    field.set(testCase, linkedHashMap.get(field.getName()));
//                }
//            }
//        }
//
//        this.testCaseRepository.save(testCase);
//
//        return testCase;
//    }
//}
