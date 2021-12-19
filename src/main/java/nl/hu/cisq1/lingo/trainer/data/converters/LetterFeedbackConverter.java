package nl.hu.cisq1.lingo.trainer.data.converters;

import nl.hu.cisq1.lingo.trainer.domain.LetterFeedback;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LetterFeedbackConverter implements AttributeConverter<List<LetterFeedback>, String> {
    @Override
    public String convertToDatabaseColumn(List<LetterFeedback> letterFeedbackList) {
        return letterFeedbackList.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<LetterFeedback> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(","))
                .map(LetterFeedback::valueOf)
                .collect(Collectors.toList());
    }
}
