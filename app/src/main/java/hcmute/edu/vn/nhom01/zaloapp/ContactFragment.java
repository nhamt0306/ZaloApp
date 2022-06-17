package hcmute.edu.vn.nhom01.zaloapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {

    // Constructor rỗng
    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate fragment_contact cho fragment này
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }
}