package com.example.noticebook;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.os.AsyncTask;
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

    private View popupView;
    private EditText editTextTitle;
    private EditText editTextNote;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonHigh;
    private RadioButton radioButtonMedium;
    private Button buttonAdd;
    private Button buttonCancel;
    private PopupWindow popupWindow;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerViewNotices);

        notices = App.getInstance().getDatabase().noticeDao().getNotices();
        notices.sort(Comparator.comparing(Notice::getPriority));
        noticeAdapter = new NoticeAdapter(notices);

        recyclerView.setAdapter(noticeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        noticeAdapter.setOnItemClickListener(position -> {
            showPopUpWidowEdit(this.recyclerView, position);
        });

        noticeAdapter.setOnItemLongClickListener(position -> {
            Notice noticeToDelete = notices.get(position);
            noticeToDelete.setId(App.getInstance().getDatabase().noticeDao().getId(noticeToDelete.info, noticeToDelete.title, noticeToDelete.priority.getValue()));
            new NoticeDeleteTask().execute(noticeToDelete);
            notices.remove(position);
        });
        button.setOnClickListener(this::showPopupWindow);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setView(final View view){
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.note_editor, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        editTextTitle = popupView.findViewById(R.id.editTextTitle);
        editTextNote = popupView.findViewById(R.id.editTextNote);

        radioButtonLow = popupView.findViewById(R.id.radioLow);
        radioButtonHigh = popupView.findViewById(R.id.radioHigh);
        radioButtonMedium = popupView.findViewById(R.id.radioMedium);

        buttonAdd = popupView.findViewById(R.id.buttonAddNote);
        buttonCancel = popupView.findViewById(R.id.buttonCancel);

        buttonCancel.setOnClickListener(v -> {
            notices.sort(Comparator.comparing(Notice::getPriority));
            noticeAdapter.notifyDataSetChanged();
            popupWindow.dismiss();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showPopUpWidowEdit(final View view, int position){
        setView(view);
        editTextNote.setText(notices.get(position).getInfo());
        editTextTitle.setText(notices.get(position).getTitle());

        buttonAdd.setText("Save changes");

        buttonAdd.setOnClickListener(v -> {
            Priority priority = Priority.HIGH;
            if (radioButtonMedium.isChecked()){
                priority = Priority.MEDIUM;
            } else if (radioButtonLow.isChecked()){
                priority = Priority.LOW;
            }
            Notice notice = new Notice(editTextTitle.getText().toString(), editTextNote.getText().toString(), priority);
            Notice oldNotice = notices.get(position);
            notice.setId(App.getInstance().getDatabase().noticeDao().getId(oldNotice.info, oldNotice.title, oldNotice.priority.getValue()));
            new NoticeUpdateTask().execute(notice);
            notices.set(position, notice);
            popupWindow.dismiss();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showPopupWindow(final View view) {
        setView(view);

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
            new NoticeInsertTask().execute(notice);
            notices.add(notice);
            popupWindow.dismiss();
        });
    }

    private class NoticeInsertTask extends AsyncTask<Notice, Void, Void> {
        @Override
        protected Void doInBackground(Notice... notices) {
            App.getInstance().getDatabase().noticeDao().insert(notices[0]);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notices.sort(Comparator.comparing(Notice::getPriority));
            noticeAdapter.notifyDataSetChanged();
        }
    }

    private class NoticeDeleteTask extends AsyncTask<Notice, Void, Void> {
        @Override
        protected Void doInBackground(Notice... notices) {
            App.getInstance().getDatabase().noticeDao().delete(notices[0]);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notices.sort(Comparator.comparing(Notice::getPriority));
            noticeAdapter.notifyDataSetChanged();
        }
    }

    private class NoticeUpdateTask extends AsyncTask<Notice, Void, Void> {
        @Override
        protected Void doInBackground(Notice... notices) {
            App.getInstance().getDatabase().noticeDao().update(notices[0]);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notices.sort(Comparator.comparing(Notice::getPriority));
            noticeAdapter.notifyDataSetChanged();
        }
    }
}