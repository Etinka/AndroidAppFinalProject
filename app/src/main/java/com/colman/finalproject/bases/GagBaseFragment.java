package com.colman.finalproject.bases;

import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;

import com.colman.finalproject.utils.Logger;

import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class GagBaseFragment extends Fragment {
    protected Logger logger = new Logger(this.getClass().getSimpleName());

    protected void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getCurrentFocus()).getWindowToken(), 0);
        }
    }
}
