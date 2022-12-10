package base.myFarm;

/**
 * This class represents the tool that the farmer will use for the manipulation of data within the farm board and manipulation of crops.
 */
public class Tool {
    private final String name;
    private final int cost;
    private final double expGain;

    /**
     * Constructs a tool object for the farmer to use and manipulate with
     * @param name Name of Tool
     * @param cost Tool Cost Usage
     * @param expGain Exp Gained per Use
     */
    public Tool(String name, int cost, double expGain){
        this.name = name;
        this.cost = cost;
        this.expGain = expGain;

    }

    /* Required Getters */
    public double getExpGain() {
        return expGain;
    }
    public int getCost() {
        return cost;
    }
}
