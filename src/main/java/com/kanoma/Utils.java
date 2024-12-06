package com.kanoma;

public class Utils {
    
    
    /**
     * Fait pivoter une grille de caractères dans le sens horaire ou antihoraire.
     *
     * @param grid La grille de caractères à faire pivoter.
     * @param clockwise Si vrai, la grille est pivotée dans le sens horaire; sinon, dans le sens antihoraire.
     * @return La grille pivotée.
     */
    public static char[][] rotateGrid(char[][] grid, boolean clockwise) {
        int rows = grid.length;
        int cols = grid[0].length;
        char[][] rotatedGrid = new char[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (clockwise) {
                    rotatedGrid[j][rows - 1 - i] = grid[i][j];
                } else {
                    rotatedGrid[cols - 1 - j][i] = grid[i][j];
                }
            }
        }

        return rotatedGrid;
    }

}