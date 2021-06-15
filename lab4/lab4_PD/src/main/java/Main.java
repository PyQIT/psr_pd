import java.util.Scanner;
import com.arangodb.*;
import com.arangodb.entity.BaseDocument;

public class Main {

    public static void main(String[] args) {

        System.out.println("Initializing ArangoDb......");
        final ArangoDB arangoDB = new ArangoDB.Builder().user("root").password("admin").build();
        String dbName = "footballerDB";
        String collectionName = "footballerCol";

        try {
            arangoDB.createDatabase(dbName);
            arangoDB.db(dbName).createCollection(collectionName);
        } catch (ArangoDBException e) {
            System.err.println("Database already created.");
        }

        Scanner scan = new Scanner(System.in);
        String option = "";
        while (!option.equals("0")) {
            System.out.println("Pick the option: ");
            System.out.println("1.Save");
            System.out.println("2.Update");
            System.out.println("3.Delete footballer");
            System.out.println("4.Download");
            System.out.println("5.Download footballer by id");
            System.out.println("0.Exit");
            option = scan.nextLine();
            switch (option) {
                case "0":
                    System.exit(0);
                    break;
                case "1":
                    //creating a document
                    BaseDocument footballer = new BaseDocument();
                    footballer.setKey("1");
                    footballer.addAttribute("Imie", "Przemyslaw");
                    footballer.addAttribute("Nazwisko", "Pyk");

                    BaseDocument footballer2 = new BaseDocument();
                    footballer2.setKey("2");
                    footballer2.addAttribute("Imie", "Grzegorz");
                    footballer2.addAttribute("Nazwisko", "Krychowiak");

                    try {
                        arangoDB.db(dbName).collection(collectionName).insertDocument(footballer);
                        System.out.println("Document created");
                        arangoDB.db(dbName).collection(collectionName).insertDocument(footballer2);
                        System.out.println("Document created");
                    } catch (ArangoDBException e) {
                        System.err.println("Failed to create document. " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("Enter the ID of footballer to update records : ");
                    String upID = scan.next();
                    if(arangoDB.db(dbName).collection(collectionName).documentExists(upID)) {

                        BaseDocument footballerUp = arangoDB.db(dbName).collection(collectionName).getDocument(upID, BaseDocument.class);

                        footballerUp.setKey(upID);
                        System.out.println("Enter First Name");
                        String upFname = scan.next();
                        footballerUp.addAttribute("Imie", upFname);
                        System.out.println("Enter Last Name");
                        String upLname = scan.next();
                        footballerUp.addAttribute("Nazwisko", upLname);

                        try {
                            arangoDB.db(dbName).collection(collectionName).updateDocument(upID, footballerUp);
                        } catch (ArangoDBException e) {
                            System.err.println("Failed to update document. " + e.getMessage());
                        }
                    }
                    else {
                        System.out.println("Record #" + upID + " doesn't exist. Failed to update record.");
                    }
                    break;
                case "3":
                    System.out.println("Enter ID of footballer to delete : ");
                    String delID = scan.next();

                    try {
                        arangoDB.db(dbName).collection(collectionName).deleteDocument(delID);
                        System.out.println("Record #" + delID + " deleted successfully.");
                    } catch (ArangoDBException e) {
                        System.err.println("The record ID does not exist. Failed to delete record.");
                    }
                    break;
                case "4":
                    try {
                       System.out.println(arangoDB.db(dbName).collection(collectionName).getDocument("1",
                               BaseDocument.class));
                        System.out.println(arangoDB.db(dbName).collection(collectionName).getDocument("2",
                                BaseDocument.class));
                    } catch (NullPointerException e) {
                        System.err.println("Record doesn't exist. ");
                    }
                    break;
                case "5":
                    try {
                        System.out.println("Enter ID of footballer to check records :");
                        String checkID = scan.next();

                        System.out.println(arangoDB.db(dbName).collection(collectionName).getDocument(checkID,
                                BaseDocument.class));

                    } catch (NullPointerException e) {
                        System.err.println("Record doesn't exist. ");
                    }
                    break;
            }
        }
    }

}
