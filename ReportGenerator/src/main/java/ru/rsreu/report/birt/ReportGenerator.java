package ru.rsreu.report.birt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;


@Component
public class ReportGenerator {

    private IReportEngine reportEngine;
    public DataSource dataSource;

    public ReportGenerator (DataSource parDataSource) throws BirtException {
        EngineConfig config = new EngineConfig();
        Platform.startup(config);
        IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        reportEngine = factory.createReportEngine(config);
        dataSource = parDataSource;
    }

    public void shutdownReportEngine() {
        if (reportEngine != null) {
            reportEngine.destroy();
            Platform.shutdown();
        }
    }

    public void generateReport(Integer userId, Integer courseId, HttpServletResponse response, HttpServletRequest request) throws EngineException, IOException, SQLException {
        IReportRunnable reportDesign = reportEngine.openReportDesign("UserStatistics/user_answers_statistic_for_course.rptdesign"); // classpath in the result jar

        IRunAndRenderTask task = reportEngine.createRunAndRenderTask(reportDesign);

        response.setContentType(reportEngine.getMIMEType("pdf"));

        PDFRenderOption pdfRenderOption = new PDFRenderOption();
        pdfRenderOption.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);

        task.setRenderOption(pdfRenderOption);
        pdfRenderOption.setOutputStream(response.getOutputStream());
        task.getAppContext().put("OdaJDBCDriverPassInConnection", dataSource.getConnection());
        task.setParameter("UserId", userId, "");
        task.setParameter("CourseId", courseId, "");
        task.run();
    }
}
