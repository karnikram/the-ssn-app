package se.par.amsen.ssn.service.categorydata;

import java.util.List;

import se.par.amsen.ssn.domain.Category;

import android.content.Context;

public interface ShopDataService
{
    public List<Category> getShopData(Context context);
}
