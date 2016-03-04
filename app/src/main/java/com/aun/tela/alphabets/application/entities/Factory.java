package com.aun.tela.alphabets.application.entities;

import com.aun.tela.alphabets.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

        public static final List<String> CONSONANTS_LOWERCASE = Value.As.<String>LIST("b", "c", "d", "f", "g", "h", "j", /*"k",*/
                "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", /*"x",*/ "y", "z");

        public static final List<String> VOWELS = Value.As.<String>LIST("a", "e", "i", "o", "u");

        public static Map<Integer, String> getPaddedLettersFrom(String alphabet, int max){
            Random random = new Random();
            int randPosition = random.nextInt(max);
            Map<Integer, String> map = new HashMap<>();
            map.put(randPosition, alphabet.toLowerCase());
            List<String> alphabets = copyAlphabetsLowercase();
            alphabets.remove(alphabet.toLowerCase());
            int added = randPosition == 0 ? 1 : 0;
            while(added < max){
                if(added != randPosition) {
                    if (alphabets.size() < 1) {
                        map.put(map.size(), alphabet);
                    } else {
                        int curr = alphabets.size();
                        int position = random.nextInt(curr);
                        String randomAlphabet = alphabets.get(position);
                        map.put(added, alphabets.remove(position));
                    }
                }
                added++;
            }
            return map;
        }

        public static class LetterSound{

            public String alphabet;
            public int introIdentResId;
            public int introSoundResId;
            public int soundResId;
            public int wordIntroResId;

            public Word[] words;

            public static class Word {
                public int audioResId;
                public String word;

                public Word(String word, int audioResId){this.word = word; this.audioResId = audioResId;}

            }

            public String passageText;

            public LetterSound(String alphabet, int introIdentResId, int introSoundResId, int soundResId, int wordIntroResId, Word... words){
                this.alphabet = alphabet; this.introIdentResId = introIdentResId; this.introSoundResId = introSoundResId; this.soundResId = soundResId;
                this.wordIntroResId = wordIntroResId; this.words = words;
            }

            public LetterSound setPassageText(String passageText){this.passageText = passageText; return this;}

            public static LetterSound build(String alphabet){
                int introIdentResId;
                int introSoundResId;
                int soundResId;
                int wordIntroResId;
                Word[] words;
                String alp;
                switch (alphabet.toLowerCase()){
                    default:
                        introIdentResId = R.raw.tela_audio_alpha_a_ident;
                        introSoundResId = R.raw.tela_letsnd_introsnd_a;
                        soundResId = R.raw.tela_letsnd_snd_a;
                        wordIntroResId = R.raw.tela_letsnd_wordintrosnd_a;
                        alp = "A";
                        words = new Word[]{
                                new Word("Cat", R.raw.tela_letsnd_word_cat),
                                new Word("Tap", R.raw.tela_letsnd_word_tap),
                                new Word("Ant", R.raw.tela_letsnd_word_ant),
                                new Word("Pan", R.raw.tela_letsnd_word_pan),
                                new Word("Rat", R.raw.tela_letsnd_word_rat),
                                new Word("Accident", R.raw.tela_letsnd_word_accident),
                                new Word("Arm", R.raw.tela_letsnd_word_arm),
                                new Word("Abuse", R.raw.tela_letsnd_word_abuse),
                                new Word("Ran", R.raw.tela_letsnd_word_ran),
                                new Word("Sand", R.raw.tela_letsnd_word_sand)
                        };
                        break;
                }
                return new LetterSound(alp, introIdentResId, introSoundResId, soundResId, wordIntroResId, words);
            }
        }

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

        public static List<String> getVowels(){
            return VOWELS;
        }

        public static List<String> copyVowels(){
            return Value.As.LIST(getVowels());
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

        public static List<String> getConsonantsLowercase(){
            return CONSONANTS_LOWERCASE;
        }

        public static List<String> copyAlphabetConsonants(){
            return Value.As.LIST(getConsonantsLowercase());
        }

        public static class Consonant {

            public HashMap<Integer, ConsonantSound> consonantSounds;
            public String accompanyingText;
            public int imageResId, accompanyingTextSoundResId;
            public String value;

            public Consonant(String value, HashMap<Integer, ConsonantSound> consonantSounds, String accompanyingText, int imageResId, int accompanyingTextSoundResId){
                this.consonantSounds = consonantSounds; this.accompanyingText = accompanyingText; this.imageResId = imageResId; this.accompanyingTextSoundResId = accompanyingTextSoundResId;
                this.value = value;
            }

            public static class ConsonantSound{
                public String alphabet; public int soundResId;
                public ConsonantSound(String alphabet, int soundResId){
                    this.alphabet = alphabet; this.soundResId = soundResId;
                }
            }

            public static Consonant build(String alphabet){
                HashMap<Integer, ConsonantSound> consonantSoundHashMap = new HashMap<>();
                String accompanyingText = "";
                int imageResId = 0, accompanyingTextSoundResId = 0;

                switch (alphabet.toLowerCase()){
                    case "b":
                        consonantSoundHashMap.put(0, new ConsonantSound("B", R.raw.b));
                        consonantSoundHashMap.put(1, new ConsonantSound("b", R.raw.b));
                        accompanyingText = "Baby in a book";
                        imageResId = R.drawable.consonant_baby_in_book;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_b_ident;
                        break;
                    case "c":
                        consonantSoundHashMap.put(0, new ConsonantSound("C", R.raw.c));
                        consonantSoundHashMap.put(1, new ConsonantSound("c", R.raw.c));
                        consonantSoundHashMap.put(2, new ConsonantSound("K", R.raw.c));
                        consonantSoundHashMap.put(3, new ConsonantSound("k", R.raw.c));
                        accompanyingText = "Cat in a car";
                        imageResId = R.drawable.consonant_cat_in_car;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_c_ident;
                        break;
                    case "d":
                        consonantSoundHashMap.put(0, new ConsonantSound("D", R.raw.d));
                        consonantSoundHashMap.put(1, new ConsonantSound("d", R.raw.d));
                        accompanyingText = "Dad and a dog";
                        imageResId = R.drawable.consonant_dad_and_dog;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_d_ident;
                        break;
                    case "f":
                        consonantSoundHashMap.put(0, new ConsonantSound("F", R.raw.f));
                        consonantSoundHashMap.put(1, new ConsonantSound("f", R.raw.f));
                        accompanyingText = "Fish on a frog";
                        imageResId = R.drawable.consonant_fish_on_frog;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_f_ident;
                        break;
                    case "g":
                        consonantSoundHashMap.put(0, new ConsonantSound("G", R.raw.g));
                        consonantSoundHashMap.put(1, new ConsonantSound("g", R.raw.g));
                        accompanyingText = "Goat on the grass";
                        imageResId = R.drawable.consonant_goat_on_grass;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_g_ident;
                        break;
                    case "h":
                        consonantSoundHashMap.put(0, new ConsonantSound("H", R.raw.h));
                        consonantSoundHashMap.put(1, new ConsonantSound("h", R.raw.h));
                        accompanyingText = "Hat on the head";
                        imageResId = R.drawable.consonant_hat_on_head;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_h_ident;
                        break;
                    case "j":
                        consonantSoundHashMap.put(0, new ConsonantSound("J", R.raw.j));
                        consonantSoundHashMap.put(1, new ConsonantSound("j", R.raw.j));
                        accompanyingText = "Jam for John";
                        imageResId = R.drawable.consonant_jam_for_jim;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_j_ident;
                        break;
                    case "l":
                        consonantSoundHashMap.put(0, new ConsonantSound("L", R.raw.l));
                        consonantSoundHashMap.put(1, new ConsonantSound("l", R.raw.l));
                        accompanyingText = "Leg on a log";
                        imageResId = R.drawable.consonant_leg_on_log;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_l_ident;
                        break;
                    case "m":
                        consonantSoundHashMap.put(0, new ConsonantSound("M", R.raw.m));
                        consonantSoundHashMap.put(1, new ConsonantSound("m", R.raw.m));
                        accompanyingText = "Man on the moon";
                        imageResId = R.drawable.consonant_man_on_moon;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_m_ident;
                        break;
                    case "n":
                        consonantSoundHashMap.put(0, new ConsonantSound("N", R.raw.n));
                        consonantSoundHashMap.put(1, new ConsonantSound("n", R.raw.n));
                        accompanyingText = "Nail in a net";
                        imageResId = R.drawable.consonant_nail_in_net;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_n_ident;
                        break;
                    case "p":
                        consonantSoundHashMap.put(0, new ConsonantSound("P", R.raw.p));
                        consonantSoundHashMap.put(1, new ConsonantSound("p", R.raw.p));
                        accompanyingText = "Pencil in a pot";
                        imageResId = R.drawable.consonant_pen_in_pot;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_p_ident;
                        break;
                    case "r":
                        consonantSoundHashMap.put(0, new ConsonantSound("R", R.raw.r));
                        consonantSoundHashMap.put(1, new ConsonantSound("r", R.raw.r));
                        accompanyingText = "Ring on a rat";
                        imageResId = R.drawable.consonant_ring_on_rat;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_r_ident;
                        break;
                    case "s":
                        consonantSoundHashMap.put(0, new ConsonantSound("S", R.raw.s));
                        consonantSoundHashMap.put(1, new ConsonantSound("s", R.raw.s));
                        accompanyingText = "Snake in the sun";
                        imageResId = R.drawable.consonant_snake_in_sun;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_s_ident;
                        break;
                    case "t":
                        consonantSoundHashMap.put(0, new ConsonantSound("T", R.raw.t));
                        consonantSoundHashMap.put(1, new ConsonantSound("t", R.raw.t));
                        accompanyingText = "Teddy on a table";
                        imageResId = R.drawable.consonant_teddy_on_table;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_t_ident;
                        break;
                    case "v":
                        consonantSoundHashMap.put(0, new ConsonantSound("V", R.raw.v));
                        consonantSoundHashMap.put(1, new ConsonantSound("v", R.raw.v));
                        accompanyingText = "Vultures at the vet";
                        imageResId = R.drawable.consonant_baby_in_book;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_v_ident;
                        break;
                    case "w":
                        consonantSoundHashMap.put(0, new ConsonantSound("W", R.raw.w));
                        consonantSoundHashMap.put(1, new ConsonantSound("w", R.raw.w));
                        accompanyingText = "Water in a well";
                        imageResId = R.drawable.consonant_water_in_well;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_w_ident;
                        break;
                    case "y":
                        consonantSoundHashMap.put(0, new ConsonantSound("Y", R.raw.y));
                        consonantSoundHashMap.put(1, new ConsonantSound("y", R.raw.y));
                        accompanyingText = "Yam for you";
                        imageResId = R.drawable.consonant_yam_for_you;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_y_ident;
                        break;
                    case "z":
                        consonantSoundHashMap.put(0, new ConsonantSound("Z", R.raw.z));
                        consonantSoundHashMap.put(1, new ConsonantSound("z", R.raw.z));
                        accompanyingText = "Zip on a zebra";
                        imageResId = R.drawable.consonant_zip_on_zebra;
                        accompanyingTextSoundResId = R.raw.tela_audio_alpha_z_ident;
                        break;
                }
                return new Consonant(alphabet, consonantSoundHashMap, accompanyingText, imageResId, accompanyingTextSoundResId);
            }

            public static int getPosition(String consonant){
                switch (consonant.toLowerCase()){
                    case "b": return 0;case "c": return 1;case "d": return 2;case "f": return 3;case "g":return 4;case "h": return 5;
                    case "j": return 6;/*case "k": return 7;*/case"l": return 7;case "m": return 8;case "n": return 9;case"p":return 10;
                    case "q":return 11;case"r":return 12;case"s": return 13;case "t":return 14;case"v":return 15;case"w":return 16;
                    /*case "x": return 18;*/case "y":return 17;case"z": return 18;
                    default: return -1;
                }
            }
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
            public Integer audio_res_intro, audio_res_ident, audio_res_cap_ident, audio_res_low_ident, audio_res_cap_low_desc,
            audio_res_find_1, audio_res_find_2, audio_res_find_3, audio_res_exit;
            private Map<Integer, WordImagePair> wordImagePairMap;

            public Alphabet setAudioRes(int audio_res_intro, int audio_res_ident, int audio_res_cap_ident, int audio_res_low_ident,
                                        int audio_res_cap_low_desc, int audio_res_find_1, int audio_res_find_2, int audio_res_find_3,
                                        int audio_res_exit){
                this.audio_res_intro = audio_res_intro;
                this.audio_res_ident = audio_res_ident;
                this.audio_res_cap_ident = audio_res_cap_ident;
                this.audio_res_low_ident = audio_res_low_ident;
                this.audio_res_cap_low_desc = audio_res_cap_low_desc;
                this.audio_res_find_1 = audio_res_find_1;
                this.audio_res_find_2 = audio_res_find_2;
                this.audio_res_find_3 = audio_res_find_3;
                this.audio_res_exit = audio_res_exit;
                return this;
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
                int imageRes = 0; // the resourceId for this image tied to this word
                public int audioRes = 0;

                /**
                 * Constructor to create a new WordImagePair
                 * @param word the word for this pair
                 * @param imageRes the resourceId for the image for this pair
                 */
                public WordImagePair(String word, int imageRes, int audioRes){this.word = word; this.imageRes = imageRes; this.audioRes = audioRes;}
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
            HashMap<Integer, Alphabet.WordImagePair> wordImagePairMap = new HashMap<>();
            List<Alphabet.WordImagePair> pairs = new ArrayList<>();
            Integer  audio_res_intro, audio_res_ident, audio_res_cap_ident, audio_res_low_ident, audio_res_cap_low_desc,
                    audio_res_find_1, audio_res_find_2, audio_res_find_3, audio_res_exit;
            switch (alphabet.toLowerCase()){
                case "a":
                   // pairs.add(new Alphabet.WordImagePair("Abacus", R.drawable.abacus));
                    //pairs.add(new Alphabet.WordImagePair("Adaptor", R.drawable.adaptor));
                    //pairs.add(new Alphabet.WordImagePair("Alms", R.drawable.alms));
                    //pairs.add(new Alphabet.WordImagePair("Angry", R.drawable.angry));
                    pairs.add(new Alphabet.WordImagePair("Ant", R.drawable.ant, R.raw.tela_audio_alpha_a_ant));
                    pairs.add(new Alphabet.WordImagePair("Apple", R.drawable.apple, R.raw.tela_audio_alpha_a_apple));
                    pairs.add(new Alphabet.WordImagePair("Plate", R.drawable.plate, R.raw.tela_audio_alpha_a_plate));
                    //pairs.add(new Alphabet.WordImagePair("Apex", R.drawable.apex));
                    //pairs.add(new Alphabet.WordImagePair("Arch", R.drawable.arch));
                    //pairs.add(new Alphabet.WordImagePair("Arm", R.drawable.arm));
                    //pairs.add(new Alphabet.WordImagePair("Army", R.drawable.army));
                    //pairs.add(new Alphabet.WordImagePair("Axe", R.drawable.axe));
                    buildWordImagePairHash(wordImagePairMap, pairs, 3);
                    audio_res_intro = R.raw.tela_audio_alpha_a_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_a_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_a_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_a_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_a_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_a_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_a_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_a_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_a_exit;
                    break;
                case "b":
                    //pairs.add(new Alphabet.WordImagePair("Bag", R.drawable.bag));
                    //pairs.add(new Alphabet.WordImagePair("Balance", R.drawable.balance));
                    //pairs.add(new Alphabet.WordImagePair("Bandage", R.drawable.bandage));
                    //pairs.add(new Alphabet.WordImagePair("Battery", R.drawable.battery));
                    //pairs.add(new Alphabet.WordImagePair("Bed", R.drawable.bed));
                    //pairs.add(new Alphabet.WordImagePair("Bee", R.drawable.bee));
                    //pairs.add(new Alphabet.WordImagePair("Bell", R.drawable.bell));
                    //pairs.add(new Alphabet.WordImagePair("Bicycle", R.drawable.bicycle));
                    //pairs.add(new Alphabet.WordImagePair("Big", R.drawable.big));
                    //pairs.add(new Alphabet.WordImagePair("Bleach", R.drawable.bleach));
                    //pairs.add(new Alphabet.WordImagePair("Bomb", R.drawable.bomb));
                    pairs.add(new Alphabet.WordImagePair("Book", R.drawable.book, R.raw.tela_audio_alpha_b_book));
                    pairs.add(new Alphabet.WordImagePair("Broom", R.drawable.broom, R.raw.tela_audio_alpha_b_broom));
                    pairs.add(new Alphabet.WordImagePair("Table", R.drawable.table,  R.raw.tela_audio_alpha_b_table));
                    buildWordImagePairHash(wordImagePairMap, pairs, 3);
                    audio_res_intro = R.raw.tela_audio_alpha_b_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_b_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_b_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_b_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_b_cap_low_ident;
                    audio_res_find_1 = R.raw.tela_audio_alpha_b_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_b_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_b_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_b_exit;
                    break;
                case "c":
                    /*pairs.add(new Alphabet.WordImagePair("Cabbage", R.drawable.cabbage));
                    pairs.add(new Alphabet.WordImagePair("Cage", R.drawable.cage));
                    pairs.add(new Alphabet.WordImagePair("Calf", R.drawable.calf));
                    pairs.add(new Alphabet.WordImagePair("Can", R.drawable.can));
                    pairs.add(new Alphabet.WordImagePair("Cap", R.drawable.cap));
                    pairs.add(new Alphabet.WordImagePair("Car", R.drawable.car));
                    pairs.add(new Alphabet.WordImagePair("Cargo", R.drawable.cargo));
                    pairs.add(new Alphabet.WordImagePair("Carrot", R.drawable.carrot));
                    pairs.add(new Alphabet.WordImagePair("Cat", R.drawable.cat));
                    pairs.add(new Alphabet.WordImagePair("Clap", R.drawable.clap));
                    pairs.add(new Alphabet.WordImagePair("Clown", R.drawable.clown));
                    pairs.add(new Alphabet.WordImagePair("Coat", R.drawable.coat));
                    pairs.add(new Alphabet.WordImagePair("Cow", R.drawable.cow));
                    pairs.add(new Alphabet.WordImagePair("Crack", R.drawable.crack));*/
                    pairs.add(new Alphabet.WordImagePair("Cat", R.drawable.cat, R.raw.tela_audio_alpha_c_cat));
                    pairs.add(new Alphabet.WordImagePair("Bucket", R.drawable.bucket, R.raw.tela_audio_alpha_c_bucket));
                    pairs.add(new Alphabet.WordImagePair("Cup", R.drawable.cup, R.raw.tela_audio_alpha_c_cup));
                    buildWordImagePairHash(wordImagePairMap, pairs, 3);
                    audio_res_intro = R.raw.tela_audio_alpha_c_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_c_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_c_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_c_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_c_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_c_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_c_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_c_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_c_exit;
                    break;
                case "d":
                    /*pairs.add(new Alphabet.WordImagePair("Dig", R.drawable.dig));
                    pairs.add(new Alphabet.WordImagePair("Dollar", R.drawable.dollar));
                    pairs.add(new Alphabet.WordImagePair("Dolphin", R.drawable.dolphin));
                    pairs.add(new Alphabet.WordImagePair("Dove", R.drawable.dove));
                    pairs.add(new Alphabet.WordImagePair("Drag", R.drawable.drag));
                    pairs.add(new Alphabet.WordImagePair("Dress", R.drawable.dress));
                    pairs.add(new Alphabet.WordImagePair("Drill", R.drawable.drill));
                    pairs.add(new Alphabet.WordImagePair("Drive", R.drawable.drive));*/
                    pairs.add(new Alphabet.WordImagePair("Duck", R.drawable.duck, R.raw.tela_audio_alpha_d_duck));
                    pairs.add(new Alphabet.WordImagePair("Door", R.drawable.door, R.raw.tela_audio_alpha_d_door));
                    pairs.add(new Alphabet.WordImagePair("Window", R.drawable.window, R.raw.tela_audio_alpha_d_window));
                    buildWordImagePairHash(wordImagePairMap, pairs, 3);
                    audio_res_intro = R.raw.tela_audio_alpha_d_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_d_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_d_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_d_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_d_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_d_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_d_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_d_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_d_exit;
                    break;
                case "e":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Egg", R.drawable.egg, R.raw.tela_audio_alpha_e_egg));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Elephant", R.drawable.elephant, R.raw.tela_audio_alpha_e_elephant));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Machet", R.drawable.machet, R.raw.tela_audio_alpha_e_machet));
                    audio_res_intro = R.raw.tela_audio_alpha_e_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_e_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_e_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_e_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_e_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_e_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_e_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_e_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_e_exit;
                    break;
                case "f":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Finger", R.drawable.finger, R.raw.tela_audio_alpha_f_finger));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Fish", R.drawable.fish, R.raw.tela_audio_alpha_f_fish));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Leaf", R.drawable.leaf, R.raw.tela_audio_alpha_f_leaf));
                    audio_res_intro = R.raw.tela_audio_alpha_f_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_f_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_f_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_f_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_f_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_f_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_f_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_f_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_f_exit;
                    break;
                case "g":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Goat", R.drawable.goat, R.raw.tela_audio_alpha_g_goat));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Grass", R.drawable.grass, R.raw.tela_audio_alpha_g_grass));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Bag", R.drawable.bag, R.raw.tela_audio_alpha_g_bag));
                    audio_res_intro = R.raw.tela_audio_alpha_g_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_g_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_g_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_g_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_g_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_g_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_g_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_g_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_g_exit;
                    break;
                case "h":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Horn", R.drawable.horn, R.raw.tela_audio_alpha_h_horn));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Hush", R.drawable.hush, R.raw.tela_audio_alpha_h_hut));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Brush", R.drawable.brush, R.raw.tela_audio_alpha_h_brush));
                    audio_res_intro = R.raw.tela_audio_alpha_h_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_h_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_h_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_h_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_h_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_h_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_h_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_h_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_h_exit;
                    break;
                case "i":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Ice", R.drawable.ice, R.raw.tela_audio_alpha_i_ice));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Ink", R.drawable.ink, R.raw.tela_audio_alpha_i_ink));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Rice", R.drawable.rice, R.raw.tela_audio_alpha_i_rice));
                    audio_res_intro = R.raw.tela_audio_alpha_i_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_i_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_i_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_i_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_i_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_i_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_i_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_i_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_i_exit;
                    break;
                case "j":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Jug", R.drawable.jug, R.raw.tela_audio_alpha_j_jug));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Jump", R.drawable.jump, R.raw.tela_audio_alpha_j_jump));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Judge", R.drawable.judge, R.raw.tela_audio_alpha_j_judge));
                    audio_res_intro = R.raw.tela_audio_alpha_j_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_j_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_j_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_j_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_j_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_j_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_j_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_j_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_j_exit;
                    break;
                case "k":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Kettle", R.drawable.kettle, R.raw.tela_audio_alpha_k_kettle));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Kite", R.drawable.kite, R.raw.tela_audio_alpha_k_kite));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Ankle", R.drawable.ankle, R.raw.tela_audio_alpha_k_ankle));
                    audio_res_intro = R.raw.tela_audio_alpha_k_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_k_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_k_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_k_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_k_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_k_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_k_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_k_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_k_exit;
                    break;
                case "l":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Lion", R.drawable.lion, R.raw.tela_audio_alpha_l_lion));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Lemon", R.drawable.lemon, R.raw.tela_audio_alpha_l_lemon));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Doll", R.drawable.doll, R.raw.tela_audio_alpha_l_doll));
                    audio_res_intro = R.raw.tela_audio_alpha_l_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_l_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_l_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_l_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_l_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_l_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_l_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_l_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_l_exit;
                    break;
                case "m":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Monkey", R.drawable.monkey, R.raw.tela_audio_alpha_m_monkey));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Man", R.drawable.man, R.raw.tela_audio_alpha_m_man));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Lamp", R.drawable.lamp, R.raw.tela_audio_alpha_m_lamp));
                    audio_res_intro = R.raw.tela_audio_alpha_m_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_m_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_m_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_m_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_m_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_m_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_m_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_m_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_m_exit;
                    break;
                case "n":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Nail", R.drawable.nail, R.raw.tela_audio_alpha_n_nail));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Nest", R.drawable.nest, R.raw.tela_audio_alpha_n_nest));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Money", R.drawable.money, R.raw.tela_audio_alpha_n_money));
                    audio_res_intro = R.raw.tela_audio_alpha_n_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_n_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_n_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_n_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_n_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_n_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_n_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_n_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_n_exit;
                    break;
                case "o":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Orange", R.drawable.orange, R.raw.tela_audio_alpha_o_orange));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Owl", R.drawable.owl, R.raw.tela_audio_alpha_o_owl));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Bowl", R.drawable.bowl, R.raw.tela_audio_alpha_o_bowl));
                    wordImagePairMap.put(3, new Alphabet.WordImagePair("Boy", R.drawable.boy, R.raw.tela_audio_alpha_o_bowl));
                    audio_res_intro = R.raw.tela_audio_alpha_o_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_o_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_o_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_o_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_o_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_o_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_o_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_o_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_o_exit;
                    break;
                case "p":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Pineapple", R.drawable.pineapple, R.raw.tela_audio_alpha_p_pineapple));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Pot", R.drawable.pot, R.raw.tela_audio_alpha_p_pot));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Shop", R.drawable.shop, R.raw.tela_audio_alpha_p_shop));
                    audio_res_intro = R.raw.tela_audio_alpha_p_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_p_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_p_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_p_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_p_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_p_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_p_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_p_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_p_exit;
                    break;
                case "q":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Queen", R.drawable.queen, R.raw.tela_audio_alpha_q_queen));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Quarter", R.drawable.quarter, R.raw.tela_audio_alpha_q_quarter));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Mosque", R.drawable.mosque, R.raw.tela_audio_alpha_q_mosque));
                    audio_res_intro = R.raw.tela_audio_alpha_q_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_q_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_q_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_q_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_q_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_q_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_q_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_q_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_q_exit;
                    break;
                case "r":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Rat", R.drawable.rat, R.raw.tela_audio_alpha_r_rat));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Roof", R.drawable.roof, R.raw.tela_audio_alpha_r_roof));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Car", R.drawable.car, R.raw.tela_audio_alpha_r_car));
                    audio_res_intro = R.raw.tela_audio_alpha_r_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_r_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_r_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_r_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_r_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_r_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_r_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_r_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_r_exit;
                    break;
                case "s":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Stone", R.drawable.stone, R.raw.tela_audio_alpha_s_stone));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Bus", R.drawable.bus, R.raw.tela_audio_alpha_s_bus));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Nest", R.drawable.nest, R.raw.tela_audio_alpha_s_nest));
                    audio_res_intro = R.raw.tela_audio_alpha_s_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_s_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_s_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_s_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_s_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_s_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_s_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_s_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_s_exit;
                    break;
                case "t":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Tap", R.drawable.tap, R.raw.tela_audio_alpha_t_tap));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Train", R.drawable.train, R.raw.tela_audio_alpha_t_train));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Seat", R.drawable.seat, R.raw.tela_audio_alpha_t_seat));
                    audio_res_intro = R.raw.tela_audio_alpha_t_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_t_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_t_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_t_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_t_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_t_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_t_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_t_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_t_exit;
                    break;
                case "u":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Umbrella", R.drawable.umbrella, R.raw.tela_audio_alpha_u_umbrella));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("University", R.drawable.university, R.raw.tela_audio_alpha_u_university));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Soup", R.drawable.soup, R.raw.tela_audio_alpha_u_soup));
                    audio_res_intro = R.raw.tela_audio_alpha_u_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_u_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_u_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_u_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_u_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_u_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_u_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_u_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_u_exit;
                    break;
                case "v":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Van", R.drawable.van, R.raw.tela_audio_alpha_v_van));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Village", R.drawable.village, R.raw.tela_audio_alpha_v_village));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("River", R.drawable.river, R.raw.tela_audio_alpha_v_river));
                    audio_res_intro = R.raw.tela_audio_alpha_v_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_v_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_v_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_v_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_v_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_v_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_v_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_v_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_v_exit;
                    break;
                case "w":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Water", R.drawable.water, R.raw.tela_audio_alpha_w_water));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Well", R.drawable.well, R.raw.tela_audio_alpha_w_well));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Towel", R.drawable.towel, R.raw.tela_audio_alpha_w_towel));
                    audio_res_intro = R.raw.tela_audio_alpha_w_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_w_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_w_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_w_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_w_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_w_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_w_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_w_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_w_exit;
                    break;
                case "x":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Xylophone", R.drawable.xylophone, R.raw.tela_audio_alpha_x_xylophone));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Axe", R.drawable.axe, R.raw.tela_audio_alpha_x_axe));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Box", R.drawable.box, R.raw.tela_audio_alpha_x_box));
                    audio_res_intro = R.raw.tela_audio_alpha_x_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_x_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_x_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_x_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_x_cap_low_ident;
                    audio_res_find_1 = R.raw.tela_audio_alpha_x_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_x_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_x_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_x_exit;
                    break;
                case "y":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Yam", R.drawable.yam, R.raw.tela_audio_alpha_y_yam));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Baby", R.drawable.baby, R.raw.tela_audio_alpha_y_baby));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Butterfly", R.drawable.butterfly, R.raw.tela_audio_alpha_y_butterfly));
                    audio_res_intro = R.raw.tela_audio_alpha_y_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_y_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_y_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_y_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_y_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_y_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_y_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_y_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_y_exit;
                    break;
                case "z":
                    wordImagePairMap = new HashMap<>();
                    wordImagePairMap.put(0, new Alphabet.WordImagePair("Zebra", R.drawable.zebra, R.raw.tela_audio_alpha_z_zebra));
                    wordImagePairMap.put(1, new Alphabet.WordImagePair("Zip", R.drawable.zipper, R.raw.tela_audio_alpha_z_zip));
                    wordImagePairMap.put(2, new Alphabet.WordImagePair("Freezer", R.drawable.freezer, R.raw.tela_audio_alpha_z_freezer));
                    audio_res_intro = R.raw.tela_audio_alpha_z_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_z_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_z_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_z_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_z_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_z_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_z_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_z_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_z_exit;
                    break;
                default:
                    audio_res_intro = R.raw.tela_audio_alpha_a_intro;
                    audio_res_ident = R.raw.tela_audio_alpha_a_ident;
                    audio_res_cap_ident = R.raw.tela_audio_alpha_a_cap_ident;
                    audio_res_low_ident = R.raw.tela_audio_alpha_a_low_ident;
                    audio_res_cap_low_desc = R.raw.tela_audio_alpha_a_cap_low_desc;
                    audio_res_find_1 = R.raw.tela_audio_alpha_a_find_1;
                    audio_res_find_2 = R.raw.tela_audio_alpha_a_find_2;
                    audio_res_find_3 = R.raw.tela_audio_alpha_a_find_3;
                    audio_res_exit = R.raw.tela_audio_alpha_a_exit;
                    break;
            }
            //buildWordImagePairHash(wordImagePairMap, pairs, 3);
            return new Alphabet()
                    .setLetter(alphabet)
                    .setWordImagePairMap(wordImagePairMap)
                    .setAudioRes(audio_res_intro, audio_res_ident, audio_res_cap_ident, audio_res_low_ident, audio_res_cap_low_desc, audio_res_find_1, audio_res_find_2, audio_res_find_3, audio_res_exit);

        }

        static void buildWordImagePairHash(HashMap<Integer, Alphabet.WordImagePair> map, List<Alphabet.WordImagePair> list, int limit ){
            Random random = new Random();
            for(int i = 0; i < limit; i++){
                map.put(i, list.remove(random.nextInt(list.size())));
            }
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
