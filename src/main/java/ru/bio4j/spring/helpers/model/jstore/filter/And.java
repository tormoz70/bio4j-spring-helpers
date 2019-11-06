package ru.bio4j.spring.helpers.model.jstore.filter;

/**
 * Элемент фильтра
 */

public class And extends Logical {

    public And() {
        super();
    }

    public And(Expression ... expressions) {
        super(expressions);
    }

}
