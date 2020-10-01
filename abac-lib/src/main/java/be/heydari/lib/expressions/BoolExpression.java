package be.heydari.lib.expressions;

import lombok.Data;

@Data
public class BoolExpression implements Expression<Boolean> {
    private Boolean value;
}


