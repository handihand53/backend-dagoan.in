package in.dagoan;

import in.dagoan.entity.document.Member;

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
    public static final String KANBAN = API + "/kanbans";
    public static final String KANBAN_POST = KANBAN + "/add";
    public static final String KANBAN_UPDATE = KANBAN + "/update";
    public static final String KANBAN_DELETE = KANBAN + "/delete";

    // TASK LIST ENDPOINTS
    public static final String TASKLIST = API + "/taskList";
    public static final String TASKLIST_POST = TASKLIST + "/add";
    public static final String TASKLIST_UPDATE = TASKLIST + "/update";
    public static final String TASKLIST_UPDATE_SECTION = TASKLIST + "/update-section";
    public static final String TASKLIST_DELETE = TASKLIST + "/delete";

    // LABEL ENDPOINTS
    public static final String LABEL = API + "/label";
    public static final String LABEL_POST = LABEL + "/add";
    public static final String LABEL_UPDATE = LABEL + "/update";
    public static final String LABEL_DELETE = LABEL + "/delete";

    // MEMBER ENDPOINTS
    public static final String MEMBER = API + "/member";
    public static final String MEMBER_POST = MEMBER + "/add";
    public static final String MEMBER_DELETE = MEMBER + "/delete";

    // COMMENT ENDPOINTS
    public static final String COMMENT = API + "/comment";
    public static final String COMMENT_POST = COMMENT + "/add";
    public static final String COMMENT_PUT = COMMENT + "/put";
    public static final String COMMENT_DELETE = COMMENT + "/delete";
}
