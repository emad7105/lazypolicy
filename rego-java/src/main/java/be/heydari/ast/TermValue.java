package be.heydari.ast;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Emad Heydari Beni
 */
public interface TermValue<T> extends ASTNode {

    T getValue();
}
