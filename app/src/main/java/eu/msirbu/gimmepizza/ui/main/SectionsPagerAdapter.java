package eu.msirbu.gimmepizza.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import eu.msirbu.gimmepizza.ui.fragments.CartFragment;
import eu.msirbu.gimmepizza.ui.fragments.MenuFragment;
import eu.msirbu.gimmepizza.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment shownFragment = null;
        switch (position) {
            case 0:
                shownFragment = new MenuFragment(); // If first tab is active, show the menu
                break;
            case 1:
                shownFragment = new CartFragment(); // If second tab is active, show the cart
                break;
        }
        return shownFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}