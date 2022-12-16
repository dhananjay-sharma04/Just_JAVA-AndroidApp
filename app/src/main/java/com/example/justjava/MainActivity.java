package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

//    this is used to track the quantity of coffees
    static int numberOfCoffees = 1;
    static int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    this Method is called when the order button clicked
    public void summitOrder(View view){
        EditText yourName = (EditText) findViewById(R.id.edit_name);
        String name = yourName.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCream = (CheckBox) findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = chocolateCream.isChecked();

        price = calcPrice(hasWhippedCream, hasChocolate);

        String message = getString(R.string.order_summary_name, name);
        message += "\nHas Whipped Cream ? " + hasWhippedCream;
        message += "\nHas Chocolate Cream ? " + hasChocolate;
        message += "\nQuantity : " + numberOfCoffees;
        message += "\nTotal : " + NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(price);
        message += "\n" + getString(R.string.thanku);
        displayMessage(message);

        composeEmail("just java order for " + name, message);

        Toast.makeText(this, "Ordered Successful", Toast.LENGTH_SHORT).show();
    }
//    this method help to minus the quantity by 1
    public void decrement(View view){
        if (numberOfCoffees == 1){
            Toast.makeText(this, "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees -= 1;
        displayQuantity(numberOfCoffees);
    }
//    this method help to plus the quantity by 1
    public void increment(View view){
        if (numberOfCoffees == 100) {
            Toast.makeText(this, "Quantity can't be more than 100", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees += 1;
        displayQuantity(numberOfCoffees);
    }
//    this method displays the given quantity value on the screen
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.txtQuantity);
        quantityTextView.setText("" + number);
    }
//    this method displays the price of coffees
    private int calcPrice(boolean hasWhippedCream, boolean hasChocolate){
        int basePrice = 5;
       if (hasWhippedCream){
            basePrice += 1;
       }
       if (hasChocolate){
            basePrice += 2;
       }
       return numberOfCoffees * basePrice;
    }
//    this method is used for display summary message of order
    private void displayMessage(String message){
        TextView orderSummary = (TextView) findViewById(R.id.txtOrderSummary);
        orderSummary.setText(message);
        orderSummary.setTextColor(getColor(R.color.purple_700));
    }
//    this method is used for compose email
    public void composeEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}