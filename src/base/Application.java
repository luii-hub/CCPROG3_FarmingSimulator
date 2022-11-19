package base;

import java.util.*;
public class Application {
	
	// Input
	public static final Scanner input = new Scanner(System.in);

	// Game Initializations and Instantiations
	public static boolean isRunning = true;
	public static Farmer player = new Farmer(FarmerType.NOVICE);
	public static HashMap<Integer, Tile> plot = new HashMap<>();
	public static FarmPlot MyFarm = new FarmPlot(plot);

	
	public static final List<Crop> seedList = new ArrayList<Crop>(Arrays.asList(
		new Crop("Turnip", CropType.ROOT_CROP, 2, 1, 2, 0, 1, 5, 6, 1, 2, 5),
		new Crop("Carrot", CropType.ROOT_CROP, 3, 1, 2, 0, 1, 10, 9, 1, 2, 7.5),
		new Crop("Potato", CropType.ROOT_CROP, 5, 3, 4, 1, 2, 20, 3, 1, 10, 12.5),
		new Crop("Rose", CropType.FLOWER, 1, 1, 2, 0, 1, 5, 5, 1, 1, 2.5),
		new Crop("Tulip", CropType.FLOWER, 2, 2, 3, 0, 1, 10, 9, 1, 1, 5),
		new Crop("Sunflower", CropType.FLOWER, 2, 2, 3, 1, 2, 20, 19, 1, 1, 7.5),
		new Crop("Mango", CropType.FRUIT_TREE, 10, 7, 7, 4, 4, 100, 8, 5, 15, 25),
		new Crop("Apple", CropType.FRUIT_TREE, 10, 7, 7, 5, 5, 200, 5, 10, 15, 25)
	));

	public static final List<Tool> toolList = new ArrayList<Tool>(Arrays.asList(
			new Tool("Plow Tool", 0, 0.5),
			new Tool("Watering Can", 0, 0.5),
			new Tool("Fertilizer", 10, 4),
			new Tool("Pickaxe", 50, 15),
			new Tool("Shovel", 7, 2)
	));

	public static void main(String[] args) {
		/* Run Program until checkConditions Function returns false */
		while (isRunning) {
			cls();
			System.out.println("\n\t[My Farm] : Day " + MyFarm.getDaytime());
			/* Print Farmer (Player) Information */
			player.printFarmerInfo(player);

			/* Print the Farm Field */
			MyFarm.printFarmPlot(plot);

			/* Farmer HUD and Commands */
			printFarmerHUD();
			var cmd = input.nextLine();
			performCommand(cmd);
			updateFarmerLevel();
			checkGameConditions();
		}
		//printFinalState();
		input.close();
	}

	public static void printFarmerHUD() {
		/* Add some restrictions later on MC02 for the other Tools */
		System.out.printf("""
		\n\tFarmer Commands:												Inventory:
		\t[B] Buy Seed		[P] Plow		[F] Fertilize				(1) Turnip : %d
		\t[C] Plant Seed		[W] Water		[H] Harvest					(2) Carrot : %d
		\t[I] Inspect Tile	[S] Shovel 		[A] PickAxe					(3) Potato : %d
		\n\t[N] Next Day	[Q] Quit Game
							
		"""	, Collections.frequency(player.getInventory(), seedList.get(0))
		, Collections.frequency(player.getInventory(), seedList.get(1))
		, Collections.frequency(player.getInventory(), seedList.get(2)));
	}
	
