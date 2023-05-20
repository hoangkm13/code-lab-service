//package com.example.codelabsvc.multithread;
//
//import com.example.codelabsvc.entity.TestCase;
//import com.example.codelabsvc.service.TestCaseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.util.List;
//import java.util.concurrent.Callable;
//
//@Component
//public class ExecutionFactory implements Callable<List<TestCase>> {
//
//    @Autowired
//    private TestCaseRepository testCaseRepository;
//
//    @Override
//    public List<TestCase> call() throws Exception {
//
//        try{
//            List<TestCase> testCases = testCaseRepository.findAll();
//            File inputFile = new File("inputFile.txt");
//            FileWriter fileWriter = new FileWriter("inputFile.txt");
//
//            File expectedOutputFile = new File("expectedOutputFile.txt");
//
//            if(inputFile.createNewFile()){
//                fileWriter.write("");
//            }
//
//            if(expectedOutputFile.createNewFile()){
//                fileWriter.write("");
//            }
//        }catch (FileNotFoundException ignored){
//
//        }
//
//
//        return null;
//    }
//}
