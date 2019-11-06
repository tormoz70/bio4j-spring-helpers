package ru.bio4j.spring.helpers.model.jstore.filter;

/**
 * Элемент фильтра
 */

public class Le extends Compare {

    public Le(String fieldName, Object value) {
        super(fieldName, value);
    }
    public Le() {
        this(null, null);
    }
}
