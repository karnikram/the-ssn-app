package se.par.amsen.ssn.service.categorydata.mock;

import java.util.ArrayList;
import java.util.List;

import se.par.amsen.ssnmachan.R;
import se.par.amsen.ssn.domain.Category;
import se.par.amsen.ssn.service.categorydata.ShopDataService;

import android.content.Context;
import android.content.res.Resources;

/**
 * Mock service/repo implementation for shop data
 *
 * @author Par Amsen, www.trixigt.com
 */
public class CategoryDataService implements ShopDataService
{
    @Override
    public List<Category> getShopData(Context context)
    {

        Resources resources = context.getResources();
        List<Category> categories = new ArrayList<Category>();

        // Create the categories and assign icons/image, titles and background colors to be used throughout the app, the icon/image
        // is shown in the main menu.
        Category categoryalerts = new Category("Alerts", resources.getColor(R.color.category_alerts), resources.getDrawable(R.drawable.ico_alerts), 0);
        Category categoryBuses = new Category("Buses", resources.getColor(R.color.category_buses), resources.getDrawable(R.drawable.ico_buses), 1);
        Category categoryDining = new Category("Dining", resources.getColor(R.color.category_dining), resources.getDrawable(R.drawable.ico_dining), 2);
       Category categoryTT = new Category("Time Tables", resources.getColor(R.color.category_tt), resources.getDrawable(R.drawable.ico_photos), 4);
        Category categoryAbout = new Category("About", resources.getColor(R.color.category_about), resources.getDrawable(R.drawable.ico_credits), 5);

        categories.add(categoryalerts);
        categories.add(categoryBuses);
        categories.add(categoryDining);
        categories.add(categoryTT);
        categories.add(categoryAbout);

        return categories;
    }
}
