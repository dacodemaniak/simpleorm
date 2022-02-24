package com.aelion.simpleorm.controllers;

import com.aelion.simpleorm.models.Hero;
import com.aelion.simpleorm.services.HeroService;

public class HeroController {
	private HeroService service = new HeroService();
	
	public void findAll() {
		this.service.findAll();
	}
	
	public void byId(int id) {
		this.service.find(id);
	}
	
	public void save(Hero hero) {
		this.service.save(hero);
	}
}
