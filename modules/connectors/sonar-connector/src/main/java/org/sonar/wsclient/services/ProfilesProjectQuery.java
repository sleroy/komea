package org.sonar.wsclient.services;

public final class ProfilesProjectQuery extends Query<Profile> {

    public static final String BASE_URL = "/api/profiles/list";

    public static ProfilesProjectQuery createWithProject(String project) {
        return new ProfilesProjectQuery(project);
    }

    private final String project;

    private ProfilesProjectQuery(String project) {
        this.project = project;
    }

    public String getProject() {
        return project;
    }

    @Override
    public Class<Profile> getModelClass() {
        return Profile.class;
    }

    @Override
    public String getUrl() {
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append('?');
        appendUrlParameter(url, "project", project);
        return url.toString();
    }

}
