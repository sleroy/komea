/**
 * 
 */
package org.komea.product.wicket.kpiimport;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

final class ImportColumn extends AbstractColumn<KpiEntry, String>
{


    private final KpiZipReadPage kpiZipReadPage;
    private static final long serialVersionUID = 1L;



    public ImportColumn(KpiZipReadPage _kpiZipReadPage) {


        super(Model.of(""));
        kpiZipReadPage = _kpiZipReadPage;
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
                link = new Label("import", kpiZipReadPage.getString("kpiimport.error"));
                break;
            case IMPORTED:
                link = new Label("import", kpiZipReadPage.getString("kpiimport.imported"));
                break;
            case NO_IMPORT:
                link = new Label("import", kpiZipReadPage.getString("kpiimport.noimport"));
                break;
            case UPDATED:
                link = new Label("import", kpiZipReadPage.getString("kpiimport.updated"));
                break;
            default:
                throw new UnsupportedOperationException();

        }

        _cellItem.add(new ImportPanel<KpiEntry>(_componentId, _rowModel, link));

    }
}