package be.heydari.ast;

public class NumberValue<E> {
    private E value;

    public NumberValue(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
