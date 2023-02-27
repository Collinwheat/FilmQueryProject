package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  
  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
//    app.test();
    app.launch();
  }

  private void launch() {
	  Scanner input = new Scanner(System.in);
	  
	  startUserInterface(input);
	  
	  input.close();
  }
  
  private void test() {
    Actor actor = null;
    List<Actor> actorList;
    
	try {
		db.findFilmById(45);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  
  
  try {
		actor = db.findActorById(13);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  System.out.println(actor);
  
  actorList = db.findActorsByFilmId(3);
  	
  	for(Actor actors: actorList) {
  		System.out.println(actors.toString());
  	}
}
  


  private void startUserInterface(Scanner input) {
    
    for(;;) {
    	menu();
    	String selection = input.next();
    	switch(selection) {
    	case "1":
    		System.out.println("What is the ID of the film you would like to know about?");
    		try {
    			int id = input.nextInt();
    			Film film = db.findFilmById(id);
    			if (film != null) {
    				System.out.println(film);
    			}else {
    				System.out.println("ID cannot be found");
    			}
    		} catch (Exception e) {
    			System.err.println("Invalid Input");
    			input.next();
    			startUserInterface(input);
    		}
    	    continue;
    	    
    	case "2":
    		System.out.println("Please enter Keyword");
    		String keyword = input.next();
    		db.keywordSearch(keyword);
    		
    		continue;
    	
    	case "3":
    		System.out.println("GoodBye!");
    	}break;
    }
  }
  
  public void menu() {
	  System.out.println("Input the number of the action you'd like to perform");
	  System.out.println("+--------------------------------------------+");
	  System.out.println("|           1. Look Up Film By ID            |");
	  System.out.println("|       2.  Look Up Film By Keyword          |");
	  System.out.println("|         3.  Exit the Application           |");
	  System.out.println("+--------------------------------------------+");
  }

}
