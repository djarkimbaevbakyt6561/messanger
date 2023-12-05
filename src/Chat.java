import java.util.Arrays;

public class Chat {
    String recipientUserName;
    String senderUserName;
    String recipientUserEmail;
    String[] messages =new String[0];
    public void addMessage(String message, String userName) {
        String[] newArray = Arrays.copyOf(messages, messages.length + 1);
        newArray[newArray.length - 1] = userName.concat(": "  + message);
        messages = newArray;
    }

    @Override
    public String toString() {
        return "Чат с " + recipientUserName;
    }
}

