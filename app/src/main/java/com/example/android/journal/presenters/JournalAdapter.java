package com.example.android.journal.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.journal.view.EditActivity;
import com.example.android.journal.R;
import com.example.android.journal.view.ViewActivity;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private List<RecyclerItem> listItems;
    private Context mContext;

    public JournalAdapter(List<RecyclerItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RecyclerItem itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.getTitle());
        holder.txtDescription.setText(itemList.getDescription());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence titleEdit = holder.txtTitle.getText();
                CharSequence descriptionEdit = holder.txtDescription.getText();

                Intent viewJournal = new Intent (mContext, ViewActivity.class);
                viewJournal.putExtra("Title", titleEdit);
                viewJournal.putExtra("Description", descriptionEdit);
                mContext.startActivity(viewJournal);
            }
        });

        holder.txtOptionDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display option menu
                PopupMenu popupMenu = new PopupMenu(mContext, holder.txtOptionDigit);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.item_edit:
                                CharSequence titleEdit = holder.txtTitle.getText();
                                CharSequence descriptionEdit = holder.txtDescription.getText();

                                Intent edit = new Intent (mContext, EditActivity.class);
                                edit.putExtra("Title", titleEdit);
                                edit.putExtra("Description", descriptionEdit);
                                mContext.startActivity(edit);
                                break;

                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDescription;
        TextView txtOptionDigit;
        LinearLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtOptionDigit = itemView.findViewById(R.id.txtOptionDigit);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}