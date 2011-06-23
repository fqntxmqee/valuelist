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
package net.mlw.vlh.adapter.ibatis.sqlmaps;

import java.util.ArrayList;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListAdapter;
import net.mlw.vlh.ValueListInfo;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/** This adapter wraps the functionality of iBATIS SQL Maps.
 * 
 * <i>"iBATIS SQL Maps provides a very simple and flexible means 
 * of moving data between your Java objects and a relational 
 * database. Use the full power of real SQL without a single 
 * line of JDBC code!"</i> -http://www.ibatis.com/common/sqlmaps.html
 * 
 * @author Matthew L. Wilson
 * @version $Revision: 1.4 $ $Date: 2004/12/14 20:15:25 $
 */
public class Ibatis20Adapter extends SqlMapClientDaoSupport implements ValueListAdapter
{
	public int getAdapterType()
	{
		return DO_NOTHING;
	}
	
	/**
	 * @see net.mlw.vlh.ValueListAdapter#getValueList(java.lang.String, net.mlw.vlh.ValueListInfo)
	 */
	public ValueList getValueList(String name, ValueListInfo info)
	{
		try
		{
			//SqlMap p;
			getSqlMapClientTemplate().queryForList("getPlayersByLastName", "Wil%");
			//List list = getSqlMapTemplate( ).executeQueryForList("searchProductList", info);

			return new DefaultListBackedValueList(new ArrayList(), info);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}