import java.util.Scanner;

public class Main {

    private static final Management management = new Management();
    private static User currentUser = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        boolean isLoggedIn = false;
        boolean exitUser = false;
        while (!exit) {
            while (!exitUser) {
                System.out.println("""
                        1. Регистрация
                        2. Вход
                        3. Выход""");
                System.out.print("Выберите команду: ");
                String num = scanner.nextLine();

                switch (num) {
                    case "1" -> {
                        User registerUser = User.register(management);
                        if (registerUser != null) {
                            management.addUser(registerUser);
                        }
                    }
                    case "2" -> {
                        currentUser = User.login(management.users);
                        if (currentUser != null) {
                            isLoggedIn = true;
                            exitUser = true;
                        }
                    }
                    case "3" -> {
                        System.out.println("Вы вышли из программы!✅");
                        exitUser = true;
                        exit = true;

                    }
                }
            }
            if (isLoggedIn) {
                System.out.println("""
                        1.Все пользователи
                        2.Мои сообщения
                        3.Друзья
                        4.Запросы в друзья
                        5.Выйти""");
                System.out.print("Введите команду: ");
                String num = scanner.nextLine();
                switch (num) {
                    case "1" -> {
                        if (management.users.length == 1) {
                            System.out.println("Пользователь нету!");
                        } else {
                            System.out.println("Все пользователи:");
                            int userIndex = 1;
                            for (User user : management.users) {
                                if (!user.getEmail().equals(currentUser.getEmail())) {
                                    boolean found = false;
                                    for (User friend : user.friends) {
                                        if (friend.getEmail().equals(currentUser.getEmail())) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (found) {
                                        System.out.println((userIndex++) + ". " + user + " (В друзьях)");

                                    } else {
                                        System.out.println((userIndex++) + ". " + user);
                                    }

                                }
                            }
                            System.out.print("Кого хотите добавить в друзья (email): ");
                            String email = scanner.nextLine();
                            boolean found = false;
                            for (User user : management.users) {
                                if (user.getEmail().equals(email)) {
                                    boolean foundFriendRequest = false;
                                    boolean foundFriend = false;
                                    for (User friendRequest : user.friendRequests) {
                                        if (friendRequest.getEmail().equals(currentUser.getEmail())) {
                                            System.out.println("Вы уже отправили запрос в друзья!");
                                            foundFriendRequest = true;
                                            break;
                                        }
                                    }
                                    for (User friend : currentUser.friends) {
                                        if (friend.getEmail().equals(email)) {
                                            foundFriend = true;
                                            System.out.println("Этот друг уже в друзьях!");
                                            break;
                                        }
                                    }
                                    if (!foundFriendRequest && !foundFriend) {
                                        boolean found2 = false;
                                        for (User friendRequest : currentUser.friendRequests) {
                                            if (friendRequest.getEmail().equals(user.getEmail())) {
                                                found2 = true;
                                                for (User user1 : management.users) {
                                                    if (user1.getEmail().equals(friendRequest.getEmail())) {
                                                        user1.addFriend(currentUser);
                                                        break;
                                                    }
                                                }
                                                currentUser.addFriend(friendRequest);
                                                currentUser.deleteFriendRequests(friendRequest.getEmail());
                                                break;
                                            }
                                        }
                                        if (found2) {
                                            System.out.println("Вы успешно добавили друга!");
                                        } else {
                                            user.addFriendRequest(currentUser);
                                            System.out.println("Вы успешно отправили запрос в друзья!");
                                        }
                                    }
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.println("Пользователь с таким email не существует!");
                            }
                        }
                    }
                    case "2" -> {
                        boolean found = currentUser.displayMessages();
                        if (found) {
                            while (true) {
                                try {
                                    System.out.print("Выберите сообщение по id (0 Выйти): ");
                                    int index = scanner.nextInt();
                                    scanner.nextLine();
                                    if (index == 0) {
                                        break;
                                    }
                                    if (index - 1 >= currentUser.chats.length) {
                                        throw new Exception();
                                    }
                                    while (true) {
                                        System.out.println("Сообщение: ");
                                        if (currentUser.chats[index - 1].messages.length == 0) {
                                            System.out.println("""
                                                                                                        
                                                                                                        
                                                                                                        
                                                            Пусто
                                                                                                        
                                                                                                        
                                                                                                        
                                                    """);
                                        } else {
                                            for (String message : currentUser.chats[index - 1].messages) {
                                                if (message.contains(currentUser.getFirstName())) {
                                                    System.out.println("                " + message);
                                                } else {
                                                    System.out.println(message);
                                                }
                                            }
                                        }
                                        System.out.print("Написать сообщение (0 Выйти): ");
                                        String message = scanner.nextLine();
                                        if (message.equals("0")) {
                                            break;
                                        }
                                        if (!message.isEmpty()) {
                                            currentUser.chats[index - 1].addMessage(message, currentUser.getFirstName());
                                            for (User user : management.users) {
                                                if (currentUser.chats[index - 1].recipientUserEmail.equals(user.getEmail())) {
                                                    for (Chat chat : user.chats) {
                                                        if (chat.recipientUserEmail.equals(currentUser.getEmail())) {
                                                            chat.addMessage(message, currentUser.getFirstName());
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            System.out.println("Значение не должно быть пустым!");
                                        }
                                    }
                                } catch (Exception e) {
                                    System.out.println("Введите правильное id!");
                                    scanner.nextLine();
                                }
                            }
                        }

                    }
                    case "3" -> {
                        boolean found = currentUser.displayFriends();
                        if (found) {
                            while (true) {
                                System.out.print("Выберите друга (email) (0 Выйти): ");
                                String email = scanner.nextLine();
                                if (email.equals("0")) {
                                    break;
                                }
                                User currentFriend = null;
                                for (User friend : currentUser.friends) {
                                    if (friend.getEmail().equals(email)) {
                                        currentFriend = friend;
                                        break;
                                    }
                                }
                                if (currentFriend != null) {
                                    boolean foundChat = false;
                                    for (Chat chat : currentUser.chats) {
                                        if (chat.recipientUserEmail.equals(currentFriend.getEmail())) {
                                            foundChat = true;
                                            break;
                                        }
                                    }
                                    if(foundChat){
                                        System.out.println("У вас уже есть чат с эти другом!");
                                    } else {
                                        Chat chat = new Chat();
                                        chat.senderUserName = currentUser.getFirstName();
                                        chat.recipientUserName = currentFriend.getFirstName();
                                        chat.recipientUserEmail = currentFriend.getEmail();
                                        currentUser.addChat(chat);
                                        for (User user : management.users) {
                                            if (user.getEmail().equals(currentFriend.getEmail())) {
                                                Chat friendChat = new Chat();
                                                friendChat.senderUserName = currentFriend.getFirstName();
                                                friendChat.recipientUserName = currentUser.getFirstName();
                                                friendChat.recipientUserEmail = currentUser.getEmail();
                                                user.addChat(friendChat);
                                            }
                                        }
                                        System.out.println("Вы успешно создали чат!");
                                        System.out.println("Зайдите в раздел Мои сообщения чтобы написать другу!");
                                    }

                                    break;
                                } else {
                                    System.out.println("Неправильный email!");
                                }
                            }

                        }
                    }
                    case "4" -> {
                        boolean found = currentUser.displayFriendRequests();
                        if (found) {
                            while (true) {
                                try {
                                    System.out.print("Введите id (0 Выйти): ");
                                    int index = scanner.nextInt();
                                    if (index == 0) {
                                        break;
                                    }
                                    if (index - 1 >= currentUser.friendRequests.length) {
                                        throw new Exception();
                                    }
                                    while (true) {
                                        System.out.println("Хотите добавить этого пользователя в друзья? (1) Да (2) Нет");
                                        scanner.nextLine();
                                        if (scanner.hasNextInt()) {
                                            int choice = scanner.nextInt();
                                            if (choice == 1) {
                                                for (User user : management.users) {
                                                    String userEmail = user.getEmail();
                                                    if (userEmail.equals(currentUser.friendRequests[index - 1].getEmail())) {
                                                        user.addFriend(currentUser);
                                                        break;
                                                    }
                                                }
                                                currentUser.addFriend(currentUser.friendRequests[index - 1]);
                                                currentUser.deleteFriendRequests(currentUser.friendRequests[index - 1].getEmail());
                                                System.out.println("Вы успешно добавили друга!");

                                                scanner.nextLine();
                                                break;
                                            } else if (choice == 2) {
                                                currentUser.deleteFriendRequests(currentUser.friendRequests[index - 1].getEmail());
                                                System.out.println("Запрос отклонён!");
                                                scanner.nextLine();
                                                break;
                                            } else {
                                                System.out.println("Введите правильное число");
                                            }
                                        } else {
                                            System.out.println("Введите число!");
                                        }

                                    }
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Введите правильное id!");
                                    scanner.nextLine();
                                }
                            }
                        }

                    }
                    case "5" -> {
                        System.out.println("Вы вышли из программы!✅");
                        isLoggedIn = false;
                        exitUser = false;
                        for (User user : management.users) {
                            if (user.getEmail().equals(currentUser.getEmail())) {
                                user.chats = currentUser.chats;
                                user.friends = currentUser.friends;
                            }
                        }
                    }
                    default -> System.out.println("Введите правильное число!");
                }
            }
        }
    }
}