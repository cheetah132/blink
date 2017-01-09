package com.blink.blink.Chatting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blink.blink.R;
import com.blink.blink.Realm.LocalDB;
import com.blink.blink.Realm.RealmChattingRoom;
import com.blink.blink.Realm.RealmMessage;
import com.blink.blink.Realm.RealmUser;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.Sort;

/**
 * Created by Beomseok on 2016. 12. 29..
 */

public class ChatListActivity extends AppCompatActivity{

    private static final String TAG = "ChatListActivity";

    // Realm instance variables
    private Realm realm;
    public static RealmConfiguration config;
    public static LocalDB localDB;

    public static final String USER_NAME = "John";
    public static final int USER_ID = 1234;

    private RecyclerView chattingRoomRecyclerView;
    private LinearLayoutManager chattingRoomLayoutManger;
    public static ChattingRoomViewAdapter chattingRoomViewAdapter;

    private ImageView petView;

    private byte[] key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        // set Toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.chatListToolBar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // set View
        chattingRoomRecyclerView = (RecyclerView) findViewById(R.id.chatListRecyclerView);
        petView = (ImageView) findViewById(R.id.petView);
        petView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChattingActivity.class));
                //createChattingRoom();
            }
        });

        // init
        initRealm();
        setChattingRoomRecyclerView();
        loadChattingRooms();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // 렐름을 사용하면 반드시 닫을 것
    }

    private void initRealm(){
        // createLocalDBKey();
        //key = new byte[64];
        //Base64.encodeToString(key, Base64.DEFAULT);
        //key = Base64.decode(stringKey, Base64.DEFAULT);

        config = new RealmConfiguration.Builder().name("test.realm")//.encryptionKey(key)
                .deleteRealmIfMigrationNeeded().build();
        Realm.deleteRealm(config);

        // loadLocalDB();
        realm = Realm.getInstance(config);
        LocalDB temp = realm.where(LocalDB.class).equalTo("user.id", USER_ID).findFirst();

        if(temp == null){
            createLocalDB();
        }else{
            localDB = temp;
        }
    }

    private void createLocalDB(){
        realm = Realm.getInstance(config);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmUser realmUser = realm.createObject(RealmUser.class);
                realmUser.setName(USER_NAME);
                realmUser.setId(USER_ID);

                localDB = realm.createObject(LocalDB.class);
                localDB.setUser(realmUser);

                localDB.setMatchedUsers(new RealmList<RealmUser>());
                localDB.setChattingRooms(new RealmList<RealmChattingRoom>());
            }
        });

        realm.close();;
    }

    private void setChattingRoomRecyclerView() {
        chattingRoomLayoutManger = new LinearLayoutManager(this);
        chattingRoomRecyclerView.setLayoutManager(chattingRoomLayoutManger);
        chattingRoomLayoutManger.setReverseLayout(true);

        chattingRoomViewAdapter = new ChattingRoomViewAdapter(getApplicationContext());
        chattingRoomRecyclerView.setAdapter(chattingRoomViewAdapter);
        chattingRoomViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                chattingRoomLayoutManger.scrollToPosition(chattingRoomViewAdapter.getItemCount()-1);
            }
        });
    }

    private void loadChattingRooms(){
        realm = Realm.getInstance(config);
        chattingRoomViewAdapter.loadItems(localDB.getChattingRooms());
        realm.close();;
    }

    public void createChattingRoom() {
        realm = Realm.getInstance(config);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmUser matchedUser = realm.createObject(RealmUser.class);
                matchedUser.setName(String.valueOf(UUID.randomUUID().getMostSignificantBits()));
                matchedUser.setId(UUID.randomUUID().getMostSignificantBits());

                RealmChattingRoom chattingRoom = realm.createObject(RealmChattingRoom.class);
                chattingRoom.setMatchingId(UUID.randomUUID().getMostSignificantBits());
                chattingRoom.setMatchedUser(matchedUser);
                chattingRoom.setMessages(new RealmList<RealmMessage>());

                localDB.getMatchedUsers().add(matchedUser);
                //localDB.getChattingRooms().add(chattingRoom);

                chattingRoomViewAdapter.addItem(chattingRoom);
            }

        });

        realm.close();
    }
}
