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
    public void addTimesPlowed() {
        ++timesPlowed;
    }
    public void addTimesWatered() {
        ++timesWatered;
    }
    public void addTimesFertilized() {
        ++timesFertilized;
    }
    public void addTimesPlanted() {
         ++timesPlanted;
    }
    public void addTimesHarvested() {
         ++timesHarvested;
    }
    public void addTimesBoughtSeeds(){
         ++timesBoughtSeeds;
    }

    public int getTimesPlowed() {
        return timesPlowed;
    }

    public int getTimesWatered() {
        return timesWatered;
    }

    public int getTimesFertilized() {
        return timesFertilized;
    }

    public int getTimesPlanted() {
        return timesPlanted;
    }

    public int getTimesHarvested() {
        return timesHarvested;
    }

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
