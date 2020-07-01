package by.gradomski.apartments.service.validator;

import by.gradomski.apartments.exception.ImageValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ImageValidator {
    private static final Logger log = LogManager.getLogger();
    private static final String[] formats = new String[]{"JPG", "GIF", "JPEG", "PNG"};
    private static final Set<String> formatsSet = new HashSet<>(Arrays.asList(formats));

    public static boolean isValid(InputStream inputStream) throws ImageValidationException {
        ImageInputStream imageInputStream;
        try {
            imageInputStream = ImageIO.createImageInputStream(inputStream);
        } catch (IOException e) {
            throw new ImageValidationException(e);
        }
        Iterator<ImageReader> readerIterator = ImageIO.getImageReaders(imageInputStream);
        if (readerIterator.hasNext()) {
            ImageReader reader = readerIterator.next();
            String imageFormat = null;
            try {
                imageFormat = reader.getFormatName();
            } catch (IOException e) {
                throw new ImageValidationException(e);
            }
            log.debug("input stream format: " + imageFormat);
            return formatsSet.contains(imageFormat.toUpperCase());
        } else {
            log.info("input stream has invalid type");
            return false;
        }
    }
}
