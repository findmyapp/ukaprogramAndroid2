package no.uka.findmyapp.ukaprogram.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * The ImageCache class can be used to download images and store them in the
 * cache directory of the device. Multiple requests to the same URL will result
 * in a single download, until the cache timeout has passed.
 * @author Thomas Vervest - http://squarewolf.nl/2010/11/android-image-cache/
 */
public class ImageCache {

    /**
     * The ImageCallback interface defines a single method used to pass an image
     * back to the calling object when it has been loaded.
     */
    public static interface ImageCallback {
        /**
         * The onImageLoaded method is called by the ImageCache when an image
         * has been loaded.
         * @param image The requested image in the form of a Drawable object.
         * @param url The originally requested URL
         */
        void onImageLoaded(Drawable image, String url);
    }

    private static ImageCache _instance = null;

    /**
     * Gets the singleton instance of the ImageCache.
     * @return The ImageCache.
     */
    public synchronized static ImageCache getInstance() {
        if (_instance == null) {
            _instance = new ImageCache();
        }
        return _instance;
    }

    private static final long CACHE_TIMEOUT = 60000;
    private final Object _lock = new Object();
    private HashMap<String, WeakReference<Drawable>> _cache;
    private HashMap<String, List<ImageCallback>> _callbacks;

    private ImageCache() {
        _cache = new HashMap<String, WeakReference<Drawable>>();
        _callbacks = new HashMap<String, List<ImageCallback>>();
    }

    private String getHash(String url) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            return new BigInteger(digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            // this should never happen, but just to make sure return the url
            return url;
        }
    }

    private Drawable drawableFromCache(String url, String hash) {
        Drawable d = null;
        synchronized (_lock) {
            if (_cache.containsKey(hash)) {
                WeakReference ref = _cache.get(hash);
                if ( ref != null ) {
                    d = (Drawable)ref.get();
                    if (d == null)
                        _cache.remove(hash);
                }
            }
        }
        return d;
    }

    public Drawable loadSync(String url, Context context) {
    	
        Drawable d = null;
        String hash = getHash(url);
        try {
            d = drawableFromCache(url, getHash(url));

            File f = new File(context.getCacheDir(), hash);
            boolean timeout = f.lastModified() + CACHE_TIMEOUT < new Date().getTime();
                if (d == null || timeout) {
                    if (timeout)
                        f.delete();
                    if (!f.exists())
                    {
                     InputStream is = new URL(url + "?rand=" + new Random().nextInt()).openConnection().getInputStream();
                     if (f.createNewFile())
                     {
                         FileOutputStream fo = new FileOutputStream(f);
                         byte[] buffer = new byte[256];
                         int size;
                                                  while ((size = is.read(buffer)) > 0) {
                            fo.write(buffer, 0, size);
                        }
                        fo.flush();
                        fo.close();
                    }
                }
                d = Drawable.createFromPath(f.getAbsolutePath());

                synchronized (_lock) {
                    _cache.put(hash, new WeakReference(d));
                }
            }
        } catch (Exception ex) {
            Log.e(getClass().getName(), ex.getMessage(), ex);
        }
        return d;
    }

    /**
     * Loads an image from the passed URL and calls the callback method when
     * the image is done loading.
     * @param url The URL of the target image.
     * @param callback A ImageCallback object to pass the loaded image. If null,
     * the image will only be pre-loaded into the cache.
     * @param context The context of the new Drawable image.
     */
    public void loadAsync(final String url, final ImageCallback callback, final Context context) {
        final String hash = getHash(url);

        synchronized (_lock) {
            List callbacks = _callbacks.get(hash);
            if (callbacks != null) {
                if (callback != null)
                    callbacks.add(callback);
                return;
            }

            callbacks = new ArrayList();
            if (callback != null)
                callbacks.add(callback);
            _callbacks.put(hash, callbacks);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable d = loadSync(url, context);
                List<ImageCallback> callbacks;

                synchronized (_lock) {
                    callbacks = _callbacks.remove(hash);
                }

                for (ImageCallback iter : callbacks) {
                    iter.onImageLoaded(d, url);
                }
            }
        }, "ImageCache loader: " + url).start();
    }
}