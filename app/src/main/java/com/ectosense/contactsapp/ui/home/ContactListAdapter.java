package com.ectosense.contactsapp.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ectosense.contactsapp.APIData.model.Contact;
import com.ectosense.contactsapp.R;
import com.ectosense.contactsapp.constants.Constants;
import com.ectosense.contactsapp.ui.contactdetail.ContactDetailActivity;

import java.util.ArrayList;

/**
 * Created by Abhilash MB on 25-07-2020.
 */
public class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private ArrayList<Contact> mContactList;
    private static final int SECTION_VIEW = 0;
    private static final int CONTENT_VIEW = 1;
    private Activity mActivity;

    ContactListAdapter(Activity activity, ArrayList<Contact> contactList) {
        this.mActivity = activity;
        this.mContactList = contactList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SECTION_VIEW) {
            return new SectionHeaderViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_contact_header, parent, false));
        }
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        if (mContactList.get(position).getSection()) {
            return SECTION_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (SECTION_VIEW == getItemViewType(position)) {

            SectionHeaderViewHolder sectionHeaderViewHolder = (SectionHeaderViewHolder) holder;
            Contact sectionItem = mContactList.get(position);
            sectionHeaderViewHolder.textHeader.setText(sectionItem.getHeader());
            return;
        }

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Contact contact = ((Contact) mContactList.get(position));
        if(contact.getFavorite()){
            itemViewHolder.imageFavourite.setBackgroundResource(R.drawable.favourite_filled);
        }else{
            itemViewHolder.imageFavourite.setBackgroundResource(R.drawable.favourite);
        }
        itemViewHolder.textName.setText(String.format("%s %s", contact.getFirstname(), contact.getLastname()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailsIntent = new Intent(mActivity, ContactDetailActivity.class);
                detailsIntent.putExtra(Constants.EXTRA_CONTACT_DETAIL, contact);
                //detailsIntent.putExtra(Constants.EXTRA_CONTACT_DETAIL, contact);
                mActivity.startActivity(detailsIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textName;
        private ImageView imageFavourite;

        private ItemViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.tv_contact_name);
            imageFavourite = (ImageView)itemView.findViewById(R.id.iv_favourite);

        }

    }

    public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textHeader;

        private SectionHeaderViewHolder(View itemView) {
            super(itemView);
            textHeader = (TextView) itemView.findViewById(R.id.tv_header_text);
        }
    }
}
