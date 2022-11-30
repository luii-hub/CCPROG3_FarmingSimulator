package base;

import java.util.HashMap;

public class FarmPlot {
    private int daytime;
    public FarmPlot(HashMap<Integer, Tile> plot){
        /* Generate 50 Tiles stored in a HashMap */
        for(int i = 0; i < 50; i++){
            Tile tile = new Tile(i + 1, null, TileStatus.UNPLOWED);
            plot.put(tile.getPosition(), tile);
        }
        this.daytime = 1;
        generateEdges(plot);
        //generateRocks(plot);

    }

    private void generateEdges(HashMap<Integer, Tile> plot){
        for(int i = 1; i <= 50; i++){
            if(i <= 6 || i >= 45) {
                plot.get(i).setEdge(true);
            }
            if((i % 5) == 1 || (i % 5) == 0){
                plot.get(i).setEdge(true);
            }
        }
    }
    private int rngRockPosition(){
        int lb = 1, ub = 50;
        return (int) Math.floor(Math.random()*(ub-lb+1)+lb);
    }

    private int rngRockCount(){
        int minRocks = 10, maxRocks = 30;
        return (int) Math.floor(Math.random()*(maxRocks-minRocks+1)+minRocks);
    }

    private void generateRocks(HashMap<Integer, Tile> plot){
        int index;
        int randomRock = rngRockCount();
        int counter = 0;
        for(int i = 0; i < randomRock; i++){
            while(counter != randomRock){
                index = rngRockPosition();
                if(plot.get(index).getStatus().equals(TileStatus.ROCK)){
                    continue;
                }
                else {
                    plot.get(index).setStatus(TileStatus.ROCK);
                    counter++;
                }
            }
        }
    }
    public void printFarmPlot(HashMap<Integer, Tile> plot){
        String tileStatus = "" ;
        int tileCount = 1, i, j;
        while(tileCount <= 50){
            for(i = 0; i < 10; i++){
                for(j = 0; j < 5; j++){
                    tileStatus = switch (plot.get(tileCount).getStatus()) {
                        case UNPLOWED -> "UNPLOWED";
                        case PLOWED -> "PLOWED";
                        case ROCK -> "ROCK";
                        case SEED -> "SEED";
                        case WITHERED -> "WITHERED";
                        case PLANT -> "PLANT";
                        case TREE -> "FTREE";
                    };

                    System.out.printf("\t%-11s %-2d ", ("[" + tileStatus + "]"), plot.get(tileCount).getPosition());
                    tileCount++;
                }
                System.out.println(" ");
            }
        }
    }

    public int getDaytime() {
        return daytime;
    }

    public void setDaytime(int daytime) {
        this.daytime = this.daytime + daytime;
    }
}
