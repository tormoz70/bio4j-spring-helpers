package ru.bio4j.spring.helpers.model.jstore.filter;

/**
 * Элемент фильтра
 */

public class IsNull extends Expression {

    private final String column;

    public IsNull(String column) {
        this.column = column;
    }

    public IsNull() {
        this(null);
    }

    @Override
    public String getColumn() {
        return this.column;
    }

}
