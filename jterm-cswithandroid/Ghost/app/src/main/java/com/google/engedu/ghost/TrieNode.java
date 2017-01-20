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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord = false;
    private Random mRandom;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        if (s.length()==0){
            isWord=true;
            return;
        }
        String firstChar = s.substring(0,1);
        String remaining = s.substring(1); //substring with one param automatically goes to the end
        if (children.containsKey(firstChar)){
            //if char is already in my children map
            //then ask child at that char to add the remaining string suffix
            children.get(firstChar).add(remaining);
        } else{
            //it doesn't exist in the children, si I'll need to insert it into the char map
            TrieNode newNode = new TrieNode();
            children.put(firstChar,newNode);
            newNode.add(remaining);
        }
    }

    public boolean isWord(String s) {
        if (s.length()==0){
            return isWord;
        }
        String firstChar = s.substring(0,1);
        String remaining = s.substring(1);
        if (children.containsKey(firstChar)){
            return children.get(firstChar).isWord(remaining);
        }
        return false;
    }

    public String getAnyWordStartingWith(String s) {
        if (s == null){
            // we have no prefix at all and s is null
            if (children.size() > 0){
                // pick any character and return that character's trienode's word
                String nextChar = pickRandomChildChar();
                String nextRemaining = children.get(nextChar).getAnyWordStartingWith(null);
                return nextChar + nextRemaining;
            }else{
                return "";
            }
        }
        if (s.length()==0){
            //if there is no prefix, return a random word
            // if we have no children, we can return the empty string if we are a word or null
            if (children.size() == 0){
                if (isWord){
                    //we are a word!
                    return "";
                }else{
                    return null;
                }
            } else if (children.size() > 0) {
                String childKey = pickRandomChildChar();
                String nextRemaining = children.get(childKey).getAnyWordStartingWith("");
                return childKey + nextRemaining;
            }

        } else {
            //s is not empty, then return a word that starts with the first letter of s
            String firstChar = s.substring(0, 1);
            String remaining = s.substring(1);
            if (children.containsKey(firstChar)) {
                String word = children.get(firstChar).getAnyWordStartingWith(remaining);
                if (word != null) {
                    return firstChar + remaining;
                } else {
                    return null;
                }
            } else{
                //we are looking for a prefix that is not in our dictionary!
                return null;
            }
        }
        return null;
    }

    private String pickRandomChildChar() {
        // find a rondom key in our hashmap
        int index = mRandom.nextInt(children.size());
        int reached = 0;
        //assume the children's order doesn't change after creation in their set
        for (String s : children.keySet()){
            if (index == reached){
                return s;
            }
            reached++;
        }
        return null;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}