import javax.annotation.processing.SupportedSourceVersion;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    private final static int attempts = 10;
    public static void main(String[] args) throws IOException {
        String movie_name = getRandomMovie();

        char[] display = movie_name.replaceAll("\\S","_").toCharArray();
        StringBuilder wrong_letters_blob = new StringBuilder();

        while (wrong_letters_blob.length() < attempts*2){ // '*2' is to include space for every character
            if(!Any(display,'_')){
              System.out.println("You win!");
              System.out.println("You have guessed '"+movie_name+"' correctly.");
              return;
            }
            System.out.print("You are guessing:");
            System.out.println(BuildDisplayString(display));

            System.out.println("You have guessed ("+wrong_letters_blob.length()/2+") wrong letters:"+wrong_letters_blob);

            System.out.print("Guess a letter:");
            char input = GetLetter();

            boolean guessed_letter = false;
            for (int i = 0; i < movie_name.length(); i++) {
                if(movie_name.charAt(i) == input){
                    guessed_letter = true;
                    display[i] = input;
                }
            }
            if(!guessed_letter) wrong_letters_blob.append(" ").append(input);
        }
        System.out.println("game over!");
    }
    private static String getRandomMovie(){
        Random randomizer = new Random();
        List<String> movie_names = getMovieNames();
        return movie_names.get(randomizer.nextInt(0,movie_names.size()-1));
        // shut up 'get'
    }
    private static String BuildDisplayString(char[] display){
        StringBuilder result = new StringBuilder();
        for (char letter : display) {
            result.append(letter);
        }
        return result.toString();
    }
    private static boolean Any(char[] src,char item){
        for (char letter:src)
        {
            if (letter == item){
                return true;
            }
        }
        return false;
    }
    private static char GetLetter(){
        String input =  new Scanner(System.in).next();
        if(input.length() != 1) {
            System.out.println("Too many characters.");
            return GetLetter();
        }
        return input.toCharArray()[0];
    }
    private static List<String> getMovieNames() {
        List<String> movie_names;
        try {
            movie_names = Files.readAllLines(Path.of("movies.txt"));
            if (movie_names.size() == 0){
                System.out.println("movies.txt doesn't contain any valid movie names.");
                return null;
            }
        }
        catch (IOException x){
            System.out.println("Couldn't read movies.txt. Did you create that file?");
            return null;
        }
        return movie_names;
    }
}