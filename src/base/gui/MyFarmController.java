package base.gui;

import base.myFarm.Crop;

import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyFarmController{

    private final MyFarmView farmView;
    private final MyFarmModel farmModel;
    String inventory = null;

    MyFarmController(MyFarmView farmView, MyFarmModel model){
        this.farmView = farmView;
        this.farmModel = model;
    }

    public void runGame(){
        farmView.init();
        farmView.initializePanels();

        updateGame();
        farmMarket();

        actionCommand();

    }

    public void updateGame(){
        gameConditionUpdater();
        updateFarmPlotPanel();
        updateFarmerDetailsPanel();
        updateFarmerInventory();
    }

    private void updateFarmerDetailsPanel(){
        farmView.getFarmerDetailsTitle().setText(" - Farmer Details -");
        farmView.getFarmerLevelLabel().setText(" Farmer Level: " + String.valueOf(farmModel.player.getFarmerLevel()));
        farmView.getFarmerExpLabel().setText(" Experience: " +  String.valueOf(farmModel.player.getExperience()));
        farmView.getFarmerObjectCoinLabel().setText(" ObjectCoins: " + String.valueOf(farmModel.player.getObjectCoins()));
        farmView.getFarmerTypeLabel().setText(" Status: " + String.valueOf(farmModel.player.getType().getFarmerType()));
        farmView.getRegisterFarmerButton().setText(" Register");
        farmView.getNextDayButton().setText(" Rest");

    }

    private void updateFarmPlotPanel(){

        farmView.getPlotDetailsTitle().setText("Mi Sakahan");
        farmView.getPlotDetailsLabel().setText("Day: " + String.valueOf(farmModel.MyFarm.getDaytime()));
        String mahaba = "Hello World Hello World Hello World Hello World Hello World Hello World Hello World";
        farmView.getGameTextLabel().setText("<html>" + mahaba +  "</html>");

    }

    private void updateFarmerInventory(){
        farmView.getFarmerInventoryTitle().setText(" - Farmer Seed Inventory -");
        for (int i = 0; i < farmModel.seedList.size(); i++) {
            int inputIndex = i + 1;
            inventory = " [" + inputIndex + "] " + farmModel.seedList.get(i).getName() + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(i));
            farmView.getFarmerSeedInventoryLabel().get(inputIndex).setText(inventory);
            System.out.println(inventory);

        }
    }

    private void farmMarket(){
        farmView.getSeedShopTitle().setText("- Farmer SuperMarket -");
        for (int i = 0; i < farmModel.seedList.size(); i++) {
            int inputIndex = i + 1;
            farmView.getSeedShopButtons().get(inputIndex).setEnabled(false);
            inventory = farmModel.seedList.get(i).getName() + " Seed";
            farmView.getSeedShopButtons().get(inputIndex).setText(inventory);
            System.out.println(inventory);

        }
    }

    private void actionCommand() {

        /* Plow Tool */
        plowMouseListener();
        waterMouseListener();
        fertMouseListener();
        shovelMouseListener();
        pickaxeMouseListener();
        buySeedMouseListener();
        buySeedActionListener();
        plantSeedMouseListener();
        harvestPlantMouseListener();
        registerFarmerMouseListener();
        nextDayMouseListener();
        nextDayActionListener();

    }

    private void gameConditionUpdater(){
        /* Buy Seed Conditions */
        farmView.getBuySeedButton().setEnabled(!(farmModel.player.getObjectCoins() <= 0));

    }
    private void plowMouseListener(){
        farmView.getPlowButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Plow Button Clicked");
                //Access Plow Tool
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                    "<html> Converts an unplowed tile to a plowed tile. Can only be performed on an unplowed tile. </html> ");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }
    private void waterMouseListener() {
        farmView.getWaterButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Water Button Clicked");
                //Access Water Tool
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Adds to the total number of times a tile/crop has been watered. " +
                                "Can only be performed on a plowed tile with a crop. </html> ");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }
    private void fertMouseListener() {
        farmView.getFertilizerButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Fertilizer Button Clicked");
                //Access Water Tool
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Adds to the total number of times a tile/crop has been applied with fertilizer. " +
                                "Can only be performed on a plowed tile with a crop. ");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }
    private void shovelMouseListener() {
        farmView.getShovelButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Shovel Button Clicked");
                //Access Shovel Tool
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Removes a withered plant from a tile. " +
                                "Can be used on any tile/crop with varying effects, as described above. </html> ");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }
    private void pickaxeMouseListener() {
        farmView.getPickaxeButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Pickaxe Button Clicked");
                //Access Pickaxe Tool
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Removes a rock from a tile. Can only be applied to tiles with a rock. </html> ");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }
    private void buySeedMouseListener() {
        farmView.getBuySeedButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /* Buy Seed Method */
                System.out.println("BuySeed Button");
                //farmView.getBuySeedButton().setEnabled(true);

                /* Enable Buy Seed Button for each Seed so long as the player has enough objectCoins to buy it */
                for(int i = 1; i <= farmModel.seedList.size(); i++){
                    if(farmModel.player.getObjectCoins() >= farmModel.seedList.get(i-1).getBuyCost()) {
                        farmView.getSeedShopButtons().get(i).setEnabled(true);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Buy a Seed from the super market. " +
                                "Purchase your desired seed crops from there. </html> ");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }

    private void buySeedUpdater(){
        farmView.getFarmerExpLabel().setText(" Experience: " + String.valueOf(farmModel.player.getExperience()));
        farmView.getFarmerObjectCoinLabel().setText(" ObjectCoins: " + String.valueOf(farmModel.player.getObjectCoins()));

        /* Update Buy Seed Button Restrictions */
        for(int i = 1; i <= farmModel.seedList.size(); i++) {
            farmView.getSeedShopButtons().get(i).setEnabled(false);
        }
    }
    private void buySeedActionListener(){
        boolean select = true;

        farmView.getSeedShopButtons().get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(1, (ArrayList<Crop>) farmModel.seedList);
                inventory = " [" + 1 + "] " + farmModel.seedList.get(0).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(0));
                farmView.getFarmerSeedInventoryLabel().get(1).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(2, (ArrayList<Crop>) farmModel.seedList);
                inventory = " [" + 2 + "] " + farmModel.seedList.get(1).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(1));
                farmView.getFarmerSeedInventoryLabel().get(2).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(3, (ArrayList<Crop>) farmModel.seedList);
                inventory = " [" + 3 + "] " + farmModel.seedList.get(2).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(2));
                farmView.getFarmerSeedInventoryLabel().get(3).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(4).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(4, (ArrayList<Crop>) farmModel.seedList);
                inventory = " [" + 4 + "] " + farmModel.seedList.get(3).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(3));
                farmView.getFarmerSeedInventoryLabel().get(4).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(5).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(5, (ArrayList<Crop>) farmModel.seedList);
                inventory = " [" + 5 + "] " + farmModel.seedList.get(4).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(4));
                farmView.getFarmerSeedInventoryLabel().get(5).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(6).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(6, (ArrayList<Crop>) farmModel.seedList);
                inventory = " [" + 6 + "] " + farmModel.seedList.get(5).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(5));
                farmView.getFarmerSeedInventoryLabel().get(6).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(7).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(7, (ArrayList<Crop>) farmModel.seedList);
                inventory = " [" + 7 + "] " + farmModel.seedList.get(6).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(6));
                farmView.getFarmerSeedInventoryLabel().get(7).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(8).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(8, (ArrayList<Crop>) farmModel.seedList);
                inventory = " [" + 8 + "] " + farmModel.seedList.get(7).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(7));
                farmView.getFarmerSeedInventoryLabel().get(8).setText(inventory);
                buySeedUpdater();
            }
        });

    }

    private void plantSeedMouseListener() {
        farmView.getPlantSeedButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Plant Seed Button Clicked");
                //Access Plant Seed Method
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Plants a seed on a plowed tile. Can only plant a seed on an unoccupied tile, plowed tile." +
                                "Fruit Tree Seeds cannot be planted on edges and can only be planted on an unoccupied, plowed tile" +
                                "with no neighboring objects around the selected tile.</html> ");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }
    private void harvestPlantMouseListener() {
        farmView.getHarvestSeedButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Harvest Button Clicked");
                //Access Pickaxe Tool
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Harvest a harvestable Plant on the farm plot. " +
                                "Can only harvest a crop that is harvestable " +
                                "Different Crops have different attributes, harvesting them may contain other benefits" +
                                "</html> ");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }
    private void registerFarmerMouseListener() {
        farmView.getRegisterFarmerButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Register Button Clicked");
                //Access Register Tool
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Register your status to become a newly pledged farmer." +
                                "</html> ");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }

    private void nextDayMouseListener() {
        farmView.getNextDayButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /* Access Next Day Method */
                System.out.println("Next Day Button Clicked");
                farmModel.nextDay(farmModel.MyFarm);
                farmView.getPlotDetailsLabel().setText("Day " + String.valueOf(farmModel.MyFarm.getDaytime()));
                System.out.println(farmModel.MyFarm.getDaytime());

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Take a good night's rest and advance unto the next day." +
                                "Resting resets all parameters and updates the gameplay.</html> ");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }
    private void nextDayActionListener(){
        farmView.getNextDayButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Next Day Button Pressed");
                farmModel.nextDay(farmModel.MyFarm);
                farmView.getPlotDetailsLabel().setText("Day " + String.valueOf(farmModel.MyFarm.getDaytime()));
                System.out.println(farmModel.MyFarm.getDaytime());
            }
        });
    }




}
