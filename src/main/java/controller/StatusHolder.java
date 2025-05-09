package controller;

public class StatusHolder { // bruges til at holde error messages
    private static String statusMessage;

    public static void setStatus(String s) {
        statusMessage = s;
    }

    public static String getStatus() {
        return statusMessage;        
    }
}
