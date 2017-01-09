package com.blink.blink.Chatting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blink.blink.R;
import com.blink.blink.Realm.RealmChattingRoom;
import com.blink.blink.Realm.RealmMessage;
import com.blink.blink.Realm.RealmUser;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

import static com.blink.blink.Chatting.ChatListActivity.USER_ID;
import static com.blink.blink.Chatting.ChatListActivity.chattingRoomViewAdapter;
import static com.blink.blink.Chatting.ChatListActivity.localDB;


/**
 * Created by Beomseok on 2016. 12. 28..
 */

public class ChattingActivity extends AppCompatActivity{

    private static final String TAG = "ChattingActivity";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 100;

    private SharedPreferences mSharedPreferences; // DB에 일일히 쓸 필요 없음

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;

    // Realm instance variables
    private Realm realm;
    private RealmConfiguration config;

    private RealmChattingRoom chattingRoom;
    private RealmUser user;
    private RealmUser matchedUser;

    //private ChattingRoom chattingRoom;

    private RecyclerView messageRecyclerView;
    private LinearLayoutManager messageLayoutManager;
    private MessageViewAdapter messageViewAdapter;

    private ImageView messageBackImageView;
    private ImageView messageOptionImageView;
    private RelativeLayout messageTopProfileLayout;
    private CircleImageView messageTopImageView;
    private TextView messageTopNameView;

    private EditText messageEditText;
    private ImageView sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        setSupportActionBar((Toolbar) findViewById(R.id.chattingToolBar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        messageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        setMessageRecyclerView();

        loadRealm();

        setToolBar();

        sendButton = (ImageView) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        messageEditText = (EditText) findViewById(R.id.messageEditText);
        setMessageEditText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkDeleteChattingRoom();
        chattingRoomViewAdapter.refresh();
        realm.close(); // 렐름을 사용하면 반드시 닫을 것
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void createChattingRoom(){
        realm = Realm.getInstance(config);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                localDB.getMatchedUsers().add(matchedUser);
                chattingRoomViewAdapter.addItem(chattingRoom);
            }
        });
        realm.close();
    }

    private void checkDeleteChattingRoom(){
        realm = Realm.getInstance(config);
        if(chattingRoom.getLastMessage() == null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    matchedUser.deleteFromRealm();
                    chattingRoom.deleteFromRealm();
                }
            });
        }
        realm.close();
    }

    private void setMessageRecyclerView() {
        messageLayoutManager = new LinearLayoutManager(this);
        messageViewAdapter = new MessageViewAdapter(getApplicationContext());

        messageRecyclerView.setLayoutManager(messageLayoutManager);
        messageRecyclerView.setAdapter(messageViewAdapter);

        messageLayoutManager.setStackFromEnd(true);
        messageViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                messageLayoutManager.scrollToPosition(messageViewAdapter.getItemCount()-1);
            }
        });
    }

    private void loadRealm(){
        // loadLocalDB();
        config = ChatListActivity.config;
        realm = Realm.getInstance(config);
        user = realm.where(RealmUser.class).equalTo("id", USER_ID).findFirst();

        // setChattingRoom();
        Serializable serializable = getIntent().getSerializableExtra("chattingRoomMatchingID");
        if(serializable == null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    matchedUser = realm.createObject(RealmUser.class);
                    matchedUser.setName(String.valueOf(UUID.randomUUID().getMostSignificantBits()));
                    matchedUser.setId(UUID.randomUUID().getMostSignificantBits());

                    chattingRoom = realm.createObject(RealmChattingRoom.class);
                    chattingRoom.setMatchingId(UUID.randomUUID().getMostSignificantBits());
                    chattingRoom.setMatchedUser(matchedUser);
                    chattingRoom.setMessages(new RealmList<RealmMessage>());
                }
            });
        } else {
            long matcingID = (long) serializable;
            chattingRoom = realm.where(RealmChattingRoom.class).equalTo("matchingId", matcingID).findFirst();
            matchedUser = chattingRoom.getMatchedUser();
        }

        // loadMessages();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(chattingRoom != null) {
                    RealmList<RealmMessage> messages = chattingRoom.getMessages();
                    if (!messages.isEmpty()) {
                        for (RealmMessage m : messages) {
                            m.setRead(true);
                        }
                    }
                    messageViewAdapter.loadItems(messages);
                }
            }
        });
    }

    private void setToolBar(){
        messageTopProfileLayout = (RelativeLayout) findViewById(R.id.messageTopProfile);
        messageTopImageView = (CircleImageView) findViewById(R.id.messageTopCircleImageView);
        messageTopNameView = (TextView) findViewById(R.id.messageTopNameView);
        messageBackImageView = (ImageView) findViewById(R.id.messageBackImageView);
        messageOptionImageView = (ImageView) findViewById(R.id.messageOptionImageView);

        //messageTopImageView.setImageURI();

        String matchedUserName = matchedUser.getName();
        if(matchedUserName.length() > 8){
            matchedUserName = matchedUserName.substring(0,8);
            matchedUserName += "...";
        }
        messageTopNameView.setText(matchedUserName);

        messageBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });

        messageTopProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        messageOptionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveMessage();
            }
        });
    }

    private void setMessageEditText(){
        messageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mSharedPreferences
                .getInt("blink_msg_length", DEFAULT_MSG_LENGTH_LIMIT))});
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendButton.setClickable(true);
                    sendButton.setBackgroundColor(Color.parseColor("#FF5722") );
                } else {
                    sendButton.setClickable(false);
                    sendButton.setBackgroundColor(Color.parseColor("#FFAB91"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        messageEditText.setText("");
    }

    private void sendMessage(){
        if(chattingRoom.getLastMessage() == null) createChattingRoom();

        sendMessageToDB();
        //sendMessageToFB();

        messageEditText.setText("");
    }

    private void sendMessageToDB() {

        realm = Realm.getInstance(config);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person
                RealmMessage message = realm.createObject(RealmMessage.class);
                message.setUser(user);
                message.setText(messageEditText.getText().toString());
                message.setTime(new Date());
                message.setRead(true);
                messageViewAdapter.addItem(message);
            }
        });

        realm.close();
    }

    private void receiveMessage() {

        realm = Realm.getInstance(config);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person
                RealmMessage message = realm.createObject(RealmMessage.class);
                message.setUser(chattingRoom.getMatchedUser());
                message.setText(messageEditText.getText().toString());
                message.setTime(new Date());
                message.setRead(false);
                messageViewAdapter.addItem(message);
            }
        });

        realm.close();
    }

    /*
    private void sendMessageToFB(){
        BlinkMessage blinkMessage = new
                BlinkMessage(messageEditText.getText().toString(),
                mUsername,
                mPhotoUrl);
        mFirebaseDatabaseReference.child(MESSAGES_CHILD)
                .push().setValue(blinkMessage);
    }
    */
}
