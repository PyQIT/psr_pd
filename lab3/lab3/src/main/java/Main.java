import java.util.Optional;
import java.util.Scanner;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;

public class Main {


    public static void main(String[] args) {


        CqlSession session = CqlSession.builder().build();
        KeyspaceManager keyspaceManager = new KeyspaceManager(session, "movie");
        keyspaceManager.dropKeyspace();
        keyspaceManager.selectKeyspaces();
        keyspaceManager.createKeyspace();
        keyspaceManager.useKeyspace();

        MovieTableManager tableManager = new MovieTableManager(session);
        tableManager.createTable();

        CodecManager codecManager = new CodecManager(session);
        codecManager.registerViewerCodec();
        codecManager.registerCinemaCodec();

        MovieMapper visitMapper = new MovieMapperBuilder(session).build();
        MovieDao dao = visitMapper.movieDao(CqlIdentifier.fromCql("movie"));


        Scanner scan = new Scanner(System.in);
        String option = "";
        while (!option.equals("0")) {
            System.out.println("Pick the option: ");
            System.out.println("1.Save");
            System.out.println("2.Update");
            System.out.println("3.Delete movie by ID");
            System.out.println("4.Download");
            System.out.println("5.Download movie by ID");
            System.out.println("0.Exit");
            option = scan.nextLine();
            switch (option) {
                case "0":
                    System.out.println("Exit");
                    break;
                case "1":

                    Viewer viewer1 = new Viewer("Przemyslaw", "Pyk");
                    Viewer viewer2 = new Viewer("Jacek", "Placek");

                    Cinema cinema1 = new Cinema("1","Wielkopolska 15");
                    Cinema cinema2 = new Cinema("2","Zakopianka 3");

                    Movie movie1 = new Movie(1, "Obcy kontra predator", viewer1, cinema1);
                    Movie movie2 = new Movie(2, "Smolensk", viewer2, cinema1);
                    Movie movie3 = new Movie(3, "Kung fu Panda", viewer2, cinema2);

                    dao.save(movie1);
                    dao.save(movie2);
                    dao.save(movie3);

                    break;
                case "2":
                  System.out.println("Enter the movie ID:");
                  int movieToEdit = new Scanner(System.in).nextInt();
                  Movie movie = dao.getByIdMovie(movieToEdit);
                  System.out.println("Enter new title:");
                  String newTitle = new Scanner(System.in).nextLine();
                  dao.updateByTitle(movie, movieToEdit,newTitle);
                  System.out.println(dao.getByIdMovie(movieToEdit));
                  break;
                case "3":
                    System.out.println("Enter the movie ID:");
                    int movieToDelete = new Scanner(System.in).nextInt();
                    dao.getById(movieToDelete).ifPresent(dao::delete);
                    break;
                case "4":
                    dao.getAll().forEach(System.out::println);
                    break;
                case "5":
                    System.out.println("Enter ID: ");
                    int id = new Scanner(System.in).nextInt();
                    Optional<Movie> movie4 = dao.getById(id);
                    System.out.println(movie4);
                    break;
            }
        }

    }

    }
