import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import se1app.datatypes.ImageType;
import se1app.exceptions.InvalidImageException;


public class ImageTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "ghdhvngshgh.jpeg",
            "iy7jfw3vtn93.png",
            "745.8nvt.tiff",
    })

    public void createImageFromStringSuccess(String image) {
        new ImageType(image);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            ".jpeg",
            "ghdhvngshgh..jpeg",
            "iy7jfw3vtn93.pn",
            "745.8nvt.ti",
            "745.8nvt......"
    })
    public void createImageFromStringFail(String image) {
        Assertions.assertThrows(
                InvalidImageException.class,
                () -> new ImageType(image),
                "'"+image+"' is no valid image extension!"
        );
    }
}


