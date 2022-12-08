package base.gui;

import base.myFarm.Crop;
import base.myFarm.Tool;

import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyFarmController {

    private final MyFarmView farmView;
    private final MyFarmModel farmModel;
    String inventory = null;
    private int tileIndex;

    MyFarmController(MyFarmView farmView, MyFarmModel model) {
        this.farmView = farmView;
        this.farmModel = model;
    }

    public void runGame() {
        farmView.init();
        farmView.initializePanels();
        setToolActionCommands();

        updateGame();
        farmMarket();

        actionCommand();

    }

    public void updateGame() {
        gameConditionUpdater();
        updateFarmPlotPanel();
        updateFarmerDetailsPanel();
        updateFarmerInventory();
    }

    private void updateFarmerDetailsPanel() {
        farmView.getFarmerDetailsTitle().setText(" - Farmer Details -");
        farmView.getFarmerLevelLabel().setText(" Farmer Level: " + String.valueOf(farmModel.player.getFarmerLevel()));
        farmView.getFarmerExpLabel().setText(" Experience: " + String.valueOf(farmModel.player.getExperience()));
        farmView.getFarmerObjectCoinLabel().setText(" ObjectCoins: " + String.valueOf(farmModel.player.getObjectCoins()));
        farmView.getFarmerTypeLabel().setText(" Status: " + String.valueOf(farmModel.player.getType().getFarmerType()));
        farmView.getRegisterFarmerButton().setText(" Register");
        farmView.getNextDayButton().setText(" Rest");

    }

    private void updateFarmPlotPanel() {

        farmView.getPlotDetailsTitle().setText("Mi Sakahan");
        farmView.getPlotDetailsLabel().setText("Day: " + String.valueOf(farmModel.MyFarm.getDaytime()));
        //farmView.getGameTextLabel().setText("<html>" + mahaba +  "</html>");

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

    private void setToolActionCommands() {
        farmView.getPlantSeedButton().setActionCommand("Plant");
    }

    private void actionCommand() {

        /* Hover Tile Details */
        showTileInfoListener();

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

        farmCommandActionListener();

    }

    private void enableTiles() {
        for (int i = 1; i <= farmModel.plot.size(); i++) {
            farmView.getFarmPlotButtons().get(i).setEnabled(true);
        }
    }

    private void disableTiles() {
        for (int i = 1; i <= farmModel.plot.size(); i++) {
            farmView.getFarmPlotButtons().get(i).setEnabled(false);
        }
    }

    private void gameConditionUpdater() {

        /* Buy Seed Conditions */
        farmView.getBuySeedButton().setEnabled(!(farmModel.player.getObjectCoins() <= 0));

    }

    private void farmDetailsUpdater() {
        farmView.getFarmerExpLabel().setText(" Experience: " + String.valueOf(farmModel.player.getExperience()));
        farmView.getFarmerObjectCoinLabel().setText(" ObjectCoins: " + String.valueOf(farmModel.player.getObjectCoins()));
    }

    private void farmCommandActionListener() {
        ActionListener commandListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object buttonSource = e.getSource();

                if (buttonSource == farmView.getPlowButton()) {
                    System.out.println("Plow Button Action Performed");
                    int finalPosition = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Plow On", "Plow Selection",
                            JOptionPane.QUESTION_MESSAGE));

                    if (JOptionPane.showConfirmDialog(null, "Are you sure to Plow this tile?",
                            "Plow Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        farmModel.player.plowTool(farmModel.plot, (ArrayList<Tool>) farmModel.toolList, finalPosition);
                        farmDetailsUpdater();
                        disableTiles();
                    }

                } else if (buttonSource == farmView.getWaterButton()) {
                    System.out.println("Water Button Action Performed");
                    int finalPosition = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Water On", "Water Selection",
                            JOptionPane.QUESTION_MESSAGE));

                    if (JOptionPane.showConfirmDialog(null, "Are you sure to Water tile?",
                            "Water Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        farmModel.player.waterTool(farmModel.plot, (ArrayList<Tool>) farmModel.toolList, finalPosition);
                        farmDetailsUpdater();
                        disableTiles();
                    }
                } else if (buttonSource == farmView.getFertilizerButton()) {
                    System.out.println("Fertilizer Button Action Performed");
                    int finalPosition = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Fertilize On", "Fertilize Selection",
                            JOptionPane.QUESTION_MESSAGE));

                    if (JOptionPane.showConfirmDialog(null, "Are you sure to Fertilizer this tile?",
                            "Fertilizer Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        farmModel.player.fertilizerTool(farmModel.plot, (ArrayList<Tool>) farmModel.toolList, finalPosition);
                        farmDetailsUpdater();
                        disableTiles();
                    }
                } else if (buttonSource == farmView.getShovelButton()) {
                    System.out.println("Shovel Button Action Performed");
                    int finalPosition = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Shovel On", "Shovel Selection",
                            JOptionPane.QUESTION_MESSAGE));

                    if (JOptionPane.showConfirmDialog(null, "Are you sure to Shovel this tile?",
                            "Shovel Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        farmModel.player.shovelTool(farmModel.plot, (ArrayList<Tool>) farmModel.toolList, finalPosition);
                        farmDetailsUpdater();
                        disableTiles();
                    }
                } else if (buttonSource == farmView.getPickaxeButton()) {
                    System.out.println("Pickaxe Button Action Performed");
                    int finalPosition = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Pickaxe On", "Pickaxe Selection",
                            JOptionPane.QUESTION_MESSAGE));

                    if (JOptionPane.showConfirmDialog(null, "Are you sure to Pickaxe this tile?",
                            "Pickaxe Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        farmModel.player.shovelTool(farmModel.plot, (ArrayList<Tool>) farmModel.toolList, finalPosition);
                        farmDetailsUpdater();
                        disableTiles();
                    }
                } else if (buttonSource == farmView.getRegisterFarmerButton()) {
                    System.out.println("Register Button Action Performed");
                    /*	If player has met the minimum requirements, allow player the option to register into new role.
                     * 	If player has reached maximum level, disable player to register anymore */
                    if (farmModel.player.isRegisterable()) {
                        farmModel.registerFarmer(farmModel.player);
                    } else if (farmModel.player.getRegisterCounter() == 3) {
                        JOptionPane.showInternalMessageDialog(null, "You have reached the maximum status as a Farmer.",
                                "Farmer Registration", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("\tYou have reached the maximum status as a Farmer.");
                    } else {
                        JOptionPane.showInternalMessageDialog(null, "You cannot register farmer at this time.",
                                "Farmer Registration", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("You cannot register farmer at this time.");
                    }
                    farmDetailsUpdater();
                } else if (buttonSource == farmView.getNextDayButton()) {
                    System.out.println("Next day Button Action Performed");
                    if (JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to rest for today?", "Rest Option", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        farmModel.nextDay(farmModel.MyFarm);
                        farmView.getPlotDetailsLabel().setText("Day " + String.valueOf(farmModel.MyFarm.getDaytime()));
                        System.out.println(farmModel.MyFarm.getDaytime());
                    }
                    farmDetailsUpdater();
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
    }

    private void plowMouseListener() {
        farmView.getPlowButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Plow Button Clicked");
                //enableTiles();
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
                //enableTiles();
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

    private void showSeedInfoListener() {
        for (int i = 1; i <= farmView.getSeedShopButtons().size(); i++) {
            int finalI = i - 1;
            farmView.getSeedShopButtons().get(i).addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    farmView.getGameTextLabel().setText(farmModel.seedList.get(finalI).toString());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    farmView.getGameTextLabel().setText("");
                }
            });
        }
    }

    private void showTileInfoListener() {
        for (int i = 1; i <= farmModel.plot.size(); i++) {
            int finalI = i;
            farmView.getFarmPlotButtons().get(i).addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    farmView.getGameTextLabel().setText(farmModel.plot.get(finalI).toString());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    farmView.getGameTextLabel().setText("");
                }
            });
        }
    }

    private void buySeedUpdater() {
        farmDetailsUpdater();
        /* Update Buy Seed Button Restrictions */
        for (int i = 1; i <= farmModel.seedList.size(); i++) {
            farmView.getSeedShopButtons().get(i).setEnabled(false);
        }
    }

    private void buySeedMouseListener() {
        farmView.getBuySeedButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /* Buy Seed Method */
                System.out.println("BuySeed Button");
                /* Enable Buy Seed Button for each Seed so long as the player has enough objectCoins to buy it */
                for (int i = 1; i <= farmModel.seedList.size(); i++) {
                    if (farmModel.player.getObjectCoins() >= farmModel.seedList.get(i - 1).getBuyCost()) {
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
        buySeedActionListener();
    }

    private void buySeedActionListener() {

        farmView.getSeedShopButtons().get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(1, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(0).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(0));
                farmView.getFarmerSeedInventoryButtons().get(1).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(2, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(1).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(1));
                farmView.getFarmerSeedInventoryButtons().get(2).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(3, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(2).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(2));
                farmView.getFarmerSeedInventoryButtons().get(3).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(4).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(4, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(3).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(3));
                farmView.getFarmerSeedInventoryButtons().get(4).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(5).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(5, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(4).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(4));
                farmView.getFarmerSeedInventoryButtons().get(5).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(6).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(6, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(5).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(5));
                farmView.getFarmerSeedInventoryButtons().get(6).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(7).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(7, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(6).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(6));
                farmView.getFarmerSeedInventoryButtons().get(7).setText(inventory);
                buySeedUpdater();
            }
        });
        farmView.getSeedShopButtons().get(8).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farmModel.player.buySeed(8, (ArrayList<Crop>) farmModel.seedList);
                inventory = " " + farmModel.seedList.get(7).getName()
                        + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(7));
                farmView.getFarmerSeedInventoryButtons().get(8).setText(inventory);
                buySeedUpdater();
            }
        });

    }

    private void plantSeedMouseListener() {
        farmView.getPlantSeedButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("Plant Seed Button Clicked");
                /* Access SeedShopButtons Depending on available Seeds */
                int checker = 0;
                for (int i = 0; i < farmModel.seedList.size(); i++) {
                    int inputIndex = i + 1;
                    if (Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(i)) != 0) {
                        farmModel.seedList.get(i).setChoosable(true);
                        farmView.getFarmerSeedInventoryButtons().get(inputIndex).setEnabled(true);
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

    private void plantSeedActionListener() {
        for (int i = 1; i <= farmView.getFarmerSeedInventoryButtons().size(); i++) {
            int finalI = i - 1;
            farmView.getFarmerSeedInventoryButtons().get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Plant On", "Plant Selection",
                            JOptionPane.QUESTION_MESSAGE));

                    if (JOptionPane.showConfirmDialog(null, "Are you sure to Plant on this tile?",
                            "Plant Seed Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (farmModel.checkTileStatus(farmModel.plot, "plant")) {
                            farmModel.player.plantSeed(farmModel.plot, tileIndex, farmModel.seedList.get(finalI));
                            updateFarmerInventory();
                            farmDetailsUpdater();
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
                }
            });
        }
    }

    private void harvestPlantMouseListener() {
        farmView.getHarvestSeedButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Harvest Button Clicked");
                //farmModel.player.plantSeed(farmModel.plot, finalI, farmModel.seedList.get();
                int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Harvest", "Harvest Selection",
                        JOptionPane.QUESTION_MESSAGE));

                if (JOptionPane.showConfirmDialog(null, "Are you sure to Harvest on this Plant?",
                        "Harvest Plant Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (farmModel.checkTileStatus(farmModel.plot, "harvest")) {
                        farmModel.player.harvestPlant(farmModel.player, farmModel.plot, tileIndex);
                        updateFarmerInventory();
                        farmDetailsUpdater();
                    } else {
                        JOptionPane.showInternalMessageDialog(null, "Error! You cannot access this feature unless you have a Plant.",
                                "Harvest Plant Validation", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("Error! You cannot access this feature unless you have a Plant.");
                    }
                }
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {
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
                farmView.getPlotDetailsLabel().setText("Day " + String.valueOf(farmModel.MyFarm.getDaytime()));
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
                        "<html> Take a good nights rest and advance unto the next day. " +
                                "Resting resets all parameters and updates the gameplay.</html> ");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }

    private void nextDayActionListener() {
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

