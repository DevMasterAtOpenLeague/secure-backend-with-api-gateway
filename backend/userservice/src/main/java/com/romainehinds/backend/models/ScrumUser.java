package com.romainehinds.backend.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.springframework.data.annotation.Id;

public class ScrumUser extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7126451133097983066L;
	
	private String objectId;
	private ArrayList<String> allPictureURLs;
	private String profilePictureURL;
	private int mediaCount;
	private int followsCount;
	private int followed_byCount;
	private Location location;
	
	public ScrumUser() {
		
	}
	
	public ScrumUser(String objId, String [] pictures, String profilePicUrl, String username, String fullname,
			int mediaCount, int followsCount, int followed_byCount, Map<String, String> locationMap){
		
		if (locationMap.containsKey("lat") && locationMap.containsKey("long")) {
			this.objectId = objId;
			this.allPictureURLs = new ArrayList<>();
			Arrays.asList(pictures).forEach((str) -> {
				this.allPictureURLs.add(str);
			});
			
			this.profilePictureURL = profilePicUrl;
			this.setFirstName(fullname.split(" ")[0]);
			this.setLastName(fullname.split(" ")[1]);
			
			this.mediaCount = mediaCount;
			this.followsCount = followsCount;
			this.followed_byCount = followed_byCount;
			
			this.location = new Location(locationMap.get("lat"), locationMap.get("long"));
		}
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public ArrayList<String> getAllPictureURLs() {
		return allPictureURLs;
	}

	public void setAllPictureURLs(ArrayList<String> allPictureURLs) {
		this.allPictureURLs = allPictureURLs;
	}

	public String getProfilePictureURL() {
		return profilePictureURL;
	}

	public void setProfilePictureURL(String profilePictureURL) {
		this.profilePictureURL = profilePictureURL;
	}

	public int getMediaCount() {
		return mediaCount;
	}

	public void setMediaCount(int mediaCount) {
		this.mediaCount = mediaCount;
	}

	public int getFollowsCount() {
		return followsCount;
	}

	public void setFollowsCount(int followsCount) {
		this.followsCount = followsCount;
	}

	public int getFollowed_byCount() {
		return followed_byCount;
	}

	public void setFollowed_byCount(int followed_byCount) {
		this.followed_byCount = followed_byCount;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScrumUser [objectId=");
		builder.append(objectId);
		builder.append(", allPictureURLs=");
		builder.append(allPictureURLs);
		builder.append(", profilePictureURL=");
		builder.append(profilePictureURL);
		builder.append(", mediaCount=");
		builder.append(mediaCount);
		builder.append(", followsCount=");
		builder.append(followsCount);
		builder.append(", followed_byCount=");
		builder.append(followed_byCount);
		builder.append(", location=");
		builder.append(location);
		builder.append("]");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((allPictureURLs == null) ? 0 : allPictureURLs.hashCode());
		result = prime * result + followed_byCount;
		result = prime * result + followsCount;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + mediaCount;
		result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
		result = prime * result + ((profilePictureURL == null) ? 0 : profilePictureURL.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScrumUser other = (ScrumUser) obj;
		if (allPictureURLs == null) {
			if (other.allPictureURLs != null)
				return false;
		} else if (!allPictureURLs.equals(other.allPictureURLs))
			return false;
		if (followed_byCount != other.followed_byCount)
			return false;
		if (followsCount != other.followsCount)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (mediaCount != other.mediaCount)
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		if (profilePictureURL == null) {
			if (other.profilePictureURL != null)
				return false;
		} else if (!profilePictureURL.equals(other.profilePictureURL))
			return false;
		return true;
	}
	
	

}

class Location {
	
	private String latitude;
	private String longitude;
	
	public Location(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "Location [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		return true;
	}
	
	
}