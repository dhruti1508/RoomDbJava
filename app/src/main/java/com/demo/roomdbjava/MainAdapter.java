package com.demo.roomdbjava;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>
{
    //initialize variable
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    //create constructor
    public MainAdapter(Activity context,List<MainData> dataList)
    {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    //@org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        //initialize main data
        MainData data = dataList.get(position);
        //initialize database
        database = RoomDB.getInstance(context);

        holder.text_view.setText(data.getText());

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                MainData d = dataList.get(holder.getAdapterPosition());

                int sID = d.getID();
                String sText = d.getText();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                EditText edt_text = dialog.findViewById(R.id.edt_text);
                edt_text.setText(sText);

                Button btn_update = dialog.findViewById(R.id.btn_update);
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();

                        //get updated text from edittext
                        String uText = edt_text.getText().toString().trim();

                        //update text in database
                        database.mainDao().update(sID,uText);

                        //notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                MainData d = dataList.get(holder.getAdapterPosition());

                //delete text from database
                database.mainDao().delete(d);

                //notify when data is deleted
                int position  = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //initialize variable
        TextView text_view;
        ImageView img_edit,img_delete;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //assign variable
             text_view = itemView.findViewById(R.id.text_view);
             img_edit = itemView.findViewById(R.id.img_edit);
             img_delete = itemView.findViewById(R.id.img_delete);
        }
    }
}
