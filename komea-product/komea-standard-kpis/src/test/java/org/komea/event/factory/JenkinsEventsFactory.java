
package org.komea.event.factory;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.enums.BuildIndustrialization;



public class JenkinsEventsFactory
{
    
    
    private static final String URL_JENKINS = "http://";
    
    
    
    public static EventSimpleDto createBuildBroken(
            final long start,
            final int buildNumber,
            final String jenkinsProjectName,
            final String providerUrl,
            final String user,
            final String projectKey,
            final String branch) {
    
    
        final String message =
                "User " + user + " may have broken the build for job " + jenkinsProjectName;
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        properties.put("person", user);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.BUILD_BROKEN.getEventKey());
        event.setMessage(message);
        event.setPerson(user);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(URL_JENKINS);
        event.setValue(start);
        return event;
    }
    
    
    public static EventSimpleDto createBuildFixed(
            final long start,
            final int buildNumber,
            final String jenkinsProjectName,
            final String providerUrl,
            final String user,
            final String projectKey,
            final String branch) {
    
    
        final String message =
                "User " + user + " may have fixed the build for job " + jenkinsProjectName;
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        properties.put("person", user);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.BUILD_FIXED.getEventKey());
        event.setMessage(message);
        event.setPerson(user);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(URL_JENKINS);
        event.setValue(start);
        return event;
    }
    
    
    public static EventSimpleDto createCodeChangedEvent(
            final long start,
            final int buildNumber,
            final String jenkinsProjectName,
            final String providerUrl,
            final int nbCommits,
            final String commiter,
            final String projectKey,
            final String branch) {
    
    
        final String message =
                "User "
                        + commiter + " did " + nbCommits + " comit(s) since last build of job "
                        + jenkinsProjectName;
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        properties.put("person", commiter);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.BUILD_CODE_CHANGED.getEventKey());
        event.setMessage(message);
        event.setPerson(commiter);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(URL_JENKINS);
        event.setValue(nbCommits);
        return event;
    }
    
    
    public static EventSimpleDto createIndustrializationEvent(
            final long start,
            final int buildNumber,
            final String jenkinsProjectName,
            final String providerUrl,
            final String projectKey,
            final String branch,
            final String industrialization) {
    
    
        final BuildIndustrialization indus = BuildIndustrialization.valueOf(industrialization);
        final String message =
                "Jenkins build industrialization for job "
                        + jenkinsProjectName + " is " + enumNameToDisplayName(indus.name());
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        properties.put("industrialization", industrialization);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.BUILD_INDUSTRIALIZATION.getEventKey());
        event.setMessage(message);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(URL_JENKINS);
        event.setValue(indus.ordinal());
        return event;
    }
    
    
    public static EventSimpleDto createJobConfigurationChanged(
            final String jenkinsProjectName,
            final String providerUrl,
            final String user,
            final String projectKey,
            final String branch) {
    
    
        final String message =
                "User " + user + " changed configuration of job " + jenkinsProjectName;
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("branch", branch);
        properties.put("person", user);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.JOB_CONFIGURATION_CHANGED.getEventKey());
        event.setMessage(message);
        event.setPerson(user);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(URL_JENKINS);
        event.setValue(new Date().getTime());
        return event;
    }
    
    
    public static EventSimpleDto createStartedByUser(
            final long start,
            final int buildNumber,
            final String jenkinsProjectName,
            final String providerUrl,
            final String user,
            final String projectKey,
            final String branch) {
    
    
        final String message = "User " + user + " started build for job " + jenkinsProjectName;
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        properties.put("person", user);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.BUILD_STARTED_BY_USER.getEventKey());
        event.setMessage(message);
        event.setPerson(user);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(URL_JENKINS);
        event.setValue(start);
        return event;
    }
    
    
    public static EventSimpleDto createStartEvent(
            final long start,
            final int buildNumber,
            final String jenkinsProjectName,
            final String providerUrl,
            final String projectKey,
            final String branch) {
    
    
        final String message = "Jenkins build started for job " + jenkinsProjectName;
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaComputerListener.BUILD_STARTED.getEventKey());
        event.setMessage(message);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(URL_JENKINS);
        event.setValue(start);
        return event;
    }
    
    
    public static EventSimpleDto createStringEvent(
            final long start,
            final long end,
            final int buildNumber,
            final String result,
            final String jenkinsProjectName,
            final String providerUrl,
            final String projectKey,
            final String branch) {
    
    
        final long duration = end - start;
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(end));
        properties.put("project", projectKey);
        properties.put("jenkinsProject", jenkinsProjectName);
        properties.put("buildNumber", String.valueOf(buildNumber));
        properties.put("branch", branch);
        properties.put("duration", String.valueOf(duration));
        properties.put("result", result.toString());
        final EventSimpleDto event = new EventSimpleDto();
        if ("ABORTED".equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_INTERRUPTED.getEventKey());
            event.setMessage("Jenkins build interrupted for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else if ("FAILURE".equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_FAILED.getEventKey());
            event.setMessage("Jenkins build failed for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else if ("SUCCESS".equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_COMPLETE.getEventKey());
            event.setMessage("Jenkins build performed for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else if ("UNSTABLE".equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_UNSTABLE.getEventKey());
            event.setMessage("Jenkins build is unstable for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else {
            return null;
        }
        event.setDate(new Date());
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(providerUrl);
        event.setUrl(URL_JENKINS);
        event.setValue(result.hashCode());
        return event;
    }
    
    
    public static String enumNameToDisplayName(final String enumName) {
    
    
        return enumName.charAt(0) + enumName.substring(1).replace("_", " ").toLowerCase();
    }
    
    
    /**
     * @param _projectName
     * @param _i
     * @param _branchName
     * @param _userName
     * @return
     */
    public static EventSimpleDto sendBuildBroken(
            final String _projectName,
            final int _i,
            final String _branchName,
            final String _userName) {
    
    
        return createBuildBroken(0, 1, _projectName, "http://", _userName, _projectName,
                _branchName);
    }
    
    
    /**
     * @param _projectName
     * @param _buildNumber
     * @param _branchName
     * @return
     */
    public static EventSimpleDto sendBuildComplete(
            final String _projectName,
            final int _buildNumber,
            final String _branchName) {
    
    
        return JenkinsEventsFactory.createStringEvent(0, 10, 1, "SUCCESS", _projectName, "http//",
                _projectName, _branchName);
        
    }
    
    
    /**
     * @param _projectName
     * @param _i
     * @param _branchName
     * @param _userName
     * @return
     */
    public static EventSimpleDto sendBuildComplete(
            final String _projectName,
            final int _i,
            final String _branchName,
            final String _userName) {
    
    
        return JenkinsEventsFactory.createStringEvent(0, 10, 1, "SUCCESS", _projectName, "http//",
                _projectName, _branchName);
    }
    
    
    /**
     * @param _string
     * @param _i
     * @param _string2
     * @param _string3
     * @return
     */
    public static EventSimpleDto sendBuildFixed(
            final String _projectName,
            final int _i,
            final String _branchName,
            final String _userName) {
    
    
        return JenkinsEventsFactory.createBuildFixed(new Date().getTime(), _i, _projectName
                + " Build", "http://", _userName, _projectName, _branchName);
    }
    
    
    /**
     * @param _projectName
     * @param time
     * @param _branchName
     * @return
     */
    public static EventSimpleDto sendBuildStarted(
            final String _projectName,
            final int _buildNumber,
            final String _branchName) {
    
    
        return JenkinsEventsFactory.createStartEvent(new Date().getTime(), _buildNumber,
                _projectName + " Build", "http://", _projectName, _branchName);
    }
    
    
    /**
     * @param _projectName
     * @param _i
     * @param _branchName
     * @param _userName
     * @return
     */
    public static EventSimpleDto sendBuildStarted(
            final String _projectName,
            final int _i,
            final String _branchName,
            final String _userName) {
    
    
        return JenkinsEventsFactory.createStartedByUser(new Date().getTime(), _i, _projectName
                + " Build", "http://", _userName, _projectName, _branchName);
    }
    
    
    private JenkinsEventsFactory() {
    
    
    }
}
