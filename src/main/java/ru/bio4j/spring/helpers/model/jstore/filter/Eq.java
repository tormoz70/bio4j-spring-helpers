package ru.bio4j.spring.helpers.model.jstore.filter;

/**
 * Элемент фильтра
 */

public class Eq extends Compare {

    public Eq(String column, Object value, boolean ignoreCase) {
        super(column, value, ignoreCase);
    }
    public Eq(String column, Object value) {
        this(column, value, false);
    }

    public Eq() {
        this(null, null, false);
    }

}
