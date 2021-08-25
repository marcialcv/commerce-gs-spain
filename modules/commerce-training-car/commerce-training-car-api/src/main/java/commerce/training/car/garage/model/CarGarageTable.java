/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package commerce.training.car.garage.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CarGarage&quot; database table.
 *
 * @author Roselaine Marques
 * @see CarGarage
 * @generated
 */
public class CarGarageTable extends BaseTable<CarGarageTable> {

	public static final CarGarageTable INSTANCE = new CarGarageTable();

	public final Column<CarGarageTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CarGarageTable, Long> carGarageId = createColumn(
		"carGarageId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CarGarageTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CarGarageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CarGarageTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CarGarageTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CarGarageTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CarGarageTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CarGarageTable, Long> cpDefinitionId = createColumn(
		"cpDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CarGarageTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CarGarageTable, Double> latitude = createColumn(
		"latitude", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CarGarageTable, Double> longitude = createColumn(
		"longitude", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);

	private CarGarageTable() {
		super("CarGarage", CarGarageTable::new);
	}

}