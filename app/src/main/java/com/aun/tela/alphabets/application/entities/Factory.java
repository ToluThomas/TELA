package com.aun.tela.alphabets.application.entities;

import com.aun.tela.alphabets.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.meengle.util.Value;

/**
 * Created by Joseph Dalughut on 31/12/15 at 7:38 PM.
 * Project name : TELA.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class Factory {

    /**
     * This class holds the various methods for creating and getting the entity resources to be used
     * in the application. Instead of using a database, we could just statically code these (non complex)
     * entities and return them when needed, beats creating a database, database tables and querying them
     * each time we need entities plus, it's faster than using a database.
     */

    /**
     * The class {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets}
     */
    public static final class Alphabets {

        public static final List<String> ALPHABETS_UPPERCASE = Value.As.<String>LIST("A", "B", "C", "D",
                "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z");

        public static final List<String> ALPHABETS_LOWERCASE = Value.As.<String>LIST("a", "b", "c", "d",
                "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
                "v", "w", "x", "y", "z");

        /**
         * @return the list containing the 26 alphabets in uppercase
         */
        public static List<String> getAlphabetsUppercase(){
            return ALPHABETS_UPPERCASE;
        }

        /**
         * @return the list containing the 26 alphabets in lowercase
         */
        public static List<String> getAlphabetsLowercase(){
            return ALPHABETS_LOWERCASE;
        }

        /**
         * @return a copy of the list containing the 26 alphabets in uppercase
         */
        public static List<String> copyAlphabetsUppercase(){
            return Value.As.LIST(getAlphabetsUppercase());
        }

        /**
         * @return a copy of list containing the 26 alphabets in uppercase
         */
        public static List<String> copyAlphabetsLowercase(){
            return Value.As.LIST(getAlphabetsLowercase());
        }

        /**
         * This entity class is a model to be used for the animations based on the app script by Tela.
         * It holds an Alphabet, a {@link Map} of {@link WordImagePair}
         * and resource ids for sounds to be played at specific instants from within the application.
         *
         * Note that the nomenclature the sound resourceIds is in the following order
         * First CamelCaseLetter + Purpose + PurposeOrdinal + "Sound" + ordinal
         * for example, aLUASound1 is for : AlphaLearningUpcaseAnimationSound1 (no purposes identified)
         * another example, aLWAWord2Sound1 is for : AlphaLearningWordAnimationWord2Sound1 (purpose is word, purpose ordinal is 2)
         *
         * More information can be found for each setter and getter method.
         */
        public static class Alphabet {
            private String letter;
            public Integer aLUASound1, aLUASound2, aLLASound1, aLLASound2, aLWAWord1Sound1, aLWAWord1Sound2, aLWAWord2Sound1,
                    aLWAWord2Sound2, aLWAWord3Sound1, aLWAWord3Sound2, aLEASound1;
            private Map<Integer, WordImagePair> wordImagePairMap;

            /**
             * Set AlphaLearningUpcaseSound 1
             * @param aLUASound1 the resourceId of the sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLUASound1(Integer aLUASound1){
                this.aLUASound1 = aLUASound1; return this;
            }
            /**
             * Set AlphaLearningUpcaseSound 2
             * @param aLUASound2 the resourceId of this sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLUASound2(Integer aLUASound2){
                this.aLUASound2 = aLUASound2; return this;
            }

            /**
             * Set AlphaLearningLowCaseSound 1
             * @param aLLASound1 the rsourceId of the sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLLASound1(Integer aLLASound1){
                this.aLLASound1 = aLLASound1; return this;
            }

            /**
             * Set AlphaLearningLowCaseSound 2
             * @param aLLASound2 the rsourceId of the sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLLASound2(Integer aLLASound2){this.aLLASound2 = aLLASound2; return this;}

            /**
             * Set AlphaLearningWordAnimation Word 1 Sound 1
             * @param aLWAWord1Sound1 the rsourceId of the sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLWAWord1Sound1(Integer aLWAWord1Sound1){this.aLWAWord1Sound1 = aLWAWord1Sound1; return this;}

            /**
             * Set AlphaLearningWordAnimation Word 1 Sound 2
             * @param aLWAWord1Sound2 the rsourceId of the sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLWAWord1Sound2(Integer aLWAWord1Sound2){this.aLWAWord1Sound2 = aLWAWord1Sound2; return this;}

            /**
             * Set AlphaLearningWordAnimation Word 2 Sound 1
             * @param aLWAWord2Sound1 the rsourceId of the sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLWAWord2Sound1(Integer aLWAWord2Sound1){this.aLWAWord2Sound1 = aLWAWord2Sound1; return this;}

            /**
             * Set AlphaLearningWordAnimation Word 2 Sound 2
             * @param aLWAWord2Sound2 the rsourceId of the sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLWAWord2Sound2(Integer aLWAWord2Sound2){this.aLWAWord2Sound2 = aLWAWord2Sound2; return this;}

            /**
             * Set AlphaLearningWordAnimation Word 3 Sound 1
             * @param aLWAWord3Sound1 the rsourceId of the sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLWAWord3Sound1(Integer aLWAWord3Sound1){this.aLWAWord3Sound1 = aLWAWord3Sound1; return this;}

            /**
             * Set AlphaLearningWordAnimation Word 3 Sound 2
             * @param aLWAWord3Sound2 the rsourceId of the sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLWAWord3Sound2(Integer aLWAWord3Sound2){this.aLWAWord3Sound2 = aLWAWord3Sound2; return this;}

            /**
             * Set AlphaLearningExitAnimation Sound 1
             * @param aLEASound1 the rsourceId of the sound to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setaLEASound1(Integer aLEASound1){
                this.aLEASound1 = aLEASound1; return this;
            }

            /**
             * Set the letter of this alphabet. Should be a single letter or character
             * @param letter the letter for this alphabet
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setLetter(String letter){
                this.letter = letter; return this;
            }

            /**
             * Set the {@link Map} of {@link WordImagePair} for this
             * alphabet. The class {@link WordImagePair} is an entity
             * which holds words and their corresponding images.
             * @param wordImagePairMap the map to set
             * @return the current {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public Alphabet setWordImagePairMap(Map<Integer, WordImagePair> wordImagePairMap){
                this.wordImagePairMap = wordImagePairMap; return this;
            }

            /**
             * @return the letter tied to this Alphabet instance
             */
            public String getLetter(){
                return letter;
            }

            /**
             * @return this alphabets letter in uppercase
             */
            public String getUppercase(){
                return getLetter().toUpperCase();
            }

            /**
             * @return this alphabets letter in lowercase
             */
            public String getLowerCase(){
                return getLetter().toLowerCase();
            }

            /**
             * @return the {@link Map} of {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet.WordImagePair}
             * tied to this {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet} instance
             */
            public Map<Integer, WordImagePair> getWordImagePairMap(){
                return this.wordImagePairMap;
            }

            /**
             * This class holds a word of type {@link String} and an {@link Integer} resourceId for an associated
             * {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
             */
            public static class WordImagePair {
                String word; // the word for this pair
                int imageRes; // the resourceId for this image tied to this word

                /**
                 * Constructor to create a new WordImagePair
                 * @param word the word for this pair
                 * @param imageRes the resourceId for the image for this pair
                 */
                public WordImagePair(String word, int imageRes){this.word = word; this.imageRes = imageRes;}

                /**
                 * Set the word for this pair
                 * @param word the word to set
                 * @return this {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet.WordImagePair} instance
                 */
                public WordImagePair setWord(String word){this.word = word; return this;}

                /**
                 * Set the image resourceId for this pair
                 * @param imageRes the resourceId to set
                 * @return this {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet.WordImagePair} instance
                 */
                public WordImagePair setImageRes(int imageRes){this.imageRes = imageRes; return this;}

                /**
                 * @return the word tied to this {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet.WordImagePair} instance
                 */
                public String getWord(){return this.word;}

                /**
                 * @return the image resourceId tied to this {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet.WordImagePair} instance
                 */
                public int getImageRes(){return this.imageRes;}
            }

        }

        /**
         * This method is used to build a {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet} based on an alphabet.
         * @param alphabet the alphabet string which should be made into an entity of type {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
         * @return the created instance of {@link com.aun.tela.alphabets.application.entities.Factory.Alphabets.Alphabet}
         */
        public static Alphabet build(String alphabet){
            Map<Integer, Alphabet.WordImagePair> wordImagePairMap = null;
            Integer aLUASound1 = null, aLUASound2 = null, aLLASound1 = null, aLLASound2 = null, aLWAWord1Sound1 = null,
                    aLLWAWord1Sound2 = null, aLLWAWord2Sound1 = null, aLLWAWord2Sound2 = null, aLLWAWord3Sound1 = null, aLLWAWord3Sound2 = null,
                    aLEASound1 = null;
            switch (alphabet.toLowerCase()){
                case "a":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Apple", R.drawable.apple));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Ant", R.drawable.ant));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Plate", R.drawable.plate));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "b":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Bicycle", R.drawable.bicycle));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Bird", R.drawable.bird));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Football", R.drawable.football));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "c":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Cake", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Cutlass", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Circle", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "d":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Deer", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Door", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Diet coke",0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "e":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Egg", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Ferry", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Earth", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "f":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Food", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Foot", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Football", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "g":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Game", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Goat", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Giggle", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "h":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("House", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Hunter", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Hair", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "i":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Idol", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Ice", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Ice-cream", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "j":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Jug", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Jam", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Jail", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "k":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Kite", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Kettle", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Brick", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "l":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Light bulb", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Bubble", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Bicycle", R.drawable.bicycle));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "m":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Machine", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Match", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Meat", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "n":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Nest", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Nut", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Fence", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "o":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Ocean", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Orange", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Football", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "p":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Pet", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Pear", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Penny", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "q":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Queen", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Queue", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Quartz", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "r":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Rabbit", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Rat", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Rake", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "s":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Saddle",0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Sand", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Share", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "t":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Tent", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Table", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Taste", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "u":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Umbrella", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Pull", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Under", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "v":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Veteran", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Vapor", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Violet", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "w":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Wall", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Waste", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Water", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "x":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Xylophone", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("X-ray", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Next", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "y":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Young", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Bicycle", 0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Yatch", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;
                case "z":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Zebra", 0));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Zig-zag",0));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Zipper", 0));
                    aLUASound1 = aLUASound2 = aLLASound1 = aLLASound2 = aLWAWord1Sound1 = aLLWAWord1Sound2 = aLLWAWord2Sound1 = aLLWAWord2Sound2 = aLLWAWord3Sound1 = aLLWAWord3Sound2 = aLEASound1 = R.raw.a1;
                    break;

            }
            return new Alphabet()
                    .setLetter(alphabet)
                    .setWordImagePairMap(wordImagePairMap)
                    .setaLUASound1(aLUASound1)
                    .setaLUASound2(aLUASound2)
                    .setaLLASound1(aLLASound1)
                    .setaLLASound2(aLLASound2)
                    .setaLWAWord1Sound1(aLWAWord1Sound1)
                    .setaLWAWord1Sound2(aLLWAWord1Sound2)
                    .setaLWAWord2Sound1(aLLWAWord2Sound1)
                    .setaLWAWord2Sound2(aLLWAWord2Sound2)
                    .setaLWAWord3Sound1(aLLWAWord3Sound1)
                    .setaLWAWord3Sound2(aLLWAWord3Sound2)
                    .setaLEASound1(aLEASound1);

        }

        /**
         * Convert an alphabet to its position in the aphabets. Note that this starts from ordinal 0, so each letter is -1 offset from
         * it's normal Alphabetic ordinal
         * @param alphabet the alphabet whose ordinal we need
         * @return the position of {@param alphabet} or -1 if not found
         */
        public static int getPosition(String alphabet){
            switch (alphabet.toLowerCase()){
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
