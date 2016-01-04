package com.aun.tela.alphabets.application.entities;

import com.aun.tela.alphabets.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.meengle.util.Value;

public class Factory {

    public static final class Alphabets {

        public static List<String> ALPHABETS_UPPERCASE = Value.As.<String>LIST("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
        public static List<String> ALPHABETS_LOWERCASE = Value.As.<String>LIST("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");

        public static List<String> getAlphabetsUppercase(){
            return ALPHABETS_UPPERCASE;
        }

        public static List<String> getAlphabetsLowercase(){
            return ALPHABETS_LOWERCASE;
        }

        public static List<String> copyAlphabetsUppercase(){
            return Value.As.LIST(getAlphabetsUppercase());
        }

        public static List<String> copyAlphabetsLowercase(){
            return Value.As.LIST(getAlphabetsLowercase());
        }

        public static class Alphabet {
            private String value;
            public Integer intro, introInfo, presentation, presentationInfo, word1Info, word1, word2Info, word2, word3Info, word3, outro;
            private Map<Integer, AlphabetInfo> alphabetInfoMap;

            public Alphabet setIntro(Integer intro){
                this.intro = intro; return this;
            }

            public Alphabet setIntroInfo(Integer intro){
                this.introInfo = intro; return this;
            }

            public Alphabet setPresentation(Integer presentation){
                this.presentation = presentation; return this;
            }

            public Alphabet setPresentationInfo(Integer presentationInfo){this.presentationInfo = presentationInfo; return this;}

            public Alphabet setWord1Info(Integer word1Info){this.word1Info = word1Info; return this;}

            public Alphabet setWord1(Integer word1){this.word1 = word1; return this;}

            public Alphabet setWord2Info(Integer word2Info){this.word2Info = word2Info; return this;}

            public Alphabet setWord2(Integer word2){this.word2 = word2; return this;}

            public Alphabet setWord3Info(Integer word3Info){this.word3Info = word3Info; return this;}

            public Alphabet setWord3(Integer word3){this.word3 = word3; return this;}

            public Alphabet setOutro(Integer outro){
                this.outro = outro; return this;
            }

            public Alphabet setValue(String value){
                this.value = value; return this;
            }

            public Alphabet setAlphabetInfoMap(Map<Integer, AlphabetInfo> alphabetInfoMap){
                this.alphabetInfoMap = alphabetInfoMap; return this;
            }

            public String getValue(){
                return value;
            }

            public String getUppercase(){
                return getValue().toUpperCase();
            }

            public String getLowerCase(){
                return getValue().toLowerCase();
            }

            public Map<Integer, AlphabetInfo> getAlphabetInfoMap(){
                return this.alphabetInfoMap;
            }

            public static class AlphabetInfo{
                String title;
                int imageRes;

                public AlphabetInfo(String title, int imageRes){this.title = title; this.imageRes = imageRes;}

                public AlphabetInfo setTitle(String title){this.title = title; return this;}
                public AlphabetInfo setImageRes(int imageRes){this.imageRes = imageRes; return this;}

                public String getTitle(){return this.title;}
                public int getImageRes(){return this.imageRes;}
            }

        }


        public static Alphabet build(String alphabet){
            Map<Integer, Alphabet.AlphabetInfo> alphabetInfoMap = null;
            Integer intro = null, introInfo = null, presentation = null, presentationInfo = null, word1Info = null,
                    word1 = null, word2Info = null, word2 = null, word3Info = null, word3 = null,
                    outro = null;
            switch (alphabet.toLowerCase()){
                case "a":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Apple", R.drawable.apple));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Ant", R.drawable.ant));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Plate", R.drawable.plate));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "b":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Bicycle", R.drawable.bicycle));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Bird", R.drawable.bird));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Football", R.drawable.football));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "c":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Cake", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Cutlass", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Circle", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "d":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Deer", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Door", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Diet coke",0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "e":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Egg", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Ferry", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Earth", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "f":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Food", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Foot", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Football", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "g":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Game", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Goat", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Giggle", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "h":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("House", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Hunter", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Hair", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "i":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Idol", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Ice", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Ice-cream", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "j":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Jug", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Jam", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Jail", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "k":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Kite", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Kettle", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Brick", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "l":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Light bulb", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Bubble", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Bicycle", R.drawable.bicycle));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "m":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Machine", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Match", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Meat", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "n":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Nest", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Nut", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Fence", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "o":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Ocean", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Orange", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Football", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "p":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Pet", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Pear", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Penny", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "q":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Queen", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Queue", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Quartz", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "r":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Rabbit", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Rat", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Rake", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "s":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Saddle",0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Sand", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Share", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "t":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Tent", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Table", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Taste", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "u":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Umbrella", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Pull", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Under", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "v":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Veteran", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Vapor", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Violet", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "w":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Wall", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Waste", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Water", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "x":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Xylophone", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("X-ray", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Next", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "y":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Young", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Bicycle", 0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Yatch", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;
                case "z":
                    alphabetInfoMap = new HashMap<>();
                    alphabetInfoMap.put(0, new Alphabet.AlphabetInfo("Zebra", 0));
                    alphabetInfoMap.put(1, new Alphabet.AlphabetInfo("Zig-zag",0));
                    alphabetInfoMap.put(2, new Alphabet.AlphabetInfo("Zipper", 0));
                    intro = introInfo = presentation = presentationInfo = word1Info = word1 = word2Info = word2 = word3Info = word3 = outro = R.raw.a1;
                    break;

            }
            return new Alphabet()
                    .setValue(alphabet)
                    .setAlphabetInfoMap(alphabetInfoMap)
                    .setIntro(intro)
                    .setIntroInfo(introInfo)
                    .setPresentation(presentation)
                    .setPresentationInfo(presentationInfo)
                    .setWord1Info(word1Info)
                    .setWord1(word1)
                    .setWord2Info(word2Info)
                    .setWord2(word2)
                    .setWord3Info(word3Info)
                    .setWord3(word3)
                    .setOutro(outro);

        }

        public static int getPosition(String string){
            switch (string.toLowerCase()){
                case "a": return 0;case "b": return 1;case "c": return 2;case "d": return 3;case "e":return 4;case "f": return 5;
                case "g": return 6;case "h": return 7;case"i": return 8;case "j": return 9;case "k": return 10;case"l":return 11;
                case "m":return 12;case"n":return 13;case"o": return 14;case "p":return 15;case"q":return 16;case"r":return 17;
                case "s": return 18;case "t":return 19;case"u":return 20;case"v":return 21;case"w":return 22;case"x":return 23;
                case"y":return 24;case"z":return 25;
                default: return -1;
            }
        };

    }




}
