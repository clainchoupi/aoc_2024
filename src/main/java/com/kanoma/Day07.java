package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day07 {
    private static final Logger logger = LogManager.getLogger(Day07.class);
    
    private static ArrayList<Calibrations> calibrations = new ArrayList<>();
    
    public static void main(String[] args){
        String day = "07";
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
            
            //Lire les lignes du fichier 
            /**
            * Exemples de lignes 
            * 190: 10 19
            * 3267: 81 40 27
            * Avant le : on a le nombre visé
            * Après le : on a les nombres qui peuvent être utilisés
            */
            
            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] parts = line.split(":");
                
                //On récupère le nombre visé
                BigInteger target = new BigInteger(parts[0].trim());
                
                //On récupère les nombres qui peuvent être utilisés
                String[] numbers = parts[1].trim().split(" ");
                
                ArrayList<Integer> calibrationNumbers = new ArrayList<>();
                for (String number : numbers) {
                    calibrationNumbers.add(Integer.parseInt(number));
                }
                calibrations.add(new Calibrations(target, calibrationNumbers));
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
            
            // Liste des opérateurs
            //char[] operators = {'+', '-', '*', '/'};
            char[] operators = {'+','*'};
            
            
            //On parcourt la liste des calibrations
            for (Calibrations calibration : calibrations) {
                BigInteger target = calibration.getTarget();
                ArrayList<Integer> numbers = calibration.getNumbers();
                
                // Calculer toutes les combinaisons possibles
                List<BigInteger> results = calculateAllResults(numbers, operators);
                
                // Si un des resultats est égal à la cible, on ajoute la valeur cible au compteur 
                if (results.contains(target)) {
                    result = result.add(target);
                }
                
            }
            
            logger.info("Result part one: " + result);
            
        } catch (Exception e) {
            logger.error("Error in partOne", e);
        }
    }
    
    private static List<BigInteger> calculateAllResults(ArrayList<Integer> numbers, char[] operators) {
        List<BigInteger> results = new ArrayList<>();
        
        // De façon récursive, on calcule toutes les combinaisons possibles
        // On commence par le premier nombre, et on ajoute dans la liste des résultats
        results.add(BigInteger.valueOf(numbers.get(0)));
        
        // On parcourt les nombres suivants
        for (int i = 1; i < numbers.size(); i++) {
            List<BigInteger> newResults = new ArrayList<>();
            for (BigInteger result : results) {
                for (char operator : operators) {
                    switch (operator) {
                        case '+':
                        // On ajoute le nombre à la valeur précédente
                        newResults.add(result.add(BigInteger.valueOf(numbers.get(i))));
                        break;
                        case '*':
                        // On multiplie le nombre à la valeur précédente
                        newResults.add(result.multiply(BigInteger.valueOf(numbers.get(i))));
                        break;
                        case 'ù':
                        // On colle le nombre à la valeur précédente
                        String concat = result.toString() + numbers.get(i);
                        newResults.add(new BigInteger(concat));
                        break;
                        default:
                        break;
                    }
                }
            }
            results = newResults;
        }
        
        return results;
    }
    
    private static void partTwo() {
        try {
            logger.info("Part Two");
            BigInteger result = BigInteger.ZERO;
            
            // Liste des opérateurs
            //char[] operators = {'+', '-', '*', '/'};
            char[] operators = {'+','*', 'ù'};
            
            
            //On parcourt la liste des calibrations
            for (Calibrations calibration : calibrations) {
                BigInteger target = calibration.getTarget();
                ArrayList<Integer> numbers = calibration.getNumbers();
                
                // Calculer toutes les combinaisons possibles
                List<BigInteger> results = calculateAllResults(numbers, operators);
                
                // Si un des resultats est égal à la cible, on ajoute la valeur cible au compteur 
                if (results.contains(target)) {
                    result = result.add(target);
                }
                
            }
            
            
            logger.info("Result part two: " + result);
            
        } catch (Exception e) {
            logger.error("Error in partTwo", e);
        }
    }
    
    private static class Calibrations {
        private BigInteger target;
        private ArrayList<Integer> numbers;
        
        public Calibrations(BigInteger target, ArrayList<Integer> numbers) {
            this.target = target;
            this.numbers = numbers;
        }
        
        public BigInteger getTarget() {
            return target;
        }
        
        public ArrayList<Integer> getNumbers() {
            return numbers;
        }
    }
    
}