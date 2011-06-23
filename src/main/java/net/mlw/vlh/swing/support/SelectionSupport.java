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

import javax.swing.JTable;

/**
 * @author Matthew L. Wilson
 * @version $Revision: 1.1 $ $Date: 2005/08/30 18:54:25 $
 */
public class SelectionSupport
{
   public static void maintainSelectedRows(KeyRetriever keyRetriever, JTable table, AbstractValueListTableModel model, TableSorter sorter,
         Runnable runnable)
   {
      String key = null;
      int row = table.getSelectedRow();

      if (table.getSelectedRow() != -1)
      {
         int selectedRow = table.getSelectedRow();
         int sortedRow = sorter.modelIndex(selectedRow);
         key = keyRetriever.getKey(model.getBean(sortedRow));
      }

      runnable.run();

      if (key != null)
      {
         int topIndex = 0;
         int bottomIndex = 0;
         int length = model.getRowCount();
         for (int i = 1;; i++)
         {
            if (((topIndex = row + i) < length) || ((bottomIndex = row - i) > -1))
            {
               if (topIndex < length)
               {
                  Object bean = model.getBean(sorter.modelIndex(topIndex));
                  if (key.equals(keyRetriever.getKey(bean)))
                  {
                     table.getSelectionModel().clearSelection();
                     table.getSelectionModel().setLeadSelectionIndex(topIndex);
                     break;
                  }
               }

               if (bottomIndex > -1)
               {
                  Object bean = model.getBean(sorter.modelIndex(bottomIndex));
                  if (key.equals(keyRetriever.getKey(bean)))
                  {
                     table.getSelectionModel().clearSelection();
                     table.getSelectionModel().setLeadSelectionIndex(bottomIndex);
                     break;
                  }
               }
            }
            else
            {
               break;
            }
         }
      }
   }

   public interface KeyRetriever
   {
      String getKey(Object bean);
   }
}