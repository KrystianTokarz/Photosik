package layout;


import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import pl.silnepalce.photosik.R;
import pl.silnepalce.photosik.settings.RgbSingleton;


public class RgbFragment extends Fragment {

    private FragmentRgbListener activityCommander;
    private SeekBar redSeekBar,blueSeekBar,greenSeekBar;
    private int lastR, lastG, lastB;
    private RgbSingleton rgbSingleton;

    public interface  FragmentRgbListener{
        void rgbSelected(int redColour, int greenColour,int blueColour);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCommander = (FragmentRgbListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rgb, container, false);
        rgbSingleton = RgbSingleton.getInstance();

        redSeekBar = (SeekBar) view.findViewById(R.id.redSeekBar);
        blueSeekBar = (SeekBar) view.findViewById(R.id.blueSeekBar);
        greenSeekBar = (SeekBar) view.findViewById(R.id.greenSeekBar);
        RgbSingleton rbgSingleton = RgbSingleton.getInstance();
        redSeekBar.setProgress(rbgSingleton.getR());
        blueSeekBar.setProgress(rbgSingleton.getB());
        greenSeekBar.setProgress(rbgSingleton.getG());

        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                lastR = seekBar.getProgress();
                activityCommander.rgbSelected(lastR,rgbSingleton.getG(),rgbSingleton.getB());
                RgbSingleton rgbSingleton = RgbSingleton.getInstance();
                rgbSingleton.setR(lastR);

            }
        });

        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                lastB = seekBar.getProgress();
                activityCommander.rgbSelected(rgbSingleton.getR(),rgbSingleton.getG(),lastB);
                rgbSingleton.setB(lastB);
            }
        });

        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                lastG = seekBar.getProgress();
                activityCommander.rgbSelected(rgbSingleton.getG(),lastG,rgbSingleton.getB());
                rgbSingleton.setG(lastG);
            }
        });
        return view;
    }
}
