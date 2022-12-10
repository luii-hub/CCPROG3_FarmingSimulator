package base.gui;
import base.myFarm.Crop;
import base.myFarm.CropType;
import base.myFarm.Tool;

import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * This is the Controller Class of MyFarm where all the interactions between the view and the model take place.
 */
public class MyFarmController {
    /** View of the GUI*/
    private final MyFarmView farmView;
    /** Model of the GUI */
    private final MyFarmModel farmModel;
    /** Forces decimal values to have a decimal place */
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    /** String Name of Seed in the Inventory*/
    String inventory = null;

    /**
     * Constructor for the Controller of MyFarm in MVC
     * @param farmView MyFarm View Class
     * @param model MyFarm Model Class
     */
    MyFarmController(MyFarmView farmView, MyFarmModel model) {
        this.farmView = farmView;
        this.farmModel = model;
    }

    /**
     * This Method is basically the "start game" or the method where is nests all the necessary functions such as game initializations,
     * game and file updaters, event listeners, and commands.
     */
    public void runGame() {
        farmView.init();
        farmView.initializePanels();
        updateGame();
        checkEndGame();
        farmMarket();
        actionCommand();
    }

    /**
     * This method checks where the program should stop running if some conditions are met and
     * will determine if the program should keep running or not once certain conditions are met.
     */
    private void checkEndGame(){
        if(!farmModel.checkGameConditions(farmModel.isRunning())){
            JOptionPane.showMessageDialog(null,
                    "<html> Game Over! <p><p> You cannot continue anymore because you have failed in being a Farmer.",
                    "Game Over!", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null,
                    String.valueOf(farmModel.player.getFarmerStats().toString()),
                    "Final Game Statistics!", JOptionPane.INFORMATION_MESSAGE);
            farmView.getPlowButton().setEnabled(false);
            farmView.getWaterButton().setEnabled(false);
            farmView.getFertilizerButton().setEnabled(false);
            farmView.getPickaxeButton().setEnabled(false);
            farmView.getShovelButton().setEnabled(false);
            farmView.getRegisterFarmerButton().setEnabled(false);
            farmView.getPlantSeedButton().setEnabled(false);
            farmView.getHarvestSeedButton().setEnabled(false);
            farmView.getBuySeedButton().setEnabled(false);
            farmView.getNextDayButton().setEnabled(false);
        }
    }

    /**
     * This method nests all the game updaters and label updaters. Once this method is called,
     * it updates what is being viewed by the user in the interface.
     */
    public void updateGame() {
        updateSeedShop();
        updateFarmerLevel();
        updateFarmPlotPanel();
        updateFarmerDetailsPanel();
        updateFarmerInventory();
    }

    /**
     * This method updates the farmer details in the farmer panel located at the top left of the screen,
     * and it updates what is inside the farmer details interface.
     */
    private void updateFarmerDetailsPanel() {
        farmView.getFarmerDetailsTitle().setText(" - Farmer Details -");
        farmView.getFarmerLevelLabel().setText(" Farmer Level: " + farmModel.player.getFarmerLevel());
        farmView.getFarmerExpLabel().setText(" Experience: " + decimalFormat.format(farmModel.player.getExperience()));
        farmView.getFarmerObjectCoinLabel().setText(" ObjectCoins: " + decimalFormat.format(farmModel.player.getObjectCoins()));
        farmView.getFarmerTypeLabel().setText(" Status: " + farmModel.player.getType().getFarmerType());
        farmView.getRegisterFarmerButton().setText(" Register");
        farmView.getNextDayButton().setText(" Rest");

    }

    /**
     * Updates the farm plot in the game board, and it updates what is inside
     * the farm plot interface once certain attributes are manipulated by certain events.
     */
    private void updateFarmPlotPanel() {
        farmView.getPlotDetailsTitle().setText("Mi Sakahan");
        farmView.getPlotDetailsLabel().setText("Day: " + farmModel.MyFarm.getDaytime());
    }

