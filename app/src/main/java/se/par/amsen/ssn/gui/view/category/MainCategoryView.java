package se.par.amsen.ssn.gui.view.category;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.Fragments.AboutFragment;
import se.par.amsen.ssn.Fragments.AlertsFragment;
import se.par.amsen.ssn.Fragments.BusFragment;
import se.par.amsen.ssn.Fragments.DiningFragment;
import se.par.amsen.ssn.Fragments.EventsFragment;
import se.par.amsen.ssn.Fragments.TimeTableFragment;
import se.par.amsen.ssn.domain.Category;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.app.FragmentManager;
import android.widget.ImageView;


/**
 * A MainCategoryView is a FrameLayout and used to show and animate each category, it has a GridView
 * that shows each "shop item".
 *
 * @author Par Amsen, www.trixigt.com
 */
public class MainCategoryView extends FrameLayout
{

    protected static final String TAG = "MainCategoryView";
    private View container;
    private Category currentCategory;
    private FrameLayout contentContainer;
    private Context context;
    private Activity activity;
    private int systemShortAnimTime;
    private boolean animateClipping = false;

    private Rect rect;


    private View currentMainMenuCategoryView;

    private View root;
    private DisplayMetrics dm;
    private ImageView navigationImageView;

    public MainCategoryView(Context context, View root)
    {
        super(context);
        init(context, root);
    }

