package com.example.projetofinalconversor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.*;
import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity {

    EditText editValor;
    RadioButton radioCtoF, radioFtoC;
    TextView txtResultado;
    Button btnConverter, btnLogout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        editValor = findViewById(R.id.editValor);
        radioCtoF = findViewById(R.id.radioCtoF);
        radioFtoC = findViewById(R.id.radioFtoC);
        txtResultado = findViewById(R.id.txtResultado);
        btnConverter = findViewById(R.id.btnConverter);
        btnLogout = findViewById(R.id.btnLogout);
        auth = FirebaseAuth.getInstance();

        btnConverter.setOnClickListener(v -> {
            String valorTexto = editValor.getText().toString();
            if (valorTexto.isEmpty()) {
                Toast.makeText(this, "Digite um valor", Toast.LENGTH_SHORT).show();
                return;
            }

            double valor = Double.parseDouble(valorTexto);
            double resultado;

            if (radioCtoF.isChecked()) {
                resultado = (valor * 9 / 5) + 32;
                txtResultado.setText("Resultado: " + String.format("%.1f °F", resultado));
            } else {
                resultado = (valor - 32) * 5 / 9;
                txtResultado.setText("Resultado: " + String.format("%.1f °C", resultado));
            }
        });

        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
