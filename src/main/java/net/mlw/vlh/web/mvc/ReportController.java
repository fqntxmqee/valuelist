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
package net.mlw.vlh.web.mvc;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.springframework.web.servlet.view.tiles.ComponentControllerSupport;

/**
 * This ComponentControllerSupport simply puts an generated Attribute in the ComponentContext. This give the ability to use one tiles def
 * for many pages.
 * @author Matthew L. Wilson
 * @version $Revision: 1.5 $ $Date: 2004/07/21 11:33:28 $
 */
public class ReportController extends ComponentControllerSupport
{
   protected static boolean init = false;

   /**
    * @see org.springframework.web.servlet.view.tiles.ComponentControllerSupport#doPerform(org.apache.struts.tiles.ComponentContext,
    *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   protected void doPerform(ComponentContext componentContext, HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      // The uri will look like: /report/Report.go?report={name}
      String name = request.getParameter("report");

      // get the definition from the config file. It should look
      // soomething like: (/WEB-INF/jsp/reports/{0}.jsp)
      String report = MessageFormat.format(componentContext.getAttribute("body").toString(), new String[] { name });

      componentContext.putAttribute("body", report);
   }
}