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

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.swing.ValueListTableModel;

/**
 * @author Matthew L. Wilson
 * @version $Revision: 1.5 $ $Date: 2005/08/30 18:54:25 $
 */
public abstract class AbstractValueListTableModel extends AbstractTableModel implements ValueListTableModel
{
   protected List list = new LinkedList();

   protected int rowCount = 0;

   /**
    * @see javax.swing.table.TableModel#getRowCount()
    */
   public int getRowCount()
   {
      return rowCount;
   }

   public synchronized int addBean(Object bean)
   {
      list.add(bean);
      return (rowCount = list.size()) - 1;
   }

   public Object getBean(int row)
   {
      if (row == -1 || list.size() < row)
      {
         return null;
      }
      return list.get(row);
   }

   public synchronized void setList(List list)
   {
      this.list = list;
      this.rowCount = list.size();
      fireTableDataChanged();
   }

   public synchronized void setValueList(ValueList valueList)
   {
      this.list = valueList.getList();
      this.rowCount = list.size();
      fireTableDataChanged();
   }

   /**
    * @see net.mlw.vlh.swing.ValueListTableModel#trimFromBottomOfList()
    */
   public synchronized int trimFromList(int index)
   {
      list.remove(index);
      return (rowCount = list.size()) - 1;
   }

   /**
    * @see net.mlw.vlh.swing.ValueListTableModel#contains(Object)
    */
   public boolean contains(Object bean)
   {
      return list.contains(bean);
   }

   public synchronized int removeBean(Object bean)
   {
      int index = list.indexOf(bean);
      if (index >= 0)
      {
         list.remove(index);
         this.rowCount = list.size();
      }
      return index;
   }
}