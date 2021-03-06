import java.io.*;
import java.util.*;
import java.net.*;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpResponse.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import java.net.http.*;
import java.nio.charset.StandardCharsets;

public class Aapp {


    // one instance, reuse
    private final HttpClient httpClient = HttpClient.newBuilder()
           .version(HttpClient.Version.HTTP_1_1)
              //.authenticator(Authenticator.getDefault())
              .build();


 public static void main(String[] args) throws Exception {
  System.out.println("start");
  String file = args.length == 0 ? "input.txt" : args[0];
  BufferedReader input = new BufferedReader(new FileReader(file));
  
  List<String> lines = new LinkedList<>();
  while(true){
	String line = input.readLine();
	if(line == null) break;
	lines.add(line);
  }
  input.close();
  //System.out.println("list: " + lines);
  
  new Aapp().sendPost(args, lines);
  
  System.out.println("finish");
 }

private void sendPost(String[] args, List<String> lines) throws Exception {

        // form parameters
        Map<Object, Object> data = new HashMap<>();
        data.put("username", "abc");
        data.put("password", "123");
        data.put("custom", "secret");
        data.put("ts", System.currentTimeMillis());

        System.out.println(args[1]);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data, lines))
                .uri(URI.create(args[1]))
                .setHeader("Authorization", "Basic "+args[2])
               // setHeader("Content-Type", "application/json")
                 .build();

        System.out.println(URI.create(args[1]).toString());

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data , List<String> lines) {
        var builder = new StringBuilder();
        int i = 0;
        for (String line : lines) {
          i++;
          if (i < 100 && line != null && line.length() > 10) {
           // builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
           builder.append("{ \"index\":{} }\n{\"title\": \"");
           String str = new StringBuilder(line).reverse().toString().replaceAll("\"","");
           System.out.println(str);
           builder.append(str);
           builder.append("\"}\n");
          }
        } 
       
       return HttpRequest.BodyPublishers.ofString(builder.toString());
       // System.out.println(builder.toString());
        //return HttpRequest.BodyPublishers.ofString("{ \"index\":{} }\n{\"title\": \"geodata\"}\n{ \"index\":{} }\n{\"title\": \"geodata\"}\n");
    }

}
