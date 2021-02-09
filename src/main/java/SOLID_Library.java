//import org.cef.SystemBootstrap;

import java.io.*;
import java.util.*;

public class SOLID_Library {

    public static class loader {
        private File file;
        public loader (File file) {
            this.file = file;
        }

        public HashMap<String, User> UserLoader(File file) {
            User user;
            String[] fileContent;
            HashMap<String, User> users = new HashMap<String, User>();
            boolean isAdmin;

            try {
                FileReader fReader = new FileReader(file);
                BufferedReader bReader = new BufferedReader(fReader);
                for (String line = bReader.readLine(); line != null; line = bReader.readLine()) {
                    fileContent = line.split(";");
                    if (fileContent.equals("1")) isAdmin = true;
                    else isAdmin = false;
                    user = new User(fileContent[0], isAdmin);
                    users.put(user.login, user);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return users;
        }

        public HashMap<String, Book> LibraryLoader(File file) {
            Book book;
            String[] fileContent;
            HashMap<String, Book> library = new HashMap<String, Book>();
            boolean available;

            try {
                FileReader fReader = new FileReader(file);
                BufferedReader bReader = new BufferedReader(fReader);
                for (String line = bReader.readLine(); line != null; line = bReader.readLine()) {

                    fileContent = line.split(";");
                    if (fileContent[2].equals("1")) available = true;
                    else available = false;
                    book = new Book(fileContent[0], fileContent[1], available);
                    library.put(book.title, book);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return library;
        }
    }

    public static class writer {
        private File file;
        public writer (File file) {
            this.file = file;
        }

        public void UserWriter(File file, HashMap<String, User> users) {

            try {
                FileWriter fWriter = new FileWriter(file);
                BufferedWriter bWriter = new BufferedWriter(fWriter);
                for (Map.Entry<String, User> entry : users.entrySet()) {
                    bWriter.write(entry.getValue().login + ";" + entry.getValue().isAdmin);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void LibraryWriter(File file, HashMap<String, Book> library) {
            try {
                FileWriter fWriter = new FileWriter(file);
                BufferedWriter bWriter = new BufferedWriter(fWriter);
                for (Map.Entry<String, Book> entry : library.entrySet()) {
                    bWriter.write(entry.getValue().title + ";" + entry.getValue().author_name + ";" + entry.getValue().available);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static class Book {
        private String title;
        private String author_name;
        private boolean available;
        public Book (String title, String author_name, boolean available) {
            this.title = title;
            this.author_name = author_name;
            this.available = available;
        }

        public void Borrow (Book book) {
            book.available = false;
        }



        public HashMap<String, Book> newReference (Book book, HashMap<String, Book> library) {
            library.put(book.title, book);
            return library;
        }
    }

    public static class User {
        private final String login;
        private boolean isAdmin;
        public User (String login, boolean isAdmin) {
            this.login = login;
            this.isAdmin = isAdmin;
        }

    }

    public static void ShowLibrary(HashMap<String, Book> library) {
        for (Map.Entry<String, Book> entry : library.entrySet()) {
            System.out.println(entry.getValue().title + " " + entry.getValue().author_name);
        }
    }

    public static void main(String[] args){
        int action;
        File file = new File("Users.csv");
        File file2 = new File("library.csv");
        loader userFile = new loader(file);
        loader libraryFile = new loader(file2);

        HashMap<String, Book> library = new HashMap<String, Book>();
        HashMap<String, User> users = new HashMap<String, User>();
        users = userFile.UserLoader(userFile.file);
        library = libraryFile.LibraryLoader(libraryFile.file);
        if (args.length == 0 || args.length > 0 && !users.containsKey(args[0])) {
            System.out.println("Connected as guest");
            System.out.println("1 : See library content");
            System.out.println("2 : Exit");
            Scanner sc = new Scanner(System.in);
            action = sc.nextInt();
            switch (action) {
                case 1 :
                    ShowLibrary(library);
                    break;
                default :
                    System.exit(0);
            }

        }
        else {
            String name = args[0];
            User currentUser = users.get(name);
            System.out.println("Connected as " + name);
            System.out.println("1 : Borrow a book");
            System.out.println("2 : See library content");
            System.out.println("3 : Exit");
            Scanner sc = new Scanner(System.in);
            action = sc.nextInt();
            switch (action) {
                case 1 :
                    ShowLibrary(library);
                    break;
                case 2 :
                    ShowLibrary(library);
                    break;
                default :
                    System.exit(0);
            }
        }


    }
}
