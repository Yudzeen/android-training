package ebj.yujinkun.notificationhistory;

public class NotifHistory {

    private final String appOrigin;
    private final String title;
    private final String description;

    public NotifHistory(String appOrigin, String title, String description) {
        this.appOrigin = appOrigin;
        this.title = title;
        this.description = description;
    }

    public String getAppOrigin() {
        return appOrigin;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "NotifHistory{" +
                "appOrigin='" + appOrigin + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
