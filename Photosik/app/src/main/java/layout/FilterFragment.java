package layout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.silnepalce.photosik.R;
import pl.silnepalce.photosik.settings.RgbSingleton;

public class FilterFragment extends Fragment {

    private FragmentFilterListener activityCommander;
    private RgbSingleton rgbSingleton;


    public interface  FragmentFilterListener{
        void filterSelected(int number);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCommander = (FragmentFilterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        rgbSingleton = RgbSingleton.getInstance();

        Button firstButton = (Button) view.findViewById(R.id.button1);
        Button secondButton = (Button) view.findViewById(R.id.button2);
        Button thirdButton = (Button) view.findViewById(R.id.button3);
        Button fourthButton = (Button) view.findViewById(R.id.button4);
        Button fifthButton = (Button) view.findViewById(R.id.button5);
        Button sixthButton = (Button) view.findViewById(R.id.button6);
        Button seventhButton = (Button) view.findViewById(R.id.button7);
        Button eighthButton = (Button) view.findViewById(R.id.button8);
        Button ninthButton = (Button) view.findViewById(R.id.button9);

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbSingleton.resetAllValue();
                activityCommander.filterSelected(1);
            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbSingleton.resetAllValue();
                activityCommander.filterSelected(2);
            }
        });
        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbSingleton.resetAllValue();
                activityCommander.filterSelected(3);
            }
        });
        fourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbSingleton.resetAllValue();
                activityCommander.filterSelected(4);
            }
        });
        fifthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbSingleton.resetAllValue();
                activityCommander.filterSelected(5);
            }
        });
        sixthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbSingleton.resetAllValue();
                activityCommander.filterSelected(6);
            }
        });
        seventhButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbSingleton.resetAllValue();
                activityCommander.filterSelected(7);
            }
        });
        eighthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbSingleton.resetAllValue();
                activityCommander.filterSelected(8);
            }
        });
        ninthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgbSingleton.resetAllValue();
                activityCommander.filterSelected(9);
            }
        });
        return view;
    }
}
