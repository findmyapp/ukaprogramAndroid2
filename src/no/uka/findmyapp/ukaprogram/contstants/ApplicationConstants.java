/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.contstants;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationConstants.
 *
 * @author torstein.barkve
 */
public final class ApplicationConstants
{	
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
	
	/** Persistent favourite table. */
	public static final String FAVOURITE_TABLE_NAME = "persistent_favourite_list";
	
	/** The Constant FAVOURITE_TABLE_COLUMN_ID. */
	public static final String FAVOURITE_TABLE_COLUMN_ID = "_id";
	
	/** The Constant FAVOURITE_TABLE_COLUMN_EVENT_ID. */
	public static final String FAVOURITE_TABLE_COLUMN_EVENT_ID = UkaEventContract.EVENT_ID; 
	
	/** The Constant FAVOURITE_TABLE_COLUMN_IS_FAVOURITE. */
	public static final String FAVOURITE_TABLE_COLUMN_IS_FAVOURITE = "is_favourite";
}
