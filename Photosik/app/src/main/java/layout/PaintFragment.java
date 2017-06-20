package layout;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import pl.silnepalce.photosik.R;


public class PaintFragment extends Fragment {

    private FragmentPaintListener activityCommander;

    public interface  FragmentPaintListener{
        void paintAction(int number);
        void cancelLastActions();
        void clearAllActions();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCommander = (FragmentPaintListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paint, container, false);
        ImageButton blueButton = (ImageButton) view.findViewById(R.id.blueButton);
        ImageButton yellowButton = (ImageButton) view.findViewById(R.id.yellowButton);
        ImageButton blackButton = (ImageButton) view.findViewById(R.id.blackButton);
        ImageButton greenButton = (ImageButton) view.findViewById(R.id.greenButton);
        ImageButton redButton = (ImageButton) view.findViewById(R.id.redButton);
        ImageButton pinkButton = (ImageButton) view.findViewById(R.id.pinkButton);
        ImageButton clearLastOperationButton = (ImageButton) view.findViewById(R.id.clearLastOperationButton);
        ImageButton clearAllActionsButton = (ImageButton) view.findViewById(R.id.clearAllActionsButton);

        clearLastOperationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.cancelLastActions();
            }
        });

        clearAllActionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.clearAllActions();
            }
        });


        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.paintAction(Color.BLUE);
            }
        });

        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.paintAction(Color.YELLOW);
            }
        });

        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.paintAction(Color.BLACK);
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.paintAction(Color.GREEN);
            }
        });

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.paintAction(Color.RED);
            }
        });

        pinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.paintAction(Color.MAGENTA);
            }
        });
        return view;
    }
}
