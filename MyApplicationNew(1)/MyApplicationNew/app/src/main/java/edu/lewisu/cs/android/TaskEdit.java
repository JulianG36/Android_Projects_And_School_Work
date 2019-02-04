package edu.lewisu.cs.android;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class TaskEdit extends Activity {

	private EditText taskDesc;
	private Spinner spinner; 
	private CheckBox completeCheck;
	private Button confirmButton;
	private Long taskId;
	private DbAdapter dbHelper;
	private String[] priorities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//instantiate the dbhelper class and open the database
		dbHelper = new DbAdapter(this);
		dbHelper.open();

		setContentView(R.layout.edit_task);

		//get references to ui components
		taskDesc = (EditText)findViewById(R.id.editTextDescr);
		confirmButton = (Button) findViewById(R.id.confirmButton);
		spinner = (Spinner)findViewById(R.id.prioritySpinner);
		completeCheck = (CheckBox)findViewById(R.id.completeCheck);

		//set the dropdown text in the spinner
		priorities = getResources().getStringArray(R.array.priorities);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, priorities);
		spinner.setAdapter(adapter);
		spinner.setSelection(0);

		taskId = null;
		String buttonText;

		//if there is a save instanced use it to set the ui
		if(savedInstanceState != null) 
			taskId = (Long) savedInstanceState.getSerializable(DbAdapter.KEY_ROWID);

		//if there was an id passed use it to set the ui
		if (taskId == null) {     
			Bundle extras = getIntent().getExtras();
			if (extras != null) 
				taskId =  extras.getLong(DbAdapter.KEY_ROWID); 
		}
		
		//set the text on the button to "add" or "update"
		if(taskId != null)
			buttonText = getResources().getString(R.string.update);
		else
			buttonText = getResources().getString(R.string.add);
		confirmButton.setText(buttonText);

		//fill the form with data
		populateFields();

		//listener for button press
		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();

			}

		});
	}


	//retrieves a task from the db to populate the form
	private void populateFields() {
		if (taskId != null) {
			Cursor task = dbHelper.fetchTask(taskId);
			startManagingCursor(task);

			String desc = task.getString(
					task.getColumnIndexOrThrow(DbAdapter.DESCR_NAME));
			taskDesc.setText(desc);

			String priority = task.getString(
					task.getColumnIndexOrThrow(DbAdapter.PRIORITY_NAME));           

			//set the spinner to reflect the priority retrieved from the db
			for(int i=0; i < priorities.length; i++){
				if(priorities[i].equals(priority))
					spinner.setSelection(i);
			}

			int check = task.getInt(
					task.getColumnIndexOrThrow(DbAdapter.DONE_NAME));
			
			if(check == 1)
				completeCheck.setChecked(true);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(DbAdapter.KEY_ROWID, taskId);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}


	private void saveState() {
		String descr = taskDesc.getText().toString();
		String priority = priorities[spinner.getSelectedItemPosition()];
		int done=0; 
		if(completeCheck.isChecked())
			done = 1;

		//if new task data entered but not save, save it
		if (taskId == null && descr!="") {
			long id = dbHelper.createTask(descr, priority);
			if (id > 0) {
				taskId = id;
			}
		} else {  //otherwise save changes 
			dbHelper.updateTask(taskId, descr, priority, done);

		}
	}

}
