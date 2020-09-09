package be.heydari.ast;

public class NumberValue<E> {
    private E value;
    private NumberType type;

    public NumberValue(E value, NumberType type) {
        this.value = value;
        this.type = type;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public NumberType getType() {
        return type;
    }

    public void setType(NumberType type) {
        this.type = type;
    }
}
