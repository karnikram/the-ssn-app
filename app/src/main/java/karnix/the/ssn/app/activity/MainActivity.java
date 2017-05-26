package karnix.the.ssn.app.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.heinrichreimersoftware.androidissuereporter.IssueReporterLauncher;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;

import io.karim.MaterialTabs;
import karnix.the.ssn.app.activity.alerts.AlertsActivity;
import karnix.the.ssn.app.activity.bus.BusActivity;
import karnix.the.ssn.app.activity.dining.DiningActivity;
import karnix.the.ssn.app.adapters.MainActivityPagerAdapter;
import karnix.the.ssn.app.utils.LogHelper;
import karnix.the.ssn.ssnmachan.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = LogHelper.makeLogTag(MainActivity.class);

    private Toolbar toolbar;
    private MaterialTabs tabs;
    private ViewPager pager;
    private MainActivityPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (MaterialTabs) findViewById(R.id.material_tabs);
        pager = (ViewPager) findViewById(R.id.pager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return getResources().getDrawable(R.drawable.ic_launcher);
            }
        });

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        AccountHeader accountHeader = new AccountHeaderBuilder().withActivity(this)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.drawer_header)
                .addProfiles(
                        new ProfileDrawerItem().withName(firebaseUser.getDisplayName())
                                .withEmail(firebaseUser.getEmail())
                                .withIcon(firebaseUser.getPhotoUrl().toString())
                )
                .build();

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(accountHeader)
                .withTranslucentNavigationBar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(0).withName(getString(R.string.drawer_home)).withIcon(GoogleMaterial.Icon.gmd_home),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_alma).withIcon(GoogleMaterial.Icon.gmd_school),
                        new PrimaryDrawerItem().withIdentifier(2).withName(getString(R.string.drawer_buses)).withIcon(GoogleMaterial.Icon.gmd_directions_bus),
                        new PrimaryDrawerItem().withIdentifier(3).withName(getString(R.string.drawer_bus_announcements)).withIcon(GoogleMaterial.Icon.gmd_announcement),
                        new PrimaryDrawerItem().withIdentifier(4).withName(getString(R.string.drawer_dining)).withIcon(GoogleMaterial.Icon.gmd_local_dining),
                        new PrimaryDrawerItem().withIdentifier(5).withName(getString(R.string.drawer_exam_cell)).withIcon(GoogleMaterial.Icon.gmd_assignment),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(8).withName(getString(R.string.drawer_settings)).withIcon(GoogleMaterial.Icon.gmd_settings),
                        new PrimaryDrawerItem().withIdentifier(10).withName(getString(R.string.drawer_report_issue)).withIcon(GoogleMaterial.Icon.gmd_bug_report),
                        new PrimaryDrawerItem().withIdentifier(7).withName(getString(R.string.drawer_about)).withIcon(GoogleMaterial.Icon.gmd_info),
                        new PrimaryDrawerItem().withIdentifier(9).withName(R.string.drawer_sign_out).withIcon(GoogleMaterial.Icon.gmd_exit_to_app))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        boolean flag = false;
                        if (drawerItem != null) {
                            flag = true;
                            switch ((int) drawerItem.getIdentifier()) {
                                case 0:
                                    return false;
                                case 1:
                                    String url = "http://ssn.almaconnect.com";
                                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                    builder.setToolbarColor(getResources().getColor(R.color.primaryColor));
                                    builder.setShowTitle(true);

                                    CustomTabsIntent customTabsIntent = builder.build();
                                    customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                                    break;
                                case 2:
                                    startActivity(new Intent(MainActivity.this, BusActivity.class));
                                    break;
                                case 3:
                                    startActivity(new Intent(MainActivity.this, AlertsActivity.class)
                                            .putExtra("type", "busdept"));
                                    break;
                                case 4:
                                    startActivity(new Intent(MainActivity.this, DiningActivity.class));
                                    break;
                                case 5:
                                    startActivity(new Intent(MainActivity.this, AlertsActivity.class)
                                            .putExtra("type", "examcell"));
                                    break;
                                case 7:
                                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                                    break;
                                case 8:
                                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                                    break;
                                case 9:
                                    String[] topics = getResources().getStringArray(R.array.notification_category_keys);
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topics[0]);
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topics[1]);
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topics[2]);
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topics[3]);
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topics[4]);
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topics[5]);

                                    String[] departmentKeys = getResources().getStringArray(R.array.department_category_keys);
                                    for (String departmentKey : departmentKeys) {
                                        FirebaseMessaging.getInstance().unsubscribeFromTopic(departmentKey);
                                        LogHelper.d(TAG, "Unsubscribed from " + departmentKey);
                                    }

                                    GoogleSignInOptions googleSignInOptions =
                                            new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                                    .build();
                                    final GoogleApiClient googleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                                            .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                                            .build();

                                    googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                                        @Override
                                        public void onConnected(@Nullable Bundle bundle) {
                                            Auth.GoogleSignInApi.signOut(googleApiClient);
                                        }

                                        @Override
                                        public void onConnectionSuspended(int i) {
                                        }
                                    });
                                    googleApiClient.connect();

                                    FirebaseAuth.getInstance().signOut();

                                    startActivity(new Intent(MainActivity.this, SplashActivity.class));
                                    finish();
                                    break;
                                case 10:
                                    IssueReporterLauncher.forTarget("Karnix", "The-SSN-App")
                                            .theme(R.style.IssueReporterTheme)
                                            .guestToken(getString(R.string.github_guest_token))
                                            .launch(MainActivity.this);
                                    break;
                            }
                        }
                        return flag;
                    }
                })
                .build();

        adapter = new MainActivityPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(1);

        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.getBoolean("notification_popup_shown", false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.popup_notification_message))
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        }
                    })
                    .show();
        }
        editor.putBoolean("notification_popup_shown", true);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_feed) {
            startActivity(new Intent(MainActivity.this, NewsFeedActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
