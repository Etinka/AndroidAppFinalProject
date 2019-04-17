package com.colman.finalproject.bases;

import com.colman.finalproject.model.Model;
import com.colman.finalproject.utils.Logger;

import androidx.fragment.app.Fragment;

public class GagBaseFragment extends Fragment {
    protected Logger logger = new Logger(this.getClass().getSimpleName());
    protected Model mModel = Model.getInstance();
}
