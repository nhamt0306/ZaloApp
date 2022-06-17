package hcmute.edu.vn.nhom01.zaloapp.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import hcmute.edu.vn.nhom01.zaloapp.MessageFragment;
import hcmute.edu.vn.nhom01.zaloapp.NewFeedFragment;
import hcmute.edu.vn.nhom01.zaloapp.UserInfoFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager supportFragmentManager, int behaviorResumeOnlyCurrentFragment) {
        super(supportFragmentManager, behaviorResumeOnlyCurrentFragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MessageFragment();
            case 1:
                return new NewFeedFragment();
            case 2:
                return new UserInfoFragment();
            default:
                return new MessageFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
