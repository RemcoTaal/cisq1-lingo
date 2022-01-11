package nl.hu.cisq1.lingo.trainer.data.converters;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HintConverterTest {

    @Test
    void convertToDatabaseColumn() {
        // Given
        HintConverter hintConverter = new HintConverter();
        String expectedResult = "w,.,.,r,d";
        // When
        String result = hintConverter.convertToDatabaseColumn(List.of('w', '.', '.', 'r', 'd'));
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void convertToEntityAttribute() {
        // Given
        HintConverter hintConverter = new HintConverter();
        String inputString = "w,.,.,r,d";
        List<Character> expectedResult = List.of('w', '.', '.', 'r', 'd');
        // When
        List<Character> result = hintConverter.convertToEntityAttribute(inputString);
        // Then
        assertEquals(expectedResult, result);
    }
}