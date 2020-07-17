package com.example.sudoku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter κλάση που γεμίζει το GridView των παιχνιδιών sudoku με grid_item κουτάκια και τα εμφανίζει
 */
public class GridAdapter extends BaseAdapter {
    private Context context;
    protected String[] array;//πίνακας του οποίου τα περιεχόμενα θα γεμίσουν τa κουτάκια του Grid
    private static LayoutInflater inflater=null;
    private Classic classic; //αντικείμενο του κλασικού sudoku (στην περίπτωση του κλασικού
    // τα κουτάκια που έχουν εξαρχής αριθμό έχουν διαφορετικό χρώμα)

    /**
     * Constructor για το κλασικό
     * @param context
     * @param array
     * @param classic
     */
    public GridAdapter(Context context,String[] array,Classic classic){
        this.context=context;
        this.classic=classic;
        this.array=array;

    }

    /**
     * Constructor για το duidoku
     * @param context
     * @param array
     */
    public GridAdapter(Context context,String[] array){
        this.context=context;
        this.array=array;
        classic=null;

    }

    /**
     * Μέθοδος που επιστρέφει το μέγεθος του πίνακα array
     * @return
     */
    @Override
    public int getCount() {
        return array.length;
    }

    /**
     * Μέθοδος που επιστρέφει το αντικείμενο του πίνακα array στη θέση position
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return array[position];
    }

    /**
     * Μέθοδος που επιστρέφει τη θέση του αντικέμενου του πίνακα
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Μέθοδος που επιστρέφει το πίνακα το πίνακα γραφικά στην εφαρμογή
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.grid_item,null);

        TextView text=(TextView) convertView.findViewById(R.id.textView);
        text.setText(array[position]);
        if(classic!=null){ //αν το παιχνίδι είναι το κλασικό
        if (!classic.isEmpty(position/9,position%9)) //αν δεν είναι άδειο δηλαδή αν
            //δεν υπήρχε τιμή στον αρχικό πίνακα και μπορεί να τροποποιηθεί
        {
            text.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }}
        return convertView;
    }
}
