package ru.bio4j.spring.helpers.model.jstore.filter;

/**
 * Элемент фильтра
 */

public class Bgn extends Compare {

    public Bgn(String column, Object value, boolean ignoreCase) {
        super(column, value, ignoreCase);
    }
    public Bgn(String column, Object value) {
        this(column, value, false);
    }

    public Bgn() {
        this(null, null, false);
    }
}
