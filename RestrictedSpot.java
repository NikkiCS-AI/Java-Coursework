// RestrictedSpot.java
public class RestrictedSpot {
    public String spotId;
    public String spotName;
    public double spotArea;
    public double averageTime; // in minutes
    public int maxCapacity;

    public RestrictedSpot(String id, String name, double area, double avgTime) {
        this.spotId = id;
        this.spotName = name;
        this.spotArea = area;
        this.averageTime = avgTime;
        this.maxCapacity = calculateMaxCapacity();
    }

    // Calculate capacity based on 1 meter radius per person (πr²)
    private int calculateMaxCapacity() {
        double minDist = 1.0;
        double areaPerPerson = Math.PI * minDist * minDist;
        return (int) (spotArea / areaPerPerson);
    }

    public void displayInfo() {
        System.out.println("[" + spotId + "] " + spotName +
            " | Area: " + spotArea + " sq.m | Max Capacity: " + maxCapacity);
    }
}
