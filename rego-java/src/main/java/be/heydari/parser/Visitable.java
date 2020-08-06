package be.heydari.parser;

public interface Visitable {

    String accept(Visitor visitor);

}
