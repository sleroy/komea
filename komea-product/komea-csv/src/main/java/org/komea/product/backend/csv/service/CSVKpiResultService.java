/**
 *
 */

package org.komea.product.backend.csv.service;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.komea.product.backend.csv.errors.CSVConversionException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.utils.Validate;
import org.komea.product.service.dto.EntityKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.collect.Maps;



/**
 * @author sleroy
 */
@Service
public class CSVKpiResultService implements ICSVKpiResult
{
    
    
    private static List<String[]> toStringArray(final Map<String, Number> _kpIResultConverted) {
    
    
        final List<String[]> records = new ArrayList<String[]>();
        // add header record
        records.add(new String[] {
                "Entity", "Value" });
        for (final Entry<String, Number> entry : _kpIResultConverted.entrySet()) {
            records.add(new String[] {
                    entry.getKey(), entry.getValue().toString() });
        }
        return records;
    }
    
    
    
    @Autowired
    private IEntityService entityService;
    
    
    
    public void exportCSV(final KpiResult _result, final File _file) {
    
    
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(_file));
            exportCSV(_result, writer);
        } catch (final Exception e) {
            throw new CSVConversionException(
                    "Could not write the object into csv format in the file " + _file, e);
        } finally {
            IOUtils.closeQuietly(writer);
            ;
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.csv.service.ICSVKpiResult#exportCSV(org.komea.product.database.dto.KpiResult, java.io.Writer)
     */
    @Override
    public void exportCSV(final KpiResult _kpiResult, final Writer _writer) {
    
    
        Validate.notNull(_kpiResult, "A kpi result should be provided");
        Validate.notNull(_writer, "The stream should exist");
        final Map<String, Number> convertKpiResult = convertKpiResult(_kpiResult);
        
        
        CSVWriter csvWriter = null;
        try {
            csvWriter = new CSVWriter(_writer);
            
            if (csvWriter.checkError()) {
                throw new CSVConversionException("unknown error during writing the csv", null);
            }
            csvWriter.writeAll(toStringArray(convertKpiResult));
        } finally {
            IOUtils.closeQuietly(csvWriter);
        }
        
        
    }
    
    
    public IEntityService getEntityService() {
    
    
        return entityService;
    }
    
    
    public void setEntityService(final IEntityService _entityService) {
    
    
        entityService = _entityService;
    }
    
    
    private Map<String, Number> convertKpiResult(final KpiResult _kpiResult) {
    
    
        final Map<String, Number> results = Maps.newHashMap();
        for (final Entry<EntityKey, Number> entry : _kpiResult.getMap().entrySet()) {
            final IEntity entityOrFail = entityService.getEntityOrFail(entry.getKey());
            
            results.put(entityOrFail.getKey(), entry.getValue());
            
        }
        return results;
    }
}
