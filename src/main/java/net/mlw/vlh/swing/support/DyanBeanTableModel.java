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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Matthew L. Wilson
 * @version $Revision: 1.2 $ $Date: 2005/04/01 20:21:47 $
 */
public class DyanBeanTableModel extends AbstractValueListTableModel
{
   private String[] columns = {};

   private String[] properties = {};

   private Class[] classes = {};

   public void addColumn(String title, String property)
   {
      addColumn(title, property, Object.class);
   }

   public void addColumn(String title, String property, Class klass)
   {
      List columns = new ArrayList(Arrays.asList(this.columns));
      columns.add(title);
      this.columns = (String[]) columns.toArray(new String[] {});

      List properties = new ArrayList(Arrays.asList(this.properties));
      properties.add(property);
      this.properties = (String[]) properties.toArray(new String[] {});

      List classes = new ArrayList(Arrays.asList(this.classes));
      classes.add(klass);
      this.classes = (Class[]) classes.toArray(new Class[] {});
   }

   public String getSortPropertyName(int i)
   {
      return properties[i];
   }

   /**
    * @see javax.swing.table.TableModel#getColumnName(int)
    */
   public String getColumnName(int i)
   {
      return columns[i];
   }

   /**
    * @see javax.swing.table.TableModel#getColumnCount()
    */
   public int getColumnCount()
   {
      return columns.length;
   }

   /**
    * @see javax.swing.table.TableModel#getValueAt(int, int)
    */
   public Object getValueAt(int row, int column)
   {
      try
      {
         Object bean = list.get(row);
         if (bean == null)
         {
            return null;
         }
         return PropertyUtils.getProperty(bean, properties[column]);
      }
      catch (Exception e)
      {
         return e;
      }
   }

   /**
    * @see javax.swing.table.TableModel#getColumnClass(int)
    */
   public Class getColumnClass(int index)
   {
      return classes[index];
   }

}