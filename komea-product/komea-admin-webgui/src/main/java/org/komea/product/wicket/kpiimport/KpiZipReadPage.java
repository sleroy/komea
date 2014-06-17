
package org.komea.product.wicket.kpiimport;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IKpiImportationService;
import org.komea.product.backend.service.kpi.KpiDefinition;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Kpi importation page.
 *
 * @author sleroy
 */
public class KpiZipReadPage extends LayoutPage
{


    public class KpiUpdateButton extends AjaxLink
    {


        private final IModel<KpiEntry> rowModel;



        public KpiUpdateButton(
                final String _id,
                final IModel<String> _model,
                final IModel<KpiEntry> _rowModel) {


            super(_id, _model);
            rowModel = _rowModel;
        }


        @Override
        public void onClick(final AjaxRequestTarget _target) {


            info("Importing the kpi " + rowModel.getObject().getEntry());
            kpiService.saveOrUpdate(rowModel.getObject().getKpiDefinition().getKpi());
            info("kpi imported");
            setEnabled(false);
            setModelObject("Already imported");
        }
    }



    private final class ImportColumn extends AbstractColumn<KpiEntry, String>
    {


        private static final long serialVersionUID = 1L;



        public ImportColumn() {


            super(Model.of(""));
        }


        /*
         * (non-Javadoc)
         * @see
         * org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item,
         * java.lang.String, org.apache.wicket.model.IModel)
         */
        @Override
        public void populateItem(
                final Item<ICellPopulator<KpiEntry>> _cellItem,
                final String _componentId,
                final IModel<KpiEntry> _rowModel) {


            final KpiEntry kpiEntry = _rowModel.getObject();
            Component link = null;
            if (kpiEntry.hasNoDef()) {
                link = new Label("import", Model.of("Could not import the script"));

            } else {
                if (kpiService.exists(kpiEntry.getKpiDefinition().getKpi().getKey())) {
                    link =
                            new KpiUpdateButton("import", Model.of("Upgrade existing kpi"),
                                    _rowModel);
                } else {
                    link = new KpiUpdateButton("import", Model.of("Import"), _rowModel);
                }

            }
            _cellItem.add(new ImportPanel(_componentId, _rowModel, link));
        }
    }



    private static final Logger    LOGGER           = LoggerFactory.getLogger(KpiZipReadPage.class);

    private static final long      serialVersionUID = 825152658028992367L;

    @SpringBean
    private IKpiImportationService kpiImportationService;


    @SpringBean
    private IKPIService            kpiService;



    public KpiZipReadPage(final PageParameters _parameters, final File _file) {


        super(_parameters);
        final Map<String, KpiDefinition> importCatalog = kpiImportationService.importCatalog(_file);

        final ListDataModel<KpiEntry> dataModel =
                new ListDataModel<KpiEntry>(buildCatalogItems(importCatalog));

        final DataTable<KpiEntry, String> build =
                DataTableBuilder.<KpiEntry, String> newTable("table")
                .addColumn(getString("kpiimport.resource"), "entry")
                .addColumn(getString("kpiimport.kpikey"), "kpiDefinition.kpi.kpiKey")

                .addColumn(new ImportColumn()).displayRows(100).withData(dataModel).build();
        add(build);

    }


    /**
     * Import catalog
     */
    private List<KpiEntry> buildCatalogItems(final Map<String, KpiDefinition> _importCatalog) {


        final List<KpiEntry> entries = new ArrayList<KpiEntry>(_importCatalog.size());
        for (final Entry<String, KpiDefinition> catalogEntry : _importCatalog.entrySet()) {
            final KpiEntry kpiEntry = new KpiEntry();
            kpiEntry.setEntry(catalogEntry.getKey());
            kpiEntry.setKpiDefinition(catalogEntry.getValue());
            entries.add(kpiEntry);
        }

        return entries;
    }


}
