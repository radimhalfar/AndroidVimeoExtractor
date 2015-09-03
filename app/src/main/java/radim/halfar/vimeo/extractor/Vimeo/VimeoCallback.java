package radim.halfar.vimeo.extractor.Vimeo;

import radim.halfar.vimeo.extractor.Vimeo.Exceptions.VimeoException;

/**
 * Created by radimhalfar on 03.09.15.
 */
public interface VimeoCallback {
    public void vimeoURLCallback(String callback);

    public void videoExceptionCallback(VimeoException exceptionCallback);
}
