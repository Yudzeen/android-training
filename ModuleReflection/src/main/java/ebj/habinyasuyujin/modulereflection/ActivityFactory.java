package ebj.habinyasuyujin.modulereflection;

public class ActivityFactory {

    public static final String MY_LIBRARY_PACKAGE_NAME = "com.example.modulereflection.mylibrary";
    public static final String MY_LIBRARY_ACTIVITY_CLASS_NAME = "com.example.modulereflection.mylibrary.MyLibraryActivity";

    public static Class<?> getMyLibraryActivityClass() {
        return classForName(MY_LIBRARY_ACTIVITY_CLASS_NAME);
    }

    private static Class<?> classForName(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}