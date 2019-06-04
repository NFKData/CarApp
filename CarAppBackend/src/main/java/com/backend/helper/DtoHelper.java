package com.backend.helper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper for converting entities on DTO objects
 * @author gmiralle
 *
 */
public class DtoHelper {

	/**
	 * Convert a single entity on a DTO
	 * @param entity Entity to be converted
	 * @param clazz Class of the DTO of the result
	 * @return DTO object of the entity
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	public static <T,V> V entityToDto(T entity, Class<V> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		V dto = clazz.getConstructor(entity.getClass()).newInstance(entity);
		return dto;
	}
	
	/**
	 * Convert a list of entities on a list of DTOs
	 * @param entities Entities to be converted
	 * @param clazz Class of the DTOs of the result
	 * @return List of DTOs objects of the entity
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	public static <T,V> List<V> entityListToDtoList(List<T> entities, Class<V> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<V> dtos = new ArrayList<>();
		for(T entity : entities) {
			dtos.add(entityToDto(entity, clazz));
		}
		return dtos;
	}
	
}
