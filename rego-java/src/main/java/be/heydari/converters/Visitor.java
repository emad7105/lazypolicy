package be.heydari.converters;

import be.heydari.expressions.BoolPredicate;
import be.heydari.expressions.Conjunction;
import be.heydari.expressions.Disjunction;
import be.heydari.expressions.RefExpression;

/**
 * @author Emad Heydari Beni
 */
public interface Visitor<TEntity, TDisjunction, TConjunction, TPredicate, TReference> {

    TDisjunction visit(Disjunction disjunction, TEntity entity);

    TConjunction visit(Conjunction conjunction, TEntity entity);

    <TValueType> TPredicate visit(BoolPredicate<TValueType> predicate, TEntity entity);

    TReference visit(RefExpression refExpression, TEntity entity);
}
