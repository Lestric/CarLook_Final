package carlook.services.util;

import java.util.regex.Pattern;

public class RegistrationResult {

    public enum FailureType{
        VORNAME_MISSING,
        NACHNAME_MISSING,
        EMAIL_MISSING, EMAIL_NOT_VALID,
        PASSWORD_MISSING, PASSWORD_TOO_SHORT, PASSWORDS_DONT_MATCH,
        EVERYTHING_MISSING, EMAIL_EXISTS
    }

    private FailureType failureType;
    private boolean result;

    public void setReason(FailureType ft){
        this.failureType = ft;
    }
    public void setResult(boolean res){
        this.result = res;
    }

    public String error(){
        if(!result){
            if(failureType == FailureType.EMAIL_NOT_VALID) return "Ungültige Email-Adresse!";
            if(failureType == FailureType.PASSWORDS_DONT_MATCH) return "Die eingegebenen Passwörter stimmen nicht überein.";
            if(failureType == FailureType.EVERYTHING_MISSING) return "Bitte alle Felder ausüllen!";
            if(failureType == FailureType.PASSWORD_TOO_SHORT) return "Passwort zu kurz! Mind. 8 Zeichen notwendig!";
            if(failureType == FailureType.NACHNAME_MISSING) return "Bitte geben sie ihren Nachnamen ein!";
            if(failureType == FailureType.PASSWORD_MISSING) return "Bitte geben sie ein Passwort ein!";
            if(failureType == FailureType.VORNAME_MISSING) return "Bitte geben sie ihren Vorname ein!";
            if(failureType == FailureType.EMAIL_EXISTS) return "Email existiert bereits!";
            if(failureType == FailureType.EMAIL_MISSING) return "Bitte geben sie ihre Email-Adresse ein!";
        }
        return null;
    }


    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public FailureType getFailureType(){
        return this.failureType;
    }

    public boolean getResult(){
        return this.result;
    }
}
