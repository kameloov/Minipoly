package com.minipoly.android.utils;

import com.minipoly.android.DataListener;
import com.minipoly.android.repository.MiscRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SearchUtils {

    public static String removeExtraSpaces(String input) {
        return input == null ? null : input.trim().replaceAll("\\s+", " ");
    }

    public static List<String> extractWords(String input) {
        ArrayList<String> words = new ArrayList<>();
        if (input != null)
            return Arrays.asList(removeExtraSpaces(input).split(" "));
        return words;
    }

    public static void getTagIds(List<String> words, DataListener<List<String>> listener) {
        List<String> ids = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(0);
        for (String s : words) {
            count.getAndIncrement();
            MiscRepository.getTagId(s, (success, data) -> {
                count.getAndDecrement();
                if (data != null)
                    ids.add(data);
                if (count.get() == 0)
                    listener.onComplete(true, ids);
            });
        }
    }

    public static void getTagIds(String text, DataListener<List<String>> listener) {
        if (text == null)
            listener.onComplete(false, null);
        List<String> words = extractWords(text);
        getTagIds(words, listener);
    }
}
