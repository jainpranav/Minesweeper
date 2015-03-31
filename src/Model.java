import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;


/**
 *
 * @author Pranav Jain
 */
public class Model 
{
    // Reference to other Classes
    private GUI gui;
    private Timer timer;
    public Thread timerThread;
    private Thread shakeTheScreenThread;
    private HighScoreTable hs;
    
    
    // Class Variables
    public int clickCount = 0;
    public boolean gameFinished;
    public int minesLeftToFind;
    public int flagAllowance;
    private Point[] mineLocations;
    private List<Point> emptyAdjPointsArrayList;
    private boolean[][] isChecked;
  
    
    // Constructor
    public Model(GUI gui, HighScoreTable hs)
    {
        this.gui = gui;
        this.hs = hs;
        
        this.gameFinished = false;
    
        this.minesLeftToFind = gui.numberOfMines;
        this.flagAllowance = this.minesLeftToFind;
        this.mineLocations = new Point[gui.numberOfMines];
        assignRandomMines();
    
        this.isChecked = new boolean[gui.gridSizeH][gui.gridSizeV];
    
        this.timer = new Timer(gui, this);
        this.timerThread = new Thread(this.timer);
        this.timerThread.start();
    }
    
    private void assignRandomMines()
    {
        Random r = new Random();
        boolean safeToAdd = true;
        for (int i = 0; i < this.mineLocations.length; i++) 
        {
            this.mineLocations[i] = new Point();
        }
        for (int i = 0; i < this.mineLocations.length; i++)
        {
            safeToAdd = true;
            int x = r.nextInt(this.gui.gridSizeV);
            int y = r.nextInt(this.gui.gridSizeH);
            
            for (int j = 0; j < this.mineLocations.length; j++) 
            {
                if (i != j) 
                {
                    if ((this.mineLocations[j].x == x) && (this.mineLocations[j].y == y)) // if the pair of random numbers already exist
                    {
                        i--; // reduce value of i by 1 to give it another try. i will be incremented by 1 in the next iteration
                        safeToAdd = false;
                        break;
                    }
                }
            }
            if (safeToAdd)
            {
                this.mineLocations[i].x = x;
                this.mineLocations[i].y = y;
            }
        }
    }
    
