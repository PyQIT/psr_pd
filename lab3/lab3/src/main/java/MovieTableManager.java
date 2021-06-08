import com.datastax.oss.driver.api.core.CqlSession;

public class MovieTableManager extends SimpleManager {
    public MovieTableManager(CqlSession session) {
        super(session);
    }

    public void createTable() {
        executeSimpleStatement(
                "CREATE TYPE Cinema (\n" +
                        "    uniqueID text,\n" +
                        "    address text,\n" +
                        ");");
        executeSimpleStatement(
                "CREATE TYPE Viewer (\n" +
                        "    name text,\n" +
                        "    surname text,\n" +
                        ");");
        executeSimpleStatement(
                "CREATE TABLE Movie (\n" +
                        "    id int PRIMARY KEY,\n" +
                        "    title text\n," +
                        "    viewer frozen<viewer>,\n" +
                        "    cinema frozen<cinema>,\n" +
                        ");");
    }
}
