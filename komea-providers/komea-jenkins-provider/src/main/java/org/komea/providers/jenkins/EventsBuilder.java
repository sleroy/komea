
package org.komea.providers.jenkins;



import hudson.model.Result;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.enums.BuildIndustrialization;



public abstract class EventsBuilder
{
    
    
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
        event.setUrl(KomeaComputerListener.getProjectBuildUrl(jenkinsProjectName, buildNumber));
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
        event.setUrl(KomeaComputerListener.getProjectBuildUrl(jenkinsProjectName, buildNumber));
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
        event.setUrl(KomeaComputerListener.getProjectBuildUrl(jenkinsProjectName, buildNumber));
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
        event.setUrl(KomeaComputerListener.getProjectBuildUrl(jenkinsProjectName, buildNumber));
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
        event.setUrl(KomeaComputerListener.getProjectConfigurationUrl(jenkinsProjectName));
        event.setValue(new Date().getTime());
        return event;
    }
    
    
    public static EventSimpleDto createResultEvent(
            final long start,
            final long end,
            final int buildNumber,
            final Result result,
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
        if (Result.ABORTED.equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_INTERRUPTED.getEventKey());
            event.setMessage("Jenkins build interrupted for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else if (Result.FAILURE.equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_FAILED.getEventKey());
            event.setMessage("Jenkins build failed for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else if (Result.SUCCESS.equals(result)) {
            event.setEventType(KomeaComputerListener.BUILD_COMPLETE.getEventKey());
            event.setMessage("Jenkins build performed for the project "
                    + jenkinsProjectName + " in " + duration + "ms.");
        } else if (Result.UNSTABLE.equals(result)) {
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
        event.setUrl(KomeaComputerListener.getProjectBuildUrl(jenkinsProjectName, buildNumber));
        event.setValue(result.ordinal);
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
        event.setUrl(KomeaComputerListener.getProjectBuildUrl(jenkinsProjectName, buildNumber));
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
        event.setUrl(KomeaComputerListener.getProjectBuildUrl(jenkinsProjectName, buildNumber));
        event.setValue(start);
        return event;
    }
    
    
    public static String enumNameToDisplayName(final String enumName) {
    
    
        return enumName.charAt(0) + enumName.substring(1).replace("_", " ").toLowerCase();
    }
    
    
    private EventsBuilder() {
    
    
    }
}
