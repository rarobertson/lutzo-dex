package max.com.lutzodex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created on 27/07/2015.
 */
public class AboutActivity extends Fragment {
/**
 * The fragment argument representing the section number for this
 * fragment.
 */
    private static final String ARG_SECTION_NUMBER = "section_about";

    private static AboutActivity m_aboutActivity;
/**
 * Returns a new instance of this fragment for the given section
 * number.
 */
    public static AboutActivity getInstance(int sectionNumber) {

        if (m_aboutActivity == null) {
            m_aboutActivity = new AboutActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            m_aboutActivity.setArguments(args);
        }
        return m_aboutActivity;
    }

    public AboutActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sobre_layout, container, false);

        return rootView;
    }

}
