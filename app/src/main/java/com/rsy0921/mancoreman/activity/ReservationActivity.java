package com.rsy0921.mancoreman.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.common.CalendarSaturdayDecorator;
import com.rsy0921.mancoreman.common.CalendarSundayDecorator;

import java.util.Calendar;

public class ReservationActivity extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;
    ChipGroup chipGroup;
    BottomSheetDialog bottomSheetDialog;
    String sToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_activity);


        materialCalendarView = findViewById(R.id.calender_reservation);
        chipGroup = findViewById(R.id.chip_group_reservation);
        bottomSheetDialog = new BottomSheetDialog(ReservationActivity.this);

        //-----------------------------달력------------------------------//
        //주말색상
        materialCalendarView.addDecorators( new CalendarSaturdayDecorator(), new CalendarSundayDecorator());

        //상단 년월
//        materialCalendarView.setTopbarVisible(false);

        Calendar minCalendar = Calendar.getInstance();
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.add(Calendar.MONTH, 1);
//        maxCalendar.add(Calendar.DATE, 14);

        materialCalendarView.state().edit()
                .setMinimumDate(minCalendar)
                .setMaximumDate(maxCalendar)
                .setFirstDayOfWeek(minCalendar.get(Calendar.DAY_OF_WEEK))
                .commit();

        materialCalendarView.setSelectedDate(CalendarDay.today());
        materialCalendarView.setDynamicHeightEnabled(true);

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                sToday = date.getYear() + "년 " + date.getMonth() + "월 " + date.getDay() + "일";
            }
        });


        //---------------------------시간------------------------------//
        Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.reservation_card_time, null, false);
        chip.setText("10:30");
        chip.setCheckable(true);
        chipGroup.addView(chip);

        chip = (Chip) this.getLayoutInflater().inflate(R.layout.reservation_card_time, null, false);
        chip.setText("11:30");
        chip.setCheckable(true);
        chipGroup.addView(chip);

        chip = (Chip) this.getLayoutInflater().inflate(R.layout.reservation_card_time, null, false);
        chip.setText("12:30");
        chip.setCheckable(true);
        chipGroup.addView(chip);

        chip = (Chip) this.getLayoutInflater().inflate(R.layout.reservation_card_time, null, false);
        chip.setText("13:30");
        chip.setCheckable(true);
        chipGroup.addView(chip);

        chip = (Chip) this.getLayoutInflater().inflate(R.layout.reservation_card_time, null, false);
        chip.setText("14:30");
        chip.setCheckable(true);
        chipGroup.addView(chip);

        chip = (Chip) this.getLayoutInflater().inflate(R.layout.reservation_card_time, null, false);
        chip.setText("15:30");
        chip.setCheckable(true);
        chipGroup.addView(chip);

        chip = (Chip) this.getLayoutInflater().inflate(R.layout.reservation_card_time, null, false);
        chip.setText("16:30");
        chip.setCheckable(true);
        chipGroup.addView(chip);

        chip = (Chip) this.getLayoutInflater().inflate(R.layout.reservation_card_time, null, false);
        chip.setText("17:30");
        chip.setCheckable(true);
        chipGroup.addView(chip);

        chip = (Chip) this.getLayoutInflater().inflate(R.layout.reservation_card_time, null, false);
        chip.setText("18:30");
        chip.setCheckable(true);
        chipGroup.addView(chip);



        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {

                Chip chip1 = group.findViewById(checkedId);

                if(checkedId != -1){

                    sToday =  materialCalendarView.getSelectedDate().getYear() + "년 " + materialCalendarView.getSelectedDate().getMonth() + "월 " + materialCalendarView.getSelectedDate().getDay() + "일";


                    bottomSheetDialog.setContentView(R.layout.reservation_dialog);
                    TextView txtDate =  bottomSheetDialog.findViewById(R.id.txt_reservation_date);
                    TextView txtTime =  bottomSheetDialog.findViewById(R.id.txt_reservation_time);
                    txtDate.setText(sToday);
                    txtTime.setText(chip1.getText());
                    bottomSheetDialog.show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}