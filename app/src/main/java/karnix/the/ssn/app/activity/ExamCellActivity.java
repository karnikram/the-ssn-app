package karnix.the.ssn.app.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import karnix.the.ssn.app.Fragments.ExamCellFeedFragment;
import karnix.the.ssn.ssnmachan.R;

public class ExamCellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_cell);

        Fragment examCellFeedFragment = new ExamCellFeedFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.examCellFrameLayout,examCellFeedFragment).commit();
    }
}
