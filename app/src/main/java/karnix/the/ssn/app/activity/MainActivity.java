package karnix.the.ssn.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import karnix.the.ssn.app.Fragments.AlertsFragment;
import karnix.the.ssn.app.Fragments.NewsFeedFragment;
import karnix.the.ssn.app.activity.dining.DiningActivity;
import karnix.the.ssn.app.utils.LogHelper;
import karnix.the.ssn.ssnmachan.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = LogHelper.makeLogTag(MainActivity.class);

    private Toolbar toolbar;
    private MaterialTabs tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

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
                        new PrimaryDrawerItem().withIdentifier(1).withName(getString(R.string.drawer_home)).withIcon(R.drawable.ic_home),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(2).withName(getString(R.string.drawer_buses)).withIcon(R.drawable.ic_bus),
                        new PrimaryDrawerItem().withIdentifier(3).withName(getString(R.string.drawer_dining)).withIcon(R.drawable.ic_dining),
                        new PrimaryDrawerItem().withIdentifier(4).withName(getString(R.string.drawer_exam_cell)).withIcon(R.drawable.ic_assignment_black_24dp),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(5).withName(getString(R.string.drawer_message)).withIcon(R.drawable.ic_post_message),
                        new PrimaryDrawerItem().withIdentifier(6).withName(getString(R.string.drawer_about)).withIcon(R.drawable.ic_about))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        boolean flag = false;
                        if (drawerItem != null) {
                            flag = true;
                            switch ((int) drawerItem.getIdentifier()) {
                                case 1:
                                    break;
                                case 2:
                                    startActivity(new Intent(MainActivity.this, BusActivity.class));
                                    break;
                                case 3:
                                    startActivity(new Intent(MainActivity.this, DiningActivity.class));
                                    break;
                                case 4:
                                    startActivity(new Intent(MainActivity.this, MessageActivity.class));
                                    break;
                                case 5:
                                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                                    break;
                                case 6:
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                    break;
                            }
                        }
                        return flag;
                    }
                })
                .build();

        drawer.addStickyFooterItem(new PrimaryDrawerItem().withIdentifier(6)
                .withName(getString(R.string.drawer_sign_out)).withIcon(R.drawable.ic_sign_out));

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(1);
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"ADMIN", "CLUBS", "DEPARTMENT"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            switch (position) {
                case 0:
                    f = AlertsFragment.newInstance();
                    break;
                case 1:
                    f = AlertsFragment.newInstance();
                    break;
                case 2:
                    f = new NewsFeedFragment();
                    break;
            }
            return f;
        }
    }
}
