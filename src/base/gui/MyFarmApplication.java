package base.gui;

/* Driver Class and Application of GUI */
public class MyFarmApplication {

    public static void main(String[] args) {

        MyFarmView view = new MyFarmView();
        MyFarmModel model = new MyFarmModel();

        MyFarmController controller = new MyFarmController(view, model);
        controller.runGame();
        view.setVisible(true);


    }
}
