
public class Crit {

	private static final int DURATION = 50; 
	private long timestamp = System.currentTimeMillis();
	
	private Coordinates coordinates;
	private boolean crit = true;
	private long critTimestamp;
	private int critCnt;
	
	public Crit(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public boolean isCrit() {
		return crit;
	}
	
	public long getCritTimestamp() {
		return critTimestamp;
	}
	
	public int getCritCnt() {
		return critCnt;
	}
	
	public void setCritTimestamp() {
		critTimestamp = System.currentTimeMillis();
	}
	
	public void setCritCnt() {
		critCnt++;
	}
	
	public void disableCrit() {
		crit = false;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
}
