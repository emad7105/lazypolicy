package be.heydari.expressions;

import lombok.Data;

@Data
public class NumericExpression implements Expression<Long> {
    private Long value;
}
