import models.Book;
import models.Client;
import models.Library;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class RedisApp {

        public static void main(String[] args) throws Exception{

            Jedis jedis = new Jedis("localhost");
            System.out.println("Connection Successful");
            System.out.println("The server is running " + jedis.ping());
            jedis.set("library-name", "library");
            System.out.println("Stored string in redis: "+ jedis.get("library-name"));

            Scanner scan = new Scanner(System.in);
            String option = "";
            while (!option.equals("0")) {
                System.out.println("Pick the option: ");
                System.out.println("1.Save \n2.Delete \n3.Delete All \n4.Download \n5.Process after client \n0.Exit");
                option = scan.nextLine();
                switch (option) {
                    case "0" -> System.out.println("Exit");
                    case "1" -> {
                        System.out.println("Save");
                        save(jedis);
                    }
                    case "2" -> {
                        System.out.println("Delete");
                        deleteAll(jedis);
                    }
                    case "3" -> {
                        System.out.println("Delete All");
                        deleteAll(jedis);
                    }
                    case "4" -> {
                        System.out.println("Download");
                        download(jedis);
                        getListByName(jedis);
                    }
                    case "5" -> {
                        System.out.println("Process after client");
                        clientProcessing(jedis);
                    }
                }
            }
       }

    public static void save(Jedis jedis){

        jedis.lpush("clients", "przemyslaw", "pyk", "6207080362","123123123");
        jedis.lpush("clients", "jan", "Placek","75121968629","111222333");

        jedis.lpush("books","Lalka", "Bolesław Prus", "10");
        jedis.lpush("books","Dziady", "Adam Mickiewicz", "9");
        jedis.lpush("books","Wiedzmin", "Andrzej Sapkowski", "48");

        jedis.lpush("libraries","Biblioteka Glowna", "Swietokrzyska 5", "92");
        jedis.lpush("libraries","Biblioteka Miejska", "Wyscigowa 25", "56");

        jedis.lpush("borrows", "Lalka", "Bolesław Prus", "10", "przemyslaw", "pyk", "6207080362","123123123", "Biblioteka Glowna", "Swietokrzyska 5", "92");
        jedis.lpush("borrows", "Wiedzmin", "Andrzej Sapkowski", "48", "jan", "Placek","75121968629","111222333", "Biblioteka Miejska", "Wyscigowa 25", "56");
    }

    public static void download(Jedis jedis) {
        List<String> clientsList = jedis.lrange("clients",0, -1);
        List<String> booksList = jedis.lrange("books",0, -1);
        List<String> librariesList = jedis.lrange("libraries",0, -1);
        List<String> borrowsList = jedis.lrange("borrows",0, -1);
        System.out.println("Client list: " + clientsList);
        System.out.println("Books list: " + booksList);
        System.out.println("Libraries list: " + librariesList);
        System.out.println("Borrows list: " + borrowsList);
    }


    public static void getListByName(Jedis jedis){
        System.out.println("Enter the wanted list: client, books, libraries, borrows");

        Scanner scan = new Scanner(System.in);
        String list = scan.nextLine();

        List<String> clientsList = jedis.lrange("clients",0, -1);
        List<String> booksList = jedis.lrange("books",0, -1);
        List<String> librariesList = jedis.lrange("libraries",0, -1);
        List<String> borrowsList = jedis.lrange("borrows",0, -1);

        if(list.equals("client"))
            System.out.println("Client list: " + clientsList);
        if(list.equals("books"))
            System.out.println("Books list: " + booksList);
        if(list.equals("libraries"))
            System.out.println("Libraries list: " + librariesList);
        if(list.equals("borrows"))
            System.out.println("Borrows list: " + borrowsList);
    }

    public static void delete(Jedis jedis){
        System.out.println("Enter the name: ");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();

        Set<String> keyList = jedis.keys("name");
        for(String key : keyList){
            System.out.println(key);
            jedis.del(key);
        }
    }

    public static void deleteAll(Jedis jedis){

        String keyList = jedis.flushAll();
        System.out.println(keyList);
    }

    public static void clientProcessing(Jedis jedis){
        Object o = jedis.eval("return redis.call('time')[1]");
        System.out.println(o.toString());
    }
}
