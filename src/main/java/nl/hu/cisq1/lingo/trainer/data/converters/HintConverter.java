package nl.hu.cisq1.lingo.trainer.data.converters;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HintConverter implements AttributeConverter<List<Character>, String> {
    @Override
    public String convertToDatabaseColumn(List<Character> hint) {
        return hint.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<Character> convertToEntityAttribute(String s) {
        return Arrays.stream(s.split(","))
                .map(s1 -> s1.charAt(0))
                .collect(Collectors.toList());
    }
}
