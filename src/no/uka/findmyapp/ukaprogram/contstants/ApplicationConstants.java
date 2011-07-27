/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.contstants;


// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationConstants.
 *
 * @author torstein.barkve
 */
public final class ApplicationConstants
{	
	/* General constants */
	public static final String UKA_PATH = "http://www.uka.no";
	
	public static final int UKA_START_DATE = 6;
	
	public static final int UKA_END_DATE = 30;
	
	public static final boolean IS_FAVOURITE = true; 

	/*Date Constants. */
	public static final int YEAR = 111;
	
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
	public static final int DATABASE_VERSION = 1; 
	
	/* Favourite table constants */
	/** Persistent favourite table. */
	public static final String FAVOURITE_TABLE_NAME = "persistent_favourite_list";
	
	/** The Constant FAVOURITE_TABLE_COLUMN_ID. */
	public static final String FAVOURITE_TABLE_COLUMN_ID = "_id";
	
	/** The Constant FAVOURITE_TABLE_COLUMN_EVENT_ID. */
	public static final String FAVOURITE_TABLE_COLUMN_EVENT_ID = "event_id"; 
	
	/** The Constant FAVOURITE_TABLE_COLUMN_IS_FAVOURITE. */
	public static final String FAVOURITE_TABLE_COLUMN_IS_FAVOURITE = "is_favourite";
	
	/* Favourite related queries */
	public static final String FAVOURITE_TABLE_WHERE_CLAUSE_EVENT_ID_IS_FAVOURITE = 
		ApplicationConstants.FAVOURITE_TABLE_COLUMN_EVENT_ID + "=?";
	
	/* User privacy table constants */
	
	public static final String USER_PRICVAY_TABLE_NAME = "user_privacy_settings";
	
	public static final String USER_PRICVAY_COLUMN_ID = "_id";
	
	public static final String USER_PRICVAY_COLUMN_USER_PRIVACY_ID = "user_privacy_id";
	
	public static final String USER_PRICVAY_COLUMN_POSITION = "position";
	
	public static final String USER_PRICVAY_COLUMN_MONEY = "money";
	
	public static final String USER_PRICVAY_COLUMN_EVENTS = "events";
	
	public static final String USER_PRICVAY_COLUMN_MEDIA = "media";
	
	
	/* Facebook constants */
	
	public static final String UKA_PROGRAM_FACEBOOK_ID =
		"197690866955602";
}
