/**
 * 
 */

package org.komea.product.backend.genericservice;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.komea.eventory.utils.ClassUtils;
import org.komea.product.backend.service.alert.AlertTypeService;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.model.KpiAlertType;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;



/**
 * @author sleroy
 */
public class AbstractServiceTest
{
    
    
    public class GenericServiceTester<TEntity extends IHasKey, TCriteria>
    {
        
        
        /**
         * @author sleroy
         */
        private final class DeleteByPrimaryKeyAnswer implements Answer<Integer>
        {
            
            
            @Override
            public Integer answer(final InvocationOnMock _invocation) throws Throwable {
            
            
                final boolean remove = entities.remove(_invocation.getArguments()[0]);
                return remove ? 1 : 0;
            }
        }
        
        
        
        /**
         * @author sleroy
         */
        private final class InsertAnswer implements Answer<Integer>
        {
            
            
            @Override
            public Integer answer(final InvocationOnMock _invocation) throws Throwable {
            
            
                final TEntity entity = (TEntity) _invocation.getArguments()[0];
                entities.add(entity);
                ((IHasKey) entity).setId(entities.size());
                
                
                return entities.size();
            }
        }
        
        
        
        /**
         * @author sleroy
         */
        private final class SelectByPrimaryKeyAnswer implements Answer<TEntity>
        {
            
            
            @Override
            public TEntity answer(final InvocationOnMock _invocation) throws Throwable {
            
            
                return entities.get((Integer) _invocation.getArguments()[0]);
                
            }
        }
        
        
        
        /**
         * @author sleroy
         */
        private final class UpdateByPrimaryKeyAnswer implements Answer<TEntity>
        {
            
            
            @Override
            public TEntity answer(final InvocationOnMock _invocation) throws Throwable {
            
            
                final IHasKey iHasKey = (IHasKey) _invocation.getArguments()[0];
                final TEntity tEntity = (TEntity) _invocation.getArguments()[0];
                return entities.set(iHasKey.getId(), tEntity);
                
            }
        }
        
        
        
        private final List<TEntity>                                entities   =
                                                                                      new ArrayList<TEntity>();
        private final IGenericDAO<IHasKey, Integer, TCriteria>     genericDAO =
                                                                                      mock(IGenericDAO.class);
        private final AbstractService<TEntity, Integer, TCriteria> genericService;
        
        
        private final Class<?>                                     pojo;
        
        
        
        public GenericServiceTester(
                final AbstractService<TEntity, Integer, TCriteria> _genericService,
                final Class<?> _pojo) {
        
        
            super();
            genericService = new AbstractService()
            {
                
                
                @Override
                public IGenericDAO getRequiredDAO() {
                
                
                    return genericDAO;
                }
                
                
                @Override
                protected Object createKeyCriteria(final String _key) {
                
                
                    return null;
                }
            };
            
            pojo = _pojo;
            
            // when(genericService.getRequiredDAO()).thenReturn((IGenericDAO) genericDAO);
            
            initDAO(genericDAO);
        }
        
        
        public void testInsertion() {
        
        
            final TEntity entity = (TEntity) ClassUtils.instantiate(pojo);
            genericService.insert(entity);
            assertTrue(entity.getId() == 1);
            
            final TEntity entity2 = (TEntity) ClassUtils.instantiate(pojo);
            genericService.insert(entity2);
            assertTrue(entity2.getId() == 2);
            
            assertEquals(2, genericService.selectAll().size());
            assertEquals(entity, genericService.selectAll().get(0));
            assertEquals(entity2, genericService.selectAll().get(1));
            assertEquals(entity, genericDAO.selectByPrimaryKey(0));
            assertEquals(entity2, genericDAO.selectByPrimaryKey(1));
            
            
        }
        
        
        /**
         * @param _genericDAO
         */
        @SuppressWarnings("boxing")
        private void initDAO(final IGenericDAO<IHasKey, Integer, TCriteria> _genericDAO) {
        
        
            when(_genericDAO.selectByCriteria(null)).thenReturn((List) entities);
            // when(_genericDAO.selectByCriteriaWithRowbounds(any(Object.class), any(RowBounds.class)))
            // .thenReturn((List) entities);
            when(_genericDAO.insert(Matchers.any(IHasKey.class))).then(new InsertAnswer());
            when(_genericDAO.insertSelective(Matchers.any(IHasKey.class))).then(new InsertAnswer());
            when(_genericDAO.deleteByPrimaryKey(Matchers.anyInt())).then(
                    new DeleteByPrimaryKeyAnswer());
            when(_genericDAO.selectByPrimaryKey(Matchers.anyInt())).then(
                    new SelectByPrimaryKeyAnswer());
            when(_genericDAO.updateByPrimaryKey(Matchers.any(IHasKey.class))).then(
                    new UpdateByPrimaryKeyAnswer());
            when(_genericDAO.updateByPrimaryKeySelective(Matchers.any(IHasKey.class))).then(
                    new UpdateByPrimaryKeyAnswer());
            // when(_genericDAO.updateByPrimaryKey(Matchers.any(IHasKey.class))).then(
            // new UpdateByPrimaryKeyAnswer());
        }
    }
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.genericservice.AbstractService#AbstractService()}.
     */
    @Test
    public final void testAbstractService() throws Exception {
    
    
        final GenericServiceTester serviceTester =
                new GenericServiceTester(new AlertTypeService(), KpiAlertType.class);
        serviceTester.testInsertion();
    }
    
}
