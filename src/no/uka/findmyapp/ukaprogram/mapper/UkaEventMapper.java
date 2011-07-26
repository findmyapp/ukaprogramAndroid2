/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.mapper;

import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.utils.CursorTools;
import android.database.Cursor;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class UkaEventMapper.
 */
public class UkaEventMapper 
{
	
	/** The Constant debug. */
	private static final String debug = "EventDatabase";
		
	/**
	 * Gets the uka event from cursor.
	 *
	 * @param cursor the cursor
	 * @return the uka event from cursor
	 */
	public static UkaEvent getUkaEventFromCursor(Cursor cursor) {
		Log.v(debug, "Inside CursorTools.getEventFromCursor");
		CursorTools ct = new CursorTools();
		UkaEvent ukaEvent = new UkaEvent(); 
		ukaEvent.setId(CursorTools.getIntFromTableColumn(cursor, UkaEventContract.ID));
		ukaEvent.setBillingid(CursorTools.getIntFromTableColumn(cursor, UkaEventContract.BILLING_ID));
		ukaEvent.setEntranceId(CursorTools.getIntFromTableColumn(cursor, UkaEventContract.ENTRANCE_ID));
		ukaEvent.setTitle(CursorTools.getStringFromTableColumn(cursor, UkaEventContract.TITLE));
		ukaEvent.setLead(CursorTools.getStringFromTableColumn(cursor, UkaEventContract.LEAD));
		ukaEvent.setText(CursorTools.getStringFromTableColumn(cursor, UkaEventContract.TEXT));
		ukaEvent.setPlace(CursorTools.getStringFromTableColumn(cursor, UkaEventContract.PLACE));
		ukaEvent.setEventType(CursorTools.getStringFromTableColumn(cursor, UkaEventContract.EVENT_TYPE));
		ukaEvent.setCanceled(CursorTools.getBooleanFromTableColumn(cursor, UkaEventContract.CANCELED));
		ukaEvent.setPrice(CursorTools.getIntFromTableColumn(cursor, UkaEventContract.LOWEST_PRICE));
		ukaEvent.setFavourite(CursorTools.getBooleanFromTableColumn(cursor, UkaEventContract.FAVOURITE));
		ukaEvent.setAgeLimit(CursorTools.getIntFromTableColumn(cursor, UkaEventContract.AGE_LIMIT));			
		ukaEvent.setEventType(CursorTools.getStringFromTableColumn(cursor, UkaEventContract.EVENT_TYPE));
		ukaEvent.setShowingTime(CursorTools.getLongFromTableColumn(cursor, UkaEventContract.SHOWING_TIME));
		ukaEvent.setFree(CursorTools.getBooleanFromTableColumn(cursor, UkaEventContract.FREE));
		ukaEvent.setThumbnail(CursorTools.getStringFromTableColumn(cursor, UkaEventContract.THUMBNAIL));
		ukaEvent.setImage(CursorTools.getStringFromTableColumn(cursor, UkaEventContract.IMAGE));
		ukaEvent.setSpotifyString(CursorTools.getStringFromTableColumn(cursor, UkaEventContract.SPOTIFY_STRING));
		ukaEvent.setPlaceString(CursorTools.getStringFromTableColumn(cursor, UkaEventContract.PLACE_STRING));
		ukaEvent.setUpdatedDate(CursorTools.getLongFromTableColumn(cursor, UkaEventContract.UPDATED_DATE));
		
		return ukaEvent;
	}
}

