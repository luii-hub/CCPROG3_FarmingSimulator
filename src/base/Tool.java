package base;

public class Tool {
    private String name;
    private int cost;
    private double expGain;

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
