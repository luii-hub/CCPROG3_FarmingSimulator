package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import static base.Application.input;
import static base.Application.player;

public class Farmer {
    private Stats FarmerStats;
    private FarmerType type;
    private double experience;
    private int farmerLevel;
    private double objectCoins;
    private boolean isRegisterable;
    private int registerCounter;
    private final ArrayList<Crop> inventory = new ArrayList<>();


    public Farmer(FarmerType type){
        FarmerStats = new Stats();
        this.type = type;
        this.farmerLevel = 0;
        this.experience = 0.0;
        this.objectCoins = 100;
        this.isRegisterable = false;
        this.registerCounter = 0;
    }

    public Stats getFarmerStats() {
        return FarmerStats;
    }
    public FarmerType getType() {
        return type;
    }

    public int getFarmerLevel() {
        return farmerLevel;
    }

    public double getExperience() {
        return experience;
    }

    public double getObjectCoins() {
        return objectCoins;
    }

    public boolean isRegisterable() {
        return isRegisterable;
    }

    public int getRegisterCounter() {
        return registerCounter;
    }

    public ArrayList<Crop> getInventory() {
        return inventory;
    }

    public void setFarmerLevel(int farmerLevel) {
        this.farmerLevel = farmerLevel;
    }

    public void addExperience(double experience) {
        this.experience += experience;
    }

    public void setType(FarmerType type) {
        this.type = type;
    }

    public void addObjectCoin(double objectCoins) {
        this.objectCoins += objectCoins;
    }

    public void deductObjectCoin(double objectCoins){
        this.objectCoins -= objectCoins;
    }

    public void addSeedToInventory(Crop seed){
        this.inventory.add(seed);
    }
    public void removeSeedFromInventory(Crop seed){
        this.inventory.remove(seed);
    }
    public void setRegisterable(boolean registerable) {
        isRegisterable = registerable;

    }

    public void setRegisterCounter(int registerCounter) {
        this.registerCounter = registerCounter;
    }

    public void buySeed(int index, ArrayList<Crop> seedList){
        index--;
        int count;
        /* Print the list of Seeds that are available for Purchase */
        System.out.println(seedList.get(index).toString());
        /* Ask user the N amount of Seeds for Purchase */
        System.out.println("\n\tHow many will you purchase? (Max 5)");
        /* Error Checking for user-input, if conditions are met, buySeed */
        try {
            count = input.nextInt();
            if (count > 0 && count <= 5) {
                int pricePerCount = count * seedList.get(index).getBuyCost();
                if (getObjectCoins() == pricePerCount && count == 1) {
                    /* If user buys EXACTLY one seed, execute this line of Code */
                    for (int i = 0; i < count; i++) {
                        addSeedToInventory(seedList.get(index));
                        deductObjectCoin((seedList.get(index).getBuyCost() - getType().getSeedCostReduction()));
                        getFarmerStats().addTimesBoughtSeeds();
                    }
                    System.out.printf("\tSuccessfully purchased %d %s Seed!\n", count, seedList.get(index).getName());

                } else if (getObjectCoins() > pricePerCount) {
                    /* If user buys MORE THAN one seed, execute this line of Code */
                    for (int i = 0; i < count; i++) {
                        addSeedToInventory(seedList.get(index));
                        deductObjectCoin((seedList.get(index).getBuyCost() - getType().getSeedCostReduction()));
                        getFarmerStats().addTimesBoughtSeeds();
                    }
                    System.out.printf("\tSuccessfully purchased %d %s Seed(s)!\n", count, seedList.get(index).getName());
                } else {
                    System.out.println("\tError! You do not have enough ObjectCoins for purchasing the selected amount");
                }
            } else {
                System.out.println("\tError! Invalid Input.");
            }
        }
        catch (InputMismatchException ex) {
            System.out.println("\tInvalid Input!");
        }
    }
    public void plantSeed(HashMap<Integer, Tile> plot, int index, Crop seed){
        Crop newCrop = Crop.newCrop(seed);
        plot.get(index).setPlantedCrop(newCrop);
        plot.get(index).setStatus(TileStatus.SEED);
        System.out.println("\tYou have successfully planted a " + newCrop.getName() + "!");
        removeSeedFromInventory(seed);
    }
    public void plowTool(HashMap<Integer, Tile> plot, ArrayList<Tool> toolList, int index){
        if(plot.get(index).getStatus().equals(TileStatus.UNPLOWED)){
            //Set Tile Status of selected tile into PlOWED
            plot.get(index).setStatus(TileStatus.PLOWED);
            addExperience(toolList.get(0).getExpGain());
            System.out.println("\tYou have successfully plowed a Tile! Player has gained 0.5 Experience.");
            getFarmerStats().addTimesPlowed();
        }
        else{
            if(plot.get(index).getStatus().equals(TileStatus.PLOWED)){
                System.out.println("\tError! This tile is already Plowed.");
            } else if (plot.get(index).getStatus().equals(TileStatus.WITHERED)){
                System.out.println( "\tThis tile cannot be plowed since it is currently being occupied by a Withered Plant.\n" +
                                    "\tPerhaps I could use a different tool to remove the Withered Plant.");
            } else {
                System.out.println("\tError! Plow Tool exception only works on Unplowed Tiles");
            }
        }
    }

