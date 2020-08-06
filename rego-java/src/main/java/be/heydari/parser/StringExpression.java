package be.heydari.parser;

import lombok.Data;

@Data
public class StringExpression implements Expression<String> {
    private String value;
}
