package base.gui;

public class MyFarmApplication {

    public static void main(String[] args) {

        MyFarmView view = new MyFarmView();
        MyFarmModel model = new MyFarmModel();

        MyFarmController controller = new MyFarmController(view, model);
        controller.intializeGame();
        view.setVisible(true);


    }
}
