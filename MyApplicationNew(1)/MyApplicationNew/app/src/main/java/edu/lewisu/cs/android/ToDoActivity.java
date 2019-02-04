package edu.lewisu.cs.android;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ToDoActivity extends ListActivity {
	private Cursor tasksCursor;
	DbAdapter dbHelper;
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
    	dbHelper = new DbAdapter(this);
    	dbHelper.open();
        fillData();
        registerForContextMenu(getListView());
    }
    
    private void fillData() {
        // Get all of the rows from the database and create the item list
        tasksCursor = dbHelper.fetchIncompleteTasks();
        startManagingCursor(tasksCursor);

        // Create an array to specify the fields we want to display in the list 
        String[] from = new String[]{DbAdapter.PRIORITY_NAME, DbAdapter.DESCR_NAME};

        // add an array of the fields we want to bind those fields to 
        int[] to = new int[]{R.id.priorityText, R.id.taskText};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter tasks = 
            new SimpleCursorAdapter(this, R.layout.task_row, tasksCursor, from, to);
        setListAdapter(tasks);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_options_menu, menu);
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_add:
                createTask();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
    

    private void createTask() {
        Intent i = new Intent(this, TaskEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, TaskEdit.class);
        i.putExtra(DbAdapter.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    } 



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.menu_delete:
            AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            dbHelper.deleteTask(info.id);
            fillData();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}