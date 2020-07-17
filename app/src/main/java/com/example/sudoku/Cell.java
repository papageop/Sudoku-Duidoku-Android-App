package com.example.sudoku;

/**
 * Κλάση που υλοποιεί το κελί στο παιχνίδι Sudoku.
 * Το κελί έχει συγκεκριμένες συντεταγμένες στο
 * χώρο, που καθορίζονται από τη γραμμή και τη στήλη
 * που βρίσκεται, καθώς επισής ανήκει σε ένα συγκεκριμένο
 *  3Χ3 πλέγμα(κλασικό ) ή 2X2(duidoku) και έχει μια τιμή.
 */

public class Cell {
    private int row;//γραμμή του κελιού
    private int column;//στήλη του κελιού
    private int nonet; //κουτί που ανήκει το κελί
    private int value; //τιμή του κελιού

    /**
     * Constructor
     * @param x: η γραμμή του κελιού
     * @param y: η στήλη του κελιού
     */
    public Cell(int x,int y)
    {
        this.row=x;
        this.column=y;
        if(row<3){
            if(column<3) nonet=1;
            else if(column<6) nonet=2;
            else if(column<9) nonet=3;
        }
        else if(row<6){
            if(column<3) nonet=4;
            else if(column<6) nonet=5;
            else if(column<9) nonet=6;
        }
        else if(row<9){
            if(column<3) nonet=7;
            else if(column<6) nonet=8;
            else if(column<9) nonet=9;
        }
        value=0;

    }

    /**
     * Getter για τη row
     * @return γραμμή του κελιού
     */
    public int getRow()
    {return row;}

    /**
     * Getter για τη column
     * @return στήλη του κελιού
     */
    public int getColumn()
    {return column;
    }

    /**
     * Setter για τη value
     * @param value
     */
    public void setValue(int value)
    {this.value=value;}

    /**
     * Getter για τη value
     * @return την τιμή του κελιού
     */
    public int getValue()
    {return value;}

    /**
     * Getter για το κουτί 3Χ3 του πλέγματος
     * @return nonet
     */
    public int getNonet()
    {return nonet;}

}

