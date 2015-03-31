/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Pranav Jain
 */
public class ButtonListener implements ActionListener 
{
    // Reference to other Classes
    private Model model;
    private GUI gui;
    private HighScoreTable hs;
    
    // Constructor
    public ButtonListener(GUI gui, Model model, HighScoreTable hs)
    {
        this.model = model;
        this.gui = gui;
        this.hs = hs;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().contains("Help"))
        {
            JOptionPane.showMessageDialog(this.gui.f, "Right Click to Place a Flag.\nLeft Click to Explore !\n\nKeyboard Shortcuts:\nCtrl+b - Change to Beginner Grid\nCtrl+i - Change to Intermediate Grid\nCtrl+a - Change to Advanced Grid\n\nCtrl+t - Toggle Animation\nCtrl+n - Start new game", "Help", 1);
        }
        else if (e.getActionCommand().equals("High Scores"))
        {
            this.hs.showHighScoreWindow();
        }
        else if (e.getActionCommand().equals("New Game"))
        {
            if(this.model.gameFinished) 
            {
                this.model.resetGame();
            }
            this.gui.f.requestFocus();
        }
    }
}
