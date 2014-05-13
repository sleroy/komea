package org.komea.product.wicket.kpivalues;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.IKpiValueService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.LayoutPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Person admin page
 * 
 * @author sleroy
 */
public class KpiValuesPage extends LayoutPage {

	private final class KpiTableList extends ListView<Kpi> {

		/**
		 * @param _id
		 */
		public KpiTableList(final String _id, final List<Kpi> _kpis) {

			super(_id, _kpis);

		}

		@Override
		protected void populateItem(final ListItem<Kpi> _item) {
			final Kpi kpi = _item.getModelObject();

			final List<IEntity> entitiesByEntityType = entityService.getEntitiesByEntityType(kpi.getEntityType());
			_item.add(new Label("kpi", kpi.getName()));
			_item.add(new KpiValueList("rows", entitiesByEntityType, kpi));

		}
	}

	private final class KpiValueList extends ListView<IEntity> {

		private final Kpi	kpi;

		public KpiValueList(final String _id, final List<IEntity> _entitiesByEntityType, final Kpi _kpi) {
			super(_id, _entitiesByEntityType);
			kpi = _kpi;

		}

		@Override
		protected void populateItem(final ListItem<IEntity> _item) {
			final IEntity entity = _item.getModelObject();

			_item.add(new Label("entity", Model.of(_item.getModelObject().getDisplayName())));
			final HistoryKey historyKey = HistoryKey.of(kpi, entity);
			_item.add(new Label("lastvalue", Model.of(statsService.getLastStoredValueInHistory(historyKey))));
			_item.add(new Label("currentvalue", Model.of(statsService.evaluateTheCurrentKpiValue(historyKey))));
		}
	}

	private static final Logger	LOGGER	         = LoggerFactory.getLogger(KpiValuesPage.class);

	/**
     * 
     */
	private static final long	serialVersionUID	= 825152658028992367L;
	@SpringBean
	private IEntityService	    entityService;

	@SpringBean
	private IKPIService	        kpiService;

	@SpringBean
	private IKpiValueService	kpiValueService;

	@SpringBean
	private IStatisticsAPI	    statsService;

	public KpiValuesPage(final PageParameters _parameters) {

		super(_parameters);

		final List<Kpi> selectAll = kpiService.selectAll();
		final ListView<Kpi> listView = new KpiTableList("kpis", selectAll);

		add(listView);

	}

	public IKpiValueService getKpiValueService() {

		return kpiValueService;
	}

	public void setKpiValueService(final IKpiValueService _kpiValueService) {

		kpiValueService = _kpiValueService;

	}

}
