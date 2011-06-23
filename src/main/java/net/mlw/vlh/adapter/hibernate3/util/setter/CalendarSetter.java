/**
 * Copyright (c) 2003 held jointly by the individual authors.
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. >
 * http://www.gnu.org/copyleft/lesser.html >
 * http://www.opensource.org/licenses/lgpl-license.php
 */
package net.mlw.vlh.adapter.hibernate3.util.setter;

import java.text.ParseException;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * @author Stepan Marek
 * @version $Revision: 1.2 $ $Date: 2005/11/03 18:08:51 $
 */
public class CalendarSetter extends AbstractSetter
{
   /**
    * Logger for this class
    */
   private static final Log LOGGER = LogFactory.getLog(CalendarSetter.class);

   /**
    * <ol>
    * <li>If is filter value instance of the Calendar, it will set it directly
    * to query. </li>
    * <li>If is filter value instance of String, it try to convert it it to long and set to Calendar instance</li>
    * <li>If is filter value instance of Long, it try to convert it it to long and set to Calendar instance</li>
    * <li>Otherwise it will set null to query for key.</li>
    * </ol>
    * 
    * @see net.mlw.vlh.adapter.hibernate3.util.Setter#set(org.hibernate.Query,
    *      java.lang.String, java.lang.Object)
    */
   public void set(Query query, String key, Object value) throws HibernateException, ParseException
   {

      Calendar calendar = null;

      if (value instanceof String)
      {
         calendar = Calendar.getInstance();
         try
         {
            calendar.setTimeInMillis(Long.valueOf((String) value).longValue());
            if (LOGGER.isDebugEnabled())
            {
               LOGGER.debug("The key's='" + key + "' String value='" + value + "' was converted to Calendar.");
            }
         }
         catch (NumberFormatException e)
         {
            if (LOGGER.isWarnEnabled())
            {
               LOGGER.warn("The key's='" + key + "' String value='" + value + "' was not converted to Calendar, error was:"
                     + e.getMessage());
            }
            throw e;
         }
      }
      else if (value instanceof Long)
      {
         calendar = Calendar.getInstance();
         calendar.setTimeInMillis(((Long) value).longValue());
         if (LOGGER.isDebugEnabled())
         {
            LOGGER.debug("The key's='" + key + "' Long value='" + value + "' was converted to Calendar.");
         }
      }
      else if (value instanceof Calendar)
      {
         calendar = (Calendar) value;
      }
      else if (value == null)
      {
         if (LOGGER.isInfoEnabled())
         {
            LOGGER.info("The key='" + key + "'s value is null.");
         }
      }
      else
      {
         if (LOGGER.isWarnEnabled())
         {
            LOGGER.warn("The key's='" + key + "' value='" + value + "' was expected as Calendar.");
         }
         throw new IllegalArgumentException("Cannot convert value of class " + value.getClass().getName() + " to calendar (key=" + key
               + ")");
      }

      query.setCalendar(key, calendar);

      if (LOGGER.isInfoEnabled())
      {
         LOGGER.info("The key='" + key + "' was set to the query as Calendar='" + calendar + "'.");
      }
   }
}