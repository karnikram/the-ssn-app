package karnix.the.ssn.app.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import karnix.the.ssn.ssnmachan.R;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_notifications_admin)
    Switch switchNotificationsAdmin;
    @BindView(R.id.switch_notifications_clubs)
    Switch switchNotificationsClubs;
    @BindView(R.id.switch_notifications_departments)
    Switch switchNotificationsDepartments;
    @BindView(R.id.switch_notifications_exam_cell)
    Switch switchNotificationsExamCell;
    @BindView(R.id.switch_notifications_bus)
    Switch switchNotificationsBus;
    @BindView(R.id.switch_notifications_feed)
    Switch switchNotificationsFeed;

    private SharedPreferences sharedPreferences;
    private String[] topics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        topics = getResources().getStringArray(R.array.notification_category_keys);
        for (String topic : topics) {
            if (topic.equals(topics[0])) {
                switchNotificationsAdmin.setChecked(sharedPreferences.getBoolean("notifications_" + topic, false));
            } else if (topic.equals(topics[1])) {
                switchNotificationsClubs.setChecked(sharedPreferences.getBoolean("notifications_" + topic, false));
            } else if (topic.equals(topics[2])) {
                switchNotificationsDepartments.setChecked(sharedPreferences.getBoolean("notifications_" + topic, false));
            } else if (topic.equals(topics[3])) {
                switchNotificationsExamCell.setChecked(sharedPreferences.getBoolean("notifications_" + topic, false));
            } else if (topic.equals(topics[4])) {
                switchNotificationsBus.setChecked(sharedPreferences.getBoolean("notifications_" + topic, false));
            } else if (topic.equals(topics[5])) {
                switchNotificationsFeed.setChecked(sharedPreferences.getBoolean("notifications_" + topic, false));
            }
        }
    }


    @OnClick({R.id.switch_notifications_admin, R.id.switch_notifications_clubs,
            R.id.switch_notifications_departments, R.id.switch_notifications_exam_cell,
            R.id.switch_notifications_bus, R.id.switch_notifications_feed})
    public void onSwitchClicked(View view) {
        switch (view.getId()) {
            case R.id.switch_notifications_admin:
                switchTopicSubscription(topics[0]);
                break;
            case R.id.switch_notifications_clubs:
                switchTopicSubscription(topics[1]);
                break;
            case R.id.switch_notifications_departments:
                switchTopicSubscription(topics[2]);
                break;
            case R.id.switch_notifications_exam_cell:
                switchTopicSubscription(topics[3]);
                break;
            case R.id.switch_notifications_bus:
                switchTopicSubscription(topics[4]);
                break;
            case R.id.switch_notifications_feed:
                switchTopicSubscription(topics[5]);
                break;
        }
    }

    private void switchTopicSubscription(String topic) {
        String key = "notifications_" + topic;

        SharedPreferences.Editor editor;
        if (sharedPreferences.getBoolean(key, false)) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
            editor = sharedPreferences.edit();
            editor.putBoolean(key, false);
        } else {
            FirebaseMessaging.getInstance().subscribeToTopic(topic);
            editor = sharedPreferences.edit();
            editor.putBoolean(key, true);
        }
        editor.apply();
    }
}
