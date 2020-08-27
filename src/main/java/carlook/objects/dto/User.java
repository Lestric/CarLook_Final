package carlook.objects.dto;

public class User {

    private static int id;
    private static String email;
    private static String passwort;
    private static String passwort2;
    private static String role;

    public static String getEmail() {
        return User.email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPasswort() {
        return User.passwort;
    }

    public static void setPasswort(String passwort) {
        User.passwort = passwort;
    }

    public static String getRole() {
        return User.role;
    }

    public static void setRole(String role) {
        User.role = role;
    }

    public static int getId() {
        return User.id;
    }

    public static void setId(int id) {
        User.id = id;
    }

    public static String getPasswort2() {
        return User.passwort2;
    }

    public static void setPasswort2(String passwort2) {
        User.passwort2 = passwort2;
    }


}
