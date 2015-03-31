import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
/**
 *
 * @author Pranav Jain
 */
public class MineListener implements MouseListener 
{
    // Reference to other Classes
    private GUI gui;
    private Model model;
    
    // Class Variables
    private String mouseFocus;
    private boolean leftMouseButtonPressed;
    private boolean rightMouseButtonPressed;
    
    // Constructor
    public MineListener(GUI gui, Model model)
    {
        this.gui = gui;
        this.model = model;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        for (int i = 0; i < this.gui.mines.length; i++) 
        {
            for (int k = 0; k < this.gui.mines[i].length; k++) 
            {
                if (e.getSource() == this.gui.mines[i][k])  // Gets the [i][k] index values for the button pressed
                {
                    this.mouseFocus = this.gui.mines[i][k].toString(); // Update mouseFocus
                    if (e.getButton() == 1)
                    {
                        this.leftMouseButtonPressed = true;
                    }
                    else if (e.getButton() == 3)
                    {
                        this.rightMouseButtonPressed = true;
                    }
                    else if (e.getButton() == 2)
                    {
                        this.leftMouseButtonPressed = true;
                        this.rightMouseButtonPressed = true;
                    }
                }
            } 
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        for (int i = 0; i < this.gui.mines.length; i++)
        {
            for (int k = 0; k < this.gui.mines[i].length; k++) 
            {
                if ((e.getSource() == this.gui.mines[i][k]) && (this.gui.mines[i][k].toString().equals(this.mouseFocus))) // Gets the [i][k] index values for the button clicked
                {
                    if (this.gui.mines[i][k].isEnabled()) // if the mouse cursor is still on that button
                    {
                        this.gui.mines[i][k].setBackground(Color.decode(this.gui.enabledGridColour));
                    }
                    if ((this.leftMouseButtonPressed) && (this.rightMouseButtonPressed))
                    {
                        this.model.middleClickLogic(i, k);
                        this.leftMouseButtonPressed = false;
                        this.rightMouseButtonPressed = false;
                    }
                    else if ((!this.leftMouseButtonPressed) || (!this.rightMouseButtonPressed))
                    {
                        if (e.getButton() == 3)
                        {
                            this.model.flagLogic(i, k);
                            this.rightMouseButtonPressed = false;
                            this.model.clickCount += 1;
                        }
                        else if (e.getButton() == 1)
                        {
                            this.model.mineLogic(i, k);
                            this.leftMouseButtonPressed = false;
                            this.model.clickCount += 1;
                        }
                    }
                    this.mouseFocus = this.gui.mines[i][k].toString();
                    
                    // Skip to the end of the [i][k] array, since correct button already found and processed
                    i = this.gui.mines.length - 1;
                    k = this.gui.mines[i].length - 1;
                    break;
                }
            }
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e)
    {
        for (int i = 0; i < this.gui.mines.length; i++) 
        {
            for (int k = 0; k < this.gui.mines[i].length; k++) 
            {
                if (e.getSource() == this.gui.mines[i][k]) // Gets the [i][k] index values for the button entered
                {
                    this.mouseFocus = this.gui.mines[i][k].toString();
                    if ((this.gui.mines[i][k].isEnabled()) && (this.gui.mines[i][k].getIcon() == null) && (!this.model.gameFinished)) 
                    {
                        this.gui.mines[i][k].setBackground(Color.decode(this.gui.hoverOverGridColor));
                    }
                }
            }
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e)
    {
        this.mouseFocus = null;
    
        this.leftMouseButtonPressed = false;
        this.rightMouseButtonPressed = false;
        for (int i = 0; i < this.gui.mines.length; i++)
        {
            for (int k = 0; k < this.gui.mines[i].length; k++) 
            {
                if ((e.getSource() == this.gui.mines[i][k]) && (this.gui.mines[i][k].isEnabled()) && (this.gui.mines[i][k].getIcon() == null) && (!this.model.gameFinished)) // Gets the [i][k] index values for the button exited
                {
                    this.gui.mines[i][k].setBackground(Color.decode(this.gui.enabledGridColour));
                }
            }
        }
    }
    
}
