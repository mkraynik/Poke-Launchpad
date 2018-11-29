package com.example.mjkra.poke;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NotificationItem> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView listTime;
        public TextView listDate;
        public Button delButton;
        public MyViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.listItem);
            listTime = (TextView) v.findViewById(R.id.listTime);
            listDate = (TextView) v.findViewById(R.id.listDate);
            delButton = (Button) v.findViewById(R.id.delete);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<NotificationItem> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listitem, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final NotificationItem myNotification = mDataset.get(position);
        holder.mTextView.setText(myNotification.getName());
        Calendar cal = myNotification.getCal();
        DateFormat sdf = new SimpleDateFormat("h:mm a");
        holder.listTime.setText(sdf.format(cal.getTime()));
        holder.listDate.setText((cal.get(Calendar.MONTH)+1) +"/"+ cal.get(Calendar.DAY_OF_MONTH) +"/"+ cal.get(Calendar.YEAR));
        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNotification.getAm().cancel(myNotification.getPt());
                mDataset.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}