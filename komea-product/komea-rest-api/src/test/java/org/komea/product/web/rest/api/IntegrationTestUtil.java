
package org.komea.product.web.rest.api;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.komea.product.database.model.PersonGroup;

public class IntegrationTestUtil {
    
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
    
    public static <T> T convertJSONToObject(final String _jsonMessage, final Class<T> _returnType) throws JsonGenerationException,
            JsonMappingException, IOException {
    
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(_jsonMessage, _returnType);
        
    }
    
    public static void main(final String[] args) {
    
        try {
            System.out.println(convertObjectToJSON(new ArrayList<PersonGroup>()));
        } catch (final JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
