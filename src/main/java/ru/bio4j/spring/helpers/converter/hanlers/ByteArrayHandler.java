package ru.bio4j.spring.helpers.converter.hanlers;

import ru.bio4j.spring.helpers.stringHelper;
import ru.bio4j.spring.helpers.converter.Converter;
import ru.bio4j.spring.helpers.converter.Types;
import ru.bio4j.spring.helpers.errors.ConvertValueException;

import java.util.ArrayList;
import java.util.List;

public class ByteArrayHandler extends TypeHandlerBase implements TypeHandler<Byte[]> {

    @Override
    public boolean isHandler(Class<?> type) {
        return type.isArray() && (type.getComponentType() == Byte.class);
    }

    @Override
    public Byte[] read(Object value, Class<?> targetType) throws ConvertValueException {
        if (value == null)
            return null;
        Class<?> valType = (value == null) ? null : value.getClass();

        if (valType.isArray()) {
            if(valType.getComponentType() == String.class) {
                List<Byte> r = new ArrayList<>();
                for(String e : (String[])value)
                    r.add(Byte.parseByte(e));
                return (Byte[])r.toArray();
            }
            if(valType.getComponentType() == Byte.class)
                return (Byte[])value;
            if(valType.getComponentType() == byte.class)
                return Types.toObjects((byte[]) value);
            List<String> r = new ArrayList<>();
            for (Object e : ((Object[])value)) {
                r.add(Converter.toType(e, String.class));
            }
            return (Byte[])r.toArray();
        } else if (Types.typeIsDate(valType))
            return Types.toObjects(value.toString().getBytes());
        else if (Types.typeIsNumber(valType))
            return new Byte[] {((Number)value).byteValue()};
        else if (valType == String.class) {
            List<Byte> r = new ArrayList<>();
            String[] elems = stringHelper.split((String)value, new String[] {",", ";", " "});
            for(String e : elems)
                r.add(Byte.parseByte(e));
            return (Byte[])r.toArray();
        } else if (valType == Character.class)
            return new Byte[] {(Byte)value};
        throw new ConvertValueException(value, valType, genericType);
    }

    @Override
    public <T> T write(Byte[] value, Class<T> targetType) throws ConvertValueException {
        Class<?> targetTypeWrapped = Types.wrapPrimitiveType(targetType);
        if (targetType.isArray()) {
            Class<?> targetArrayType = targetType.getComponentType();
            if (targetArrayType == Byte.class)
                return (T) value;
            else if (targetArrayType == byte.class)
                return (T) Types.toPrimitives(value);
        } else {
            if (targetTypeWrapped == String.class)
                return (T) stringHelper.combineArray(value, ",");
        }
        throw new ConvertValueException(value, genericType, targetTypeWrapped);
    }

    public <T> T write(Byte[] value, Class<T> targetType, String format) throws ConvertValueException {
        return write(value, targetType);
    }

}
