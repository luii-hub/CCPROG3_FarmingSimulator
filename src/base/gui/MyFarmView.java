package base.gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;

public class MyFarmView extends JFrame {

    //Major View Panel
    private JPanel centerPanel = new JPanel();
    private JPanel farmPlotPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel leftPanel = new JPanel();

    //FarmPlotPanel Attributes
    private final HashMap<Integer, JButton> farmPlotButtons = new HashMap<>();

    //Farm Plot Details Panel & Attributes
    private final JPanel plotDetailsPanel = new JPanel();
    private final JLabel plotDetailsLabel = new JLabel();
    private final JLabel plotDetailsTitle = new JLabel();

    //Farmer Detail Panel & Attributes
    private final JPanel farmerDetailsPanel = new JPanel();
    private final JLabel farmerDetailsTitle = new JLabel();
    private final JLabel farmerLevelLabel = new JLabel();
    private final JLabel farmerExpLabel = new JLabel();
    private final JLabel farmerTypeLabel = new JLabel();
    private final JLabel farmerObjectCoinLabel = new JLabel();

    //Farmer Commands Panel & Attributes
    private final JPanel farmerCommandPanel = new JPanel();
    private final JLabel farmerCommandTitle = new JLabel();

    //Farmer Command Buttons
    private final JButton plowButton = new JButton();
    private final JButton waterButton = new JButton();
    private final JButton fertilizerButton = new JButton();
    private final JButton pickaxeButton = new JButton();
    private final JButton shovelButton = new JButton();
    private final JButton plantSeedButton = new JButton();
    private final JButton harvestSeedButton = new JButton();
    private final JButton buySeedButton = new JButton();

    public MyFarmView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    public void init(){

        //LEFT PANEL
        leftPanel.setBackground(Color.CYAN);
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 1280/4, getHeight());
        setFarmerDetailsPanel();

        //RIGHT PANEL
        rightPanel.setBackground(Color.MAGENTA);
        rightPanel.setLayout(null);
        rightPanel.setBounds(960, 0, 1280/4, getHeight());

        //MiDDLE PANEL (TILES)
        centerPanel.setBackground(Color.ORANGE);
        centerPanel.setLayout(null);
        centerPanel.setBounds(1280/4, 0, 640, getHeight());


            plotDetailsPanel.setBackground(Color.PINK);
            plotDetailsLabel.setLayout(null);
            setPlotDetailsPanel();

            farmPlotPanel.setBackground(Color.GREEN);
            farmPlotPanel.setLayout(new GridLayout(10, 5, 5, 5));
            setFarmPlotPanel();

            farmerCommandPanel.setBackground(Color.YELLOW);
            farmerCommandPanel.setLayout(new GridLayout(1, 8, 5, 5));
            setFarmerCommandPanel();

//        //FarmerDetailsPanel
//        farmerDetailsPanel.setBackground(Color.RED);
//        farmerDetailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
//        farmerDetailsPanel.setLayout(new GridLayout(7, 1, 10, 10));
//        setFarmerDetailsPanel();


    }

    public void initializePanels(){

        this.add(leftPanel);
        this.leftPanel.add(farmerDetailsPanel);


        this.add(centerPanel, BorderLayout.CENTER);
        this.centerPanel.add(plotDetailsPanel);
        this.centerPanel.add(farmPlotPanel);
        this.centerPanel.add(farmerCommandPanel);

        this.add(rightPanel, BorderLayout.EAST);
    }

    private void setFarmerDetailsPanel() {
        Font OpenSans = new Font("OpenSans", Font.ITALIC, 18);
        farmerLevelLabel.setFont(OpenSans);
        farmerTypeLabel.setFont(OpenSans);
        farmerExpLabel.setFont(OpenSans);
        farmerObjectCoinLabel.setFont(OpenSans);
        farmerDetailsTitle.setFont(new Font("OpenSans", Font.BOLD | Font.ITALIC, 20));

        farmerDetailsPanel.setBounds(0,0, 320, 240);
        farmerDetailsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        farmerDetailsPanel.setLayout(new GridLayout(5, 1, 1, 1));

        farmerDetailsPanel.add(farmerDetailsTitle);
        farmerDetailsPanel.add(farmerTypeLabel);
        farmerDetailsPanel.add(farmerLevelLabel);
        farmerDetailsPanel.add(farmerExpLabel);
        farmerDetailsPanel.add(farmerObjectCoinLabel);



    }

    private void setPlotDetailsPanel(){

        Font OpenSans = new Font("OpenSans", Font.ITALIC, 24);

        plotDetailsLabel.setFont(OpenSans);
        plotDetailsLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        plotDetailsPanel.setBounds(0, 0, 640, 60);

        plotDetailsPanel.add(plotDetailsLabel);
        plotDetailsPanel.add(plotDetailsTitle);

    }

    private void setFarmPlotPanel() {

        farmPlotPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        farmPlotPanel.setBounds(0, 60, 640, 550);

        for (int position = 1; position <= 50; position++) {
            JButton tileButton = new JButton();
            farmPlotButtons.put(position, tileButton);
            tileButton.setContentAreaFilled(false);
            tileButton.setText("Tile " + position);
            tileButton.setSize(new Dimension(5, 5));
            farmPlotPanel.add(farmPlotButtons.get(position));
        }
    }

    private void setFarmerCommandPanel(){

        plowButton.setSize(100, 100);
        waterButton.setSize(100, 100);
        fertilizerButton.setSize(100, 100);
        shovelButton.setSize(100, 100);
        pickaxeButton.setSize(100, 100);
        buySeedButton.setSize(100, 100);
        plowButton.setSize(100, 100);
        plowButton.setSize(100, 100);

        farmerCommandPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        farmerCommandPanel.setBounds(0, 610, 640, 50);

        farmerCommandPanel.add(plowButton);
        farmerCommandPanel.add(waterButton);
        farmerCommandPanel.add(fertilizerButton);
        farmerCommandPanel.add(shovelButton);
        farmerCommandPanel.add(pickaxeButton);
        farmerCommandPanel.add(buySeedButton);
        farmerCommandPanel.add(plantSeedButton);
        farmerCommandPanel.add(harvestSeedButton);


    }

    public JLabel getFarmerDetailsTitle() {
        return farmerDetailsTitle;
    }
    public JLabel getFarmerExpLabel() {
        return farmerExpLabel;
    }
    public JLabel getFarmerLevelLabel() {
        return farmerLevelLabel;
    }
    public JLabel getFarmerTypeLabel() {
        return farmerTypeLabel;
    }
    public JLabel getFarmerObjectCoinLabel() {
        return farmerObjectCoinLabel;
    }

    public JLabel getPlotDetailsTitle() {
        return plotDetailsTitle;
    }
    public JLabel getPlotDetailsLabel() {
        return plotDetailsLabel;
    }
}
