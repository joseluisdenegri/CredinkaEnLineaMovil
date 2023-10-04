package com.credinkamovil.pe.utils;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentUtils {
    public static void clearBackStackFragment(@NonNull FragmentManager fragmentManager) {
        int iCountEntry = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < iCountEntry; i++){
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(i).getId(),1);
        }
    }

    public static void removeAllFragments(@NonNull FragmentManager fragmentManager) {
        List<Fragment> fragments = getFragments(fragmentManager);
        if (!fragments.isEmpty()) {
            for (int i = fragments.size() - 1; i >= 0; --i) {
                Fragment fragment = (Fragment) fragments.get(i);
                if (fragment != null) {
                    removeAllFragments(fragment.getChildFragmentManager());
                    removeFragment(fragment);
                }
            }
        }
    }

    public static void removeFragment(Fragment fragment) {
        operateFragment(fragment.getFragmentManager(), (Fragment) null, fragment, 4);
    }

    private static Fragment operateFragment(FragmentManager fragmentManager, Fragment srcFragment, @NonNull Fragment destFragment, int type) {
        if (srcFragment == destFragment) {
            return null;
        } else if (srcFragment != null && srcFragment.isRemoving()) {
            return null;
        } else {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (type == 4) {
                ft.remove(destFragment);
            }
            ft.commitAllowingStateLoss();
            return destFragment;
        }
    }

    public static List<Fragment> getFragments(FragmentManager fragmentManager) {
        return getFragmentsIsInStack(fragmentManager);
    }

    private static List<Fragment> getFragmentsIsInStack(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (!fragments.isEmpty()) {
            List<Fragment> result = new ArrayList();
            for (int i = fragments.size() - 1; i >= 0; --i) {
                Fragment fragment = (Fragment) fragments.get(i);
                if (fragment != null) {
                    result.add(fragment);
                }
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }
    public static void removeFragmentsByName(@NonNull FragmentManager fragmentManager, String sNameBy){
        List<Fragment> fragments = FragmentUtils.getFragments(fragmentManager);
        if (!fragments.isEmpty()){
            for (int i = fragments.size() - 1; i >= 0; --i){
                Fragment fragment = (Fragment) fragments.get(i);
                if (fragment != null){
                    String sName = fragment.getClass().getName();
                    boolean isContains = sName.toUpperCase().contains(sNameBy.toUpperCase());
                    if (isContains){
                        removeAllFragments(fragment.getChildFragmentManager());
                        removeFragment(fragment);
                    }
                }
            }
        }
    }
}
