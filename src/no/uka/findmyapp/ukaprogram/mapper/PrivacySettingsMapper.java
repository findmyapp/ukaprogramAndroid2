/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.mapper;


import no.uka.findmyapp.android.rest.contracts.UkaEvents.UkaEventContract;
import no.uka.findmyapp.android.rest.datamodels.enums.PrivacySetting;
import no.uka.findmyapp.android.rest.datamodels.models.UkaEvent;
import no.uka.findmyapp.android.rest.datamodels.models.UserPrivacy;
import no.uka.findmyapp.ukaprogram.contstants.ApplicationConstants;
import no.uka.findmyapp.ukaprogram.utils.CursorTools;
import android.database.Cursor;
import android.util.Log;


public class PrivacySettingsMapper {
	private static final String debug ="Inside PrivacySettingsMapper"; 
	
	public static UserPrivacy getUserPrivacyUkaEventFromCursor(Cursor cursor) {
		CursorTools ct = new CursorTools();
		UserPrivacy userprivacy = new UserPrivacy(); 
		userprivacy.setEventsPrivacySetting(PrivacySetting.getSetting(CursorTools.getIntFromTableColumn(cursor, ApplicationConstants.USER_PRIVACY_COLUMN_EVENTS)));
		userprivacy.setMediaPrivacySetting(PrivacySetting.getSetting(CursorTools.getIntFromTableColumn(cursor, ApplicationConstants.USER_PRIVACY_COLUMN_MEDIA)));
		userprivacy.setMoneyPrivacySetting(PrivacySetting.getSetting(CursorTools.getIntFromTableColumn(cursor, ApplicationConstants.USER_PRIVACY_COLUMN_MONEY)));
		userprivacy.setPositionPrivacySetting(PrivacySetting.getSetting(CursorTools.getIntFromTableColumn(cursor, ApplicationConstants.USER_PRIVACY_COLUMN_POSITION)));
		userprivacy.setId(CursorTools.getIntFromTableColumn(cursor, ApplicationConstants.USER_PRIVACY_COLUMN_USER_PRIVACY_ID));
		return userprivacy;
	}

}



