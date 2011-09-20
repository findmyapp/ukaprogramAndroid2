/* 
 * Copyright (c) 2011 Accenture
 * Licensed under the MIT open source license
 * http://www.opensource.org/licenses/mit-license.php
 */
package no.uka.findmyapp.ukaprogram.restlibrary;

import java.net.URI;

import no.uka.findmyapp.ukaprogram.models.Fact;
import no.uka.findmyapp.ukaprogram.models.HttpType;
import no.uka.findmyapp.ukaprogram.models.Humidity;
import no.uka.findmyapp.ukaprogram.models.LocationCount;
import no.uka.findmyapp.ukaprogram.models.Noise;
import no.uka.findmyapp.ukaprogram.models.ServiceModel;
import no.uka.findmyapp.ukaprogram.models.Temperature;
import no.uka.findmyapp.ukaprogram.models.UkaEvent;
import no.uka.findmyapp.ukaprogram.models.User;
import android.location.Location;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating UkappService objects.
 */
public class UkappServiceFactory {
	
	/** The Constant debug. */
	private static final String debug = "createServiceModel";
	
	/**
	 * Creates a new UkappService object.
	 *
	 * @param service the service
	 * @return the service model
	 */
	public static ServiceModel createServiceModel(UkappsServices service) {
		ServiceModel model; 
		try {
			switch (service) {
				case GET_TEMPERATURE_DATA_FROM_LOCATION: 
					model = new ServiceModel(
				 		new URI("http://findmyapp.net/findmyapp/locations/??/temperature/latest"),
				 		HttpType.GET,
						Temperature.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 
				case GET_NOISE_DATA_FROM_LOCATION: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/locations/??/noise/latest"),
						HttpType.GET,
						Noise.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 
				case GET_HUMIDITY_DATA_FROM_LOCATION: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/locations/??/humidity/latest"),
						HttpType.GET,
						Humidity.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 
	
				case GET_ALL_LOCATIONS: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/locations"),
						HttpType.GET,
						Location.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 

				case GET_USER_COUNT_AT_LOCATION: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/locations/??/users/count"),
						HttpType.GET,
						Integer.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 
				case GET_USER_COUNT_AT_ALL_LOCATIONS: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/locations/all/users/count"),
						HttpType.GET,
						LocationCount.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 
	
				case GET_ALL_LOCATION_FACTS: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/locations/??/facts"),
						HttpType.GET,
						Fact.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 
					
				case GET_RANDOM_LOCATION_FACT: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/locations/??/facts/random"),
						HttpType.GET,
						Fact.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 
					
				case GET_UKA_PROGRAM_FOR_DATE: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/program/??/events&date=??"),
						HttpType.GET,
						UkaEvent.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 

					Log.d("GET EVENTS FOR DATE2", model.toString());

					return model; 
					
				case GET_ALL_UKAEVENTS: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/program/??/events"),
						HttpType.GET,
						UkaEvent.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model;
	
				case GET_UKA_PROGRAM_PLACES: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/program/uka11/places"),
						HttpType.GET,
						String.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 
				case GET_FRIENDS_ATTENDING_EVENT: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/events/??/friends"),
						HttpType.GET,
						User.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 
				case GET_PRIVACY: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/users/me/privacy"),
						HttpType.GET,
						String.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model; 
					
				/* SET SERVICES */
				case SET_TEMPERATURE_DATA: 
					model = new ServiceModel(
						new URI("http://findmyapp.net/findmyapp/locations/??/temperature"),
						HttpType.POST,
						Temperature.class,
						null,
						null,
						"no.uka.findmyapp.android.demo.BROADCAST_INTENT_TOKEN"); 
					return model;
			}
		} 
		catch (Exception e) {
			Log.e(debug, e.getMessage());
		}
		
		return null;  
	}
}