    public void waterTool(HashMap<Integer, Tile> plot, ArrayList<Tool> toolList, int index){
        if(plot.get(index).getStatus().equals(TileStatus.SEED)){
            int maxWaterCount =  plot.get(index).getPlantedCrop().getWaterNeeded() + plot.get(index).getPlantedCrop().getWaterBonus() + getType().getWaterBonusIncrease();
            if(plot.get(index).getPlantedCrop().getWater() < maxWaterCount) {
                plot.get(index).getPlantedCrop().setWater(1);
                plot.get(index).getPlantedCrop().setWatered(true);
                addExperience(toolList.get(1).getExpGain());
                System.out.println("\tYou have successfully watered the crop! Player has gained 0.5 Experience.");
            } else {
                plot.get(index).getPlantedCrop().setWatered(true);
                System.out.println("\tYou have successfully watered the crop.\n\t" +
                        "You have reached the maximum water limit for watering this Crop. No experienced is gained throughout the process");
            }
            getFarmerStats().addTimesWatered();
        } else {
            System.out.println("\tError! You can only water tiles that are occupied by a growing seed");
        }

    }

public void fertilizerTool(HashMap<Integer, Tile> plot, ArrayList<Tool> toolList, int index){
        /* Validation 1: Check if the player has enough money */
        if(this.objectCoins >= 10) {
            /* Validation 2: Check if the chosen tile index is a SEED */
            if (plot.get(index).getStatus().equals(TileStatus.SEED)) {
                deductObjectCoin(toolList.get(2).getCost());
                int maxFertilizerCount = plot.get(index).getPlantedCrop().getFertilizerNeeded() + plot.get(index).getPlantedCrop().getFertilizerBonus() + getType().getFertilizerBonusIncrease();
                if (plot.get(index).getPlantedCrop().getFertilizer() < maxFertilizerCount) {
                    plot.get(index).getPlantedCrop().setFertilizer(1);
                    plot.get(index).getPlantedCrop().setFertilized(true);
                    addExperience(toolList.get(2).getExpGain());
                    System.out.println("\tYou have successfully fertilized the crop! Player has gained 4.0 Experience.");
                } else {
                    plot.get(index).getPlantedCrop().setFertilized(true);
                    System.out.println("\tYou have successfully fertilized the crop.\n\t" +
                            "You have reached the maximum  limit for fertilizing this Crop. No experienced is gained throughout the process");
                }

            } else {
                System.out.println("\tError! You can only fertilize tiles that are occupied by a growing seed");
            }
            getFarmerStats().addTimesFertilized();
        }
        else{
            System.out.println("\tError! You do not have any ObjectCoins to Fertilize a Plant.");
        }
    }

    public void pickaxeTool(HashMap<Integer, Tile> plot, ArrayList<Tool> toolList, int index) {
        /* Validation 1: Check if player has enough money to use Pickaxe Tool */
        if(this.objectCoins >= 50) {
            /* Validation 2: Check if the chosen tile index is a ROCK */
            if (plot.get(index).getStatus().equals(TileStatus.ROCK)) {
                deductObjectCoin(toolList.get(3).getCost());
                plot.get(index).setStatus(TileStatus.UNPLOWED);
                addExperience(toolList.get(3).getExpGain());
                System.out.println( "\tYou have successfully removed a Rock! Tile " + plot.get(index).getPosition() + " is now accessible. \n" +
                                    "\tPlayer has gained 15.0 Experience and used 50 Objections.");

            }
            else{
                System.out.println("\tError! You have selected an invalid Tile.");
            }
        }
        else{
            System.out.println("\tError! You do not have enough ObjectCoins to use this function.");
        }
    }

