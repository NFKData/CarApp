package com.backend.serialization;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)	
@PrepareForTest(LocalDateTime.class)
public class LocalDateTimeAdapterTest {
	
	@InjectMocks
	private LocalDateTimeAdapter adapter;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}
	
	@Test
	public void whenUnmarshallingAString_shouldReturnLocalDateTimeOfTheString() throws Exception {
		String date = "1980-01-01T00:00:00.000";
		LocalDateTime result = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
		PowerMockito.mockStatic(LocalDateTime.class);
		PowerMockito.doReturn(result).when(LocalDateTime.class, "parse", date, DateTimeFormatter.ISO_DATE_TIME);
		assertEquals(result, adapter.unmarshal(date));
	}

	@Test
	public void whenMarshallingALocalDateTime_shouldReturnAStringOfTheDate() throws Exception {
		String result = "1980-01-01T00:00:00";
		LocalDateTime date = LocalDateTime.parse(result, DateTimeFormatter.ISO_DATE_TIME);
		DateTimeFormatter formatter = PowerMockito.mock(DateTimeFormatter.class);
		when(formatter.format(date)).thenReturn(result);
		assertEquals(result, adapter.marshal(date));
	}

}

