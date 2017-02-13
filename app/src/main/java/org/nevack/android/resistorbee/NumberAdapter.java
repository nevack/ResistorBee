package org.nevack.android.resistorbee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

public class NumberAdapter extends ArrayAdapter<ColorCode> {

    private final DecimalFormat format = new DecimalFormat("0.##");

    public NumberAdapter(@NonNull Context context) {
        super(context, R.layout.spinner_item, R.id.colorName);

        add(new ColorCode(getContext().getResources().getColor(R.color.black), "Black", 0));
        add(new ColorCode(getContext().getResources().getColor(R.color.brown), "Brown", 1));
        add(new ColorCode(getContext().getResources().getColor(R.color.red), "Red", 2));
        add(new ColorCode(getContext().getResources().getColor(R.color.orange), "Orange", 3));
        add(new ColorCode(getContext().getResources().getColor(R.color.yellow), "Yellow", 4));
        add(new ColorCode(getContext().getResources().getColor(R.color.green), "Green", 5));
        add(new ColorCode(getContext().getResources().getColor(R.color.blue), "Blue", 6));
        add(new ColorCode(getContext().getResources().getColor(R.color.violet), "Violet", 7));
        add(new ColorCode(getContext().getResources().getColor(R.color.grey), "Grey", 8));
        add(new ColorCode(getContext().getResources().getColor(R.color.white), "White", 9));
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView name = (TextView) root.findViewById(R.id.colorName);
        RoundColorView color = (RoundColorView) root.findViewById(R.id.color);
        name.setText(getItem(position).getName());
        color.setColor(getItem(position).getColor());
        return root;
    };

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView name = (TextView) root.findViewById(R.id.colorName);
        RoundColorView color = (RoundColorView) root.findViewById(R.id.color);
        name.setText("Digit " + format.format(getItem(position).getValue()));
        color.setColor(getItem(position).getColor());
        return root;
    }
}
