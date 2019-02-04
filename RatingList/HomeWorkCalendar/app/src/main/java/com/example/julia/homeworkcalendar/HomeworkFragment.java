package com.example.julia.homeworkcalendar;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeworkFragment extends Fragment {
    Database db;
    private Button submitButton;
    private EditText DateDialogEditText;
    private EditText DueDateEditText;
    private EditText AssignedDateEditText;
    private EditText DescriptionEditText;
    private EditText CourseEditText;
    private CheckBox ReminderCheckBox;
    private final String TAG = "HomeworkFragment";
    final Calendar myCalendar = Calendar.getInstance();
    public HomeworkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_homework, container, false);
        submitButton = (Button) view.findViewById(R.id.mSubmitButton);
        DueDateEditText = (EditText) view.findViewById(R.id.mDueEditText);
        AssignedDateEditText = (EditText) view.findViewById(R.id.mAssignedEditText);
        CourseEditText = (EditText) view.findViewById(R.id.mCourseEditText);
        DescriptionEditText = (EditText) view.findViewById(R.id.mDescriptionEditText);
        ReminderCheckBox = (CheckBox) view.findViewById(R.id.mReminderCheck);
        db = new Database(getActivity());

        //Creates popup date picker when called and also updates the text in the edit text
        final  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(DateDialogEditText);
            }
        };
        //Listens for a click on the DueDate edit text then calls the DatePicker to popup once clicked
        DueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        //Used to have updateLabel change the correct editTextBased on which one was clicked
                        DateDialogEditText = DueDateEditText;
            }
        });
        //Other Date picker for Assigned Date
        AssignedDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        DateDialogEditText = AssignedDateEditText;
            }
        });
        //Submits and adds data to the Database once clicked
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checks if all Required fields are filled
                if(DescriptionEditText.getText().length() == 0 || AssignedDateEditText.getText().length() == 0 ||
                        DueDateEditText.getText().length() == 0 || CourseEditText.getText().length() == 0)
                {
                    Toast toast = Toast.makeText(getContext(),"Fill All Fields Before Submitting", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    //Submits everything to the Database
                    String DueDate = DueDateEditText.getText().toString();
                    String ClassAssigned = CourseEditText.getText().toString();
                    String Description = DescriptionEditText.getText().toString();
                    boolean Reminder = ReminderCheckBox.isChecked();
                    String AssignedDate = AssignedDateEditText.getText().toString();
                    CalendarObject calendarObject = new CalendarObject();
                    calendarObject.setAssignedDate(AssignedDate);
                    calendarObject.setClassAssigned(ClassAssigned);
                    calendarObject.setDueDate(DueDate);
                    calendarObject.setDescription(Description);
                    calendarObject.setReminder(Reminder);
                    boolean check = db.insertData(DueDate,ClassAssigned, Description, Reminder, AssignedDate);
                    //Checks wether or not it was subitted prints out Log whether it does or doesnt
                    insertCheck(check);
                    //Resets fields to blank
                    resetText();
                    Toast toast = Toast.makeText(getContext(), "Submitted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });



        // Inflate the layout for this fragment
        return view;
    }
    //Changes Date EditTexts
    private void updateLabel(EditText editText){
        String myformat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myformat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }
    //checks db.insert calls
    public void insertCheck(boolean check){
        if(check == true){
            Log.i(TAG, "Inserted Data");
        }
        else{
            Log.i(TAG, "Was not inserted");
        }
    }
    //resets texts to blank
    public void resetText(){
        CourseEditText.setText("");
        DueDateEditText.setText("");
        AssignedDateEditText.setText("");
        DescriptionEditText.setText("");
        ReminderCheckBox.setChecked(false);
    }

}
