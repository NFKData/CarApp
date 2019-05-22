package backend.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} to parse correctly {@link LocalDateTime} fields from and
 * to ISO date time string format. This class will be used with
 * {@link XmlJavaTypeAdapter} annotation to parse {@link LocalDateTime}
 * properties of a class.
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

	/**
	 * Convert ISO date time string to a valid {@link LocalDateTime} format
	 * 
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public LocalDateTime unmarshal(String localDateString) throws Exception {
		return LocalDateTime.parse(localDateString, DateTimeFormatter.ISO_DATE_TIME);
	}

	/**
	 * Convert {@link LocalDateTime} to an ISO date time string format
	 * 
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(LocalDateTime localDateString) throws Exception {
		return DateTimeFormatter.ISO_DATE_TIME.format(localDateString);
	}
}
