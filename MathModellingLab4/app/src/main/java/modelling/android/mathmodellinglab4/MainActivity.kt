package modelling.android.mathmodellinglab4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment

class MainActivity : AppCompatActivity() {

    private var mFragmentFirst : Fragment? = null
    private var mFragmentSecond : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager =  supportFragmentManager
        val firstFragment = fragmentManager.findFragmentById(R.id.fragment_container_first)
        val secondFragment = fragmentManager.findFragmentById(R.id.fragment_container_second)

        if (firstFragment == null) {
            mFragmentFirst = FirstFunctionFragment.newInstance()
            fragmentManager.beginTransaction()
                .add(R.id.fragment_container_first, mFragmentFirst)
                .commit()
        }
        if (secondFragment == null) {
            mFragmentSecond = SecondFunctionFragment.newInstance()
            fragmentManager.beginTransaction()
                .add(R.id.fragment_container_second, mFragmentSecond)
                .commit()
        }
    }
}
