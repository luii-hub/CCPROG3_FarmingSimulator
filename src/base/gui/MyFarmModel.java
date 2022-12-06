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




}
