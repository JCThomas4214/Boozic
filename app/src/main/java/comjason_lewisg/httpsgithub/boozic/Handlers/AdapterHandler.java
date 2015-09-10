package comjason_lewisg.httpsgithub.boozic.Handlers;

import android.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import comjason_lewisg.httpsgithub.boozic.Models.TopTensModel;
import comjason_lewisg.httpsgithub.boozic.R;

public class AdapterHandler extends RecyclerView.Adapter<AdapterHandler.ListItemViewHolder> {
    private List<TopTensModel> items;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView label;

        // each data item is just a string in this case
        public ListItemViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.txt_label_item);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterHandler(List<TopTensModel> modeldata) {
        if (modeldata == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        items = modeldata;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterHandler.ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // create a new view
        View itemView = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.top_ten_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        return new AdapterHandler.ListItemViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TopTensModel model = items.get(position);
        viewHolder.label.setText(model.label);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

}
