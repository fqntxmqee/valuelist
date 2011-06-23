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

/**
 * 
 * @author Matthew L. Wilson
 * @version $Revision: 1.5 $ $Date: 2005/08/19 16:06:29 $
 */
public abstract class AbstractArraySetter extends AbstractSetter
{
	private boolean useBindVarables = true;

	/**
	 * @see net.mlw.vlh.adapter.jdbc.util.Setter#getReplacementString(java.lang.Object)
	 */
	public String getReplacementString(Object value)
	{
		if (value instanceof String)
		{
			return (useBindVarables) ? "?" : (String) value;
		}
		else if (value instanceof Object[])
		{
			Object[] values = (Object[]) value;
			StringBuffer sb = new StringBuffer();
			if (useBindVarables)
			{
				for (int i = 0, length = values.length; i < length; i++)
				{
					sb.append("?");
					if ((i + 1) < length)
					{
						sb.append(",");
					}
				}
			}
			else
			{
				for (int i = 0, length = values.length; i < length; i++)
				{
					sb.append(values[i]);
					if ((i + 1) < length)
					{
						sb.append(",");
					}
				}
			}

			return sb.toString();
		}
		else
		{
			return null;
		}
	}

	/**
	 * @return Returns the useBindVarables.
	 */
	public boolean isUseBindVarables()
	{
		return useBindVarables;
	}

	/**
	 * @param useBindVarables The useBindVarables to set.
	 */
	public void setUseBindVarables(boolean useBindVarables)
	{
		this.useBindVarables = useBindVarables;
	}
}