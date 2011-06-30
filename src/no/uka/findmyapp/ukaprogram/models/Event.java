package no.uka.findmyapp.ukaprogram.models;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable{
	private int id;
	private Date showingTime;
	private Date publishTime;
	private String place;
	private int billigId;
	private int eventId;
	private Date netsaleFrom;
	private Date netsaleTo;
	private boolean free;
	private boolean canceled;
	private int entranceId;
	private String title;
	private String lead;
	private String text;
	private String eventType;
	private String image;
	private String thumbnail;
	private Boolean hiddenFromListing;
	private String slug;
	private int ageLimit;
	private int detailPhotoId;
	
	private String weekday;
	private String startTime;
	private String dayNumber;
	private int price;
	private String description;
	
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(String dayNumber) {
		this.dayNumber = dayNumber;
	}

	public String toString(){
		return title + "  " + place;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getShowingTime() {
		return showingTime;
	}
	public void setShowingTime(Date showingTime) {
		this.showingTime = showingTime;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getBilligId() {
		return billigId;
	}
	public void setBilligId(int billigId) {
		this.billigId = billigId;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public Date getNetsaleFrom() {
		return netsaleFrom;
	}
	public void setNetsaleFrom(Date netsaleFrom) {
		this.netsaleFrom = netsaleFrom;
	}
	public Date getNetsaleTo() {
		return netsaleTo;
	}
	public void setNetsaleTo(Date netsaleTo) {
		this.netsaleTo = netsaleTo;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
	public boolean isCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	public int getEntranceId() {
		return entranceId;
	}
	public void setEntranceId(int entranceId) {
		this.entranceId = entranceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLead() {
		return lead;
	}
	public void setLead(String lead) {
		this.lead = lead;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Boolean getHiddenFromListing() {
		return hiddenFromListing;
	}
	public void setHiddenFromListing(Boolean hiddenFromListing) {
		this.hiddenFromListing = hiddenFromListing;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public int getAgeLimit() {
		return ageLimit;
	}
	public void setAgeLimit(int ageLimit) {
		this.ageLimit = ageLimit;
	}
	public int getDetailPhotoId() {
		return detailPhotoId;
	}
	public void setDetailPhotoId(int detailPhotoId) {
		this.detailPhotoId = detailPhotoId;
	}

}
