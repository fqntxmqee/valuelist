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
package net.mlw.vlh.adapter.jdbc.util.setter;

import net.mlw.vlh.adapter.jdbc.util.Setter;

/**
 * 
 * @author Matthew L. Wilson
 * @version $Revision: 1.2 $ $Date: 2004/07/29 14:20:01 $
 */
public abstract class AbstractSetter implements Setter
{
   /**
    * @see net.mlw.vlh.adapter.jdbc.util.Setter#getReplacementString(java.lang.Object)
    */
   public String getReplacementString(Object value)
   {
      return "?";
   }
}