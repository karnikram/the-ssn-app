package se.par.amsen.ssn.gui.view.mainmenu;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.domain.Category;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * A MainMenuItem is a FrameLayout and the View used to draw the buttons in the main menu, it has a Category
 * which specifies the title, background color and image.
 * <p/>
 * A MainMenuItem can also be created through XML with the defined XML-attributes.
 *
 * @author Par Amsen, www.trixigt.com
 */
public class MainMenuItem extends FrameLayout
{
    public Typeface advFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Adventure.otf");

    public MainMenuItem(Context context, Category category)
    {
        super(context);
        init(context, null, category);
    }

    public MainMenuItem(Context context)
    {
        super(context);
        init(context, null, null);
    }

    public MainMenuItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs, null);
    }

    public MainMenuItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context, attrs, null);
    }

    public void init(Context context, AttributeSet attrs, Category category)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.main_menu_item, this, true);

        FrameLayout iconView = (FrameLayout)findViewById(R.id.frame_menu_item);
        TextView titleView = (TextView) findViewById(R.id.txt_main_menu_item);

        titleView.setTypeface(advFont);

        if (attrs != null)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MainMenuItem, 0, 0);

            Drawable itemIcon = a.getDrawable(R.styleable.MainMenuItem_mainMenuItemIcon);
            float itemIconAlpha = a.getFloat(R.styleable.MainMenuItem_mainMenuItemIconAlpha, 1);
            String itemTitle = a.getString(R.styleable.MainMenuItem_mainMenuItemTitle);

            a.recycle();

            iconView.setBackground(itemIcon);
            iconView.setAlpha(itemIconAlpha);
            titleView.setText(itemTitle);
        }
        else
            if (category != null)
            {
                iconView.setBackground(category.getCategoryIcon());
                iconView.setAlpha(Category.ICON_ALPHA);
                titleView.setText(category.getCategoryName());
                setBackgroundColor(category.getCategoryBackgroundColor());
                setId(category.getId());
            }
    }

}
