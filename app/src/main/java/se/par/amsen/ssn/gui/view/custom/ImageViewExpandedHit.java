package se.par.amsen.ssn.gui.view.custom;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An ImageViewExpandedHit expands the hit box (hitRect) of the ImageView with by a fixed constant
 *
 * @author Par Amsen, www.trixigt.com
 */
public class ImageViewExpandedHit extends ImageView
{

    private static final int expandPixels = 30;

    public ImageViewExpandedHit(Context context, AttributeSet attrs,
                                int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public ImageViewExpandedHit(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ImageViewExpandedHit(Context context)
    {
        super(context);
    }

    @Override
    public void getHitRect(Rect outRect)
    {
        super.getHitRect(outRect);

        if (outRect == null)
        {
            return;
        }

        outRect.top -= expandPixels;
        outRect.bottom += expandPixels;
        outRect.left -= expandPixels;
        outRect.right += expandPixels;
    }

}
