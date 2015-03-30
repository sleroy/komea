package org.komea.connectors.cra.launch;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.kohsuke.args4j.Option;
import org.komea.connectors.cra.model.Activity;
import org.komea.connectors.sdk.main.IConnectorCommand;
import org.komea.connectors.sdk.std.impl.DateOptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CraPushCsvCommand implements IConnectorCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(CraPushCsvCommand.class.getName());
    private static final String FILE_ENCODING = "UTF-8";

    @Option(name = "-url", usage = "Url of Komea", required = true)
    private String komeaUrl;

    @Option(name = "-in", usage = "Path of the directory of Cras", required = true)
    private String craDirectoryPath;

    @Option(name = "-out", usage = "Path of the csv result file", required = false)
    private String resultFilePath = null;

    @Option(name = "-since", usage = "Date of beginning in format dd/MM/yyyy", required = false, handler = DateOptionHandler.class)
    private DateTime since;

    @Option(name = "-craSheetName", usage = "Name of sheet. Default value is 'CRA'", required = false)
    private String craSheetName = "CRA";

    @Option(name = "-firstDataRowIndex", usage = "Index of the first data row. Default value is 5", required = false)
    private int firstDataRowIndex = 5;

    @Option(name = "-firstDataColumnIndex", usage = "Index of the first data column. Default value is 3", required = false)
    private int firstDataColumnIndex = 3;

    @Option(name = "-projectColumnIndex", usage = "Index of the project column. Default value is 0", required = false)
    private int projectColumnIndex = 0;

    @Option(name = "-activityColumnIndex", usage = "Index of the activity column. Default value is 1", required = false)
    private int activityColumnIndex = 1;

    @Option(name = "-dateRowIndex", usage = "Index of the date row. Default value is 5", required = false)
    private int dateRowIndex = 5;

    @Option(name = "-emailRowIndex", usage = "Index of the email row. Default value is 3", required = false)
    private int emailRowIndex = 3;

    @Option(name = "-emailColumnIndex", usage = "Index of the email column. Default value is 1", required = false)
    private int emailColumnIndex = 1;

    @Option(name = "-ignoreUnknownFields", usage = "Ignore activities and projects that are not in Fields page. Default value is false", required = false)
    private boolean ignoreUnknownFields = false;

    @Override
    public String action() {
        return "push";
    }

    @Override
    public String description() {
        return "Push Cra csv to Komea";
    }

    @Override
    public void init() throws Exception {
        // do nothing
    }

    @Override
    public void run() throws Exception {
        final List<Activity> activities = getActivities();
        if (activities.isEmpty()) {
            LOGGER.error("No activities was detected.");
            return;
        }
        LOGGER.info(activities.size() + " activities detected");
        final String data = Activity.toCsv(activities);
        if (resultFilePath != null) {
            writeCsvResults(data);
        }
        sendResultsToKomea(data);
    }

    private List<Activity> getActivities() throws Exception {
        final List<Activity> activities = Lists.newArrayList();
        final Map<File, Date[]> directories = getDirectories();
        for (final File directory : directories.keySet()) {
            for (final File file : directory.listFiles()) {
                final String fileName = file.getName();
                if (!file.isFile() || !fileName.endsWith(".xls")
                        || fileName.contains("old") || fileName.contains("Copie")) {
                    LOGGER.info("Exclude file " + file.getAbsolutePath());
                    continue;
                }
                final Date[] dates = directories.get(directory);
                final List<Activity> fileActivities = processFile(file, dates[0], dates[1]);
                activities.addAll(fileActivities);
            }
        }
        return activities;
    }

    private Map<File, Date[]> getDirectories() {
        final Map<File, Date[]> directories = Maps.newHashMap();
        for (final File directory : new File(craDirectoryPath).listFiles()) {
            final String dirName = directory.getName();
            if (!directory.isDirectory() || !directory.getName().matches("cra20\\d\\ds[1-2]")) {
                LOGGER.info("Exclude file " + directory.getAbsolutePath());
                continue;
            }
            final int semester = Integer.valueOf(dirName.substring(8));
            final int year = Integer.valueOf(dirName.substring(3, 7));
            final DateTime from = new DateTime(year, semester == 1 ? 1 : 7, 1, 0, 0);
            final DateTime to = new DateTime(semester == 1 ? year : year + 1, semester == 1 ? 7 : 1, 1, 0, 0);
            if (since != null && since.isAfter(from)) {
                LOGGER.info("Exclude file " + directory.getAbsolutePath());
                continue;
            }
            directories.put(directory, new Date[]{from.toDate(), to.toDate()});
        }
        return directories;
    }

    private List<Activity> processFile(final File file, final Date from,
            final Date to) throws Exception {
        LOGGER.info("Process file " + file.getAbsolutePath());
        final List<Activity> activities = Lists.newArrayList();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            final Workbook workbook = new HSSFWorkbook(fis);
            final Sheet sheet = workbook.getSheet(craSheetName);
            final String email = getEmail(sheet);
            final List<String> projectCodes = getProjectCodes(workbook);
            final List<String> activityCodes = getActivityCodes(workbook);
            final Map<Integer, Date> dates = getDates(sheet, from, to);
            for (int i = firstDataRowIndex; i < sheet.getLastRowNum(); i++) {
                final Row row = sheet.getRow(i);
                fillRowActivities(email, dates, row, activities, projectCodes, activityCodes);
            }
        } catch (Exception ex) {
            LOGGER.error("Could not process file " + file.getAbsolutePath(), ex);
        } finally {
            IOUtils.closeQuietly(fis);
        }
        return activities;
    }

    private String getEmail(final Sheet sheet) {
        final Row rowEmail = sheet.getRow(emailRowIndex);
        final Cell cellEmail = rowEmail.getCell(emailColumnIndex);
        return cellEmail.getStringCellValue();
    }

    private List<String> getProjectCodes(final Workbook workbook) {
        return getFields(workbook, "PROJECT");
    }

    private List<String> getActivityCodes(final Workbook workbook) {
        return getFields(workbook, "ACTIVITY");
    }

    private List<String> getFields(final Workbook workbook, final String fieldType) {
        final List<String> fields = Lists.newArrayList();
        if (!ignoreUnknownFields) {
            return fields;
        }
        final Sheet sheet = workbook.getSheet("Fields");
        boolean add = false;
        final String begin = "B" + fieldType;
        final String end = "E" + fieldType;
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            final Row row = sheet.getRow(i);
            final Cell cell = row.getCell(0);
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                final String field = cell.getStringCellValue();
                if (end.equals(field)) {
                    break;
                } else if (begin.equals(field)) {
                    add = true;
                } else if (add) {
                    fields.add(field);
                }
            }
        }
        return fields;
    }

    private Map<Integer, Date> getDates(final Sheet sheet,
            final Date from, final Date to) {
        final Map<Integer, Date> dates = Maps.newHashMap();
        final Row row = sheet.getRow(dateRowIndex);
        for (int i = firstDataColumnIndex; i < row.getLastCellNum(); i++) {
            final Date date = row.getCell(i).getDateCellValue();
            if (!date.before(from) && date.before(to)) {
                dates.put(i, date);
            }
        }
        return dates;
    }

    private void fillRowActivities(final String email, final Map<Integer, Date> dates,
            final Row row, final List<Activity> activities, final List<String> projectCodes,
            final List<String> activityCodes) throws Exception {
        if (row == null) {
            return;
        }
        final Cell projectCell = row.getCell(projectColumnIndex);
        final Cell activityCell = row.getCell(activityColumnIndex);
        if (projectCell == null || activityCell == null) {
            return;
        }
        String projectCode = projectCell.getStringCellValue();
        if ("non-applicable".equals(projectCode) || "projet non attribué".equals(projectCode)
                || (!projectCodes.isEmpty() && !projectCodes.contains(projectCode))) {
            projectCode = "";
        }
        final String activityName = activityCell.getStringCellValue();
        if (!"Absence".equals(activityName)
                && (activityCodes.isEmpty() || activityCodes.contains(activityName))) {
            for (int j = firstDataColumnIndex; j < row.getLastCellNum(); j++) {
                final Cell cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }
                final int cellType = cell.getCellType();
                if (cellType == Cell.CELL_TYPE_NUMERIC) {
                    final Double value = cell.getNumericCellValue();
                    final Date date = dates.get(j);
                    if (value == 0d || date == null) {
                        continue;
                    }
                    final Activity activity = new Activity(email, projectCode, activityName, date, value);
                    activities.add(activity);
                }
            }
        }
    }

    private void writeCsvResults(final String data) throws IOException {
        final File file = new File(resultFilePath);
        LOGGER.info("Write CSV results to " + file.getAbsolutePath());
        FileUtils.write(file, data, FILE_ENCODING);
    }

    private void sendResultsToKomea(final String data) throws IOException {
        final String url = komeaUrl + "/rest/cra/update";
        LOGGER.info("Send CSV results to " + url);
        final URL obj = new URL(url);
        final HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
        con.setDoOutput(true);
        final DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(data.getBytes(FILE_ENCODING));
        wr.flush();
        wr.close();
        final int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            LOGGER.error("Error " + responseCode + " : " + con.getResponseMessage());
        }
    }

    public String getKomeaUrl() {
        return komeaUrl;
    }

    public void setKomeaUrl(String komeaUrl) {
        this.komeaUrl = komeaUrl;
    }

    public String getCraDirectoryPath() {
        return craDirectoryPath;
    }

    public void setCraDirectoryPath(String craDirectoryPath) {
        this.craDirectoryPath = craDirectoryPath;
    }

    public String getResultFilePath() {
        return resultFilePath;
    }

    public void setResultFilePath(String resultFilePath) {
        this.resultFilePath = resultFilePath;
    }

    public String getCraSheetName() {
        return craSheetName;
    }

    public void setCraSheetName(String craSheetName) {
        this.craSheetName = craSheetName;
    }

    public int getFirstDataRowIndex() {
        return firstDataRowIndex;
    }

    public void setFirstDataRowIndex(int firstDataRowIndex) {
        this.firstDataRowIndex = firstDataRowIndex;
    }

    public int getFirstDataColumnIndex() {
        return firstDataColumnIndex;
    }

    public void setFirstDataColumnIndex(int firstDataColumnIndex) {
        this.firstDataColumnIndex = firstDataColumnIndex;
    }

    public int getProjectColumnIndex() {
        return projectColumnIndex;
    }

    public void setProjectColumnIndex(int projectColumnIndex) {
        this.projectColumnIndex = projectColumnIndex;
    }

    public int getActivityColumnIndex() {
        return activityColumnIndex;
    }

    public void setActivityColumnIndex(int activityColumnIndex) {
        this.activityColumnIndex = activityColumnIndex;
    }

    public int getDateRowIndex() {
        return dateRowIndex;
    }

    public void setDateRowIndex(int dateRowIndex) {
        this.dateRowIndex = dateRowIndex;
    }

    public int getEmailRowIndex() {
        return emailRowIndex;
    }

    public void setEmailRowIndex(int emailRowIndex) {
        this.emailRowIndex = emailRowIndex;
    }

    public int getEmailColumnIndex() {
        return emailColumnIndex;
    }

    public void setEmailColumnIndex(int emailColumnIndex) {
        this.emailColumnIndex = emailColumnIndex;
    }

    public DateTime getSince() {
        return since;
    }

    public void setSince(DateTime since) {
        this.since = since;
    }

    public boolean isIgnoreUnknownFields() {
        return ignoreUnknownFields;
    }

    public void setIgnoreUnknownFields(boolean ignoreUnknownFields) {
        this.ignoreUnknownFields = ignoreUnknownFields;
    }

}
