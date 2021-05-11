import java.time.LocalDate;
import java.util.ArrayList;

public class Movie extends Lists {
	
        public String name;
        public ArrayList<LocalDate> dates;
        

        public Movie()
        {
        	dates = new ArrayList<LocalDate>();
            genres = new ArrayList<String>();
            actors = new ArrayList<String>();
            halls = new ArrayList<String>();
       
        
        
        }
    }

