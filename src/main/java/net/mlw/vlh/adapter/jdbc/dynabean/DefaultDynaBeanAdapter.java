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
package net.mlw.vlh.adapter.jdbc.dynabean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.jdbc.AbstractDynaJdbcAdapter;
import net.mlw.vlh.adapter.jdbc.dynabean.fix.ResultSetDynaClass;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This ValueListAdapter returns a ValueList of DynaBean(s).
 * @see net.mlw.vlh.adapter.jdbc.AbstractDynaJdbcAdapter
 * @see net.mlw.vlh.adapter.jdbc.AbstractJdbcAdapter
 *  
 * @author Matthew L. Wilson, Andrej Zachar
 * @version $Revision: 1.6 $ $Date: 2005/08/19 16:06:29 $
 */
public class DefaultDynaBeanAdapter extends AbstractDynaJdbcAdapter
{

   private static final Log LOGGER = LogFactory.getLog(DefaultDynaBeanAdapter.class);

   public List processResultSet(String name, ResultSet result, int numberPerPage, ValueListInfo info) throws SQLException
   {
      List list = new ArrayList();

      ResultSetDynaClass rsdc = new ResultSetDynaClass(result, false, isUseName());
      BasicDynaClass bdc = new BasicDynaClass(name, BasicDynaBean.class, rsdc.getDynaProperties());

      int rowIndex = 0;
      for (Iterator rows = rsdc.iterator(); rows.hasNext() && rowIndex < numberPerPage; rowIndex++)
      {
         try
         {
            DynaBean oldRow = (DynaBean) rows.next();
            DynaBean newRow = bdc.newInstance();

            DynaProperty[] properties = oldRow.getDynaClass().getDynaProperties();
            for (int i = 0, length = properties.length; i < length; i++)
            {
               String propertyName = properties[i].getName();
               Object value = oldRow.get(propertyName);
               newRow.set(propertyName, value);
            }

            list.add(newRow);
         }
         catch (Exception e)
         {
            LOGGER.error(e);
         }
      }

      return list;
   }
}