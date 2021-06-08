import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

@Entity
public class Movie {

    @PartitionKey
    private Integer id;
    private String title;
    private Viewer viewer;
    private Cinema cinema;

    public Movie(Integer id, String title, Viewer viewer, Cinema cinema){
        this.id = id;
        this.title = title;
        this.viewer = viewer;
        this.cinema = cinema;
    }

    public Movie() {

    }

    public int getId(){ return id; }

    public void setId(int id){ this.id = id; }

    public void setTitle(String title) {this.title = title;}

    public String getTitle() {return title;}

    public Viewer getViewer(){
        return viewer;
    }

    public void setViewer(Viewer viewer){
        this.viewer = viewer;
    }

    public Cinema getCinema(){
        return cinema;
    }

    public void setCinema(Cinema cinema){
        this.cinema = cinema;
    }


    @Override
    public String toString(){
        return "Movie: "+" Id: " + id + " Title: " + title + " Viewer: " + viewer + " Cinema: " + cinema;
    }


}
