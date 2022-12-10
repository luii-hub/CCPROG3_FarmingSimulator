package base.myFarm;

public enum FarmerType {
    DEFAULT("Novice Farmer", 0, 0, 0, 0, 0, 0),
    REGISTERED("Registered Farmer", 5, 1, 1, 0, 0, 200),
    DISTINGUISHED("Distinguished Farmer", 10, 2, 2, 1, 0, 300),
    LEGENDARY("Legendary Farmer", 15, 4, 3, 2, 1, 400);

    private final String farmerType;
    private final int levelRequirement;
    private final int bonusEarnings;
    private final double registrationFee;
    private final int fertilizerBonusIncrease;
    private final int waterBonusIncrease;
    private final int seedCostReduction;

    FarmerType(String farmerType, int levelRequirement, int bonusEarnings, int seedCostReduction, int waterBonusIncrease, int fertilizerBonusIncrease, double registrationFee) {
        this.farmerType = farmerType;
        this.levelRequirement = levelRequirement;
        this.bonusEarnings = bonusEarnings;
        this.seedCostReduction = seedCostReduction;
        this.waterBonusIncrease = waterBonusIncrease;
        this.fertilizerBonusIncrease = fertilizerBonusIncrease;
        this.registrationFee = registrationFee;
    }

    public String getFarmerType() {
        return farmerType;
    }

    public int getBonusEarnings() {
        return bonusEarnings;
    }

    public int getSeedCostReduction() {
        return seedCostReduction;
    }

    public int getWaterBonusIncrease() {
        return waterBonusIncrease;
    }

    public int getFertilizerBonusIncrease() {
        return fertilizerBonusIncrease;
    }

    @Override
    public String toString() {
        return "<html> [" + getFarmerType() + "] : <p>" +
                "Bonus Earning(s): " + getBonusEarnings() + "<p>" +
                "Seed Cost Reduction: " + getSeedCostReduction() + "<p>" +
                "Water B.Limit Increase: " + getWaterBonusIncrease() + "<p>" +
                "Fertilizer B.Limit Increase: " + getFertilizerBonusIncrease() + "<p> <html>";
    }
}



