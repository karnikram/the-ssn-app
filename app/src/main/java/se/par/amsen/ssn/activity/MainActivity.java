
package se.par.amsen.ssn.activity;

import java.util.ArrayList;
import java.util.List;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.domain.Category;
import se.par.amsen.ssn.gui.view.category.MainCategoryView;
import se.par.amsen.ssn.gui.view.mainmenu.MainMenuView;
import se.par.amsen.ssn.service.categorydata.ShopDataService;
import se.par.amsen.ssn.service.categorydata.mock.CategoryDataService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity
{

    private MainMenuView mainMenuView;
    private MainCategoryView mainCategoryView;

    private boolean introPlayed = false;
    //This List contains all the data whom the app presents, i.e. the menu items, the shop items and prices etc.
    private List<Category> categories;

    private ShopDataService shopDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final FrameLayout mainContainer = (FrameLayout) findViewById(R.id.container_activity_main);

        shopDataService = new CategoryDataService();

        categories = new ArrayList<Category>();

        mainMenuView = new MainMenuView(this);
        mainCategoryView = new MainCategoryView(this, mainContainer);

        //fill the category list with demo data, i.e. demo categories and shop items
        categories = shopDataService.getShopData(this);

        //add the demo categories to the main menu
        mainMenuView.addCategoriesToMenu(this, categories);

        //add views to main container
        mainContainer.addView(mainMenuView);

        mainCategoryView.setVisibility(View.GONE);
        mainContainer.addView(mainCategoryView);


        LinearLayout mainMenuItemsContainer = (LinearLayout) mainMenuView.findViewById(R.id.content_main_menu); //ScrollView

        OnClickListener mainMenuListener = new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Category tempCategory = null;
                for (int i = 0; i < categories.size(); i++)
                {
                    tempCategory = categories.get(i);
                    if (tempCategory.getId() == v.getId() && mainCategoryView.getVisibility() != View.VISIBLE)
                    {
                        mainCategoryView.setCurrentCategory(tempCategory, v);
                        mainCategoryView.playIntro(null);
                        break;
                    }
                }
            }
        };

        for (int i = 0; i < mainMenuItemsContainer.getChildCount(); i++)
        {
            mainMenuItemsContainer.getChildAt(i).setOnClickListener(mainMenuListener);
        }

        //Play intro anim when the views have been laid out
        mainContainer.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                if (!introPlayed)
                {
                    mainMenuView.playIntro(null);
                    introPlayed = true;
                }
            }


        });
    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to quit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                      .show();
    }
}




