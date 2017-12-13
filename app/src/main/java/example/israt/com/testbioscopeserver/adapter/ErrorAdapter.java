package example.israt.com.testbioscopeserver.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import example.israt.com.testbioscopeserver.R;
import example.israt.com.testbioscopeserver.model.ServerStatus;

/**
 * Created by HP on 12/7/2017.
 */

public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.MyViewHolder> {

    private Context mContext;
    private List<ServerStatus> albumList;
    String diff;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,duration,uptime;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.cardheader);
           count = (TextView) view.findViewById(R.id.carddate);
            duration = (TextView) view.findViewById(R.id.cardduration);
            uptime=(TextView)view.findViewById(R.id.cardheader1);
           // thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
           // overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public ErrorAdapter(Context mContext, List<ServerStatus> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view0fcard, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ServerStatus album = albumList.get(position);
        long timeinhour= Long.parseLong(album.getDownTime());

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd-HH:mm");
        Date resultdate = new Date(timeinhour);

try{
    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
    Date date1 = format.parse("08:00:12 pm");
    Date date2 = format.parse("05:30:12 pm");
    long time=Long.parseLong(album.getUpTime())- Long.parseLong(album.getDownTime());
    int hours = (int) (time/(1000 * 60 * 60));
    int mins = (int) (time/(1000*60)) % 60;
    int secs = (int) (time/(1000)) % 60;
    if(hours==0)
    {

        diff =  mins+ " min :" + secs + " secs";
    }else {

        diff = hours +" h" + " :" + mins+ " min :" + secs + " secs";
    }
}catch (Exception e)
{
    diff="Still down";
}

        String timeinformatted=sdf.format(resultdate);
        String[] separated = timeinformatted.split("-");
        holder.title.setText(separated[1]);
        holder.count.setText(separated[0]);
        holder.duration.setText(diff);
        try {
            long timeupinhour= Long.parseLong(album.getUpTime());
            Date resultdate2 = new Date(timeupinhour);
            String timeinformatted2=sdf.format(resultdate2);
            String[] separated2 = timeinformatted2.split("-");
            holder.uptime.setText(separated2[1]);
        }catch (Exception e)
        {
            holder.uptime.setText("~~");
        }

        //holder.count.setText(album.getNumOfSongs() + " songs");

        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
    public void update(ArrayList<ServerStatus> datas){
        albumList.clear();
        albumList.addAll(datas);
        notifyDataSetChanged();
    }
    /**
     * Showing popup menu when tapping on 3 dots
     */
  /*  private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }*/

    /**
     * Click listener for popup menu items
     */
   /* class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }*/
}