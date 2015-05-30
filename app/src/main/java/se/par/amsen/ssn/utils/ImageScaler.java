package se.par.amsen.ssn.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Util class for handling and scaling images
 *
 * @author Par Amsen, www.trixigt.com
 */
public class ImageScaler
{
    /**
     * Load scaled versions of drawables to save memory, i.e. the main menu button images etc.
     * does not need to use 100% size.
     *
     * @param resourceId
     * @param reqSideLength
     * @return Scaled Drawable
     */
    public static Drawable getSquareScaledDrawable(Context context, int resourceId, int reqSideLength)
    {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqSideLength, reqSideLength);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return new BitmapDrawable(context.getResources(), BitmapFactory.decodeResource(context.getResources(), resourceId, options));
    }

    /**
     * Calculate which factor the image should be downsampled with to fit the bounds, logic from the official
     * Android-dev site.
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
