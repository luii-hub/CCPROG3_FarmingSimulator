package base;

import java.util.HashMap;

public class Crop { // DO NOT EDIT
	private String name; // DO NOT EDIT
	private  CropType type;
	private int harvestTime;
	private int growTime;
	private int water; // DO NOT EDIT 
	private int waterNeeded; // DO NOT EDIT
	private int waterBonus;
	private boolean isWatered;
	private int fertilizer; // DO NOT EDIT
	private int fertilizerNeeded; // DO NOT EDIT
	private int fertilizerBonus;
	private boolean isFertilized;
	private int buyCost;
	private int sellPrice;
	private int maxProduce;
	private int minProduce;
	private double expYield;
	private boolean isChoosable;

	public Crop(){

	}
	public Crop(String name, CropType type, int harvestTime, int waterNeeded, int waterBonus, int fertilizerNeeded,
				int fertilizerBonus, int buyCost, int sellPrice, int minProduce, int maxProduce, double expYield) { // DO NOT EDIT
		this.name = name; // DO NOT EDIT
		this.type = type;
		this.harvestTime = harvestTime;
		this.growTime = 0;
		this.water = 0; // DO NOT EDIT
		this.waterNeeded = waterNeeded; // DO NOT EDIT
		this.waterBonus = waterBonus;
		this.fertilizer = 0; // DO NOT EDIT
		this.fertilizerNeeded = fertilizerNeeded; // DO NOT EDIT
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

	//Declares a new crop so that they won't have the same MA in the tiles.
	public Crop(Crop newCrop){
		this.name = newCrop.name; // DO NOT EDIT
		this.type = newCrop.type;
		this.harvestTime = newCrop.harvestTime;
		this.growTime = 0;
		this.water = 0; // DO NOT EDIT
		this.waterNeeded = newCrop.waterNeeded; // DO NOT EDIT
		this.waterBonus = newCrop.waterBonus;
		this.fertilizer = 0; // DO NOT EDIT
		this.fertilizerNeeded = newCrop.fertilizerNeeded; // DO NOT EDIT
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
	public boolean isFertilized() {
		return isFertilized;
	}
	public boolean isWatered() {
		return isWatered;
	}
	public void setFertilized(boolean fertilized) {
		isFertilized = fertilized;
	}
	public void setWatered(boolean watered) {
		isWatered = watered;
	}
	public String getName() { // DO NOT EDIT
		return name; // DO NOT EDIT
	}
	public int getWater() { // DO NOT EDIT
		return water; // DO NOT EDIT
	}
	public void setWater(int water) { // DO NOT EDIT
		this.water = this.water + water; // DO NOT EDIT
	}
	public int getWaterNeeded() { // DO NOT EDIT
		return waterNeeded; // DO NOT EDIT
	}
	public void setWaterNeeded(int waterNeeded) { // DO NOT EDIT
		this.waterNeeded = waterNeeded; // DO NOT EDIT
	}
	public int getFertilizer() { // DO NOT EDIT
		return fertilizer; // DO NOT EDIT
	}
	public void setFertilizer(int fertilizer) { // DO NOT EDIT
		this.fertilizer = this.fertilizer + fertilizer; // DO NOT EDIT
	}
	public int getFertilizerNeeded() { // DO NOT EDIT
		return fertilizerNeeded; // DO NOT EDIT
	}
	public void setFertilizerNeeded(int fertilizerNeeded) { // DO NOT EDIT
		this.fertilizerNeeded = fertilizerNeeded; // DO NOT EDIT
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

	public void setChoosable(boolean choosable) {
		isChoosable = choosable;
	}

	public int productProduce(int minProduce, int maxProduce){
		return (int) Math.floor(Math.random()*(maxProduce - minProduce +1)+ minProduce);
	}
	@Override
	public String toString() { // YOU COULD EDIT
		return "\t[" + name + "] : " + type +
				"\n\tHarvest Time: " + harvestTime + "\t\tWater Needed: " + waterNeeded +
				"\n\tSeed Cost: " + buyCost + "\t\tFertilizer Needed: " + fertilizerNeeded +
				"\n\tBase Price: " + sellPrice + "\t\tProduce/Exp: String / Exp";
	}
}
