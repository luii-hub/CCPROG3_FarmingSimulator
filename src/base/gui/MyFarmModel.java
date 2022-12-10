package base.gui;
import base.myFarm.*;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* MyFarm Model of the GUI */
public class MyFarmModel {
    protected boolean isRunning = true;
    protected final Farmer player = new Farmer(FarmerType.DEFAULT);

    protected final HashMap<Integer, Tile> plot = new HashMap<>();

    protected final FarmPlot MyFarm = new FarmPlot(plot);

    protected final List<Crop> seedList = new ArrayList<Crop>(Arrays.asList(
            new Crop("Turnip", CropType.ROOT_CROP, 2, 1, 2, 0, 1, 5, 6, 1, 2, 5),
            new Crop("Carrot", CropType.ROOT_CROP, 3, 1, 2, 0, 1, 10, 9, 1, 2, 7.5),
            new Crop("Potato", CropType.ROOT_CROP, 5, 3, 4, 1, 2, 20, 3, 1, 10, 12.5),
            new Crop("Rose", CropType.FLOWER, 1, 1, 2, 0, 1, 5, 5, 1, 1, 2.5),
            new Crop("Tulip", CropType.FLOWER, 2, 2, 3, 0, 1, 10, 9, 1, 1, 5),
            new Crop("Sunflower", CropType.FLOWER, 2, 2, 3, 1, 2, 20, 19, 1, 1, 7.5),
            new Crop("Mango", CropType.FRUIT_TREE, 10, 7, 7, 4, 4, 100, 8, 5, 15, 25),
            new Crop("Apple", CropType.FRUIT_TREE, 10, 7, 7, 5, 5, 200, 5, 10, 15, 25)
    ));

    protected final List<Tool> toolList = new ArrayList<Tool>(Arrays.asList(
            new Tool("Plow Tool", 0, 25),
            new Tool("Watering Can", 0, 0.5),
            new Tool("Fertilizer", 10, 4),
            new Tool("Pickaxe", 50, 15),
            new Tool("Shovel", 7, 2)
    ));

