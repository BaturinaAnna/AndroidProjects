package com.example.noticebook;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    private final List<Notice> notices;

    public NoticeAdapter(List<Notice> notices) {
        this.notices = notices;
    }

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notice notice = notices.get(position);
        holder.titleView.setText(notice.getTitle());
        holder.infoView.setText(notice.getInfo());
        switch (notice.getPriority()){
            case LOW:
                holder.priorityView.setImageResource(R.drawable.green);
                break;
            case MEDIUM:
                holder.priorityView.setImageResource(R.drawable.orange);
                break;
            case HIGH:
                holder.priorityView.setImageResource(R.drawable.red);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView titleView;
        final TextView infoView;
        final ImageView priorityView;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            titleView = itemView.findViewById(R.id.textViewTitle);
            infoView = itemView.findViewById(R.id.textViewInfo);
            priorityView = itemView.findViewById(R.id.imageViewPriority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
