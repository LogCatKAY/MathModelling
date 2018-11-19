package modelling.android.mathmodellinglab2;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return Lab2MainFragment.newInstance();
    }
}
