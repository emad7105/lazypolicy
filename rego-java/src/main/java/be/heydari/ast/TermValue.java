package be.heydari.ast;


/**
 * @author Emad Heydari Beni
 */
public interface TermValue<T> extends ASTNode {

    T getValue();
}