    public void init(final Context context, View root)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.main_category, this, true);
        this.root = root;
        this.context = context;
        this.activity = (Activity) context;
        dm = context.getResources().getDisplayMetrics();
        systemShortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

        container = findViewById(R.id.container_main_category);
        contentContainer = (FrameLayout) findViewById(R.id.container_main_category_content);

        navigationImageView = (ImageView) container.findViewById(R.id.img_main_category_navigation);

        navigationImageView.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                switchNavigationIcon(getResources().getDrawable(R.drawable.ico_home));
                playOutro();
            }

        });
    }

    /**
     * Switch the top navigation icon with a rotation transition.
     *
     * @param icon
     */
    private void switchNavigationIcon(final Drawable icon)
    {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator rotateIn = ObjectAnimator.ofFloat(navigationImageView, View.ROTATION_Y, 0, 90);
        ObjectAnimator rotateOut = ObjectAnimator.ofFloat(navigationImageView, View.ROTATION_Y, 90, 0);

        rotateIn.setInterpolator(new AccelerateInterpolator());
        rotateOut.setInterpolator(new DecelerateInterpolator());

        rotateIn.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                navigationImageView.setImageDrawable(icon);
            }
        });

        set.playSequentially(rotateIn, rotateOut);
        set.setDuration(systemShortAnimTime);

        set.start();
    }

    public void setSectionContent()
    {
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (currentCategory.getId())
        {
            case 0:
                ft.replace(R.id.container_main_category_content, new AlertsFragment());
                ft.commit();
                fm.executePendingTransactions();
                break;
            case 1:
                ft.replace(R.id.container_main_category_content, new BusFragment());
                ft.commit();
                fm.executePendingTransactions();
                break;
            case 2:
                ft.replace(R.id.container_main_category_content, new DiningFragment());
                ft.commit();
                fm.executePendingTransactions();
                break;
            case 3:
                ft.replace(R.id.container_main_category_content, new EventsFragment());
                ft.commit();
                fm.executePendingTransactions();
                break;
            case 4:
                ft.replace(R.id.container_main_category_content,new TimeTableFragment());
                ft.commit();
                fm.executePendingTransactions();
                break;
            case 5:
                ft.replace(R.id.container_main_category_content,new AboutFragment());
                ft.commit();
                fm.executePendingTransactions();
                break;
        }
    }


    /**
     * Set which Category to present, changing Category updates the background color and
     * GridView with the new CategoryItems to display.
     *
     * @param category
     * @param mainMenuCategoryView
     */
    public void setCurrentCategory(Category category, View mainMenuCategoryView)
    {
        container.setBackgroundColor(category.getCategoryBackgroundColor());
        currentCategory = category;
        currentMainMenuCategoryView = mainMenuCategoryView;
    }

    /**
     * Play the intro animations/transitions, i.e. expanding the View from the bounds
     * of the clicked button in the main menu, expand the content View from the upper left and
     * perform a cool transition on the Views in the GridView.
     *
     * @param listener
     */
    public void playIntro(AnimatorListener listener)
    {
        setVisibility(VISIBLE);

        //bounds of the main-menu button to expand from
        final Rect startRect = new Rect();
        //bounds of this View to expand to
        final Rect endRect = new Rect();

        navigationImageView.setAlpha(0f);

        //assign the bounds of the main menu button to startRect
        currentMainMenuCategoryView.getGlobalVisibleRect(startRect);
        //assign bounds of endRect to cover the whole Window area
        endRect.set(0, 0, root.getWidth(), root.getHeight());

        // calculate the offset from the top of the Window, i.e. the ActionBars height.
        int topOffset = dm.heightPixels - root.getMeasuredHeight();

        //assign offset to the startRect to get correct bounds of the main menu button
        // opening this category
        startRect.offset(0, -(topOffset));

        //assign the startRect to the rect used to draw this View in dispatchDraw(Canvas)
        rect = startRect;

        //when animateClipping is true the Rect rect is used to draw this View in dispatchDraw(Canvas)
        animateClipping = true;


        //init objects to perform animations and transitions
        PropertyValuesHolder holderTop = PropertyValuesHolder.ofInt("top", startRect.top, endRect.top);
        PropertyValuesHolder holderBottom = PropertyValuesHolder.ofInt("bottom", startRect.bottom, endRect.bottom);

        final ValueAnimator clipRectAnim = ValueAnimator.ofPropertyValuesHolder(holderTop, holderBottom);
        ObjectAnimator containerAlphaAnim = ObjectAnimator.ofFloat(this, View.ALPHA, 0, 1);
        final ObjectAnimator navigationAlphaAnim = ObjectAnimator.ofFloat(navigationImageView, View.ALPHA, 0, 1);

        contentContainer.setPivotX(0f);
        contentContainer.setPivotY(0f);
        contentContainer.setScaleX(0f);
        contentContainer.setScaleY(0f);

        final ObjectAnimator contentContainerXAnim = ObjectAnimator.ofFloat(contentContainer, View.SCALE_X, 0f, 1f);
        final ObjectAnimator contentContainerYAnim = ObjectAnimator.ofFloat(contentContainer, View.SCALE_Y, 0f, 1f);

        clipRectAnim.setDuration(200);
        clipRectAnim.setStartDelay(100);
        containerAlphaAnim.setDuration(200);
        navigationAlphaAnim.setDuration(200);

        contentContainerXAnim.setDuration(200);
        contentContainerYAnim.setDuration(200);

        // on each update/frame of the clipRectAnim, update the field Rect rect with the new top/bottom coordinates
        clipRectAnim.addUpdateListener(new AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int top = ((Integer) clipRectAnim.getAnimatedValue("top")).intValue();
                int bottom = ((Integer) clipRectAnim.getAnimatedValue("bottom")).intValue();

                rect.top = top;
                rect.bottom = bottom;
                // call invalidate() to perform a call to onDraw(Canvas) and dispatchDraw(Canvas) in the near future,
                // dispatchDraw(Canvas) is called before the View draws its children.
                invalidate();
            }
        });

        clipRectAnim.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                animateClipping = false;
                rect = endRect;
                //perform the navigation icon animation, i.e. rotate it 180 degrees
                switchNavigationIcon(getResources().getDrawable(R.drawable.ico_home));

            }

            @Override
            public void onAnimationCancel(Animator animation)
            {
                animateClipping = false;
                rect = endRect;
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(containerAlphaAnim, clipRectAnim);

        set.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator arg0)
            {

                AnimatorSet setContentContainer = new AnimatorSet();
                setContentContainer.playTogether(contentContainerXAnim, contentContainerYAnim, navigationAlphaAnim);
                setContentContainer.setStartDelay(50);
                setContentContainer.start();
            }
        });

        set.start();
        setSectionContent();
    }


    /**
     * dispatchDraw(Canvas) is overriden in order to perform the intro/outro expand/minimize, on each
     * call to onDraw(Canvas) dispatchDraw(Canvas) is called before the View draws its children.
     * View animations.
     */
    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        if (animateClipping)
        {
            canvas.save();
            canvas.clipRect(rect);
        }

        super.dispatchDraw(canvas);

        if (animateClipping)
        {
            canvas.restore();

        }
    }

    /**
     * Play the outro animation, i.e. minimize the View to the button for this MainCategoryView
     * in the main menu View etc.
     */
    public void playOutro()
    {
        ObjectAnimator anim = ObjectAnimator.ofFloat(contentContainer, View.ALPHA, 1f, 0f);
        anim.setDuration(systemShortAnimTime).addListener(new AnimatorListenerAdapter()
        {

            @Override
            public void onAnimationEnd(Animator animation)
            {

                final Rect startRect = new Rect();
                final Rect endRect = new Rect();
                Point globalOffset = new Point();

                currentMainMenuCategoryView.getGlobalVisibleRect(endRect, globalOffset);
                startRect.set(0, 0, root.getWidth(), root.getHeight());

                int topOffset = dm.heightPixels - root.getMeasuredHeight();

                endRect.offset(0, -(topOffset));

                rect = startRect;

                animateClipping = true;

                PropertyValuesHolder holderTop = PropertyValuesHolder.ofInt("top", startRect.top, endRect.top);
                PropertyValuesHolder holderBottom = PropertyValuesHolder.ofInt("bottom", startRect.bottom, endRect.bottom);

                final ValueAnimator clipRectAnim = ValueAnimator.ofPropertyValuesHolder(holderTop, holderBottom);
                ObjectAnimator containerAlphaAnim = ObjectAnimator.ofFloat(MainCategoryView.this, View.ALPHA, 1, 0);

                clipRectAnim.setDuration(150);
                containerAlphaAnim.setStartDelay(120);
                containerAlphaAnim.setDuration(30);

                clipRectAnim.addUpdateListener(new AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation)
                    {
                        int top = ((Integer) clipRectAnim.getAnimatedValue("top")).intValue();
                        int bottom = ((Integer) clipRectAnim.getAnimatedValue("bottom")).intValue();

                        rect.top = top;
                        rect.bottom = bottom;
                        invalidate();
                    }
                });

                clipRectAnim.addListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        animateClipping = false;
                        rect = endRect;

                        setVisibility(View.GONE);
                        contentContainer.setAlpha(1f);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation)
                    {
                        animateClipping = false;
                        rect = endRect;

                        setVisibility(View.GONE);
                        contentContainer.setAlpha(1f);
                    }
                });

                clipRectAnim.start();
                containerAlphaAnim.start();


            }
        });

        anim.start();
    }

}
