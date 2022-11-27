package base;

import java.util.HashMap;

public class Tile {
	private Crop plantedCrop = null;
	// Additional appropriate attribute  [1 POINT]
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

	public boolean isEdge() {
		return edge;
	}

	public boolean IsPlantable() {
		return IsPlantable;
	}

	public void setPlantable(boolean occupied) {
		IsPlantable = occupied;
	}

	public void setEdge(boolean edge) {
		this.edge = edge;
	}

	@Override
	public String toString() { // YOU COULD EDIT
		return "Tile [plantedCrop=" + plantedCrop + "]"; // YOU COULD EDIT
	}
}
