package org.rmit.mindfulapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEditActivity extends AppCompatActivity {
    private EditText addName;
    private EditText addDuration;
    private Button saveButton;
    private Button cancelButton;
    private String requestType;
    private Integer sentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_main);
        addName = findViewById(R.id.inputName);
        addDuration = findViewById(R.id.inputDuration);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        Intent intent = getIntent();
        requestType = (String) intent.getExtras().get("requestType");

        if(requestType.equals("edit")){
            addName.setText(intent.getExtras().get("sentName").toString());
            addDuration.setText(intent.getExtras().get("sentDuration").toString());
            sentId = (Integer) intent.getExtras().get("sentID");
        }
    }

    public void selectionReader(View view){
        switch (view.getId()){
            case R.id.saveButton:
                if(addName.getText().toString().matches("") || addDuration.getText().toString().matches("")){
                    final AlertDialog alertDialog = new AlertDialog.Builder(AddEditActivity.this).create();
                    alertDialog.setTitle("You must fill the necessary field");
                    alertDialog.setMessage("You must fill the necessary field");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();

                        }
                    });
                    alertDialog.show();
                }else{
                    if(requestType.equals("edit")){
                        String returnName = addName.getText().toString();
                        Integer returnDuration = Integer.parseInt(addDuration.getText().toString());
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("returnName", returnName);
                        intent.putExtra("returnDuration", returnDuration);
                        intent.putExtra("returnID", sentId);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else {
                        String returnName = addName.getText().toString();
                        Integer returnDuration = Integer.parseInt(addDuration.getText().toString());
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("returnName", returnName);
                        intent.putExtra("returnDuration", returnDuration);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
                break;
            case R.id.cancelButton:
                finish();
                break;

        }
    }
}
