package ru.bio4j.spring.helpers.converter;


import ru.bio4j.spring.helpers.regexHelper;
import ru.bio4j.spring.helpers.stringHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author ayrat
 * 
 * Класс для преобразований из строки в дату.
 * 
 */
public class DateTimeParser {

	/**
	 * Экземпляр класса.
	 */
	private static DateTimeParser instance;

	public static DateTimeParser getInstance() {
		if (instance == null)
			synchronized (DateTimeParser.class) {
				if (instance == null)
					createDateTimeParser();
			}
		return instance;
	}

	private static final List<DateTimeParserTemplate> templates;
    static {
        templates = new ArrayList<>();
        templates.add(new DateTimeParserTemplate("yyyyMMddHHmmss", "^[012]\\d{3}[01]\\d{1}[0123]\\d{1}[012]\\d{1}[012345]\\d{1}[012345]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("dd.MM.yyyy HH:mm:ss", "^[0123]\\d{1}\\.[01]\\d{1}\\.[012]\\d{3}\\s[012]\\d{1}[:][012345]\\d{1}[:][012345]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("yyyy.MM.dd HH:mm:ss", "^[012]\\d{3}\\.[01]\\d{1}\\.[0123]\\d{1}\\s[012]\\d{1}[:][012345]\\d{1}[:][012345]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("yyyy.MM.dd", "^[012]\\d{3}\\.[01]\\d{1}\\.[0123]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("dd.MM.yyyy", "^[0123]\\d{1}\\.[01]\\d{1}\\.[012]\\d{3}$"));
        templates.add(new DateTimeParserTemplate("yyyyMMdd", "^[012]\\d{3}[01]\\d{1}[0123]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("yyyyMM", "^[012]\\d{3}[01]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("ddMMyyyy", "^[0123]\\d{1}[01]\\d{1}[012]\\d{3}$"));
        templates.add(new DateTimeParserTemplate("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "^[012]\\d{3}[-][01]\\d{1}[-][0123]\\d{1}[T][012]\\d{1}[:][012345]\\d{1}[:][012345]\\d{1}[.]\\d{3}[+-]\\d{4}$"));
        templates.add(new DateTimeParserTemplate("yyyy-MM-dd'T'HH:mm:ss.SSS", "^[012]\\d{3}[-][01]\\d{1}[-][0123]\\d{1}[T][012]\\d{1}[:][012345]\\d{1}[:][012345]\\d{1}[.]\\d{3}$"));
        templates.add(new DateTimeParserTemplate("yyyy-MM-dd'T'HH:mm:ss", "^[012]\\d{3}[-][01]\\d{1}[-][0123]\\d{1}[T][012]\\d{1}[:][012345]\\d{1}[:][012345]\\d{1}$"));
		templates.add(new DateTimeParserTemplate("yyyy.MM.dd'T'HH:mm:ss", "^[012]\\d{3}\\.[01]\\d{1}\\.[0123]\\d{1}[T][012]\\d{1}[:][012345]\\d{1}[:][012345]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("yyyy-MM-dd'T'HH:mm", "^[012]\\d{3}[-][01]\\d{1}[-][0123]\\d{1}[T][012]\\d{1}[:][012345]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("yyyy-MM-dd", "^[012]\\d{3}[-][01]\\d{1}[-][0123]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("yyyy-MM-dd HH:mm:ss", "^[012]\\d{3}[-][01]\\d{1}[-][0123]\\d{1}\\s[012]\\d{1}[:][012345]\\d{1}[:][012345]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("dd.MM.yyyy H:mm:ss", "^[0123]\\d{1}\\.[01]\\d{1}\\.[012]\\d{3}\\s[012]?\\d{1}[:][012345]\\d{1}[:][012345]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("yyyy.MM.dd HH:mm", "^[012]\\d{3}\\.[01]\\d{1}\\.[0123]\\d{1}\\s[012]\\d{1}[:][012345]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("yyyyMMdd HH:mm:ss", "^[012]\\d{3}[01]\\d{1}[0123]\\d{1}\\s[012]\\d{1}[:][012345]\\d{1}[:][012345]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("yyyyMMdd HH:mm", "^[012]\\d{3}[01]\\d{1}[0123]\\d{1}\\s[012]\\d{1}[:][012345]\\d{1}$"));
        templates.add(new DateTimeParserTemplate("dd.MM.yyyy H:mm", "^[0123]\\d{1}\\.[01]\\d{1}\\.[012]\\d{3}\\s[012]?\\d{1}[:][012345]\\d{1}$"));
    }

	private static void createDateTimeParser() {
		instance = new DateTimeParser();
	}

	private DateTimeParser() {
	}

	public String detectFormat(String datetimeValue) {

		for (DateTimeParserTemplate f : this.templates) {
			if (regexHelper.match(datetimeValue, f.getRegex(), Pattern.CASE_INSENSITIVE).matches())
				return f.getFormat();
		}
		return null;
	}

	public Date pars(String value, String format) {
		if (!stringHelper.isNullOrEmpty(value)) {
			if (value.toUpperCase().equals("NOW"))
				return new Date();
			if (value.toUpperCase().equals("MAX"))
				return Types.maxValue();
			if (value.toUpperCase().equals("MIN"))
				return Types.minValue();
			try {
				return Types.parse(value, format);
			} catch (Exception ex) {
				throw new DateParseException("Ошибка разбора даты. Параметры: (" + value + ", " + format + "). Сообщение: " + ex.toString());
			}
		}
		return Types.minValue();
	}

	public Date pars(String value) {
		String datetimeFormat = detectFormat(value);
		if (stringHelper.isNullOrEmpty(datetimeFormat))
			throw new DateParseException("Не верная дата: [" + value + "]. Невозможно определить формат даты.");
		return pars(value, datetimeFormat);
	}

}
