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
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * @author Andrej Zachar, Matthew L. Wilson
 * @version $Revision: 1.2 $ $Date: 2005/11/03 18:08:51 $
 */
public class DateSetter extends AbstractSetter
{
   /**
    * Logger for this class
    */
   private static final Log LOGGER = LogFactory.getLog(DateSetter.class);

   public static final String DEFAULT_FORMAT = "MM/dd/yyyy";

   private SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);

   /**
    * <ol>
    * <li>If is value instance of the String, it try to parse value using
    * SimpleDateFormat with specified format.</li>
    * <li>If is value instance of the Date, it will set it directly to query .
    * </li>
    * <li>Otherwise it will set null to query for key.</li>
    * </ol>
    * 
    * @see net.mlw.vlh.adapter.hibernate3.util.Setter#set(org.hibernate.Query,
    *      java.lang.String, java.lang.Object)
    * @see #setFormat(String)
    */
   public void set(Query query, String key, Object value) throws HibernateException, ParseException
   {
      Date date = null;
      if (value instanceof String)
      {
         if (LOGGER.isInfoEnabled())
         {
            LOGGER.info("The key='" + key + "'s value is instance of a String, now is parsing to date.");
         }
         date = formatter.parse((String) value);
      }
      else if (value instanceof Date)
      {
         if (LOGGER.isInfoEnabled())
         {
            LOGGER.info("The key='" + key + "' is instance of a Date.");
         }
         date = (Date) value;
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
            LOGGER.warn("The key's='" + key + "' value='" + value + "' was expected as Date or String parseable to Date.");
         }
         throw new IllegalArgumentException("Cannot convert value of class " + value.getClass().getName() + " to date (key=" + key + ")");
      }

      if (LOGGER.isInfoEnabled())
      {
         LOGGER.info("The key='" + key + "' was set to the query as Date with the date='" + date + "'.");
      }

      query.setDate(key, date);
   }

   /**
    * Set the format for parsing <code>Date</code> from string values of
    * keys. Default is "MM/dd/yyyy"
    * 
    * @param format The format to set.
    */
   public void setFormat(String format)
   {
      formatter = new SimpleDateFormat(format);
   }
}