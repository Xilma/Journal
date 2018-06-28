package com.example.android.journal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalAdapterViewHolder> {
    private String[] journalData;

    public JournalAdapter() {

    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new JournalAdapterViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public JournalAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.journal_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new JournalAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param journalAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(JournalAdapterViewHolder journalAdapterViewHolder, int position) {
        String entryForThisDay = journalData[position];
        journalAdapterViewHolder.mJournalTextView.setText(entryForThisDay);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if (null == journalData)
            return 0;
        return journalData.length;
    }

    /**
     * Cache of the children views for a journal list item.
     */
    public class JournalAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mJournalTextView;

        public JournalAdapterViewHolder(View view) {
            super(view);
            mJournalTextView = (TextView) view.findViewById(R.id.tv_journal_data);
        }
    }

    /**
     * This method is used to set the weather forecast on a ForecastAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ForecastAdapter to display it.
     *
     * @param jData The new weather data to be displayed.
     */
    public void setJournalData(String[] jData) {
        journalData = jData;
    }
}
