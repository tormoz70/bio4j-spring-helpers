package ru.bio4j.spring.helpers.converter;

import ru.bio4j.spring.helpers.converter.hanlers.*;

public class TypeHandlerMapper {
    private static final TypeHandler[] handlerMap = {
            new ArrayHandler(),
            //new StringArrayHandler(),
            new ByteArrayHandler(),
            new BytePArrayHandler(),
            new DateHandler(),
            new StringHandler(),
            new CharacterHandler(),
            new NumberHandler(),
            new BooleanHandler(),
            new ResultSetHandler(),
            new MetaTypeHandler(),
            new DirectionHandler()
        };

    public static TypeHandler getHandler(Class<?> type) {
        for(TypeHandler h : handlerMap) {
            if(h.isHandler(type))
                return h;
        }
        return null;
    }
}
