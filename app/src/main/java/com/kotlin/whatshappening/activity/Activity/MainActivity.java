package com.kotlin.whatshappening.activity.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kotlin.whatshappening.R;
import com.kotlin.whatshappening.activity.fragment.Entertainment;
import com.kotlin.whatshappening.activity.fragment.PhotosFragment;
import com.kotlin.whatshappening.activity.fragment.Sports;
import com.kotlin.whatshappening.activity.fragment.TopStories;
import com.kotlin.whatshappening.activity.fragment.Technology;
import com.kotlin.whatshappening.activity.model.News;
import com.kotlin.whatshappening.activity.model.NewsResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "1bb1ed35ad1547b8adbda7fc5a643da8";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    List<NewsResponse> newsResponse;

    List<News> news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);




    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopStories(), "top stories");
        adapter.addFragment(new Technology(), "technology");
        adapter.addFragment(new Entertainment(), "entertainment");
        adapter.addFragment(new Sports(), "sports");
        adapter.addFragment(new PhotosFragment(),"photos");
        viewPager.setAdapter(adapter);
    }

    private void initializeViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }


     class ViewPagerAdapter extends FragmentPagerAdapter {

         private final List<Fragment> mFragmentList = new ArrayList<>();
         private final List<String> mFragmentTitleList = new ArrayList<>();

         public ViewPagerAdapter(FragmentManager manager) {
             super(manager);
         }

         @Override
         public Fragment getItem(int position) {
             return mFragmentList.get(position);
         }

         @Override
         public int getCount() {
             return mFragmentList.size();
         }

         public void addFragment(Fragment fragment, String title) {
             mFragmentList.add(fragment);
             mFragmentTitleList.add(title);
         }

         @Override
         public CharSequence getPageTitle(int position) {
             return mFragmentTitleList.get(position);
         }
     }
    }



