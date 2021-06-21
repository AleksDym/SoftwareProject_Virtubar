package se1app.datatypes;

import javax.persistence.Embeddable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import se1app.exceptions.InvalidEmailException;
import se1app.exceptions.InvalidImageException;

@Embeddable
public class ImageType {

    private String _image;


    //private static final String imageRegex = ".*\\.{1}(gif|jpeg|tiff|png|webp|bmp)$";
    private static final String imageRegex = "[\\w,\\s-\\.]*[\\w,\\s-]\\.{1}(gif|jpeg|tiff|png|webp|bmp)$";


    @Contract(pure = true)
    private static boolean isValid(@NotNull String image) {
        return image.matches(imageRegex);
    }

    public ImageType(String image) throws InvalidEmailException {
        if (isValid(image)) _image = image;
        else {
            throw new InvalidImageException(image);
        }
    }

    public ImageType() {
    }

    public String getImage() {
        return _image;
    }

    public void setImage(String image) {
        this._image = image;
    }
}
