package one.microstream.sampler.customlegacytypehandler;

public class Location 
{
	String directions;
	double latitude;
	double longitude;
	
	public Location(String directions, double latitude, double longitude) 
	{
		super();
		this.directions = directions;
		this.latitude = latitude;
		this.longitude = longitude;
	}		
	
	public String toString()
	{
		return "Latitude: " + latitude + "\nLogitude: " + longitude + "\ndirections: " + directions;  
	}
}
