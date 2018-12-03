package com.cs252.soggyapples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) throws IOException {
    	String name = "sdsds";
    	FileInputStream serviceAccount = new FileInputStream("/Users/SiD/Desktop/coding/soggyapples/src/main/webapp/WEB-INF/serviceAccountKey.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		    .setDatabaseUrl("https://soggyapples-110e5.firebaseio.com/")
		    .build();

		FirebaseApp.initializeApp(options);
    	mDatabase = FirebaseDatabase.getInstance().getReference();
    	mDatabase.child("users").child("Users").setValueAsync(name);
        model.put("message", this.message);
        return "/home";
    }
    

}