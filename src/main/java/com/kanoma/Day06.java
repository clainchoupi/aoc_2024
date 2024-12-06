package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day06 {
    private static final Logger logger = LogManager.getLogger(Day06.class);
    
    private static char[][] grid;
    private static int[] guardPosition = new int[2];
    
    
    public static void main(String[] args){
        String day = "06";
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
            
            //Initialisation de la grille
            int nbRows = 0;
            int nbCols = 0;
            while (reader.hasNext()) {
                String line = reader.nextLine();
                nbRows++;
                nbCols = line.length();
            }
            grid = new char[nbRows][nbCols];
            reader.close();
            
            // deuxième lecture pour remplir la grille
            reader = new Scanner(file);
            //Lecture du fichier
            int nbLine = 0;
            while (reader.hasNext()){
                String line = reader.nextLine();
                //Pour chaque ligne ajouter chaque caractère dans la grille 
                for (int i = 0; i < line.length(); i++) {
                    grid[nbLine][i] = line.charAt(i);
                }
                nbLine++;
                
            }
            
            // Initialiser la position du garde
            guardPosition = getGuardPosition(grid);
            
            reader.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
        
    }
    
    private static void partOne() {
        try {
            logger.info("Part One");
            boolean isGuardExiting = false;
            
            // Note : le garde tourne dans le sens horaire à chaque obstacle rencontré
            // plutot que de parcourir les lignes ou colonnes, on va faire une rotation de la grille et toujours avancer vers la droite
            
            // On commence par tourner la grille clockwise pour que le garde regarde vers la droite
            grid = Utils.rotateGrid(grid, true);
            //Apres chaque rotation, calculer la position du garde dans la nouvelle grille
            guardPosition = getGuardPosition(grid);
            
            while (!isGuardExiting){
                // Si le garde rencontre un obstacle, on le fait tourner dans le sens horaire
                if (grid[guardPosition[0]][guardPosition[1]+1] == '#') {
                    int colonneObstacle = guardPosition[1]+1;
                    logger.debug("Obstacle detected at position: " + guardPosition[0] + ", " + colonneObstacle);
                    grid = Utils.rotateGrid(grid, false);
                    guardPosition = getGuardPosition(grid);
                }
                
                //MAJ de la position du garde
                grid[guardPosition[0]][guardPosition[1]] = 'X';
                guardPosition[1]++;
                grid[guardPosition[0]][guardPosition[1]] = '^';

                // Si le garde est sur le bord droit de la grille, on arrête
                if (guardPosition[1] == grid[0].length-1) {
                    isGuardExiting = true;
                }
                
            }
            
            //Compte le nombre de `X` dans la grille
            //On part à 1 car le garde est sur la dernière case à la sortie donc nb 'X' + 1 '^''
            int nbX = 1;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 'X') {
                        nbX++;
                    }
                }
            }
            logger.info("Result part one: " + nbX);
            
        } catch (Exception e) {
            logger.error("Error in partOne", e);
        }
    }
    
    
    private static void partTwo() {
        try {
            logger.info("Part Two");
            int result = 0;

            // Potentiellement : tester de rajouter un 0, un par un, et tester pour voir si ça déclenche une boucle infinie
            // Comment détecter une boucle infinie ? Au delà de 20000, on peut considérer que c'est une boucle infinie ?
            
            logger.info("Result part two: " + result);
            
        } catch (Exception e) {
            logger.error("Error in partTwo", e);
        }
    }
    
    private static int[] getGuardPosition(char[][] grid) {
        int[] guardPosition = new int[2];
        
        // Parcours de la grille jusqu'à trouver le garde `^`
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '^') {
                    guardPosition[0] = i;
                    guardPosition[1] = j;
                    break;
                }
            }
        }
        
        return guardPosition;
    }
    
}