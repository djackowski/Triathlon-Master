package com.djackowski.trenings.monthsOfBasicSchedule;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.djackowski.R;

/**
 * Created by Damcios on 2015-12-22.
 */
public class Schedule extends AppCompatActivity {
    ScrollView sv;
    Button scroll;
    TextView month, toolbarTV;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_schedule);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        month = (TextView) findViewById(R.id.month);
        toolbarTV = (TextView) findViewById(R.id.toolbarTextView);
        toolbarTV.setText("POCZĄTKUJĄCY");
        sv = (ScrollView) findViewById(R.id.scrollViewSchedule);
        scroll = (Button) findViewById(R.id.buttonScrollDown);


        if (findViewById(R.id.basicScheduleContainer) != null) {

            FirstMonthFragment firstFragment = new FirstMonthFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.basicScheduleContainer, firstFragment).commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        Intent intent = new Intent("android.intent.action.FREERIDE");
        switch (view.getId()) {
            case R.id.buttonNextMonthFromFirstBasic:
                SecondMonthFragment secondMonthFragment = new SecondMonthFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.basicScheduleContainer, secondMonthFragment);
                transaction.commit();
                break;
            case R.id.buttonNextMonthFromSecondBasic:
                ThirdMonthFragment thirdMonthFragment = new ThirdMonthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.basicScheduleContainer, thirdMonthFragment).commit();
                break;
            case R.id.buttonPreviouslyMonthFromSecondBasic:
                FirstMonthFragment firstMonthFragment = new FirstMonthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.basicScheduleContainer, firstMonthFragment).commit();
                break;
            case R.id.buttonPreviouslyMonthFromThirdBasic:
                getSupportFragmentManager().beginTransaction().replace(R.id.basicScheduleContainer, new SecondMonthFragment()).commit();
                break;
            //region first month
            //region running
            case R.id.button1MonthWeek1Czw:
                intent.putExtra("DYSTANS", 5l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek2Czw:
                intent.putExtra("DYSTANS", 5l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek3Sr:
                intent.putExtra("DYSTANS", 5l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek4Sr:
                intent.putExtra("DYSTANS", 6l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            //endregion
            //region riding
            case R.id.button1MonthWeek1Sob:
                intent.putExtra("DYSTANS", 30l);
                intent.putExtra("COMPETITION", "KOLARSTWO");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek2Sob:
                intent.putExtra("DYSTANS", 30l);
                intent.putExtra("COMPETITION", "KOLARSTWO");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek3Sob:
                intent.putExtra("DYSTANS", 20l);
                intent.putExtra("COMPETITION", "KOLARSTWO");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek4Sob:
                intent.putExtra("DYSTANS", 35l);
                intent.putExtra("COMPETITION", "KOLARSTWO");
                startActivity(intent);
                break;
            //endregion
            //region swimming
            case R.id.button1MonthWeek1Pon:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek2Pon:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek3Wt:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek4Wt:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek3Czw:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button1MonthWeek4Czw:
                intent.putExtra("DYSTANS", 2l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            //endregion
            //endregion

            //region second month
            //region running
            case R.id.button2MonthWeek1Pon:
                intent.putExtra("DYSTANS", 6l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek2Pon:
                intent.putExtra("DYSTANS", 6l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek3Pon:
                intent.putExtra("DYSTANS", 7l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek4Pon:
                intent.putExtra("DYSTANS", 6l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek1Sr:
                intent.putExtra("DYSTANS", 6l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek2Sr:
                intent.putExtra("DYSTANS", 7l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek3Sr:
                intent.putExtra("DYSTANS", 6l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek4Sr:
                intent.putExtra("DYSTANS", 7l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            //endregion

            //region swimming
            case R.id.button2MonthWeek1Wt:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek2Wt:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek3Wt:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek4Wt:
                intent.putExtra("DYSTANS", 2l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek1Czw:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek2Czw:
                intent.putExtra("DYSTANS", 2l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek3Czw:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button2MonthWeek4Czw:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            //endregion

            //region riding
            case R.id.button2MonthWeek1Sob:
                intent.putExtra("DYSTANS", 40l);
                intent.putExtra("COMPETITION", "KOLARSTWO");

                startActivity(intent);
                break;
            case R.id.button2MonthWeek2Sob:
                intent.putExtra("DYSTANS", 35l);
                intent.putExtra("COMPETITION", "KOLARSTWO");

                startActivity(intent);
                break;
            case R.id.button2MonthWeek3Sob:
                intent.putExtra("DYSTANS", 40l);
                intent.putExtra("COMPETITION", "KOLARSTWO");

                startActivity(intent);
                break;
            case R.id.button2MonthWeek4Sob:
                intent.putExtra("DYSTANS", 40l);
                intent.putExtra("COMPETITION", "KOLARSTWO");

                startActivity(intent);
                break;
            //endregion

            //endregion


            //region third month
            //region running
            case R.id.button3MonthWeek1Pon:
                intent.putExtra("DYSTANS", 7l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek1Pt:
                intent.putExtra("DYSTANS", 8l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Pon:
                intent.putExtra("DYSTANS", 7l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Pt:
                intent.putExtra("DYSTANS", 6l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Pon:
                intent.putExtra("DYSTANS", 5l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Pt:
                intent.putExtra("DYSTANS", 6l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek4Pon:
                intent.putExtra("DYSTANS", 7l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek4Czw:
                intent.putExtra("DYSTANS", 5l);
                intent.putExtra("COMPETITION", "BIEG");
                startActivity(intent);
                break;

            //endregion

            //region swimming
            case R.id.button3MonthWeek1Wt:
                intent.putExtra("DYSTANS", 2l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek1Czw:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Wt:
                intent.putExtra("DYSTANS", 2l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Czw:
                intent.putExtra("DYSTANS", 2l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Wt:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Czw:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek4Wt:
                intent.putExtra("DYSTANS", 1l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            case R.id.button3MonthWeek4Sob:
                intent.putExtra("DYSTANS", 2l);
                intent.putExtra("COMPETITION", "PLYWANIE");
                startActivity(intent);
                break;
            //endregion

            //region riding
            case R.id.button3MonthWeek1Sr:
                intent.putExtra("DYSTANS", 35l);
                intent.putExtra("COMPETITION", "KOLARSTWO");

                startActivity(intent);
                break;
            case R.id.button3MonthWeek1Sob:
                intent.putExtra("DYSTANS", 50l);
                intent.putExtra("COMPETITION", "KOLARSTWO");

                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Sr:
                intent.putExtra("COMPETITION", "KOLARSTWO");

                intent.putExtra("DYSTANS", 30l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Sob:
                intent.putExtra("DYSTANS", 40l);
                intent.putExtra("COMPETITION", "KOLARSTWO");

                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Sr:
                intent.putExtra("DYSTANS", 25l);
                intent.putExtra("COMPETITION", "KOLARSTWO");

                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Sob:
                intent.putExtra("DYSTANS", 30l);
                intent.putExtra("COMPETITION", "KOLARSTWO");

                startActivity(intent);
                break;
            case R.id.button3MonthWeek4Sr:
                intent.putExtra("DYSTANS", 25l);
                intent.putExtra("COMPETITION", "KOLARSTWO");

                startActivity(intent);
                break;
            //endregion

            //endregion


            default:
                break;
        }
    }
}
