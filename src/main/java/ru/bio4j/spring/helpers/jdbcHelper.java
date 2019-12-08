package ru.bio4j.spring.helpers;

import ru.bio4j.spring.helpers.converter.Converter;
import ru.bio4j.spring.helpers.errors.BioError;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.bio4j.spring.helpers.model.ABean;
import ru.bio4j.spring.helpers.model.Mapper;
import ru.bio4j.spring.helpers.model.Param;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jdbcHelper {

    public static <T> List<T> query(
            final NamedParameterJdbcTemplate jdbcTemplate,
            final String sql,
            final Object prms,
            final Class<T> clazz) {
        if (jdbcTemplate == null)
            throw new IllegalArgumentException("Argument \"jdbcTemplate\" cannot be null!");
        if (sql == null)
            throw new IllegalArgumentException("Argument \"sql\" cannot be null!");

        final Map<String, Object> paramMap = mapHelper.decodeParams(prms);
        paramMap.put("BIO_CURUSR_UID", "qwe");

        List<T> rslt = jdbcTemplate.query(
                sql,
                paramMap,
                (rs, rowNum) -> {
                    T result;
                    try {
                        result = clazz.newInstance();
                    } catch (Exception e) {
                        throw BioError.wrap(e);
                    }
                    for (java.lang.reflect.Field fld : reflectHelper.getAllObjectFields(result.getClass())) {
                        String attrName = fld.getName();
                        Mapper p = reflectHelper.findAnnotation(Mapper.class, fld);
                        if (p != null)
                            attrName = p.name();
                        //String fldName = metaData != null ? findFieldName(metaData, attrName) : attrName;
                        String fldName = attrName;
                        if (stringHelper.isNullOrEmpty(fldName))
                            fldName = attrName;
                        Object valObj = null;
                        try {
                            valObj = rs.getObject(fldName);
                        } catch (SQLException e) {
                            BioError.wrap(e);
                        }
                        if (valObj != null) {
                            try {
                                Object val = (fld.getType() == Object.class) ? valObj : Converter.toType(valObj, fld.getType());
                                fld.setAccessible(true);
                                fld.set(result, val);
                            } catch (Exception e) {
                                throw new BioError(String.format("Can't set value %s to field %s(%s). Msg: %s", valObj, fld.getName(), fld.getType(), e.getMessage()));
                            }
                        }
                    }
                    return result;
                }
        );

        return rslt;
    }

}
