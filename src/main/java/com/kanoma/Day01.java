package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day01 {
    private static final Logger logger = LogManager.getLogger(Day01.class);
    public static void main(String[] args){
        logger.info("Day 01");
        var startTimer = Instant.now();
        String day = "01";
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        partOne(inputFile);
        
        partTwo(inputFile);
        
        var endTimer = Instant.now();
        logger.info("Execution time: " + Duration.between(startTimer, endTimer).toMillis() + " ms");
    }
    
    private static void partOne(File file) {
        try {
            List<Integer> listOne = new ArrayList<>();
            List<Integer> listTwo = new ArrayList<>();
            int sum = 0;
            
            Scanner reader = new Scanner(file);
            //Lecture du fichier
            while (reader.hasNext()){
                String line = reader.nextLine();
                //Pour chaque ligne faire un split sur les espaces
                String[] numbers = line.split("   ");
                
                listOne.add(Integer.parseInt(numbers[0]));
                listTwo.add(Integer.parseInt(numbers[1]));
                
            }
            
            reader.close();
            
            Collections.sort(listOne);
            Collections.sort(listTwo);
            
            // on boucle sur les deux listes pour comparer le i-ème élément de la première liste avec le i-ème élément de la deuxième liste
            // le résultat final et la somme de la comparaison des deux (deuxième liste - premiere liste)
            for (int i = 0; i < listOne.size(); i++) {
                Integer valCol1 = listOne.get(i);
                Integer valCol2 = listTwo.get(i);
                logger.debug("listTwo.get(i) =" + valCol2);
                logger.debug("listOne.get(i) =" + valCol1);
                
                sum += Math.abs(valCol2 - valCol1);
            }
            
            logger.info("PartOne result --> Sum: " + sum);
            
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        
    }
    
    
    private static void partTwo(File file) {
        try {
            List<Integer> listOne = new ArrayList<>();
            List<Integer> listTwo = new ArrayList<>();
            int sum = 0;
            
            Scanner reader = new Scanner(file);
            //Lecture du fichier
            while (reader.hasNext()){
                String line = reader.nextLine();
                //Pour chaque ligne faire un split sur les espaces
                String[] numbers = line.split("   ");
                
                listOne.add(Integer.parseInt(numbers[0]));
                listTwo.add(Integer.parseInt(numbers[1]));
                
            }
            
            reader.close();
            
            //Pour chaque élément de la listOne, on cherche combien de fois il apparait dans la listTwo
            for (int i = 0; i < listOne.size(); i++) {
                Integer valCol1 = listOne.get(i);
                
                //On récupère le nombre d'apparition de l'élément dans la listTwo
                int valCol2 = Collections.frequency(listTwo, valCol1);
                
                // Le résultat ajouté a la somme totale est l'élément * son nombre d'apparition dans la listTwo
                sum += valCol2 * valCol1;
            }
            
            logger.info("PartTwo result --> Sum: " + sum);
            
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        
    }
    
    
}
