package com.aelion.simpleorm.repositories;

import com.aelion.simpleorm.annotations.Table;
import com.aelion.simpleorm.models.Hero;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Repository<T> {
	protected String tableName;
	protected ArrayList<String> properties = new ArrayList<String>();
	
	private Class<T> model;
	
	protected Repository(Class<T> model) {
		this.tableName = this.getTableName(model);
		this.propertyList(model);
		
		this.model = model;
	}
	

	
	protected String select() {
		String alias = this.tableName.substring(0, 1);
		
		StringBuilder select = new StringBuilder();
		
		select.append("SELECT ");
		
		int propertiesCount = this.properties.size() - 1;
		int count = 0;
		
		for (String property : this.properties) {
			if (count < propertiesCount) {
				select.append("`" + alias + "`." + "`" + property + "`,");
			} else {
				select.append("`" + alias + "`." + "`" + property + "`");
			}
			count++;
		}
		
		select.append(" FROM ");
		select.append(this.tableName + " " + alias + ";");
		
		String query = select.toString();
		
		return query;
	}
	
	protected String insert() {
		StringBuilder sb = new StringBuilder();
		
		sb
			.append("INSERT INTO ")
			.append(this.tableName + " (");
		
		
		List<String> filteredList = this.properties
			.stream()
			.filter(p -> p.equals("id") == false)
			.collect(Collectors.toList());
		
		int propertiesCount = filteredList.size() - 1;
		int count = 0;
		
		for (String property : filteredList) {
			if (count < propertiesCount) {
				sb.append(property + ",");
			} else {
				sb.append(property);
			}
			count++;
		}
		
		sb.append(") VALUES (");
		
		// Add placeholders
		count = 0;
		for (String property : filteredList) {
			if (count < propertiesCount) {
				sb.append("?,");
			} else {
				sb.append("?");
			}
			count++;
		}
		
		sb.append(");");
		
		String query = sb.toString();
		
		return query;
	}
	
	private String getTableName(Class<T> model) {
		
		// Récupérer une annotation Table dans la classe Hero
		Table tableAnnotation = model.getAnnotation(Table.class);
		
		if (tableAnnotation != null) {
			return tableAnnotation.name();
		} else {
			String className = model.getSimpleName().toLowerCase();
			return className;
		}
	}
	
	private void propertyList(Class<T> model) {
		Field[] properties = model.getDeclaredFields();
		
		for (Field property : properties) {
			this.properties.add(property.getName());
		}
	}
}
