/*
 * Knowage, Open Source Business Intelligence suite
 * Copyright (C) 2016 Engineering Ingegneria Informatica S.p.A.
 *
 * Knowage is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Knowage is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.eng.spagobi.utilities.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import it.eng.spagobi.tools.dataset.cache.query.SqlDialect;

/**
 * @author Andrea Gioia (andrea.gioia@eng.it)
 *
 */
public interface IDataBase {
	/**
	 * @param javaType
	 *            The java type to map to a database' type
	 *
	 * @return The database type to use to map the specified java type
	 */
	String getDataBaseType(Class javaType);

	/**
	 *
	 * @return the length used for mapped varchar database type. getDataBaseType(String.class) = DBTYPE(X) where X is equal to getVarcharLength().
	 */
	int getVarcharLength();

	/**
	 *
	 * @param varcharLength
	 *            the length used for varchart database type
	 */
	void setVarcharLength(int varcharLength);

	/**
	 *
	 * @return the sql dialect
	 */
	SqlDialect getSqlDialect();

	/**
	 *
	 * @return the alias delimiter
	 */
	String getAliasDelimiter();

	BigDecimal getUsedMemorySize(String schema, String tablePrefix);

	/**
	 * @param conn
	 *            The connection to the datasource
	 *
	 * @return The current schema
	 */
	String getSchema(Connection conn) throws SQLException;

	/**
	 * @param conn
	 *            The connection to the datasource
	 *
	 * @return The current catalog
	 */
	String getCatalog(Connection conn) throws SQLException;
}
