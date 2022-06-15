package hcmute.edu.vn.nhom01.zaloapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class UserInfoFragment extends Fragment {

    TextView txtLogout, txtCloud, txtEditProfile, txtSetting;
    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        txtEditProfile = view.findViewById(R.id.txtEditProfile);
        txtCloud = view.findViewById(R.id.txtCloud);
        txtLogout = view.findViewById(R.id.txtLogout);
        txtSetting = view.findViewById(R.id.txtSetting);


        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
//                Toast.makeText(getActivity(), "Log out success!", Toast.LENGTH_SHORT).show();
            }
        });

        txtCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Chưa xử lý tính năng này!", Toast.LENGTH_SHORT).show();
            }
        });

        txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Chưa xử lý tính năng này!", Toast.LENGTH_SHORT).show();
            }
        });

        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
}