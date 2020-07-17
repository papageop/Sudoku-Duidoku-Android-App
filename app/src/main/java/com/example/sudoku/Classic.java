package com.example.sudoku;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Κλάση που υλοποιεί το κλασικό παιχνίδι σουντόκου.
 * Φορτώνει κάποιο παζλ που υπάρχει στο αρχείο και κάνει τους
 *  απαραίτητους ελέγχους έτσι ώστε να μην υπάρχει ο
 *  ίδιος αριθμός στην ίδια γραμμή,στήλη και nonet.
 */
public class Classic extends Puzzle {




    /**
     * Constructor που αρχικοποιεί το παζλ για το
     * sudoku παιχνίδι που θα παίξει ο χρήστης.
     */
    public Classic()
    {
//
        super(); //κληρονομεί από την κλάση Puzzle
        this.type=GameType.CLASSIC;
        this.setRows(9);
        this.setColumns(9);
        board=new Cell[getRows()][getColumns()];
        this.empty=new boolean[getRows()][getColumns()];

        //αρχικοποιεί τον πίνακα empty σε τιμή true για όλες τις θέσεις του πίνακα
        for (int i=0;i<getRows();i++)
        {
            for (int j=0;j<getColumns();j++)
            {
                empty[i][j]=true;
            }
        }


    }

    /**
     * Συνάρτηση που επιστρέφει τον πίνακα που δείχνει τις κενές θέσεις
     * του πίνακα.
     * @return ο πίνακας empty
     */
    public boolean[][] getEmpty(){return empty;}

    /**
     * Συνάρτηση που φορτώνει από το αρχείο ένα συγκεκριμένο παζλ
     *  sudoku
     * @param number ο αριθμός του παζλ από 1-10
     */



    /**
     * Μέθοδος που επιστρέφει το κελί στη θέση (x,y) του παζλ
     * @param x η γραμμή του κελιού
     * @param y η στήλη του κελιού
     * @return το κελί
     */
    public Cell getBoard(int x, int y)
    {return super.board[x][y];}

    /**
     * Μέθοδος που ελέγχει αν μια κίνηση είναι έγκυρη, δηλαδή αν ο αριθμός είναι στο διάστημα [1,4],
     * αν δεν υπάρχει στην ίδια γραμμή, στήλη και κουτί.
     * @param x η συντεταγμένη στον άξονα x που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param y η συντεταγμένη στον άξονα y που θέλουμε να τοποθετήσουμε τον αριθμο number
     * @param number η τιμή που θέλουμε να ελέγξουμε
     * @return true αν είναι έγκυρη αλλιώς false
     */
    @Override
    public boolean validMove(int x,int y,int number)
    {

        return !super.inRow(x,y,number) && !super.inColumn(x, y, number) && !super.inBox(x,y,number) &&super.inRange(x,y);

    }


    /**
     * Μέθοδος που επιστρέφει αν ένα κελί είναι κενό και μπορεί ο
     * χρήστης να τοποθετήσει τιμή.
     * @param row η γραμμή του κελιου προς έλεγχο
     * @param column η στήλη του κελιου προς έλεγχο
     * @return αν το κελί είναι μεταλλάξιμο ή όχι
     */
    public boolean isEmpty(int row,int column){
        return super.empty[row][column];

    }




    /**
     * Συνάρτηση που επιστρέφει τις επιτρεπόμενες τιμές
     * για ένα συγκεκριμένο κελί στη θέση (x,y). Επιστρέφει
     * ένα πίνακα ακεραίων τιμών με τις τιμές.
     * @param x η γραμμή του κελιού που ζητάμε βοήθεια
     * @param y η στήλη του κελιού που ζητάμε βοήθεια
     * @return ένας πίνακας ακεραίων με τις επιτρεπόμενες τιμές
     */
    public int[] getHelp(int x, int y) {
        return super.getHelp(x,y);
    }

    public void setEmpty()
    {
        super.setEmpty();
    }
    /**
     * Συνάρτηση που δέχεται την είσσοδο του χρήστη και επιστρέφει αν είναι
     * αποδεκτή ή όχι.
     * @param x η γραμμή του κελιού που δέχεται είσοδο
     * @param y η στήλη του κελιού που δέχεται είσοδο
     * @param number η τιμή που δέχεται το κελί ως είσοσδος
     * @return true αν είναι αποδεκτή η είσοδος αλλιώς false
     */
    @Override
    public boolean getUserInput(int x,int y,int number)
    {

        if (validMove(x, y, number) && isEmpty(x,y) ){
            board[x][y].setValue(number);
            return true;
        }
        return false;
    }

    /**
     * Μέθοδος που επιστρέφει αν το παιχνίδι έχει τελειώσει
     * επειδή δεν υπάρχουν κενά κελιά.
     * @return true αν το παιχνίδι τελείωσε αλλιώς false
     */
    @Override
    public boolean isOver()
    {
        for (int i=0;i<getRows();i++)
        {
            for (int j=0;j<getColumns();j++)
            {
                if (board[i][j].getValue()==0)
                    return false;
            }
        }
        return true;
    }

    /**
     * Μέθοδος που επιστρέφει αν το παιχνίδι έχει λυθεί λάθος είτε
     * επειδή είτε επειδή δεν υπάρχουν
     * άλλες επιστρεπόμενες είσοδοι ενώ υπάρχουν κενά κελιά.
     * @return true αν το παιχνίδι τελείωσε αλλιώς false
     */

    public boolean wrongSolution()
    {
        for (int i=0;i<9;i++)
        {
            for (int j=0;j<9;j++)
            {
                if (board[i][j].getValue()==0 && getHelp(i,j).length!=0 )
                    return false;


            }
        }
        return true;
    }




}
