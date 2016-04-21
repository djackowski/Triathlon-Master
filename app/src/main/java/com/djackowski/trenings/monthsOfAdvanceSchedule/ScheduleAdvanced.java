package com.djackowski.trenings.monthsOfAdvanceSchedule;

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
import com.djackowski.trenings.monthsOfBasicSchedule.FirstMonthFragment;
import com.djackowski.trenings.monthsOfBasicSchedule.SecondMonthFragment;
import com.djackowski.trenings.monthsOfBasicSchedule.ThirdMonthFragment;

/**
 * Created by Damcios on 2016-02-16.
 */
public class ScheduleAdvanced extends AppCompatActivity {
    ScrollView sv;
    Button scroll;
    TextView month, toolbarTV;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_schedule);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        month = (TextView) findViewById(R.id.month);
        toolbarTV = (TextView) findViewById(R.id.toolbarTextView);
        toolbarTV.setText("ZAAWANSOWANY");
        sv = (ScrollView) findViewById(R.id.scrollViewSchedule);
        scroll = (Button) findViewById(R.id.buttonScrollDown);
        if (findViewById(R.id.advancedScheduleContainer) != null) {

            FirstMonth firstFragment = new FirstMonth();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.advancedScheduleContainer, firstFragment).commit();
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
            case R.id.buttonNextMonthFromFirstAdvanced:
                SecondMonth secondMonthFragment = new SecondMonth();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.advancedScheduleContainer, secondMonthFragment);
                transaction.commit();
                break;
            case R.id.buttonNextMonthFromSecondAdvanced:
                ThirdMonth thirdMonthFragment = new ThirdMonth();
                getSupportFragmentManager().beginTransaction().replace(R.id.advancedScheduleContainer, thirdMonthFragment).commit();
                break;
            case R.id.buttonPreviouslyMonthFromSecondAdvanced:
                FirstMonth firstMonthFragment = new FirstMonth();
                getSupportFragmentManager().beginTransaction().replace(R.id.advancedScheduleContainer, firstMonthFragment).commit();
                break;
            case R.id.buttonPreviouslyMonthFromThirdAdvanced:
                getSupportFragmentManager().beginTransaction().replace(R.id.advancedScheduleContainer, new SecondMonth()).commit();
                break;
            //region first month
            //region running
            case R.id.button1MonthWeek1Czw:
                intent.putExtra("DYSTANS", 6l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek2Czw:
                intent.putExtra("DYSTANS", 6l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek3Sr:
                intent.putExtra("DYSTANS", 6l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek4Sr:
                intent.putExtra("DYSTANS", 7l);
                startActivity(intent);
                break;
            //endregion
            //region riding
            case R.id.button1MonthWeek1Sob:
                intent.putExtra("DYSTANS", 45l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek2Sob:
                intent.putExtra("DYSTANS", 45l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek3Sob:
                intent.putExtra("DYSTANS", 40l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek4Sob:
                intent.putExtra("DYSTANS", 45l);
                startActivity(intent);
                break;
            //endregion
            //region swimming
            case R.id.button1MonthWeek1Pon:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek2Pon:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek3Wt:
                intent.putExtra("DYSTANS", 1l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek4Wt:
                intent.putExtra("DYSTANS", 1l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek3Czw:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button1MonthWeek4Czw:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            //endregion
            //endregion

            //region second month
            //region running
            case R.id.button2MonthWeek1Pon:
                intent.putExtra("DYSTANS", 8l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek2Pon:
                intent.putExtra("DYSTANS", 8l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek3Pon:
                intent.putExtra("DYSTANS", 9l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek4Pon:
                intent.putExtra("DYSTANS", 8l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek1Sr:
                intent.putExtra("DYSTANS", 8l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek2Sr:
                intent.putExtra("DYSTANS", 9l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek3Sr:
                intent.putExtra("DYSTANS", 8l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek4Sr:
                intent.putExtra("DYSTANS", 9l);
                startActivity(intent);
                break;
            //endregion

            //region swimming
            case R.id.button2MonthWeek1Wt:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek2Wt:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek3Wt:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek4Wt:
                intent.putExtra("DYSTANS", 3l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek1Czw:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek2Czw:
                intent.putExtra("DYSTANS", 3l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek3Czw:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek4Czw:
                intent.putExtra("DYSTANS", 3l);
                startActivity(intent);
                break;
            //endregion

            //region riding
            case R.id.button2MonthWeek1Sob:
                intent.putExtra("DYSTANS", 45l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek2Sob:
                intent.putExtra("DYSTANS", 50l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek3Sob:
                intent.putExtra("DYSTANS", 40l);
                startActivity(intent);
                break;
            case R.id.button2MonthWeek4Sob:
                intent.putExtra("DYSTANS", 45l);
                startActivity(intent);
                break;
            //endregion

            //endregion


            //region third month
            //region running
            case R.id.button3MonthWeek1Pon:
                intent.putExtra("DYSTANS", 8l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek1Pt:
                intent.putExtra("DYSTANS", 10l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Pon:
                intent.putExtra("DYSTANS", 8l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Pt:
                intent.putExtra("DYSTANS", 6l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Pon:
                intent.putExtra("DYSTANS", 7l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Pt:
                intent.putExtra("DYSTANS", 6l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek4Pon:
                intent.putExtra("DYSTANS", 7l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek4Czw:
                intent.putExtra("DYSTANS", 6l);
                startActivity(intent);
                break;

            //endregion

            //region swimming
            case R.id.button3MonthWeek1Wt:
                intent.putExtra("DYSTANS", 3l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek1Czw:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Wt:
                intent.putExtra("DYSTANS", 3l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Czw:
                intent.putExtra("DYSTANS", 3l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Wt:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Czw:
                intent.putExtra("DYSTANS", 1l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek4Wt:
                intent.putExtra("DYSTANS", 2l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek4Sob:
                intent.putExtra("DYSTANS", 3l);
                startActivity(intent);
                break;
            //endregion

            //region riding
            case R.id.button3MonthWeek1Sr:
                intent.putExtra("DYSTANS", 80l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek1Sob:
                intent.putExtra("DYSTANS", 70l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Sr:
                intent.putExtra("DYSTANS",60l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek2Sob:
                intent.putExtra("DYSTANS", 40l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Sr:
                intent.putExtra("DYSTANS", 50l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek3Sob:
                intent.putExtra("DYSTANS", 30l);
                startActivity(intent);
                break;
            case R.id.button3MonthWeek4Sr:
                intent.putExtra("DYSTANS", 40l);
                startActivity(intent);
                break;
            //endregion

            //endregion



            default:
                break;
        }
    }
}
