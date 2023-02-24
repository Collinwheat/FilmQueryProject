package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String user = "student";
	private static final String pass = "student";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {

		Film film = null;

		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * FROM film WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();

			if (filmResult.next()) {
				film = new Film(filmResult.getInt("id"), filmResult.getString("title"),
						filmResult.getString("description"), filmResult.getInt("release_Year"),
						filmResult.getInt("language_Id"), filmResult.getInt("length"),
						filmResult.getDouble("replacement_Cost"), filmResult.getString("rating"),
						filmResult.getString("special_Features"));
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return film;
	}

	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
//
//		// ...
//		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
//		Connection conn = DriverManager.getConnection(URL, user, pass);
//		PreparedStatement stmt = conn.prepareStatement(sql);
//		stmt.setInt(1, actorId);
//		ResultSet actorResult = stmt.executeQuery();
//		
//		
//		if (actorResult.next()) {
//			actor = new Actor(actorResult.getInt("id"),
//					actorResult.getString("first_name"),
//					actorResult.getString("last_name")); // Create the object
//			// Here is our mapping of query columns to our object fields:
//
//		}
//		// ...
		return actor;
	}

}
