package layout;


import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import pl.silnepalce.photosik.R;


public class ResizableFragment extends Fragment {

    private FragmentResizableListener activityCommander;

    public interface  FragmentResizableListener{
        public void resizableSelected(int type, int value);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCommander = (FragmentResizableListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resizable, container, false);
        ImageButton firstButton = (ImageButton) view.findViewById(R.id.button1);
        ImageButton secondButton = (ImageButton) view.findViewById(R.id.button2);
        ImageButton thirdButton = (ImageButton) view.findViewById(R.id.button3);
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.resizableSelected(1,-90);
            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.resizableSelected(2,180);
            }
        });
        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.resizableSelected(3,90);
            }
        });
        return view;
    }
}
