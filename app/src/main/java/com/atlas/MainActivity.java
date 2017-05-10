package com.atlas;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.atlas.fragments.AboutUsFragment;
import com.atlas.fragments.ContactUsFragment;
import com.atlas.fragments.FAQFragment;
import com.atlas.fragments.HomeFragment;
import com.atlas.fragments.MapDetailsFragment;
import com.atlas.fragments.SettingFragment;
import com.atlas.fragments.ThematicMapFragment;
import com.atlas.helper.DebugUtils;
import com.atlas.models.MapEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity implements SettingFragment.onSettingFragmentInteraction {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private Context mContext;
    public NavigationView mNavigationView;
    private TextView mTxtToolbarTitle;
    private boolean mapFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = MainActivity.this;
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTxtToolbarTitle = (TextView) mToolbar.findViewById(R.id.lblTitle);
        setSupportActionBar(mToolbar);
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_action_home);
       /* mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction(homeFragment);
                mDrawerLayout.closeDrawers();
                mNavigationView.setCheckedItem(-1);

//                setTitle("Atlas");
            }
        });*/

        initNavigationDrawer();
        EventBus.getDefault().register(this);


    }
    @Override
    protected void onStart() {
        super.onStart();
        // Wake the device and show our activity
        if (BuildConfig.DEBUG) {
            // Calling this from your launcher activity is enough, but I needed a good example spot ;)
            DebugUtils.riseAndShine(this);
        }
    }
    public void setTitle(String title) {
        mTxtToolbarTitle.setText(title);
    }

    private void initNavigationDrawer() {

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.about_us:
                        AboutUsFragment aboutUsFragment = new AboutUsFragment();
                        fragmentTransaction(aboutUsFragment);
                        mDrawerLayout.closeDrawers();
                        setTitle(getResources().getString(R.string.about_us));
                        break;
                    case R.id.contact_us:
                        ContactUsFragment contactUsFragment = new ContactUsFragment();
                        fragmentTransaction(contactUsFragment);
                        mDrawerLayout.closeDrawers();
                        setTitle(getResources().getString(R.string.contact_us));
                        break;
                    case R.id.faq:
                        FAQFragment faqFragment = new FAQFragment();
                        fragmentTransaction(faqFragment);
                        mDrawerLayout.closeDrawers();
                        setTitle(getResources().getString(R.string.faq));
                        break;
                    case R.id.setting:
                        SettingFragment settingFragment = new SettingFragment();
                        fragmentTransaction(settingFragment);
                        mDrawerLayout.closeDrawers();
                        setTitle(getResources().getString(R.string.settings));
                        break;
                    case R.id.thematic_map:
                        ThematicMapFragment thematicMapFragment = new ThematicMapFragment();
                        fragmentTransaction(thematicMapFragment);
                        mDrawerLayout.closeDrawers();
                        setTitle(getResources().getString(R.string.thematic_map));
                        mToolbar.setNavigationIcon(R.drawable.ic_action_home);
                        mapFlag = false;
                        break;
                }
                return true;
            }
        });
       /* View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
        tv_email.setText("raj.amalw@learn2crack.com");*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

      /*  ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
//                super.onDrawerClosed(v);
//                drawerLayout.closeDrawer(GravityCompat.END);
            }

            @Override
            public void onDrawerOpened(View v) {
//                super.onDrawerOpened(v);
//                drawerLayout.openDrawer(GravityCompat.END);

            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();*/
        findViewById(R.id.imgSettingOptionRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction(homeFragment);
        setTitle(getResources().getString(R.string.home));
        unCheckAllMenuItem();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (mapFlag) {
                    mToolbar.setNavigationIcon(R.drawable.ic_action_home);
                    ThematicMapFragment thematicMapFragment = new ThematicMapFragment();
                    fragmentTransaction(thematicMapFragment);
                    setTitle(getResources().getString(R.string.thematic_map));
                    mNavigationView.setCheckedItem(R.id.thematic_map);
                    mapFlag = false;
                } else {
                    mToolbar.setNavigationIcon(R.drawable.ic_action_home);
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction(homeFragment);
                    mDrawerLayout.closeDrawers();
                    unCheckAllMenuItem();
                    mapFlag = false;
                }
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fragmentTransaction(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.container, fragment);
//            transaction.addToBackStack(null);
            transaction.commit();
        }
    }




   /* public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }*/

    @Override
    public void onSettingFragmentInteraction(boolean flag) {

        mNavigationView.getMenu().getItem(4).setEnabled(true);
        ThematicMapFragment thematicMapFragment = new ThematicMapFragment();
        fragmentTransaction(thematicMapFragment);
        setTitle(getResources().getString(R.string.thematic_map));
        mNavigationView.setCheckedItem(R.id.thematic_map);
        Snackbar.make(mToolbar, getResources().getString(R.string.map_key_success), Snackbar.LENGTH_SHORT).show();

    }

    /*
        class MyDialog extends AlertDialog {
            public MyDialog(Context ctx) {
                super(ctx);
            }

        }*/
    private void unCheckAllMenuItem() {
        int size = mNavigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            mNavigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    @Subscribe
    public void onEvent(MapEvent mapEvent) {
        // your implementation
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        MapDetailsFragment mapDetailsFragment = MapDetailsFragment.newInstance(mapEvent.getMap());
        fragmentTransaction(mapDetailsFragment);
        mapFlag = true;

    }

    @Override
    public void onBackPressed() {
        if (mapFlag) {
            ThematicMapFragment thematicMapFragment = new ThematicMapFragment();
            fragmentTransaction(thematicMapFragment);
            setTitle(getResources().getString(R.string.thematic_map));
            mNavigationView.setCheckedItem(R.id.thematic_map);
            mapFlag = false;
            mToolbar.setNavigationIcon(R.drawable.ic_action_home);

        } else {
            mToolbar.setNavigationIcon(R.drawable.ic_action_home);
            super.onBackPressed();
            mapFlag = false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
