package ru.bio4j.spring.helpers.model.jstore.filter;

/**
 * Элемент фильтра
 */

public class Compare extends Expression {

    private final String column;
    private final Object value;
    private final boolean ignoreCase;

    public Compare(String column, Object value, boolean ignoreCase) {
        this.column = column;
        this.value = value;
        this.ignoreCase = ignoreCase;
    }
    public Compare(String column, Object value) {
        this(column, value, false);
    }

    public Compare() {
        this(null, null, false);
    }

    @Override
    public String getColumn() {
        return this.column;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public boolean ignoreCase() {
        return this.ignoreCase;
    }
}
