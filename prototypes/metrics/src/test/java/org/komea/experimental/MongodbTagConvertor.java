package org.komea.experimental;



public class MongodbTagConvertor implements IReleaseTagConvertor
{

    @Override
    public String toTag(final String release) {
    
        return "r"+release;
    }

    @Override
    public String toRelease(final String tag) {
    
        return tag.substring(1);
    }
    
}
