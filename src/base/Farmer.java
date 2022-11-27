package base;

import java.util.ArrayList;
import java.util.HashMap;

import static base.Application.input;
import static base.Application.player;

public class Farmer {
    private FarmerType type;
    private double experience;
    private int farmerLevel;
    private int objectCoins;
    private final ArrayList<Crop> inventory = new ArrayList<>();


    public Farmer(FarmerType type){
        this.type = type;
        this.farmerLevel = 0;
        this.experience = 1000.0;
        this.objectCoins = 10000;
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

    public int getObjectCoins() {
        return objectCoins;
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

    public void addObjectCoin(int objectCoins) {
        this.objectCoins += objectCoins;
    }

    public void deductObjectCoin(int objectCoins){
        this.objectCoins -= objectCoins;
    }

    public void addSeedToInventory(Crop seed){
        this.inventory.add(seed);
    }
    public void removeSeedFromInventory(Crop seed){
        this.inventory.remove(seed);
    }

    public void plantSeed(HashMap<Integer, Tile> plot, int index, Crop seed){
        Crop newCrop = Crop.newCrop(seed);
        plot.get(index).setPlantedCrop(newCrop);
        plot.get(index).setStatus(TileStatus.SEED);
        removeSeedFromInventory(seed);
    }
    public void plowTool(HashMap<Integer, Tile> plot, ArrayList<Tool> toolList, int index){
        if(plot.get(index).getStatus().equals(TileStatus.UNPLOWED)){
            //Set Tile Status of selected tile into PlOWED
            plot.get(index).setStatus(TileStatus.PLOWED);
            addExperience(toolList.get(0).getExpGain());
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
            int maxWaterCount =  plot.get(index).getPlantedCrop().getWaterNeeded() + plot.get(index).getPlantedCrop().getWaterBonus();
            if(plot.get(index).getPlantedCrop().getWater() < maxWaterCount) {
                plot.get(index).getPlantedCrop().setWater(1);
                plot.get(index).getPlantedCrop().setWatered(true);
                addExperience(toolList.get(1).getExpGain());
                System.out.println("\tYou have successfully watered the crop.");
            } else {
                plot.get(index).getPlantedCrop().setWater(1);
                plot.get(index).getPlantedCrop().setWatered(true);
                System.out.println("\tYou have successfully watered the crop.\n\t" +
                        "You have reached the maximum water limit for watering this Crop. No experienced is gained throughout the process");
            }

        } else {
            System.out.println("\tError! You can only water tiles that are occupied by a growing seed");
        }
        /* Implement Water Bonus Limit Later on */
    }

public void fertilizerTool(HashMap<Integer, Tile> plot, ArrayList<Tool> toolList, int index){
        /* Validation 1: Check if the player has enough money */
        if(this.objectCoins >= 10) {
            /* Validation 2: Check if the chosen tile index is a SEED */
            if (plot.get(index).getStatus().equals(TileStatus.SEED)) {
                deductObjectCoin(toolList.get(2).getCost());
                int maxFertilizerCount = plot.get(index).getPlantedCrop().getFertilizerNeeded() + plot.get(index).getPlantedCrop().getFertilizerBonus();
                if (plot.get(index).getPlantedCrop().getFertilizer() < maxFertilizerCount) {
                    plot.get(index).getPlantedCrop().setFertilizer(1);
                    plot.get(index).getPlantedCrop().setFertilized(true);
                    addExperience(toolList.get(2).getExpGain());
                    System.out.println("\tYou have successfully fertilized the crop.");
                } else {
                    plot.get(index).getPlantedCrop().setFertilizer(1);
                    plot.get(index).getPlantedCrop().setFertilized(true);
                    System.out.println("\tYou have successfully fertilized the crop.\n\t" +
                            "You have reached the maximum  limit for fertilizing this Crop. No experienced is gained throughout the process");
                }

            } else {
                System.out.println("\tError! You can only fertilize tiles that are occupied by a growing seed");
            }
            /* Implement Fertilizer Bonus Limit Later on */
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
                System.out.println("\tYou have successfully removed a Rock! Tile " + plot.get(index).getPosition() + " is now accessible.");
            }
            else{
                System.out.println("\tError! You have selected an invalid Tile.");
            }
        }
        else{
            System.out.println("\tError! You do not have any ObjectCoins to Fertilize a Plant.");
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
                        "Player has gained 2 Exp and used 7 ObjectCoins.");
            }
            else if(plot.get(index).getStatus().equals(TileStatus.PLANT)){
                deductObjectCoin(toolList.get(4).getCost());
                plot.replace(index, new Tile(index, null, TileStatus.UNPLOWED));
                addExperience(toolList.get(4).getExpGain());
                System.out.println("\tYou have successfully removed a Plant.\n\t" +
                        "Player has gained 2 exp and used 7 ObjectCoins.");
            }
            else if(plot.get(index).getStatus().equals(TileStatus.SEED)){
                deductObjectCoin(toolList.get(4).getCost());
                plot.replace(index, new Tile(index, null, TileStatus.UNPLOWED));
                addExperience(toolList.get(4).getExpGain());
                System.out.println("\tYou have successfully removed a growing Seed.\n\t" +
                        "Player has gained 2 exp and used 7 ObjectCoins.");

            }
            else if(plot.get(index).getStatus().equals(TileStatus.UNPLOWED) || plot.get(index).getStatus().equals(TileStatus.ROCK) || plot.get(index).getStatus().equals(TileStatus.PLOWED)){
                deductObjectCoin(toolList.get(4).getCost());
                addExperience(toolList.get(4).getExpGain());
                System.out.println("\tYou have used a shovel... Nothing Happened...\n\t" +
                        "Player has gained 2 exp and used 7 ObjectCoins.");
            }
            else{
                System.out.println("\tError! You have selected an invalid Tile.");
            }
        }
        else{
            System.out.println("\tError! You do not have any ObjectCoins to Fertilize a Plant.");
        }
    }

    public void harvestPlant(Farmer player, HashMap<Integer, Tile> plot, int index) {

        //Get Product Produce
        int earnBonus = 0; //To be implemented later on
        int minProduce = plot.get(index).getPlantedCrop().getMinProduce();
        int maxProduce = plot.get(index).getPlantedCrop().getMaxProduce();
        int productsProduced = plot.get(index).getPlantedCrop().productProduce(minProduce, maxProduce);
        //Sell and get ObjectCoin, get exp
        int harvestTotal = productsProduced * ( plot.get(index).getPlantedCrop().getSellPrice() + earnBonus);
        double totalExpGain = plot.get(index).getPlantedCrop().getExpYield() * productsProduced;
        player.addObjectCoin(harvestTotal);
        player.addExperience(totalExpGain);
        // Display appropriate message based from the report [1 POINT]
        System.out.println( "\tCongratulations!\n\tYou have harvested & sold " + productsProduced + " " + plot.get(index).getPlantedCrop().getName() + " Crops!" +
                            "\n\tPlayer has received " + harvestTotal + " ObjectCoins and earned " + totalExpGain + " experience.");
        //Remove crop from tile and Set to unplowed
        plot.replace(index, new Tile(index, null, TileStatus.UNPLOWED));
        /* To be implemented Later */
        // Implement FinalHarvestPrice for different farmerLevel, bonusLimit, etc.
    }

    public void levelUP(Farmer player){
        switch (player.getFarmerLevel()){
            case 5 -> {
                System.out.println("\tYou are now eligible to promote to a 'Registered Farmer'.\n" +
                        "\tWould you like to promote to a 'Registered Farmer' for 200 ObjectCoins");
            }
            case 10 ->{
                System.out.println("\tYou are now eligible to promote to a 'Distinguished Farmer'.\n" +
                        "\tWould you like to promote to a 'Distinguished Farmer' for 300 ObjectCoins");
            }
            case 15 ->{
                System.out.println("\tYou are now eligible to promote to a 'Legendary Farmer'.\n" +
                        "\tWould you like to promote to a 'Legendary Farmer' for 400 ObjectCoins");
            }
        }
        var cmd = input.nextLine();
        switch (cmd){
            case "Y", "y" ->{
                //DO something
            }
            case "N", "n" -> {
                //Do something
            }
        }
    }
    public void printFarmerInfo(Farmer player){
        System.out.println("\n\t[" + player.getType()+ " FARMER]" + "\n" +
                "\tLvl: " + player.getFarmerLevel() +  " | Experience: " + player.getExperience() + "\n" +
                "\tCoins: " + player.getObjectCoins() + "\n");
    }


}