	public static void performCommand(String cmd) {
		/* Function where the program will 'perform' the command that the user wants */
		int index;
		switch (cmd) {
			case "B" -> { /* Buy Seed Method */
				if (player.getObjectCoins() <= 0) {
					System.out.println("\tError! You do not have anymore ObjectCoins. \n\tHint: Get Rich");
				} else {
					System.out.print("""
								Which Seed do you want to purchase?
								[Root Crops]	[Flower Seed]	[Fruit Tree]
								[1] Turnip		[4] Rose		[7] Apple
								[2] Carrot		[5] Tulip		[8] Mango
								[3] Potato		[6] Sunflower	[9] Exit
							""");
					try {
						index = input.nextInt();
						if (index < 9 && index > 0) {
							buySeed(index, player);
						} else {
							if(index == 10){
								System.out.println("\tGoing Back...");
							}
							else {
								System.out.println("\tUnknown Command.");
							}
						}
					} catch (InputMismatchException ex) {
						System.out.println("\tInvalid Input!");

					}
				}
				keyContinue();
			}
			case "I" -> { /* Inspect Tile and see its contents */
				System.out.println("\tWhich Tile do you want to inspect? ");
				try {
					index = input.nextInt();
					if (index > 0 && index <= 50) {
						inspectTile(index);
					} else {
						System.out.println("\tInvalid Range");
					}
				} catch (InputMismatchException ex) {
					System.out.println("\tInvalid Input!");

				}
				keyContinue();
			}
			case "C" -> { /* Plant Seed Method */
				if (player.getInventory().size() == 0) {
					System.out.println("\tYou don't have access to Plant Seed since you have not existing Seeds present in your Inventory!");
					System.out.println("\tHint: Buy Seeds from the Seed Shop.");
				} else {
					//Check if Tile is Plowed, Else print an error statement
					if (checkTileStatus(plot, "plow")) {
						System.out.println("\tSelect a Plowed Tile to Plant: ");
						try {
							index = input.nextInt();
							//Check if selected tile in within the range
							if (index > 0 && index <= 50) {
								//Check if selected tile has a different tile status, else proceed to plant
								if (plot.get(index).getStatus().equals(TileStatus.SEED)) {
									System.out.println("\tError! The tile that you are accessing is currently occupied.");

								} else if (plot.get(index).getStatus().equals(TileStatus.PLOWED)) {
									// Display crop options from seedList
									for (int i = 0; i < seedList.size(); i++) {
										int inputIndex = i + 1;
										if (Collections.frequency(player.getInventory(), seedList.get(i)) != 0) {
											System.out.println("\t[" + inputIndex + "] " + seedList.get(i).getName() + " Seed(s): " + Collections.frequency(player.getInventory(), seedList.get(i)));
											seedList.get(i).setChoosable(true);
										}
									}
									// Ask which crop & Select Seed to plant
									System.out.println("\n\tSelect a Seed: ");
									int seedIndex;
									do {
										do {
											seedIndex = input.nextInt();
										}while (seedIndex <= 0 || seedIndex > seedList.size());
										seedIndex--;
									}while (!seedList.get(seedIndex).isChoosable());
									/* Reset crop options for seed list */
									for (Crop crop : seedList) {
										crop.setChoosable(false);
									}
									/* Plant new Instance of Seed (Crop), Set Tile Status to SEED, Update Farmer Inventory */
									player.plantSeed(plot, index, seedList.get(seedIndex));
								} else {
									System.out.println("\tError! You have selected an Unplowed Tile.");
								}
							} else {
								System.out.println("\tError! Tile Index is out of bounds. ");
							}
						} catch (InputMismatchException ex) {
							System.out.println("\tInvalid Input!");
						}
					} else {
						System.out.println("\tYou do not have any Plowed Tiles present! You can only plant once a tile is plowed");
						System.out.println("\tHint: Use the Plow Tool to cultivate a tile");
					}

				}
				keyContinue();
			}
			case "P" -> { /* Plow Tile Method */
				System.out.println("\tWhich tile do you want to Plow?");
				/* Error Checking for user-input, if conditions are met, Plow Tile */
				try {
					index = input.nextInt();
					if (index > 0 && index <= 50) {
						player.plowTool(plot, (ArrayList<Tool>) toolList, index);

					} else {
						System.out.println("\tError! Tile Index is out of bounds.");
					}
				} catch (InputMismatchException ex) {
					System.out.println("\tInvalid Input!");
				}
				keyContinue();
			}
			case "W" -> { /* WaterCan Tool Method */
				if (checkTileStatus(plot, "water")) {
					/* Error Checking for user-input, if conditions are met, Water Crop/Tile */
					try {
						System.out.println("\tWhich tile do you want to Water?");
						index = input.nextInt();
						if (index > 0 && index <= 50) {
							/* Water the Crop ONCE per day, Update the Crop's Stats */
							player.waterTool(plot, (ArrayList<Tool>) toolList, index);

						} else {
							System.out.println("\tError! Tile Index is out of bounds.");
						}
					} catch (InputMismatchException ex) {
						System.out.println("\tInvalid Input!");
					}
				}
				else {
					System.out.println("\tError! You can only use the Water Can on Plowed Tiles with a growing Crop (Seed).");
				}
				keyContinue();
			}
			case "F" -> { /* Fertilizer Tool Method */
				if (checkTileStatus(plot, "fertilize")) {
					/* Error Checking for user-input, if conditions are met, Water Crop/Tile */
					try {
						System.out.println("\tWhich tile do you want to Fertilize?");
						index = input.nextInt();
						if (index > 0 && index <= 50) {
							/* Fertilize the Crop ONCE per day, Update the Crop's Stats */
							player.fertilizerTool(plot, (ArrayList<Tool>) toolList, index);

						} else {
							System.out.println("\tError! Tile Index is out of bounds.");
						}
					} catch (InputMismatchException ex) {
						System.out.println("\tInvalid Input!");
					}
				}
				else {
					System.out.println("\tError! You can only use Fertilizer on Plowed Tiles with a growing Crop (Seed).");
				}
				keyContinue();
			}
			case "H" -> { /* Harvest Plant Method */
				if (checkTileStatus(plot, "harvest")) {
					/* Error Checking for user-input, if conditions are met, Water Crop/Tile */
					System.out.println("\tWhich plant do you want to harvest?");
					try {
						index = input.nextInt();
						if (index > 0 && index <= 50) {
							/* Harvest Crop(s) from Tile, Update Tile Status to Unplowed, Update Farmer ObjectCoin(s) and Experience */
							player.harvestPlant(player, plot, index);
						} else {
							System.out.println("\tError! Tile Index is out of bounds");
						}
					} catch (InputMismatchException ex) {
						System.out.println("\tInvalid Input!");
					}
				} else {
					System.out.println("\tError! You cannot access this feature unless you have a Plant.");
				}
				keyContinue();
			}
			case "S" -> { /* Shovel Tool Method */
				/* Error Checking for user-input, if conditions are met, Water Crop/Tile */
				try {
					System.out.println("\tWhich tile do you want to shovel?");
					index = input.nextInt();
					if (index > 0 && index <= 50) {
						/* Execute ShovelTool Method, Update the Crop's Stats */
						player.shovelTool(plot, (ArrayList<Tool>) toolList, index);

					} else {
						System.out.println("\tError! Tile Index is out of bounds.");
					}
				} catch (InputMismatchException ex) {
					System.out.println("\tInvalid Input!");
				}
				keyContinue();
			}
			case "A" -> { /* Pickaxe Tool Method */
				if (checkTileStatus(plot, "pickaxe")) {
					/* Error Checking for user-input, if conditions are met, Water Crop/Tile */
					System.out.println("\tWhich plant do you want to harvest?");
					try {
						index = input.nextInt();
						if (index > 0 && index <= 50) {
							/* Harvest Crop(s) from Tile, Update Tile Status to Unplowed, Update Farmer ObjectCoin(s) and Experience */
							player.pickaxeTool(plot, (ArrayList<Tool>) toolList, index);
						} else {
							System.out.println("\tError! Tile Index is out of bounds");
						}
					} catch (InputMismatchException ex) {
						System.out.println("\tInvalid Input!");
					}
				} else {
					System.out.println("\tError! You can only use this tool on a Rock.");
				}
				keyContinue();
			}
			case "N" -> {/* Next Day Method */
				// Input CheckGameCondition Function
				nextDay(MyFarm);
			}
			case "Q" -> { /* Quit Game */
				isRunning = false;
				System.out.println("\tExiting the game");

			}
			/* Print Statistics on Quit Game to be Implemented on Main Function */
		}
	}

