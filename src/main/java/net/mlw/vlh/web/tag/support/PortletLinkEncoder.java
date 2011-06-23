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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this library; if not, write to the Free Software Foundation, 
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. 
 * 
 * > http://www.gnu.org/copyleft/lesser.html 
 * > http://www.opensource.org/licenses/lgpl-license.php
 */
package net.mlw.vlh.web.tag.support;

import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

/** This LinkEncoder encodes a URL to the standards defined
 *  in the JSR168 Portlet API.
 * 
 *  To use this class an entry needs to be made in the spring.xml file:
    <pre>
      ... 
      <bean id="classicLook" singleton="true" class="net.mlw.vlh.web.ValueListConfigBean">
        ...
        <property name="linkEncoder">
          <bean class="net.mlw.vlh.web.tag.support.PortletLinkEncoder"/
        </property>
        ...
      </bean>
      ...
    </pre> 
 * @author Keith R. Davis
 * @version $Revision: 1.2 $ $Date: 2004/08/17 15:30:51 $
 */
public class PortletLinkEncoder implements LinkEncoder
{
	public PortletURL getRenderURL(PageContext pageContext)
	{
		ServletRequest request = pageContext.getRequest();
		RenderResponse renderResponse = (RenderResponse) request.getAttribute("javax.portlet.response");
		PortletURL url = renderResponse.createRenderURL();
		return url;
	}

	/*** Returns an encoded String from the given parameters.
	 * 
	 * @param pageContext The PageContext.
	 * @param parameters A Map containing all the parameters to encode.
	 * @param includedKeys The parameters to include. Includes all if null or empty.
	 * @param ignoredKeys The parameters to exclude. Excludes none if null or empty.
	 * @return An encoded String
	 */
	public String encode(PageContext pageContext, Map parameters)
	{
		PortletURL url = getRenderURL(pageContext);

		for (Iterator iter = parameters.keySet().iterator(); iter.hasNext();)
		{
			String key = (String) iter.next();

			Object value = parameters.get(key);
			if (value instanceof String[])
			{
				String[] values = (String[]) value;
				url.setParameter(key, values);
			}
			else if (value instanceof String)
			{
				url.setParameter(key, (String) value);
			}
			else if (value != null)
			{
				url.setParameter(key, value.toString());
			}
		}

		return url.toString();
	}

}