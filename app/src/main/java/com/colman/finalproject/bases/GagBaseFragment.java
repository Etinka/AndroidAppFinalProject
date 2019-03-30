package com.colman.finalproject.bases;

import android.support.v4.app.Fragment;

import com.colman.finalproject.model.Model;
import com.colman.finalproject.utils.Logger;

public class GagBaseFragment extends Fragment {
    protected Logger logger = new Logger(this.getClass().getSimpleName());
    protected Model mModel = Model.getInstance();
}
