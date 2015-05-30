package se.par.amsen.ssn.gui.view.mainmenu;

import java.util.List;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.domain.Category;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * A MainMenuView is used as the entry-point of the application (from a users point of view).
 *
 * @author Par Amsen, www.trixigt.com
 */
public class MainMenuView extends FrameLayout
{

    private ViewGroup mainMenuItemsContainer;

    public MainMenuView(Context context)
    {
        super(context);
        init(context);
    }

    public void init(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.main_menu, this, true);

        mainMenuItemsContainer = (ViewGroup) findViewById(R.id.content_main_menu);
    }

    /**
     * Create MainMenuItem Views for each item in the supplied List of Categories and add them
     * to the main menu.
     *
     * @param context
     * @param categories
     */
    public void addCategoriesToMenu(Context context, List<Category> categories)
    {
        for (int i = 0; i < categories.size(); i++)
        {
            mainMenuItemsContainer.addView(new MainMenuItem(context, categories.get(i)));
        }
    }

    /**
     * Play the intro for the main menu view, i.e. slide in the main menu buttons and
     * fade/slide in the category titles.
     *
     * @param listener
     */
    public void playIntro(AnimationListener listener)
    {
        for (int i = 0; i < mainMenuItemsContainer.getChildCount(); i++)
        {
            MainMenuItem item = (MainMenuItem) mainMenuItemsContainer.getChildAt(i);
            final TextView title = (TextView) item.findViewById(R.id.txt_main_menu_item);
            title.setAlpha(0);
            title.setY(title.getY() + 30);

            item.setX(item.getWidth());

            item.animate()
                    .translationX(0)
                    .setInterpolator(new DecelerateInterpolator(1.2f))
                    .setStartDelay(i * 30)
                    .setDuration(800)
                    .setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            title.animate()
                                    .alpha(1)
                                    .setDuration(200)
                                    .translationYBy(-30)
                                    .start();
                        }
                    })
                    .start();
        }

        }


}
