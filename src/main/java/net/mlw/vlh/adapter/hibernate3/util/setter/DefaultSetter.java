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
package net.mlw.vlh.adapter.hibernate3.util.setter;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * Sets a <code>java.lang.?????</code> on a <code>PreparedStatement</code>.
 * 
 * @author Stepan Marek
 * @version $Revision: 1.1 $ $Date: 2005/10/20 16:45:24 $
 */
public class DefaultSetter extends AbstractSetter
{
   /**
    * Logger for this class
    */
   private static final Log LOGGER = LogFactory.getLog(DefaultSetter.class);

   public void set(Query query, String key, Object value) throws HibernateException, ParseException
   {
      if (LOGGER.isInfoEnabled())
      {
         LOGGER.info("The key='" + key + "' was set to the query as the parameter='" + value + "'.");
      }
      query.setParameter(key, value);
   }
}