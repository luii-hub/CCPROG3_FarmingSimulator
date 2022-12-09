package base.gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
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
    private final JButton registerFarmerButton = new JButton();
    private final JButton nextDayButton = new JButton();

    //Farmer Inventory Panel & Attributes
    private final JPanel farmerInventoryPanel = new JPanel();
    private final JLabel farmerInventoryTitle = new JLabel();
    private final HashMap<Integer, JButton> farmerSeedInventoryButtons = new HashMap<>();

    //Game Text Field Presenter
    private final JPanel gameTextPanel = new JPanel();
    private final JLabel gameTextLabel = new JLabel();

    //Farmer's Market Panel & Attributes
    private final JPanel seedShopPanel = new JPanel();
    private final JLabel seedShopTitle = new JLabel();
    private final HashMap<Integer, JButton> seedShopButtons = new HashMap<>();

    //Farmer Commands Panel & Attributes
    private final JPanel farmerCommandPanel = new JPanel();

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
        setSize(1280, 760);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void init() {

        //LEFT PANEL
        leftPanel.setBackground(Color.decode("#70a270"));
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 320, 720);
        setFarmerDetailsPanel();
        setFarmerInventoryPanel();

        //RIGHT PANEL
        rightPanel.setBackground(Color.decode("#70a270"));
        rightPanel.setLayout(null);
        rightPanel.setBounds(960, 0, 320, 720);
        setGameTextLabel();
        setSeedShopPanel();

        //MIDDLE PANEL (TILES)
        //centerPanel.setBackground(Color.decode("#70a270"));
        centerPanel.setLayout(null);
        centerPanel.setBounds(320, 0, 640, 720);

        //plotDetailsPanel.setBackground(Color.decode("#70a270"));
        plotDetailsLabel.setLayout(null);
        setPlotDetailsPanel();

        //farmPlotPanel.setBackground(Color.decode("#70a270"));
        farmPlotPanel.setLayout(new GridLayout(10, 5, 5, 5));
        setFarmPlotPanel();

        //farmerCommandPanel.setBackground(Color.decode("#70a270"));
        farmerCommandPanel.setLayout(new GridLayout(2, 5, 5, 5));
        setFarmerCommandPanel();

    }

    public void initializePanels() {

        this.add(rightPanel);
        this.rightPanel.add(gameTextPanel);
        this.rightPanel.add(seedShopPanel);

        this.add(leftPanel);
        this.leftPanel.add(registerFarmerButton);
        this.leftPanel.add(nextDayButton);
        registerFarmerButton.setBounds(55, 200, 100, 30);
        nextDayButton.setBounds(165, 200, 100, 30);

        this.leftPanel.add(farmerDetailsPanel);
        this.leftPanel.add(farmerInventoryPanel);

        this.add(centerPanel, BorderLayout.CENTER);
        this.centerPanel.add(plotDetailsPanel);
        this.centerPanel.add(farmPlotPanel);
        this.centerPanel.add(farmerCommandPanel);

    }

    private void setFarmerDetailsPanel() {
        Font OpenSansTitle = new Font("OpenSans", Font.BOLD | Font.ITALIC, 20);
        Font OpenSans = new Font("OpenSans", Font.ITALIC, 18);

        farmerDetailsTitle.setFont(OpenSansTitle);
        farmerLevelLabel.setFont(OpenSans);
        farmerTypeLabel.setFont(OpenSans);
        farmerExpLabel.setFont(OpenSans);
        farmerObjectCoinLabel.setFont(OpenSans);

        //farmerDetailsPanel.setBackground(Color.BLUE);
        farmerDetailsPanel.setBounds(0, 0, 320, 240);
        farmerDetailsPanel.setBorder(new EmptyBorder(5, 60, 5, 5));
        farmerDetailsPanel.setLayout(new GridLayout(6, 1, 1, 1));

        farmerDetailsPanel.add(farmerDetailsTitle);
        farmerDetailsPanel.add(farmerTypeLabel);
        farmerDetailsPanel.add(farmerLevelLabel);
        farmerDetailsPanel.add(farmerExpLabel);
        farmerDetailsPanel.add(farmerObjectCoinLabel);

    }

    private void setPlotDetailsPanel() {

        Font OpenSans = new Font("OpenSans", Font.ITALIC, 24);

        plotDetailsTitle.setFont(OpenSans);
        plotDetailsLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        plotDetailsPanel.setBounds(0, 0, 640, 60);

        plotDetailsPanel.add(plotDetailsTitle);
        plotDetailsPanel.add(plotDetailsLabel);
        plotDetailsLabel.setAlignmentX(CENTER_ALIGNMENT);
        plotDetailsTitle.setAlignmentX(CENTER_ALIGNMENT);

    }

    private void setFarmPlotPanel() {

        farmPlotPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        farmPlotPanel.setBounds(0, 60, 640, 550);

        for (int position = 1; position <= 50; position++) {
            JButton tileButton = new JButton();
            farmPlotButtons.put(position, tileButton);
            tileButton.setContentAreaFilled(false);
            tileButton.setSize(new Dimension(5, 5));
            tileButton.setEnabled(false);
            farmPlotPanel.add(farmPlotButtons.get(position));
        }
    }

    private void setFarmerCommandPanel() {

        plowButton.setSize(100, 100);
        waterButton.setSize(100, 100);
        fertilizerButton.setSize(100, 100);
        shovelButton.setSize(100, 100);
        pickaxeButton.setSize(100, 100);
        buySeedButton.setSize(100, 100);
        plowButton.setSize(100, 100);
        plowButton.setSize(100, 100);

        farmerCommandPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
        farmerCommandPanel.setBounds(0, 610, 640, 80);
        plowButton.setText("Hoe");
        waterButton.setText("WaterCan");
        fertilizerButton.setText("Fertilizer");
        shovelButton.setText("Shovel");
        pickaxeButton.setText("Pickaxe");
        buySeedButton.setText("Buy Seed");
        plantSeedButton.setText("Plant Seed");
        harvestSeedButton.setText("Harvest Plant");

        farmerCommandPanel.add(plowButton);
        farmerCommandPanel.add(waterButton);
        farmerCommandPanel.add(fertilizerButton);
        farmerCommandPanel.add(shovelButton);
        farmerCommandPanel.add(pickaxeButton);
        farmerCommandPanel.add(buySeedButton);
        farmerCommandPanel.add(plantSeedButton);
        farmerCommandPanel.add(harvestSeedButton);


    }

    private void setFarmerInventoryPanel() {
        Font OpenSansTitle = new Font("OpenSans", Font.BOLD | Font.ITALIC, 20);
        Font OpenSans = new Font("OpenSans", Font.ITALIC, 18);

        farmerInventoryTitle.setFont(OpenSansTitle);
        //farmerInventoryPanel.setBackground(Color.RED);
        farmerInventoryPanel.setBounds(0, 240, 320, 480);
        farmerInventoryPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        farmerInventoryPanel.setLayout(null);

        farmerInventoryTitle.setBounds(30, 10, 300, 40);
        farmerInventoryPanel.add(farmerInventoryTitle);

        int y_axis = 60;
        for (int position = 1; position <= 8; position++) {
            JButton seedInventoryButton = new JButton();
            farmerSeedInventoryButtons.put(position, seedInventoryButton);
            farmerSeedInventoryButtons.get(position).setBounds(50, y_axis, 220, 40);
            farmerInventoryPanel.add(farmerSeedInventoryButtons.get(position));
            farmerSeedInventoryButtons.get(position).setEnabled(false);
            farmerSeedInventoryButtons.get(position).setFont(OpenSans);
            y_axis += 50;
        }
    }

    private void setSeedShopPanel(){
        Font OpenSansTitle = new Font("OpenSans", Font.BOLD | Font.ITALIC, 20);
        Font OpenSans = new Font("OpenSans", Font.ITALIC, 18);
        seedShopTitle.setFont(OpenSansTitle);

        //seedShopPanel.setBackground(Color.RED);
        seedShopPanel.setBounds(0, 240, 320, 480);
        seedShopPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        seedShopPanel.setLayout(null);

        seedShopTitle.setBounds(40, 10, 300, 40);
        seedShopPanel.add(seedShopTitle);

        int y_axis = 60;
        for (int position = 1; position <= 8; position++) {
            JButton seedButton = new JButton();
            seedShopButtons.put(position, seedButton);
            seedShopButtons.get(position).setBounds(50,y_axis,200, 40);
            seedShopPanel.add(seedShopButtons.get(position));
            seedShopButtons.get(position).setFont(OpenSans);
            y_axis += 50;
        }
    }

    private void setGameTextLabel(){
        Font OpenSansTitle = new Font("OpenSans", Font.BOLD | Font.ITALIC, 20);
        Font OpenSans = new Font("OpenSans", Font.ITALIC, 18);

        gameTextPanel.setBounds(0, 0, 320, 240);
        gameTextPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        gameTextPanel.setLayout(null);

        gameTextLabel.setBounds(25, 40, 250, 200);
        gameTextLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        gameTextPanel.add(gameTextLabel);
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

    public JButton getRegisterFarmerButton() {
        return registerFarmerButton;
    }

    public JButton getNextDayButton() {
        return nextDayButton;
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

    public JLabel getFarmerInventoryTitle() {
        return farmerInventoryTitle;
    }
    public HashMap<Integer, JButton> getFarmerSeedInventoryButtons() {
        return farmerSeedInventoryButtons;
    }

    public JLabel getSeedShopTitle() {
        return seedShopTitle;
    }
    public HashMap<Integer, JButton> getSeedShopButtons() {
        return seedShopButtons;
    }

    public JLabel getGameTextLabel() {
        return gameTextLabel;
    }

    public JButton getPlowButton() {
        return plowButton;
    }

    public JButton getWaterButton() {
        return waterButton;
    }

    public JButton getFertilizerButton() {
        return fertilizerButton;
    }

    public JButton getShovelButton() {
        return shovelButton;
    }

    public JButton getPickaxeButton() {
        return pickaxeButton;
    }

    public JButton getBuySeedButton() {
        return buySeedButton;
    }

    public JButton getPlantSeedButton() {
        return plantSeedButton;
    }

    public JButton getHarvestSeedButton() {
        return harvestSeedButton;
    }

    public HashMap<Integer, JButton> getFarmPlotButtons() {
        return farmPlotButtons;
    }

    public JPanel getFarmerDetailsPanel() {
        return farmerDetailsPanel;
    }
}
