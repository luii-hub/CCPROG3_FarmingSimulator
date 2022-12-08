package base.myFarm;
public class Crop { 
	private String name; 
	private  CropType type;
	private int harvestTime;
	private int growTime;
	private int water;  
	private int waterNeeded; 
	private int waterBonus;
	private boolean isWatered;
	private int fertilizer; 
	private int fertilizerNeeded; 
	private int fertilizerBonus;
	private boolean isFertilized;
	private int buyCost;
	private int sellPrice;
	private int maxProduce;
	private int minProduce;
	private double expYield;
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

	public static Crop newCrop(Crop newCrop){
		return new Crop(newCrop);
	}

	/* Required Getters */
	public boolean isFertilized() {
		return isFertilized;
	}
	public boolean isWatered() {
		return isWatered;
	}
	public String getName() { 
		return name; 
	}
	public int getWater() { 
		return water; 
	}
	public int getWaterNeeded() { 
		return waterNeeded; 
	}
	public int getFertilizer() { 
		return fertilizer; 
	}
	public void setFertilizer(int fertilizer) { 
		this.fertilizer = this.fertilizer + fertilizer; 
	}
	public int getFertilizerNeeded() { 
		return fertilizerNeeded; 
	}
	public int getHarvestTime() {
		return harvestTime;
	}
	public int getGrowTime() {
		return growTime;
	}
	public void addGrowTime() {
		this.growTime += 1;
	}
	public CropType getType() {
		return type;
	}
	public int getBuyCost() {
		return buyCost;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public int getMaxProduce() {
		return maxProduce;
	}
	public int getMinProduce() {
		return minProduce;
	}
	public int getFertilizerBonus() {
		return fertilizerBonus;
	}
	public int getWaterBonus() {
		return waterBonus;
	}
	public double getExpYield() {
		return expYield;
	}
	public boolean isChoosable() {
		return isChoosable;
	}

	/* Required Setters */
	public void setFertilized(boolean fertilized) {
		isFertilized = fertilized;
	}
	public void setWatered(boolean watered) {
		isWatered = watered;
	}
	public void setWater(int water) {
		this.water = this.water + water;
	}
	public void setChoosable(boolean choosable) {
		isChoosable = choosable;
	}

	public int productProduce(int minProduce, int maxProduce){
		return (int) Math.floor(Math.random()*(maxProduce - minProduce +1)+ minProduce);
	}
	@Override
	public String toString() {
		return "<html> [" + name + "] : " + type + "<p>" +
				"Harvest Time: " + harvestTime +  "<p>" +
				"Base Price: " + sellPrice + "<p>" + "Seed Cost: " + buyCost + "<p>" +
				"Water Needed: " + waterNeeded + "<p>" + "Water Bonus: " + fertilizerNeeded + "<p>" +
				"Fertilizer Needed: " + waterBonus + "<p>" + "Fertilizer Bonus: " + fertilizerBonus + "<p>" +
				"Exp Gained Per Produce: " + expYield +"</html>";
	}
}
