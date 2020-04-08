import java.io.*;
import java.util.*;

public class TecmintApp {
 public static void main(String[] args){
  System.out.println("start");
  BufferedReader input = new BufferedReader(new FileReader(args[0]));
  
  List<String> lines = new LinkedList<>();
  while(true){
	String line = input.readLine();
	if(line == null) break;
	lines.add(line);
  }
  input.close();
  System.out.println("good bye");
  System.out.println("finish");
 }
}
