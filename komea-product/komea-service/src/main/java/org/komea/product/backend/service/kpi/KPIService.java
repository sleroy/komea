package org.komea.product.backend.service.kpi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.Validate;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.IKpiQueryService;
import org.komea.product.backend.api.IKpiValueService;
import org.komea.product.backend.api.exceptions.KpiAlreadyExistingException;
import org.komea.product.backend.criterias.FindKpi;
import org.komea.product.backend.criterias.FindKpiOrFail;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.dao.HasSuccessFactorKpiDao;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.dao.KpiAlertTypeDao;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.HasSuccessFactorKpiCriteria;
import org.komea.product.database.model.HasSuccessFactorKpiKey;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.database.model.KpiAlertTypeCriteria;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.database.model.SuccessFactor;
import org.komea.product.service.dto.KpiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
@Transactional()
public final class KPIService extends AbstractService<Kpi, Integer, KpiCriteria> implements IKPIService {

    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-service");

    @Autowired
    private KpiAlertTypeDao alertDao;

    @Autowired
    private ICronRegistryService cronRegistry;

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IKpiQueryService kpiQueryRegistry;

    @Autowired
    private IKpiValueService kpiValueService;

    @Autowired
    private MeasureDao measureDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private KpiDao requiredDAO;

    @Autowired
    private HasSuccessFactorKpiDao successFactorKpiDao;

    @Override
    protected KpiCriteria createKeyCriteria(final String key) {

        final KpiCriteria criteria = new KpiCriteria();
        criteria.createCriteria().andKpiKeyEqualTo(key);
        return criteria;
    }

    @Override
    public void deleteKpi(final Kpi kpi) {

        final String kpiFormula = kpi.getEsperRequest();
        final Integer idKpi = kpi.getId();

        final KpiCriteria kpiCriteria = new KpiCriteria();
        // FIXME TODO : si kpiFormula n'est pas utilisee dans d'autres kpis alors supprimer les mesures
        final boolean deleteMeasures = false;
        if (deleteMeasures) {
            final MeasureCriteria measureCriteria = new MeasureCriteria();
            measureCriteria.createCriteria().andIdKpiEqualTo(kpiFormula);
            measureDao.deleteByCriteria(measureCriteria);
        }

        final KpiAlertTypeCriteria kpiAlertTypeCriteria = new KpiAlertTypeCriteria();
        kpiAlertTypeCriteria.createCriteria().andIdKpiEqualTo(idKpi);
        alertDao.deleteByCriteria(kpiAlertTypeCriteria);

        final HasSuccessFactorKpiCriteria hasSuccessFactorKpiCriteria = new HasSuccessFactorKpiCriteria();
        hasSuccessFactorKpiCriteria.createCriteria().andIdKpiEqualTo(idKpi);
        successFactorKpiDao.deleteByCriteria(hasSuccessFactorKpiCriteria);

        kpiQueryRegistry.removeQuery(kpi);

        delete(kpi);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.cep.tester.IKPIService#findKPI(org.komea.product.service
     * .dto.KpiKey)
     */
    @Override
    public Kpi findKPI(final KpiKey _kpiKey) {

        return new FindKpi(_kpiKey, requiredDAO).find();
    }

    @Override
    public Kpi findKPI(final String _kpiKey) {

        return new FindKpi(_kpiKey, requiredDAO).find();
    }

    /**
     * Method findKPIOrFail.
     *
     * @param _kpiKey KpiKey
     * @return Kpi
     * @see org.komea.product.backend.api.IKPIService#findKPIOrFail(KpiKey)
     */
    @Override
    public Kpi findKPIOrFail(final KpiKey _kpiKey) {

        return new FindKpiOrFail(_kpiKey, requiredDAO).find();
    }

    /**
     * (non-Javadoc)
     *
     * @see
     * org.komea.product.backend.api.IKPIService#findKPIOrFail(java.lang.String)
     */
    @Override
    public Kpi findKPIOrFail(final String _kpiKey) {

        return new FindKpi(_kpiKey, requiredDAO).find();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.api.IKPIService#getAllKpisOfEntityType(org.
     * komea.product.database.enums.EntityType)
     */
    @Override
    public List<Kpi> getAllKpisOfEntityType(final EntityType _entityType) {

        final KpiCriteria kpiCriteria = new KpiCriteria();
        kpiCriteria.createCriteria().andEntityTypeEqualTo(_entityType);
        return selectByCriteria(kpiCriteria);

    }

