/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.contstants;


// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationConstants.
 */
public final class ApplicationConstants
{	
	/* General constants */
	/** The Constant UKA_PATH. */
	public static final String UKA_PATH = "http://www.uka.no";
	
	/** The Constant UKA_START_DATE. */
	public static final int UKA_START_DATE = 6;
	
	/** The Constant UKA_END_DATE. */
	public static final int UKA_END_DATE = 30;
	
	/** The Constant IS_FAVOURITE. */
	public static final boolean IS_FAVOURITE = true; 
	
	/** The Constant FINDMYAPP_API_KEY. */
	public static final String FINDMYAPP_API_KEY = "key1231231";
	
	/** The Constant FINDMYAPP_API_SECRET. */
	public static final String FINDMYAPP_API_SECRET = "aodsfiaocenaodi";
	

	/*Date Constants. */
	/** The Constant YEAR. */
	public static final int YEAR = 111;
	
	/** The Constant MONTH. */
	public static final int MONTH = 9;
	
	/* Gesture contants. */
	/** The Constant GESTURE_LEFT. */
	public static final String GESTURE_LEFT = "left";
	
	/** The Constant GESTURE_RIGHT. */
	public static final String GESTURE_RIGHT = "right";
	
	/** The Constant GESTURE_DELETE. */
	public static final String GESTURE_DELETE = "delete";
	
	/** The Constant GESTURE_UPDATE. */
	public static final String GESTURE_UPDATE = "update";
	
	/* Event categories. */
	/** The Constant CATEGORY_CONCERT. */
	public final static String CATEGORY_CONCERT = "'konsert'";
	
	/** The Constant CATEGORY_REVUE. */
	public final static String CATEGORY_REVUE = "'revy-og-teater'";
	
	/** The Constant CATEGORY_LECTURE. */
	public final static String CATEGORY_LECTURE = "'andelig-fode'";
	
	/** The Constant CATEGORY_PARTY. */
	public final static String CATEGORY_PARTY = "'fest-og-moro'";
	//TODO implement favourite category query
	/** The Constant CATEGORY_FAVOURITE. */
	public final static String CATEGORY_FAVOURITE = "";
	
	/** The Constant LIST_ITEM_CLICKED_SIGNAL. */
	public final static String LIST_ITEM_CLICKED_SIGNAL = "clicked";
	
	/* UKA-program database constants. */
	/** The Constant DATABASE_NAME. */
	public static final String DATABASE_NAME = "ukaprogram.db"; 
	
	/** The Constant DATABASE_VERSION. */
	public static final int DATABASE_VERSION = 3; 
	
	/* Favourite table constants */
	/** The Constant FAVOURITE_DATABASE_CREATE_TABLE. */
	public static final String FAVOURITE_DATABASE_CREATE_TABLE = 
		  "CREATE TABLE " + ApplicationConstants.FAVOURITE_TABLE_NAME + " ("	
		  + ApplicationConstants.FAVOURITE_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, "
		  + ApplicationConstants.FAVOURITE_TABLE_COLUMN_EVENT_ID + " INTEGER, "
		  + ApplicationConstants.FAVOURITE_TABLE_COLUMN_IS_FAVOURITE + " BOOLEAN "
		  + ");";
	
	/** The Constant FAVOURITE_TABLE_NAME. */
	public static final String FAVOURITE_TABLE_NAME = "persistent_favourite_list";
	
	/** The Constant FAVOURITE_TABLE_COLUMN_ID. */
	public static final String FAVOURITE_TABLE_COLUMN_ID = "_id";
	
	/** The Constant FAVOURITE_TABLE_COLUMN_EVENT_ID. */
	public static final String FAVOURITE_TABLE_COLUMN_EVENT_ID = "event_id"; 
	
	/** The Constant FAVOURITE_TABLE_COLUMN_IS_FAVOURITE. */
	public static final String FAVOURITE_TABLE_COLUMN_IS_FAVOURITE = "is_favourite";
	
	/* Favourite related queries */
	/** The Constant FAVOURITE_TABLE_WHERE_CLAUSE_EVENT_ID_IS_FAVOURITE. */
	public static final String FAVOURITE_TABLE_WHERE_CLAUSE_EVENT_ID_IS_FAVOURITE = 
		ApplicationConstants.FAVOURITE_TABLE_COLUMN_EVENT_ID + "=?";
	
	/* User privacy table constants */
	
	/** The Constant USER_PRIVACY_CREATE_TABLE. */
	public static final String USER_PRIVACY_CREATE_TABLE = 
		  "CREATE TABLE " + ApplicationConstants.USER_PRIVACY_TABLE_NAME + " ("	
		  + ApplicationConstants.USER_PRIVACY_COLUMN_ID + " INTEGER PRIMARY KEY, "
		  + ApplicationConstants.USER_PRIVACY_COLUMN_USER_PRIVACY_ID + " INTEGER, "
		  + ApplicationConstants.USER_PRIVACY_COLUMN_MONEY + " INTEGER,  "
		  + ApplicationConstants.USER_PRIVACY_COLUMN_EVENTS + " INTEGER,  "
		  + ApplicationConstants.USER_PRIVACY_COLUMN_MEDIA + " INTEGER,  "
		  + ApplicationConstants.USER_PRIVACY_COLUMN_POSITION + " INTEGER"
		  + ");";
	
	/** The Constant USER_PRIVACY_TABLE_NAME. */
	public static final String USER_PRIVACY_TABLE_NAME = "user_privacy_settings";
	
	/** The Constant USER_PRIVACY_COLUMN_ID. */
	public static final String USER_PRIVACY_COLUMN_ID = "_id";
	
	/** The Constant USER_PRIVACY_COLUMN_USER_PRIVACY_ID. */
	public static final String USER_PRIVACY_COLUMN_USER_PRIVACY_ID = "user_privacy_id";
	
	/** The Constant USER_PRIVACY_COLUMN_POSITION. */
	public static final String USER_PRIVACY_COLUMN_POSITION = "position";
	
	/** The Constant USER_PRIVACY_COLUMN_MONEY. */
	public static final String USER_PRIVACY_COLUMN_MONEY = "money";
	
	/** The Constant USER_PRIVACY_COLUMN_EVENTS. */
	public static final String USER_PRIVACY_COLUMN_EVENTS = "events";
	
	/** The Constant USER_PRIVACY_COLUMN_MEDIA. */
	public static final String USER_PRIVACY_COLUMN_MEDIA = "media";
	
	
	/* Facebook constants */
	
	/** The Constant UKA_PROGRAM_FACEBOOK_ID. */
	public static final String UKA_PROGRAM_FACEBOOK_ID =
		"197690866955602";
}
