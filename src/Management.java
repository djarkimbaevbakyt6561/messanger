import java.util.Arrays;

public class Management {
    User[] users = new User[]{new User("Bakyt", "Djarkimbaev", "king@gmail.com", "kingpro", "0707109090", "Shevchenko 45"), new User("Daniar", "Djarkimbaev", "daniar@gmail.com", "kingpro", "0707109090", "Shevchenko 45")};

    public void addUser(User registerUser) {
        User[] newArray = Arrays.copyOf(users, users.length + 1);
        newArray[newArray.length - 1] = registerUser;
        users = newArray;
    }
}
