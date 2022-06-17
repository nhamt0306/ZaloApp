package hcmute.edu.vn.nhom01.zaloapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hcmute.edu.vn.nhom01.zaloapp.adapters.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.botton_nav);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_item1).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_item3).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_item4).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_item3:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_item4:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }

}
