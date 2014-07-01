/**
 *
 */

package org.komea.product.backend.csv.service;



import java.util.Date;

import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.PersonCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.google.common.base.Predicate;



/**
 * @author sleroy
 */
public class GestionDeTempsImportationMain
{


    /**
     * @param args
     */
    public static void main(final String[] args) {


        // Get sum of activity per developer per date
        final CSVRowKpi csvKpi = new CSVRowKpi();
        csvKpi.loadFromFile("/home/sleroy/Téléchargements/data.csv");
        csvKpi.beginRow(1);
        csvKpi.beginCol(0);

        final CSVConverter csvConverter = new CSVConverter();
        csvConverter.defineColumns("time", "date", "month", "year", "Head", "Manager", "Worker",
                "Site", "By", "Activity Code", "Activity Name", "Cost Code", "Cost Name",
                "Project Code", "Project Customer", "Project Product", "Project Version",
                "Project Integrator", "Project Customer", "Project Model");

        csvConverter.defineColumnType("time", Double.class);
        csvConverter.defineColumnType("date", new Converter<String, Date>()
                {


            @Override
            public Date convert(final String _source) {


                return null;// TODO
            }

                });
        csvConverter.defineColumnType("Worker", new Converter<String, IEntity>()
                {


            @Autowired
            IPersonService personService;



            @Override
            public IEntity convert(final String _workerName) {


                final PersonCriteria personCriteria = new PersonCriteria();
                personCriteria.createCriteria().andLastNameEqualTo(_workerName);
                return CollectionUtil.singleOrNull(personService.selectByCriteria(personCriteria));

            }

                });
        // FILTER1
        csvConverter.filterRows(new Predicate<CSVEntry>()
                {


            @Override
            public boolean apply(final CSVEntry _input) {


                return new Date().equals(_input.getValue("date"));
            }

                });
        csvConverter.filterRowsWithValue("Activity Name", "AWAY"); // FILTER2


        csvConverter.filterColumns("time", "Worker");

        csvConverter.groupBy("Worker");

        // GROUP BY, date, Worker, Activity Name
        csvConverter.convertRemaininColumns(new CSVRemainingColumnsConverter()
        {


            @Override
            public void groupColumns(final CSVEntry[] _entries, final CSVEntry _newEntry) {


                _newEntry.defineColumn("sum");
                _newEntry.setValue("sum", new Sum(_entries).compute());
            }
        });


        csvKpi.setCsvConverter(csvConverter);

    }
}
