package com.cs252.soggyapples;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@Controller
public class HomeController {
	
	private DatabaseReference mDatabase;
	
	@Value("${home.message}")
    private String message;
	private String baseURL = "https://image.tmdb.org/t/p/w500";
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String welcome(Map<String, Object> model) throws IOException {
    	String name = "AYudsh";
		
		try {
			FileInputStream serviceAccount = new FileInputStream("/Users/SiD/Desktop/coding/soggyapples/src/main/webapp/WEB-INF/serviceAccountKey.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
				    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				    .setDatabaseUrl("https://soggyapples-110e5.firebaseio.com/")
				    .build();
			FirebaseApp.initializeApp(options);
			mDatabase = FirebaseDatabase.getInstance().getReference();
	    	mDatabase.child("users").child("Users").setValueAsync(name);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	
        model.put("message", this.message);
        return "/home";
    }
	@RequestMapping(value = { "/api" }, method = RequestMethod.POST)
    public String api(@RequestParam("j_username") 
	String username) throws IOException {
		String name = "AYudsh";
		
		try {
			FileInputStream serviceAccount = new FileInputStream("/Users/SiD/Desktop/coding/soggyapples/src/main/webapp/WEB-INF/serviceAccountKey.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
				    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
				    .setDatabaseUrl("https://soggyapples-110e5.firebaseio.com/")
				    .build();
			FirebaseApp.initializeApp(options);
			
			mDatabase = FirebaseDatabase.getInstance().getReference();
	    	mDatabase.child("users").child("Users").setValueAsync(name);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		try {
			String res = sendGet(username);
			JSONObject obj = new JSONObject(res);
			

			JSONArray arr = obj.getJSONArray("results");
			for (int i = 0; i < arr.length(); i++)
			{
			    String post_id = arr.getJSONObject(i).getString("title");
			    System.out.println(post_id);
			   
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
        //model.put("message", this.message);
        return "/home";
    }
	private String sendGet(String title) throws Exception {
		
		String url = "https://api.themoviedb.org/3/search/movie?api_key=5b85ae54ca7b9e80f18626c3b0fd285b&query="+title;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
		//System.out.println(response.toString());

	}
	
    

}