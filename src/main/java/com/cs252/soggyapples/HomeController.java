package com.cs252.soggyapples;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

@Controller
public class HomeController {
    
    private DatabaseReference mDatabase;
    
    @Value("${home.message}")
    private String message;

	private String baseURL = "https://image.tmdb.org/t/p/w500/";
	private String searchURL = "https://api.themoviedb.org/3/search/movie?api_key=5b85ae54ca7b9e80f18626c3b0fd285b&query=";
	private String movieURL = "https://api.themoviedb.org/3/movie/";
	private String api_key = "?api_key=5b85ae54ca7b9e80f18626c3b0fd285b";
	private String recentURL = "https://api.themoviedb.org/3/movie/now_playing?api_key=5b85ae54ca7b9e80f18626c3b0fd285b";
    
  @RequestMapping(value = { "/" }, method = RequestMethod.GET)
  public String welcome(Map<String, Object> model, HttpServletRequest request) throws IOException {
        
        List<Movie> movies = new ArrayList<Movie>();
        try {
            String res = sendGet("", recentURL);
            JSONObject obj = new JSONObject(res);

            JSONArray arr = obj.getJSONArray("results");
            for (int i = 0; i < arr.length(); i++)
            {
                if(i == 10) break;
                System.out.println(arr.getJSONObject(i).getString("poster_path"));
                movies.add(new Movie(arr.getJSONObject(i).getString("title"), arr.getJSONObject(i).getString("poster_path"), arr.getJSONObject(i).getString("overview"), arr.getJSONObject(i).getString("release_date"), arr.getJSONObject(i).getInt("id")));
            }
        } catch (Exception e) {
            
            e.printStackTrace();
        }

        HttpSession session = request.getSession();
        session.setAttribute("movies", movies);
        //session.setAttribute("title", title);
        
        List<Comment> comments = getAllComments("335983");
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println("get size: " + comments.size());
        for(int i =0 ; i < comments.size(); i++) {
        	System.out.println(comments.get(i).getComment());
        }

        model.put("message", this.message);
        return "/home";
  }
  @RequestMapping(value = { "/api" }, method = RequestMethod.POST)
  public String api(@RequestParam("title") String title, HttpServletRequest request) throws IOException {
		
		List<Movie> movies = new ArrayList<Movie>();
		try {
			String res = sendGet(title, searchURL);
			JSONObject obj = new JSONObject(res);

			JSONArray arr = obj.getJSONArray("results");
			for (int i = 0; i < arr.length(); i++)
			{
				movies.add(new Movie(arr.getJSONObject(i).getString("title"), arr.getJSONObject(i).getString("poster_path"), arr.getJSONObject(i).getString("overview"), arr.getJSONObject(i).getString("release_date"), arr.getJSONObject(i).getInt("id")));
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		session.setAttribute("movies", movies);
		session.setAttribute("title", title);
        return "/movie";
  }
	
	@RequestMapping(value = { "/pick-{id}" }, method = RequestMethod.GET)
    public String pick(@PathVariable String id, HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		//session.setAttribute("movie", id);
		Movie movie = new Movie();
		try {
			String res = getMovie(id);
			JSONObject obj = new JSONObject(res);
			//JSONArray arr = obj.getJSONArray("bindings");
//			for (int i = 0; i < arr.length(); i++)
//			{
//				movie = (new Movie(arr.getJSONObject(i).getString("title"), arr.getJSONObject(i).getString("poster_path"), arr.getJSONObject(i).getString("overview"), arr.getJSONObject(i).getString("release_date"), arr.getJSONObject(i).getInt("id")));
//			}
			movie = (new Movie(obj.getString("title"), obj.getString("poster_path"), obj.getString("overview"), obj.getString("release_date"), obj.getInt("id")));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		try {
			mDatabase = FirebaseDatabase.getInstance().getReference();
			
			DatabaseReference usersRef = mDatabase.child("Movies");
			Map<String, String> movieData = new HashMap<String, String>();
			Map<String, String> commentData = new HashMap<String, String>();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long time = timestamp.getTime();
			String key = mDatabase.push().getKey();   
			movieData.put("id", key);
			movieData.put("num_ratings", "0");
			movieData.put("ratings", "0");
			movieData.put("title", movie.getTitle());
			commentData.put("comment", "default");
			commentData.put("timestamp", String.valueOf(time));
			
			usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
				  public void onDataChange(DataSnapshot snapshot) {
				    if (snapshot.hasChild(id)) {
				    	String comment_key = mDatabase.push().getKey();
				    	System.out.println("Movie exists!");
				    	//usersRef.child(id).child("comments").child(comment_key).setValueAsync(commentData);
				    }else {
				    	String comment_key = mDatabase.push().getKey();  
				    	usersRef.child(id).setValueAsync(movieData);
				    	//usersRef.child(id).child("comments").child(comment_key).setValueAsync(commentData);
				    }
				  }
	
				@Override
				public void onCancelled(DatabaseError error) {
					System.out.println(error);
					
				}
				});
			
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		movie.comments = getAllComments(id);
		movie.setRating("0");
		session.setAttribute("comments", movie.getComments());
		session.setAttribute("pick", movie);
		return "/pick";
	}
	
	@RequestMapping(value = { "/pick-{id}" }, method = RequestMethod.POST)
    public String update(@PathVariable String id, @RequestParam("rating") String rating, @RequestParam("comment") String comment, HttpServletRequest request) throws IOException {
		Movie movie = new Movie();
		try {
			String res = getMovie(id);
			JSONObject obj = new JSONObject(res);
			movie = (new Movie(obj.getString("title"), obj.getString("poster_path"), obj.getString("overview"), obj.getString("release_date"), obj.getInt("id")));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		try {
			mDatabase = FirebaseDatabase.getInstance().getReference();
			
			DatabaseReference usersRef = mDatabase.child("Movies");
			Map<String, String> movieData = new HashMap<String, String>();
			Map<String, String> commentData = new HashMap<String, String>();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long time = timestamp.getTime();
			String key = mDatabase.push().getKey();   
			movieData.put("id", key);
			movieData.put("num_ratings", "0");
			movieData.put("ratings", movie.getRating());
			movieData.put("title", movie.getTitle());
			commentData.put("comment", comment);
			commentData.put("timestamp", String.valueOf(time));
			
			usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
				  public void onDataChange(DataSnapshot snapshot) {
				    if (snapshot.hasChild(id)) {
				    	String comment_key = mDatabase.push().getKey();
				    	System.out.println("Movie exists!");
				    	usersRef.child(id).child("comments").child(comment_key).setValueAsync(commentData);
				    }else {
				    	String comment_key = mDatabase.push().getKey();  
				    	usersRef.child(id).setValueAsync(movieData);
				    	usersRef.child(id).child("comments").child(comment_key).setValueAsync(commentData);
				    }
				  }
	
				@Override
				public void onCancelled(DatabaseError error) {
					System.out.println(error);
					
				}
				});
			
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		
		System.out.println("Rating: " + rating);
		System.out.println("Comment: " + comment);
		return "/pick";
	}
	
private String getMovie(String id) throws Exception {
		
		String url = movieURL + URLEncoder.encode(id, "UTF-8") + api_key;
		
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
	    
    
    private String sendGet(String title, String movie_url) throws Exception {
        
        
        String url = movie_url + URLEncoder.encode(title, "UTF-8")+"&sort_by=popularity";
        
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
    
    private List<Comment> getAllComments(String movie_id) {
    	List<Comment> comments = new ArrayList<Comment>();
    	Query q = FirebaseDatabase.getInstance().getReference().child("Movies").child(movie_id).child("comments");
    	q.addValueEventListener(
    	        new ValueEventListener() {
    	            @Override
    	            public void onDataChange(DataSnapshot dataSnapshot) {
    	            	if (dataSnapshot.exists()) {
    	            		
    	            		for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
    	            			//System.out.println("Enters");
    	            			Comment comment = userSnapshot.getValue(Comment.class);
    	            			//System.out.println(comment.getTimestamp());
    	            			comments.add(comment);
    	            		}
    	            	}
    	            }

    	            @Override
    	            public void onCancelled(DatabaseError databaseError) {
    	                // read query is cancelled.
    	            }
    	});
    	Collections.reverse(comments); 
		return comments;
    	
    }
    
}







