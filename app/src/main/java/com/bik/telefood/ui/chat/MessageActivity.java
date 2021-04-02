package com.bik.telefood.ui.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.SharedPreferencesHelper;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityMessageBinding;
import com.bik.telefood.model.network.ApiConstant;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MessageActivity extends AppCompatActivity implements MessageAdapter.OnMapClickListener {
    private ActivityMessageBinding binding;
    private static final int FINE_LOCATION_PERMISSION_REQUEST = 1;

    private DocumentReference firebaseFirestore;
    private int inputUserId;
    private List<MessageModel> messageModels;
    private MessageAdapter messageAdapter;
    private boolean isLocation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        int roomId = intent.getIntExtra(AppConstant.ROOM_ID, 0);
        String name = intent.getStringExtra(AppConstant.USER_NAME);
        String roomIdS = String.valueOf(roomId);
        String userId = SharedPreferencesHelper.getUserId(getApplication());
        inputUserId = Integer.parseInt(userId);

        firebaseFirestore = FirebaseFirestore.getInstance().collection(ApiConstant.CHAT).document(roomIdS);

        messageModels = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, this, messageModels, SharedPreferencesHelper.getUserId(getApplication()));
        LinearLayoutManager linearLayout = new LinearLayoutManager(MessageActivity.this);
        linearLayout.setStackFromEnd(true);
        binding.rvMessages.setLayoutManager(linearLayout);
        binding.rvMessages.setAdapter(messageAdapter);
        listenForChatMessages();

        binding.includeChatContact.tvTitle.setText(name);


        binding.etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    isLocation = true;
                    binding.ivLocation.setImageResource(R.drawable.ic_my_location);
                } else {
                    isLocation = false;
                    binding.ivLocation.setImageResource(R.drawable.ic_send);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.ivLocation.setOnClickListener(v -> {
            if (isLocation) {
                Intent intent1 = new Intent(this, MapsActivity.class);
                startActivityForResult(intent1, FINE_LOCATION_PERMISSION_REQUEST);
            } else {
                String msg = binding.etMessage.getText().toString();
                String tempMsg = msg.trim();
                if (tempMsg.isEmpty()) return;
                sendMessage(msg);
            }
        });
    }

    private void sendMessage(String msg) {
        Map<String, Object> post = new HashMap<>();
        post.put("timestamp", FieldValue.serverTimestamp());
        post.put("is_deleted", false);
        post.put("location", null);
        post.put("message", msg);
        post.put("sender_id", inputUserId);
        post.put("type", ApiConstant.MESSAGE_TYPE_TEXT);

        firebaseFirestore
                .collection(ApiConstant.MESSAGES)
                .add(post);
        binding.etMessage.setText("");
    }

    private void listenForChatMessages() {

        Query query = firebaseFirestore.collection(ApiConstant.MESSAGES).orderBy("timestamp", Query.Direction.ASCENDING);

        query.addSnapshotListener((value, error) -> {
            if (value == null || value.isEmpty()) return;
            messageModels.clear();
            for (DocumentSnapshot snapshot : value.getDocuments()) {
                MessageModel messageModel = snapshot.toObject(MessageModel.class);
                messageModels.add(messageModel);
            }
            binding.rvMessages.scrollToPosition(messageAdapter.getItemCount() - 1);
            messageAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FINE_LOCATION_PERMISSION_REQUEST && resultCode == RESULT_OK && data != null) {
            double lat = data.getDoubleExtra(AppConstant.LAT, 0);
            double lng = data.getDoubleExtra(AppConstant.LNG, 0);
            GeoPoint geoPoint = new GeoPoint(lat, lng);

            Map<String, Object> post = new HashMap<>();
            post.put("timestamp", FieldValue.serverTimestamp());
            post.put("is_deleted", false);
            post.put("location", geoPoint);
            post.put("message", null);
            post.put("sender_id", inputUserId);
            post.put("type", ApiConstant.MESSAGE_TYPE_LOCATION);

            firebaseFirestore
                    .collection(ApiConstant.MESSAGES)
                    .add(post);
        }
    }

    @Override
    public void onMapClick(GeoPoint geoPoint) {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f", geoPoint.getLatitude(), geoPoint.getLongitude());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}