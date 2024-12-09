package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day10 {
    private static final Logger logger = LogManager.getLogger(Day10.class);
    
    public static void main(String[] args){
        String day = "11";
        logger.info("Day " + day);
        var startTimer = Instant.now();
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        //File inputFile = new File("src/main/resources//DAY/"+day+"sample.txt");
        
        readData(inputFile);
        
        partOne();
        
        partTwo();
        
        var endTimer = Instant.now();
        logger.info("Execution time: " + Duration.between(startTimer, endTimer).toMillis() + " ms");
    }
    
    private static void readData(File file) {
        try {
            Scanner reader = new Scanner(file);
            
            while (reader.hasNext()) {
                String line = reader.nextLine();
                
            }
            
            reader.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        
    }
    
    private static void partOne() {
        try {
            logger.info("Part One");
            BigInteger result = BigInteger.ZERO;
            
            logger.info("Result part one: " + result);
            
        } catch (Exception e) {
            logger.error("Error in partOne", e);
        }
    }
    
    private static void partTwo() {
        try {
            logger.info("Part Two");
            BigInteger result = BigInteger.ZERO;
            
            logger.info("Result part two: " + result);
            
        } catch (Exception e) {
            logger.error("Error in partTwo", e);
        }
    }
    
}