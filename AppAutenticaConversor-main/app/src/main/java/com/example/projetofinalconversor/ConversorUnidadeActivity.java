package com.example.projetofinalconversor;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class ConversorUnidadeActivity extends AppCompatActivity {

    // Constantes para conversão (melhor legibilidade)
    private static final double KILO_FACTOR = 1024.0;

    private Spinner spnCategoria, spnOrigem, spnDestino;
    private EditText etValor;
    private Button btnConverter;
    private TextView tvResultado;

    private final String[] categorias = {"Temperatura", "Comprimento", "Bits/Bytes"};
    private final String[] temp = {"Celsius", "Fahrenheit", "Kelvin"};
    private final String[] comprimento = {"Metro", "Centimetro", "Kilometro", "Polegada", "Pé"};
    private final String[] datasize = {"bit", "byte", "KB", "MB", "GB"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor_unidade);

        // 1. Inicialização dos componentes
        spnCategoria = findViewById(R.id.spnCategoria);
        spnOrigem = findViewById(R.id.spnOrigem);
        spnDestino = findViewById(R.id.spnDestino);
        etValor = findViewById(R.id.etValorConv);
        btnConverter = findViewById(R.id.btnConverterConv);
        tvResultado = findViewById(R.id.tvResultadoConv);

        // 2. Configuração do Spinner de Categoria
        ArrayAdapter<String> adapterCat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoria.setAdapter(adapterCat);

        // 3. Inicialização dos Spinners de Unidade (com a primeira categoria: Temperatura)
        // ESSA É A PRIMEIRA E ÚNICA CHAMADA NECESSÁRIA PARA O ESTADO INICIAL
        updateUnitSpinners(0);

        // 4. Listener para a mudança de Categoria
        spnCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUnitSpinners(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Não é necessário fazer nada aqui
            }
        });

        // 5. Listener para o botão Converter
        btnConverter.setOnClickListener(v -> {
            String valorStr = etValor.getText().toString().trim();
            if (valorStr.isEmpty()) {
                Toast.makeText(ConversorUnidadeActivity.this, "Informe um valor", Toast.LENGTH_SHORT).show();
                return;
            }
            double valor;
            try {
                valor = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                Toast.makeText(ConversorUnidadeActivity.this, "Valor inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            int cat = spnCategoria.getSelectedItemPosition();
            String origem = (String) spnOrigem.getSelectedItem();
            String destino = (String) spnDestino.getSelectedItem();

            // Verifica se origem e destino estão definidos (pode acontecer logo após a inicialização)
            if (origem == null || destino == null) {
                Toast.makeText(ConversorUnidadeActivity.this, "Selecione as unidades", Toast.LENGTH_SHORT).show();
                return;
            }

            double resultado = 0;
            switch (cat) {
                case 0: // Temperatura
                    resultado = converterTemperatura(valor, origem, destino);
                    break;
                case 1: // Comprimento
                    resultado = converterComprimento(valor, origem, destino);
                    break;
                case 2: // Bits/Bytes
                    resultado = converterDataSize(valor, origem, destino);
                    break;
            }
            DecimalFormat df = new DecimalFormat("#.######");
            tvResultado.setText("Resultado: " + df.format(resultado) + " " + destino);
        });

        // CHAMADA REDUNDANTE REMOVIDA DAQUI
    }

    private void updateUnitSpinners(int categoriaPos) {
        String[] arr;
        if (categoriaPos == 0) arr = temp;
        else if (categoriaPos == 1) arr = comprimento;
        else arr = datasize;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOrigem.setAdapter(adapter);
        spnDestino.setAdapter(adapter);
    }

    // Conversões

    private double converterTemperatura(double valor, String origem, String destino) {
        // Converte para Celsius (base)
        double c;
        if (origem.equals("Celsius")) c = valor;
        else if (origem.equals("Fahrenheit")) c = (valor - 32) * 5.0/9.0;
        else c = valor - 273.15; // Kelvin -> C

        // Converte de Celsius para o destino
        if (destino.equals("Celsius")) return c;
        else if (destino.equals("Fahrenheit")) return c * 9.0/5.0 + 32;
        else return c + 273.15; // Kelvin
    }

    private double converterComprimento(double valor, String origem, String destino) {
        // Converte tudo para metros (base)
        double m;
        switch (origem) {
            case "Metro": m = valor; break;
            case "Centimetro": m = valor / 100.0; break;
            case "Kilometro": m = valor * 1000.0; break;
            case "Polegada": m = valor * 0.0254; break;
            case "Pé": m = valor * 0.3048; break;
            default: return 0; // Unidade de origem desconhecida
        }
        // Converte de metros para o destino
        switch (destino) {
            case "Metro": return m;
            case "Centimetro": return m * 100.0;
            case "Kilometro": return m / 1000.0;
            case "Polegada": return m / 0.0254;
            case "Pé": return m / 0.3048;
            default: return 0; // Unidade de destino desconhecida
        }
    }

    private double converterDataSize(double valor, String origem, String destino) {
        // Converte para bytes (base)
        double bytes;
        switch (origem) {
            case "bit": bytes = valor / 8.0; break;
            case "byte": bytes = valor; break;
            case "KB": bytes = valor * KILO_FACTOR; break;
            case "MB": bytes = valor * KILO_FACTOR * KILO_FACTOR; break;
            case "GB": bytes = valor * KILO_FACTOR * KILO_FACTOR * KILO_FACTOR; break;
            default: return 0; // Unidade de origem desconhecida
        }
        // Converte de bytes para o destino
        switch (destino) {
            case "bit": return bytes * 8.0;
            case "byte": return bytes;
            case "KB": return bytes / KILO_FACTOR;
            case "MB": return bytes / (KILO_FACTOR * KILO_FACTOR);
            case "GB": return bytes / (KILO_FACTOR * KILO_FACTOR * KILO_FACTOR);
            default: return 0; // Unidade de destino desconhecida
        }
    }
}