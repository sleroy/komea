
package org.komea.product.backend.batch;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.service.cron.CronRegistryService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.kpi.IKpiImportationService;
import org.komea.product.database.dao.BugzillaDao;
import org.komea.product.database.dto.ProjectDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;



public class Main
{


    private static final String BATCH_CONF                                   = "batch-conf.xml";

    private static final int    CONF_FOLDER                                  = 0;

    private static final String CONFIGURATION_SPRING_APPLICATION_CONTEXT_XML =
            "configuration/spring/application-context.xml";

    private static final Logger LOGGER                                       =
            LoggerFactory
            .getLogger(Main.class);


    private static final int    NUMBER_ARGS                                  = 1;



    public static void main(final String[] args) throws IOException {


        // ARG0 = ProjectName
        if (args.length != NUMBER_ARGS) {
            System.err.println("Configuration folder argument is required.");
            return;
        }
        final File configFolder = new File(args[CONF_FOLDER]).getAbsoluteFile();
        LOGGER.info("Configuration folder : {}", configFolder);
        
        InputStream inputStream = null;
        SqlSession openSession = null;
        FileSystemXmlApplicationContext fileSystemXmlApplicationContext = null;
        try {
            inputStream = FileUtils.openInputStream(new File(configFolder, BATCH_CONF));
            LOGGER.info("Loading mybatis conf");
            final SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
            LOGGER.info("Opening database session");
            openSession = build.openSession();
            final BugzillaDao mapper = openSession.getMapper(BugzillaDao.class);
            final List<ProjectDto> projects = mapper.getProjects();
            LOGGER.info("List of projects {}", projects);
            System.out.println(projects);
            
            LOGGER.info("Opening Spring session");
            fileSystemXmlApplicationContext =
                    new FileSystemXmlApplicationContext(
                            CONFIGURATION_SPRING_APPLICATION_CONTEXT_XML);
            final IKpiImportationService kpiImportationService =
                    fileSystemXmlApplicationContext.getBean(IKpiImportationService.class);
            LOGGER.info("Force importation of kpis if necessary");
            kpiImportationService.importFolder(configFolder);
            final ISpringService springService =
                    fileSystemXmlApplicationContext.getBean(ISpringService.class);


            for (final ProjectDto project : projects) {

                launchRebuilding(project.getName(), openSession, fileSystemXmlApplicationContext,
                        springService, mapper);
            }
        } finally {
            IOUtils.closeQuietly(fileSystemXmlApplicationContext);
            IOUtils.closeQuietly(openSession);
            IOUtils.closeQuietly(inputStream);
        }
        ;

    }


    private static void launchRebuilding(
            final String PROJECT_NAME,
            final SqlSession openSession,
            final FileSystemXmlApplicationContext fileSystemXmlApplicationContext,
            final ISpringService springService,
            final BugzillaDao _mapper) {
    
    
        LOGGER.info("------------------>>>>>>>>>>>>>>>> Treating the project {}", PROJECT_NAME);
        
        final IRebuildHistoryService rebuildHistoryService =
                new RebuildHistoryService(PROJECT_NAME, _mapper);
        springService.autowirePojo(rebuildHistoryService);
        rebuildHistoryService.setMyBatis(openSession);
        final ICronRegistryService bean =
                fileSystemXmlApplicationContext.getBean(ICronRegistryService.class);
        ((CronRegistryService) bean).destroy();


        rebuildHistoryService.run();
    }
}
