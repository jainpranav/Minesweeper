/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Pranav Jain
 */
public class GUI 
{
    public JFrame f;
    public JPanel pnlTop;
    public JPanel pnlTopE;
    public JPanel pnlTopC;
    public JPanel pnlTopW;
    public JPanel pnlMinefield;
    public JPanel pnlBottom;
    public JPanel pnlBottomE;
    public JPanel pnlBottomC;
    public JPanel pnlBottomW;
    public JButton[][] mines;
    public JButton highScores;
    public JButton newGame;
    public JButton help;
    public JTextField tfTime;
    public JTextField tfMine;
    public JLabel imgTime;
    public JLabel imgMine;
    public JLabel imgFlag;
    public JLabel animationStatus;
    public ImageIcon iconMine;
    public ImageIcon iconFlag;
    
    // Reference to other Classes
    private Model model;
    private HighScoreTable hs;
    
    
    // Variables for setting the Grid Size
    public String gridSize;
    public int gridSizeH;
    public int gridSizeV;
    public int frameSizeH;
    public int frameSizeV;
    public int numberOfMines;
    
    
    // Variable for setting the Animation Option
    public boolean animate;
    
    // Global Fonts
    private Font font = new Font("SansSerif", 0, 10);
    private Font fontBigger = new Font("SansSerif", 1, 12);
    
    // Colours
    public String backgroundColour = "#151515";
    public String enabledGridColour = "#DDDDDD";
    public String disabledGridColour = "#2E2E2E";
    public String hoverOverGridColor = "#A6BBCC";
    
    // Constructor
    public GUI(String gridSize, Boolean animate)
    {
        if (gridSize.equalsIgnoreCase("advanced"))
        {
            this.gridSize = "advanced";
            this.numberOfMines = 85;
            this.gridSizeH = 16;
            this.gridSizeV = 30;
            this.frameSizeH = 1200;
            this.frameSizeV = 675;
        }
        else if (gridSize.equalsIgnoreCase("intermediate"))
        {
            this.gridSize = "intermediate";
            this.numberOfMines = 40;
            this.gridSizeH = 16;
            this.gridSizeV = 16;
            this.frameSizeH = 600;
            this.frameSizeV = 675;
        }
        else
        {   
            this.gridSize = "beginner";
            this.numberOfMines = 10;
            this.gridSizeH = 8;
            this.gridSizeV = 8;
            this.frameSizeH = 300;
            this.frameSizeV = 375;
        }
        
        // Special Case , tfTime needs to be initialized before new Model 
        setUpTextFields();
        
        this.animate = animate.booleanValue();
        this.hs = new HighScoreTable(this);
        this.model = new Model(this, this.hs);
    
        setUpFrame();
        setUpPanels();
        setPanelBackground();
        setUpButtons();
        hideNewGame();
        setUpImages();
        setUpIcons();
    
        addComponentsToPanels();
        addPanelsToPanels();
        addPanelsToFrame();
    
        // Setting JFrame to be Visible
        this.f.setVisible(true);
        this.f.addKeyListener(new FrameListener(this.model, this));
    }
    
    private void setUpFrame()
    
    {
        this.f = new JFrame("Minesweeper");
        this.f.setLayout(new BorderLayout());
        this.f.setBackground(Color.LIGHT_GRAY);
        this.f.setBounds(0, 0, this.frameSizeH, this.frameSizeV);
        this.f.setLocationRelativeTo(null);
        this.f.setResizable(false);
        this.f.setDefaultCloseOperation(3);
    }
    
    private void setUpPanels()
    {
        this.pnlTop = new JPanel(new BorderLayout());
        this.pnlTopE = new JPanel(new FlowLayout());
        this.pnlTopC = new JPanel(new BorderLayout());
        this.pnlTopW = new JPanel(new FlowLayout());
        this.pnlMinefield = new JPanel(new GridLayout(this.gridSizeH, this.gridSizeV));
        this.pnlBottom = new JPanel(new BorderLayout());
        this.pnlBottomE = new JPanel(new FlowLayout());
        this.pnlBottomC = new JPanel(new FlowLayout());
        this.pnlBottomW = new JPanel(new FlowLayout());
    }
    
    private void setPanelBackground()
    {
        this.pnlTop.setBackground(Color.decode(this.backgroundColour));
        this.pnlTopE.setBackground(Color.decode(this.backgroundColour));
        this.pnlTopC.setBackground(Color.decode(this.backgroundColour));
        this.pnlTopW.setBackground(Color.decode(this.backgroundColour));
        this.pnlMinefield.setBackground(Color.decode(this.backgroundColour));
        this.pnlBottom.setBackground(Color.decode(this.backgroundColour));
        this.pnlBottomE.setBackground(Color.decode(this.backgroundColour));
        this.pnlBottomC.setBackground(Color.decode(this.backgroundColour));
        this.pnlBottomW.setBackground(Color.decode(this.backgroundColour));
    }
    
