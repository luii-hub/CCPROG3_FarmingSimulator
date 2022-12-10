/*************************************************************************************************************
 This is to certify that this project is of own work, based on my personal efforts in studying and in applying
 the concepts learned throughout this course. All functions, methods, implementations, and algorithms are indeed
 typed and coded by myself. The program was run, tested, and debugged by my own efforts and I certify that
 I have not 100% copied in part or whole or otherwise plagiarized the work of other students and/or persons.

 RANA, Luis Miguel D. F. | 12179124
 CCPROG3 - S21 | Submitted to Prof. Jun Rey Puertollano l.

 This program is a revised version of the previous applications written by the Author.
 *************************************************************************************************************/

package base.gui;

/**
 * The driver class or the runnable application of the GUI MyFarm which holds the Model, View, and Controller of the Application
 */
public class MyFarmApplication {

    public static void main(String[] args) {

        MyFarmView view = new MyFarmView();
        MyFarmModel model = new MyFarmModel();

        MyFarmController controller = new MyFarmController(view, model);
        controller.runGame();
        view.setVisible(true);


    }
}
