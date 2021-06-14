package neo4jLab4;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.util.*;

public class Main {

    private static FootballerService footballerService;
    private static RefereeService refereeService;
    private static FootballerPenaltyService footballerPenaltyService;

    public static void main(String[] args) {
        Configuration configuration = new Configuration.Builder().uri("bolt://localhost:7687").credentials("neo4j", "neo4jpassword").build();
        SessionFactory sessionFactory = new SessionFactory(configuration, "neo4jLab4");

        Session session = sessionFactory.openSession();
        session.purgeDatabase();

        Scanner in = new Scanner(System.in);
        footballerService = new FootballerService(session);
        refereeService = new RefereeService(session);
        footballerPenaltyService = new FootballerPenaltyService(session);


        menu:
        while (true) {
            displayMenu();
            System.out.print("Choose operation: ");
            switch (in.nextInt()) {
                case 1: {
                    System.out.println("Footballer penalty: ");
                    footballerPenaltyService.readAll().forEach(System.out::println);
                    refereeService.readAll().forEach(System.out::println);
                    footballerService.readAll().forEach(System.out::println);
                    break;
                }
                case 2: {

                    Footballer footballer1 = new Footballer("Jan", "Duda","Ekstraklasa");
                    Footballer footballer2 = new Footballer("Przemyslaw", "Pyk","La Liga");
                    Footballer footballer3 = new Footballer("Grzegorz", "Krzychowiak","Dno");

                    Referee referee1 = new Referee("Jan","Kran", 23.0);
                    Referee referee2 = new Referee("Marian","Trak", 42.0);
                    Referee referee3 = new Referee("Norbert","Moczak", 35.0);


                    FootballerPenalty footballerPenalty1 = new FootballerPenalty( "Polska-Slowacja", 2.0, referee1, footballer3);
                    FootballerPenalty footballerPenalty2 = new FootballerPenalty("Polska-Hiszpania", 1.0, referee3, footballer1);
                    FootballerPenalty footballerPenalty3 = new FootballerPenalty("Polska-Szwecja", 2.0, referee2, footballer2);


                    footballerPenaltyService.createOrUpdate(footballerPenalty1);
                    footballerPenaltyService.createOrUpdate(footballerPenalty2);
                    footballerPenaltyService.createOrUpdate(footballerPenalty3);

                    break;
                }
                case 3: {
                    System.out.println("Enter id: ");
                    var id = new Scanner(System.in).nextLine();
                    footballerPenaltyService.delete(Long.parseLong(id));
                    break;
                }
                case 4: {
                    Scanner reader = new Scanner(System.in);
                    System.out.println("Enter id: ");
                    long penaltyId = reader.nextLong();
                    reader.close();
                    System.out.println("Enter new footballer penalty");
                    Scanner reader1 = new Scanner(System.in);
                    double newPenalty = reader1.nextDouble();

                    var query = "MATCH (e:FootballerPenalty) WHERE ID(e) = " + penaltyId + " SET e.penalty = " + newPenalty + " return e";
                    session.query(query, Collections.emptyMap());
                    System.out.println((footballerPenaltyService.read(Long.parseLong(Long.toString(penaltyId)))));
                    break;
                }
                case 5: {
                    System.out.println("Enter id: ");
                    var id = new Scanner(System.in).nextLine();
                    System.out.println((footballerPenaltyService.read(Long.parseLong(id))));
                    break;
                }
                case 6: {
                    System.out.println("Enter referee name:");
                    var name = new Scanner(System.in).nextLine();
                    System.out.println("Enter referee surname:");
                    var surname = new Scanner(System.in).nextLine();

                    var query ="MATCH (e:FootballerPenalty)-[:CREATED_BY]->(ta:Referee) WHERE ta.name = '"+ name + "' AND ta.surname = '"+ surname + "' RETURN e";
                    var result = session.query(query, Collections.emptyMap());

                    result.forEach(System.out::println);

                    break;
                }
                default: {
                    break menu;
                }
            }
        }
        sessionFactory.close();
    }

    private static void displayMenu() {
        System.out.println(
                "1. All Excursion\n" +
                "2. Save all\n" +
                "3. Remove penalty from DB\n" +
                "4. Update penalty cards\n" +
                "5. Get penalty by id\n" +
                "6. Get cards by referee name and surname \n" +
                "7. Finish\n");
    }
}
