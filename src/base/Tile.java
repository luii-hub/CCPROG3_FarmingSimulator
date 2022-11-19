package base;

import java.util.HashMap;

public class Tile {
	private Crop plantedCrop = null;
	// Additional appropriate attribute  [1 POINT]
	private TileStatus status;
	private final int position;


	public Tile(int position, Crop plantedCrop, TileStatus status){
		this.plantedCrop = plantedCrop;
		this.position = position;
		this.status = status;
	};

	public Crop getPlantedCrop() { // DO NOT EDIT
		return plantedCrop; // DO NOT EDIT
	}

	public int getPosition() {
		return position;
	}

	public TileStatus getStatus() {
		return status;
	}

	public void setPlantedCrop(Crop plantedCrop) {
		this.plantedCrop = plantedCrop;
	}

	public void setStatus(TileStatus status) {
		this.status = status;
	}

	@Override
	public String toString() { // YOU COULD EDIT
		return "Tile [plantedCrop=" + plantedCrop + "]"; // YOU COULD EDIT
	}
}
