package com.example.sudoku;

import java.util.HashSet;

/**
 * Abstract κλάση που υλοποιεί το γενικό παζλ του σουντόκου.
 * Αποτελείται από τον πίνακα που περιέχει τις τιμές κάθε κελιού
 * και ένα πίνακα που δείχνει ποια κελιά είναι άδεια και συνεπώς
 * μπορέι ο χρήστης να τοποθετήσει κάποια τιμή. Επίσης, κρατά τον
 * τύπο του παιχνιδιού και τον αριθμό γραμμών και στηλών για κάθε
 * παιχνίδι.
 */

public abstract class Puzzle  {
    private int id; //ο αριθμός του Puzzle
    private int rows; //αριθμός γραμμών
    private int columns;//αριθμός στηλών
    protected Cell[][] board; //πίνακας τιμών του παζλ
    protected boolean[][] empty; //πίνακας που δείχνει τα άδεια κελιά
    public GameType type; //τύπος του παιχνιδιού

    /**
     *Κλάση που θέτει το id του παζλ που φορτώνεται
     * @param id το id του παζλ
     */
    public void setId(int id){this.id=id;}


    /**
     *Μέθοδος που επιστρέφει το id του παζ
     * @return το id
     */
    public int getId(){return id;}

    /**
     *Συνάρτηση που επιστρέφει το κελί του παζλ στη
     *θέση (x,y).
     * @param x η γραμμή που βρίσκεται το κελί
     * @param y η στήλη που βρίσκετσι το κελί
     * @return το κελί
     */
    public Cell getBoard(int x, int y)
    {return board[x][y];}
    /**
     *Μέθοδος που επιστρέφει αν η θέση (x,y) του board
     *είνσι κενή και μπορεί να δεχτεί είσοδο.
     * @param row η γραμμή που βρίσκεται το κελί
     * @param column η στήλη που βρίσκετσι το κελί
     * @return true αν είναι κενό το κελί αλλιώς false
     */
    public boolean isEmpty(int row,int column){
        return empty[row][column];

    }
    /**
     *Συνάρτηση που αρχικοποιεί το πίνακα empty διατρέχοντας τον πίνακα
     * και βρίσκοντας τις κενές θέσεις.
     */
    public void setEmpty()
    {
        for (int i=0;i<getRows();i++)
        {
            for (int j= 0;j<getColumns();j++)
            {
                if (board[i][j].getValue()==0)
                    empty[i][j]=true;
                else
                    empty[i][j]=false;
            }
        }
    }

    /**
     * Συνάρτηση που επιστρέφει ένα πίνακα με τις
     * επιτρεπόμενες τιμές για το κελί στη θέση (x,y).
     * @param x η γραμμή που βρίσκεται το κελί
     * @param y η στήλη που βρίσκετσι το κελί
     * @return ένας πίνακας ακεραίων με τις επιτρεπόμενες τιμές
     */
    public int[] getHelp(int x, int y) {
        HashSet<Integer> vals=new HashSet<>();
        int r = x /(int) Math.sqrt(getRows());
        int col=y/(int) Math.sqrt(getColumns());
        int startRow=r*(int)Math.sqrt(getRows());
        int startCol=col*(int)Math.sqrt(getColumns());

        //βρίσκει ποιοι αριθμοί βρίσκονται στην ίδια γραμμή και στήλη με
        //το κελί στη θέση (x,y)
        for (int i=0;i<getRows();i++)
        {
            if(board[i][y].getValue()!=0)
            {vals.add(board[i][y].getValue());}
            if(board[x][i].getValue()!=0)
            {vals.add(board[x][i].getValue());}

        }

        //βρίσκει ποιοι αριθμοί βρίσκονται στo ίδιο nonet με
        //το κελί στη θέση (x,y)
        for (int i=0;i<Math.sqrt(getRows());i++)
        {
            for (int j=0;j<Math.sqrt(getColumns());j++)
            {
                if(startRow+i==x || startCol+j==y)
                    continue;
                if (board[startRow+i][startCol+j].getValue()!=0)
                    vals.add(board[startRow+i][startCol+j].getValue());
            }
        }
        //επιστρέφει στην έξοδο όσες τιμές δεν ανήκουν στο σύνολο vals
        int[] output=new int[getRows()-vals.size()];
        int count=0;
        for (int i=1;i<=getRows();i++)
        {
            if (!vals.contains(i))
            {
                output[count]=i;
                count++;
            }
        }
        return output;
    }

