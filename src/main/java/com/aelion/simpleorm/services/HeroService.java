package com.aelion.simpleorm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.aelion.simpleorm.models.Hero;
import com.aelion.simpleorm.repositories.IRepository;
import com.aelion.simpleorm.repositories.HeroRepository;

public class HeroService {
	private IRepository<Hero> repository = new HeroRepository(com.aelion.simpleorm.models.Hero.class);
	
	public void findAll() {
		try {
			List<Hero> heroes = this.repository.findAll();
			if (heroes.size() > 0) {
				heroes
					.stream()
					.forEach(h -> System.out.println(h.getName()));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void find(int id) {
		Optional<Hero> oHero = this.repository.findById(id);
		
		if (oHero.isPresent()) {
			Hero hero = oHero.get();
			System.out.println(hero.getName());
		} else {
			System.out.println("No hero with id : " + id + " was found");
		}
	}
	
	public void save(Hero hero) {
		hero = this.repository.save(hero);
		
		System.out.println("Hero was saved with id : " + hero.getId());
	}
}
