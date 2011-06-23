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

package net.mlw.vlh.adapter.jdbc.spring.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.mlw.vlh.adapter.jdbc.util.StandardConnectionCreator;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * This connection creator uses spring to manage connection in transactional environment.
 * 
 * @author Stepan Marek
 * @version $Revision: 1.2 $ $Date: 2005/10/20 16:37:49 $
 * @see DataSourceUtils
 */
public class SpringConnectionCreator extends StandardConnectionCreator {

	public SpringConnectionCreator() {
	}

	public SpringConnectionCreator(DataSource dataSource) {
		super(dataSource);
	}

	public Connection createConnection() throws SQLException {
		return DataSourceUtils.getConnection(getDataSource());
	}

	public void close(ResultSet result, PreparedStatement statement, Connection connection) {
		JdbcUtils.closeResultSet(result);
		JdbcUtils.closeStatement(statement);
		// deprecated since spring 1.2
		DataSourceUtils.releaseConnection(connection, getDataSource());
	}
}