    public void shovelTool(HashMap<Integer, Tile> plot, ArrayList<Tool> toolList, int index) {
        /* Validation 1: Check if player has enough money to use Pickaxe Tool */
        if(this.objectCoins >= 7) {
            /* Validation 2: Check the chosen tile index's status; Different S.outs for each status */
            if (plot.get(index).getStatus().equals(TileStatus.WITHERED)) {
                deductObjectCoin(toolList.get(4).getCost());
                plot.get(index).setStatus(TileStatus.UNPLOWED);
                addExperience(toolList.get(4).getExpGain());
                System.out.println("\tYou have successfully removed a Withered Plant.\n\t" +
                        "Player has gained 2.0 Experience and used 7 ObjectCoins.");
            }
            else if(plot.get(index).getStatus().equals(TileStatus.PLANT)){
                deductObjectCoin(toolList.get(4).getCost());
                plot.replace(index, new Tile(index, null, TileStatus.UNPLOWED));
                addExperience(toolList.get(4).getExpGain());
                System.out.println("\tYou have successfully removed a Plant.\n\t" +
                        "Player has gained 2.0 Experience and used 7 ObjectCoins.");
            }
            else if(plot.get(index).getStatus().equals(TileStatus.SEED)){
                deductObjectCoin(toolList.get(4).getCost());
                plot.replace(index, new Tile(index, null, TileStatus.UNPLOWED));
                addExperience(toolList.get(4).getExpGain());
                System.out.println("\tYou have successfully removed a growing Seed.\n\t" +
                        "Player has gained 2.0 Experience and used 7 ObjectCoins.");

            }
            else if(plot.get(index).getStatus().equals(TileStatus.UNPLOWED) || plot.get(index).getStatus().equals(TileStatus.ROCK) || plot.get(index).getStatus().equals(TileStatus.PLOWED)){
                deductObjectCoin(toolList.get(4).getCost());
                addExperience(toolList.get(4).getExpGain());
                System.out.println("\tYou have used a shovel... Nothing Happened...\n\t" +
                        "Player has gained 2.0 Experience and used 7 ObjectCoins.");
            }
            else{
                System.out.println("\tError! You have selected an invalid Tile.");
            }
        }
        else{
            System.out.println("\tError! You do not have enough ObjectCoins to use this function.");
        }
    }

    public void harvestPlant(Farmer player, HashMap<Integer, Tile> plot, int index) {

        //Get Product Produce
        int earnBonus = player.getType().getBonusEarnings();
        int minProduce = plot.get(index).getPlantedCrop().getMinProduce();
        int maxProduce = plot.get(index).getPlantedCrop().getMaxProduce();
        int productsProduced = plot.get(index).getPlantedCrop().productProduce(minProduce, maxProduce);

        //Harvest Total and CropBonuses
        double harvestTotal = productsProduced * (plot.get(index).getPlantedCrop().getSellPrice() + earnBonus);
        double harvestWaterBonus = (harvestTotal * 0.2 * (plot.get(index).getPlantedCrop().getWater() - 1));
        double harvestFertilizerBonus = (harvestTotal * 0.5 * (plot.get(index).getPlantedCrop().getFertilizer()));
        double finalHarvestPrice = harvestTotal + harvestWaterBonus + harvestFertilizerBonus;
        double totalExpGain = plot.get(index).getPlantedCrop().getExpYield() * productsProduced;

        //Sell and get ObjectCoin, get exp
        if(plot.get(index).getPlantedCrop().getType().equals(CropType.FLOWER)){
            finalHarvestPrice = finalHarvestPrice * 1.1;
        }
        player.addObjectCoin(finalHarvestPrice);
        player.addExperience(totalExpGain);
        //BUG: Fix crop's water and fertilizer Cap

        // Display appropriate message based from the report [1 POINT]
        System.out.println("\tTotal Harvest Price: " + harvestTotal);
        System.out.println("\tBonus ObjectCoins from Watering: " + harvestWaterBonus);
        System.out.println("\tBonus ObjectCoins from Fertilizing: " + harvestFertilizerBonus);
        System.out.println( "\tCongratulations!\n\tYou have harvested & sold " + productsProduced + " " + plot.get(index).getPlantedCrop().getName() + " Crops!" +
                            "\n\tPlayer has received " + finalHarvestPrice + " ObjectCoins and earned " + totalExpGain + " experience.");

        //Remove crop from tile and Set to unplowed
        plot.replace(index, new Tile(index, null, TileStatus.UNPLOWED));


        // Implement FinalHarvestPrice for different farmerLevel, bonusLimit, etc.
        getFarmerStats().addTimesHarvested();
    }

    public void printFarmerInfo(Farmer player){
        System.out.println("\n\t[" + player.getType().getFarmerType() + "]" + "\n" +
                "\tLvl: " + player.getFarmerLevel() +  " | Experience: " + player.getExperience() + "\n" +
                "\tCoins: " + player.getObjectCoins() + "\n");
    }


}
