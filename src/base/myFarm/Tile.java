package base.myFarm;

public class Tile {
	private Crop plantedCrop;
	private TileStatus status;
	private final int position;
	private boolean IsPlantable;
	private boolean edge;


	public Tile(int position, Crop plantedCrop, TileStatus status){
		this.plantedCrop = plantedCrop;
		this.position = position;
		this.status = status;
		this.edge = false;
		this.IsPlantable = true;
	}

	/* Required Getters */
	public Crop getPlantedCrop() {
		return plantedCrop;
	}

	public int getPosition() {
		return position;
	}

	public TileStatus getStatus() {
		return status;
	}

	/* Required Setters */
	public void setPlantedCrop(Crop plantedCrop) {
		this.plantedCrop = plantedCrop;
	}

	public void setStatus(TileStatus status) {
		this.status = status;
	}
	public void setPlantable(boolean occupied) {
		IsPlantable = occupied;
	}

	public void setEdge(boolean edge) {
		this.edge = edge;
	}

	public boolean isEdge() {
		return edge;
	}

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
