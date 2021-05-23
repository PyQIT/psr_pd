import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.client.HazelcastClient;

import com.hazelcast.map.IMap;
import models.Client;
import models.Book;
import models.Library;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;



public class ClientApp {

    final private static Random r = new Random(System.currentTimeMillis());
    static int i = 0;
    public static void save(){

        HazelcastInstance client = HazelcastClient.newHazelcastClient();

        Map<Long, Client> clients = client.getMap("clients");
        Map<Long, Book> books = client.getMap("books");
        Map<Long, Library> libraries = client.getMap("libraries");

        Long clientKey1 = (long) Math.abs(r.nextInt());
        Client client1 = new Client("Przemyslaw", "Pyk","6207080362", 123123123);
        System.out.println("PUT " + clientKey1 + " => " + client1);
        clients.put(clientKey1, client1);

        Long clientKey2 = (long) Math.abs(r.nextInt());
        Client client2 = new Client("Jacek","Placek","75121968629",111222333);
        clients.put(clientKey2, client2);
        System.out.println("PUT " + clientKey2 + " => " + client2);

        Long bookKey1 = (long) Math.abs(r.nextInt());
        Book book1 = new Book("Lalka","Bolesław Prus","555333222");
        books.put(bookKey1, book1);
        System.out.println("PUT " + bookKey1 + " => " + book1);

        Long bookKey2 = (long) Math.abs(r.nextInt());
        Book book2 = new Book("Dziady","Adam Mickiewicz","222333444");
        books.put(bookKey2, book2);
        System.out.println("PUT " + bookKey2 + " => " + book2);

        Long bookKey3 = (long) Math.abs(r.nextInt());
        Book book3 = new Book("Wiedzmin","Andrzej Sapkowski","111444333");
        books.put(bookKey3, book3);
        System.out.println("PUT " + bookKey3 + " => " + book3);

        Long libraryKey1 = (long) Math.abs(r.nextInt());
        Library library1 = new Library("BibliotekaGlowna", "Swietokrzyska 5", 100);
        libraries.put(libraryKey1, library1);
        System.out.println("PUT " + libraryKey1 + " => " + library1);

        Long libraryKey2 = (long) Math.abs(r.nextInt());
        Library library2 = new Library("BibliotekaMiejska", "Wyscigowa 25", 150);
        libraries.put(libraryKey2, library2);
        System.out.println("PUT " + libraryKey2 + " => " + library2);

    }
    public static void download() {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        IMap<Long, Client> clients = client.getMap("clients");
        IMap<Long, Book> books = client.getMap("books");
        IMap<Long, Library> libraries = client.getMap("libraries");

        System.out.println("All clients: ");
        for (Entry<Long, Client> e : clients.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }

        System.out.println("All books: ");
        for (Entry<Long, Book> e : books.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }

        System.out.println("All libraries: ");
        for (Entry<Long, Library> e : libraries.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }
    }


    public static void getClientByPesel() {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        System.out.println("Enter the PESEL of client");
        Scanner scan = new Scanner(System.in);
        String pesel = scan.nextLine();

        IMap<Long, Client> clients = client.getMap("clients");
        long key = -1;
        for (Entry<Long, Client> entry : clients.entrySet()) {
            if(entry.getValue().getPesel().equals(pesel)){
             key = entry.getKey();
            }
        }
        if(key != -1){
            Client client1 = clients.get(key);
            System.out.println("Client found." + client1.getName() + " " + client1.getSurname());
        } else
            System.out.println("PESEL doesnt match");
    }

    public static void delete(){
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        System.out.println("Enter the number of book");
        Scanner scan = new Scanner(System.in);
        String bookNumber = scan.nextLine();

        IMap<Long, Book> books = client.getMap("books");
        long key = -1;
        for (Entry<Long, Book> entry : books.entrySet()) {
            if(entry.getValue().getBookNumber().equals(bookNumber)){
                key = entry.getKey();
            }
        }
        if(i <= 0){
            books.remove(key);
            i++;
            System.out.println("Removed");
        } else {
            System.out.println("Number doesnt match");
        }
    }

    public static void update(){
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        System.out.println("Enter the number of book");
        Scanner scan = new Scanner(System.in);
        String bookNumber = scan.nextLine();

        IMap<Long, Book> books = client.getMap("books");
        long key = -1;
        for (Entry<Long, Book> entry : books.entrySet()) {
            if(entry.getValue().getBookNumber().equals(bookNumber)){
                key = entry.getKey();
            }
        }
        if(key != -1){
            Book bookModel = books.get(key);
            bookModel.setBookNumber("666111222");
        } else
            System.out.println("Number doesnt match");
    }


    public static void main( String[] args ) throws UnknownHostException {

        Scanner scan = new Scanner(System.in);
        String option = "";
        while (!option.equals("0")) {
            System.out.println("Pick the option: ");
            System.out.println("1.Save \n2.Update \n3.Delete \n4.Download \n5.Process after client \n6.Process after server \n0.Exit");
            option = scan.nextLine();
            switch (option) {
                case "0":
                    System.out.println("Exit");
                    break;
                case "1":
                    System.out.println("Save");
                    save();
                    break;
                case "2":
                    System.out.println("Update");
                    update();
                    break;
                case "3":
                    System.out.println("Delete");
                    delete();
                    break;
                case "4":
                    System.out.println("Download");
                    download();
                    getClientByPesel();
                    break;
                case "5":
                    System.out.println("Process after client");
                    HAgregate.init();
                    break;
                case "6":
                    System.out.println("Process after server");
                    HExecutorService.init();
                    break;
            }
        }


    }
}
