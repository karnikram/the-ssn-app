package karnix.the.ssn.app.activity.dining;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.afollestad.sectionedrecyclerview.SectionedViewHolder;

import java.util.ArrayList;
import java.util.List;

import karnix.the.ssn.app.model.DatabaseHandler;
import karnix.the.ssn.app.utils.LogHelper;
import karnix.the.ssn.ssnmachan.R;

public class DiningMenuAdapter extends SectionedRecyclerViewAdapter<DiningMenuAdapter.DiningMenuViewHolder> {
    private static final String TAG = LogHelper.makeLogTag(DiningMenuAdapter.class);

    private Context context;
    private int is;

    private DatabaseHandler databaseHandler;

    private String[] messSections = {"Breakfast", "Lunch", "Snacks", "Dinner"};
    private String[] storesSections = {"Fresh Juice", "Milk Shakes", "Soda", "Tea", "Coffee",
            "Others", "Noodles", "Puff", "Corn", "Kulfi", "Sandwich", "Omlette", "Rice",
            "Chappathi", "Paratha", "Chat"};

    private String[] snowCubeSections = {"Softies Cone", "Delights", "Sundae Mania", "Sundae & Fantasies", "Jelly Jellose",
            "Snow Specials", "Ice Cream Shakes", "Sandwiches", "Crispy Snacks", "Wraps", "Sweet Corn", "Pop Corn"};

    private String[] tuttiSections = {"South Indian", "Snack", "Manchurian", "Indian", "Parotha", "Chinese",
            "Biryani"};

    private List<List<List<String>>> messMenuList;
    private List<List<List<String>>> storesMenuList;
    private List<List<List<String>>> snowCubeList;
    private List<List<List<String>>> tuttiList;

    public DiningMenuAdapter(Context context, String place, String day) {
        this.context = context;
        if (place.contains("Mess"))
            this.is = 0;

        if (place.contains("Canteen"))
            this.is = 1;

        if (place.contains("Snow"))
            this.is = 2;

        if (place.contains("Tutti"))
            this.is = 3;

        databaseHandler = new DatabaseHandler(context);
        messMenuList = new ArrayList<>();
        storesMenuList = new ArrayList<>();
        snowCubeList = new ArrayList<>();
        tuttiList = new ArrayList<>();

        if (is == 0)
            for (String messSection : messSections)
                messMenuList.add(databaseHandler.getMessMenu(place, day, messSection));
        else if (is == 1)
            for (String storesSection : storesSections)
                storesMenuList.add(databaseHandler.getStoresMenu(place, storesSection));

        else if (is == 2)
            for (String snowCubeSection : snowCubeSections)
                snowCubeList.add(databaseHandler.getSnowMenu(place, snowCubeSection));

        else
            for (String tuttiSection : tuttiSections)
                tuttiList.add(databaseHandler.getSnowMenu(place, tuttiSection));
    }

    @Override
    public int getSectionCount() {
        if (is == 0) return messSections.length;
        else if (is == 1) return storesSections.length;
        else if (is == 2) return snowCubeSections.length;
        else return tuttiSections.length;
    }

    @Override
    public int getItemCount(int section) {
        if (is == 0) return messMenuList.get(section).size();
        else if (is == 1) return storesMenuList.get(section).size();
        else if (is == 2) return snowCubeList.get(section).size();
        else return tuttiList.get(section).size();
    }

    @Override
    public void onBindHeaderViewHolder(DiningMenuViewHolder holder, int section, boolean expanded) {
        if (is == 0) holder.title.setText(messSections[section]);
        else if (is == 1) holder.title.setText(storesSections[section]);
        else if (is == 2) holder.title.setText(snowCubeSections[section]);
        else holder.title.setText(tuttiSections[section]);
        holder.caret.setImageResource(expanded ? R.drawable.ic_collapse : R.drawable.ic_expand);
    }

    @Override
    public void onBindViewHolder(DiningMenuViewHolder holder, int section,
                                 int relativePosition, int absolutePosition) {
        List<List<String>> menuList;
        if (is == 0) {
            menuList = messMenuList.get(section);

            if (menuList.get(relativePosition).get(1).equals("Veg"))
                holder.title.setTextColor(context.getResources().getColor(R.color.open));
            else holder.title.setTextColor(context.getResources().getColor(R.color.close));

            holder.price.setVisibility(View.GONE);
        } else if (is == 1) menuList = storesMenuList.get(section);

        else if (is == 2) menuList = snowCubeList.get(section);

        else menuList = tuttiList.get(section);

        holder.title.setText(menuList.get(relativePosition).get(0));
        holder.price.setText(menuList.get(relativePosition).get(1));
    }

    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        if (section == 1) {
            return 0; // VIEW_TYPE_HEADER is -2, VIEW_TYPE_ITEM is -1. You can return 0 or greater.
        }
        return super.getItemViewType(section, relativePosition, absolutePosition);
    }

    @Override
    public DiningMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                layout = R.layout.item_dining_header;
                break;

            case VIEW_TYPE_ITEM:
                layout = R.layout.item_dining_item;
                break;

            default:
                layout = R.layout.item_dining_item;
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new DiningMenuViewHolder(v, this);
    }

    static class DiningMenuViewHolder extends SectionedViewHolder implements View.OnClickListener {

        final TextView title;
        final TextView price;
        final ImageView caret;
        final DiningMenuAdapter adapter;

        DiningMenuViewHolder(View itemView, DiningMenuAdapter adapter) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.price = (TextView) itemView.findViewById(R.id.textView_itemDining_price);
            this.caret = (ImageView) itemView.findViewById(R.id.caret);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (isHeader()) adapter.toggleSectionExpanded(getRelativePosition().section());
        }
    }
}
