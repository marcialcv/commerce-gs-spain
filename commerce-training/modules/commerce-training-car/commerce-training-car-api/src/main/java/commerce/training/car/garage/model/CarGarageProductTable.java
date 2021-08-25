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
 * The table class for the &quot;CarGarageProduct&quot; database table.
 *
 * @author Roselaine Marques
 * @see CarGarageProduct
 * @generated
 */
public class CarGarageProductTable extends BaseTable<CarGarageProductTable> {

	public static final CarGarageProductTable INSTANCE =
		new CarGarageProductTable();

	public final Column<CarGarageProductTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CarGarageProductTable, Long> carGarageProductId =
		createColumn(
			"carGarageProductId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CarGarageProductTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CarGarageProductTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CarGarageProductTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CarGarageProductTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CarGarageProductTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CarGarageProductTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CarGarageProductTable, Long> carGarageId = createColumn(
		"carGarageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CarGarageProductTable, Long> cpDefinitionId =
		createColumn(
			"cpDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CarGarageProductTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CarGarageProductTable() {
		super("CarGarageProduct", CarGarageProductTable::new);
	}

}