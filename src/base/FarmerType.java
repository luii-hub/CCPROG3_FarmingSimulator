package base;

public enum FarmerType {
    DEFAULT,
    REGISTERED("Registered Farmer", 5, 1, 1, 0, 0, 200),
    DISTINGUISHED("Distinguished Farmer", 10, 2, 2, 1, 0, 300),
    LEGENDARY("Legendary Farmer", 15, 4, 3, 2, 1, 400);

    private String farmerType;
    private int levelRequirement;
    private double bonusEarnings;
    private double registrationFee;
    private int fertilizerBonusIncrease;
    private int waterBonusIncrease;
    private int seedCostReduction;

    FarmerType() {
    }

    FarmerType(String farmerType, int levelRequirement, double bonusEarnings, int seedCostReduction, int waterBonusIncrease, int fertilizerBonusIncrease, double registrationFee) {
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

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public double getBonusEarnings() {
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

    public double getRegistrationFee() {
        return registrationFee;
    }
}



