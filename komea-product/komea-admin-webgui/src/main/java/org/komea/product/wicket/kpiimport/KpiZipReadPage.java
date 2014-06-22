
package org.komea.product.wicket.kpiimport;



import java.io.File;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IKpiImportationService;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;



/**
 * Kpi importation page.
 *
 * @author sleroy
 */
public class KpiZipReadPage extends LayoutPage
{


    private static final Logger LOGGER           = LoggerFactory.getLogger(KpiZipReadPage.class);

    private static final long   serialVersionUID = 825152658028992367L;

    @SpringBean
    IKpiImportationService      kpiImportationService;


    @SpringBean
    IKPIService                 kpiService;



    public KpiZipReadPage(final PageParameters _parameters, final File _file) {
    
    
        super(_parameters);
        final List<KpiEntry> modelList = Lists.newArrayList();

        add(new AjaxLink("import")
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget _target) {
            
            
                new ImportCatalogTask(_file, modelList, kpiImportationService, kpiService).run();


            }
        });
        
        
        final ListDataModel<KpiEntry> dataModel = new ListDataModel<KpiEntry>(modelList);
        
        final DataTable<KpiEntry, String> build =
                DataTableBuilder.<KpiEntry, String> newTable("table")
                        .addColumn(getString("kpiimport.resource"), "entry")
                        .addColumn(getString("kpiimport.kpikey"), "kpiName")
                        .addColumn(new ImportColumn(this)).displayRows(100).withData(dataModel)
                        .build();
        build.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)));
        add(build);
        
        // In WebPage
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        
        add(feedbackPanel);
        
        
    }


    @Override
    public String getTitle() {


        return getString("KpiZipReadPage.title");
    }


}
