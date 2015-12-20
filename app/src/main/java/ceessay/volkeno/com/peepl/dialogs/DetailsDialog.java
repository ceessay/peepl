package ceessay.volkeno.com.peepl.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ceessay.volkeno.com.peepl.R;
import ceessay.volkeno.com.peepl.classes.Person;

/**
 * Created by mohamed on 17/12/15.
 */
public class DetailsDialog extends Dialog{
    private final TextView nameValueTextView,sexValueTextView,ageValueTextView,
            surnameValueTextView, closeButton;

    public DetailsDialog(Context context, final Person p) {
        super(context);

        setContentView(R.layout.details_person_layout);
        setTitle("Détails");


        nameValueTextView = (TextView) findViewById(R.id.nameValueTextView);
        surnameValueTextView = (TextView) findViewById(R.id.surnameValueTextView);
        sexValueTextView = (TextView) findViewById(R.id.sexeValueTextView);
        ageValueTextView = (TextView) findViewById(R.id.ageValueTextView);
        closeButton = (Button) findViewById(R.id.closeButton);

        String sex = p.getSex().equals("M")
                ? context.getResources().getString(R.string.male)
                : context.getResources().getString(R.string.male);

        nameValueTextView.setText(p.getName());
        surnameValueTextView.setText(p.getSurname());
        sexValueTextView.setText(sex);
        ageValueTextView.setText(String.format("%d an(s)", p.getAge()));

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
