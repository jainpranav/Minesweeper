import javax.swing.*;

/**
 *
 * @author Pranav Jain
 */

public class Animation implements Runnable
{
    // Reference to other Classes
    GUI gui;
    Model model;
    
    // Class Variables
    private int currentXOffset;
    private int currentYOffset;
    
    // Constructor
    public Animation(GUI gui, Model model)
    {
        this.gui = gui;
        this.model = model;
        this.currentXOffset = gui.f.getX();
        this.currentYOffset = gui.f.getY();
    }
    
    @Override
    public void run() 
    {
        try
        {
            if (this.gui.gridSize.equalsIgnoreCase("beginner")) 
            {
                for (int i = 1; i < this.gui.numberOfMines; i++)
                {
                    this.gui.f.setBounds(this.currentXOffset + 7, this.currentYOffset + 4, this.gui.frameSizeH, this.gui.frameSizeV);
                    Thread.sleep(50 - this.gui.numberOfMines);
                    this.gui.f.setBounds(this.currentXOffset, this.currentYOffset, this.gui.frameSizeH, this.gui.frameSizeV);
                    Thread.sleep(50 - this.gui.numberOfMines);
                }
            }
            else if (this.gui.gridSize.equalsIgnoreCase("intermediate")) 
            {
                for (int i = 1; i < this.gui.numberOfMines / 3; i++)
                {
                    this.gui.f.setBounds(this.currentXOffset + 10, this.currentYOffset + 3, this.gui.frameSizeH, this.gui.frameSizeV);
                    Thread.sleep(65 - this.gui.numberOfMines);
                    this.gui.f.setBounds(this.currentXOffset, this.currentYOffset, this.gui.frameSizeH, this.gui.frameSizeV);
                    Thread.sleep(65 - this.gui.numberOfMines);
                }
            }
            else if (this.gui.gridSize.equalsIgnoreCase("advanced")) 
            {
                for (int i = 1; i < this.gui.numberOfMines / 3; i++)
                {
                    this.gui.f.setBounds(this.currentXOffset + 11, this.currentYOffset + 3, this.gui.frameSizeH, this.gui.frameSizeV);
                    Thread.sleep(100 - this.gui.numberOfMines);
                    this.gui.f.setBounds(this.currentXOffset, this.currentYOffset, this.gui.frameSizeH, this.gui.frameSizeV);
                    Thread.sleep(100 - this.gui.numberOfMines);
                }
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        
    }
    
}
