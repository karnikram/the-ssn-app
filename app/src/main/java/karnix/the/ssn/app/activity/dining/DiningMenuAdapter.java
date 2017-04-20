package karnix.the.ssn.app.activity.dining;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.afollestad.sectionedrecyclerview.SectionedViewHolder;

import karnix.the.ssn.ssnmachan.R;

public class DiningMenuAdapter extends SectionedRecyclerViewAdapter<DiningMenuAdapter.DiningMenuViewHolder> {

    private String[] sections;

    public DiningMenuAdapter(String[] sections) {
        this.sections = sections;
    }

    @Override
    public int getSectionCount() {
        return 4;
    }

    @Override
    public int getItemCount(int section) {
        switch (section) {
            default:
                return 4;
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
        holder.title.setText("Item " + absolutePosition);
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
