package be.heydari.converters.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;

public interface Visitable {

    <T> BooleanExpression accept(Visitor visitor, Class<T> entityType);

}
