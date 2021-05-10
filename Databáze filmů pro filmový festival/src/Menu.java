import java.util.ArrayList;
import java.util.Scanner;

public class Menu extends Lists {
	private ArrayList<Movie> movies;
	Scanner sc = new Scanner(System.in);
    public Menu()
    {
        genres = new ArrayList<String>();
        actors = new ArrayList<String>();
        halls = new ArrayList<String>();
        movies = new ArrayList<Movie>();
    }

    public void MenuMainMethod()
    {
        while (true)
        {
            System.out.println("Choose from options: ADDGENRE, ADDACTOR, ADDMOVIE, ADDHALL, GETMOVIES, MOVIESTODAY, FILMINHALL");
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
                    break;
                case "FILMINHALL":
                    GetHallMovies();
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
    private void GetHallMovies()
    {
        System.out.println("Choose hall in which you are interested or write DONE if you want to exit: ");
        for (String hall : halls)
        {
            System.out.println(hall + ", ");
        }
        System.out.println();

        while(true)
        {
            try
            {
                String response = sc.nextLine().toUpperCase();
                if (response.equals("DONE"))
                {
                    return;
                }
                else if (halls.contains(response))
                {
                    System.out.println("Movies that will be played in hall " + response + " are:");
                    for(Movie movie : movies)
                    {
                        if (movie.halls.contains(response))
                        {
                            System.out.println(movie.name + ", ");
                        }
                    }
                    System.out.println();
                }
                else
                {
                    throw new Exception("Hall doesnt exist!");
                }
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println("Try existing one!");
                System.out.println();
            }

        }
    }

    private void AddMovie()
    {
        Movie movie = new Movie();
        System.out.println("Enter movie name: ");
        movie.name = sc.nextLine();

        movie.genres = ChooseToAdd(genres, "genre");
        movie.actors = ChooseToAdd(actors, "actor");
        movie.halls = ChooseToAdd(halls, "hall");

        movies.add(movie);
        System.out.println("Movie added");
        System.out.println();
    }

    private ArrayList<String> ChooseToAdd(ArrayList<String> list, String name)
    {
        System.out.println();
        ArrayList<String> temp = new ArrayList<String>(list);
        ArrayList<String> _list = new ArrayList<String>();
        while (true)
        {
            try
            {
                System.out.println("Choose " + name + " from this list: ");
                for (String item : temp)
                {
                    System.out.println(item + ", ");
                }
                System.out.println("If you are done with " + name + " selection, write DONE");
                String response = sc.nextLine().toUpperCase();
                if (response.equals("DONE"))
                {
                    return _list;
                }
                else if (temp.contains(response))
                {
                    temp.remove(response);
                    _list.add(response);
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
            }

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
        for (Movie movie : movies)
        {
            System.out.println("Movie: " + movie.name);
            System.out.println("Actors: ");

            for (String actor : movie.actors)
            {
                System.out.println(actor + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
