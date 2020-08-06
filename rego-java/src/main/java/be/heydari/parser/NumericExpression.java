package be.heydari.parser;

import lombok.Data;

@Data
public class NumericExpression implements Expression<Double> {
    private Double value;
}
