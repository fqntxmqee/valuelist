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
package net.mlw.vlh.adapter.jdbc.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.adapter.jdbc.AbstractJdbcAdapter;
import net.mlw.vlh.adapter.jdbc.spring.util.SpringConnectionCreator;

import org.springframework.jdbc.core.RowMapper;

/**
 * net.mlw.vlh.adapter.jdbc.spring.SpringDaoValueListAdapter
 * 
 * @author Matthew L. Wilson
 * @version $Revision: 1.2 $ $Date: 2005/10/17 11:42:36 $
 */
public class SpringDaoValueListAdapter extends AbstractJdbcAdapter
{
   private RowMapper rowMapper;

   public SpringDaoValueListAdapter()
   {
      setConnectionCreator(new SpringConnectionCreator());
   }

   /**
    * @see net.mlw.vlh.adapter.jdbc.AbstractJdbcAdapter#processResultSet(java.lang.String, java.sql.ResultSet, int, net.mlw.vlh.ValueListInfo)
    */
   public List processResultSet(String name, ResultSet result, int numberPerPage, ValueListInfo info) throws SQLException
   {
      List list = new ArrayList();

      for (int rowIndex = 0; result.next() && rowIndex < numberPerPage; rowIndex++)
      {
         list.add(rowMapper.mapRow(result, rowIndex));
      }

      return list;
   }

   /**
    * @return Returns the rowMapper.
    */
   public RowMapper getRowMapper()
   {
      return rowMapper;
   }

   /**
    * @param rowMapper The rowMapper to set.
    */
   public void setRowMapper(RowMapper rowMapper)
   {
      this.rowMapper = rowMapper;
   }
}