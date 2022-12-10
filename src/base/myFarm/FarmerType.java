package base.myFarm;

/**
 * This enum class is declares the FarmerType where it contains the benefits of the farmer upon registration of a new Farmer Status.
 */
public enum FarmerType {
    /**
     * A Farmer with this type is considered a Default Farmer with Zero Farming Benefits.
     */
    DEFAULT("Novice Farmer", 0, 0, 0, 0, 0, 0),
    /**
     * A Farmer with this type is considered a Default Farmer with little Farming Benefits.
     */
    REGISTERED("Registered Farmer", 5, 1, 1, 0, 0, 200),
    /**
     * A Farmer with this type is considered a Default Farmer with Moderate Farming Benefits.
     */
    DISTINGUISHED("Distinguished Farmer", 10, 2, 2, 1, 0, 300),
    /**
     * A Farmer with this type is considered a Default Farmer with a lot Farming Benefits.
     */
    LEGENDARY("Legendary Farmer", 15, 4, 3, 2, 1, 400);

    private final String farmerType;
    private final int levelRequirement;
    private final int bonusEarnings;
    private final double registrationFee;
    private final int fertilizerBonusIncrease;
    private final int waterBonusIncrease;
    private final int seedCostReduction;

    /** Farmer Type Constructor */
    FarmerType(String farmerType, int levelRequirement, int bonusEarnings, int seedCostReduction, int waterBonusIncrease, int fertilizerBonusIncrease, double registrationFee) {
        this.farmerType = farmerType;
        this.levelRequirement = levelRequirement;
        this.bonusEarnings = bonusEarnings;
        this.seedCostReduction = seedCostReduction;
        this.waterBonusIncrease = waterBonusIncrease;
        this.fertilizerBonusIncrease = fertilizerBonusIncrease;
        this.registrationFee = registrationFee;
    }
    /** Returns the name of the FarmerType of the farmer */
    public String getFarmerType() {
        return farmerType;
    }
    /** Returns the bonus earnings of the farmer */
    public int getBonusEarnings() {
        return bonusEarnings;
    }
    /** Returns the seed cost reduction of the farmer */
    public int getSeedCostReduction() {
        return seedCostReduction;
    }
    /** Returns the water bonus increase of the farmer */
    public int getWaterBonusIncrease() {
        return waterBonusIncrease;
    }
    /** Returns the fertilizer bonus increase of the farmer */
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



