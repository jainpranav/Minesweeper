/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweepergame;
import java.util.*;
/**
 *
 * @author Pranav Jain
 */
public class SortArrayListInIncreasingOrder implements Comparator<String> 
{

    @Override
    public int compare(String o1, String o2) 
    {
        o1 = o1.substring(o1.indexOf(":") + 1);
        o2 = o2.substring(o2.indexOf(":") + 1);
        
        if (o1.compareTo(o2) < 0) 
        {
            return -1;
        }
        if (o1.compareTo(o2) == 0) 
        {
            return 0;
        }
        return 1;
    }
}
