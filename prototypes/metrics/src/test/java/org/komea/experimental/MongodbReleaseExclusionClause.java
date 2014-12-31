package org.komea.experimental;


public class MongodbReleaseExclusionClause implements IReleaseFilter
{

    @Override
    public String getReleaseExclusionClause() {
    
        return "AND name NOT LIKE '%-rc%'";
    }
    
}
