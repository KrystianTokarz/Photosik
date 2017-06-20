package layout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import pl.silnepalce.photosik.R;


public class BorderFragment extends Fragment {


    private FragmentBorderListener activityCommander;

    public interface  FragmentBorderListener{
        void drawBorder();
        void clearBorder();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_border, container, false);
        final ImageButton drawBorderButton = (ImageButton) view.findViewById(R.id.drawBorderButton);
        ImageButton clearBorderButton = (ImageButton) view.findViewById(R.id.clearBorderButton);
        drawBorderButton.setEnabled(true);
        drawBorderButton.setImageResource(R.drawable.ic_zoom_out_map_black_24dp);

        drawBorderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawBorderButton.setImageResource(R.drawable.ic_cancel_black_24dp);
                drawBorderButton.setEnabled(false);
                activityCommander.drawBorder();

            }
        });
        clearBorderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawBorderButton.setEnabled(true);
                drawBorderButton.setImageResource(R.drawable.ic_zoom_out_map_black_24dp);
                activityCommander.clearBorder();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCommander = (FragmentBorderListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }



}
