package com.blink.blink.Chatting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blink.blink.R;
import com.blink.blink.Realm.RealmMessage;
import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;

import static com.blink.blink.Chatting.ChatListActivity.USER_ID;

/**
 * Created by Beomseok on 2016. 12. 31..
 */

public class MessageViewAdapter extends RecyclerView.Adapter<MessageViewAdapter.MessageViewHolder>{
    private Context context;
    private RealmList<RealmMessage> messages;

    public MessageViewAdapter(Context context) {
        this.context = context;
        this.messages = new RealmList<RealmMessage>();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, final int position) {
        RealmMessage obj = messages.get(position);
        holder.data = obj;

        if(position != 0) {
            SimpleDateFormat day = new SimpleDateFormat("yyyy년 MM월 dd일");
            String prev = day.format(messages.get(position - 1).getTime());
            String curr = day.format(obj.getTime());

            if (prev.equals(curr)) {
                holder.dateView.setVisibility(View.GONE);
            }
            else {
                holder.dateView.setVisibility(View.VISIBLE);
                holder.dateView.setText(curr);
            }
        } else{
            holder.dateView.setVisibility(View.GONE);
        }

        CircleImageView imageView;
        TextView textView;
        TextView timeView;
        ImageView stateView;

        if(obj.getUser().getId() == USER_ID) {
            holder.sendMessage.setVisibility(View.VISIBLE);
            holder.receiveMessage.setVisibility(View.GONE);

            imageView = (CircleImageView) holder.sendMessage.findViewById(R.id.sendImageView);
            textView = (TextView) holder.sendMessage.findViewById(R.id.sendTextView);
            timeView = (TextView) holder.sendMessage.findViewById(R.id.sendTimeView);
            stateView = (ImageView) holder.sendMessage.findViewById(R.id.sendStateView);

            textView.setBackgroundResource(R.drawable.message_ballon_send);
            imageView.setImageResource(R.drawable.john_image);

        }else{
            holder.sendMessage.setVisibility(View.GONE);
            holder.receiveMessage.setVisibility(View.VISIBLE);

            imageView = (CircleImageView) holder.receiveMessage.findViewById(R.id.receiveImageView);
            textView = (TextView) holder.receiveMessage.findViewById(R.id.receiveTextView);
            timeView = (TextView) holder.receiveMessage.findViewById(R.id.receiveTimeView);
            stateView = (ImageView) holder.receiveMessage.findViewById(R.id.receiveStateView);

            textView.setBackgroundResource(R.drawable.message_ballon_receive);
            imageView.setImageResource(R.drawable.john_image);

            if (obj.getUser().getImageURL() == null) {
                imageView.setImageResource(R.drawable.mary_image);
            }
            else {
                Glide.with(context).load(obj.getUser().getImageURL()).into(imageView);
            }
        }

        textView.setText(obj.getText());
        timeView.setText(new SimpleDateFormat("hh:mmaa").format(obj.getTime()).toLowerCase());

        if(obj.isRead()) stateView.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {return messages.size();}

    public void addItem(RealmMessage message){
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    public void loadItems(RealmList<RealmMessage> messages){
        this.messages = messages;
        notifyItemRangeInserted(0, messages.size());
    }


    class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView dateView;

        public RelativeLayout receiveMessage;
        public RelativeLayout sendMessage;

        public RealmMessage data; // 해당 데이터 저장! 포지션 따로 기억할 필요 없음 굿굿

        public MessageViewHolder(View itemView) {
            super(itemView);
            dateView = (TextView) itemView.findViewById(R.id.messageDateView);

            receiveMessage = (RelativeLayout) itemView.findViewById(R.id.receiveMessage);
            sendMessage = (RelativeLayout) itemView.findViewById(R.id.sendMessage);
        }
    }
}
