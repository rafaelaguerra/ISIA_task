/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrices;

import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author galvez
 */
public class Matriz {
    private int[][]datos;
    private Random rnd = new Random();
    
    public Matriz(int filas, int columnas, boolean inicializarAleatorio){
        datos = new int[columnas][];
        for(int i=0; i<columnas; i++){
            datos[i] = new int[filas];
            if (inicializarAleatorio)
                for(int j=0; j<filas; j++)
                    datos[i][j] = rnd.nextInt(100);
        }
    }
    public Matriz(Dimension d, boolean inicializarAleatorio){
        this(d.height, d.width, inicializarAleatorio);
    }
    
    public Dimension getDimension(){
        return new Dimension(datos.length, datos[0].length);
    }

    public static Matriz getCofactor(Matriz a, int p, int q, int n) {
        int i = 0, j = 0;
        int filasA = a.getDimension().height; int columnasA = a.getDimension().width;
        Matriz temp = new Matriz(filasA, columnasA, false);

        for (int row = 0; row < n; row++)  {
            for (int col = 0; col < n; col++)  {
                if (row != p && col != q)  {
                    temp.datos[i][j++] = a.datos[row][col];
                    if (j == n - 1)  {
                        j = 0;
                        i++;
                    }
                }
            }
        }

        return temp;
    }

    public static int determinantOfMatrix(Matriz a, int n) {
        int D = 0; 
        if (n == 1)
            return a.datos[0][0];
 
        int sign = 1;
        for (int f = 0; f < n; f++) {
            Matriz temp = getCofactor(a, 0, f, n);
            D += sign * a.datos[0][f]
                 * determinantOfMatrix(temp, n - 1);
            sign = -sign;
        }
 
        return D;
    }
    
    /* Recursive function for finding determinant of matrix. 
    n is current dimension of A[][]. */
    public static int determinant(Matriz a) 
    { 
        int D = 0; // Initialize result 
        int n = a.getDimension().width;
    
        // Base case : if matrix contains single element 
        if (n == 1) 
            return a.datos[0][0]; 
    
        int sign = 1;
    
        // Iterate for each element of first row 
        for (int f = 0; f < n; f++) 
        { 
            // Getting Cofactor of A[0][f] 
            Matriz temp = getCofactor(a, 0, f, n); 
            D += sign * a.datos[0][f] * determinant(temp); 
    
            // terms are to be added with alternate sign 
            sign = -sign; 
        } 
    
        return D; 
    } 
    

    public static Matriz adjoint(Matriz a)  { 

        int dimension = a.getDimension().width;
        Matriz adj = new Matriz(dimension, dimension, false);
    
        // temp is used to store cofactors of A[][] 
        int sign = 1;     
        for (int i = 0; i < dimension; i++)  { 
            for (int j = 0; j < dimension; j++)  { 
                // Get cofactor of A[i][j] 
                Matriz temp = getCofactor(a, i, j, dimension); 
    
                // sign of adj[j][i] positive if sum of row 
                // and column indexes is even. 
                sign = ((i + j) % 2 == 0)? 1: -1; 
    
                // Interchanging rows and columns to get the 
                // transpose of the cofactor matrix 
                adj.datos[j][i] = (sign)*(determinant(temp)); 
            } 
        } 

        return adj;
    } 

    // Function to calculate and store inverse, returns false if 
    // matrix is singular 
    public static Matriz inverse(Matriz a, Matriz inversa)  { 
        // Find determinant of A[][] 
        int det = determinant(a); 
        Matriz inverse = new Matriz(a.getDimension().width, a.getDimension().width, false);
        if (det == 0)  { 
            return inverse;
        }

        Matriz adj = adjoint(a); 
    
    
        // Find Inverse using formula "inverse(A) = adj(A)/det(A)" 
        for (int i = 0; i < a.getDimension().width; i++) 
            for (int j = 0; j < a.getDimension().width; j++) 
                inverse.datos[i][j] = adj.datos[i][j]/(int)det; 
                
        return inverse; 
    } 
    
    
    public static Matriz sumarDosMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(! a.getDimension().equals(b.getDimension())) throw new DimensionesIncompatibles("La suma de matrices requiere matrices de las mismas dimensiones");        
        int i, j, filasA, columnasA; 
        filasA = a.getDimension().height; 
        columnasA = a.getDimension().width; 
        Matriz matrizResultante = new Matriz(filasA, columnasA, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasA; i++) { 
                matrizResultante.datos[i][j] += a.datos[i][j] + b.datos[i][j]; 
            } 
        } 
        return matrizResultante; 
    } 

    @Override
    public String toString(){
        String ret = "";
        ret += "[\n";
        for (int i = 0; i < getDimension().width; i++) {
            ret += "(";
            for (int j = 0; j < getDimension().height; j++) {  
                ret += String.format("%3d", datos[i][j]); 
                if (j != getDimension().height - 1) ret += ", ";
            } 
            ret += ")";
            if (i != getDimension().width - 1) ret += ",";
            ret += "\n";
        } 
        ret += "]\n";
        return ret;
    }
}