    @Override
    public List<Kpi> getBaseKpisOfGroupKpiKeys(final List<String> groupKpiKeys) {

        final Set<String> kpiKeys = new HashSet<String>(groupKpiKeys.size());
        for (final String groupKpiKey : groupKpiKeys) {
            kpiKeys.add(Kpi.getBaseKey(groupKpiKey));
        }
        return selectByKeys(new ArrayList<String>(kpiKeys));
    }

    @Override
    public List<Kpi> getKpisOfProviderType(final ProviderType providerType) {
        final KpiCriteria kpiCriteria = new KpiCriteria();
        kpiCriteria.createCriteria().andProviderTypeEqualTo(providerType);
        return selectByCriteria(kpiCriteria);
    }

    /**
     * Method getCronRegistry.
     *
     * @return ICronRegistryService
     */
    public ICronRegistryService getCronRegistry() {

        return cronRegistry;
    }

    /**
     * Method getEntityService.
     *
     * @return IEntityService
     */
    public IEntityService getEntityService() {

        return entityService;
    }

    /**
     * Method getKpiDAO.
     *
     * @return KpiDao
     */
    public KpiDao getKpiDAO() {

        return requiredDAO;
    }

    public IKpiQueryService getKpiQueryRegistry() {

        return kpiQueryRegistry;
    }

    @Override
    public List<Kpi> getKpisForGroups(final List<Kpi> simpleKpis) {

        final List<Kpi> kpis = new ArrayList<Kpi>(simpleKpis.size() * 2);
        for (final Kpi kpi : simpleKpis) {
            kpis.addAll(Kpi.getKpisForGroups(kpi));
        }
        return kpis;
    }

    @Override
    public List<Kpi> getKpisOfGroupKpiKeys(final List<String> groupKpiKeys, final List<Kpi> kpis) {

        final Map<String, Kpi> kpiByKeys = new HashMap<String, Kpi>(kpis.size());
        for (final Kpi kpi : kpis) {
            kpiByKeys.put(kpi.getKpiKey(), kpi);
        }
        final List<Kpi> results = new ArrayList<Kpi>(groupKpiKeys.size());
        for (final String groupKpiKey : groupKpiKeys) {
            final Kpi kpi = kpiByKeys.get(Kpi.getBaseKey(groupKpiKey));
            final Kpi groupKpi = Kpi.copy(kpi);
            if (Kpi.isAverage(groupKpiKey)) {
                groupKpi.setAverageKpi(!ValueType.PERCENT.equals(groupKpi.getValueType()));
            } else if (Kpi.isSum(groupKpiKey)) {
                groupKpi.setSumKpi();
            }
            results.add(groupKpi);
        }
        return results;
    }

    public IKpiValueService getKpiValueService() {

        return kpiValueService;
    }

