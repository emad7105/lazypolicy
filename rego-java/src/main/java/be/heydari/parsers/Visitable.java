package be.heydari.parsers;

import com.querydsl.core.types.dsl.BooleanExpression;

public interface Visitable<TReturnType, TEntity> {

    <TReturnType,TEntityType> TReturnType accept(Visitor visitor, TEntityType entity);

}
