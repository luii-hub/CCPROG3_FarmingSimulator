package base.gui;
import base.myFarm.Crop;
import base.myFarm.Tool;

import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class MyFarmController {
    private final MyFarmView farmView;
    private final MyFarmModel farmModel;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    String inventory = null;

    MyFarmController(MyFarmView farmView, MyFarmModel model) {
        this.farmView = farmView;
        this.farmModel = model;
    }

    public void runGame() {
        farmView.init();
        farmView.initializePanels();
        updateGame();
        farmMarket();
        actionCommand();
    }

    public void updateGame() {
        gameConditionUpdater();
        updateFarmerLevel();
        updateFarmPlotPanel();
        updateFarmerDetailsPanel();
        updateFarmerInventory();
    }

    private void updateFarmerDetailsPanel() {
        farmView.getFarmerDetailsTitle().setText(" - Farmer Details -");
        farmView.getFarmerLevelLabel().setText(" Farmer Level: " + String.valueOf(farmModel.player.getFarmerLevel()));
        farmView.getFarmerExpLabel().setText(" Experience: " + String.valueOf(decimalFormat.format(farmModel.player.getExperience())));
        farmView.getFarmerObjectCoinLabel().setText(" ObjectCoins: " + String.valueOf(decimalFormat.format(farmModel.player.getObjectCoins())));
        farmView.getFarmerTypeLabel().setText(" Status: " + String.valueOf(farmModel.player.getType().getFarmerType()));
        farmView.getRegisterFarmerButton().setText(" Register");
        farmView.getNextDayButton().setText(" Rest");

    }

    private void updateFarmPlotPanel() {
        farmView.getPlotDetailsTitle().setText("Mi Sakahan");
        farmView.getPlotDetailsLabel().setText("Day: " + String.valueOf(farmModel.MyFarm.getDaytime()));
    }

    private void updateFarmerInventory() {
        farmView.getFarmerInventoryTitle().setText(" - Farmer Seed Inventory -");
        for (int i = 0; i < farmModel.seedList.size(); i++) {
            int inputIndex = i + 1;
            inventory = " " + farmModel.seedList.get(i).getName() + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(i));
            farmView.getFarmerSeedInventoryButtons().get(inputIndex).setText(inventory);
            System.out.println(inventory);
        }
    }
    private void famerStatusMouseListener(){
        farmView.getFarmerDetailsPanel().addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(String.valueOf(farmModel.player.getType().toString()));
            }
            @Override public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }
    public void updateFarmerLevel() {
        /* Reset the Farmer's Exp Counter since the CAP is 100 */
        if (farmModel.player.getExperience() >= 100){
            while (farmModel.player.getExperience() >= 100) {
                farmModel.player.setFarmerLevel(farmModel.player.getFarmerLevel() + 1);
                farmModel.player.addExperience((-100));
            }
            System.out.println("\tCongratulations, you have leveled up! You are now Level " + farmModel.player.getFarmerLevel());
        }

        if(farmModel.player.getFarmerLevel() >= 5 && farmModel.player.getRegisterCounter() == 0){
            System.out.println("\tCongratulations, you are now eligible to promote to a 'Registered Farmer'. (Costs 200 ObjetCoins)");
            farmModel.player.setRegisterable(true);
        }
        else if(farmModel.player.getFarmerLevel() >= 10 && farmModel.player.getRegisterCounter() == 1){
            System.out.println("\tCongratulations, you are now eligible to promote to a 'Distinguished Farmer'. (Costs 300 ObjetCoins)");
            farmModel.player.setRegisterable(true);
        }
        else if(farmModel.player.getFarmerLevel() >= 15 && farmModel.player.getRegisterCounter() == 2){
            System.out.println("\tCongratulations, you are now eligible to promote to a 'Legendary Farmer'. (Costs 400 ObjetCoins)");
            farmModel.player.setRegisterable(true);
        }
    }

    private void farmMarket() {
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

        /* Misc Methods & Details */
        showTileInfoListener();
        updateTileStatuses();
        famerStatusMouseListener();

        /* Farmer Action Command MouseListeners */
        plowMouseListener();
        waterMouseListener();
        fertMouseListener();
        shovelMouseListener();
        pickaxeMouseListener();
        buySeedMouseListener();
        showSeedInfoListener();
        plantSeedMouseListener();
        plantSeedActionListener();
        harvestPlantMouseListener();
        registerFarmerMouseListener();
        nextDayMouseListener();

        /* Farmer Action Button Listener */
        farmCommandActionListener();
    }
    private void updateTileStatuses(){
        for (int position = 1; position <= 50; position++) {
            farmView.getFarmPlotButtons().get(position).setText(String.valueOf(farmModel.plot.get(position).getStatus()));
        }
    }
    private void gameConditionUpdater() {

        /* Buy Seed Conditions */
        farmView.getBuySeedButton().setEnabled(!(farmModel.player.getObjectCoins() <= 0));

    }

    private void farmCommandActionListener() {
        ActionListener commandListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object buttonSource = e.getSource();

                if (buttonSource == farmView.getPlowButton()) {
                    System.out.println("Plow Button Action Performed");
                    try {
                        int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Plow On", "Plow Selection",
                                JOptionPane.QUESTION_MESSAGE));
                        switch (farmModel.plot.get(tileIndex).getStatus()) {
                            case UNPLOWED -> {
                                if (JOptionPane.showConfirmDialog(null, "Are you sure to Plow this tile?",
                                        "Plow Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                    farmModel.player.plowTool(farmModel.plot, (ArrayList<Tool>) farmModel.toolList, tileIndex);
                                    updateTileStatuses();
                                    updateGame();
                                }
                            }
                            case SEED, PLANT, TREE, WITHERED, ROCK, PLOWED -> {
                                JOptionPane.showMessageDialog(null,
                                        "Error! You can only use the Hoe at Unplowed Tiles!",
                                        "Plow Tool Tile Validation", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception error) {
                        displayErrorMessage();
                    }

                } else if (buttonSource == farmView.getWaterButton()) {
                    System.out.println("Water Button Action Performed");
                    try {
                        int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Water On", "Water Selection",
                                JOptionPane.QUESTION_MESSAGE));
                        switch (farmModel.plot.get(tileIndex).getStatus()) {
                            case SEED -> {
                                if (JOptionPane.showConfirmDialog(null, "Are you sure to Water tile?",
                                        "Water Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                    farmModel.player.waterTool(farmModel.plot, (ArrayList<Tool>) farmModel.toolList, tileIndex);
                                    updateTileStatuses();
                                    updateGame();
                                }
                            }
                            case PLOWED, PLANT, UNPLOWED, ROCK, TREE, WITHERED -> {
                                JOptionPane.showMessageDialog(null,
                                        "Error! You can only water tiles that contains a Seed!",
                                        "Water Seed Tile Validation", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception error) {
                        displayErrorMessage();
                    }
                } else if (buttonSource == farmView.getFertilizerButton()) {
                    System.out.println("Fertilizer Button Action Performed");
                    try {
                        int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Fertilize On", "Fertilize Selection",
                                JOptionPane.QUESTION_MESSAGE));
                        switch (farmModel.plot.get(tileIndex).getStatus()) {
                            case SEED -> {
                                if (JOptionPane.showConfirmDialog(null, "Are you sure to Fertilizer this tile?",
                                        "Fertilizer Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                    farmModel.player.fertilizerTool(farmModel.plot, (ArrayList<Tool>) farmModel.toolList, tileIndex);
                                    updateTileStatuses();
                                    updateGame();
                                }
                            }
                            case PLOWED, PLANT, UNPLOWED, ROCK, TREE, WITHERED -> {
                                JOptionPane.showMessageDialog(null,
                                        "Error! You can only fertilize tiles that contains a Seed!",
                                        "Fertilize Seed Tile Validation", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception error) {
                        displayErrorMessage();
                    }
                } else if (buttonSource == farmView.getShovelButton()) {
                    System.out.println("Shovel Button Action Performed");
                    try {
                        int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Shovel On", "Shovel Selection",
                                JOptionPane.QUESTION_MESSAGE));
                        switch (farmModel.plot.get(tileIndex).getStatus()) {
                            case SEED, PLANT, ROCK, TREE, WITHERED, PLOWED, UNPLOWED -> {
                                if (JOptionPane.showConfirmDialog(null, "Are you sure to Shovel this tile?",
                                        "Shovel Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                    farmModel.player.shovelTool(farmModel.plot, (ArrayList<Tool>) farmModel.toolList, tileIndex);
                                    updateTileStatuses();
                                    updateGame();
                                    JOptionPane.showMessageDialog(
                                            null, "You have successfully used the Shovel",
                                            "Shovel Tile Message",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                    } catch (Exception error) {
                        displayErrorMessage();
                    }
                } else if (buttonSource == farmView.getPickaxeButton()) {
                    System.out.println("Pickaxe Button Action Performed");
                    try {
                        int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Pickaxe On", "Pickaxe Selection",
                                JOptionPane.QUESTION_MESSAGE));
                        switch (farmModel.plot.get(tileIndex).getStatus()) {
                            case ROCK -> {
                                if (JOptionPane.showConfirmDialog(null, "Are you sure to Pickaxe this tile?",
                                        "Pickaxe Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                    farmModel.player.pickaxeTool(farmModel.plot, (ArrayList<Tool>) farmModel.toolList, tileIndex);
                                    updateTileStatuses();
                                    updateGame();
                                    JOptionPane.showMessageDialog(
                                            null, "You have successfully used a Pickaxe",
                                            "Pickaxe Tile Message",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            case SEED, PLANT, PLOWED, UNPLOWED, TREE, WITHERED -> {
                                JOptionPane.showMessageDialog(null,
                                        "Error! You can only use the Pickaxe Tool on Tiles that contains a Rock!",
                                        "Pickaxe Tool Tile Validation", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception error) {
                        displayErrorMessage();
                    }
                } else if (buttonSource == farmView.getRegisterFarmerButton()) {
                    System.out.println("Register Button Action Performed");
                    /*	If player has met the minimum requirements, allow player the option to register into new role.
                     * 	If player has reached maximum level, disable player to register anymore */
                    try {
                        if (farmModel.player.isRegisterable()) {
                                if (JOptionPane.showConfirmDialog(null, "Do you want to register to a new Farmer Title?",
                                        "Farmer Registration Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                    farmModel.registerFarmer(farmModel.player);
                                }
                        } else if (farmModel.player.getRegisterCounter() == 3) {
                            JOptionPane.showInternalMessageDialog(null, "You have reached the maximum status as a Farmer.",
                                    "Farmer Registration", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("\tYou have reached the maximum status as a Farmer.");
                        } else {
                            JOptionPane.showInternalMessageDialog(null, "You cannot register farmer at this time.",
                                    "Farmer Registration", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("You cannot register farmer at this time.");
                        }
                    } catch (Exception error) {
                        displayErrorMessage();
                    }
                    updateTileStatuses();
                    updateGame();
                } else if (buttonSource == farmView.getNextDayButton()) {
                    System.out.println("Next day Button Action Performed");
                    try {
                        if (JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to rest for today?", "Rest Option", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            farmModel.nextDay(farmModel.MyFarm);
                            farmView.getPlotDetailsLabel().setText("Day " + String.valueOf(farmModel.MyFarm.getDaytime()));
                            System.out.println(farmModel.MyFarm.getDaytime());
                        }
                        updateTileStatuses();
                        updateGame();
                    } catch (Exception error) {
                        displayErrorMessage();
                    }
                }
            }
        };
        farmView.getPlowButton().addActionListener(commandListener);
        farmView.getWaterButton().addActionListener(commandListener);
        farmView.getFertilizerButton().addActionListener(commandListener);
        farmView.getPickaxeButton().addActionListener(commandListener);
        farmView.getShovelButton().addActionListener(commandListener);
        farmView.getRegisterFarmerButton().addActionListener(commandListener);
        farmView.getNextDayButton().addActionListener(commandListener);
        updateTileStatuses();
    }

    private void plowMouseListener() {
        farmView.getPlowButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(
                        "<html> Plow Tool | Cost 0 ObjectCoins <p><p>" +
                                "Converts an unplowed tile to a plowed tile. Can only be performed on an unplowed tile. </html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
        });
    }

    private void waterMouseListener() {
        farmView.getWaterButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(
                        "<html> WaterCan Tool | Cost 0 ObjectCoins <p><p>" +
                                "Adds to the total number of times a tile/crop has been watered. " +
                                "Can only be performed on a plowed tile with a crop. </html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
        });
    }

    private void fertMouseListener() {
        farmView.getFertilizerButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(
                        "<html> Fertilizer Tool | Cost 10 ObjectCoins <p><p>" +
                                "Adds to the total number of times a tile/crop has been applied with fertilizer. " +
                                "Can only be performed on a plowed tile with a crop. ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
        });
    }

    private void shovelMouseListener() {
        farmView.getShovelButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Shovel Tool | Cost 7 ObjectCoins <p><p>" +
                                "Removes a withered plant from a tile. " +
                                "Can be used on any tile/crop with varying effects, as described above. </html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
        });
    }

    private void pickaxeMouseListener() {
        farmView.getPickaxeButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Pickaxe Tool | Cost 50 ObjectCoins <p><p> + " +
                                "Removes a rock from a tile. Can only be applied to tiles with a rock. </html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
        });
    }

    private void showSeedInfoListener() {
        for (int i = 1; i <= farmView.getSeedShopButtons().size(); i++) {
            int finalI = i - 1;
            farmView.getSeedShopButtons().get(i).addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(farmModel.seedList.get(finalI).toString());}
                @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
            });
        }
    }

    private void showTileInfoListener() {
        for (int i = 1; i <= farmModel.plot.size(); i++) {
            int finalI = i;
            farmView.getFarmPlotButtons().get(i).addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(farmModel.plot.get(finalI).toString());}
                @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
            });
        }
    }

    private void buySeedUpdater() {
        updateGame();
        /* Update Buy Seed Button Restrictions */
        for (int i = 1; i <= farmModel.seedList.size(); i++) {
            farmView.getSeedShopButtons().get(i).setEnabled(false);
        }
    }

    private void buySeedMouseListener() {
        farmView.getBuySeedButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                /* Enable Buy Seed Button for each Seed so long as the player has enough objectCoins to buy it */
                for (int i = 1; i <= farmModel.seedList.size(); i++) {
                    if (farmModel.player.getObjectCoins() >= farmModel.seedList.get(i - 1).getBuyCost()) {
                        farmView.getSeedShopButtons().get(i).setEnabled(true);
                    }
                }
            }
            @Override public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Buy a Seed from the super market. " +
                                "Purchase your desired seed crops from there. </html> ");
            }
            @Override public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
        buySeedActionListener();
    }

    private void buySeedActionListener() {
        farmView.getSeedShopButtons().get(1).addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(1, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(0).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(0));
                farmView.getFarmerSeedInventoryButtons().get(1).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(2).addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(2, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(1).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(1));
                farmView.getFarmerSeedInventoryButtons().get(2).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(3).addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(3, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(2).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(2));
                farmView.getFarmerSeedInventoryButtons().get(3).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(4).addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(4, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(3).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(3));
                farmView.getFarmerSeedInventoryButtons().get(4).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(5).addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(5, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(4).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(4));
                farmView.getFarmerSeedInventoryButtons().get(5).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(6).addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(6, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(5).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(5));
                farmView.getFarmerSeedInventoryButtons().get(6).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(7).addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(7, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(6).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(6));
                farmView.getFarmerSeedInventoryButtons().get(7).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(8).addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(8, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(7).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(7));
                farmView.getFarmerSeedInventoryButtons().get(8).setText(inventory);
                buySeedUpdater();
            }
        });

    }

    private void plantSeedMouseListener() {
        farmView.getPlantSeedButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                System.out.println("Plant Seed Button Clicked");
                /* Access SeedShopButtons Depending on available Seeds */
                boolean isAccessible = false;
                for (int i = 0; i < farmModel.seedList.size(); i++) {
                    int inputIndex = i + 1;
                    if (Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(i)) != 0) {
                        farmModel.seedList.get(i).setChoosable(true);
                        farmView.getFarmerSeedInventoryButtons().get(inputIndex).setEnabled(true);
                        isAccessible = true;
                    }
                }
                if(!isAccessible){
                    JOptionPane.showInternalMessageDialog(null, "You cannot access this feature since you currently do not own any seeds",
                            "Plant Seed Validation", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            @Override public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Plants a seed on a plowed tile. Can only plant a seed on an unoccupied tile, plowed tile." +
                                "Fruit Tree Seeds cannot be planted on edges and can only be planted on an unoccupied, plowed tile" +
                                "with no neighboring objects around the selected tile.</html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
        });
    }

    private void plantSeedActionListener() {
        for (int i = 1; i <= farmView.getFarmerSeedInventoryButtons().size(); i++) {
            int finalI = i - 1;
            farmView.getFarmerSeedInventoryButtons().get(i).addActionListener(new ActionListener() {
                @Override public void actionPerformed(ActionEvent e) {
                    try {
                        int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Plant On", "Plant Selection",
                                JOptionPane.QUESTION_MESSAGE));
                        if (JOptionPane.showConfirmDialog(null, "Are you sure to Plant on this tile?",
                                "Plant Seed Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            if (farmModel.checkTileStatus(farmModel.plot, "plant")) {
                                farmModel.player.plantSeed(farmModel.plot, tileIndex, farmModel.seedList.get(finalI));
                                updateGame();
                            } else {
                                JOptionPane.showInternalMessageDialog(null, "Error! You can only plant on unoccupied, PLOWED tiles.",
                                        "Plant Seed Validation", JOptionPane.INFORMATION_MESSAGE);
                                System.out.println("Error! You can only plant on unoccupied, PLOWED tiles.");
                            }
                        }
                        /* Update Farmer Inventory Seed Button Restrictions */
                        for (int i = 1; i <= farmModel.seedList.size(); i++) {
                            farmView.getFarmerSeedInventoryButtons().get(i).setEnabled(false);
                        }
                        updateTileStatuses();
                        updateGame();
                    } catch (Exception error){
                        JOptionPane.showInternalMessageDialog(null, "Cancelled Plant Seed Action", "Plant Seed Validation", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
        }
    }

    private void harvestPlantMouseListener() {
        farmView.getHarvestSeedButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                System.out.println("Harvest Button Clicked");
                try {
                    int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Harvest", "Harvest Selection",
                            JOptionPane.QUESTION_MESSAGE));
                    switch (farmModel.plot.get(tileIndex).getStatus()) {
                        case PLANT -> {
                            if (JOptionPane.showConfirmDialog(null, "Are you sure to Harvest on this Plant?",
                                    "Harvest Plant Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                if (farmModel.checkTileStatus(farmModel.plot, "harvest")) {
                                    farmModel.player.harvestPlant(farmModel.player, farmModel.plot, tileIndex);
                                    updateGame();
                                    JOptionPane.showMessageDialog(
                                            null, "You have successfully harvested a Plant",
                                            "Harvest Plant Tile Message",
                                            JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showInternalMessageDialog(null, "Error! You cannot access this feature unless you have a Plant.",
                                            "Harvest Plant Validation", JOptionPane.INFORMATION_MESSAGE);
                                    System.out.println("Error! You cannot access this feature unless you have a Plant.");
                                }
                            }
                            updateTileStatuses();
                        }
                        case SEED, ROCK, TREE, WITHERED, PLOWED, UNPLOWED -> {
                            JOptionPane.showInternalMessageDialog(null, "Error! You cannot access this feature unless you have a Plant.",
                                    "Harvest Plant Validation", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Error! You cannot access this feature unless you have a Plant.");
                        }
                    }
                } catch (Exception error) {
                    displayErrorMessage();
                }
            }
            @Override public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Harvest a harvestable Plant on the farm plot. " +
                                "Can only harvest a crop that is harvestable " +
                                "Different Crops have different attributes, harvesting them may contain other benefits" +
                                "</html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
        });
    }

    private void registerFarmerMouseListener() {
        farmView.getRegisterFarmerButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Register your status to become a newly pledged farmer.<p><p>" +
                                "Novice Farmer: (Default Status)<p><p>" +
                                "Registered Farmer: Cost 200 OC<p><p>" +
                                "Distinguished Farmer: Cost 300 OC<p><p>" +
                                "Legendary Farmer: Cost 400 OC" +
                                "</html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
        });
    }

    private void nextDayMouseListener() {
        farmView.getNextDayButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                farmView.getPlotDetailsLabel().setText("Day " + String.valueOf(farmModel.MyFarm.getDaytime()));
                updateFarmerDetailsPanel();
            }
            @Override public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Take a good nights rest and advance unto the next day. " +
                                "Resting resets all parameters and updates the gameplay.</html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("");}
        });
    }
    private void displayErrorMessage() {
        JOptionPane.showMessageDialog(null,
                "Error!  Command action got disrupted OR Command action was unable to be executed.",
                "Error Message", JOptionPane.ERROR_MESSAGE);
    }
}