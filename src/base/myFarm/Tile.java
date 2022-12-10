package base.myFarm;

/**
 * This class represents a TILE object in the game where it holds a planted crop and lets where the user access
 */
public class Tile {
	private Crop plantedCrop;
	private TileStatus status;
	private final int position;
	private boolean IsPlantable;
	private boolean edge;

	/**
	 * Constructs a Tile Object that contains a crop, a position in the board, and also has a status
	 * @param position Position in the hashmap
	 * @param plantedCrop Crop object in the tile (can be null)
	 * @param status the tile's status within the game
	 */
	public Tile(int position, Crop plantedCrop, TileStatus status){
		this.plantedCrop = plantedCrop;
		this.position = position;
		this.status = status;
		this.edge = false;
		this.IsPlantable = true;
	}

	/* Required Getters */
	/** Returns the present Crop Object in the tile */
	public Crop getPlantedCrop() {
		return plantedCrop;
	}
	/** Returns the present position of the tile in the HashMap*/
	public int getPosition() {
		return position;
	}
	/** Returns the status  of the tile */
	public TileStatus getStatus() {
		return status;
	}

	/* Required Setters */
	/** Sets the crop present in the tile */
	public void setPlantedCrop(Crop plantedCrop) {
		this.plantedCrop = plantedCrop;
	}
	/** Sets the Tile Status of the tile */
	public void setStatus(TileStatus status) {
		this.status = status;
	}
	/** Sets the Plantable Boolean value of the tile */
	public void setPlantable(boolean occupied) {
		IsPlantable = occupied;
	}
	/** Sets the tile if it is located in the edges of the board */
	public void setEdge(boolean edge) {
		this.edge = edge;
	}
	/** Returns the boolean value the tile if it is located in the edges of the board */
	public boolean isEdge() {
		return edge;
	}
	/** Returns the boolean value the tile if it can be planted */
	public boolean IsPlantable() {
		return IsPlantable;
	}

	@Override
	public String toString() {
		String tileDetails = null;
		switch (status){
			case PLOWED -> tileDetails = "<html> Tile Status: " + getStatus() + "<p>" +
					"This tile is cultivated, and ready to be planted. </html>";
			case UNPLOWED -> tileDetails = "<html> Tile Status: " + getStatus() + "<p>" +
					"This tile is Unplowed, cultivate the tile using the Hoe in order to plant a seed. </html>";
			case WITHERED -> tileDetails = "<html> Tile Status: " + getStatus() + "<p>" +
					"This tile contains a Withered plant. <p>" +
					"You are unable to do anything to this tile unless you use a pickaxe to remove it. </html>";
			case ROCK -> tileDetails = "<html> Tile Status: " + getStatus() + "<p>" +
					"This tile contains a Rock. Rocks are annoying and they block you from accessing the Tile. <p>" +
					"Use the pickaxe tool to remove the rock <p> </html>";
			case PLANT -> tileDetails = "<html> Plant: " + getPlantedCrop().getName() + " Plant <p>" +
					"Tile Status: " + getStatus() + "<p>" +
					"This plant is very healthy and is very much ready to harvest. <p>" +
					"Note: Forgetting to Harvest a plant may face you consequences for doing so <p> </html>";
			case TREE -> tileDetails = "<html> Tree: " + getPlantedCrop().getName() + " Tree <p>" +
					"Tile Status: " + getStatus() + "<p>" +
					"This tile contains a chunky fruit tree and is very much ready to harvest <p>" +
					"Note: Forgetting to Harvest a fruit tree may face you consequences for doing so <p> </html>";
			case SEED -> tileDetails = "<html> Planted Seed: " + getPlantedCrop().getName() + " Seed <p>" +
					"Tile Status: " + getStatus() + "<p>" +
					"Seed Type: " + getPlantedCrop().getType() + "<p>" +
					"Times Watered: " + getPlantedCrop().getWater() + "<p>" +
					"Times Fertilized: " + getPlantedCrop().getFertilizer() + "<p>" +
					"Growth Time: " + getPlantedCrop().getGrowTime() + "<p> </html>";

		}
		return tileDetails;
	}
}
