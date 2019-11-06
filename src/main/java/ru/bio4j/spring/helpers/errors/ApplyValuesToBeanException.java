package ru.bio4j.spring.helpers.errors;

public class ApplyValuesToBeanException extends BioError {
    public ApplyValuesToBeanException() {
        super();
        field = null;
    }
    private final String field;

    public ApplyValuesToBeanException(String field, String message, Exception parentException) {
        super(message, parentException);
        this.field = field;
    }

    public ApplyValuesToBeanException(String field, String message) {
        this(field, message, null);
    }

    public String getField() {
        return field;
    }
}
