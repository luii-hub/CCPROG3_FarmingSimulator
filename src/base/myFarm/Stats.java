package base.myFarm;
/* This Class is simply the Player's overall statisics or progress within the duration of the game */
public class Stats {

    private int timesPlanted = 0;
    private int timesPlowed = 0;
    private int timesWatered = 0;
    private int timesFertilized = 0;
    private int timesHarvested = 0;
    private int timesBoughtSeeds = 0;

    /* Methods for Incrementation  */
    public int addTimesPlowed() {
        return ++timesPlowed;
    }
    public int addTimesWatered() {
        return ++timesWatered;
    }
    public int addTimesFertilized() {
        return ++timesFertilized;
    }
    public int addTimesPlanted() {
        return ++timesPlanted;
    }
    public int addTimesHarvested() {
        return ++timesHarvested;
    }
    public int addTimesBoughtSeeds(){
        return ++timesBoughtSeeds;
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
