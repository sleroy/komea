/**
 * 
 */

package org.komea.product.backend.storage;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.utils.SearchFilter;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class DAOObjectStorageTest
{
    
    
    /**
     */
    public class ObjectStorageStub<T> implements IObjectStorage<T>
    {
        
        
        private T res;
        
        
        
        /**
         * Method get.
         * 
         * @return T
         * @see org.komea.product.backend.service.fs.IObjectStorage#get()
         */
        @Override
        public T get() {
        
        
            return res;
        }
        
        
        /**
         * Method set.
         * 
         * @param _object
         *            T
         * @see org.komea.product.backend.service.fs.IObjectStorage#set(T)
         */
        @Override
        public void set(final T _object) {
        
        
            res = _object;
            
        }
        
    }
    
    
    
    /**
     * @throws Exception
     * @throws java.lang.Exception
     */
    @Test
    public void testDAO() throws Exception {
    
    
        final ObjectStorageStub<DAOStorageIndex<MockID>> registerStorage =
                new ObjectStorageStub<DAOStorageIndex<MockID>>();
        final DAOObjectStorage<MockID> daoStorage = new DAOObjectStorage<MockID>(registerStorage);
        daoStorage.enableSaveOnChange();
        daoStorage.saveOrUpdate(new MockID("A"));
        final MockID bId = new MockID("B");
        daoStorage.saveOrUpdate(bId);
        final MockID cId = new MockID("C");
        daoStorage.saveOrUpdate(cId);
        daoStorage.saveOrUpdate(new MockID("D"));
        Assert.assertEquals(4, daoStorage.selectAll().size());
        daoStorage.delete(cId);
        Assert.assertEquals(3, daoStorage.selectAll().size());
        daoStorage.delete(bId);
        Assert.assertEquals(2, daoStorage.selectAll().size());
        Assert.assertEquals(1, daoStorage.find(new SearchFilter<MockID>()
        {
            
            
            @Override
            public boolean match(final MockID _object) {
            
            
                return "A".equals(_object.getStr());
            }
        }).size());
        daoStorage.deleteAll();
        Assert.assertEquals(0, daoStorage.selectAll().size());
        
    }
    
    
}
