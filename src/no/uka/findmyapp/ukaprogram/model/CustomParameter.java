/**
 * 
 */
package no.uka.findmyapp.ukaprogram.model;

/**
 * @author torstein.barkve
 *
 */
public class CustomParameter
{
	private String parameterName; 
	private String parameterTextValue; 
	private float parameterNumberValue; 
	private String username;
	
	public String getParameterName( ) {
		return parameterName;
	}
	public void setParameterName(String parameterName ) {
		this.parameterName = parameterName;
	}
	public String getParameterTextValue( ) {
		return parameterTextValue;
	}
	public void setParameterTextValue(String parameterTextValue ) {
		this.parameterTextValue = parameterTextValue;
	}
	public float getParameterNumberValue( ) {
		return parameterNumberValue;
	}
	public void setParameterNumberValue(float parameterNumberValue ) {
		this.parameterNumberValue = parameterNumberValue;
	}
	public String getUsername( ) {
		return username;
	}
	public void setUsername(String username ) {
		this.username = username;
	} 
	
	
}
