package base.myFarm;

/**
 * This class represents the status of a single Tile Object and represents a title in the program.
 */
public enum TileStatus {
    /**
     * A tile with this status means that it is being occupied by a ROCK.
     */
    ROCK,
    /**
     * A tile with this status means that it is not cultivated it is not plowed.
     */
    UNPLOWED,
    /**
     * A tile with this status means that it is cultivated and plowed.
     */
    PLOWED,
    /**
     * A tile with this status means that it is being occupied by a HARVESTABLE PLANT.
     */
    PLANT,
    /**
     * A tile with this status means that it is being occupied by a HARVESTABLE FRUIT TREE.
     */
    TREE,
    /**
     * A tile with this status means that it is being occupied by a GROWING SEED .
     */
    SEED,
    /**
     * A tile with this status means that it is being occupied by a WITHERED PLANT.
     */
    WITHERED,

}
