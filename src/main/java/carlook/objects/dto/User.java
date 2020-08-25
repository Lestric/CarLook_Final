package carlook.objects.dto;

public class User {

    private int id;
    private String email;
    private String passwort;
    private String passwort2;
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasswort2() {
        return passwort2;
    }

    public void setPasswort2(String passwort2) {
        this.passwort2 = passwort2;
    }


}
