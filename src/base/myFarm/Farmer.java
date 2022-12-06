package base.myFarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import static base.myFarm.Application.input;

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

    /* Required Getters */
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

    public void setRegisterable(boolean registerable) {
        isRegisterable = registerable;

    }
    public void setRegisterCounter(int registerCounter) {
        this.registerCounter = registerCounter;
    }

    /* Incrementation & Decremental Methods */
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

    /** This method lets the user buy the desired seed from the the seed shop (seedlist). The user can buy a maximum of
     *  five seeds per purchase. If the player does not have enough ObjectCoins to buy, this function will be restricted.
     *
     * @param index
     * @param seedList
     */
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

    /** This method lets the user plant the desired seed given the desired tile index inside the farmable plot. Player can
     *  only plant on unoccupied, unplowed tiles. The user has certain limitations when planting a certain seed (i.e. Fruit Tree).
     *
     *  @param plot
     *  @param index
     *  @param seed
     */
    public void plantSeed(HashMap<Integer, Tile> plot, int index, Crop seed){
        Crop newCrop = Crop.newCrop(seed);
        plot.get(index).setPlantedCrop(newCrop);
        plot.get(index).setStatus(TileStatus.SEED);
        System.out.println("\tYou have successfully planted a " + newCrop.getName() + "!");
        removeSeedFromInventory(seed);
    }

    /** This method lets the user set a tile's status to PLOWED. Lets the user plant a seed after plowing. It updates the TileStatus of
     *  the given tile index once certain conditions are met.
     *  @param plot
     *  @param toolList
     *  @param index
     */
    public void plowTool(HashMap<Integer, Tile> plot, ArrayList<Tool> toolList, int index){
        /* If selected tile is unplowed, proceed to execute method, else reject the user's command to plow the tile. */
        if(plot.get(index).getStatus().equals(TileStatus.UNPLOWED)){
            //Set Tile Status of selected tile into PlOWED
            plot.get(index).setStatus(TileStatus.PLOWED);
            addExperience(toolList.get(0).getExpGain());
            System.out.println("\tYou have successfully plowed a Tile! Player has gained 0.5 Experience.");
            getFarmerStats().addTimesPlowed();
        }
        else{
            /* If tile is already plowed or the tile's stauts is !UNPLOWED, there is no need to execute this method */
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

    /** This method lets the user water the SEED given the tile index. It updates the Crop's (seed) attributes and updates the user
     *  via feedback to let the user know if the given command was successful or not.
     *  @param plot
     *  @param toolList
     *  @param index
     */
    public void waterTool(HashMap<Integer, Tile> plot, ArrayList<Tool> toolList, int index){
        /* If the tile's status is a seed, proceed to watering the tile (crop), else do not execute */
        if(plot.get(index).getStatus().equals(TileStatus.SEED)){
            int maxWaterCount =  plot.get(index).getPlantedCrop().getWaterNeeded() + plot.get(index).getPlantedCrop().getWaterBonus() + getType().getWaterBonusIncrease();
            /* Water the Crop, Update the Crop's Attributes, If the crop's water requirement reaches a certain threshold, disable exp incrementation */
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

    /** This method lets the user water the SEED given the tile index. It updates the Crop's (seed) attributes and updates the user
     *  via feedback to let the user know if the given command was successful or not.
     *
     * @param plot
     * @param toolList
     * @param index
     */
    public void fertilizerTool(HashMap<Integer, Tile> plot, ArrayList<Tool> toolList, int index){
        /* Validation 1: Check if the player has enough ObjectCoins to fertilize a Seed */
        if(this.objectCoins >= 10) {
            /* Validation 2: Check if the chosen tile index contains a SEED */
            if (plot.get(index).getStatus().equals(TileStatus.SEED)) {
                deductObjectCoin(toolList.get(2).getCost());
                int maxFertilizerCount = plot.get(index).getPlantedCrop().getFertilizerNeeded() + plot.get(index).getPlantedCrop().getFertilizerBonus() + getType().getFertilizerBonusIncrease();
                /* Fertilize the Crop, Update the Crop's Attributes, If the crop's fertilize requirement reaches a certain threshold, disable exp incrementation */
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

    /** This method lets the user removes a ROCK given the tile index. It lets the user remove the rock so that the tile
     *  will be accessible to the user for future purposes.
     *
     *  @param plot
     *  @param toolList
     *  @param index
     */
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

    /** This method lets the user use the shovel tool and remove any given object (Plant) except a Rock.
     *  Similar to the PickAxe Tool, it also lets the user access the tile for future purposes.
     *
     *  @param plot
     *  @param toolList
     *  @param index
     */
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

    /** This method lets the user harvest a plant once conditions are met (in main function). This method
     *  generates the number of crop(s) harvested, the amount of experience gained from the harvest, and computes
     *  the final acquisition of ObjectCoins gained from the harvest. Bonuses are applied given the farmer's type
     *  of status.
     *
     * @param player
     * @param plot
     * @param index
     */
    public void harvestPlant(Farmer player, HashMap<Integer, Tile> plot, int index) {

        /* Get the Product Produce of the crop */
        int earnBonus = player.getType().getBonusEarnings();
        int minProduce = plot.get(index).getPlantedCrop().getMinProduce();
        int maxProduce = plot.get(index).getPlantedCrop().getMaxProduce();
        int productsProduced = plot.get(index).getPlantedCrop().productProduce(minProduce, maxProduce);

        /* Computation of Harvest Total and Additional Bonuses */
        double harvestTotal = productsProduced * (plot.get(index).getPlantedCrop().getSellPrice() + earnBonus);
        double harvestWaterBonus = (harvestTotal * 0.2 * (plot.get(index).getPlantedCrop().getWater() - 1));
        double harvestFertilizerBonus = (harvestTotal * 0.5 * (plot.get(index).getPlantedCrop().getFertilizer()));
        double finalHarvestPrice = harvestTotal + harvestWaterBonus + harvestFertilizerBonus;
        double totalExpGain = plot.get(index).getPlantedCrop().getExpYield() * productsProduced;

        /* If the crop is a flower, it contains a small bonus for the final harvest price */
        if(plot.get(index).getPlantedCrop().getType().equals(CropType.FLOWER)){
            finalHarvestPrice = finalHarvestPrice * 1.1;
        }
        /* Player received the final amount of ObjectCoins gained and the amount of experience gained */
        player.addObjectCoin(finalHarvestPrice);
        player.addExperience(totalExpGain);

        /* Display appropriate message based from the report given */
        System.out.println("\tTotal Harvest Price: " + harvestTotal);
        System.out.println("\tBonus ObjectCoins from Watering: " + harvestWaterBonus);
        System.out.println("\tBonus ObjectCoins from Fertilizing: " + harvestFertilizerBonus);
        System.out.println( "\tCongratulations!\n\tYou have harvested & sold " + productsProduced + " " + plot.get(index).getPlantedCrop().getName() + " Crops!" +
                            "\n\tPlayer has received " + finalHarvestPrice + " ObjectCoins and earned " + totalExpGain + " experience.");

        /* Remove crop from the tile, set a newly fresh tile */
        plot.replace(index, new Tile(index, null, TileStatus.UNPLOWED));
        getFarmerStats().addTimesHarvested();
    }

    /** This method simply prints out and display the farmer's information (i.e. type, level, objectcoins
     *
     * @param player
     */
    public void printFarmerInfo(Farmer player){
        System.out.println("\n\t[" + player.getType().getFarmerType() + "]" + "\n" +
                "\tLvl: " + player.getFarmerLevel() +  " | Experience: " + player.getExperience() + "\n" +
                "\tCoins: " + player.getObjectCoins() + "\n");
    }


}
