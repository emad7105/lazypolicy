package be.heydari.parsers.querydsl;

import be.heydari.expressions.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

/**
 * @author Emad Heydari Beni
 */
public interface Visitor {

    <E> BooleanExpression visit(Conjunction conjunction, Class<E> entityType);
    <E> BooleanExpression visit(Disjunction disjunction, Class<E> entityType);
    <T,E> BooleanExpression visit(BoolPredicate<T> predicate, Class<E> entityType);
    <E> PathBuilder visit(RefExpression<Long> refExpression, Class<E> entityType);
}
