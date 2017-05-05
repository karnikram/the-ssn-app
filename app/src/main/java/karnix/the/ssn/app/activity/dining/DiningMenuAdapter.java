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
    private boolean isMess;

    private DatabaseHandler databaseHandler;

    private String[] messSections = {"Breakfast", "Lunch", "Snacks", "Dinner"};
    private String[] storesSections = {"Fresh Juice", "Milk Shakes", "Soda", "Tea", "Coffee",
            "Others", "Noodles", "Puff", "Corn", "Kulfi", "Sandwich", "Omlette", "Rice",
            "Chappathi", "Paratha", "Chat"};

    private List<List<List<String>>> messMenuList;
    private List<List<List<String>>> storesMenuList;

    public DiningMenuAdapter(Context context, String place, String day) {
        this.context = context;
        this.isMess = place.contains("Mess");

        databaseHandler = new DatabaseHandler(context);
        messMenuList = new ArrayList<>();
        storesMenuList = new ArrayList<>();

        if (isMess)
            for (String messSection : messSections)
                messMenuList.add(databaseHandler.getMessMenu(place, day, messSection));
        else
            for (String storesSection : storesSections)
                storesMenuList.add(databaseHandler.getStoresMenu(place, storesSection));
    }

    @Override
    public int getSectionCount() {
        if (isMess) return messSections.length;
        else return storesSections.length;
    }

    @Override
    public int getItemCount(int section) {
        if (isMess) return messMenuList.get(section).size();
        else return storesMenuList.get(section).size();
    }

    @Override
    public void onBindHeaderViewHolder(DiningMenuViewHolder holder, int section, boolean expanded) {
        if (isMess) holder.title.setText(messSections[section]);
        else holder.title.setText(storesSections[section]);
        holder.caret.setImageResource(expanded ? R.drawable.ic_collapse : R.drawable.ic_expand);
    }

    @Override
    public void onBindViewHolder(DiningMenuViewHolder holder, int section,
                                 int relativePosition, int absolutePosition) {
        List<List<String>> menuList;
        if (isMess) {
            menuList = messMenuList.get(section);

            if (menuList.get(relativePosition).get(1).equals("Veg"))
                holder.title.setTextColor(context.getResources().getColor(R.color.open));
            else holder.title.setTextColor(context.getResources().getColor(R.color.close));

            holder.price.setVisibility(View.GONE);
        } else menuList = storesMenuList.get(section);

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
