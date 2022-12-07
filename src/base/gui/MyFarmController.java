package base.gui;

import java.awt.Component;
import java.util.Collections;

public class MyFarmController {

    private final MyFarmView farmView;
    private final MyFarmModel farmModel;
    String inventory = null;

    MyFarmController(MyFarmView farmView, MyFarmModel model){
        this.farmView = farmView;
        this.farmModel = model;
    }

    public void intializeGame(){
        farmView.init();
        farmView.initializePanels();
        updateGame();
        farmMarket();

    }

    public void updateGame(){
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

        farmView.getPlotDetailsLabel().setText("Mi Sakahan");
        farmView.getPlotDetailsTitle().setText("Day: " + String.valueOf(farmModel.MyFarm.getDaytime()));

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
            inventory = farmModel.seedList.get(i).getName() + " Seed";
            farmView.getSeedShopButtons().get(inputIndex).setText(inventory);
            System.out.println(inventory);

        }
    }
}
