package com.esp.alarm.ui.alarm.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.esp.alarm.R;
import com.esp.alarm.dto.Alarm;

import java.util.List;

import io.realm.RealmList;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder>
        implements AlarmListAdapterContract.Model, AlarmListAdapterContract.View {
    private RealmList<Alarm> mAlarmList = new RealmList<>();

    private AlarmListContract.Presenter mPresenter;

    public AlarmListAdapter(AlarmListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public AlarmListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_alarm, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmListAdapter.ViewHolder holder, int position) {
        Alarm alarm = getItem(position);
        holder.itemTime.setText(alarm.getTime());
        holder.itemToggleButton.setChecked(alarm.isTurnOn());
    }

    @Override
    public int getItemCount() {
        return mAlarmList.size();
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    @Override
    public Alarm getItem(int position) {
        return mAlarmList.get(position);
    }

    @Override
    public void setList(List<Alarm> alarmList) {
        mAlarmList.clear();
        mAlarmList.addAll(alarmList);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTime;
        private ToggleButton itemToggleButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTime = (TextView) itemView.findViewById(R.id.item_alarm_time);
            itemToggleButton = (ToggleButton) itemView.findViewById(R.id.item_alarm_toggle_button);
        }
    }
}
