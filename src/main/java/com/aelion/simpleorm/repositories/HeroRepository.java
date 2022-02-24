package com.aelion.simpleorm.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.aelion.simpleorm.annotations.Table;
import com.aelion.simpleorm.dbal.DbConnect;
import com.aelion.simpleorm.dbal.mysql.MySQLConnect;
import com.aelion.simpleorm.models.Hero;

public class HeroRepository extends Repository<Hero> implements IRepository<Hero> {
	private DbConnect mysql;
	private Connection cnx;
	
	
	private PreparedStatement preparedFindById;
	private PreparedStatement preparedInsert;
	private PreparedStatement preparedUpdate;
	
	public HeroRepository(Class<Hero> className) {
		super(className);
		
		this.mysql = MySQLConnect.getInstance();
		
		try {
			this.cnx = this.mysql.connect();
			
			// Création d'une requête préparée
			String query = "SELECT id, name, age, strength FROM hero WHERE id = ?;";
			this.preparedFindById = this.cnx.prepareStatement(query);
			
			this.preparedInsert = this.cnx.prepareStatement(this.insert());
			
			query = "UPDATE hero SET name = ?, age = ?, strength = ? WHERE id = ?;";
			this.preparedUpdate = this.cnx.prepareStatement(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Hero> findAll() {
		try {
			// Envoyer une requête de type SELECT * FROM hero;
			Statement statement = this.cnx.createStatement();
			ResultSet results = statement.executeQuery(this.select());
			
			// Hydratation des modèles
			ArrayList<Hero> heroes = new ArrayList<Hero>();
			
			// Parcourir le jeu de résultats
			while(results.next()) {
				Hero hero = new Hero();
				hero.setName(results.getString(2));
				hero.setId(results.getInt(1));
				hero.setAge(results.getInt(3));
				hero.setStrength(results.getInt(4));
				// Ajouter l'objet hero dans la liste des héros
				heroes.add(hero);
			}
			statement.close();
			results.close();
			//this.mysql.disconnect();
			
			// Retourner la liste des héros finale
			return heroes;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<Hero>();
		}
	}

	public Optional<Hero> findById(int id) {
		try {
			this.preparedFindById.setInt(1, id);
			ResultSet result = this.preparedFindById.executeQuery();
			if (result.next()) {
				Hero hero = new Hero();
				hero.setId(result.getInt(1));
				hero.setName(result.getString(2));
				hero.setAge(result.getInt(3));
				hero.setStrength(result.getInt(4));
				
				return Optional.of(hero);
			} else {
				return Optional.empty();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public void delete(Hero t) {
		// TODO Auto-generated method stub
		
	}

	public Hero save(Hero t) {
		// INSERT INTO hero (name, age, force) VALUES ('....', 35,45);
		if (t.getId() == 0) {
			try {
				this.preparedInsert.setString(1, t.getName());
				this.preparedInsert.setInt(2, t.getAge());
				this.preparedInsert.setInt(3, t.getStrength());
				
				this.preparedInsert.execute();
				
				String query = "SELECT id FROM hero ORDER BY id DESC LIMIT 1";
				Statement statement = this.cnx.createStatement();
				ResultSet rs = statement.executeQuery(query);
				rs.next();
				t.setId(rs.getInt(1));
				return t;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return t;
			}
		} else {
			try {
				this.preparedUpdate.setString(1, t.getName());
				this.preparedUpdate.setInt(2, t.getAge());
				this.preparedUpdate.setInt(3, t.getStrength());
				this.preparedUpdate.setInt(4, t.getId());
				
				this.preparedUpdate.execute();
				
				return t;
			} catch (SQLException e) {
				e.printStackTrace();
				return t;
			}
		}
	}

}