    private boolean checkForMines(int i, int k)
    {
        for (int m = 0; m < this.mineLocations.length; m++) 
        {
            if ((this.mineLocations[m].y == i) && (this.mineLocations[m].x == k)) 
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean findAdjacentFlags(int i, int k)
    {
        try
        {
            if ((this.gui.mines[i][k] != null) && (this.gui.mines[i][k].getIcon() == this.gui.iconFlag)) 
            {
                return true;
            }
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
        return false;
    }
    
    private void findAdjacentMines(int i, int k)
    {
        int adjacentMines = 0;
        if (checkForMines(i + 1, k - 1)) 
        {
            adjacentMines++;
        }
        if (checkForMines(i + 1, k)) 
        {
            adjacentMines++;
        }
        if (checkForMines(i + 1, k + 1)) 
        {
            adjacentMines++;
        }
        if (checkForMines(i, k - 1)) 
        {
            adjacentMines++;
        }
        if (checkForMines(i, k + 1)) 
        {
            adjacentMines++;
        }
        if (checkForMines(i - 1, k - 1)) 
        {
            adjacentMines++;
        }
        if (checkForMines(i - 1, k)) 
        {
            adjacentMines++;
        }
        if (checkForMines(i - 1, k + 1)) 
        {
            adjacentMines++;
        }
        isChecked[i][k] = true;
        
        if (adjacentMines > 0)
        {
            this.gui.mines[i][k].setText(Integer.toString(adjacentMines));
            this.gui.mines[i][k].setBackground(Color.decode(this.gui.disabledGridColour));
            this.gui.mines[i][k].setEnabled(false);
        }
        else
        {
            this.gui.mines[i][k].setBackground(Color.decode(this.gui.disabledGridColour));
            this.gui.mines[i][k].setEnabled(false);
            if (i > 0) 
            {
                if (isChecked[(i - 1)][k] == false) 
                {
                    findAdjacentMines(i - 1, k);  // check button above
                }
            }
            if (k < this.gui.mines[i].length - 1) 
            {
                if (isChecked[i][(k + 1)] == false) 
                {
                    findAdjacentMines(i, k + 1); // check button to the right
                }
            }
            if (i < this.gui.mines.length - 1) 
            {
                if (isChecked[(i + 1)][k] == false) 
                {
                    findAdjacentMines(i + 1, k); // check button below
                }
            }
            if (k > 0) 
            {
                if (isChecked[i][(k - 1)] == false) 
                {
                    findAdjacentMines(i, k - 1); // check button to the left
                }
            }
            if ((i > 0) && (k < this.gui.mines[i].length - 1)) 
            {
                if (isChecked[(i - 1)][(k + 1)] == false) 
                {
                    findAdjacentMines(i - 1, k + 1); // check button above right
                }
            }
            if ((i < this.gui.mines.length - 1) && (k < this.gui.mines[i].length - 1))
            {
                if (isChecked[(i + 1)][(k + 1)] == false) 
                {
                    findAdjacentMines(i + 1, k + 1); // check button below right
                }
            }
            if ((i < this.gui.mines.length - 1) && (k > 0)) 
            {
                if (isChecked[(i + 1)][(k - 1)] == false) 
                {
                    findAdjacentMines(i + 1, k - 1);  // check button below left
                }
            }
            if ((i > 0) && (k > 0)) 
            {
                if (isChecked[(i - 1)][(k - 1)] == false) 
                {
                    findAdjacentMines(i - 1, k - 1); // check button above left
                }
            }
        }
    }
    
    private boolean findAdjacentEmpty(int i, int k)
    {
        try
        {
            if ((this.gui.mines[i][k] != null) && (this.gui.mines[i][k].getIcon() == null) && (this.gui.mines[i][k].isEnabled()))
            {
                this.emptyAdjPointsArrayList.add(new Point(i, k));
                return true;
            }
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
        return false;
    }
    
    private void showMines()
    {
        if (this.gui.animate)
        {
            try
            {
                for (int m = 0; m < this.mineLocations.length; m++)
                {
                    this.gui.mines[this.mineLocations[m].y][this.mineLocations[m].x].setIcon(this.gui.iconMine);
                    Thread.sleep(450 / this.gui.numberOfMines);
                    this.gui.pnlMinefield.paintImmediately(0, 0, this.gui.pnlMinefield.getWidth(), this.gui.pnlMinefield.getHeight());
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            for (int m = 0; m < this.mineLocations.length; m++) 
            {
                this.gui.mines[this.mineLocations[m].y][this.mineLocations[m].x].setIcon(this.gui.iconMine);
            }
            this.gui.pnlMinefield.paintImmediately(0, 0, this.gui.pnlMinefield.getWidth(), this.gui.pnlMinefield.getHeight());
        }
    }
    
    private void shakeScreen()
    {
        if (this.gui.animate)
        {
            this.shakeTheScreenThread = null;
            this.shakeTheScreenThread = new Thread(new Animation(this.gui, this));
            this.shakeTheScreenThread.start();
        }
    }
    
    private void simpleSleep(int i)
    {
        try
        {
            Thread.sleep(i);
        }
        catch (InterruptedException e)
        {
            System.out.println("Sleep interrupted");
        }
    }
    
    public void flagLogic(int i, int k)
    {
        if (!this.gameFinished) 
        {
            if (this.gui.mines[i][k].getIcon() == null)
            {
                if (this.flagAllowance > 0)
                {
                    if (this.gui.mines[i][k].isEnabled()) // only place flag if button is enabled
                    {
                        this.flagAllowance -= 1;
                        this.gui.mines[i][k].setIcon(this.gui.iconFlag);
                        this.gui.tfMine.setText(this.flagAllowance + "F / " + this.gui.numberOfMines);
                        if (checkForMines(i, k)) 
                        {
                            this.minesLeftToFind -= 1;
                        }
                        checkWin();
                    }
                }
                else 
                {
                    JOptionPane.showMessageDialog(this.gui.f, "Flag allowance exceeded!");
                }
            }
            else
            {
                this.flagAllowance += 1;
                this.gui.mines[i][k].setIcon(null);
                this.gui.tfMine.setText(this.flagAllowance + "F / " + this.gui.numberOfMines);
                if (checkForMines(i, k)) 
                {
                    this.minesLeftToFind += 1;
                }
            }
        }
    }
    
    public void mineLogic(int i, int k)
    {
        if (!this.gameFinished) 
        {
            if (this.gui.mines[i][k].getIcon() == null) // If there is no flag on that piece
            {
                if (checkForMines(i, k)) // if there is a mine under that piece
                {
                    gameLost();
                }
                else // examine adjacent mines
                {
                    findAdjacentMines(i, k);
                    checkWin();
                }
            }
        }
    }
    
    public void middleClickLogic(int i, int k)
    {
        // Check if its a valid button for this type of action
        if ((this.gui.mines[i][k].getText() != null) && (this.gui.mines[i][k].getText().matches("[1-9]"))) 
        {
            // Check to see if right number of flags are placed
            if (getAdjacentFlagCounter(i, k) == Integer.parseInt(this.gui.mines[i][k].getText())) 
            {
                // If d.mines[i][k] has no empty buttons, surrounding it, then do nothing
                if (getEmptySurroundingButtons(i, k) == 0) 
                {
                    System.out.println("No surrounding empty.");
                } 
               // Find mines for all empty buttons
                else 
                {
                    for (int z = 0; z < this.emptyAdjPointsArrayList.size(); z++) 
                    {
                        mineLogic(((Point)this.emptyAdjPointsArrayList.get(z)).x, ((Point)this.emptyAdjPointsArrayList.get(z)).y);
                    }
                }
            }
        }
    }
    
    private int getEmptySurroundingButtons(int i, int k)
    {
        this.emptyAdjPointsArrayList = new ArrayList();
    
        int emptyCounter = 0;
        if (findAdjacentEmpty(i + 1, k - 1)) 
        {
            emptyCounter++;
        }
        if (findAdjacentEmpty(i + 1, k)) 
        {
            emptyCounter++;
        }
        if (findAdjacentEmpty(i + 1, k + 1)) 
        {
            emptyCounter++;
        }
        if (findAdjacentEmpty(i, k - 1)) 
        {
            emptyCounter++;
        }
        if (findAdjacentEmpty(i, k + 1)) 
        {
            emptyCounter++;
        }
        if (findAdjacentEmpty(i - 1, k - 1)) 
        {
            emptyCounter++;
        }
        if (findAdjacentEmpty(i - 1, k)) 
        {
            emptyCounter++;
        }
        if (findAdjacentEmpty(i - 1, k + 1)) 
        {
            emptyCounter++;
        }
        return emptyCounter;
    }
    
    private int getAdjacentFlagCounter(int i, int k)
    {
        int adjacentFlagCounter = 0;
        if (findAdjacentFlags(i + 1, k - 1)) 
        {
            adjacentFlagCounter++;
        }
        if (findAdjacentFlags(i + 1, k)) 
        {
            adjacentFlagCounter++;
        }
        if (findAdjacentFlags(i + 1, k + 1)) 
        {
            adjacentFlagCounter++;
        }
        if (findAdjacentFlags(i, k - 1)) 
        {
            adjacentFlagCounter++;
        }
        if (findAdjacentFlags(i, k + 1)) 
        {
            adjacentFlagCounter++;
        }
        if (findAdjacentFlags(i - 1, k - 1)) 
        {
            adjacentFlagCounter++;
        }
        if (findAdjacentFlags(i - 1, k)) 
        {
            adjacentFlagCounter++;
        }
        if (findAdjacentFlags(i - 1, k + 1)) 
        {
            adjacentFlagCounter++;
        }
        return adjacentFlagCounter;
    }
    
    private void checkWin()
    {
        if (this.minesLeftToFind == 0)
        {
            boolean allowGameWin = true;
            for (int x = 0; x < this.gui.mines.length; x++)
            {
                for (int y = 0; y < this.gui.mines[x].length; y++) 
                {
                    if (this.gui.mines[x][y].isEnabled()) 
                    {
                        if (this.gui.mines[x][y].getIcon() == null)
                        {
                            allowGameWin = false;
                            break;
                        }
                    }
                }
                if (!allowGameWin) 
                {
                    break;
                }
            }
            if (allowGameWin) 
            {
                gameWon();
            }
        }
    }
    
    private void gameWon()
    {
        // interrupt to stop the clock
        this.timerThread.interrupt();
        

        // sets boolean true, preventing further user interaction with the grid
        this.gameFinished = true;
    

        // congratulations
        JOptionPane.showMessageDialog(this.gui.f, "You Won , You Son of A Gun!");
        String name = JOptionPane.showInputDialog(this.gui.f, "What is your name?", "High Score Entry", 3);
        String time = this.gui.tfTime.getText();
        
        if (name == null) 
        {
            name = "";
        }
        if (name.length() > 14) // if name is too long, truncate it
        {
            name = name.substring(0, 14);
        }
        this.hs.addToHighScores(name, time);
    
	// enable new game button
        this.gui.showNewGame();
    }
    
    private void gameLost()
    {
        // stops the clock
        this.timerThread.interrupt();
    
	// sets boolean true, preventing further user interaction with the grid
        this.gameFinished = true;
    
	// show location of all mines
        shakeScreen();
        showMines();
    
	// A short pause before moving on
        simpleSleep(650);
    

        // pop up message
	//JOptionPane.showMessageDialog(d.f, "Loser!");

        // enable new game button
        this.gui.showNewGame();
    }
    
    public void resetGame()
    {
        // Reset boolean
        this.gameFinished = false;
        
        // Reset isChecked 2D boolean array
        for (int i = 0; i < this.isChecked.length; i++) 
        {
            for (int j = 0; j < this.isChecked[i].length; j++) 
            {
                isChecked[i][j] = false;
            }
        }
        
        // Reset counters
        this.clickCount = 0;
        this.minesLeftToFind = this.gui.numberOfMines;
        this.flagAllowance = this.minesLeftToFind;
        this.gui.tfMine.setText(this.flagAllowance + "F / " + this.gui.numberOfMines);
        
        // Clear state of mine buttons
        for (int i = 0; i < this.gui.gridSizeH; i++) 
        {
            for (int j = 0; j < this.gui.gridSizeV; j++)
            {
                    this.gui.mines[i][j].setIcon(null);
                    this.gui.mines[i][j].setText(null);
                    this.gui.mines[i][j].setBackground(Color.decode(this.gui.enabledGridColour));
                    this.gui.mines[i][j].setEnabled(true);
            }
        }
        
        // Get new random mine locations
        this.mineLocations = null;
        this.mineLocations = new Point[this.gui.numberOfMines];
        assignRandomMines();
    
	// Reset Timer
        this.gui.tfTime.setText("00:00:00");
        this.timerThread.interrupt();
        this.timerThread = new Thread(new Timer(this.gui, this));
        this.timerThread.start();
    
	// Hide 'New Game' Button
        this.gui.hideNewGame();
    }
}
