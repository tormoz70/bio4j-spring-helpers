package ru.bio4j.spring.helpers.model;

import ru.bio4j.spring.helpers.model.MetaType;
import ru.bio4j.spring.helpers.model.Param;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface Mapper {
    String name() default "";
    Param.Direction direction() default Param.Direction.IN;
    MetaType metaType() default MetaType.UNDEFINED;
}
