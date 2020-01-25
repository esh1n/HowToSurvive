package com.esh1n.guidtoarchapp.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.esh1n.guidtoarchapp.R;

/**
 * Fragment for each of the bottom navigation tabs.
 *
 * @author bherbst
 */
public class ChildFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child, container, false);
    }
}
