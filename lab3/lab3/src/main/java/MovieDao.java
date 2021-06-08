import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.mapper.annotations.*;

import java.util.Optional;


@Dao
public interface MovieDao {

    @Insert
    Movie save(Movie movie);

    @Select
    Optional<Movie> getById(int id);

    @Select
    Movie getByIdMovie(int id);

    @Update
    void updateByTitle(Movie movie, int id, String title);

    @Delete
    void delete(Movie movie);

    @Select
    PagingIterable<Movie> getAll();

}
