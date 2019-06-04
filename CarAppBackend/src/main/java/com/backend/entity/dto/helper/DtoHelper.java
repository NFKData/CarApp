package com.backend.entity.dto.helper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DtoHelper {

	public static <T,V> V entityToDto(T entity, Class<V> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		V dto = clazz.getConstructor(entity.getClass()).newInstance(entity);
		return dto;
	}
	
	public static <T,V> List<V> entityListToDtoList(List<T> entities, Class<V> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<V> dtos = new ArrayList<>();
		for(T entity : entities) {
			dtos.add(entityToDto(entity, clazz));
		}
		return dtos;
	}
	
}
