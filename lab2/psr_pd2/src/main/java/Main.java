import java.util.*;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import models.Travels;


public class Main {

    public static void main(String[] args) {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "eu-central-1"))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        Scanner scan = new Scanner(System.in);

        String option = "";
        while (!option.equals("0")) {
            System.out.println("Pick the option: ");
            System.out.println("1.Save");
            System.out.println("2.Update");
            System.out.println("3.Delete all");
            System.out.println("4.Download");
            System.out.println("5.Download travel by guide");
            System.out.println("0.Exit");
            option = scan.nextLine();
            switch (option) {
                case "0" -> System.out.println("Exit");
                case "1" -> {
                    System.out.println("Save");
                    save(dynamoDB, mapper);
                }
                case "2" -> {
                    System.out.println("Update");
                    update(mapper);
                }
                case "3" -> {
                    System.out.println("Delete all");
                    deleteAll(dynamoDB);
                }
                case "4" -> {
                    System.out.println("Download");
                    download(mapper);
                }
                case "5" -> {
                    System.out.println("Download travel by guide");
                    downloadByGuideName(mapper);
                }
            }
        }
    }

        public static void save(DynamoDB dynamoDB, DynamoDBMapper mapper) {
            CreateTableRequest createTableRequest = mapper.generateCreateTableRequest(Travels.class);
            createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(25L, 25L));
            dynamoDB.createTable(createTableRequest);

            Travels dubai = new Travels().builder()
                    .id(1)
                    .name("Wycieczka do Dubaju")
                    .guide("Karol Frank")
                    .date("10.09.2021-23.09.2021")
                    .attendee("Przemyslaw Pyk")
                    .build();

            mapper.save(dubai);

            Travels ibiza = new Travels().builder()
                    .id(2)
                    .name("Wycieczka na Ibize")
                    .guide("Krzysztof Klawiatura")
                    .date("01.10.2021-06.10.2021")
                    .attendee("Janusz Placek")
                    .build();

            mapper.save(ibiza);
        }

        public static void update(DynamoDBMapper mapper){
            Travels travels = mapper.load(Travels.class, 1);

            System.out.println("Set guide name: ");
            Scanner scan = new Scanner(System.in);
            String guideName = scan.nextLine();
            travels.setGuide(guideName);

            mapper.save(travels);

        }

        public static void deleteAll(DynamoDB dynamoDB){
            System.out.println(dynamoDB.getTable("NewTravel").delete());
        }

        public static void download(DynamoDBMapper mapper){

            DynamoDBScanExpression getAllLists = new DynamoDBScanExpression();

            List<Travels> travels = mapper.scan(Travels.class, getAllLists);
            travels.forEach(System.out::println);
            System.out.println("");
        }

        public static void downloadByGuideName(DynamoDBMapper mapper){
            System.out.println("Guide name: ");
            Scanner scan = new Scanner(System.in);
            String guideName = scan.nextLine();
            Map<String, AttributeValue> eav = new HashMap<>();
            eav.put(":value", new AttributeValue().withN(guideName));
            DynamoDBScanExpression getAllTravelsWithGuide  = new DynamoDBScanExpression()
                    .withFilterExpression("rating >= :value").withExpressionAttributeValues(eav);

            System.out.println("Travel list:  \n");
            List<Travels> travelsList = mapper.scan(Travels.class, getAllTravelsWithGuide);
            travelsList.forEach(System.out::println);

        }

    }
