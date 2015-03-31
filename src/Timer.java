import java.io.*;

/**
 *
 * @author Pranav Jain
 */
public class Timer implements Runnable 
{
    // Reference to other Classes
    private GUI gui;
    private Model model;
    
    // Class Variables
    public int seconds;
    public int minutes;
    public int hours;
    public String strSeconds;
    public String strMinutes;
    public String strHours;
    public boolean stop;
    
    //Constructor
    public Timer(GUI gui , Model model)
    {
        this.gui = gui;
        this.model = model;
        
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
        
        this.strSeconds = "00";
        this.strMinutes = "00";
        this.strHours = "00";
        this.stop = false;
                
    }

    
    @Override
    public void run() 
    {
        while(!this.stop)
        {
            try
            {
                // Sysout used only to refresh the thread
                System.out.print("");
                if(this.model.clickCount > 0)
                {
                    Thread.sleep(1000L);
                    if(this.seconds<59)
                    {
                        this.seconds += 1;
                        this.strSeconds = (this.seconds < 10 ? "0" + Integer.toString(this.seconds) : Integer.toString(this.seconds));
                    }
                    else
                    {
                        this.seconds=0;
                        this.strSeconds = "00";
                        if (this.minutes < 59)
                        {
                            this.minutes += 1;
                            this.strMinutes = (this.minutes < 10 ? "0" + Integer.toString(this.minutes) : Integer.toString(this.minutes));
                            
                        }
                        else
                        {
                            this.minutes = 0;
                            this.strMinutes = "00";
                            
                            this.hours += 1;
                            this.strHours = (this.hours < 10 ? "0" + Integer.toString(this.hours) : Integer.toString(this.hours));
                        }
                    }
                    this.gui.tfTime.setText(this.strHours + ":" + this.strMinutes + ":" + this.strSeconds);
                }
            }
            catch(InterruptedException e)
            {
                this.seconds = 0;
                this.minutes = 0;
                this.hours = 0;
                
                this.strSeconds = "00";
                this.strMinutes = "00";
                this.strHours = "00";
        
                this.stop = true;
            }
        }
    }
    
}
