package com.cs252.soggyapples;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	private String baseURL = "https://image.tmdb.org/t/p/w500/";
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String welcome(Map<String, Object> model) throws IOException {
        return "/home";
    }
	@RequestMapping(value = { "/api" }, method = RequestMethod.POST)
    public String api(@RequestParam("title") String title, HttpServletRequest request) throws IOException {
		
		List<Movie> movies = new ArrayList<Movie>();
		try {
			String res = sendGet(title);
			JSONObject obj = new JSONObject(res);

			JSONArray arr = obj.getJSONArray("results");
			for (int i = 0; i < arr.length(); i++)
			{
				movies.add(new Movie(arr.getJSONObject(i).getString("title"), arr.getJSONObject(i).getString("poster_path"), arr.getJSONObject(i).getString("overview"), arr.getJSONObject(i).getString("release_date")));
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		session.setAttribute("movies", movies);
		session.setAttribute("title", title);
        return "/movie";
    }
	
	@RequestMapping(value = { "/pick-{title}" }, method = RequestMethod.GET)
    public String pick(@PathVariable String title, HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		session.setAttribute("movie", title);
		return "/pick";
	}
	
	@RequestMapping(value = { "/pick-{title}" }, method = RequestMethod.POST)
    public String update(@PathVariable String title, HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		session.setAttribute("movie", title);
		return "/done";
	}
	
	private String sendGet(String title) throws Exception {
		
		
		String url = "https://api.themoviedb.org/3/search/movie?api_key=5b85ae54ca7b9e80f18626c3b0fd285b&query="+ URLEncoder.encode(title, "UTF-8")+"&sort_by=popularity";
		
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