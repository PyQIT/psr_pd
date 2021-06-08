import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

public class CinemaCodec extends MappingCodec<UdtValue, Cinema> {


    public CinemaCodec(@NonNull TypeCodec<UdtValue> innerCodec) {
        super(innerCodec, GenericType.of(Cinema.class));
    }


    @NonNull
    @Override
    public UserDefinedType getCqlType() {
        return (UserDefinedType) super.getCqlType();
    }


    @Nullable
    @Override
    protected Cinema innerToOuter(@Nullable UdtValue cinema) {
        return cinema == null ? null : new Cinema(
                cinema.getString("uniqueID"),
                cinema.getString("address")
        );
    }

    @Nullable
    @Override
    protected UdtValue outerToInner(@Nullable Cinema cinema) {
        return cinema == null ? null : getCqlType().newValue()
                .setString("uniqueID", cinema.getUniqueID())
                .setString("address", cinema.getAddress());

    }
}
