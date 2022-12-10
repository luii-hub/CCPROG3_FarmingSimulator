package base.myFarm;
/**
 * This simply the Player's overall statistics or progress within the duration of the game
 * */
public class Stats {

    private int timesPlanted = 0;
    private int timesPlowed = 0;
    private int timesWatered = 0;
    private int timesFertilized = 0;
    private int timesHarvested = 0;
    private int timesBoughtSeeds = 0;

    /* Methods for Incrementation  */
    /** Increment the number of times plowed by the Farmer */
    public void addTimesPlowed() {
        ++timesPlowed;
    }
    /** Increment the number of times watered by the Farmer */
    public void addTimesWatered() {
        ++timesWatered;
    }
    /** Increment the number of times fertilized by the Farmer */
    public void addTimesFertilized() {
        ++timesFertilized;
    }
    /** Increment the number of times planted by the Farmer */
    public void addTimesPlanted() {
         ++timesPlanted;
    }
    /** Increment the number of times harvested by the Farmer */
    public void addTimesHarvested() {
         ++timesHarvested;
    }
    /** Increment the number of times the farmer bought seeds from the seed shop  */
    public void addTimesBoughtSeeds(){
         ++timesBoughtSeeds;
    }

    /** Return the number of times plowed by the Farmer */
    public int getTimesPlowed() {
        return timesPlowed;
    }
    /** Return the number of times watered by the Farmer */
    public int getTimesWatered() {
        return timesWatered;
    }
    /** Return the number of times fertilized by the Farmer */
    public int getTimesFertilized() {
        return timesFertilized;
    }
    /** Return the number of times planted by the Farmer */
    public int getTimesPlanted() {
        return timesPlanted;
    }
    /** Return the number of times harvested by the Farmer */
    public int getTimesHarvested() {
        return timesHarvested;
    }
    /** Return the number of times farmer bought seeds from the seed shop */
    public int getTimesBoughtSeeds() {
        return timesBoughtSeeds;
    }

    @Override
    public String toString() {
        return "<html>Farmer Stats <p>" +
                "<p>No. of Seeds Bought: " + getTimesBoughtSeeds() +
                "<p>No. of times Planted: " + getTimesPlanted() +
                "<p>No. of times Harvested: " + getTimesHarvested() +
                "<p>No. of times Plowed: " + getTimesPlowed() +
                "<p>No. of times Watered: " + getTimesWatered() +
                "<p>No. of times Fertilized: " + getTimesFertilized() +
                "</html>";
    }
}
