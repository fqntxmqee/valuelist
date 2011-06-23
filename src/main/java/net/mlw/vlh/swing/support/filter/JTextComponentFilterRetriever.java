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
package net.mlw.vlh.swing.support.filter;

import javax.swing.text.JTextComponent;

import net.mlw.vlh.Errors;

/**
 * @author Matthew L. Wilson
 * @version $Revision: 1.3 $ $Date: 2005/08/30 18:54:25 $
 */
public class JTextComponentFilterRetriever extends AbstractFilterRetriever
{
   protected JTextComponent textComponent;

   public JTextComponentFilterRetriever(String key, JTextComponent textComponent)
   {
      super(key);
      this.textComponent = textComponent;
   }

   /**
    * @see net.mlw.vlh.swing.FilterRetriever#getFilterValue()
    */
   public Object getFilterValue(Errors errors)
   {
      return textComponent.getText();
   }

   public Object reset()
   {
      textComponent.setText("");
      return textComponent.getText();
   }
}