    /**
     * Project : DAO
     *
     * @return the project DAO.
     */
    public ProjectDao getProjectDao() {

        return projectDao;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.genericservice.AbstractService#getRequiredDAO()
     */
    @Override
    public IGenericDAO<Kpi, Integer, KpiCriteria> getRequiredDAO() {

        return requiredDAO;
    }

    @Override
    public boolean isKpiFormulaShared(Kpi _kpi) {
        Validate.notNull(_kpi.getId());
        Validate.notNull(_kpi.getEsperRequest());
        final String formula = _kpi.getEsperRequest();
        final List<Kpi> kpis = selectAll();
        for (final Kpi kpi : kpis) {
            if (formula.equals(_kpi.getEsperRequest())
                    && _kpi.getId() != kpi.getId()) {
                return true;
            }
        }
        return false;
    }

    private MeasureCriteria criteriaForKpiFormula(final Kpi _kpi) {
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        final FormulaID formulaId = FormulaID.of(_kpi);
        measureCriteria.createCriteria().andIdKpiEqualTo(formulaId.getId());
        return measureCriteria;
    }

    @Override
    public int purgeHistoryOfKpi(final Kpi _kpi) {
        final MeasureCriteria criteriaForKpiFormula = criteriaForKpiFormula(_kpi);
        return measureDao.deleteByCriteria(criteriaForKpiFormula);
    }

    @Override
    public int countMeasuresOfKpi(final Kpi _kpi) {
        final MeasureCriteria criteriaForKpiFormula = criteriaForKpiFormula(_kpi);
        return measureDao.countByCriteria(criteriaForKpiFormula);
    }

    /**
     * Method saveOrUpdateKpi.
     *
     * @param _kpi Kpi
     * @see org.komea.product.backend.api.IKPIService#saveOrUpdate(Kpi)
     */
    @Override
    public void saveOrUpdate(final Kpi _kpi) {

        if (_kpi.getId() == null) {
            LOGGER.debug("Saving new KPI : {}", _kpi.getKpiKey());
            if (findKPI(KpiKey.ofKpi(_kpi)) != null) {
                throw new KpiAlreadyExistingException(_kpi.getKpiKey());
            }
            requiredDAO.insert(_kpi);
        } else {
            LOGGER.debug("KPI {} updated", _kpi.getKpiKey());
            updateByPrimaryKeyWithBlobs(_kpi);
        }

        kpiQueryRegistry.createOrUpdateQueryFromKpi(_kpi);
    }

    @Override
    public void saveOrUpdateKpi(final Kpi kpi, final List<KpiAlertType> alertTypes, final List<SuccessFactor> successFactors) {

        saveOrUpdate(kpi);
        final Integer idKpi = kpi.getId();

        final KpiAlertTypeCriteria kpiAlertTypeCriteria = new KpiAlertTypeCriteria();
        kpiAlertTypeCriteria.createCriteria().andIdKpiEqualTo(idKpi);
        alertDao.deleteByCriteria(kpiAlertTypeCriteria);
        if (alertTypes != null) {
            for (final KpiAlertType alertType : alertTypes) {
                alertDao.insert(alertType);
            }
        }

        final HasSuccessFactorKpiCriteria hasSuccessFactorKpiCriteria = new HasSuccessFactorKpiCriteria();
        hasSuccessFactorKpiCriteria.createCriteria().andIdKpiEqualTo(idKpi);
        successFactorKpiDao.deleteByCriteria(hasSuccessFactorKpiCriteria);
        if (successFactors != null) {
            for (final SuccessFactor successFactor : successFactors) {
                successFactorKpiDao.insert(new HasSuccessFactorKpiKey(successFactor.getId(), idKpi));
            }
        }
    }

    @Override
    public List<Kpi> selectAll() {

        return requiredDAO.selectByCriteriaWithBLOBs(null);
    }

    /**
     * Method setCronRegistry.
     *
     * @param _cronRegistry ICronRegistryService
     */
    public void setCronRegistry(final ICronRegistryService _cronRegistry) {

        cronRegistry = _cronRegistry;
    }

    /**
     * Method setEntityService.
     *
     * @param _entityService IEntityService
     */
    public void setEntityService(final IEntityService _entityService) {

        entityService = _entityService;
    }

    /**
     * Method setKpiDAO.
     *
     * @param _kpiDAO KpiDao
     */
    public void setKpiDAO(final KpiDao _kpiDAO) {

        requiredDAO = _kpiDAO;
    }

    public void setKpiQueryRegistry(final IKpiQueryService _kpiQueryRegistry) {

        kpiQueryRegistry = _kpiQueryRegistry;
    }

    public void setKpiValueService(final IKpiValueService _kpiValueService) {

        kpiValueService = _kpiValueService;
    }

    public void setProjectDao(final ProjectDao _projectDao) {

        projectDao = _projectDao;
    }

    /**
     * @param _requiredDAO
     */
    public void setRequiredDAO(final KpiDao _requiredDAO) {

        requiredDAO = _requiredDAO;
    }

    private void updateByPrimaryKeyWithBlobs(final Kpi _kpi) {

        final KpiCriteria kpiCriteria = new KpiCriteria();
        kpiCriteria.createCriteria().andIdEqualTo(_kpi.getId());
        requiredDAO.updateByCriteriaWithBLOBs(_kpi, kpiCriteria);
    }

}
