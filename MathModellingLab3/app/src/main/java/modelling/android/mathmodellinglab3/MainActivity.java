package modelling.android.mathmodellinglab3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends SingleFragmentActivity {

    private static final String DRAWER_STATE = "drawerState";

    private static int mDrawerFocusState;    //пока таким образом решаем проблему, какой фрагмент открыть при возврате из таблиц
                                            //заодно решим из какой sql таблицы показывать результаты

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private RadioButton mRadioFunction;
    private RadioButton mRadioTable;
    private RadioGroup mRadioGroup;

    @Override
    protected Fragment createFragment() {
        return Lab3varOneFragment.newInstance();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRadioFunction = (RadioButton) findViewById(R.id.rb_function);
        mRadioTable = (RadioButton) findViewById(R.id.rb_table);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // set item as selected to persist highlight
                item.setChecked(true);
                mDrawerFocusState = item.getItemId();
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                replaceFragment(item.getItemId());

                return true;
            }
        });
        mNavigationView.setCheckedItem(R.id.nav_first);

        mDrawerFocusState = R.id.nav_first;

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        Toast.makeText(
                                MainActivity.this,
                                "Ничего не выбрано",
                                Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case R.id.rb_function:
                        Fragment fragmentFunction;
                        switch (mDrawerFocusState) {
                            case R.id.nav_first:
                                fragmentFunction = Lab3varOneFragment.newInstance();
                                break;
                            case R.id.nav_second:
                                fragmentFunction = Lab3varTwoFragment.newInstance();
                                break;
                            default:
                                fragmentFunction = Lab3varOneFragment.newInstance();
                                break;
                        }
                        replaceFunctionTableFragments(fragmentFunction);
                        break;
                    case R.id.rb_table:
                        Fragment fragmentTable = LaplasTableFragment.newInstance(mDrawerFocusState);
                        replaceFunctionTableFragments(fragmentTable);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_nodes_visibility:
                TableRow row = (TableRow) findViewById(R.id.table_row_nodes);
                if(row.getVisibility() == View.VISIBLE)
                    row.setVisibility(View.INVISIBLE);
                else
                    row.setVisibility(View.VISIBLE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(mNavigationView))
            mDrawerLayout.closeDrawer(mNavigationView);
        else
            super.onBackPressed();
    }

    private void replaceFunctionTableFragments(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    private void replaceFragment(int position){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;

        switch (position) {
            case R.id.nav_first:
                fragment = Lab3varOneFragment.newInstance();
                mRadioTable.setChecked(false);
                mRadioFunction.setChecked(true);
                break;
            case R.id.nav_second:
                fragment = Lab3varTwoFragment.newInstance();
                mRadioTable.setChecked(false);
                mRadioFunction.setChecked(true);
                break;
            default:
                fragment = Lab3varOneFragment.newInstance();
                break;
        }
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(DRAWER_STATE, mDrawerFocusState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDrawerFocusState = savedInstanceState.getInt(DRAWER_STATE);
    }
}
