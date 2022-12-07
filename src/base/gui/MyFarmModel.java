package base.gui;
import base.myFarm.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MyFarmModel {

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
            new Tool("Plow Tool", 0, 0.5),
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
                            System.out.println("Plant is Harvestable");
                            plot.get(i).setStatus(TileStatus.PLANT);
                        }
                        /* If the selected Harvestable Plant's Fertilizer requirement is 0, but it overrides due to fertilizer bonuses, run this line of code */
                        if(plot.get(i).getPlantedCrop().getFertilizerBonus() > 0){
                            System.out.println("Plant is Harvestable");
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
                            System.out.println("Plant is Harvestable");
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




}
