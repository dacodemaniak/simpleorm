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
    	
    	/**
    	controller.byId(4);
    	controller.byId(2);
    	**/
    	
    	Hero hero = new Hero();
    	hero.setName("Dardevil");
    	hero.setAge(30);
    	hero.setStrength(300);
    	
    	controller.save(hero);
    	
    }
}