    //public abstract void loadPuzzle(int number);
    public abstract boolean isOver();

    /**
     * Συνάρτηση που επιστρέφει τον αριθμό των γραμμών του παζλ
     * @return ο αριθμός των γραμμών
     */
    public int getRows(){return rows;}

    /**
     * Συνάρτηση που επιστρέφει τον αριθμό των στηλών του παζλ
     * @return ο αριθμός των στηλών
     */
    public int getColumns(){return columns;}

    /**
     * Συνάρτηση που επιστρέφει τον πίνακα του παζλ
     * @return ο πίνακας
     */
    public Cell[][] getBoard(){return board;}

    /**
     * Συνάρτηση που ελέγχει αν οι συντεταγμένες είναι μέσα στα επιτρεπτά όρια
     * @param row
     * @param col
     * @return
     */
    public boolean inRange(int row,int col) {
        return row < rows && col < columns && row >= 0 && col >= 0;
    }

    /**
     * Κλάση που ελέγχει αν υπάρχει ο αριθμός number στην γραμμή x
     * @param x η συντεταγμένη στον άξονα x που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param y η συντεταγμένη στον άξονα y που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param number o αριθμός που δέχεται ως είσοδο στη θέση (x,y)
     * @return true αν υπάρχει ο αριθμός στην ίδια γραμμή αλλιώς false
     */
    public boolean inRow(int x,int y,int number)
    {
        for(int i=0;i<getRows();i++) {

            if (board[x][i].getValue() == number) {
                return true;

            }
        }
        return false;
    }

    /**
     * Κλάση που ελέγχει αν υπάρχει ο αριθμός number στην στήλη y
     * @param x η συντεταγμένη στον άξονα x που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param y η συντεταγμένη στον άξονα y που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param number o αριθμός που δέχεται ως είσοδο στη θέση (x,y)
     * @return true αν υπάρχει ο αριθμός στην ίδια στήλη αλλιώς false
     */
    public boolean inColumn(int x,int y,int number)
    {
        for(int i=0;i<getColumns();i++)
        {


            if (board[i][y].getValue()==number)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Κλάση που ελέγχει αν υπάρχει ο αριθμός number στο nonet 3x3 που ανήκει
     * @param x η συντεταγμένη στον άξονα x που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param y η συντεταγμένη στον άξονα y που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param number o αριθμός που δέχεται ως είσοδο στη θέση (x,y)
     * @return true αν υπάρχει ο αριθμός στο ίδιο nonet αλλιώς false
     */
    public boolean inBox(int x,int y,int number)
    {
        for(int i=0;i<getRows();i++)
        {
            for (int j=0;j<getColumns();j++)
            {
                if(board[i][j].getNonet()==board[x][y].getNonet()) {
                    if (board[i][j].getValue() == number) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Θέτει τον αριθμό των γραμμών που πρέπει να είναι μεγαλύτερος
     * του μηδέν και η τετραγωνική ρίζα του αριθμού να είναι ακέραιος.
     * @param rows ο αριθμός των γραμμών που θέλουμε να θέσουμε
     */
    public void setRows(int rows) {
        if(rows>0 && Math.sqrt(rows) % 1 == 0)
            this.rows = rows;
    }

    /**
     * Θέτει τον αριθμό των στηλών που πρέπει να είναι μεγαλύτερος
     * του μηδέν και η τετραγωνική ρίζα του αριθμού να είναι ακέραιος.
     * @param columns ο αριθμός των στηλών που θέλουμε να θέσουμε
     */
    public void setColumns(int columns) {
        if(columns>0 && Math.sqrt(columns) % 1 == 0)
            this.columns = columns;
    }

    /**
     * Abstract μέθοδος που ελέγχει αν τοποθετηθεί ένας αριθμός
     * στη θέση (x,y) είναι έγκυρη κίνηση
     * @param x
     * @param y
     * @param number
     * @return
     */
    public abstract boolean validMove(int x, int y, int number);
    public abstract boolean getUserInput(int x,int y,int number);

}
