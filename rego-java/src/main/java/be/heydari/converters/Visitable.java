package be.heydari.converters;

public interface Visitable<TReturnType, TEntity> {

    <TReturnType,TEntityType> TReturnType accept(Visitor visitor, TEntityType entity);

}
