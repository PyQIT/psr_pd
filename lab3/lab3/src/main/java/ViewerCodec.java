import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.MappingCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

public class ViewerCodec extends MappingCodec<UdtValue, Viewer> {


    public ViewerCodec(@NonNull TypeCodec<UdtValue> innerCodec) {
        super(innerCodec, GenericType.of(Viewer.class));
    }


    @NonNull
    @Override
    public UserDefinedType getCqlType() {
        return (UserDefinedType) super.getCqlType();
    }


    @Nullable
    @Override
    protected Viewer innerToOuter(@Nullable UdtValue viewer) {
        return viewer == null ? null : new Viewer(
                viewer.getString("name"),
                viewer.getString("surname")
        );
    }

    @Nullable
    @Override
    protected UdtValue outerToInner(@Nullable Viewer viewer) {
        return viewer == null ? null : getCqlType().newValue()
                .setString("name", viewer.getName())
                .setString("surname", viewer.getSurname());
    }
}
