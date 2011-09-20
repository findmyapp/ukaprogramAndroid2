/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.providers.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import no.uka.findmyapp.ukaprogram.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.providers.UkaEvents.UkaEventContract;
import android.content.ContentValues;

// TODO: Auto-generated Javadoc
/**
 * The Class UkaEventContentMapper.
 */
public class UkaEventContentMapper implements IContentMapper {

	/** The Constant debug. */
	private static final String debug = "UkaEventContentMapper";

	/*
	 * (non-Javadoc)
	 * 
	 * @see no.uka.findmyapp.android.rest.mapper.IContentMapper#isList()
	 */
	@Override
	public boolean isList() {
		return false;
	}

	// TODO fix age to favourite
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * no.uka.findmyapp.android.rest.mapper.IContentMapper#mapValues(java.io
	 * .Serializable)
	 */
	@Override
	public ContentValues mapValues(Serializable temp) {
		UkaEvent event = (UkaEvent) temp;
		ContentValues contentValues = new ContentValues();
		contentValues.put(UkaEventContract.ID, event.getId());
		contentValues.put(UkaEventContract.BILLING_ID, event.getBillingid());
		contentValues.put(UkaEventContract.ENTRANCE_ID, event.getEntranceId());
		contentValues.put(UkaEventContract.TITLE, event.getTitle());
		contentValues.put(UkaEventContract.LEAD, event.getLead());
		contentValues.put(UkaEventContract.TEXT, event.getText());
		contentValues.put(UkaEventContract.PLACE, event.getPlace());
		contentValues.put(UkaEventContract.IMAGE, event.getImage());
		contentValues.put(UkaEventContract.THUMBNAIL, event.getThumbnail());
		contentValues.put(UkaEventContract.AGE_LIMIT, event.getAgeLimit());
		contentValues.put(UkaEventContract.EVENT_TYPE, event.getEventType());
		contentValues.put(UkaEventContract.SHOWING_TIME, event.getShowingTime());
		contentValues.put(UkaEventContract.FREE, event.isFree());
		contentValues.put(UkaEventContract.CANCELED, event.isCanceled());
		contentValues.put(UkaEventContract.FAVOURITE, event.isFavourite());
		contentValues.put(UkaEventContract.LOWEST_PRICE, event.getLowestPrice());
		contentValues.put(UkaEventContract.SPOTIFY_STRING, event.getSpotifyString());
		contentValues.put(UkaEventContract.UPDATED_DATE, event.getUpdatedDate());
		contentValues.put(UkaEventContract.PLACE_STRING, event.getPlaceString());

		return contentValues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * no.uka.findmyapp.android.rest.mapper.IContentMapper#mapValuesList(java
	 * .io.Serializable)
	 */
	@Override
	public List<ContentValues> mapValuesList(Serializable object) {
		List<ContentValues> list = new ArrayList<ContentValues>();
		List<UkaEvent> ukaEventList = (List<UkaEvent>) object;

		for (UkaEvent event : ukaEventList) {
			list.add(mapValues(event));
		}
		return list;
	}
}
