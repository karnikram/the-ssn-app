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
    private String place;
    private String day;
    private List<List<String>> menuList, breakfastMenu, lunchMenu, snacksMenu, dinnerMenu;
    private String[] sections = {"Breakfast", "Lunch", "Snacks", "Dinner"};

    public DiningMenuAdapter(Context context, String place, String day) {
        this.context = context;
        this.place = place;
        this.day = day;

        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        menuList = new ArrayList<>();

        breakfastMenu = databaseHandler.getMessMenu(place, day, "Breakfast");
        lunchMenu = databaseHandler.getMessMenu(place, day, "Lunch");
        snacksMenu = databaseHandler.getMessMenu(place, day, "Snacks");
        dinnerMenu = databaseHandler.getMessMenu(place, day, "Dinner");

        menuList.addAll(breakfastMenu);
        menuList.addAll(lunchMenu);
        menuList.addAll(snacksMenu);
        menuList.addAll(dinnerMenu);
    }

    @Override
    public int getSectionCount() {
        return 4;
    }

    @Override
    public int getItemCount(int section) {
        switch (section) {
            case 0:
                return breakfastMenu.size();
            case 1:
                return lunchMenu.size();
            case 2:
                return snacksMenu.size();
            case 3:
                return dinnerMenu.size();
            default:
                return 0;
        }
    }

    @Override
    public void onBindHeaderViewHolder(DiningMenuViewHolder holder, int section, boolean expanded) {
        holder.title.setText(sections[section]);
        holder.caret.setImageResource(expanded ? R.drawable.ic_collapse : R.drawable.ic_expand);
    }

    @Override
    public void onBindViewHolder(DiningMenuViewHolder holder, int section,
                                 int relativePosition, int absolutePosition) {
        List<List<String>> menuList = new ArrayList();
        switch (section) {
            case 0:
                menuList.addAll(breakfastMenu);
                break;
            case 1:
                menuList.addAll(lunchMenu);
                break;
            case 2:
                menuList.addAll(snacksMenu);
                break;
            case 3:
                menuList.addAll(dinnerMenu);
                break;
        }
        holder.title.setText(menuList.get(relativePosition).get(0));
        if (menuList.get(relativePosition).get(1).equals("Veg"))
            holder.title.setTextColor(context.getResources().getColor(R.color.open));
        else holder.title.setTextColor(context.getResources().getColor(R.color.close));
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
        final ImageView caret;
        final DiningMenuAdapter adapter;

        DiningMenuViewHolder(View itemView, DiningMenuAdapter adapter) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
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
