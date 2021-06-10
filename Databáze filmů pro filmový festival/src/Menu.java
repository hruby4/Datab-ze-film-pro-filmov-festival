import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Set;

class Menu extends Lists {
	public Hashtable<String, Movie> movies;
	public ArrayList<Play> plays;
	Scanner sc = new Scanner(System.in);
	Serilizator ss;
    public Menu()
    {
        genres = new ArrayList<String>();
        actors = new ArrayList<String>();
        halls = new ArrayList<String>();
        movies = new Hashtable<String, Movie>();
        plays = new ArrayList<Play>();
        
        ss = new Serilizator(this);
    }

    public void MenuMainMethod()
    {
    	ss.LoadData();
    	
        while (true)
        {
            System.out.println("Choose from options: ADDGENRE, ADDACTOR, ADDMOVIE, ADDHALL, GETMOVIES, MOVIESTODAY, FILMINHALL, ADDPLAY, SAVEDATA");
            String option = sc.nextLine();
            System.out.println();
            
            Option(option);
        }
    }

    private void Option(String optionName)
    {
        optionName = optionName.toUpperCase();

        try
        {
            switch (optionName)
            {
                case "ADDGENRE":
                    genres = AddOption("genre", genres);
                    break;
                case "ADDACTOR":
                    actors = AddOption("actor", actors);
                    break;
                case "ADDHALL":
                    halls = AddOption("hall", halls);
                    break;
                case "ADDMOVIE":
                    AddMovie();
                    break;
                case "GETMOVIES":
                    PrintAllMovies();
                    break;
                case "MOVIESTODAY":
                	GetTodaysMovies();
                    break;
                case "FILMINHALL":
                    GetHallMovies();
                    break;
                case "ADDPLAY":
                	AddPlay();
                	break;
                case "SAVEDATA":
                	SaveData();
                	break;
                default:
                	throw new Exception("No option with this name!");
            }
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Try options from the list!");
            System.out.println();
        }

    }
    
