package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Comparators;

public class Day02 {
    private static final Logger logger = LogManager.getLogger(Day02.class);
    
    private static ArrayList<Report> reports = new ArrayList<>();
    
    
    public static void main(String[] args){
        logger.info("Day 02");
        var startTimer = Instant.now();
        String day = "02";
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        //File inputFile = new File("src/main/resources/sample.txt");
        
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
                String[] numbers = line.split(" ");
                
                //ajouter les nombres dans une liste, et ajoute la liste dans un objet Report
                ArrayList<Integer> reportNumbers = new ArrayList<>();
                for (String number : numbers) {
                    reportNumbers.add(Integer.parseInt(number));
                }
                reports.add(new Report(reportNumbers));
                
            }
            
            reader.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        
    }
    
    private static void partOne() {
        try {
            logger.info("Part One");
            
            //Compter le nombre de reports qui sont 'Safe'
            // Un Report est 'Safe' si tous les chiffres de la liste sont croissants ou décroissants, et si l'écart entre chaque chiffre est entre 1 et 3
            int safeReports = 0;
            
            for (Report report : reports) {
                boolean isSafe = true;
                
                ArrayList<Integer> numbers = report.getNumbers();
                
                //Vérifier si les chiffres de la Collection numbers sont ordonnés
                boolean ordered = 
                Comparators.isInOrder(numbers, Comparator.<Integer> naturalOrder())
                || Comparators.isInOrder(numbers, Comparator.<Integer> reverseOrder());
                
                if (!ordered) {
                    logger.info("Report not ordered: " + numbers);
                }else {
                    logger.info("Report ordered: " + numbers);
                    
                    //Vérifier si l'écart entre chaque chiffre est entre 1 et 3
                    for (int i = 0; i < numbers.size() - 1; i++) {
                        int diff = Math.abs(numbers.get(i+1) - numbers.get(i));
                        if (diff < 1 || diff > 3) {
                            isSafe = false;
                            break;
                        }
                    }
                    if (isSafe) {
                        safeReports++;
                    }
                }
                
            }
            
            logger.info("Safe reports: " + safeReports);
            
        } catch (Exception e) {
            logger.error("Error in partOne", e);
        }
        
    }
    
    private static void partTwo() {
        try {
            logger.info("Part Two");
            
            int safeReports = 0;
            
            for (Report report : reports) {
                
                
                ArrayList<Integer> numbers = report.getNumbers();
                
                if(isSafe(numbers)) {
                    safeReports++;
                }else{
                    //recursively remove one number from the list and check if the new list is safe
                    for (int i = 0; i < numbers.size(); i++) {
                        ArrayList<Integer> newNumbers = new ArrayList<>(numbers);
                        newNumbers.remove(i);
                        if (isSafe(newNumbers)) {
                            safeReports++;
                            break;
                        }
                    }
                }
            }
            
            logger.info("Safe reports: " + safeReports);
            
        } catch (Exception e) {
            logger.error("Error in partTwo", e);
        }
        
    }
    
    private static boolean isSafe(ArrayList<Integer> numbers) {
        //Vérifier si les chiffres de la Collection numbers sont ordonnés
        boolean ordered = 
        Comparators.isInOrder(numbers, Comparator.<Integer> naturalOrder())
        || Comparators.isInOrder(numbers, Comparator.<Integer> reverseOrder());
        
        if (!ordered) {
            logger.info("Report not ordered: " + numbers);
            return false;
        }else {
            logger.info("Report ordered: " + numbers);
            
            //Vérifier si l'écart entre chaque chiffre est entre 1 et 3
            for (int i = 0; i < numbers.size() - 1; i++) {
                int diff = Math.abs(numbers.get(i+1) - numbers.get(i));
                if (diff < 1 || diff > 3) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private static class Report {
        private ArrayList<Integer> numbers = new ArrayList<>();
        
        public Report(ArrayList<Integer> numbers) {
            this.numbers = numbers;
        }
        
        public ArrayList<Integer> getNumbers() {
            return numbers;
        }
        
        public void setNumbers(ArrayList<Integer> numbers) {
            this.numbers = numbers;
        }
    }
    
    
    
}
