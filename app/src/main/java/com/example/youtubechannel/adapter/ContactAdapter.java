package com.example.youtubechannel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.base.BaseRecyclerViewAdapter;
import com.example.youtubechannel.adapter.base.Releasable;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.injection.ActivityContext;
import com.example.youtubechannel.models.Contact;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>
        implements Releasable {

    private Context context;
    private ArrayList<Contact> listContact;
    private static ICallPhone iCallPhone;

    public interface ICallPhone {
        void onClickCallPhone();
    }

    @Inject
    public ContactAdapter(@ActivityContext Context context, ICallPhone iCallPhone) {
        this.context = context;
        ContactAdapter.iCallPhone = iCallPhone;
    }

    @NotNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return ContactViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.bindData(context, listContact.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == listContact ? 0 : listContact.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setListData(ArrayList<Contact> list) {
        this.listContact = list;
        notifyDataSetChanged();
    }

    @Override
    public void release() {
        context = null;
    }

    public static class ContactViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Contact> {

        @BindView(R.id.img_contact)
        ImageView imgContact;

        @BindView(R.id.tv_contact)
        TextView tvContact;

        @BindView(R.id.layout_item)
        LinearLayout layoutItem;

        public ContactViewHolder(View itemView) {
            super(itemView);
        }

        public static ContactViewHolder create(ViewGroup parent) {
            return new ContactViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_contact, parent, false));
        }

        @Override
        public void bindData(Context context, Contact contact, int position) {
            if (contact != null) {
                imgContact.setImageResource(contact.getImage());
                switch (contact.getId()) {
                    case Contact.FACEBOOK:
                        tvContact.setText(context.getString(R.string.label_facebook));
                        break;

                    case Contact.HOTLINE:
                        tvContact.setText(context.getString(R.string.label_call));
                        break;

                    case Contact.GMAIL:
                        tvContact.setText(context.getString(R.string.label_gmail));
                        break;

                    case Contact.SKYPE:
                        tvContact.setText(context.getString(R.string.label_skype));
                        break;

                    case Contact.YOUTUBE:
                        tvContact.setText(context.getString(R.string.label_youtube));
                        break;

                    case Contact.ZALO:
                        tvContact.setText(context.getString(R.string.label_zalo));
                        break;
                }

                layoutItem.setOnClickListener(v -> {
                    switch (contact.getId()) {
                        case Contact.FACEBOOK:
                            GlobalFunction.onClickOpenFacebook(context);
                            break;

                        case Contact.HOTLINE:
                            iCallPhone.onClickCallPhone();
                            break;

                        case Contact.GMAIL:
                            GlobalFunction.onClickOpenGmail(context);
                            break;

                        case Contact.SKYPE:
                            GlobalFunction.onClickOpenSkype(context);
                            break;

                        case Contact.YOUTUBE:
                            GlobalFunction.onClickOpenYoutubeChannel(context);
                            break;

                        case Contact.ZALO:
                            GlobalFunction.onClickOpenZalo(context);
                            break;
                    }
                });
            }
        }
    }
}