    private void SaveData() 
    {
    	
    	PrintWriter writer;
		try {
			writer = new PrintWriter(ss.file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	for(String genre : genres) 
    	{
    		ss.SaveData("G", genre, null, null);
    	}
    	for(String hall : halls) 
    	{
    		ss.SaveData("H", hall, null, null);
    	}
    	for(String actor : actors) 
    	{
    		ss.SaveData("A", actor, null, null);
    	}
        Set<String> names = movies.keySet();
        for(String name : names){
        	ss.SaveData("M", null, movies.get(name), null);
        }
    	for(Play play : plays) 
    	{
    		ss.SaveData("P", null, null, play);
    	}
    }
    
    private void GetHallMovies()
    {
        System.out.println("Choose your hall from list: ");
        for(String hall : halls){
            System.out.print(hall);
        }
        String response = sc.nextLine().toUpperCase();
        for(Play play : plays){
            if(play.hall.equals(response)){
                System.out.println("Movie: " + play.movie.name + " will be played in hall: " + play.hall);
                System.out.print("Actors: ");
                for(String actor : play.movie.actors){
                    System.out.print(actor + ", ");
                }
            }
        }

        
    }

    private void AddMovie()
    {
        Movie movie = new Movie();
        System.out.println("Enter movie name: ");
        movie.name = sc.nextLine().toUpperCase();

        while(true) {
            if(genres.size() == 0)
             break;
        	ChooseToAdd(movie.genres,"Genre",genres);
        	System.out.println("If you want to add more write anything or write DONE if u are done.");
        	String response = sc.nextLine().toUpperCase();
        	if(response.equals("DONE")) {
        		break;
        	}
        	
        }
        while(true) {
            if(actors.size() == 0)
             break;
        	ChooseToAdd(movie.actors,"Actor",actors);
        	System.out.println("If you want to add more write anything or write DONE if u are done.");
        	String response = sc.nextLine().toUpperCase();
        	if(response.equals("DONE")) {
        		break;
        	}
        }
        movies.put(movie.name, movie);
        System.out.println("Movie added");
        System.out.println();
       
        
    }
    private void AddPlay() {
        
        if(movies.size() == 0 || halls.size() == 0){
            System.out.println("Impossible");
            return;
        }
        
    	Play play = new Play();

	System.out.println("Write the name of the film, that will be played.");
	Set<String> names = movies.keySet();
	System.out.println("Choose from this movies: ");
	for(String name : names){
	    System.out.print(name + ", ");
	}
	AddMovieToPlay(play);

    	System.out.println("Write date when the film will be played.");
        play.date = CreateDate();

	System.out.println("Write hall in which it will be played");
        ArrayList<String> list = new ArrayList<String>();
        ChooseToAdd(list, "Hall", halls);
        play.hall = list.get(0);

	plays.add(play);
	System.out.println("Play has been added");
        	
    }

    private void AddMovieToPlay(Play play) {
	
	
	
	String response = sc.nextLine().toUpperCase();
	

	try{

		if(movies.containsKey(response))
		{
			play.movie = movies.get(response);
		}
		else
		{
			throw new Exception("Nonexistant movie");
		}

	}
	catch(Exception e) {

		System.out.println(e.getMessage());
        System.out.println("Try existing one!");
        System.out.println();
		AddMovieToPlay(play);
	}

    }	

    private LocalDate CreateDate() {
    	int year = 0;
    	int day = 0;
    	int month = 0;
    	
    	while(true) {
    		try {
    			
    			if(day==0) {
    				System.out.println("Now type day. ");
    				int response = Integer.parseInt(sc.nextLine());
    				if(response>31 || response <=0 ) {
    					throw new Exception("Impossible day!");
    				}
    				day = response;
    				
    			}
    			else if(month == 0) {
    				System.out.println("Now type month. ");
    				int response = Integer.parseInt(sc.nextLine());
    				if(response>12 || response <=0 ) {
    					throw new Exception("Impossible month!");
    					
    			}
    				month = response;
    		}
    			else if(year == 0) {
    				System.out.println("Now type year. ");
    				int response = Integer.parseInt(sc.nextLine());
    				if(LocalDate.now().getYear() > response) {
    					throw new Exception("Impossible year!");
    					
    			}
    				year = response;
    		}
    			else {
    				return LocalDate.of(year,month,day);
    			}
    			
    	}
    			catch(Exception e) {
    			System.out.println(e.getMessage());
    			System.out.println("Try again!");
    			
    		}
    	}
    	
    }
    private void GetTodaysMovies() {
        for(Play play : plays){
            if(play.date.equals(LocalDate.now())){
                System.out.println("Today will be film " + play.movie.name + " played in hall " + play.hall);
            }
        }
        System.out.println();
    }

    private void ChooseToAdd(ArrayList<String> list, String name, ArrayList<String> list2)
    {
        System.out.println();
        
        		
            try
            {
                System.out.println("Choose " + name + " from this list: ");
                for (String item : list2)
                {
                    System.out.println(item + ", ");
                }
                String response = sc.nextLine().toUpperCase();
                
                 if (!list.contains(response) && list2.contains(response))
                {
                	 
                    list.add(response);
                }
                else
                {
                    throw new Exception(name + " doesnt exist!");
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println("Try existing option");
                System.out.println();
                ChooseToAdd(list,name,list2);
            }

        
    }

    private ArrayList<String> AddOption(String name, ArrayList<String> list)
    {
        System.out.println("Add " + name + " to list");
        System.out.println("If you are done, write DONE");

        while (true)
        {
            try
            {
                String response = sc.nextLine().toUpperCase();
                if (response.equals("DONE"))
                {
                    System.out.println();
                    return list;
                }
                else if (list.contains(response))
                {
                    System.out.println();
                    throw new Exception("List already contains this " + name);
                }
                else
                {
                    list.add(response);
                    System.out.println(name + " " + response + " added to the list");
                    System.out.println();
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println("Try another one");
                System.out.println();
            }

        }
    }

    private void PrintAllMovies()
    {
        Set<String> names = movies.keySet();
        for(String name : names){
                System.out.println("Movie: " + name);
                System.out.print("Actors: ");
                for(String actor : movies.get(name).actors){
                    System.out.print(actor + ", ");
                } 
        }
        System.out.println();
    }
    
    

}