package com.aelion.simpleorm.models;

import com.aelion.simpleorm.annotations.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="hero")
public class Hero {
	private int id;
	private String name;
	private int age;
	private int strength;
}
