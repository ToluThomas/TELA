package com.aun.tela.alphabets.application.util;

import java.util.Random;

import io.meengle.util.Value;

public class Color {

    /**
     * Map of colors from www.flatuicolorpicker.com
     */

    public static BiMap<String, String> COLORS = new BiMap<String, String>(){

        {
            put("#D24D57", "Chesnut Rose");
            put("#F22613", "Pomegranate");
            put("#D91E18", "Thunderbird");
            put("#96281B", "Old Brick");
            put("#EF4836", "Flamingo");
            put("#D64541", "Valencia");
            put("#C0392B", "Tall Poppy");
            put("#CF000F", "Monza");
            put("#E74C3C", "Cinnabar");
            put("#DB0A5B", "Razzmatazz");
            put("#F64747", "Sunset Orange");
            put("#F1A9A0", "Wax Flower");
            put("#D2527F", "Cabaret");
            put("#E08283", "New York Pink");
            put("#F62459", "Radical Red");
            put("#E26A6A", "Sunglo");
            put("#DCC6E0", "Snuff");
            put("#663399", "RebeccaPurple");
            put("#674172", "Honey Flower");
            put("#AEA8D3", "Wistful");
            put("#913D88", "Plum");
            put("#9A12B3", "Seance");
            put("#BF55EC", "Medium Purple");
            put("#BE90D4", "Light Wisteria");
            put("#8E44AD", "Studio");
            put("#9B59B6", "Wisteria");
            put("#446CB3", "San Marino");
            put("#4183D7", "Royal Blue");
            put("#59ABE3", "Picton Blue Light");
            put("#81CFE0", "Spray");
            put("#52B3D9", "Shakespeare");
            put("#C5EFF7", "Humming Bird");
            put("#22A7F0", "Picton Blue");
            put("#3498DB", "Curious Blue");
            put("#2C3E50", "Madison");
            put("#19B5FE", "Dodger Blue");
            put("#336E7B", "Ming");
            put("#22313F", "Ebony Clay");
            put("#6BB9F0", "Malibu");
            put("#1E8BC3", "Curious Blue Dark");
            put("#3A539B", "Chambray");
            put("#34495E", "Pickled Bluewood");
            put("#67809F", "Hoki");
            put("#2574A9", "Jellybean");
            put("#1F3A93", "Jacksons Purple");
            put("#89C4F4", "Jordy Blue");
            put("#4B77BE", "Steel Blue");
            put("#5C97BF", "Fountain blue");
            put("#4ECDC4", "Medium Turquoise");
            put("#A2DED0", "Aqua Island");
            put("#87D37C", "Gossip");
            put("#90C695", "Dark Sea Green");
            put("#26A65B", "Eucalyptus");
            put("#03C9A9", "Caribbean Green");
            put("#68C3A3", "Silver Tree");
            put("#65C6BB", "Downy");
            put("#1BBC9B", "Mountain Meadow");
            put("#1BA39C", "Light Sea Green");
            put("#66CC99", "Medium Aquamarins");
            put("#36D7B7", "Turquoise");
            put("#C8F7C5", "Madang");
            put("#86E2D5", "Riptide");
            put("#2ECC71", "Shamrock");
            put("#16A085", "Mountain Meadow Dark");
            put("#3FC380", "Emerald");
            put("#019875", "Green Haze");
            put("#03A678", "Free Speech Aquamarine");
            put("#4DAF7C", "Ocean Green");
            put("#2ABB9B", "Niagara");
            put("#00B16A", "Jade");
            put("#1E824C", "Salem");
            put("#049372", "Observatory");
            put("#26C281", "Jungle Green");
            put("#F5D76E", "Cream Can");
            put("#F7CA18", "Ripe Lemon");
            put("#F4D03F", "Saffron");
            put("#FDE3A7", "Cape Honey");
            put("#F89406", "California");
            put("#EB9532", "Fire bush");
            put("#E87E04", "Tahiti Gold");
            put("#F4B350", "Casablanca");
            put("#F2784B", "Crusta");
            put("#EB974E", "Jaffa Light");
            put("#F5AB35", "Lightening Yellow");
            put("#D35400", "Burnt Orange");
            put("#F39C12", "Buttercup");
            put("#F9690E", "Ecstasy");
            put("#F9BF3B", "Sandstorm");
            put("#F27935", "Jaffa");
            put("#E67E22", "Zest");
            put("#6C7A89", "Lynch");
            put("#95A5A6", "Cascade");
        }
    };

    /**
     * @return a random color
     */
    public static Integer random(){
        return android.graphics.Color.parseColor(Value.As.LIST(COLORS.keySet()).get(new Random().nextInt(COLORS.size())));
    }
}