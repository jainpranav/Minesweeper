import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Pranav Jain
 */
public class FrameListener implements KeyListener 
{
    // Reference to other Classes
    private Model model;
    private GUI gui;
    
    //Constructor
    public FrameListener(Model model, GUI gui)
    {
        this.model = model;
        this.gui = gui;
    }
    
    @SuppressWarnings("deprecation")
    private void changeGridSize(String tempGridSize)
    {
        if (!this.model.gameFinished)
        {
            int response = JOptionPane.showConfirmDialog(this.gui.f, "Changing to the new grid will abandon the current game.\nPress 'Yes' if you wish to proceed");
            if (response == 0)
            {
                this.gui.f.dispose();
                this.model.timerThread.interrupt();
                this.model.timerThread.stop();
                new GUI(tempGridSize, Boolean.valueOf(this.gui.animate));
            }
        }
        else
        {
                this.gui.f.dispose();
                this.model.timerThread.interrupt();
                new GUI(tempGridSize, Boolean.valueOf(this.gui.animate));
        }
    }
    
    private void newGame()
    {
        
        if (!this.model.gameFinished)
        {
            int response = JOptionPane.showConfirmDialog(this.gui.f, "The current game has not been finished.\nAre you sure you want to start a new one?");
            if (response == 0) 
            {
                this.model.resetGame();
            }
        }
        else
        {
                this.model.resetGame();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent arg0)
    {
        if ((arg0.isControlDown()) && (arg0.getKeyCode() == 84)) // Ctrl+t --> For Animation
        {
            this.gui.animate = (!this.gui.animate);
            this.gui.animationStatus.setText(this.gui.animate ? "Animations: On" : "Animations: Off");
        }
        if (((arg0.isControlDown()) && (arg0.getKeyCode() == 66)) || ((arg0.isControlDown()) && (arg0.getKeyCode() == 73)) || ((arg0.isControlDown()) && (arg0.getKeyCode() == 65)))
        {
            String tempGridSize = "";
            if (arg0.getKeyCode() == 83) 
            {
                tempGridSize = "beginner";
            } 
            else if (arg0.getKeyCode() == 77) 
            {
                tempGridSize = "intermediate";
            } 
            else if (arg0.getKeyCode() == 76) 
            {
                tempGridSize = "advanced";
            }
            changeGridSize(tempGridSize);
        }
        if (((arg0.isControlDown()) && (arg0.getKeyCode() == 78)) || ((arg0.isControlDown()) && (arg0.getKeyCode() == 32))) 
        {
            newGame();
        }
    }
  
    @Override
  public void keyReleased(KeyEvent arg0) {}
  
    @Override
  public void keyTyped(KeyEvent arg0) {}
}

