package no.uka.findmyapp.ukaprogram.models;

import android.graphics.Bitmap;

public class Tweet {
	public String username;
	public String message;
	public String image_url;
	public Boolean usernameSet = false;
	public Boolean messageSet = false;
	public Boolean imageSet = false;
	public Bitmap avatar;

	public Tweet(String username, String message, String url) {
		this.username = username;
		this.message = message;
		this.image_url = url;
	}
}