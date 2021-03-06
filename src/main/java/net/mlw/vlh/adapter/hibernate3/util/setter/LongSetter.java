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

/** Sets a <code>java.lang.Long</code> on a <code>Query</code>. Conversion from a string is provided using <code>Long.parseLong()</code>.
 * 
 * @author Matthew L. Wilson
 * @version $Revision: 1.3 $ $Date: 2005/12/19 12:22:26 $
 */
public class LongSetter extends AbstractSetter
{

   /**
    * Logger for this class
    */
   private static final Log LOGGER = LogFactory.getLog(LongSetter.class);

   /**
    * @see net.mlw.vlh.adapter.hibernate3.util.Setter#set(Query, String, Object)
    */
   public void set(Query query, String key, Object value) throws HibernateException, ParseException
   {
      if (value instanceof Long)
      {
         if (LOGGER.isInfoEnabled())
         {
            LOGGER.info("The key='" + key + "'s value is instance of a Long, now is converting to long.");
         }
         query.setLong(key, ((Long) value).longValue());
      }
      else if (value instanceof String)
      {
         if (LOGGER.isInfoEnabled())
         {
            LOGGER.info("The key='" + key + "'s value is instance of a String, now is parsing to long.");
         }
         long longValue = Long.parseLong((String) value);
         query.setLong(key, longValue);
      }
      else if (value == null)
      {
         if (LOGGER.isInfoEnabled())
         {
            LOGGER.info("The key='" + key + "'s value is null.");
         }
         query.setParameter(key, null);
      }
      else
      {
         throw new IllegalArgumentException("Cannot convert value of class " + value.getClass().getName() + " to long (key=" + key + ")");
      }
   }
}