    private void setUpButtons()
    {
        this.highScores = new JButton("High Scores");
        this.newGame = new JButton("New Game");
        this.help = new JButton("      Help      ");
    
        this.highScores.setFont(this.font);
        this.help.setFont(this.font);
        this.newGame.setFont(this.fontBigger);
    
        this.highScores.setMargin(new Insets(1, 2, 1, 2));
        this.help.setMargin(new Insets(1, 2, 1, 2));
        this.newGame.setMargin(new Insets(1, 5, 1, 5));
    
        this.highScores.setBackground(Color.decode(this.enabledGridColour));
        this.help.setBackground(Color.decode(this.enabledGridColour));
        this.newGame.setBackground(Color.decode(this.enabledGridColour));
    
        this.highScores.setFocusable(false);
        this.newGame.setFocusable(false);
        this.help.setFocusable(false);
    
        this.highScores.addActionListener(new ButtonListener(this, this.model, this.hs));
        this.help.addActionListener(new ButtonListener(this, this.model, this.hs));
        this.newGame.addActionListener(new ButtonListener(this, this.model, this.hs));
    }
    
    private void setUpImages()
    {
        this.imgTime = new JLabel(new ImageIcon(getClass().getResource("/images/stopwatch.png")));
        this.imgMine = new JLabel(new ImageIcon(getClass().getResource("/images/Mine2_032x032_32.png")));
        this.imgFlag = new JLabel(new ImageIcon(getClass().getResource("/images/Flag_024x024_32.png")));
        
        this.animationStatus = new JLabel();
        this.animationStatus.setForeground(Color.decode(this.hoverOverGridColor));
        this.animationStatus.setHorizontalAlignment(0);
        this.animationStatus.setText(this.animate ? "Animations: On" : "Animations: Off");
    
        this.animationStatus.setFont(this.gridSize.equalsIgnoreCase("beginner") ? this.font : this.fontBigger);
    }
    
    private void setUpIcons()
    {
        this.iconMine = new ImageIcon(getClass().getResource("/images/Mine_024x024_32.png"));
        this.iconFlag = new ImageIcon(getClass().getResource("/images/Flag_024x024_32.png"));
    }
    
    private void setUpTextFields()
    {
        this.tfTime = new JTextField(5);
        this.tfMine = new JTextField(5);
    
        this.tfTime.setText("00:00:00");
        this.tfMine.setText(this.numberOfMines + "F / " + this.numberOfMines);
    
        this.tfTime.setFocusable(false);
        this.tfMine.setFocusable(false);
    
        this.tfTime.setFont(this.fontBigger);
        this.tfMine.setFont(this.fontBigger);
    }
    
    private void addComponentsToPanels()
    {
        this.pnlTopW.add(this.imgTime);
        this.pnlTopW.add(this.tfTime);
        this.pnlTopC.add(this.animationStatus, "Center");
        this.pnlTopE.add(this.tfMine);
        this.pnlTopE.add(this.imgMine);
    
        addButtonsToMinefield();
    
        this.pnlBottomW.add(this.highScores);
        this.pnlBottomC.add(this.newGame);
        this.pnlBottomE.add(this.help);
    }
    
    private void addButtonsToMinefield()
    {
        this.mines = new JButton[this.gridSizeH][this.gridSizeV];
        for (int i = 0; i < this.gridSizeH; i++) 
        {
            for (int j = 0; j < this.gridSizeV; j++)
            {
                this.mines[i][j] = new JButton("");
                this.mines[i][j].setFocusable(false);
                this.mines[i][j].setBackground(Color.decode(this.enabledGridColour));
                this.mines[i][j].setMargin(new Insets(1, 1, 1, 1));
                this.mines[i][j].setFont(this.fontBigger);
                this.mines[i][j].addMouseListener(new MineListener(this, this.model));
                this.pnlMinefield.add(this.mines[i][j]);
            }
        }
    }
    
    private void addPanelsToPanels()    
    {
        this.pnlTop.add(this.pnlTopE, "East");
        this.pnlTop.add(this.pnlTopC, "Center");
        this.pnlTop.add(this.pnlTopW, "West");
    
        this.pnlBottom.add(this.pnlBottomE, "East");
        this.pnlBottom.add(this.pnlBottomC, "Center");
        this.pnlBottom.add(this.pnlBottomW, "West");
    }
    
    private void addPanelsToFrame()
    {
        this.f.add(this.pnlTop, "North");
        this.f.add(this.pnlMinefield, "Center");
        this.f.add(this.pnlBottom, "South");
    }
    
    public void hideNewGame()
    {
        this.newGame.setContentAreaFilled(false);
        this.newGame.setBorderPainted(false);
        this.newGame.setForeground(Color.decode(this.backgroundColour));
        this.newGame.setFocusable(false);
    }
    
    public void showNewGame()
    {
        this.newGame.setContentAreaFilled(true);
        this.newGame.setBorderPainted(true);
        this.newGame.setForeground(null);
        this.newGame.setFocusable(true);
    }
    
}
