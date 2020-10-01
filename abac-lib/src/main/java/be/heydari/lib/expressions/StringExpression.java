package be.heydari.lib.expressions;

import lombok.Data;

@Data
public class StringExpression implements Expression<String> {
    private String value;
}
