/**
 * 
 */

package org.komea.product.backend.criterias;

import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;

/**
 * This class defines a criteria to find a kpi It returns an exception if not
 * found
 * 
 * @author sleroy
 */
public class FindKpiPerId {

	private final KpiDao	kpiDao;
	private final Integer	kpiKey;

	/**
	 * Find a kpi or fail
	 * 
	 * @param _kpiKey
	 *            the kpi key
	 */
	public FindKpiPerId(final Integer _kpiKey, final KpiDao _kpiDao) {

		super();
		kpiKey = _kpiKey;
		kpiDao = _kpiDao;
	}

	public Kpi find() {

		final KpiCriteria kpiCriteria = new KpiCriteria();
		kpiCriteria.createCriteria().andIdEqualTo(kpiKey);
		final Kpi findKPI = CollectionUtil.singleOrNull(kpiDao.selectByCriteriaWithBLOBs(kpiCriteria));
		if (findKPI == null) { throw new KPINotFoundRuntimeException(getKpiKey()); }
		return findKPI;
	}

	public KpiDao getKpiDao() {

		return kpiDao;
	}

	public Integer getKpiKey() {

		return kpiKey;
	}

}