    public void nextDay(FarmPlot MyFarm){
        MyFarm.setDaytime(1);
        /* Loop through each of the tiles in the farm plot and check and update every single tile or plant */
        for(int i = 1 ; i <= plot.size(); i++){
            /* Convert Harvestable PLANTS to WITHERED crops (TileStatus)  */
            if(plot.get(i).getStatus().equals(TileStatus.PLANT)){
                plot.get(i).setStatus(TileStatus.WITHERED);
                plot.get(i).setPlantable(true);
            }

            /* If selected tile contains a SEED, manipulate its contents depending on the conditions applied to it */
            if(plot.get(i).getStatus().equals(TileStatus.SEED)){
                /* Increment the growth time (life) of a Seed */
                if(plot.get(i).getPlantedCrop().getGrowTime() <= plot.get(i).getPlantedCrop().getHarvestTime()) {
                    plot.get(i).getPlantedCrop().addGrowTime();
                }

                /* Reset isWatered and isFertilized of Seed/Crop to False */
                if(plot.get(i).getPlantedCrop().isWatered()){
                    plot.get(i).getPlantedCrop().setWatered(false);
                }
                if(plot.get(i).getPlantedCrop().isFertilized()){
                    plot.get(i).getPlantedCrop().setFertilized(false);
                }

				/* 	Convert the SEED into a harvestable PLANT once the harvest conditions of a given seed is met,
					If the conditions are not met within the harvest conditions, convert the seed into a WITHERED plant */
                if(plot.get(i).getPlantedCrop().getGrowTime() == plot.get(i).getPlantedCrop().getHarvestTime()){
                    /* Convert SEED into Harvestable PLANT or Harvestable PLANT to WITHERED with FertilizerNeeded = 0 */
                    if (plot.get(i).getPlantedCrop().getFertilizerNeeded() == 0){
                        if (plot.get(i).getPlantedCrop().getWater() < plot.get(i).getPlantedCrop().getWaterNeeded()
                                && plot.get(i).getPlantedCrop().getFertilizer() == plot.get(i).getPlantedCrop().getFertilizerNeeded()) {
                            System.out.println("Plant is Withered");
                            plot.get(i).setStatus(TileStatus.WITHERED);
                        }
                        else if(plot.get(i).getPlantedCrop().getWater() >= plot.get(i).getPlantedCrop().getWaterNeeded()
                                && plot.get(i).getPlantedCrop().getFertilizer() == plot.get(i).getPlantedCrop().getFertilizerNeeded()) {
                            System.out.println("Plant is Harvestable 1.1");
                            plot.get(i).setStatus(TileStatus.PLANT);
                        }
                        /* If the selected Harvestable Plant's Fertilizer requirement is 0, but it overrides due to fertilizer bonuses, run this line of code */
                        if(plot.get(i).getPlantedCrop().getGrowTime() == plot.get(i).getPlantedCrop().getHarvestTime()
                                && plot.get(i).getPlantedCrop().getWater() >= plot.get(i).getPlantedCrop().getWaterNeeded()
                                && plot.get(i).getPlantedCrop().getFertilizerBonus() > 0){
                            System.out.println("Plant is Harvestable 1.2");
                            plot.get(i).setStatus(TileStatus.PLANT);
                        }
                    }
                    /* Convert SEED into Harvestable PLANT or Harvestable PLANT to WITHERED with FertilizerNeeded > 0 */
                    else if(plot.get(i).getPlantedCrop().getFertilizerNeeded() > 0){
                        if (plot.get(i).getPlantedCrop().getWater() < plot.get(i).getPlantedCrop().getWaterNeeded()
                                && plot.get(i).getPlantedCrop().getFertilizer() < plot.get(i).getPlantedCrop().getFertilizerNeeded()) {
                            System.out.println("Plant is Withered");
                            plot.get(i).setStatus(TileStatus.WITHERED);
                        }
                        else if(plot.get(i).getPlantedCrop().getWater() >= plot.get(i).getPlantedCrop().getWaterNeeded()
                                && plot.get(i).getPlantedCrop().getFertilizer() >= plot.get(i).getPlantedCrop().getFertilizerNeeded()) {
                            System.out.println("Plant is Harvestable 2");
                            /* If selected seed is a FRUIT TREE, instead of turning it into a PLANT, set it into a TREE as its TileStatus */
                            if(plot.get(i).getPlantedCrop().getType().equals(CropType.FRUIT_TREE)){
                                plot.get(i).setStatus(TileStatus.TREE);
                            }
                            else{
                                plot.get(i).setStatus(TileStatus.PLANT);
                            }
                        }
                    }
                }
            }
        }
    }

    /**	This method lets the user register himself/herself for a new farmer status, which costs ObjectCoins depending on the type of
     * 	status the player wishes to register. You cannot jump status and the method updates the registerCounter so that the program
     * 	disables the function once the player reaches maximum level.
     * 	@param player
     */
    public void registerFarmer(Farmer player){
		/* 	Register the farmer (player) depending on the type of farmer status given,
			If the player does not have enough ObjectCoins, terminate the registration. */
        switch (player.getRegisterCounter()){
            case 0 -> {
                if (player.getFarmerLevel() >= 5) {
                    if (player.getObjectCoins() >= 200) {
                        player.deductObjectCoin(200);
                        player.setType(FarmerType.REGISTERED);
                        player.setRegisterCounter(1);
                        player.setRegisterable(false);
                        registerSuccess();
                    } else {
                        registerFailed();
                    }
                }
            }
            case 1 -> {
                if (player.getFarmerLevel() >= 10) {
                    if (player.getObjectCoins() >= 300) {
                        player.deductObjectCoin(300);
                        player.setType(FarmerType.DISTINGUISHED);
                        player.setRegisterCounter(2);
                        player.setRegisterable(false);
                        registerSuccess();
                    } else {
                        registerFailed();
                    }
                }
            }
            case 2 -> {
                if (player.getFarmerLevel() >= 15){
                    if (player.getObjectCoins() >= 400) {
                        player.deductObjectCoin(400);
                        player.setType(FarmerType.LEGENDARY);
                        player.setRegisterCounter(3);
                        player.setRegisterable(false);
                        registerSuccess();
                    } else {
                        registerFailed();
                    }
                }
            }
            case 3 -> {
                System.out.println("You have reached maximum farmer status");
            }
            default -> System.out.println("\tRegistration Cancelled. Going back to MyFarm...");
        }
    }

