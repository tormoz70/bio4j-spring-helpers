package ru.bio4j.spring.helpers.converter.hanlers;


import ru.bio4j.spring.helpers.model.MetaType;
import ru.bio4j.spring.helpers.converter.MetaTypeConverter;
import ru.bio4j.spring.helpers.converter.Types;
import ru.bio4j.spring.helpers.errors.ConvertValueException;

public class MetaTypeHandler extends TypeHandlerBase implements TypeHandler<MetaType> {

    @Override
    public MetaType read(Object value, Class<?> targetType) throws ConvertValueException {
        if (value == null)
            value = 0;
        Class<?> valType = (value == null) ? null : value.getClass();

        if (valType == MetaType.class)
            return (MetaType)value;
        else if (Types.typeIsNumber(valType))
            return MetaType.values()[Types.number2Number((Number)value, int.class)];
        else if (valType == String.class)
            return MetaType.decode((String)value);
        else if (valType == Class.class)
            return MetaTypeConverter.read((Class)value);
        throw new ConvertValueException(value, valType, genericType);
    }



    @Override
    public <T> T write(MetaType value, Class<T> targetType) throws ConvertValueException {
        Class<?> targetTypeWrapped = Types.wrapPrimitiveType(targetType);
        if (targetTypeWrapped == MetaType.class)
            return (T) value;
        else if (targetTypeWrapped == Integer.class)
            return (T) value;
        else if (targetTypeWrapped == String.class)
            return (T) value.toString();
        else if (targetTypeWrapped == Class.class)
            return (T) MetaTypeConverter.write(value);
        throw new ConvertValueException(value, genericType, targetTypeWrapped);
    }

    public <T> T write(MetaType value, Class<T> targetType, String format) throws ConvertValueException {
        return write(value, targetType);
    }

}
