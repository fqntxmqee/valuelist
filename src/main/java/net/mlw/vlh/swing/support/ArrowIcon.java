/**
 * Copyright (c) 2003 held jointly by the individual authors.            
 *                                                                          
 * This library is free software; you can redistribute it and/or modify it    
 * under the terms of the GNU Lesser General Public License as published      
 * by the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.                                            
 *                                                                            
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; with out even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU Lesser General Public License for more details.                                                  
 *                                                                           
 * You should have received a copy of the GNU Lesser General Public License   
 * along with this library;  if not, write to the Free Software Foundation,   
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.              
 *                                                                            
 * > http://www.gnu.org/copyleft/lesser.html                                  
 * > http://www.opensource.org/licenses/lgpl-license.php
 */
package net.mlw.vlh.swing.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.SwingConstants;

/**
 * @author Matthew L. Wilson
 * @version $Revision: 1.1 $ $Date: 2005/03/16 12:16:23 $
 */
public class ArrowIcon implements Icon
{
   private int width = 4;

   private int height = 8;

   private int numberOfArrows;

   private int[] xPoints = new int[4];

   private int[] yPoints = new int[4];

   public ArrowIcon(int direction)
   {
      this(direction, 1);
   }

   public ArrowIcon(int direction, int numberOfArrows)
   {
      this.numberOfArrows = numberOfArrows;
      if (direction == SwingConstants.LEFT)
      {
         xPoints[0] = width;
         yPoints[0] = -1;
         xPoints[1] = width;
         yPoints[1] = height;
         xPoints[2] = 0;
         yPoints[2] = height / 2;
         xPoints[3] = 0;
         yPoints[3] = height / 2 - 1;
      }
      else if (direction == SwingConstants.RIGHT)
      {
         xPoints[0] = 0;
         yPoints[0] = -1;
         xPoints[1] = 0;
         yPoints[1] = height;
         xPoints[2] = width;
         yPoints[2] = height / 2;
         xPoints[3] = width;
         yPoints[3] = height / 2 - 1;
      }
      else
      {
         throw new RuntimeException("Valid directions: SwingConstants.LEFT, SwingConstants.RIGHT.");
      }
   }

   public int getIconHeight()
   {
      return height;
   }

   public int getIconWidth()
   {
      return (width * numberOfArrows);
   }

   public void paintIcon(Component c, Graphics g, int x, int y)
   {
      int length = xPoints.length;
      
      for (int number = 0; number < numberOfArrows; number++)
      {
         int adjustedXPoints[] = new int[length];
         int adjustedYPoints[] = new int[length];

         for (int i = 0; i < length; i++)
         {
            adjustedXPoints[i] = xPoints[i] + x + (number*width);
            adjustedYPoints[i] = yPoints[i] + y;
         }

         if (c.isEnabled())
         {
            g.setColor(Color.black);
         }
         else
         {
            g.setColor(Color.gray);
         }

         g.fillPolygon(adjustedXPoints, adjustedYPoints, length);
      }
   }

}