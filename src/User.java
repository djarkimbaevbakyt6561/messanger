import java.util.Arrays;
import java.util.Scanner;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    User[] friends = new User[0];
    Chat[] chats = new Chat[0];
    User[] friendRequests = new User[0];

    public void addFriendRequest(User user) {
        User[] newArray = Arrays.copyOf(friendRequests, friendRequests.length + 1);
        newArray[newArray.length - 1] = user;
        friendRequests = newArray;
    }

    public User(String firstName, String lastName, String email, String password, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public boolean displayMessages() {
        if (chats.length == 0) {
            System.out.println("У вас нет сообщений.");
            return false;
        } else {
            System.out.println("Все сообщения:");
            int index = 1;
            for (Chat chat : chats) {
                System.out.println(index++ + ". " + chat);
            }
        }
        return true;
    }

    public boolean displayFriends() {
        if (friends.length == 0) {
            System.out.println("У вас нет друзей.");
            return false;
        } else {
            System.out.println("Все друзья:");
            int index = 1;
            for (User user : friends) {
                System.out.println(index++ + ". " + user);
            }
        }
        return true;
    }

    public boolean displayFriendRequests() {
        if (friendRequests.length == 0) {
            System.out.println("У вас нет запросов в друзья.");
            return false;
        } else {
            System.out.println("Запросы в друзья:");
            int index = 1;
            for (User user : friendRequests) {
                System.out.println(index++ + ". " + user);
            }
        }
        return true;
    }

    public void addFriend(User user) {
        User[] newArray = Arrays.copyOf(friends, friends.length + 1);
        newArray[newArray.length - 1] = user;
        friends = newArray;
    }

    public void addChat(Chat chat) {
        Chat[] newArray = Arrays.copyOf(chats, chats.length + 1);
        newArray[newArray.length - 1] = chat;
        chats = newArray;
    }

    public void deleteFriendRequests(String email) {
        User[] newFriendRequests = new User[100];
        int newIndex = 0;

        for (User user : friendRequests) {
            if (user != null && !(user.getEmail().equals(email))) {
                newFriendRequests[newIndex++] = user;
            }
        }

        friendRequests = Arrays.copyOf(newFriendRequests, friendRequests.length - 1);
    }

    public static User register(Management management) {
        User newUser = new User();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя: ");
        newUser.setFirstName(scanner.nextLine());
        System.out.print("Фамилия: ");
        newUser.setLastName(scanner.nextLine());
        System.out.print("Введите номер телефона: ");
        while (true) {
            String num = scanner.nextLine();
            if (num.length() == 10 && num.matches("\\d+")) {
                newUser.setPhoneNumber(num);
                break;
            } else {
                System.out.println("Введите правильный номер телефона!");
            }

        }
        System.out.print("Введите домашний адрес: ");
        newUser.setAddress(scanner.nextLine());
        System.out.print("Введите email: ");
        newUser.setEmail(scanner.nextLine());
        boolean foundUserEmail = false;
        for (User user : management.users) {
            if (user.getEmail().equals(newUser.getEmail())) {
                foundUserEmail = true;
                break;
            }
        }
        if (newUser.getFirstName().isEmpty() || newUser.getLastName().isEmpty() || newUser.getEmail().isEmpty()) {
            System.out.println("Поля не должны быть пустыми!❌");
        } else if (!newUser.getEmail().contains("@gmail.com")) {
            System.out.println("Не корректый адрес эл.почты!❌");
        } else if (foundUserEmail) {
            System.out.println("Пользователь с таким email уже существует!❌");
        } else {
            System.out.print("Введите пароль: ");
            newUser.setPassword(scanner.nextLine());
            if (newUser.getPassword().length() < 4) {
                System.out.println("Пароль долден быть не менее 4 символа!❌");
                return null;

            } else {
                System.out.println("Аккаунт успешно создан!✅");
                return newUser;
            }
        }
        return null;


    }

    public static User login(User[] users) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        for (User user : users) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                System.out.println("Вы успешно вошли в аккаунт!✅");
                return user;
            }
        }
        System.out.println("Не верный пароль или логин❌");
        return null;

    }

    @Override
    public String toString() {
        return "Пользователь " + firstName + " " + lastName +
                ", почта: " + email +
                ", номер телефона: " + phoneNumber;
    }
}