    private void registerSuccess(){
        JOptionPane.showMessageDialog(
                null, "Registration Success!",
                " Farmer Registration",
                JOptionPane.INFORMATION_MESSAGE);
    }
    private void registerFailed(){
        JOptionPane.showMessageDialog(null,
                "Registration Failed! Insufficient ObjectCoins",
                " Farmer Registration", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("\tError! Insufficient ObjectCoins!");
    }
    /**	This multipurpose method checks the tileStatus of the tile and returns the boolean value depending on the inputted name
     * 	of the tool. Different farmer tools require different tile statuses to proceed & it helps in error handling and method
     * 	requirements as imposed in the Machine Project Specifications.
     *
     * 	@param plot
     * 	@param toolName
     * 	@returns status
     */
    public boolean checkTileStatus(HashMap<Integer, Tile> plot, String toolName){
        /* Returns TRUE if the condition(s) of using a tool is met before using it on a TILE */
        boolean status = false;
        switch (toolName){
            case "plant" :
                for(int i = 1; i <= plot.size(); i++){
                    if(plot.get(i).getStatus().equals(TileStatus.PLOWED)){
                        status = true;
                    }
                }
                break;
            case "harvest":
                for(int i = 1; i <= plot.size(); i++){
                    if(plot.get(i).getStatus().equals(TileStatus.PLANT) || plot.get(i).getStatus().equals(TileStatus.TREE)){
                        status = true;
                    }
                }
                break;
            case "water", "fertilize" :
                for(int i = 1; i <= plot.size(); i++){
                    if(plot.get(i).getStatus().equals(TileStatus.SEED)){
                        status = true;
                    }
                }
                break;
            case "pickaxe":
                for(int i = 1; i <= plot.size(); i++){
                    if(plot.get(i).getStatus().equals(TileStatus.ROCK)){
                        status = true;
                    }
                }
                break;
        }
        return status;
    }

    /**	This method determines that state of the game. It checks for conditions to determine whether the player cannot continue the game.
     * 	If the conditions are met, return isRunning with a boolean value of false and will let the program know to stop and terminate the
     * 	program within the main function.
     *
     * 	@param isRunning
     * 	@returns isRunning
     */
    public boolean checkGameConditions(boolean isRunning) {
        int condOneCounter = 0;
        int condTwoCounter = 0;
        boolean cond1 = false, cond2 = false;
        for(int i = 1; i <= plot.size(); i++){
            /* Condition 1.1: IF there are no more active and growing crops present in the farm plot */
            if(plot.get(i).getStatus().equals(TileStatus.PLANT) || plot.get(i).getStatus().equals(TileStatus.SEED) || plot.get(i).getStatus().equals(TileStatus.TREE)){
                condOneCounter++;
            }
            /* Condition 1.2: IF the farmer has no more ObjectCoins to buy new seeds and IF the farmer has no more seeds to plant */
            if(player.getInventory().size() == 0 && player.getObjectCoins() == 0){
                cond2 = true;
            }

            /* Condition 2: IF all available tiles are all occupied with WITHERED PLANTS */
            if(plot.get(i).getStatus().equals(TileStatus.WITHERED)){
                condTwoCounter++;
            }
            if(plot.get(i).getStatus().equals(TileStatus.ROCK)){
                condTwoCounter++;
            }
        }
        /* If Conditions are met, depending on the type of end-game condition, return value to false, else return true */
        if(condOneCounter == 0){
            cond1 = true;
            System.out.println("First Condition Met");
        }
        if(condTwoCounter == 50){
            isRunning = false;
            System.out.println("Second Condition Met");
        }
        else if(cond1 && cond2){
            isRunning = false;
            System.out.println("Both First Conditions Met");
        }
        return isRunning;
    }

    /* Needed Getters */
    public boolean isRunning() {
        return isRunning;
    }
}
