package no.uka.findmyapp.ukaprogram.models;

public class WeekDay {
	public WeekDay(){}
	public String getShortWeekDayName(int dayNumber){
		switch (dayNumber) {
		case 1:
			return "man";
		case 2:
			return "tirs";
		case 3:
			return "ons";
		case 4:
			return "tors";
		case 5:
			return "fre";
		case 6:
			return "lør";
		case 0:
			return "søn";
	
		default:
			return "";
		}
		
	}
}
