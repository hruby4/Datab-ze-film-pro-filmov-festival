import java.io.File;
import java.time.LocalDate;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Serilizator {
	Menu menu;
	public File file;
	ArrayList<String[]> texts = new ArrayList<String[]>();
	
	public Serilizator(Menu menu) {
		this.menu = menu;
	}
	
	public void LoadData() {
		CreateFile();
		ReadFile();
		LoadValues();
	}
	
	private void CreateFile() {
		try {
		      file = new File("filmdata.txt");
		      if (file.createNewFile()) {
		        System.out.println("File created: " + file.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

	public void SaveData(String type, String value, Movie movie, Play play) {
				
		try {
		switch(type) 
		{
		case "M" :
			WriteMovieData(type,movie);
			break;
		case "P" :
			WritePlayData(type,play);
			break;
		case "A" : 
			WriteBasicData(type,value);
			break;
		case "G" :
			WriteBasicData(type,value);
			break;
		case "H" :
			WriteBasicData(type,value);
			break;
		default:
			throw new Exception("Wrong type!");
			
		
		}
		}
		catch(Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void WriteBasicData(String type, String value) {
			
		try {
		      FileWriter myWriter = new FileWriter(file.getName(),true);
		      myWriter.write(type + "," + value + "\n");
		      myWriter.close();
		      System.out.println("Successfully saved " + value);
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	private void WriteMovieData(String type, Movie movie) {
		
		try {
			FileWriter myWriter = new FileWriter(file.getName(),true);
		      myWriter.write(type + "," + movie.name + ",");
		      for(String genre : movie.genres) 
		      {
		    	  myWriter.write(genre + ",");
		      }
		      for(String actor : movie.actors) 
		      {
		    	  myWriter.write(actor + ",");
		      }
		      myWriter.write("\n");
		      myWriter.close();
		      System.out.println("Successfully saved " + movie.name);
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	private void WritePlayData(String type, Play play) {
		
		try {
		      FileWriter myWriter = new FileWriter(file.getName(),true);
		      myWriter.write(type + "," + play.movie.name + "," + play.date.getYear() + "," + play.date.getMonthValue() + "," + play.date.getDayOfMonth() + "," + play.hall);
		      myWriter.write("\n");
		      myWriter.close();
		      System.out.println("Successfully saved play");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	private void ReadFile() {
		try {
		      
		      Scanner myReader = new Scanner(file);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        texts.add(data.split(","));
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	private void LoadValues() {
		for(String[] line : texts) {
			switch(line[0]) {
			case "M" :
				LoadMovieData(line);
				break;
			case "P" :
				LoadPlayData(line);
				break;
			case "A" : 
				LoadBasicData(line,menu.actors);
				break;
			case "G" :
				LoadBasicData(line,menu.genres);
				break;
			case "H" :
				LoadBasicData(line,menu.halls);
				break;
			}
		}
		
	}
	private void LoadBasicData(String[] line, ArrayList<String> list) {
		list.add(line[1]);
	}	
	private void LoadPlayData(String[] line) {
		Play play = new Play();
		play.movie = menu.movies.get(line[1]);
		play.hall = line[5];
		play.date = LocalDate.of(Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4]));
		
		menu.plays.add(play);
	}
	private void LoadMovieData(String[] line) {
		Movie movie = new Movie();
		movie.name = line[1];
		
		for(int i = 2;i<line.length;i++) {
			if(menu.genres.contains(line[i]))	
			{
				movie.genres.add(line[i]);
			}	
			else 
			{
				movie.actors.add(line[i]);
			}
		}
		menu.movies.put(movie.name, movie);
	}
}
