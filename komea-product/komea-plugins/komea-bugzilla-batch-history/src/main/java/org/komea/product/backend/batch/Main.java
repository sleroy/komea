
package org.komea.product.backend.batch;



import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.MeasureCriteria;
import org.springframework.context.support.FileSystemXmlApplicationContext;



public class Main
{


    public static void main(final String[] args) {
    
    
        final FileSystemXmlApplicationContext ctx =
                new FileSystemXmlApplicationContext("configuration/spring/application-context.xml");
        final MeasureDao bean = ctx.getBean(MeasureDao.class);
        final int count = bean.countByCriteria(new MeasureCriteria());
        System.out.println(count);
        
    }
}
