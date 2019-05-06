import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.examples.Lab9_a.R;
import com.examples.lab9_a.MainActivity;

public class MyCustom extends DialogFragment {
    private static final String TAG = "MyCustomDialog";

    private EditText input;
    private TextView actionOk, actionCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.my_custom_dialog, container,false);

       actionOk= view.findViewById(R.id,actionOk);
       actionCancel =view.findViewById(R.id.action_cancel);
       input =view.findViewById(R.id.input);

       actionCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.d(TAG,"onclick: closing dialog");

               getDialog().dismiss();
           }
       });

        actionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onclick: getting input dialog");

                String userinput = input.getText().toString();
                if( !userinput.equals("")){
                    (MainActivity())getActivity().inputDisplay.setText(userinput);
                }
                getDialog().dismiss();
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
