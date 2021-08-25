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

package commerce.training.car.garage.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import commerce.training.car.garage.exception.NoSuchCarGarageProductException;
import commerce.training.car.garage.model.CarGarageProduct;
import commerce.training.car.garage.model.CarGarageProductTable;
import commerce.training.car.garage.model.impl.CarGarageProductImpl;
import commerce.training.car.garage.model.impl.CarGarageProductModelImpl;
import commerce.training.car.garage.service.persistence.CarGarageProductPersistence;
import commerce.training.car.garage.service.persistence.impl.constants.CommerceTrainingPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the car garage product service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Roselaine Marques
 * @generated
 */
@Component(service = {CarGarageProductPersistence.class, BasePersistence.class})
public class CarGarageProductPersistenceImpl
	extends BasePersistenceImpl<CarGarageProduct>
	implements CarGarageProductPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CarGarageProductUtil</code> to access the car garage product persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CarGarageProductImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the car garage products where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the car garage products where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @return the range of matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the car garage products where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CarGarageProduct> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the car garage products where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CarGarageProduct> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CarGarageProduct> list = null;

		if (useFinderCache) {
			list = (List<CarGarageProduct>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CarGarageProduct carGarageProduct : list) {
					if (!uuid.equals(carGarageProduct.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_CARGARAGEPRODUCT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CarGarageProductModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<CarGarageProduct>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first car garage product in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching car garage product
	 * @throws NoSuchCarGarageProductException if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct findByUuid_First(
			String uuid, OrderByComparator<CarGarageProduct> orderByComparator)
		throws NoSuchCarGarageProductException {

		CarGarageProduct carGarageProduct = fetchByUuid_First(
			uuid, orderByComparator);

		if (carGarageProduct != null) {
			return carGarageProduct;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCarGarageProductException(sb.toString());
	}

	/**
	 * Returns the first car garage product in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching car garage product, or <code>null</code> if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct fetchByUuid_First(
		String uuid, OrderByComparator<CarGarageProduct> orderByComparator) {

		List<CarGarageProduct> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last car garage product in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching car garage product
	 * @throws NoSuchCarGarageProductException if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct findByUuid_Last(
			String uuid, OrderByComparator<CarGarageProduct> orderByComparator)
		throws NoSuchCarGarageProductException {

		CarGarageProduct carGarageProduct = fetchByUuid_Last(
			uuid, orderByComparator);

		if (carGarageProduct != null) {
			return carGarageProduct;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCarGarageProductException(sb.toString());
	}

	/**
	 * Returns the last car garage product in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching car garage product, or <code>null</code> if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct fetchByUuid_Last(
		String uuid, OrderByComparator<CarGarageProduct> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CarGarageProduct> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the car garage products before and after the current car garage product in the ordered set where uuid = &#63;.
	 *
	 * @param carGarageProductId the primary key of the current car garage product
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next car garage product
	 * @throws NoSuchCarGarageProductException if a car garage product with the primary key could not be found
	 */
	@Override
	public CarGarageProduct[] findByUuid_PrevAndNext(
			long carGarageProductId, String uuid,
			OrderByComparator<CarGarageProduct> orderByComparator)
		throws NoSuchCarGarageProductException {

		uuid = Objects.toString(uuid, "");

		CarGarageProduct carGarageProduct = findByPrimaryKey(
			carGarageProductId);

		Session session = null;

		try {
			session = openSession();

			CarGarageProduct[] array = new CarGarageProductImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, carGarageProduct, uuid, orderByComparator, true);

			array[1] = carGarageProduct;

			array[2] = getByUuid_PrevAndNext(
				session, carGarageProduct, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CarGarageProduct getByUuid_PrevAndNext(
		Session session, CarGarageProduct carGarageProduct, String uuid,
		OrderByComparator<CarGarageProduct> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CARGARAGEPRODUCT_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CarGarageProductModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						carGarageProduct)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CarGarageProduct> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the car garage products where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CarGarageProduct carGarageProduct :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(carGarageProduct);
		}
	}

	/**
	 * Returns the number of car garage products where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching car garage products
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CARGARAGEPRODUCT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"carGarageProduct.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(carGarageProduct.uuid IS NULL OR carGarageProduct.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the car garage product where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCarGarageProductException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching car garage product
	 * @throws NoSuchCarGarageProductException if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct findByUUID_G(String uuid, long groupId)
		throws NoSuchCarGarageProductException {

		CarGarageProduct carGarageProduct = fetchByUUID_G(uuid, groupId);

		if (carGarageProduct == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCarGarageProductException(sb.toString());
		}

		return carGarageProduct;
	}

	/**
	 * Returns the car garage product where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching car garage product, or <code>null</code> if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the car garage product where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching car garage product, or <code>null</code> if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof CarGarageProduct) {
			CarGarageProduct carGarageProduct = (CarGarageProduct)result;

			if (!Objects.equals(uuid, carGarageProduct.getUuid()) ||
				(groupId != carGarageProduct.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CARGARAGEPRODUCT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<CarGarageProduct> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CarGarageProduct carGarageProduct = list.get(0);

					result = carGarageProduct;

					cacheResult(carGarageProduct);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CarGarageProduct)result;
		}
	}

	/**
	 * Removes the car garage product where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the car garage product that was removed
	 */
	@Override
	public CarGarageProduct removeByUUID_G(String uuid, long groupId)
		throws NoSuchCarGarageProductException {

		CarGarageProduct carGarageProduct = findByUUID_G(uuid, groupId);

		return remove(carGarageProduct);
	}

	/**
	 * Returns the number of car garage products where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching car garage products
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CARGARAGEPRODUCT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"carGarageProduct.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(carGarageProduct.uuid IS NULL OR carGarageProduct.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"carGarageProduct.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the car garage products where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the car garage products where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @return the range of matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the car garage products where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CarGarageProduct> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the car garage products where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CarGarageProduct> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CarGarageProduct> list = null;

		if (useFinderCache) {
			list = (List<CarGarageProduct>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CarGarageProduct carGarageProduct : list) {
					if (!uuid.equals(carGarageProduct.getUuid()) ||
						(companyId != carGarageProduct.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_CARGARAGEPRODUCT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CarGarageProductModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<CarGarageProduct>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first car garage product in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching car garage product
	 * @throws NoSuchCarGarageProductException if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CarGarageProduct> orderByComparator)
		throws NoSuchCarGarageProductException {

		CarGarageProduct carGarageProduct = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (carGarageProduct != null) {
			return carGarageProduct;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCarGarageProductException(sb.toString());
	}

	/**
	 * Returns the first car garage product in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching car garage product, or <code>null</code> if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CarGarageProduct> orderByComparator) {

		List<CarGarageProduct> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last car garage product in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching car garage product
	 * @throws NoSuchCarGarageProductException if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CarGarageProduct> orderByComparator)
		throws NoSuchCarGarageProductException {

		CarGarageProduct carGarageProduct = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (carGarageProduct != null) {
			return carGarageProduct;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCarGarageProductException(sb.toString());
	}

	/**
	 * Returns the last car garage product in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching car garage product, or <code>null</code> if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CarGarageProduct> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CarGarageProduct> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the car garage products before and after the current car garage product in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param carGarageProductId the primary key of the current car garage product
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next car garage product
	 * @throws NoSuchCarGarageProductException if a car garage product with the primary key could not be found
	 */
	@Override
	public CarGarageProduct[] findByUuid_C_PrevAndNext(
			long carGarageProductId, String uuid, long companyId,
			OrderByComparator<CarGarageProduct> orderByComparator)
		throws NoSuchCarGarageProductException {

		uuid = Objects.toString(uuid, "");

		CarGarageProduct carGarageProduct = findByPrimaryKey(
			carGarageProductId);

		Session session = null;

		try {
			session = openSession();

			CarGarageProduct[] array = new CarGarageProductImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, carGarageProduct, uuid, companyId, orderByComparator,
				true);

			array[1] = carGarageProduct;

			array[2] = getByUuid_C_PrevAndNext(
				session, carGarageProduct, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CarGarageProduct getByUuid_C_PrevAndNext(
		Session session, CarGarageProduct carGarageProduct, String uuid,
		long companyId, OrderByComparator<CarGarageProduct> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_CARGARAGEPRODUCT_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CarGarageProductModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						carGarageProduct)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CarGarageProduct> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the car garage products where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CarGarageProduct carGarageProduct :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(carGarageProduct);
		}
	}

	/**
	 * Returns the number of car garage products where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching car garage products
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CARGARAGEPRODUCT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"carGarageProduct.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(carGarageProduct.uuid IS NULL OR carGarageProduct.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"carGarageProduct.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCarGarageId;
	private FinderPath _finderPathWithoutPaginationFindByCarGarageId;
	private FinderPath _finderPathCountByCarGarageId;

	/**
	 * Returns all the car garage products where carGarageId = &#63;.
	 *
	 * @param carGarageId the car garage ID
	 * @return the matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByCarGarageId(long carGarageId) {
		return findByCarGarageId(
			carGarageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the car garage products where carGarageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param carGarageId the car garage ID
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @return the range of matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByCarGarageId(
		long carGarageId, int start, int end) {

		return findByCarGarageId(carGarageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the car garage products where carGarageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param carGarageId the car garage ID
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByCarGarageId(
		long carGarageId, int start, int end,
		OrderByComparator<CarGarageProduct> orderByComparator) {

		return findByCarGarageId(
			carGarageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the car garage products where carGarageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param carGarageId the car garage ID
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching car garage products
	 */
	@Override
	public List<CarGarageProduct> findByCarGarageId(
		long carGarageId, int start, int end,
		OrderByComparator<CarGarageProduct> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCarGarageId;
				finderArgs = new Object[] {carGarageId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCarGarageId;
			finderArgs = new Object[] {
				carGarageId, start, end, orderByComparator
			};
		}

		List<CarGarageProduct> list = null;

		if (useFinderCache) {
			list = (List<CarGarageProduct>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CarGarageProduct carGarageProduct : list) {
					if (carGarageId != carGarageProduct.getCarGarageId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_CARGARAGEPRODUCT_WHERE);

			sb.append(_FINDER_COLUMN_CARGARAGEID_CARGARAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CarGarageProductModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(carGarageId);

				list = (List<CarGarageProduct>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first car garage product in the ordered set where carGarageId = &#63;.
	 *
	 * @param carGarageId the car garage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching car garage product
	 * @throws NoSuchCarGarageProductException if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct findByCarGarageId_First(
			long carGarageId,
			OrderByComparator<CarGarageProduct> orderByComparator)
		throws NoSuchCarGarageProductException {

		CarGarageProduct carGarageProduct = fetchByCarGarageId_First(
			carGarageId, orderByComparator);

		if (carGarageProduct != null) {
			return carGarageProduct;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("carGarageId=");
		sb.append(carGarageId);

		sb.append("}");

		throw new NoSuchCarGarageProductException(sb.toString());
	}

	/**
	 * Returns the first car garage product in the ordered set where carGarageId = &#63;.
	 *
	 * @param carGarageId the car garage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching car garage product, or <code>null</code> if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct fetchByCarGarageId_First(
		long carGarageId,
		OrderByComparator<CarGarageProduct> orderByComparator) {

		List<CarGarageProduct> list = findByCarGarageId(
			carGarageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last car garage product in the ordered set where carGarageId = &#63;.
	 *
	 * @param carGarageId the car garage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching car garage product
	 * @throws NoSuchCarGarageProductException if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct findByCarGarageId_Last(
			long carGarageId,
			OrderByComparator<CarGarageProduct> orderByComparator)
		throws NoSuchCarGarageProductException {

		CarGarageProduct carGarageProduct = fetchByCarGarageId_Last(
			carGarageId, orderByComparator);

		if (carGarageProduct != null) {
			return carGarageProduct;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("carGarageId=");
		sb.append(carGarageId);

		sb.append("}");

		throw new NoSuchCarGarageProductException(sb.toString());
	}

	/**
	 * Returns the last car garage product in the ordered set where carGarageId = &#63;.
	 *
	 * @param carGarageId the car garage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching car garage product, or <code>null</code> if a matching car garage product could not be found
	 */
	@Override
	public CarGarageProduct fetchByCarGarageId_Last(
		long carGarageId,
		OrderByComparator<CarGarageProduct> orderByComparator) {

		int count = countByCarGarageId(carGarageId);

		if (count == 0) {
			return null;
		}

		List<CarGarageProduct> list = findByCarGarageId(
			carGarageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the car garage products before and after the current car garage product in the ordered set where carGarageId = &#63;.
	 *
	 * @param carGarageProductId the primary key of the current car garage product
	 * @param carGarageId the car garage ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next car garage product
	 * @throws NoSuchCarGarageProductException if a car garage product with the primary key could not be found
	 */
	@Override
	public CarGarageProduct[] findByCarGarageId_PrevAndNext(
			long carGarageProductId, long carGarageId,
			OrderByComparator<CarGarageProduct> orderByComparator)
		throws NoSuchCarGarageProductException {

		CarGarageProduct carGarageProduct = findByPrimaryKey(
			carGarageProductId);

		Session session = null;

		try {
			session = openSession();

			CarGarageProduct[] array = new CarGarageProductImpl[3];

			array[0] = getByCarGarageId_PrevAndNext(
				session, carGarageProduct, carGarageId, orderByComparator,
				true);

			array[1] = carGarageProduct;

			array[2] = getByCarGarageId_PrevAndNext(
				session, carGarageProduct, carGarageId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CarGarageProduct getByCarGarageId_PrevAndNext(
		Session session, CarGarageProduct carGarageProduct, long carGarageId,
		OrderByComparator<CarGarageProduct> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CARGARAGEPRODUCT_WHERE);

		sb.append(_FINDER_COLUMN_CARGARAGEID_CARGARAGEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CarGarageProductModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(carGarageId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						carGarageProduct)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CarGarageProduct> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the car garage products where carGarageId = &#63; from the database.
	 *
	 * @param carGarageId the car garage ID
	 */
	@Override
	public void removeByCarGarageId(long carGarageId) {
		for (CarGarageProduct carGarageProduct :
				findByCarGarageId(
					carGarageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(carGarageProduct);
		}
	}

	/**
	 * Returns the number of car garage products where carGarageId = &#63;.
	 *
	 * @param carGarageId the car garage ID
	 * @return the number of matching car garage products
	 */
	@Override
	public int countByCarGarageId(long carGarageId) {
		FinderPath finderPath = _finderPathCountByCarGarageId;

		Object[] finderArgs = new Object[] {carGarageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CARGARAGEPRODUCT_WHERE);

			sb.append(_FINDER_COLUMN_CARGARAGEID_CARGARAGEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(carGarageId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CARGARAGEID_CARGARAGEID_2 =
		"carGarageProduct.carGarageId = ?";

	public CarGarageProductPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CarGarageProduct.class);

		setModelImplClass(CarGarageProductImpl.class);
		setModelPKClass(long.class);

		setTable(CarGarageProductTable.INSTANCE);
	}

	/**
	 * Caches the car garage product in the entity cache if it is enabled.
	 *
	 * @param carGarageProduct the car garage product
	 */
	@Override
	public void cacheResult(CarGarageProduct carGarageProduct) {
		entityCache.putResult(
			CarGarageProductImpl.class, carGarageProduct.getPrimaryKey(),
			carGarageProduct);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				carGarageProduct.getUuid(), carGarageProduct.getGroupId()
			},
			carGarageProduct);
	}

	/**
	 * Caches the car garage products in the entity cache if it is enabled.
	 *
	 * @param carGarageProducts the car garage products
	 */
	@Override
	public void cacheResult(List<CarGarageProduct> carGarageProducts) {
		for (CarGarageProduct carGarageProduct : carGarageProducts) {
			if (entityCache.getResult(
					CarGarageProductImpl.class,
					carGarageProduct.getPrimaryKey()) == null) {

				cacheResult(carGarageProduct);
			}
		}
	}

	/**
	 * Clears the cache for all car garage products.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CarGarageProductImpl.class);

		finderCache.clearCache(CarGarageProductImpl.class);
	}

	/**
	 * Clears the cache for the car garage product.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CarGarageProduct carGarageProduct) {
		entityCache.removeResult(CarGarageProductImpl.class, carGarageProduct);
	}

	@Override
	public void clearCache(List<CarGarageProduct> carGarageProducts) {
		for (CarGarageProduct carGarageProduct : carGarageProducts) {
			entityCache.removeResult(
				CarGarageProductImpl.class, carGarageProduct);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CarGarageProductImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CarGarageProductImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CarGarageProductModelImpl carGarageProductModelImpl) {

		Object[] args = new Object[] {
			carGarageProductModelImpl.getUuid(),
			carGarageProductModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, carGarageProductModelImpl);
	}

	/**
	 * Creates a new car garage product with the primary key. Does not add the car garage product to the database.
	 *
	 * @param carGarageProductId the primary key for the new car garage product
	 * @return the new car garage product
	 */
	@Override
	public CarGarageProduct create(long carGarageProductId) {
		CarGarageProduct carGarageProduct = new CarGarageProductImpl();

		carGarageProduct.setNew(true);
		carGarageProduct.setPrimaryKey(carGarageProductId);

		String uuid = PortalUUIDUtil.generate();

		carGarageProduct.setUuid(uuid);

		carGarageProduct.setCompanyId(CompanyThreadLocal.getCompanyId());

		return carGarageProduct;
	}

	/**
	 * Removes the car garage product with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param carGarageProductId the primary key of the car garage product
	 * @return the car garage product that was removed
	 * @throws NoSuchCarGarageProductException if a car garage product with the primary key could not be found
	 */
	@Override
	public CarGarageProduct remove(long carGarageProductId)
		throws NoSuchCarGarageProductException {

		return remove((Serializable)carGarageProductId);
	}

	/**
	 * Removes the car garage product with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the car garage product
	 * @return the car garage product that was removed
	 * @throws NoSuchCarGarageProductException if a car garage product with the primary key could not be found
	 */
	@Override
	public CarGarageProduct remove(Serializable primaryKey)
		throws NoSuchCarGarageProductException {

		Session session = null;

		try {
			session = openSession();

			CarGarageProduct carGarageProduct = (CarGarageProduct)session.get(
				CarGarageProductImpl.class, primaryKey);

			if (carGarageProduct == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCarGarageProductException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(carGarageProduct);
		}
		catch (NoSuchCarGarageProductException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CarGarageProduct removeImpl(CarGarageProduct carGarageProduct) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(carGarageProduct)) {
				carGarageProduct = (CarGarageProduct)session.get(
					CarGarageProductImpl.class,
					carGarageProduct.getPrimaryKeyObj());
			}

			if (carGarageProduct != null) {
				session.delete(carGarageProduct);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (carGarageProduct != null) {
			clearCache(carGarageProduct);
		}

		return carGarageProduct;
	}

	@Override
	public CarGarageProduct updateImpl(CarGarageProduct carGarageProduct) {
		boolean isNew = carGarageProduct.isNew();

		if (!(carGarageProduct instanceof CarGarageProductModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(carGarageProduct.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					carGarageProduct);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in carGarageProduct proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CarGarageProduct implementation " +
					carGarageProduct.getClass());
		}

		CarGarageProductModelImpl carGarageProductModelImpl =
			(CarGarageProductModelImpl)carGarageProduct;

		if (Validator.isNull(carGarageProduct.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			carGarageProduct.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (carGarageProduct.getCreateDate() == null)) {
			if (serviceContext == null) {
				carGarageProduct.setCreateDate(date);
			}
			else {
				carGarageProduct.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!carGarageProductModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				carGarageProduct.setModifiedDate(date);
			}
			else {
				carGarageProduct.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(carGarageProduct);
			}
			else {
				carGarageProduct = (CarGarageProduct)session.merge(
					carGarageProduct);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CarGarageProductImpl.class, carGarageProductModelImpl, false, true);

		cacheUniqueFindersCache(carGarageProductModelImpl);

		if (isNew) {
			carGarageProduct.setNew(false);
		}

		carGarageProduct.resetOriginalValues();

		return carGarageProduct;
	}

	/**
	 * Returns the car garage product with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the car garage product
	 * @return the car garage product
	 * @throws NoSuchCarGarageProductException if a car garage product with the primary key could not be found
	 */
	@Override
	public CarGarageProduct findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCarGarageProductException {

		CarGarageProduct carGarageProduct = fetchByPrimaryKey(primaryKey);

		if (carGarageProduct == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCarGarageProductException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return carGarageProduct;
	}

	/**
	 * Returns the car garage product with the primary key or throws a <code>NoSuchCarGarageProductException</code> if it could not be found.
	 *
	 * @param carGarageProductId the primary key of the car garage product
	 * @return the car garage product
	 * @throws NoSuchCarGarageProductException if a car garage product with the primary key could not be found
	 */
	@Override
	public CarGarageProduct findByPrimaryKey(long carGarageProductId)
		throws NoSuchCarGarageProductException {

		return findByPrimaryKey((Serializable)carGarageProductId);
	}

	/**
	 * Returns the car garage product with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param carGarageProductId the primary key of the car garage product
	 * @return the car garage product, or <code>null</code> if a car garage product with the primary key could not be found
	 */
	@Override
	public CarGarageProduct fetchByPrimaryKey(long carGarageProductId) {
		return fetchByPrimaryKey((Serializable)carGarageProductId);
	}

	/**
	 * Returns all the car garage products.
	 *
	 * @return the car garage products
	 */
	@Override
	public List<CarGarageProduct> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the car garage products.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @return the range of car garage products
	 */
	@Override
	public List<CarGarageProduct> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the car garage products.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of car garage products
	 */
	@Override
	public List<CarGarageProduct> findAll(
		int start, int end,
		OrderByComparator<CarGarageProduct> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the car garage products.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CarGarageProductModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of car garage products
	 * @param end the upper bound of the range of car garage products (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of car garage products
	 */
	@Override
	public List<CarGarageProduct> findAll(
		int start, int end,
		OrderByComparator<CarGarageProduct> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CarGarageProduct> list = null;

		if (useFinderCache) {
			list = (List<CarGarageProduct>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CARGARAGEPRODUCT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CARGARAGEPRODUCT;

				sql = sql.concat(CarGarageProductModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CarGarageProduct>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the car garage products from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CarGarageProduct carGarageProduct : findAll()) {
			remove(carGarageProduct);
		}
	}

	/**
	 * Returns the number of car garage products.
	 *
	 * @return the number of car garage products
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CARGARAGEPRODUCT);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "carGarageProductId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CARGARAGEPRODUCT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CarGarageProductModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the car garage product persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CarGarageProductModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByCarGarageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCarGarageId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"carGarageId"}, true);

		_finderPathWithoutPaginationFindByCarGarageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCarGarageId",
			new String[] {Long.class.getName()}, new String[] {"carGarageId"},
			true);

		_finderPathCountByCarGarageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCarGarageId",
			new String[] {Long.class.getName()}, new String[] {"carGarageId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CarGarageProductImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = CommerceTrainingPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommerceTrainingPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommerceTrainingPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CARGARAGEPRODUCT =
		"SELECT carGarageProduct FROM CarGarageProduct carGarageProduct";

	private static final String _SQL_SELECT_CARGARAGEPRODUCT_WHERE =
		"SELECT carGarageProduct FROM CarGarageProduct carGarageProduct WHERE ";

	private static final String _SQL_COUNT_CARGARAGEPRODUCT =
		"SELECT COUNT(carGarageProduct) FROM CarGarageProduct carGarageProduct";

	private static final String _SQL_COUNT_CARGARAGEPRODUCT_WHERE =
		"SELECT COUNT(carGarageProduct) FROM CarGarageProduct carGarageProduct WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "carGarageProduct.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CarGarageProduct exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CarGarageProduct exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CarGarageProductPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CarGarageProductModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CarGarageProductModelImpl carGarageProductModelImpl =
				(CarGarageProductModelImpl)baseModel;

			long columnBitmask = carGarageProductModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					carGarageProductModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						carGarageProductModelImpl.getColumnBitmask(columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					carGarageProductModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CarGarageProductImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CarGarageProductTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			CarGarageProductModelImpl carGarageProductModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						carGarageProductModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = carGarageProductModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			orderByColumnsBitmask |= CarGarageProductModelImpl.getColumnBitmask(
				"cpDefinitionId");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}