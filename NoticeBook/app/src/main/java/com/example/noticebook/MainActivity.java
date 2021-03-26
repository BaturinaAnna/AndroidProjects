package com.example.noticebook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton button;
    private List<Notice> notices = new ArrayList<>();
    private NoticeAdapter noticeAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerViewNotices);
        noticeAdapter = new NoticeAdapter(notices);

        recyclerView.setAdapter(noticeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        noticeAdapter.setOnItemClickListener(position -> {
            Toast toast = Toast.makeText(getApplicationContext(),
                    notices.get(position).toString(), Toast.LENGTH_SHORT);
            toast.show();
        });

        button.setOnClickListener(this::showPopupWindow);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showPopupWindow(final View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.note_editor, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        EditText editTextTitle = popupView.findViewById(R.id.editTextTitle);
        EditText editTextNote = popupView.findViewById(R.id.editTextNote);

        RadioButton radioButtonLow = popupView.findViewById(R.id.radioLow);
        RadioButton radioButtonHigh = popupView.findViewById(R.id.radioHigh);
        RadioButton radioButtonMedium = popupView.findViewById(R.id.radioMedium);

        Button buttonAdd = popupView.findViewById(R.id.buttonAddNote);
        Button buttonCancel = popupView.findViewById(R.id.buttonCancel);

        editTextNote.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                editTextNote.setText("");
            }
        });

        editTextTitle.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                editTextTitle.setText("");
            }
        });

        buttonAdd.setOnClickListener(v -> {
            Priority priority = Priority.HIGH;
            if (radioButtonMedium.isChecked()){
                priority = Priority.MEDIUM;
            } else if (radioButtonLow.isChecked()){
                priority = Priority.LOW;
            }
            Notice notice = new Notice(editTextTitle.getText().toString(), editTextNote.getText().toString(), priority);
            notices.add(notice);
            App.getInstance().getDatabase().noticeDao().insert(notice);
            notices.sort(Comparator.comparing(Notice::getPriority));
            noticeAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
        });

        buttonCancel.setOnClickListener(v -> {
            popupWindow.dismiss();
            NoticeAdapter noticeAdapter1 = new NoticeAdapter(notices);
            recyclerView.setAdapter(noticeAdapter1);
            notices.sort(Comparator.comparing(Notice::getPriority));
            noticeAdapter1.notifyDataSetChanged();
        });

        radioButtonHigh.setOnClickListener(v -> {
            radioButtonLow.setChecked(false);
            radioButtonMedium.setChecked(false);
        });

        radioButtonMedium.setOnClickListener(v -> {
            radioButtonLow.setChecked(false);
            radioButtonHigh.setChecked(false);
        });

        radioButtonLow.setOnClickListener(v -> {
            radioButtonHigh.setChecked(false);
            radioButtonMedium.setChecked(false);
        });
    }
}