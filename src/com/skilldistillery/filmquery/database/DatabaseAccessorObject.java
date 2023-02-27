package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		if (filmId < 1) {
			return null;
		}

		String sql = "SELECT * FROM film WHERE id = ?";
		Film film = null;

		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();

			if (filmResult.next()) {

				film = new Film(filmResult.getInt("id"), filmResult.getString("title"),
						filmResult.getString("description"), filmResult.getInt("release_year"),
						filmResult.getInt("language_id"), filmResult.getInt("length"),
						filmResult.getDouble("replacement_cost"), filmResult.getString("rating"),
						filmResult.getString("special_features"), findActorsByFilmId(filmResult.getInt("id")),
						findLanguageById(filmId));

			}

			conn.close();
			filmResult.close();
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	public Actor findActorById(int actorId) {
		Actor actor = null;

		try {

			String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();

			if (actorResult.next()) {
				actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
						actorResult.getString("last_name"));
			}
			actorResult.close();
			stmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		if (filmId < 1) {
			return null;
		}
		List<Actor> actorList = new ArrayList<Actor>();
		Actor actor = null;
		String sql = "SELECT actor.first_name, actor.last_name, actor.id " + "FROM actor "
				+ "JOIN film_actor ON actor.id = film_actor.actor_id " + "JOIN film ON film_actor.film_id = film.id "
				+ "WHERE film.id = ?; ";

		try {

			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet actorResult = stmt.executeQuery();

			while (actorResult.next()) {
				actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
						actorResult.getString("last_name"));

				actorList.add(actor);

			}
			actorResult.close();
			stmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return actorList;
	}

	public void keywordSearch(String keyword) {
		Film film = null;
		List<Film> keywordFilms = new ArrayList<Film>();

		try {
			String sql = "SELECT * FROM film WHERE film.title LIKE ? OR film.description LIKE ?";
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet filmResult = stmt.executeQuery();

			while (filmResult.next()) {

				film = new Film(filmResult.getInt("id"), filmResult.getString("title"),
						filmResult.getString("description"), filmResult.getInt("release_year"),
						filmResult.getInt("language_id"), filmResult.getInt("length"),
						filmResult.getDouble("replacement_cost"), filmResult.getString("rating"),
						filmResult.getString("special_features"), findActorsByFilmId(filmResult.getInt("id")),
						findLanguageById(filmResult.getInt("id")));

				keywordFilms.add(film);

			}
				if (keywordFilms.isEmpty()) {
					System.out.println("No Matches Found");
				} else {
					for (int i = 0; i < keywordFilms.size(); i++) {
					System.out.println(keywordFilms.get(i));
				}
			}

			stmt.close();
			conn.close();
			filmResult.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String findLanguageById(int filmId) {
		if (filmId < 1) {
			return null;
		}

		String language = "";
		String sql = "SELECT language.name FROM language JOIN film ON language.id = film.language_id WHERE film.id = ?;";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet langResult = stmt.executeQuery();

			if(langResult.next()) {
				language = langResult.getNString("language.name");
			}
			conn.close();
			stmt.close();
			langResult.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return language;
	}

}
