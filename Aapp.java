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
           // .version(HttpClient.Version.HTTP_2)
              .authenticator(Authenticator.getDefault())
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
  System.out.println("list: " + lines);
  
  new Aapp().sendPost(args);
  
  System.out.println("finish");
 }

private void sendPost(String[] args) throws Exception {

        // form parameters
        Map<Object, Object> data = new HashMap<>();
        data.put("username", "abc");
        data.put("password", "123");
        data.put("custom", "secret");
        data.put("ts", System.currentTimeMillis());

        System.out.println(args[1]);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create(args[1]))
                .setHeader("Content-Type", "application/json")
                .build();

        System.out.println(request);

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
       
        System.out.println(builder.toString());
        return HttpRequest.BodyPublishers.ofString("{\"title\": \"geodata\"}");
    }

}