	public static void checkGameConditions() {
		int counter = 0;
		boolean cond1 = false, cond2 = false;
		for(int i = 0; i < plot.size(); i++){
			/* Condition 1.1: No Active/Growing Crops */
			if(plot.get(i).getStatus().equals(TileStatus.PLANT) || plot.get(i).getStatus().equals(TileStatus.PLANT)){
				cond1 = true;
			}
			/* Condition 1.2: No more ObjectCoins to buy new Seeds */
			if(player.getInventory().size() == 0 && player.getObjectCoins() == 0){
				cond2 = true;
			}
			
			/* Condition 2: When All Tiles are Withered */
			if(plot.get(i).getStatus().equals(TileStatus.WITHERED)){
				counter++;
			}

		}
		/* Force End the Game */
		if(counter == 50){
			isRunning = false;
		}
		else if(cond1 && cond2){
			isRunning = false;
		}
	}

	public static void printFinalStats () {
		// Display info about Tile and Stats
	}

	public static void buySeed(int index, Farmer player){
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
				if (player.getObjectCoins() == pricePerCount) {
					/* If user buys EXACTLY one seed, execute this line of Code */
					for (int i = 0; i < count; i++) {
						player.addSeedToInventory(seedList.get(index));
						player.deductObjectCoin(seedList.get(index).getBuyCost());
					}
					System.out.printf("\tSuccessfully purchased %d %s Seed!", count, seedList.get(index).getName());

				} else if (player.getObjectCoins() > pricePerCount) {
					/* If user buys MORE THAN one seed, execute this line of Code */
					for (int i = 0; i < count; i++) {
						player.addSeedToInventory(seedList.get(index));
						player.deductObjectCoin(seedList.get(index).getBuyCost());
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

	public static boolean checkTileStatus(HashMap<Integer, Tile> plot, String toolName){
		/* Returns TRUE if the condition(s) of using a tool is met before using it on a TILE */
		boolean status = false;
		switch (toolName){
			case "plow" :
				for(int i = 1; i <= plot.size(); i++){
					if(plot.get(i).getStatus().equals(TileStatus.PLOWED)){
						status = true;
					}
				}
				break;
			case "harvest":
				for(int i = 1; i <= plot.size(); i++){
					if(plot.get(i).getStatus().equals(TileStatus.PLANT)){
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

	public static void nextDay(FarmPlot MyFarm){
		MyFarm.setDaytime(1);
		for(int i = 1 ; i <= plot.size(); i++){
			/* Convert Harvestable PLANTS to WITHERED TileStatus */
			if(plot.get(i).getStatus().equals(TileStatus.PLANT)){
				plot.get(i).setStatus(TileStatus.WITHERED);
				//Also make withered plants unable to harvest, and watered
			}

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

				/* 	Convert the Seed (SEED) into a harvestable (PLANT) once the harvest conditions are met,
					If the conditions are not met within the harvest time, convert the seed into a WITHERED plant */
				if(plot.get(i).getPlantedCrop().getGrowTime() == plot.get(i).getPlantedCrop().getHarvestTime()){
					/* Convert Seed into Harvestable Plants/Withered Plants with FertilizerNeeded = 0 */
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
					}
					/* Convert Seed into Harvestable Plants/Withered Plants with FertilizerNeeded > 0 */
					else if(plot.get(i).getPlantedCrop().getFertilizerNeeded() > 0){
						if (plot.get(i).getPlantedCrop().getWater() < plot.get(i).getPlantedCrop().getWaterNeeded()
								&& plot.get(i).getPlantedCrop().getFertilizer() < plot.get(i).getPlantedCrop().getFertilizerNeeded()) {
							System.out.println("Plant is Withered");
							plot.get(i).setStatus(TileStatus.WITHERED);
						}
						else if(plot.get(i).getPlantedCrop().getWater() >= plot.get(i).getPlantedCrop().getWaterNeeded()
								&& plot.get(i).getPlantedCrop().getFertilizer() >= plot.get(i).getPlantedCrop().getFertilizerNeeded()) {
							System.out.println("Plant is Harvestable");
							plot.get(i).setStatus(TileStatus.PLANT);
						}
					}
				}
			}
		}
	}

	public static void inspectTile(int index) {
		/* Inspect the Tile Status of a selected TIle and Print its attributes */
		/* Inspect Tile containing a SEED */
		if(plot.get(index).getStatus().equals(TileStatus.SEED)) {
			System.out.println("\t[TILE " + plot.get(index).getPosition() + "] : " + plot.get(index).getPlantedCrop().getName().toUpperCase() + " " + plot.get(index).getStatus() +
					"\n\tType: " + plot.get(index).getPlantedCrop().getType() +
					"\n\tTimes Watered: " + plot.get(index).getPlantedCrop().getWater() + "\tTimes Fertilized: " + plot.get(index).getPlantedCrop().getFertilizer() +
					"\n\tGrow Time: " + plot.get(index).getPlantedCrop().getGrowTime() + "\n");
		}
		/* Inspect Tile containing a PLANT */
		else if((plot.get(index).getStatus().equals(TileStatus.PLANT))){
			System.out.println("\t[TILE " + plot.get(index).getPosition() + "] : " + plot.get(index).getPlantedCrop().getName().toUpperCase() + " " + plot.get(index).getStatus());
			System.out.println("\tStatus: Healthy\n\tThis plant is ready to harvest. ");
		}
		else if((plot.get(index).getStatus().equals(TileStatus.UNPLOWED))){
			System.out.println(	"\t[TILE " + plot.get(index).getPosition() + "] : " + plot.get(index).getStatus());
			System.out.println("\tThis tile is Unplowed, cultivate the TILE using the Plow Tool in order to plant a seed.");
		}
		else if((plot.get(index).getStatus().equals(TileStatus.PLOWED))) {
			System.out.println("\t[TILE " + plot.get(index).getPosition() + "] : " + plot.get(index).getStatus());
			System.out.println("\tThis tile is cultivated, and ready to be planted.");
		}
		else if((plot.get(index).getStatus().equals(TileStatus.WITHERED))) {
			System.out.println("\t[TILE " + plot.get(index).getPosition() + "] : " + plot.get(index).getStatus());
			System.out.println("\tThis tile contains a Withered plant.\n\tYou are unable to do anything to this tile unless you use a certain tool to remove it.");
		}
		else {
			System.out.println("\t[TILE " + plot.get(index).getPosition() + "] : " + plot.get(index).getStatus());
			System.out.println("This tile contains a Rock. Rock are annoying and they block you from accessing the Tile.\n\tUse the pickaxe tool to remove the rock.");
		}
	}

	public static void updateFarmerLevel(){
		/* Reset the Farmer's Exp Counter since the CAP is 100 */
		if(player.getExperience() >= 100){
			player.setFarmerLevel(player.getFarmerLevel()+1);
			System.out.println("\tCongratulations, you have leveled up! You are now Level " + player.getFarmerLevel());
			player.addExperience((-100));
		}

		if(player.getFarmerLevel() == 5){
			System.out.println("\tCongratulations, you are now eligible to promote to a 'Registered Farmer'. (Costs 200 ObjetCoins)");
		}
		else if(player.getFarmerLevel() == 10){
			System.out.println("\tCongratulations, you are now eligible to promote to a 'Distinguished Farmer'. (Costs 300 ObjetCoins)");
		}
		else if(player.getFarmerLevel() == 15){
			System.out.println("\tCongratulations, you are now eligible to promote to a 'Legendary Farmer'. (Costs 400 ObjetCoins)");
		}
	}
	public static void cls() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void keyContinue(){
		System.out.println("\tPress any key to continue");
		input.nextLine(); input.nextLine();
		//cls();
	}

}