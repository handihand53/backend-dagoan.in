package in.dagoan.enums;

public enum UploadEnum {
    commentPhoto("commentPhoto"),
    userPhoto("userPhoto");

    private String uploadEnum;

    UploadEnum(String uploadEnum) {
        this.uploadEnum = uploadEnum;
    }

    public String getUploadEnum() {
        return uploadEnum;
    }
}
