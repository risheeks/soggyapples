package com.cs252.soggyapples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.common.collect.Lists;

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
            String res = sendGet("", recentURL, "&sort_by=popularity");
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
        //setMovieRating("335983", "5");
        
        return "/home";
  }
  
  @RequestMapping(value = { "/api" }, method = RequestMethod.POST)
  public String api(@RequestParam("title") String title, HttpServletRequest request) throws IOException {
		
		List<Movie> movies = new ArrayList<Movie>();
		try {
			String res = sendGet(title, searchURL, "&sort_by=popularity");
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
			//String res = getMovie(id);
			String res = sendGet(id, movieURL, api_key);
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
			movieData.put("ratings", "0");
			movieData.put("title", movie.getTitle());
			commentData.put("comment", "default");
			commentData.put("timestamp", String.valueOf(time));
			
			usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
				  public void onDataChange(DataSnapshot snapshot) {
				    if (snapshot.hasChild(id)) {
				    	//String comment_key = mDatabase.push().getKey();
				    	System.out.println("Movie exists!");
				    	//usersRef.child(id).child("comments").child(comment_key).setValueAsync(commentData);
				    }else {
				    	//String comment_key = mDatabase.push().getKey();  
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
		
		movie.setRating(getMovieRating(id));
		movie.comments = getAllComments(id);
		movie.setRating(getMovieRating(id));
		session.setAttribute("comments", movie.getComments());
		session.setAttribute("pick", movie);
		return "/pick";
	}
	
	@RequestMapping(value = { "/pick-{id}" }, method = RequestMethod.POST)
    public String update(@PathVariable String id, @RequestParam("rating") String rating, @RequestParam("comment") String comment, HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		Movie movie = new Movie();
		try {
			String res = sendGet(id, movieURL, api_key);
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
			Calendar calendar = Calendar.getInstance();
    		calendar.setTimeInMillis(time);
    		int mYear = calendar.get(Calendar.YEAR);
    		int mMonth = calendar.get(Calendar.MONTH);
    		int mDay = calendar.get(Calendar.DAY_OF_MONTH);
    		int mHour = calendar.get(Calendar.HOUR);
    		int mMinute = calendar.get(Calendar.MINUTE);
			commentData.put("timestamp", "Time:"+mHour+ ":" +mMinute + "   Date " + mDay + "-" + mMonth + "-" + mYear);
			
			usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
				  public void onDataChange(DataSnapshot snapshot) {
				    if (snapshot.hasChild(id)) {
				    	String comment_key = mDatabase.push().getKey();
				    	System.out.println("Movie exists!");
				    	usersRef.child(id).child("comments").child(comment_key).setValueAsync(commentData);
				    }else {
				    	System.out.println("Invalid movie id: update()");
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
		
		setMovieRating(id, rating);
		movie.comments = getAllComments(id);
		movie.setRating(getMovieRating(id));
		session.setAttribute("comments", movie.getComments());
		session.setAttribute("pick", movie);
		return "/pick";
	}

	@RequestMapping(value = { "/about" }, method = RequestMethod.GET)
	  public String about(Map<String, Object> model, HttpServletRequest request) throws IOException {
	        
	        return "/about";
	  }
	
    private String sendGet(String id, String movie_url, String suffix) throws Exception {
        
        
        String url = movie_url + URLEncoder.encode(id, "UTF-8")+suffix;
        
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
    	            	System.out.println("getAllComments error");
    	            }
    	});
//    	try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
    	//Collections.reverse(comments); 
    	List<Comment> comments_rev = new ArrayList<Comment>();
    	comments_rev = Lists.reverse(comments);
		return comments_rev;
    	
    }
    
    
    private String getMovieRating(String movie_id) {
    	Movie movie = new Movie();
    	Query q = FirebaseDatabase.getInstance().getReference().child("Movies").child(movie_id).child("ratings");
    	q.addValueEventListener(
    	        new ValueEventListener() {
    	            @Override
    	            public void onDataChange(DataSnapshot dataSnapshot) {
    	            	if (dataSnapshot.exists()) {
    	            		movie.setRating(dataSnapshot.getValue((String.class)));
    	            		//System.out.println("get rating fb: " + movie.getRating());
    	            		return;
    	            	}
    	            }

    	            @Override
    	            public void onCancelled(DatabaseError databaseError) {
    	                System.out.println("getMovieRating error");
    	            }
    	});
    	try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	//System.out.println("get rating fb: " + movie.getRating());
    	return movie.getRating();
    }
    
    private void setMovieRating(String movie_id, String value) {
    	Movie movie = new Movie();
    	String num="";
    	Query q = FirebaseDatabase.getInstance().getReference().child("Movies").child(movie_id).child("num_ratings");
    	q.addValueEventListener(
    	        new ValueEventListener() {
    	            @Override
    	            public void onDataChange(DataSnapshot dataSnapshot) {
    	            	if (dataSnapshot.exists()) {
    	            		movie.setRating(dataSnapshot.getValue((String.class)));
    	            		System.out.println("get rating fb: " + movie.getRating());
    	            		return;
    	            	}
    	            }

    	            @Override
    	            public void onCancelled(DatabaseError databaseError) {
    	                System.out.println("getMovieRating error");
    	            }
    	});
//    	try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
    	String getRat = getMovieRating(movie_id);
    	num = movie.getRating();
    	double curRat = Double.parseDouble(num);
    	double newRat = ((curRat * Double.parseDouble(getRat)) + Double.parseDouble(value))/(curRat+1.0);
    	newRat =  Math.round(newRat * 10) / 10.0;
    	Map<String, Object> rating_map = new HashMap< String,Object>();
    	Map<String, Object> numRating_map = new HashMap< String,Object>();
    	
    	rating_map.put("ratings", String.valueOf(newRat));
    	numRating_map.put("num_ratings", String.valueOf(curRat+1.0));
    	DatabaseReference usersRef = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Movies").child(movie_id);
    	usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
			  public void onDataChange(DataSnapshot snapshot) {
				  usersRef.updateChildrenAsync(rating_map);
				  usersRef.updateChildrenAsync(numRating_map);
			  }
			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println(error);
				
			}
		});
		
    }
    
}







