package in.dagoan;

public class ApiPath {
    // SOME FIXED VARIABLES
    private static final String API = "/api";

    // AUTH ENDPOINTS
    public static final String AUTH = API + "/auth";
    public static final String REGISTER = AUTH + "/user";
    public static final String LOGIN = AUTH + "/login";

    // PROJECT ENDPOINTS
    public static final String PROJECT = API + "/projects";
    public static final String PROJECT_DETAIL = PROJECT + "/detail";
    public static final String PROJECT_UPDATE = PROJECT + "/update";
    public static final String PROJECT_DELETE = PROJECT + "/delete";

    // MAIN PROJECT ENDPOINTS
}
