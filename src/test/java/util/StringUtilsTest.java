package util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringUtilsTest {

    @Test
    void tokenize_shouldSplitSimpleName() {
        List<String> tokens = StringUtils.tokenize("John Doe");
        assertThat(tokens).containsExactly("John", "Doe");
    }

    @Test
    void tokenize_shouldSplitMultipleWords() {
        List<String> tokens = StringUtils.tokenize("John Michael Doe Jr");
        assertThat(tokens).containsExactly("John", "Michael", "Doe", "Jr");
    }

    @Test
    void tokenize_shouldHandleSingleWord() {
        List<String> tokens = StringUtils.tokenize("John");
        assertThat(tokens).containsExactly("John");
    }

    @Test
    void tokenize_shouldHandleEmptyString() {
        List<String> tokens = StringUtils.tokenize("");
        assertThat(tokens).containsExactly("");
    }

    @ParameterizedTest
    @ValueSource(strings = {"  John   Doe  ", "\tJohn\tDoe\t", "\nJohn\nDoe\n"})
    void tokenize_shouldTrimWhitespace(String input) {
        List<String> tokens = StringUtils.tokenize(input);
        assertThat(tokens).containsExactly("John", "Doe");
    }

    @Test
    void tokenize_shouldHandleMultipleSpaces() {
        List<String> tokens = StringUtils.tokenize("John    Doe");
        assertThat(tokens).containsExactly("John", "Doe");
    }

    @Test
    void tokenize_shouldThrowExceptionForNullInput() {
        assertThatThrownBy(() -> StringUtils.tokenize(null))
            .isInstanceOf(NullPointerException.class);
    }
}