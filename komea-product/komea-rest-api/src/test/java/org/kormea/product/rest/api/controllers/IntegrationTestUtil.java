
package org.kormea.product.rest.api.controllers;


import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class IntegrationTestUtil
{
    
    public static byte[] convertObjectToFormUrlEncodedBytes(final Object object) {
    
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        
        final Map<String, Object> propertyValues = mapper.convertValue(object, Map.class);
        
        final Set<String> propertyNames = propertyValues.keySet();
        final Iterator<String> nameIter = propertyNames.iterator();
        
        final StringBuilder formUrlEncoded = new StringBuilder();
        
        for (int index = 0; index < propertyNames.size(); index++) {
            final String currentKey = nameIter.next();
            final Object currentValue = propertyValues.get(currentKey);
            
            formUrlEncoded.append(currentKey);
            formUrlEncoded.append("=");
            formUrlEncoded.append(currentValue);
            
            if (nameIter.hasNext()) {
                formUrlEncoded.append("&");
            }
        }
        
        return formUrlEncoded.toString().getBytes();
    }
    public static String convertObjectToJSON(final Object object) throws JsonGenerationException, JsonMappingException, IOException {
    
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
        
    }
    
}
