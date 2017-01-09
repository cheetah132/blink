package com.blink.blink.Chatting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blink.blink.R;
import com.blink.blink.Realm.RealmChattingRoom;
import com.blink.blink.Realm.RealmMessage;
import com.blink.blink.Realm.RealmUser;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

import static com.blink.blink.Chatting.ChatListActivity.USER_ID;
import static com.blink.blink.Chatting.ChatListActivity.chattingRoomViewAdapter;
import static com.blink.blink.Chatting.ChatListActivity.config;
import static com.blink.blink.Chatting.ChatListActivity.localDB;

/**
 * Created by Beomseok on 2017. 1. 2..
 */

public class ChattingRoomViewAdapter extends RecyclerView.Adapter<ChattingRoomViewAdapter.ChattingRoomViewHolder> {
    private Context context;
    private RealmList<RealmChattingRoom> chattingRooms;
    private SwipeLayout openSwipeLayout;

    public ChattingRoomViewAdapter(Context context) {
        this.context = context;
        this.chattingRooms = new RealmList<>();
    }

    @Override
    public ChattingRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatting_room, parent,false);
        // set the view's size, margins, paddings and layout parameters

        return new ChattingRoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChattingRoomViewHolder holder, final int position) {
        final RealmChattingRoom obj = chattingRooms.get(position);

        holder.data = obj;

        if (obj.getMatchedUser().getImageURL() == null) {
            holder.chattingRoomImageView.setVisibility(View.VISIBLE);
            holder.chattingRoomImageView.setImageResource(R.drawable.mary_image);
        }
        else{}
        //Glide.with(context).load(obj.getMatchedUser().getImageURL()).into(holder.chattingRoomImageView);

        if(obj.getLastMessage() != null) {

            if(obj.getLastMessage().getUser().getId() != USER_ID) {
                holder.chattingRoomFromView.setText("from. ");
            }else {
                holder.chattingRoomFromView.setText("to. ");
            }

            holder.chattingRoomMessageView.setText(obj.getLastMessage().getText());
            holder.chattingRoomTimeView.setText(new SimpleDateFormat("hh:mmaa").format(obj.getLastMessage().getTime()).toLowerCase());
        } else{
            holder.chattingRoomFromView.setText("to. ");
            holder.chattingRoomMessageView.setHint(obj.getMatchedUser().getName() + "에게 말을 걸어보세요.");
        }

        String name = obj.getMatchedUser().getName();
        if(name.length() > 8){
            name = name.substring(0,8);
            name += "...";
        }
        holder.chattingRoomNameView.setText(name);

        int unCheckedCount = obj.getUnCheckedCount();
        if(unCheckedCount != 0) {
            holder.chattingRoomUnCheckedCountView.setVisibility(View.VISIBLE);
            if(unCheckedCount > 300)
                holder.chattingRoomUnCheckedCountView.setText("300...");
            else
                holder.chattingRoomUnCheckedCountView.setText(String.valueOf(unCheckedCount));
        } else{
            holder.chattingRoomUnCheckedCountView.setVisibility(View.GONE);
        }

        holder.chattingRoomSwipeContainer.getSurfaceView().setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context ,ChattingActivity.class);
                intent.putExtra("chattingRoomMatchingID", obj.getMatchingId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return;
            }
        });

        holder.chattingRoomSwipeContainer.addSwipeListener(new SimpleSwipeListener(){

            @Override
            public void onStartOpen(SwipeLayout layout) {
                super.onStartOpen(layout);
                if(openSwipeLayout != null) openSwipeLayout.close();
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                super.onOpen(layout);
                openSwipeLayout = layout;
            }
        });

        holder.chattingRoomDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.chattingRoomMessageView.setText("");
                holder.chattingRoomTimeView.setText("");

                Realm realm = Realm.getInstance(config);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmList<RealmMessage> messages = obj.getMessages();
                        messages.deleteAllFromRealm();
                        chattingRooms.remove(position);
                        obj.getMatchedUser().deleteFromRealm();
                        obj.deleteFromRealm();
                    }
                });
                realm.close();;

                notifyItemRemoved(position);
                notifyItemRangeChanged(position,chattingRooms.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return chattingRooms.size();
    }

    public void addItem(RealmChattingRoom chattingRoom){
        chattingRooms.add(chattingRoom);
        notifyItemInserted(chattingRooms.size() - 1);
    }

    public void loadItems(RealmList<RealmChattingRoom> chattingRooms){
        sortRealmChattingRooms(chattingRooms);
        this.chattingRooms = chattingRooms;
        notifyItemRangeChanged(0, chattingRooms.size());
    }

    public void refresh(){
        sortRealmChattingRooms(chattingRooms);
        notifyItemRangeChanged(0, chattingRooms.size());
    }

    private void sortRealmChattingRooms(final RealmList<RealmChattingRoom> realmChattingRooms){
        Realm realm = Realm.getInstance(config);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Collections.sort(realmChattingRooms, new Comparator<RealmChattingRoom>() {
                    @Override
                    public int compare(RealmChattingRoom o1, RealmChattingRoom o2) {
                        if(o1.getLastMessage() == null || o2.getLastMessage() == null) return 0;

                        Date o1t = o1.getLastMessage().getTime();
                        Date o2t = o2.getLastMessage().getTime();
                        return o1t.compareTo(o2t);
                    }
                });
            }
        });
        realm.close();
    }

    class ChattingRoomViewHolder extends RecyclerView.ViewHolder{
        public SwipeLayout chattingRoomSwipeContainer;
        public CircleImageView chattingRoomImageView;
        public TextView chattingRoomMessageView;
        public TextView chattingRoomFromView;
        public TextView chattingRoomNameView;
        public TextView chattingRoomUnCheckedCountView;
        public TextView chattingRoomTimeView;
        public ImageView chattingRoomDeleteButton;

        public RealmChattingRoom data;

        public ChattingRoomViewHolder(View itemView) {
            super(itemView);
            chattingRoomSwipeContainer = (SwipeLayout) itemView.findViewById(R.id.chattingRoomSwipeContainer);
            chattingRoomImageView = (CircleImageView) itemView.findViewById(R.id.chattingRoomImageView);
            chattingRoomMessageView = (TextView) itemView.findViewById(R.id.chattingRoomMessageView);
            chattingRoomFromView = (TextView) itemView.findViewById(R.id.chattingRoomFromView);
            chattingRoomNameView = (TextView) itemView.findViewById(R.id.chattingRoomNameView);
            chattingRoomUnCheckedCountView = (TextView) itemView.findViewById(R.id.chattingRoomUnCheckedCountView);
            chattingRoomTimeView = (TextView) itemView.findViewById(R.id.chattingRoomTimeView);
            chattingRoomDeleteButton = (ImageView) itemView.findViewById(R.id.chattingRoomDeleteButton);
        }
    }
}