package no.uka.findmyapp.ukaprogram.models;

import java.io.Serializable;

public class Day implements Serializable{
	private String weekday;
	private int dayNmbr;
	
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public int getDayNmbr() {
		return dayNmbr;
	}
	public void setDayNmbr(int dayNmbr) {
		this.dayNmbr = dayNmbr;
	}
}