package base.myFarm;

public class Tool {
    private final String name;
    private final int cost;
    private final double expGain;

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
