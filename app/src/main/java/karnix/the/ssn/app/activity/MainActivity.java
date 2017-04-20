package karnix.the.ssn.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import io.karim.MaterialTabs;
import karnix.the.ssn.app.Fragments.AlertsFragment;
import karnix.the.ssn.app.Fragments.NewsFeedFragment;
import karnix.the.ssn.ssnmachan.R;

public class MainActivity extends AppCompatActivity {
    private Button button;
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

        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(1).withName(getString(R.string.drawer_home)).withIcon(R.drawable.ic_home),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(2).withName(getString(R.string.drawer_buses)).withIcon(R.drawable.ic_bus),
                        new PrimaryDrawerItem().withIdentifier(3).withName(getString(R.string.drawer_dining)).withIcon(R.drawable.ic_dining),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withIdentifier(4).withName(getString(R.string.drawer_message)).withIcon(R.drawable.ic_post_message),
                        new SecondaryDrawerItem().withIdentifier(5).withName(getString(R.string.drawer_about)).withIcon(R.drawable.ic_about))
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
                            }
                        }
                        return flag;
                    }
                })
                .build();

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(getApplicationContext(), GoogleSignInActivity.class));
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), GoogleSignInActivity.class));
        }

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(1);

        button = (Button) toolbar.findViewById(R.id.signOutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
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
