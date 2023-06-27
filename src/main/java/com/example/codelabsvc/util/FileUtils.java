package com.example.codelabsvc.util;

import com.example.codelabsvc.constant.FileConfig;
import com.example.codelabsvc.controller.response.testCase.CreateTestCaseFilePathResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

@Component
public class FileUtils {

    public CreateTestCaseFilePathResponse buildMultipartFile(String inputContent, String expectedOutputContent, String testCaseId) throws IOException {
        var inputData = inputContent.getBytes();
        var expectedOutputData = expectedOutputContent.getBytes();

        String inputName = FileConfig.INPUT_FILE_NAME;
        String inputOriginalFileName = FileConfig.INPUT_FILE_NAME;
        String expectedOutputName = FileConfig.EXPECTED_OUTPUT_FILE_NAME;
        String expectedOutputOriginalFileName = FileConfig.EXPECTED_OUTPUT_FILE_NAME;
        String contentType = FileConfig.TEXT_PLAIN_CONTENT_TYPE;
        String path = FileConfig.PRE_PATH + testCaseId;

        MultipartFile inputFile = new MockMultipartFile(inputName, inputOriginalFileName, contentType, inputData);
        createFileSave(inputFile, path);

        MultipartFile expectedOutputFile = new MockMultipartFile(expectedOutputName, expectedOutputOriginalFileName, contentType, expectedOutputData);
        createFileSave(expectedOutputFile, path);

        CreateTestCaseFilePathResponse testCaseRequirementAndResponse = new CreateTestCaseFilePathResponse();
        testCaseRequirementAndResponse.setInputFilePath(path + "/" + inputFile.getName());
        testCaseRequirementAndResponse.setExpectedOutputFilePath(path + "/" + expectedOutputFile.getName());

        return testCaseRequirementAndResponse;
    }

    public void createFileSave(MultipartFile multipartFile, String path, String subPath) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();

        saveMultipartFile(inputStream, new File(path, subPath));
    }

    public void createFileSave(MultipartFile multipartFile, String path) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        saveMultipartFile(inputStream, new File(path, Objects.requireNonNull(multipartFile.getOriginalFilename())));
    }

    private void saveMultipartFile(InputStream inputStream, File destinationFile) throws IOException {

        destinationFile.getParentFile().mkdirs();

        OutputStream outputStream = new FileOutputStream(destinationFile);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
    }

    public static String convertFileToString(File file) throws IOException {
//        File file = new File(pa);

        String str = org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8");

        return str;
    }
    public static byte[] convertFileToBytes(File file) throws IOException {
        byte[] fileBytes = org.apache.commons.io.FileUtils.readFileToByteArray(file);

        return fileBytes;
    }
    public static void main(String[] args) throws IOException {
        System.out.printf(convertFileToString(null));
    }
}
