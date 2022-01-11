package nl.hu.cisq1.lingo.trainer.data.converters;

import nl.hu.cisq1.lingo.trainer.domain.LetterFeedback;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LetterFeedbackConverterTest {

    @Test
    void convertToDatabaseColumn() {
        // Given
        LetterFeedbackConverter letterFeedbackConverter = new LetterFeedbackConverter();
        String expectedResult = "CORRECT,ABSENT,ABSENT,ABSENT,ABSENT";
        // When
        String result = letterFeedbackConverter.convertToDatabaseColumn(
                List.of(
                        LetterFeedback.CORRECT,
                        LetterFeedback.ABSENT,
                        LetterFeedback.ABSENT,
                        LetterFeedback.ABSENT,
                        LetterFeedback.ABSENT
                )
        );
        // Then
        assertEquals(expectedResult, result);
    }

    @Test
    void convertToEntityAttribute() {
        // Given
        LetterFeedbackConverter letterFeedbackConverter = new LetterFeedbackConverter();
        String inputString = "CORRECT,ABSENT,ABSENT,ABSENT,ABSENT";
        List<LetterFeedback> expectedResult =
                List.of(
                        LetterFeedback.CORRECT,
                        LetterFeedback.ABSENT,
                        LetterFeedback.ABSENT,
                        LetterFeedback.ABSENT,
                        LetterFeedback.ABSENT
                );
        // When
        List<LetterFeedback> result = letterFeedbackConverter.convertToEntityAttribute(inputString);
        // Then
        assertEquals(expectedResult, result);
    }
}