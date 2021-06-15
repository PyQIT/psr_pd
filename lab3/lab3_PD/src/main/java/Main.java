import com.azure.cosmos.*;
import com.azure.cosmos.models.*;
import com.azure.cosmos.util.CosmosPagedIterable;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static CosmosClient client;
    private static CosmosDatabase database;
    private static CosmosContainer footballers;
    private static CosmosContainer clubs;
    private static CosmosContainer matches;

    public static void save(CosmosContainer footballers) {
        Footballer footballer1 = new Footballer().builder()
                .id("1")
                .partitionKey("1")
                .name("Jan")
                .surname("Bak")
                .build();

        Footballer footballer2 = new Footballer().builder()
                .id("2")
                .partitionKey("2")
                .name("Marian")
                .surname("Szybki")
                .build();

        Footballer footballer3 = new Footballer().builder()
                .id("3")
                .partitionKey("3")
                .name("Kamil")
                .surname("Stas")
                .build();


        footballers.createItem(footballer1, new PartitionKey(footballer1.getPartitionKey()), new CosmosItemRequestOptions());
        footballers.createItem(footballer2, new PartitionKey(footballer2.getPartitionKey()), new CosmosItemRequestOptions());
        footballers.createItem(footballer3, new PartitionKey(footballer3.getPartitionKey()), new CosmosItemRequestOptions());

        Club club1 = new Club().builder()
                .id("4")
                .partitionKey("4")
                .name("Korona")
                .build();

        Club club2 = new Club().builder()
                .id("5")
                .partitionKey("5")
                .name("Bilcza")
                .build();

        footballers.createItem(club1, new PartitionKey(club1.getPartitionKey()), new CosmosItemRequestOptions());
        footballers.createItem(club2, new PartitionKey(club2.getPartitionKey()), new CosmosItemRequestOptions());

        Match match1 = new Match().builder()
                .id("6")
                .partitionKey("6")
                .place("Kielce")
                .ranking(2)
                .build();

        Match match2 = new Match().builder()
                .id("7")
                .partitionKey("7")
                .place("Warszawa")
                .ranking(5)
                .build();

        Match match3 = new Match().builder()
                .id("8")
                .partitionKey("8")
                .place("Krakow")
                .ranking(6)
                .build();

        footballers.createItem(match1, new PartitionKey(match1.getPartitionKey()), new CosmosItemRequestOptions());
        footballers.createItem(match2, new PartitionKey(match2.getPartitionKey()), new CosmosItemRequestOptions());
        footballers.createItem(match3, new PartitionKey(match3.getPartitionKey()), new CosmosItemRequestOptions());

    }

    public static void delete(){
            System.out.println("Write club id");
            Scanner scanner = new Scanner(System.in);
            int clubId = scanner.nextInt();
            CosmosItemResponse<Footballer> item = clubs
                    .readItem(String.valueOf(clubId), new PartitionKey(String.valueOf(clubId)), Footballer.class);
        clubs.deleteItem(item.getItem(), new CosmosItemRequestOptions());
        }


    private static void update() {
        System.out.println("Write footballer id");
        Scanner scanner = new Scanner(System.in);
        int footballerId = scanner.nextInt();
        CosmosItemResponse<Footballer> item = footballers
                .readItem(String.valueOf(footballerId), new PartitionKey(String.valueOf(footballerId)), Footballer.class);
        if (item != null) {
            System.out.println("Write new name of footballer");
            scanner.nextLine();
            item.getItem().setName(scanner.nextLine());
            System.out.println("Write surname of footballer");
            item.getItem().setSurname(scanner.nextLine());
            footballers.upsertItem(item.getItem());
        } else
            System.out.println("Footballer with given id does not exist");
    }

    private static void downloadByRanking() {
        Scanner scan = new Scanner(System.in);
        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        queryOptions.setQueryMetricsEnabled(true);
        System.out.println("Write minimal ranking for match");
        int ranking = scan.nextInt();

        try {
            CosmosPagedIterable<Match> items = matches.queryItems(
                    "SELECT * FROM Match WHERE Match.ranking > " + ranking, queryOptions, Match.class);
            items.forEach(System.out::println);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void download() {
        try {
            CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
            queryOptions.setQueryMetricsEnabled(true);
            CosmosPagedIterable<Footballer> footballersList = footballers
                    .queryItems("SELECT * FROM Footballer", queryOptions, Footballer.class);
            footballersList.forEach(System.out::println);
            System.out.println();
            CosmosPagedIterable<Match> match1 = footballers.queryItems("SELECT * FROM Match", queryOptions, Match.class);
            match1.forEach(System.out::println);
            System.out.println();
            CosmosPagedIterable<Club> matchesList = footballers
                    .queryItems("SELECT * FROM Club", queryOptions, Club.class);
            matchesList.forEach(System.out::println);

        } catch (CosmosException e) {
            System.err.println(String.format(e.getMessage()));
        }
    }


    public static void main(String[] args) {

        ArrayList<String> preferredRegions = new ArrayList<>();
        preferredRegions.add("Central US");

        client = new CosmosClientBuilder()
                .endpoint(Connection.HOST)
                .key(Connection.MASTER_KEY)
                .preferredRegions(preferredRegions)
                .userAgentSuffix("CosmosDBJavaQuickstart")
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();

        createDatabaseIfNotExists();
        createContainersIfNotExists();

        Scanner scan = new Scanner(System.in);
        String option = "";
        while (!option.equals("0")) {
            System.out.println("Pick the option: ");
            System.out.println("1.Save");
            System.out.println("2.Update");
            System.out.println("3.Delete club");
            System.out.println("4.Download");
            System.out.println("5.Download match by ranking");
            System.out.println("0.Exit");
            option = scan.nextLine();
            switch (option) {
                case "0":
                    System.exit(0);
                    break;
                case "1":
                    save(footballers);
                    break;
                case "2":
                    update();
                    break;
                case "3":
                    delete();
                    break;
                case "4":
                    download();
                    break;
                case "5":
                    downloadByRanking();
                    break;
            }
        }
    }

    private static void createDatabaseIfNotExists() {
        CosmosDatabaseResponse databaseResponse = client.createDatabaseIfNotExists("Database");
        database = client.getDatabase(databaseResponse.getProperties().getId());
    }


    private static void createContainersIfNotExists() {
        CosmosContainerProperties containerProperties =
                new CosmosContainerProperties("Footballers", "/partitionKey");
        CosmosContainerResponse containerResponse = database.createContainerIfNotExists(containerProperties);
        footballers = database.getContainer(containerResponse.getProperties().getId());

        CosmosContainerProperties containerProperties2 =
                new CosmosContainerProperties("Clubs", "/partitionKey");
        CosmosContainerResponse containerResponse2 = database.createContainerIfNotExists(containerProperties2);
        clubs = database.getContainer(containerResponse2.getProperties().getId());

        CosmosContainerProperties containerProperties3 =
                new CosmosContainerProperties("Match", "/partitionKey");
        CosmosContainerResponse containerResponse3 = database.createContainerIfNotExists(containerProperties3);
        matches = database.getContainer(containerResponse3.getProperties().getId());
    }


}
