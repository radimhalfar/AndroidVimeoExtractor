package radim.halfar.vimeo.extractor.Vimeo.Exceptions;

/**
 * Created by radimhalfar on 03.09.15.
 */
public class VimeoException extends Exception {

    VimeoExceptionEnumType vimeoException;

    public VimeoException(String message, VimeoExceptionEnumType type) {

        super(message);
        vimeoException = type;
    }

    public VimeoExceptionEnumType getType() {
        return vimeoException;
    }

}
