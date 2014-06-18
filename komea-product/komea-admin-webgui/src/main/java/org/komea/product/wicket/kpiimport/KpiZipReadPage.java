
package org.komea.product.wicket.kpiimport;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IKpiImportationService;
import org.komea.product.backend.service.kpi.KpiDefinition;
import org.komea.product.backend.utils.StackTracePrint;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.kpiimport.KpiEntry.Status;
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
            switch (kpiEntry.getStatus()) {
                case ERROR:
                    link = new Label("import", getString("kpiimport.error"));
                    break;
                case IMPORTED:
                    link = new Label("import", getString("kpiimport.imported"));
                    break;
                case NO_IMPORT:
                    link = new Label("import", getString("kpiimport.noimport"));
                    break;
                case UPDATED:
                    link = new Label("import", getString("kpiimport.updated"));
                    break;
                default:
                    throw new UnsupportedOperationException();
                    
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
                        .addColumn(getString("kpiimport.kpikey"), "kpiName")
                        .addColumn(new ImportColumn()).displayRows(100).withData(dataModel).build();
        add(build);
        
        // In WebPage
        add(new FeedbackPanel("feedback"));
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("KpiZipReadPage.title");
    }
    
    
    /**
     * Import catalog
     */
    private List<KpiEntry> buildCatalogItems(final Map<String, KpiDefinition> _importCatalog) {
    
    
        final List<KpiEntry> entries = new ArrayList<KpiEntry>(_importCatalog.size());
        for (final Entry<String, KpiDefinition> catalogEntry : _importCatalog.entrySet()) {
            Status status = Status.NO_IMPORT;
            if (catalogEntry.getValue() != null) {
                try {
                    if (kpiService.exists(catalogEntry.getValue().getKpi().getKey())) {
                        status = Status.UPDATED;
                    } else {
                        status = Status.IMPORTED;
                    }
                    kpiService.saveOrUpdate(catalogEntry.getValue().getKpi());
                } catch (final Exception e) {
                    error("Exception : " + StackTracePrint.printTrace(e));
                    status = Status.ERROR;
                }
            }
            final KpiEntry kpiEntry = new KpiEntry();
            kpiEntry.setEntry(catalogEntry.getKey());
            kpiEntry.setKpiName(catalogEntry.getValue().getKpi().getKey());
            kpiEntry.setStatus(status);
            entries.add(kpiEntry);
        }
        
        return entries;
    }
    
    
}
