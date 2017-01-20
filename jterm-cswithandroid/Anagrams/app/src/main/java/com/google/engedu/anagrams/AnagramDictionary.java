/*
 *  Copyright 2016 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.google.engedu.anagrams;

import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashSet<String> wordSet = new HashSet<String>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            //
            //  Your code here
            //
            wordList.add(word);
            wordSet.add(word);
            String sortedWord = sortLetters(word);
            if (lettersToWord.containsKey(sortedWord)){
                lettersToWord.get(sortedWord).add(word);
            }else{
                ArrayList<String> newList = new ArrayList<String>();
                newList.add(word);
                lettersToWord.put(sortedWord,newList);
            }

        }
    }

    public boolean isGoodWord(String word, String base) {
        //
        // Your code here
        //
        if (wordSet.contains(word)&&word.contains(base)==false){
            return true;
        }
        return false;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        //
        // Your code here
        //
        for (int i=0;i<wordList.size();i++){
            if (targetWord.length() != wordList.get(i).length()){
                return result;
            }
            if (sortLetters(targetWord)==sortLetters(wordList.get(i))){
                result.add(wordList.get(i));
            }
        }
        return result;
    }

    @VisibleForTesting
    static boolean isAnagram(String first, String second) {
        // Check empty
        if (first == null || first.equals("") || second == null || second.equals("")) {
            return false;
        }
        // Check length
        if (first.length() != second.length()) {
            return false;
        }
        return sortLetters(first).equals(sortLetters(second));
    }

    @VisibleForTesting
    static String sortLetters(String input) {
        char[] chars = input.toCharArray();
        Arrays.sort(chars);
        String result = "";
        for (char c : chars) {
            result += c;
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        //
        // Your code here
        //


        return result;
    }

    public String pickGoodStarterWord() {
        //
        // Your code here
        //
        return "stop";
    }
}