    /**
     * Updates the farmer inventory located on the left side of the screen; and refreshes
     * whether the farmer buys a seed or plants a seed, it updates the amount of each seed that the farmer has.
     */
    private void updateFarmerInventory() {
        farmView.getFarmerInventoryTitle().setText(" - Farmer Seed Inventory -");
        for (int i = 0; i < farmModel.seedList.size(); i++) {
            int inputIndex = i + 1;
            inventory = " " + farmModel.seedList.get(i).getName() + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(i));
            farmView.getFarmerSeedInventoryButtons().get(inputIndex).setText(inventory);
        }
    }

    /**
     * Updates the farmer's level once the farmer's level reaches a certain threshold and
     * also allows the farmer to register once the farmer reaches a certain level.
     */
    public void updateFarmerLevel() {
        /* Reset the Farmer's Exp Counter since the CAP is 100 */
        if (farmModel.player.getExperience() >= 100){
            while (farmModel.player.getExperience() >= 100) {
                farmModel.player.setFarmerLevel(farmModel.player.getFarmerLevel() + 1);
                farmModel.player.addExperience((-100));
            }
            System.out.println("\tCongratulations, you have leveled up! You are now Level " + farmModel.player.getFarmerLevel());
        }
        /* Allow the farmer to register once he reaches a certain level  */
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

    /**
     * Generates and setups the farmer market or the seed shop for the user.
     */
    private void farmMarket() {
        farmView.getSeedShopTitle().setText("- Farmer SuperMarket -");
        for (int i = 0; i < farmModel.seedList.size(); i++) {
            int inputIndex = i + 1;
            farmView.getSeedShopButtons().get(inputIndex).setEnabled(false);
            inventory = farmModel.seedList.get(i).getName() + " Seed";
            farmView.getSeedShopButtons().get(inputIndex).setText(inventory);
        }
    }

    /**
     * An all-in-one method where it nests all the event listeners and holds all the
     * farmer's action commands. This contains the farmer tool options, farmers self options, and also what the
     * farmer basically wants to do within the game.
     */
    private void actionCommand() {

        /* Misc Methods & Details */
        showTileInfoListener();
        updateTileStatuses();
        farmerStatusMouseListener();

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

    /**
     * Updates every tile and its tile status depending on after a tile has been manipulated or not by the user.
     * Once this method is called, it sets the tile status for all the tiles in the farm-plot in the interface
     */
    private void updateTileStatuses(){
        for (int position = 1; position <= 50; position++) {
            farmView.getFarmPlotButtons().get(position).setText(String.valueOf(farmModel.plot.get(position).getStatus()));
        }
    }

    /**
     * Updates the farmer's options at the farmer supermarket or seed shop given certain conditions. If the farmer does not have
     * enough ObjectCoins to purchase a seed, That seed's button will be disabled by the user at the interface.
     */
    private void updateSeedShop() {

        /* Buy Seed Conditions */
        farmView.getBuySeedButton().setEnabled(!(farmModel.player.getObjectCoins() <= 0));

    }

    /**
     * An event listener for the Farmer Status Button which listens to the cursor and mouse movements of the user.
     * Once this method is called, it changes some attributes of the game label, and it manipulates information
     * on the farmer details label for the user's convenience.
     */
    private void farmerStatusMouseListener(){
        farmView.getFarmerDetailsPanel().addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(farmModel.player.getType().toString());
            }
            @Override public void mouseExited(MouseEvent e) {
                farmView.getGameTextLabel().setText("");
            }
        });
    }

    /**
     * A multipurpose method contains all the action listeners for every farmer command action (button).
     * This method make sures that only one button can only be executed, and it listens to that button after the user
     * clicks on that button (via mouseListener).
     */
    private void farmCommandActionListener() {
        ActionListener commandListener = e -> {
            Object buttonSource = e.getSource();

            /* Plow Tool Button Action Command */
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
                                checkEndGame();
                            }
                        }
                        case SEED, PLANT, TREE, WITHERED, ROCK, PLOWED -> JOptionPane.showMessageDialog(null,
                                "Error! You can only use the Hoe at Unplowed Tiles!",
                                "Plow Tool Tile Validation", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception error) {
                    displayErrorMessage();
                }
            }
            /* Water Tool Button Action Command */
            else if (buttonSource == farmView.getWaterButton()) {
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
                        case PLOWED, PLANT, UNPLOWED, ROCK, TREE, WITHERED -> JOptionPane.showMessageDialog(null,
                                "Error! You can only water tiles that contains a Seed!",
                                "Water Seed Tile Validation", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception error) {
                    displayErrorMessage();
                }
                checkEndGame();
            }
            /* Fertilizer Tool Button Action Command */
            else if (buttonSource == farmView.getFertilizerButton()) {
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
                        case PLOWED, PLANT, UNPLOWED, ROCK, TREE, WITHERED -> JOptionPane.showMessageDialog(null,
                                "Error! You can only fertilize tiles that contains a Seed!",
                                "Fertilize Seed Tile Validation", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception error) {
                    displayErrorMessage();
                }
                checkEndGame();
            }
            /* Shovel Tool Button Action Command */
            else if (buttonSource == farmView.getShovelButton()) {
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
                checkEndGame();
            }
            /* Pickaxe Tool Button Action Command */
            else if (buttonSource == farmView.getPickaxeButton()) {
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
                        case SEED, PLANT, PLOWED, UNPLOWED, TREE, WITHERED -> JOptionPane.showMessageDialog(null,
                                "Error! You can only use the Pickaxe Tool on Tiles that contains a Rock!",
                                "Pickaxe Tool Tile Validation", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception error) {
                    displayErrorMessage();
                }
                checkEndGame();
            }
            /* Register Farmer Button Action Command */
            else if (buttonSource == farmView.getRegisterFarmerButton()) {
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
                checkEndGame();
            }
            /* Rest Farmer (Next Day) Button Action Command */
            else if (buttonSource == farmView.getNextDayButton()) {
                System.out.println("Next day Button Action Performed");
                try {
                    if (JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to rest for today?", "Rest Option", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        farmModel.nextDay(farmModel.MyFarm);
                        farmView.getPlotDetailsLabel().setText("Day " + farmModel.MyFarm.getDaytime());
                        System.out.println(farmModel.MyFarm.getDaytime());
                    }
                    updateTileStatuses();
                    updateGame();
                } catch (Exception error) {
                    displayErrorMessage();
                }
                checkEndGame();
            }
            /* Quit Game Action Command */
            else if (buttonSource == farmView.getQuitGameButton()){
                try {
                    if (JOptionPane.showConfirmDialog(null,
                            "Are you sure you to quit game?", "Quit Game Option", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                    updateTileStatuses();
                    updateGame();
                } catch (Exception error) {
                    displayErrorMessage();
                }
            }
        };
        /* Add an action listener for every farmer action command button */
        farmView.getPlowButton().addActionListener(commandListener);
        farmView.getWaterButton().addActionListener(commandListener);
        farmView.getFertilizerButton().addActionListener(commandListener);
        farmView.getPickaxeButton().addActionListener(commandListener);
        farmView.getShovelButton().addActionListener(commandListener);
        farmView.getRegisterFarmerButton().addActionListener(commandListener);
        farmView.getNextDayButton().addActionListener(commandListener);
        farmView.getQuitGameButton().addActionListener(commandListener);
        updateTileStatuses();
    }

    /**
     * An event listener for the Plow Option which listens to the cursor and mouse movements of the user.
     * Once this method is called, it changes some attributes of the game label for the Plow Button,
     * and it manipulates information on the farmer details label for the user's convenience.
     */
    private void plowMouseListener() {
        farmView.getPlowButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(
                        "<html> Plow Tool | Cost 0 ObjectCoins <p><p>" +
                                "Converts an unplowed tile to a plowed tile. Can only be performed on an unplowed tile. </html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
        });
    }

    /**
     * An event listener for the WaterCan Option which listens to the cursor and mouse movements of the user.
     * Once this method is called, it changes some attributes of the game label for the WaterCan Button,
     * and it manipulates information on the farmer details label for the user's convenience.
     */
    private void waterMouseListener() {
        farmView.getWaterButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(
                        "<html> WaterCan Tool | Cost 0 ObjectCoins <p><p>" +
                                "Adds to the total number of times a tile/crop has been watered. " +
                                "Can only be performed on a plowed tile with a crop. </html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
        });
    }

    /**
     * An event listener for the Fertilize Option which listens to the cursor and mouse movements of the user.
     * Once this method is called, it changes some attributes of the game label for the Fertilizer Button,
     * and it manipulates information on the farmer details label for the user's convenience.
     */
    private void fertMouseListener() {
        farmView.getFertilizerButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(
                        "<html> Fertilizer Tool | Cost 10 ObjectCoins <p><p>" +
                                "Adds to the total number of times a tile/crop has been applied with fertilizer. " +
                                "Can only be performed on a plowed tile with a crop. ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
        });
    }

    /**
     * An event listener for the Shovel Option which listens to the cursor and mouse movements of the user.
     * Once this method is called, it changes some attributes of the game label for the Shovel Button,
     * and it manipulates information on the farmer details label for the user's convenience.
     */
    private void shovelMouseListener() {
        farmView.getShovelButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Shovel Tool | Cost 7 ObjectCoins <p><p>" +
                                "Removes a withered plant from a tile. " +
                                "Can be used on any tile/crop with varying effects, as described above. </html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
        });
    }

    /**
     * An event listener for the Pickaxe Option which listens to the cursor and mouse movements of the user.
     * Once this method is called, it changes some attributes of the game label for the Pickaxe Button,
     * and it manipulates information on the farmer details label for the user's convenience.
     */
    private void pickaxeMouseListener() {
        farmView.getPickaxeButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Pickaxe Tool | Cost 50 ObjectCoins <p><p> + " +
                                "Removes a rock from a tile. Can only be applied to tiles with a rock. </html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
        });
    }

    /**
     * An event listener for the Seed Shop Buttons which listens to the cursor and mouse movements of the user.
     * Once this method is called, it changes some attributes of the game label for each of the Seed Buttons,
     * It shows important information for every seed.
     */
    private void showSeedInfoListener() {
        for (int i = 1; i <= farmView.getSeedShopButtons().size(); i++) {
            int finalI = i - 1;
            farmView.getSeedShopButtons().get(i).addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(farmModel.seedList.get(finalI).toString());}
                @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
            });
            farmView.getFarmerSeedInventoryButtons().get(i).addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(farmModel.seedList.get(finalI).toString());}
                @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
            });
        }
    }

    /**
     * An event listener for each of the tiles in the plot which listens to the cursor and mouse movements of the user.
     * Once this method is called, it changes some attributes of the game label for one Tile Button,
     * Each tile contains different information, hence this method shows vital information of a certain tile
     * at the game label for the user's convenience.
     */
    private void showTileInfoListener() {
        for (int i = 1; i <= farmModel.plot.size(); i++) {
            int finalI = i;
            farmView.getFarmPlotButtons().get(i).addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) {farmView.getGameTextLabel().setText(farmModel.plot.get(finalI).toString());}
                @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
            });
        }
    }

    /**
     * Disables the options at the seed shop after buying a certain seed, and it updates after
     * the user executed the buy seed method.
     */
    private void buySeedUpdater() {
        updateGame();
        /* Update Buy Seed Button Restrictions */
        for (int i = 1; i <= farmModel.seedList.size(); i++) {
            farmView.getSeedShopButtons().get(i).setEnabled(false);
        }
    }

    /**
     * An event listener for the Buy Seed Option which listens to the cursor and mouse movements of the user.
     * Once this method is called, it changes some attributes of the game label for the buy Seed Button,
     * This method shows vital information of the BuySeed button at the game label for the user's convenience.
     */
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
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
        });
        buySeedActionListener();
    }

    /**
     * Listens to what the user clicks at a certain button from the farmer market or seed shop. This multipurpose
     * actionListener method contains every listener for each of the seed buttons at the farmer supermarket. Once a button has
     * been selected, it executes the buySeed method from the Model and disables the seed shop right after.
     */
    private void buySeedActionListener() {
        farmView.getSeedShopButtons().get(1).addActionListener(e -> {
            farmModel.player.buySeed(1, (ArrayList<Crop>) farmModel.seedList);
            inventory = " " + farmModel.seedList.get(0).getName()
                    + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(0));
            farmView.getFarmerSeedInventoryButtons().get(1).setText(inventory);
            buySeedUpdater();
        });
        farmView.getSeedShopButtons().get(2).addActionListener(e -> {
            farmModel.player.buySeed(2, (ArrayList<Crop>) farmModel.seedList);
            inventory = " " + farmModel.seedList.get(1).getName()
                    + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(1));
            farmView.getFarmerSeedInventoryButtons().get(2).setText(inventory);
            buySeedUpdater();
        });
        farmView.getSeedShopButtons().get(3).addActionListener(e -> {
            farmModel.player.buySeed(3, (ArrayList<Crop>) farmModel.seedList);
            inventory = " " + farmModel.seedList.get(2).getName()
                    + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(2));
            farmView.getFarmerSeedInventoryButtons().get(3).setText(inventory);
            buySeedUpdater();
        });
        farmView.getSeedShopButtons().get(4).addActionListener(e -> {
            farmModel.player.buySeed(4, (ArrayList<Crop>) farmModel.seedList);
            inventory = " " + farmModel.seedList.get(3).getName()
                    + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(3));
            farmView.getFarmerSeedInventoryButtons().get(4).setText(inventory);
            buySeedUpdater();
        });
        farmView.getSeedShopButtons().get(5).addActionListener(e -> {
            farmModel.player.buySeed(5, (ArrayList<Crop>) farmModel.seedList);
            inventory = " " + farmModel.seedList.get(4).getName()
                    + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(4));
            farmView.getFarmerSeedInventoryButtons().get(5).setText(inventory);
            buySeedUpdater();
        });
        farmView.getSeedShopButtons().get(6).addActionListener(e -> {
            farmModel.player.buySeed(6, (ArrayList<Crop>) farmModel.seedList);
            inventory = " " + farmModel.seedList.get(5).getName()
                    + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(5));
            farmView.getFarmerSeedInventoryButtons().get(6).setText(inventory);
            buySeedUpdater();
        });
        farmView.getSeedShopButtons().get(7).addActionListener(e -> {
            farmModel.player.buySeed(7, (ArrayList<Crop>) farmModel.seedList);
            inventory = " " + farmModel.seedList.get(6).getName()
                    + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(6));
            farmView.getFarmerSeedInventoryButtons().get(7).setText(inventory);
            buySeedUpdater();
        });
        farmView.getSeedShopButtons().get(8).addActionListener(e -> {
            farmModel.player.buySeed(8, (ArrayList<Crop>) farmModel.seedList);
            inventory = " " + farmModel.seedList.get(7).getName()
                    + " Seed(s): " + Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(7));
            farmView.getFarmerSeedInventoryButtons().get(8).setText(inventory);
            buySeedUpdater();
        });

    }

    /**
     * Listens to what is being clicked, or hovered by the user in the Plant Seed Button. Once this method is called,
     * It shows vital information at the game label panel for the user's convenience, and it also enables the farmer inventory
     * buttons depending on the seeds that the farmer currently owns.
     */
    private void plantSeedMouseListener() {
        farmView.getPlantSeedButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                System.out.println("Plant Seed Button Clicked");
                /* Access FarmerInventoryButtons Depending on available Seeds */
                boolean isAccessible = false;
                for (int i = 0; i < farmModel.seedList.size(); i++) {
                    int inputIndex = i + 1;
                    if (Collections.frequency(farmModel.player.getInventory(), farmModel.seedList.get(i)) != 0) {
                        farmModel.seedList.get(i).setChoosable(true);
                        farmView.getFarmerSeedInventoryButtons().get(inputIndex).setEnabled(true);
                        isAccessible = true;
                    }
                }
                /* Disable plant seed method for user if the farmer does not have any seeds in his/her inventory */
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
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
        });
    }

    /**
     * An action Listener for the plantSeed Method; once this method has been called and once the user clicks
     * on the button, it executes the plantSeed Method from the Model, and it updates certain attributes at the model and updates
     * some information at the interface after execution.
     */
    private void plantSeedActionListener() {
        for (int i = 1; i <= farmView.getFarmerSeedInventoryButtons().size(); i++) {
            int finalI = i - 1;
            farmView.getFarmerSeedInventoryButtons().get(i).addActionListener(e -> {
                try {
                    int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Plant On", "Plant Selection",
                            JOptionPane.QUESTION_MESSAGE));
                    if (JOptionPane.showConfirmDialog(null, "Are you sure to Plant on this tile?",
                            "Plant Seed Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (farmModel.checkTileStatus(farmModel.plot, "plant")) {
                            if(farmModel.seedList.get(finalI).getType().equals(CropType.FRUIT_TREE)) {
                                if (farmModel.plot.get(tileIndex).isEdge()) {
                                    System.out.println("\tError! You are unable to plant a Fruit Tree at the edges of the farm plot.");
                                    JOptionPane.showInternalMessageDialog(null,
                                            "Error! You are unable to plant a Fruit Tree at the edges of the farm plot.",
                                            "Plant Seed Validation", JOptionPane.INFORMATION_MESSAGE);
                                    System.out.println("Error! You can only plant on unoccupied, PLOWED tiles.");
                                } else {
                                    /* If selected tile in not an edge, check the neighboring tiles if they are occupied, if there are no neighboring objects, continue plantSeed */
                                    if (farmModel.checkSurroundings(1, tileIndex, farmModel.plot)) {
                                        farmModel.player.plantSeed(farmModel.plot, tileIndex, farmModel.seedList.get(finalI));
                                        farmModel.plot.get(tileIndex).setPlantable(false);
                                    } else {
                                        System.out.println("\tError! You are unable to plant a Fruit Tree because it needs a tile of space to grow.");
                                        JOptionPane.showInternalMessageDialog(null,
                                                "Error! You are unable to plant a Fruit Tree because it needs a tile of space to grow.",
                                                "Plant Seed Validation", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                            }
                            else{
                                /* If Seed is not a FruitTree proceed to execute plantSeed method and continue as is */
                                farmModel.player.plantSeed(farmModel.plot, tileIndex, farmModel.seedList.get(finalI));
                                farmModel.plot.get(tileIndex).setPlantable(false);
                            }
                            updateGame();
                        } else {
                            JOptionPane.showInternalMessageDialog(null, "Error! You can only plant on unoccupied, PLOWED tiles.",
                                    "Plant Seed Validation", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Error! You can only plant on unoccupied, PLOWED tiles.");
                        }
                    }
                    /* Update Farmer Inventory Seed Button Restrictions */
                    for (int i1 = 1; i1 <= farmModel.seedList.size(); i1++) {
                        farmView.getFarmerSeedInventoryButtons().get(i1).setEnabled(false);
                    }
                    updateTileStatuses();
                    updateGame();
                } catch (Exception error){
                    /* Update Farmer Inventory Seed Button Restrictions */
                    for (int i1 = 1; i1 <= farmModel.seedList.size(); i1++) {
                        farmView.getFarmerSeedInventoryButtons().get(i1).setEnabled(false);
                    }
                    JOptionPane.showInternalMessageDialog(null, "Cancelled Plant Seed Action", "Plant Seed Validation", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }
    }

    /**
     * An action Listener for the harvestPlant Method; once this method has been called and once the user clicks
     * on the button, it executes the harvestPlant Method from the Model, and it updates certain attributes at the model and updates
     * some information at the interface after execution. It also gives the user feedback on the amount of crops that the user harvested,
     * the amount of objectCoins the user gained, and the amount of experience the user earned. It also resets that tile after execution.
     */
    private void harvestPlantMouseListener() {
        farmView.getHarvestSeedButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                System.out.println("Harvest Button Clicked");
                try {
                    int tileIndex = Integer.parseInt(JOptionPane.showInputDialog(null, "Select a Tile that you want to Harvest", "Harvest Selection",
                            JOptionPane.QUESTION_MESSAGE));
                    switch (farmModel.plot.get(tileIndex).getStatus()) {
                        case PLANT, TREE -> {
                            if (JOptionPane.showConfirmDialog(null, "Are you sure to Harvest on this Plant?",
                                    "Harvest Plant Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                if (farmModel.checkTileStatus(farmModel.plot, "harvest")) {
                                    String feedback = farmModel.player.harvestPlant(farmModel.player, farmModel.plot, tileIndex);
                                    updateGame();
                                    JOptionPane.showMessageDialog(
                                            null, feedback,
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
                        case SEED, ROCK, WITHERED, PLOWED, UNPLOWED -> {
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
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
        });
    }

    /**
     * Displays some information at the game label about the Register Farmer Option interface after certain cursor movements and conditions
     * are met. This simply gives information depending on what is being labelled at the registerFarmer Button and some conditions for registering.
     */
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
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
        });
    }

    /**
     * Displays some information about the Rest or Next Day Option at the game label interface after certain cursor movements and conditions
     * are met. This simply gives information depending on what is being labelled at the Rest Button and what to expect after this button is clicked.
     */
    private void nextDayMouseListener() {
        farmView.getNextDayButton().addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                farmView.getPlotDetailsLabel().setText("Day " + farmModel.MyFarm.getDaytime());
                updateFarmerDetailsPanel();
            }
            @Override public void mouseEntered(MouseEvent e) {
                farmView.getGameTextLabel().setText(
                        "<html> Take a good nights rest and advance unto the next day. " +
                                "Resting resets all parameters and updates the gameplay.</html> ");
            }
            @Override public void mouseExited(MouseEvent e) {farmView.getGameTextLabel().setText("Game User-Feedback Provider");}
        });
    }

    /**
     * Simply throws an Error Message at the user if certain conditions are not met, or if the user wants to cancel
     * his or her action command. This error message will be called and displayed at the interface.
     */
    private void displayErrorMessage() {
        JOptionPane.showMessageDialog(null,
                "Error!  Command action got disrupted OR Command action was unable to be executed.",
                "Error Message", JOptionPane.ERROR_MESSAGE);
    }
}