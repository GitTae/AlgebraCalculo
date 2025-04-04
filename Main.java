import java.util.*;

public class Main {

    // Function to print Matrix
    static void printMatrix(double M[][], int rowSize, int colSize) {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++)
                System.out.printf("%.2f\t", M[i][j]);
            System.out.println();
        }
    }

    // Function to multiply matrices
    static void multiplyMatrix(int row1, int col1, int A[][], int row2, int col2, int B[][]) {
        int i, j, k;

        if (row2 != col1) {
            System.out.println("No se puede realizar la multiplicacion");
            return;
        }

        int C[][] = new int[row1][col2];

        for (i = 0; i < row1; i++) {
            for (j = 0; j < col2; j++) {
                for (k = 0; k < row2; k++)
                    C[i][j] += A[i][k] * B[k][j];
            }
        }

        System.out.println("Resultado de la multiplicacion:");
        printMatrix(toDoubleMatrix(C), row1, col2);
    }

    // Function to add matrices
    static void sumMatrix(int filas, int columnas, int matrizA[][], int matrizB[][]) {
        int[][] matrizSuma = new int[filas][columnas];

        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA[i].length; j++) {
                matrizSuma[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }

        System.out.println("Matriz Resultado de Suma:");
        printMatrix(toDoubleMatrix(matrizSuma), filas, columnas);
    }

    // Function to subtract matrices
    static void subMatrix(int filas, int columnas, int matrizA[][], int matrizB[][]) {
        int[][] matrizResta = new int[filas][columnas];

        for (int i = 0; i < matrizA.length; i++) {
            for (int j = 0; j < matrizA[i].length; j++) {
                matrizResta[i][j] = matrizA[i][j] - matrizB[i][j];
            }
        }

        System.out.println("Matriz Resultado de Resta:");
        printMatrix(toDoubleMatrix(matrizResta), filas, columnas);
    }

    // Function to calculate determinant
    static double determinant(double[][] matrix) {
        int n = matrix.length;
        if (n != matrix[0].length) {
            throw new IllegalArgumentException("La matriz debe ser cuadrada para calcular el determinante");
        }

        if (n == 1) {
            return matrix[0][0];
        }

        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double det = 0;
        for (int i = 0; i < n; i++) {
            double[][] minor = new double[n - 1][n - 1];
            for (int j = 1; j < n; j++) {
                for (int k = 0, l = 0; k < n; k++) {
                    if (k != i) {
                        minor[j - 1][l++] = matrix[j][k];
                    }
                }
            }
            det += matrix[0][i] * Math.pow(-1, i) * determinant(minor);
        }
        return det;
    }

    // Function to perform Gaussian elimination (row reduction)
    static double[][] rowReduction(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] reduced = copyMatrix(matrix);

        int lead = 0;
        for (int r = 0; r < rows; r++) {
            if (lead >= cols) break;

            int i = r;
            while (reduced[i][lead] == 0) {
                i++;
                if (i == rows) {
                    i = r;
                    lead++;
                    if (lead == cols) return reduced;
                }
            }

            // Swap rows i and r
            double[] temp = reduced[i];
            reduced[i] = reduced[r];
            reduced[r] = temp;

            // Normalize row r
            double lv = reduced[r][lead];
            for (int j = 0; j < cols; j++) {
                reduced[r][j] /= lv;
            }

            // Eliminate other rows
            for (int k = 0; k < rows; k++) {
                if (k != r && reduced[k][lead] != 0) {
                    double factor = reduced[k][lead];
                    for (int j = 0; j < cols; j++) {
                        reduced[k][j] -= factor * reduced[r][j];
                    }
                }
            }
            lead++;
        }
        return reduced;
    }

    // Function to calculate matrix inverse using Gauss-Jordan
    static double[][] inverse(double[][] matrix) {
        int n = matrix.length;
        if (n != matrix[0].length) {
            throw new IllegalArgumentException("La matriz debe ser cuadrada para calcular la inversa");
        }

        double det = determinant(matrix);
        if (det == 0) {
            throw new IllegalArgumentException("La matriz es singular (determinante = 0), no tiene inversa");
        }

        // Create augmented matrix [A|I]
        double[][] augmented = new double[n][2 * n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, augmented[i], 0, n);
            augmented[i][n + i] = 1;
        }

        // Perform Gauss-Jordan elimination
        augmented = rowReduction(augmented);

        // Extract the inverse matrix
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmented[i], n, inverse[i], 0, n);
        }

        return inverse;
    }

    // Helper function to copy a matrix
    static double[][] copyMatrix(double[][] matrix) {
        double[][] copy = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length);
        }
        return copy;
    }

    // Helper function to convert int matrix to double matrix
    static double[][] toDoubleMatrix(int[][] matrix) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i][j] = matrix[i][j];
            }
        }
        return result;
    }

    static int getRow(int matriz[][]) {
        return matriz.length;
    }

    static int getColumn(int matriz[][]) {
        return matriz[0].length;
    }

    public static void main(String[] args) {
        // Definimos dos matrices de 3x3
        int[][] matrizA = {
                {1, -2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] matrizB = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };

        //Obtenemos las dimensiones de las matrices
        int filasMatrizA = getRow(matrizA);
        int columnasMatrizA = getColumn(matrizA);

        int filasMatrizB = getRow(matrizB);
        int columnasMatrizB = getColumn(matrizB);

        // Imprimir matriz A
        System.out.println("Matriz A");
        printMatrix(toDoubleMatrix(matrizA), filasMatrizA, columnasMatrizA);

        // Imprimir matriz B
        System.out.println("Matriz B");
        printMatrix(toDoubleMatrix(matrizB), filasMatrizB, columnasMatrizB);

        // Operaciones básicas
        if (filasMatrizA == filasMatrizB && columnasMatrizA == columnasMatrizB) {
            sumMatrix(filasMatrizA, columnasMatrizA, matrizA, matrizB);
            subMatrix(filasMatrizA, columnasMatrizA, matrizA, matrizB);
        } else {
            System.out.println("No se puede hacer suma y resta, las matrices deben tener las mismas dimensiones");
        }

        // Multiplicación
        multiplyMatrix(filasMatrizA, columnasMatrizA, matrizA, filasMatrizB, columnasMatrizB, matrizB);

        // Operaciones avanzadas
        try {
            double[][] doubleMatrizA = toDoubleMatrix(matrizA);
            
            // Determinante
            System.out.println("\nCalculando determinante de Matriz A:");
            double detA = determinant(doubleMatrizA);
            System.out.printf("Determinante: %.2f\n", detA);

            // Reducción de matriz (forma escalonada)
            System.out.println("\nReduccion de Matriz A (forma escalonada):");
            double[][] reducedA = rowReduction(doubleMatrizA);
            printMatrix(reducedA, reducedA.length, reducedA[0].length);

            // Matriz inversa (solo si es invertible)
            if (detA != 0) {
                System.out.println("\nMatriz Inversa de A:");
                double[][] inverseA = inverse(doubleMatrizA);
                printMatrix(inverseA, inverseA.length, inverseA[0].length);
            } else {
                System.out.println("\nLa matriz A no tiene inversa (determinante = 0)");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
}