package com.kanoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day04 {
    private static final Logger logger = LogManager.getLogger(Day04.class);
    
    private static char[][] grid = new char[150][150];
    
    public static void main(String[] args){
        String day = "04";
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
            int nbLine = 0;
            while (reader.hasNext()){
                String line = reader.nextLine();
                //Pour chaque ligne ajouter chaque caractère dans la grille 
                for (int i = 0; i < line.length(); i++) {
                    grid[nbLine][i] = line.charAt(i);
                }
                nbLine++;
                
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
            
            String word = "XMAS";
            result = findWordInGrid(grid, word);
            
            logger.info("Result part one: " + result);
            
        } catch (Exception e) {
            logger.error("Error in partOne", e);
        }
    }

    private static void partTwo() {
        try {
            logger.info("Part Two");
            int result = 0;
            
            result = findCrossingInGrid(grid);
            
            logger.info("Result part two: " + result);
            
        } catch (Exception e) {
            logger.error("Error in partTwo", e);
        }
    }
    
    public static int findWordInGrid(char[][] grid, String word) {
        int rows = grid.length;
        int cols = grid[0].length;
        int result = 0;
        
        // Directions: {row_offset, col_offset}
        int[][] directions = {
            {0, 1},  // right
            {0, -1}, // left
            {1, 0},  // down
            {-1, 0}, // up
            {1, 1},  // diagonal down-right
            {-1, -1}, // diagonal up-left
            {1, -1}, // diagonal down-left
            {-1, 1}  // diagonal up-right
        };
        
        //Parcours de toutes les lettres de la grille
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                //Recherche de la première lettre du mot XMAS
                if (grid[row][col] == word.charAt(0)) {
                    //Si lettre X, alors chercher dans toutes les directions les lettres suivantes
                    for (int[] direction : directions) {
                        if (checkWord(grid, word, row, col, direction[0], direction[1])) {
                            result++;
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    public static boolean checkWord(char[][] grid, String word, int row, int col, int rowDir, int colDir) {
        int rows = grid.length;
        int cols = grid[0].length;
        int wordLength = word.length();
        
        for (int i = 0; i < wordLength; i++) {
            int newRow = row + i * rowDir;
            int newCol = col + i * colDir;
            
            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols || grid[newRow][newCol] != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }


    public static int findCrossingInGrid(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int result = 0;

        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                // Check if the current cell is 'A' and forms a crossing pattern
                if (grid[row][col] == 'A') {
                    if (isDiagonalCross(grid, row, col)) {
                        result++;
                    }
                }
            }
        }

        return result;
    }

    public static boolean isDiagonalCross(char[][] grid, int row, int col) {
        int nbTrue = 0;
        // Check the diagonal down-right and up-left (M at (row-1, col-1) and S at (row+1, col+1))
        boolean downRight = grid[row - 1][col - 1] == 'M' && grid[row + 1][col + 1] == 'S';
        if (downRight) nbTrue++;

        // Check the diagonal down-left and up-right (M at (row-1, col+1) and S at (row+1, col-1))
        boolean downLeft = grid[row - 1][col + 1] == 'M' && grid[row + 1][col - 1] == 'S';
        if (downLeft) nbTrue++;

        //Add upLeft and upRight
        boolean upRight = grid[row + 1][col - 1] == 'M' && grid[row - 1][col + 1] == 'S';
        if (upRight) nbTrue++;

        boolean upLeft = grid[row + 1][col + 1] == 'M' && grid[row - 1][col - 1] == 'S';
        if (upLeft) nbTrue++;

        //Count if two of the four diagonals are true
        return nbTrue == 2;
    }
}