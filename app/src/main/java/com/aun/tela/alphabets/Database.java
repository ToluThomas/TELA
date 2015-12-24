package com.aun.tela.alphabets;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private Context context;
    private static String NAME = "database";
    private static int version = 1;

    public static final class Tables {
        public static final String ALPHABETS = "ALPHABETS";

        public static String[] columns = new String[]{"alphabet", "names", "images", "audioResEnglish",  "audioResHausa"};
    }

    public Database(Context context){
        this(context, NAME, null, version);
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    private void setDatabaseCreated(boolean value){
        SharedPreferences preferences = context.getSharedPreferences("database",  Context.MODE_PRIVATE);
        preferences.edit().putBoolean("createdDatabase", value).apply();
    }

    private boolean isDatabaseCreated(){
        SharedPreferences preferences = context.getSharedPreferences("database",  Context.MODE_PRIVATE);
        return preferences.getBoolean("createdDatabase", false);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = "CREATE TABLE IF NOT EXISTS regex ( "+Tables.columns[0]+" TEXT PRIMARY KEY, "+Tables.columns[1]+" TEXT, "
                + Tables.columns[2]+" TEXT, "
                + Tables.columns[3]+" TEXT, "
                + Tables.columns[4]+" TEXT);";
        db.execSQL(createStatement.replace("regex", Tables.ALPHABETS));
        if(isDatabaseCreated()){
            //do nothing, database items already filled
        }else{
            fillArrays(db);
            setDatabaseCreated(true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void fillArrays(SQLiteDatabase db){
        fillTable(db, Tables.ALPHABETS, getAlphabets());
    }




    private void fillTable(SQLiteDatabase database, String tablename, Item[] items){
        for(Item item : items) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Tables.columns[0], item.alphabet);
            contentValues.put(Tables.columns[1], item.name);
            contentValues.put(Tables.columns[2], item.image);
            contentValues.put(Tables.columns[3], item.audioEngRes);
            contentValues.put(Tables.columns[4], item.audioHausaRes);
            database.replace(tablename, null, contentValues);
        }
    }


    class Item {
        String name, alphabet, image, audioEngRes, audioHausaRes;

        public Item(String alphabet, String name, String images, String audioEngRes, String audioHausaRes){
            this.alphabet = alphabet; this.name = name; this.image = images; this.audioEngRes = audioEngRes;
            this.audioHausaRes = audioHausaRes;
        }
    }

    private Item[] getAlphabets() {
        return new Item[]{
                new Item("A", "A for Apple,a for apples", "apple,apples", "", ""),
                new Item("B", "B for Book,b for books", "book,books", "", ""),
                new Item("C", "C for Carrot,c for carrots", "carrot,carrots", "", ""),
                new Item("D", "D for Dog,d for dogs", "dog,dogs", "", ""),
                new Item("E", "E for Egg,e for eggs", "egg,eggs", "", ""),
                new Item("F", "F for Fish,f for fishes", "fish,fishes", "", ""),
                new Item("G", "G for Garri,g for garri", "garri", "", ""),
                new Item("H", "H for Horse,h for horses", "horse,horses", "", ""),
                new Item("I", "I for Iron,i for irons", "iron,irons", "", ""),
                new Item("J", "J for Jug,j for jugs", "jug,jugs", "", ""),
                new Item("K", "K for Kettle,k for kettles", "kettle,kettles", "", ""),
                new Item("L", "L for Lantern,l for lanterns", "lantern,lanterns", "", ""),
                new Item("M", "M for Mat,m for mats", "mat,mats", "", ""),
                new Item("N", "N for Nest,n for nests", "nest,nests", "", ""),
                new Item("O", "O for Orange,o for oranges", "orange,oranges", "", ""),
                new Item("P", "P for Plate,p for plates", "plate,plates", "", ""),
                new Item("Q", "Q for Queen,q for queens", "queen,queens", "", ""),
                new Item("R", "R for Rat,r for rats", "rat,rats", "", ""),
                new Item("S", "S for Snail,s for snail", "snail,snails", "", ""),
                new Item("T", "T for Tyre,t for tyres", "tyre,tyres", "", ""),
                new Item("U", "U for Umbrella,u for umbrellas", "umbrella,umbrellas", "", ""),
                new Item("V", "V for Van,v for vans", "van,vans", "", ""),
                new Item("W", "W for Watermelon,w for watermelons", "watermelon,watermelons", "", ""),
                new Item("X", "X for Xylophone,x for xylophones", "xylophone,xylophones", "", ""),
                new Item("Y", "Y for Yam,y for yams", "yam,yams", "", ""),
                new Item("Z", "Z for Zip,z for zips", "zip,zips", "", ""),


        };
    }

    public static int getResPathFromName(String name, Context context){
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}