package base;

public class Tile {
	private Crop plantedCrop = null;
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
	public String toString() {
		return "Tile [plantedCrop=" + plantedCrop + "]";
	}
}
