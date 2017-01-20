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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.text.TextUtils;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import android.util.Log;


/**
 * Tests for AnagramDictionary
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class AnagramDictionaryTest {

    @Before
    public void beforeEach() {
        PowerMockito.mockStatic(Log.class);
    }
    @Test
    public void testAddition(){
        assertEquals(3, 1+2);
    }
    @Test
    public void sortedLetters(){
        assertEquals("a", AnagramDictionary.sortLetters("a"));
        assertEquals("opst",AnagramDictionary.sortLetters("stop"));
        assertEquals("abcdefg",AnagramDictionary.sortLetters("abcedfg"));

    }
    @Test
    public void isAnagram() {
        assertEquals(true, AnagramDictionary.isAnagram("cat","act"));
        assertEquals(true, AnagramDictionary.isAnagram("act","cat"));
        assertEquals(false, AnagramDictionary.isAnagram("racecar","rec"));

    }
}
