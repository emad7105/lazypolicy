package be.heydari.parser;

import lombok.Data;

@Data
public class BooleanExpression implements Expression<Boolean> {
    private Boolean value;
}


