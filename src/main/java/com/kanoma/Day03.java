package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day03 {
    private static final Logger logger = LogManager.getLogger(Day03.class);
    
    private static ArrayList<String> data = new ArrayList<>();
    
    public static void main(String[] args){
        logger.info("Day 03");
        var startTimer = Instant.now();
        String day = "03";
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        
        readData(inputFile);
        
        partOne();
        
        partTwo();
        
        var endTimer = Instant.now();
        logger.info("Execution time: " + Duration.between(startTimer, endTimer).toMillis() + " ms");
    }
    
    private static void readData(File file) {
        try {
            Scanner reader = new Scanner(file);
            //Lecture du fichier
            while (reader.hasNext()){
                String line = reader.nextLine();
                //Pour chaque ligne faire un split sur les espaces
                data.add(line);
            }
            
            reader.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        
    }
    
    private static void partOne() {
        try {
            logger.info("Part One");
            Long result = 0L;
            
            for (String ligne : data) {
                result = getMatcherMult(result, ligne);
                
            }
            
            logger.info("Result part one: " + result);
            
        } catch (Exception e) {
            logger.error("Error in partOne", e);
        }
    }
    
    private static void partTwo() {
        try {
            logger.info("Part Two");
            
            Long resultPCL = 0L;
            String fullText = "";
            
            //Passage sur le texte entier pour gérer les sauts de lignes qui cassent l'expression regulière
            for (String ligne : data) {
                fullText += ligne;
                
            }
            // Avec une expression regulière, remplacer tout ce qui se trouve entre 'don't()' et 'do()' par rien
            fullText = fullText.replaceAll("don't\\(\\).*?do\\(\\)", "");
            
            //Gestion du cas particulier : suppression de `don't()` et tout ce qu'il y a après
            if (fullText.contains("don't()")) {
                fullText = fullText.substring(0, fullText.indexOf("don't()"));
            }
            resultPCL = getMatcherMult(resultPCL, fullText);
            
            logger.info("ResultPCL part two : " + resultPCL);
            
        } catch (Exception e) {
            logger.error("Error in partTwo", e);
        }
    }
    
    private static Long getMatcherMult(Long result, String ligne) {
        // Faire un pattern matcher pour lire la chaine et chercher l'expression 'mul\(\d+,\d+\)'
        Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
        Matcher matcher = pattern.matcher(ligne);
        while (matcher.find()) {
            // logger.debug("Found match for `mult(xxx,yy)`: " + matcher.group());
            // Faire un split sur la virgule er récupérer les deux valeurs numériques
            String[] numbers = matcher.group().substring(4, matcher.group().length()-1).split(",");
            int number1 = Integer.parseInt(numbers[0]);
            int number2 = Integer.parseInt(numbers[1]);
            // Faire la multiplication
            result += number1 * number2;
            
        }
        return result;
    }
    
    
    
}