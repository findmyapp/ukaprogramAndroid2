/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.models;

import android.graphics.Bitmap;

// TODO: Auto-generated Javadoc
/**
 * The Class Tweet.
 */
public class Tweet {
	
	/** The username. */
	public String username;
	
	/** The message. */
	public String message;
	
	/** The image_url. */
	public String image_url;
	
	/** The username set. */
	public Boolean usernameSet = false;
	
	/** The message set. */
	public Boolean messageSet = false;
	
	/** The image set. */
	public Boolean imageSet = false;
	
	/** The avatar. */
	public Bitmap avatar;

	/**
	 * Instantiates a new tweet.
	 *
	 * @param username the username
	 * @param message the message
	 * @param url the url
	 */
	public Tweet(String username, String message, String url) {
		this.username = username;
		this.message = message;
		this.image_url = url;
	}
}