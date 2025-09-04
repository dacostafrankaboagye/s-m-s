package util;

import lombok.NonNull;

import java.util.Arrays;
import java.util.List;

public final class StringUtils {

    private StringUtils(){}

    /**
     * Splits a full name (or any string) into tokens based on whitespace.
     * Example: "John Doe" -> ["John", "Doe"]
     *
     * @param text the string to tokenize.
     * @return a list of tokens, never null.
     */
    public static List<String> tokenize(@NonNull String text){
        return Arrays.asList(text.trim().split("\\s+"));
    }
}
