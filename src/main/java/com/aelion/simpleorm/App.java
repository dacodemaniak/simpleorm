package com.aelion.simpleorm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.aelion.simpleorm.controllers.HeroController;
import com.aelion.simpleorm.dbal.mysql.MySQLConnect;
import com.aelion.simpleorm.models.Hero;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	App app = new App();
    	app.run();
    }
    
    private void run() {
    	HeroController controller = new HeroController();
    	controller.findAll();
    	controller.byId(4);
    	controller.byId(2);
    	
    	Hero hero = new Hero();
    	hero.setName("IronMan");
    	hero.setAge(45);
    	hero.setStrength(450);
    	
    	controller.save(hero);
    }
}
