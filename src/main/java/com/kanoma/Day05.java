package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day05 {
    private static final Logger logger = LogManager.getLogger(Day05.class);
    
    private static HashMap<String, ArrayList<Integer>> rules_MustBeBefore = new HashMap<>();
    private static HashMap<String, ArrayList<Integer>> rules_MustBeAfter  = new HashMap<>();
    
    private static ArrayList<ArrayList<Integer>> pages = new ArrayList<>();
    
    public static void main(String[] args){
        String day = "05";
        logger.info("Day " + day);
        var startTimer = Instant.now();
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
            boolean emptyLine = false;
            while (reader.hasNext()){
                String line = reader.nextLine();
                
                if (line.isEmpty()) {
                    emptyLine = true;
                    //Passer à la ligne suivante
                    continue;
                }
                
                if (!emptyLine){
                    //Premier lot de lignes
                    String[] pages = line.split("\\|");
                    //Ajouter pages[1] dans rulesBefore pour la clé pages[0]
                    if (rules_MustBeBefore.containsKey(pages[0])){
                        rules_MustBeBefore.get(pages[0]).add(Integer.parseInt(pages[1]));
                    }else{
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(Integer.parseInt(pages[1]));
                        rules_MustBeBefore.put(pages[0], list);
                    }
                    
                    //Ajouter pages[0] dans rulesAfter pour la clé pages[1]
                    if (rules_MustBeAfter.containsKey(pages[1])){
                        rules_MustBeAfter.get(pages[1]).add(Integer.parseInt(pages[0]));
                    }else{
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(Integer.parseInt(pages[0]));
                        rules_MustBeAfter.put(pages[1], list);
                    }
                    
                }else{
                    //Deuxième lot de lignes
                    pages.add(Arrays.stream(line.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new)));
                }
                
            }
            
            reader.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        
    }
    
    private static void partOne() {
        try {
            logger.info("Part One");
            int result = 0;
            
            //Pour chaque liste de pages dans `pages`, vérifier si l'ordre est respecté
            for (ArrayList<Integer> listePages : pages){
                
                if (checkListePages(result, listePages)){
                    //Si la liste est ordonnée, ajouter la page du milieu à result
                    result += getMiddlePage(listePages);
                }
            
            }
            
            logger.info("Result part one: " + result);
            
        } catch (Exception e) {
            logger.error("Error in partOne", e);
        }
    }

    private static boolean checkListePages(int result, ArrayList<Integer> listePages) {
        boolean continueLoop = true;
        //Vérifier si la liste est ordonnée
        
        for (int i = 0; i < listePages.size() && continueLoop; i++){
            //Pour chaque page, vérifier si toutes les pages suivantes sont dans rulesBefore
            if(continueLoop){
                if (rules_MustBeBefore.containsKey(listePages.get(i).toString())){
                    for (int j = i + 1; j < listePages.size(); j++){
                        if (!rules_MustBeBefore.get(listePages.get(i).toString()).contains(listePages.get(j))){
                            //Si la page n'est pas dans rulesBefore, la liste n'est pas ordonnée
                            continueLoop =false;
                            logger.debug("Liste fausse BEFORE : " + listePages);
                            break;
                        }
                    }
                }
            }
            
            //Pour chaque page, vérifier si toutes les pages précédentes sont dans rulesAfter
             if(continueLoop){
                if (rules_MustBeAfter.containsKey(listePages.get(i).toString())){
                    for (int j = i - 1; j >= 0; j--){
                        if (!rules_MustBeAfter.get(listePages.get(i).toString()).contains(listePages.get(j))){
                            //Si la page n'est pas dans rulesAfter, la liste n'est pas ordonnée
                            continueLoop =false;
                            logger.debug("Liste fausse AFTER : " + listePages);
                            break;
                        }
                    }
                }
            }
            
        }

        // Si on n'est pas sorti de la boucle pour cette liste, alors la liste est ordonnée
        return continueLoop;
    }

    private static Integer getMiddlePage(ArrayList<Integer> listePages) {
        return listePages.get(listePages.size() / 2);
    }
    
    private static void partTwo() {
        try {
            logger.info("Part Two");
            int result = 0;
            
            //Pour chaque liste de pages dans `pages`, vérifier si l'ordre est respecté
            for (ArrayList<Integer> listePages : pages){
                result += reorderListePages(result, listePages);
            }
            
            logger.info("Result part two: " + result);
            
        } catch (Exception e) {
            logger.error("Error in partTwo", e);
        }
    }

    private static Integer reorderListePages(int result, ArrayList<Integer> listePages) {
        boolean hasBeenReordered = false;
        //Vérifier si la liste est ordonnée
        
        for (int i = 0; i < listePages.size(); i++){
            //Pour chaque page, vérifier si toutes les pages suivantes sont dans rulesBefore
                if (rules_MustBeBefore.containsKey(listePages.get(i).toString())){
                    for (int j = i + 1; j < listePages.size(); j++){
                        if (!rules_MustBeBefore.get(listePages.get(i).toString()).contains(listePages.get(j))){
                            //Si la page n'est pas dans rulesBefore, la liste n'est pas ordonnée
                            //En cas d'erreur : mettre à jour listePages en inversant la page i et la page j
                            int temp = listePages.get(i);
                            listePages.set(i, listePages.get(j));
                            listePages.set(j, temp);

                            hasBeenReordered = true;
                        }
                    }
                }
            
            //Pour chaque page, vérifier si toutes les pages précédentes sont dans rulesAfter
                if (rules_MustBeAfter.containsKey(listePages.get(i).toString())){
                    for (int j = i - 1; j >= 0; j--){
                        if (!rules_MustBeAfter.get(listePages.get(i).toString()).contains(listePages.get(j))){
                            //Si la page n'est pas dans rulesAfter, la liste n'est pas ordonnée
                            //En cas d'erreur : mettre à jour listePages en inversant la page i et la page j
                            int temp = listePages.get(i);
                            listePages.set(i, listePages.get(j));
                            listePages.set(j, temp);

                            hasBeenReordered = true;
                        }
                    }
                }
            
        }

        // Si la liste a été réordonnée, on ajoute son résultat
        if (hasBeenReordered){
            return listePages.get(listePages.size() / 2);
        }
        // Sinon
        return 0;
    }
    
}