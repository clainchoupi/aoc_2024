package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day08 {
    private static final Logger logger = LogManager.getLogger(Day08.class);
    
    private static List<char[]> data = new ArrayList<>();
    private static HashMap<Character, ArrayList<Integer[]>> antinoeuds = new HashMap<>();
    
    public static void main(String[] args){
        String day = "08";
        logger.info("Day " + day);
        var startTimer = Instant.now();
        File inputFile = new File("src/main/resources/DAY/"+day+".txt");
        //Rep 1 : 662 : too high
        //Rep 2 : 410 : too high
        
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
                data.add(line.toCharArray());
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
            
            // Parcourir tous les caractères de data
            for (char[] line : data) {
                // Parcourir tous les caractères de la ligne
                for (char c : line) {
                    // Ne pas prendre en compte le . && c n'est pas dans la liste des antinoeuds
                    if (c != '.' && !antinoeuds.containsKey(c)) {
                        // Calculer la position de tous les antinoeuds possibles pour ce caractère
                        // Ajouter les positions des antinoeuds possibles à une hashmap, où la clef est le caractère
                        // et la valeur est une liste de positions
                        antinoeuds.put(c, getCaracterAntiNodes(c, data));
                    }
                }
            }
            
            // Pour tous les antinoeuds possibles, récupérer les positions x,y et les ajouter à une liste en supprimant les doublons
            ArrayList<Integer[]> allAntinoeuds = new ArrayList<>();
            for (ArrayList<Integer[]> positions : antinoeuds.values()) {
                for (Integer[] position : positions) {
                    boolean exists = false;
                    for (Integer[] existingPosition : allAntinoeuds) {
                        if (existingPosition[0].equals(position[0]) && existingPosition[1].equals(position[1])) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        allAntinoeuds.add(position);
                    }
                }
            }


            
            logger.info("Result part one: " + allAntinoeuds.size());
            
        } catch (Exception e) {
            logger.error("Error in partOne", e);
        }
    }
    
    private static ArrayList<Integer[]> getCaracterAntiNodes(char c, List<char[]> data) {
        ArrayList<Integer[]> positionsChar = new ArrayList<>();
        ArrayList<Integer[]> positionsAntinodes = new ArrayList<>();
        
        // rechercher dans la grille toutes les positions x,y où le caractère c est présent
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).length; j++) {
                if (data.get(i)[j] == c) {
                    Integer[] position = {i, j};
                    positionsChar.add(position);
                }
            }
        }
        
        // Pour chaque combinaison de deux positions, calculer les antinoeuds possibles
        for (int i = 0; i < positionsChar.size(); i++) {
            for (int j = i + 1; j < positionsChar.size(); j++) {
                positionsAntinodes.addAll(intGetPositions(positionsChar.get(i), positionsChar.get(j)));
            }
        }
        
        return positionsAntinodes;
    }
    
    private static ArrayList<Integer[]> intGetPositions(Integer[] a, Integer[] b) {
        ArrayList<Integer[]> positions = new ArrayList<>();
        
        // Calculer les antinoeuds possibles entre les deux points a et b
        // Les antinoeuds sont de chaque coté de la droite passant par a et b, et à la même distance sur cette droite
        // a et b sont au milieu de la droite passant par les antinoeuds
        // Les antinoeuds sont les points (x, y) tels que 

        // Calculer les coordonnées du point A', où A est entre A' et B
        Integer[] positionAPrime = {2 * a[0] - b[0], 2 * a[1] - b[1]};
        if (positionAPrime[0] >= 0 && positionAPrime[0] < data.size() && positionAPrime[1] >= 0 && positionAPrime[1] < data.get(0).length){
            positions.add(positionAPrime);
        }
        
        // Calculer les coordonnées du point B', où B est entre A et B'
        Integer[] positionBPrime = {2 * b[0] - a[0], 2 * b[1] - a[1]};
        //Ajouter le point s'il est dans la grille
         if (positionBPrime[0] >= 0 && positionBPrime[0] < data.size() && positionBPrime[1] >= 0 && positionBPrime[1] < data.get(0).length){
             positions.add(positionBPrime);
         }
        
        return positions;
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