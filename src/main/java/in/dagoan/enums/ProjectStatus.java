package in.dagoan.enums;


public enum ProjectStatus {

    STATUS_ON_GOING("ON_GOING"),
    STATUS_FINISHED("FINISHED");

    private String projectStatus;

    ProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

}