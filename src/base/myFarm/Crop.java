package base.myFarm;

/**
 * This class contains the Crop object which the player can manipulate, add, remove, and will be contained by Tiles in the farm plot that will act as plants.
 */
public class Crop {
	/** Name of Crop */
	private final String name;
	/** Type of Crop */
	private final CropType type;
	/** Harvest Time of Crop */
	private final int harvestTime;
	/** Growth Time of Crop */
	private int growTime;
	/** Water Count of Crop */
	private int water;
	/** Required Water of Crop */
	private final int waterNeeded;
	/** Water Bonus of Crop */
	private final int waterBonus;
	/** Water Boolean Value of Crop */
	private boolean isWatered;
	/** Fertilizer Count of Crop */
	private int fertilizer;
	/** Required Fertilizer of Crop */
	private final int fertilizerNeeded;
	/** Fertilizer Bonus of Crop */
	private final int fertilizerBonus;
	/** Fertilizable Boolean Value of Crop */
	private boolean isFertilized;
	/** Buy Cost of Crop */
	private final int buyCost;
	/** Sell Price of Crop */
	private final int sellPrice;
	/** Max Produce of Crop */
	private final int maxProduce;
	/** Minimum Produce of Crop */
	private final int minProduce;
	/** Exp Yield of Crop */
	private final double expYield;
	/** Choosable Boolean Option of Crop */
	private boolean isChoosable;

	public Crop(String name, CropType type, int harvestTime, int waterNeeded, int waterBonus, int fertilizerNeeded,
				int fertilizerBonus, int buyCost, int sellPrice, int minProduce, int maxProduce, double expYield) { 
		this.name = name; 
		this.type = type;
		this.harvestTime = harvestTime;
		this.growTime = 1;
		this.water = 0; 
		this.waterNeeded = waterNeeded; 
		this.waterBonus = waterBonus;
		this.fertilizer = 0; 
		this.fertilizerNeeded = fertilizerNeeded; 
		this.fertilizerBonus = fertilizerBonus;
		this.buyCost = buyCost;
		this.sellPrice = sellPrice;
		this.isWatered = false;
		this.isFertilized = false;
		this.maxProduce = maxProduce;
		this.minProduce = minProduce;
		this.expYield = expYield;
		this.isChoosable = false;
	}

	/* A 'Copy Constructor' of a new Crop. Why is this required? A: So that every crop has a different memory address */
	public Crop(Crop newCrop){
		this.name = newCrop.name; 
		this.type = newCrop.type;
		this.harvestTime = newCrop.harvestTime;
		this.growTime = 0;
		this.water = 0; 
		this.waterNeeded = newCrop.waterNeeded; 
		this.waterBonus = newCrop.waterBonus;
		this.fertilizer = 0; 
		this.fertilizerNeeded = newCrop.fertilizerNeeded; 
		this.fertilizerBonus = newCrop.fertilizerBonus;
		this.buyCost = newCrop.buyCost;
		this.sellPrice = newCrop.sellPrice;
		this.isWatered = newCrop.isWatered;
		this.isFertilized = newCrop.isFertilized;
		this.maxProduce = newCrop.maxProduce;
		this.minProduce = newCrop.minProduce;
		this.expYield = newCrop.expYield;
		this.isChoosable = newCrop.isChoosable;
	}

	/** Copies of New Instance of a Crop */
	public static Crop newCrop(Crop newCrop){
		return new Crop(newCrop);
	}

	/* Required Getters */
	/** Returns the boolean value of isFertilized */
	public boolean isFertilized() {
		return isFertilized;
	}
	/** Returns the boolean value of isWatered */
	public boolean isWatered() {
		return isWatered;
	}
	/** Returns the boolean value of isChoosable */
	public boolean isChoosable() {
		return isChoosable;
	}

	/** Returns the name of the Crop */
	public String getName() { 
		return name; 
	}
	/** Returns the water count of the Crop */
	public int getWater() { 
		return water; 
	}
	/** Returns the required water of the Crop */
	public int getWaterNeeded() { 
		return waterNeeded; 
	}
	/** Returns the water bonus of the Crop */
	public int getWaterBonus() {
		return waterBonus;
	}
	/** Returns the fertilizer count of the Crop */
	public int getFertilizer() { 
		return fertilizer; 
	}
	/** Sets the Fertilizer Count of the Crop */
	public void setFertilizer(int fertilizer) { 
		this.fertilizer = this.fertilizer + fertilizer; 
	}
	/** Returns the Required  Count of the Crop */
	public int getFertilizerNeeded() { 
		return fertilizerNeeded; 
	}
	/** Returns the Fertilizer Bonus of the Crop */
	public int getFertilizerBonus() {
		return fertilizerBonus;
	}
	/** Returns the Harvest Time  of the Crop */
	public int getHarvestTime() {
		return harvestTime;
	}
	/** Returns the growth time of the Crop */
	public int getGrowTime() {
		return growTime;
	}
	/** Returns the type of the Crop */
	public CropType getType() {
		return type;
	}
	/** Returns the cost of the Crop */
	public int getBuyCost() {
		return buyCost;
	}
	/** Returns the sell price of the Crop */
	public int getSellPrice() {
		return sellPrice;
	}
	/** Returns the max produce of the Crop */
	public int getMaxProduce() {
		return maxProduce;
	}
	/** Returns the min produce of the Crop */
	public int getMinProduce() {
		return minProduce;
	}
	/** Returns the expYield of the Crop */
	public double getExpYield() {
		return expYield;
	}

	/* Required Setters */
	/** Sets the Fertilizer Boolean Value of the Crop */
	public void setFertilized(boolean fertilized) {
		isFertilized = fertilized;
	}
	/** Sets the Watered Boolean Value of the Crop */
	public void setWatered(boolean watered) {
		isWatered = watered;
	}
	/** Sets the Water Count Value of the Crop */
	public void setWater(int water) {
		this.water = this.water + water;
	}
	/** Sets the Choosable Boolean Value of the Crop */
	public void setChoosable(boolean choosable) {
		isChoosable = choosable;
	}

	/** Add Grow Time of a crop */
	public void addGrowTime() {
		this.growTime += 1;
	}
	/** Returns the generalized produce of a crop */
	public int productProduce(int minProduce, int maxProduce){
		return (int) Math.floor(Math.random()*(maxProduce - minProduce +1)+ minProduce);
	}
	@Override
	public String toString() {
		return "<html> [" + name + "] : " + type + "<p>" +
				"Harvest Time: " + harvestTime +  "<p>" +
				"Orig. Base Price: " + sellPrice + "<p>" + "Orig. Seed Cost: " + buyCost + "<p>" +
				"Water Needed: " + waterNeeded + "<p>" + "Orig. Water Bonus: " + fertilizerNeeded + "<p>" +
				"Fertilizer Needed: " + waterBonus + "<p>" + "Orig. Fertilizer Bonus: " + fertilizerBonus + "<p>" +
				"Exp Gained Per Produce: " + expYield +"</html>";
	}
}
