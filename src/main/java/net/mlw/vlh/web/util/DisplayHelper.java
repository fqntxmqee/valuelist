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
package net.mlw.vlh.web.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * Abstracts the means by which text is displayed.
 * 
 * @author Matthew L. Wilson
 * @version $Revision: 1.2 $ $Date: 2004/07/26 12:56:21 $
 *  
 */
public interface DisplayHelper
{
   /**
    * Abstracts the means by which text is displayed.
    * 
    * @param pageContext
    *           The jsp PageContext
    * @param key
    *           The key, which could be the value to display or some sort of key
    *           to the text value.
    * 
    * @throws JspException
    *            Thrown by underling implementation.
    * 
    * @return The text value.
    */
   public abstract String help(PageContext pageContext, String key) throws JspException;
}