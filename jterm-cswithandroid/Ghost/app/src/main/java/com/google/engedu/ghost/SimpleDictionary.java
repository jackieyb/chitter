/* Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private static final String TAG = "SampleDictionary";
    private ArrayList<String> words;
    private Random mRandom;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @VisibleForTesting
    public SimpleDictionary(ArrayList<String> words, long randomSeed) {
        this.words = words;
        mRandom = new Random(randomSeed);
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if (prefix.isEmpty()){
            return words.get(mRandom.nextInt(words.size()));
        } else{
            int index = binarySearch(prefix,0,words.size());
            if (index == -1){
                return null;
            } else{
                return words.get(index);
            }
        }
    }
    //return index of prefix if found in words array
    private int binarySearch(String prefix, int low, int high){
        if (high<low){
            return -1;
        }
        int mid = (low+high)/2;
        if (words.get(mid).startsWith(prefix) && words.get(mid).length()>prefix.length()){
            return mid;
        }

        if(words.get(mid).compareTo(prefix)< 0){
            return binarySearch(prefix,mid+1,high);
        } else{
            return binarySearch(prefix,low,mid);
        }
    }


    @Override
    public String getGoodWordStartingWith(String prefix) {
        // If it has no length, just find any random word.
        if (prefix.length() == 0) {
            return getAnyWordStartingWith(prefix);
        }
        // Use binary search to find the indexes of first and last words with this prefix
        int start = findFirstIndexStartingWith(prefix);
        int end = findLastIndexStartingWith(prefix);
        if (start == -1 || end == -1) {
            // Nothing found.
            return null;
        }
        List<String> goodWords = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isGoodWord(words.get(i), prefix)) {
                goodWords.add(words.get(i));
            }
        }
        if (goodWords.size() > 0) {
            // Return a random good word.
            return goodWords.get(mRandom.nextInt(goodWords.size()));
        } else {
            // There are no "good" words, so just return one with the right prefix.
            return words.get(mRandom.nextInt(end - start) + start);
        }
    }
    @VisibleForTesting
    int findLastIndexStartingWith(String prefix) {
        int startIndex = 0;
        int endIndex = words.size();
        int mid;
        while (startIndex < endIndex) {
            // Base case: we are on the last possible index.
            if (startIndex + 1 == endIndex) {
                if (words.get(startIndex).startsWith(prefix)) {
                    return startIndex;
                } else {
                    return -1;
                }
            }
            mid = (startIndex + endIndex) / 2;
            if (words.get(mid).startsWith(prefix)) {
                if (mid + 1 >= endIndex || !words.get(mid + 1).startsWith(prefix)) {
                    // It is the highest index starting with this prefix, so we are done.
                    return mid;
                } else {
                    // We aren't done! Keep searching higher.
                    startIndex = mid;
                }
            } else {
                int comparison = words.get(mid).compareTo(prefix);
                if (comparison < 0) {
                    // The word at mid is too early in the dict. Continue to search the second half.
                    startIndex = mid;
                } else {
                    // The word at mid is too late in the dict. Recursively search the first half.
                    endIndex = mid;
                }
            }
        }
        return -1;
    }

    @VisibleForTesting
    int findFirstIndexStartingWith(String prefix) {
        int startIndex = 0;
        int endIndex = words.size();
        int mid;
        while (startIndex < endIndex) {
            // Base case: we are on the last possible index.
            if (startIndex + 1 == endIndex) {
                if (words.get(startIndex).startsWith(prefix)) {
                    return startIndex;
                } else {
                    return -1;
                }
            }
            mid = (startIndex + endIndex) / 2;
            if (words.get(mid).startsWith(prefix)) {
                if (mid - 1 < startIndex || !words.get(mid - 1).startsWith(prefix)) {
                    // It is the lowest index starting with this prefix, so we are done.
                    return mid;
                } else {
                    // We aren't done! Keep searching lower.
                    endIndex = mid;
                }
            } else {
                int comparison = words.get(mid).compareTo(prefix);
                if (comparison < 0) {
                    // The word at mid is too early in the dict. Continue to search the second half.
                    startIndex = mid;
                } else {
                    // The word at mid is too late in the dict. Recursively search the first half.
                    endIndex = mid;
                }
            }
        }
        return -1;
    }

    // A word is a good word if the number of letters left is even.
    private boolean isGoodWord(String word, String prefix) {
        return (word.length() - prefix.length()) % 2 == 0;
    }
}
