/**
 * 
 */

package org.komea.product.backend.storage;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.fs.IObjectStorage;
import org.komea.product.backend.utils.SearchFilter;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class DAOStorageTest
{
    
    
    /**
     */
    public class ObjectStorageStub<T> implements IObjectStorage<T>
    {
        
        
        private T res;
        
        
        
        /**
         * Method get.
         * @return T
         * @see org.komea.product.backend.fs.IObjectStorage#get()
         */
        @Override
        public T get() {
        
        
            return res;
        }
        
        
        /**
         * Method set.
         * @param _object T
         * @see org.komea.product.backend.fs.IObjectStorage#set(T)
         */
        @Override
        public void set(final T _object) {
        
        
            res = _object;
            
        }
        
    }
    
    
    
    /**
    
     * @throws Exception
     * @throws java.lang.Exception */
    @Test
    public void testDAO() throws Exception {
    
    
        final ObjectStorageStub<DAOStorageIndex<String>> registerStorage =
                new ObjectStorageStub<DAOStorageIndex<String>>();
        final DAOStorage<String> daoStorage = new DAOStorage<String>(registerStorage);
        daoStorage.enableSaveOnChange();
        daoStorage.update("A");
        daoStorage.update("B");
        daoStorage.update("C");
        daoStorage.update("D");
        Assert.assertEquals(4, daoStorage.selectAll().size());
        daoStorage.delete("C");
        Assert.assertEquals(3, daoStorage.selectAll().size());
        daoStorage.delete("B");
        Assert.assertEquals(2, daoStorage.selectAll().size());
        Assert.assertEquals(1, daoStorage.find(new SearchFilter<String>()
        {
            
            
            @Override
            public boolean match(final String _object) {
            
            
                return "A".equals(_object);
            }
        }).size());
        daoStorage.deleteAll();
        Assert.assertEquals(0, daoStorage.selectAll().size());
        
    }
    
    
}
