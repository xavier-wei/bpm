package tw.gov.pcc.domain;

public enum IsmsFullNameEnum {
    L410("L410","L410-共用系統使用者帳號申請單"),
    L414("L414", "L414-網路服務連結申請單");
    private final String simpleName;
    private final String fullName;

    IsmsFullNameEnum(String simpleName, String fullName) {
        this.simpleName = simpleName;
        this.fullName = fullName;
    }

    public static String getFullNameBySimpleName(String simpleName) {
        for (IsmsFullNameEnum ismsFullNameEnum : IsmsFullNameEnum.values()) {
            if (ismsFullNameEnum.simpleName.equals(simpleName)) {
                return ismsFullNameEnum.fullName;
            }
        }
        return null;
    }
}
