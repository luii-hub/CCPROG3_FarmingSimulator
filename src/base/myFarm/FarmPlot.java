package base.myFarm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FarmPlot {
    private int daytime;

    /** This method generates the farmable plot of the MyFarm Game. This function is only executed once
     *
     *  @param plot
     */
    public FarmPlot(HashMap<Integer, Tile> plot){
        this.daytime = 1;
        try {
            /* Generate 50 Tiles stored in a HashMap */
            for (int i = 0; i < 50; i++) {
                Tile tile = new Tile(i + 1, null, TileStatus.UNPLOWED);
                plot.put(tile.getPosition(), tile);
            }
            generateManualRocks(plot);
            /* Find the 'edges' of the Board for Fruit Tree Usage */
            generateEdges(plot);

        } catch (Exception exception){
            exception.getStackTrace();
        }

    }

    private void generateManualRocks(HashMap<Integer, Tile> plot) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("rocks2.txt"));

        String line = null;
        int rockValue;
        int tileIndex = 1;
        while((line = br.readLine()) != null){
            String[] values = line.split(",");
            for (String value : values) {
                try {
                    rockValue = Integer.parseInt(value);
                    if (rockValue == 1) {
                        plot.get(tileIndex).setStatus(TileStatus.ROCK);
                    }

                } catch (NumberFormatException error) {
                    continue;
                }
                tileIndex++;
            }
        }
        br.close();
    }

    /** This method declared what tile index is at the edge of the board. This function is only executed once
     *
     *  @param plot
     */
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

    /** This method simply prints the FarmPlot at the interface. This method is only executed once.
     *
     * @param plot
     */
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
