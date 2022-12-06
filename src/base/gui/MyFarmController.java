package base.gui;

public class MyFarmController {

    private final MyFarmView farmView;
    private final MyFarmModel farmModel;

    MyFarmController(MyFarmView farmView, MyFarmModel model){
        this.farmView = farmView;
        this.farmModel = model;
    }

    public void intializeGame(){
        farmView.init();
        farmView.initializePanels();
        updateGame();

    }

    public void updateGame(){
        updateFarmPlotPanel();
        updateFarmerDetailsPanel();
    }

    private void updateFarmerDetailsPanel(){
        farmView.getFarmerDetailsTitle().setText("- Farmer Details -");
        farmView.getFarmerLevelLabel().setText("Farmer Level: " + String.valueOf(farmModel.player.getFarmerLevel()));
        farmView.getFarmerExpLabel().setText("Experience: " +  String.valueOf(farmModel.player.getExperience()));
        farmView.getFarmerObjectCoinLabel().setText("ObjectCoins: " + String.valueOf(farmModel.player.getObjectCoins()));
        farmView.getFarmerTypeLabel().setText("Status: " + String.valueOf(farmModel.player.getType().getFarmerType()));
    }

    private void updateFarmPlotPanel(){

        farmView.getPlotDetailsLabel().setText("Mi Sakahan\n");
        farmView.getPlotDetailsTitle().setText("Day: " + String.valueOf(farmModel.MyFarm.getDaytime()));

    }
}
