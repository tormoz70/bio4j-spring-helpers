package ru.bio4j.spring.helpers.model.jstore.filter;

/**
 * Элемент фильтра
 */

public class Or extends Logical {

    public Or() {
        super();
    }

    public Or(Expression ... expressions) {
        super(expressions);
    }

}
