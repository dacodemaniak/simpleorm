package com.aelion.simpleorm.repositories;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
	public List<T> findAll();
	
	public Optional<T> findById(int id);
	
	public void delete(T t);
	
	public T save(T t);
}
