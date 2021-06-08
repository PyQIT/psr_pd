import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;

public class CodecManager extends SimpleManager {

    public CodecManager(CqlSession session) {
        super(session);
    }


    public void registerViewerCodec() {
        CodecRegistry codecRegistry = session.getContext().getCodecRegistry();
        UserDefinedType viewerUdt =
                session
                        .getMetadata()
                        .getKeyspace("movie")
                        .flatMap(ks -> ks.getUserDefinedType("viewer"))
                        .orElseThrow(IllegalStateException::new);
        TypeCodec<UdtValue> typeCodec = codecRegistry.codecFor(viewerUdt);
        ViewerCodec viewerCodec = new ViewerCodec(typeCodec);
        ((MutableCodecRegistry) codecRegistry).register(viewerCodec);
    }

    public void registerCinemaCodec() {
        CodecRegistry codecRegistry = session.getContext().getCodecRegistry();
        UserDefinedType cinemaUdt =
                session
                        .getMetadata()
                        .getKeyspace("movie")
                        .flatMap(ks -> ks.getUserDefinedType("cinema"))
                        .orElseThrow(IllegalStateException::new);
        TypeCodec<UdtValue> typeCodec = codecRegistry.codecFor(cinemaUdt);
        CinemaCodec cinemaCodec = new CinemaCodec(typeCodec);
        ((MutableCodecRegistry) codecRegistry).register(cinemaCodec);
    }
}
