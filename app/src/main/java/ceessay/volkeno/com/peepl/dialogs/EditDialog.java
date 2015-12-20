package ceessay.volkeno.com.peepl.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import ceessay.volkeno.com.peepl.R;
import ceessay.volkeno.com.peepl.adapters.PersonArrayAdapter;
import ceessay.volkeno.com.peepl.classes.Person;
import ceessay.volkeno.com.peepl.utils.RealmUtils;
import ceessay.volkeno.com.peepl.utils.Toaster;


/**
 * Created by mohamed on 17/12/15.
 */
public class EditDialog extends Dialog{

    private EditText nameEditText, surnameEditText, ageEditText;
    private Button addButton;
    private String name, surname,sex="";
    private int age;
    private RadioGroup sexRadioGroup;

    public EditDialog(final Context context, final Person p, final PersonArrayAdapter arrayAdapter) {
        super(context);

        setContentView(R.layout.edit_person_layout);
        setTitle("Modifier");


        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        sexRadioGroup = (RadioGroup) findViewById(R.id.sexRadioGroup);
        ageEditText = (EditText) findViewById(R.id.ageEditText);

        //definir les contenus des different champs

        nameEditText.setText(p.getName());
        surnameEditText.setText(p.getSurname());
        ageEditText.setText(String.valueOf(p.getAge()));

        if("M".equals(p.getSex()))
            sexRadioGroup.check(R.id.maleRadioButton);
        else
            sexRadioGroup.check(R.id.femaleRadioButton);


        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.femaleRadioButton)
                    sex = "F";
                else
                    sex = "M";
            }
        });


        Button addButton = (Button) findViewById(R.id.addButton);
        // enregitrer lorsqu'on clique sur le bouton modifier
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuper les nouvelles informations sur la personne
                name = nameEditText.getText().toString();
                surname = surnameEditText.getText().toString();
                age = Integer.valueOf(ageEditText.getText().toString());

                if (age < 0) {
                    Toaster.showShort(context, context.getResources().getString(R.string.age_negative_error_msg));
                }else {
                RealmUtils.updatePerson(p, name, surname, sex, age);
                arrayAdapter.refresh(RealmUtils.getAllPerson());
                //fermer le dialog
                dismiss();
            }

//                Toaster.showShort(context, "Modification");
            }
        });
    }
}
