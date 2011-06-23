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

import org.apache.struts.util.RequestUtils;

/** The StrutsDisplayHelper is used to i18n the column headers.  It can be used by
 *  adding the folloing in the spring config file:
 * 
 *  <pre>
 *    <bean id="myLook" singleton="true" class="net.mlw.vlh.web.ValueListConfigBean">
 *      <property name="displayHelper">
 *         <bean class="net.mlw.vlh.web.util.StrutsDisplayHelper" />
 *      </property>
 *      ...
 *    </bean>
 *  </pre>
 * 
 *  This DisplayHelper simply takes the value of the column and looks up the message in
 *  the default resource bundle.
 * 
 *  NOTE: The prefered way to i18n is to use the titleKey attribute and the 
 *  messageSource defined in the ValueListConfigBean.
 * 
 * @author Matthew L. Wilson 
 * @version $Revision: 1.5 $ $Date: 2005/10/20 16:38:08 $
 */
public class StrutsDisplayHelper implements DisplayHelper
{
   /** @see net.mlw.vlh.web.util.DisplayHelper#help(javax.servlet.jsp.PageContext, java.lang.String)
    */
   public String help(PageContext pageContext, String key) throws JspException
   {
      // deprecated since struts 1.2
      return RequestUtils.message(pageContext, null, null, key);
   }
}