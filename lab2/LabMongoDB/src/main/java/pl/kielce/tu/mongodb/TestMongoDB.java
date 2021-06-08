package pl.kielce.tu.mongodb;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Projections.include;
import java.util.Arrays;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestMongoDB {
	public static void main(String[] args) {

		String user = "student01";
		String password = "student01";
		String host = "localhost";
		int port = 27017;
		String database = "database01";

		String clientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;
		MongoClientURI uri = new MongoClientURI(clientURI);

		MongoClient mongoClient = new MongoClient(uri);

		MongoDatabase db = mongoClient.getDatabase(database);

		db.getCollection("travelAgency").drop();

		Scanner scan = new Scanner(System.in);
		String option = "";
		try {
			while (!option.equals("0")) {
				System.out.println("Pick the option: ");
				System.out.println("1.Save \n2.Delete \n3.Update \n4.Download \n0.Exit");
				option = scan.nextLine();
				switch (option) {
					case "0" -> System.out.println("Exit");
					case "1" -> {
						System.out.println("Save");
						save(db);
					}
					case "2" -> {
						System.out.println("Delete");
						delete(db);
					}
					case "3" -> {
						System.out.println("Update");
						update(db);
					}
					case "4" -> {
						System.out.println("Download");
						download(db);
						getTravelByName(db);
					}
				}
			}
		}catch(Exception ex){
			System.out.println(ex);
		}
 		
		mongoClient.close();
	}

	private static void save(MongoDatabase db){
		MongoCollection<Document> collection = db.getCollection("travelAgency");

		Document maroko = new Document("_wid", 1)
				.append("wycieczka", "Wycieczka do Maroka")
				.append("przewodnik", "Jan Tak")
				.append("data", "21.07.2021-29.07.2021")
				.append("lista_uczestnikow",
						Arrays.asList(new Document("_idu", 1)
								.append("imie", "Przemyslaw")
								.append("nazwisko", "Pyk")
						, new Document("_idu", 2)
										.append("imie", "Jan")
										.append("nazwisko", "Mocny")
						, new Document("_idu", 3)
										.append("imie", "Staszek")
										.append("nazwisko", "Tracz")));

		collection.insertOne(maroko);

		Document dubai = new Document("_wid", 2)
				.append("wycieczka", "Wycieczka do Dubaju")
				.append("przewodnik", "Karol Frank")
				.append("data", "14.09.2021-23.09.2021")
				.append("lista_uczestnikow",
						Arrays.asList(new Document("_idu", 1)
										.append("imie", "Przemyslaw")
										.append("nazwisko", "Pyk")
								, new Document("_idu", 2)
										.append("imie", "Janusz")
										.append("nazwisko", "Palikota")
								, new Document("_idu", 3)
										.append("imie", "Jacek")
										.append("nazwisko", "Placek")));

		collection.insertOne(dubai);

		Document ibiza = new Document("_wid", 3)
				.append("wycieczka", "Wycieczka na Ibize")
				.append("przewodnik", "Krzysztof Klawiatura")
				.append("data", "01.10.2021-06.10.2021")
				.append("lista_uczestnikow",
						Arrays.asList(new Document("_idu", 1)
										.append("imie", "Maciej")
										.append("nazwisko", "Kaciej")
								, new Document("_idu", 2)
										.append("imie", "Marta")
										.append("nazwisko", "Karta")
								, new Document("_idu", 3)
										.append("imie", "Wiktoria")
										.append("nazwisko", "Bogoria")));

		collection.insertOne(ibiza);
	}

	private static void delete(MongoDatabase db){
		MongoCollection<Document> collection = db.getCollection("travelAgency");

		collection.deleteOne(eq("_wid", 3));
		for (Document doc : collection.find())
			System.out.println("deleteOne(eq(\"_wid\", 3)) " + doc.toJson());
	}

	private static void update(MongoDatabase db){
		MongoCollection<Document> collection = db.getCollection("travelAgency");

		collection.updateOne(eq("_wid", 2), new Document("$set", new Document("wycieczka", "Wycieczka do Dubaju").append("przewodnik", "Franek Dolas")));
	}

	private static void download(MongoDatabase db){
		MongoCollection<Document> collection = db.getCollection("travelAgency");

		Document first = collection.find().first();
		System.out.println("find().first() " + first.toJson());

		for (Document doc : collection.find())
			System.out.println("find() " + doc.toJson());

	}

	private static void getTravelByName(MongoDatabase db){
		MongoCollection<Document> collection = db.getCollection("travelAgency");

		System.out.println("Podaj id: ");
		Scanner scan = new Scanner(System.in);
		Integer option = 0;
		option = Integer.parseInt(scan.nextLine());

		Document myDoc = collection.find(lt("_wid", option+1)).first();
		System.out.println("lt(\"_wid\"," + option + ") " + myDoc.toJson());


	}

}
