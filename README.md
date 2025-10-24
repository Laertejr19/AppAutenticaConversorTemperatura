# 🔄 App Autentica Conversor

Aplicativo Android desenvolvido em **Java + XML** no **Android Studio**, que permite ao usuário realizar autenticação básica (login) e, após o acesso, usar um conversor de unidades (ex: moedas, medidas).  
Projeto criado como parte dos estudos de **desenvolvimento mobile nativo**, unindo autenticação e lógica de conversão.

---

## 🧠 Sobre o Projeto

O **App Autentica Conversor** oferece duas etapas principais:
1. Tela de login com usuário e senha para autenticação simples.  
2. Tela de conversão onde o usuário pode inserir um valor, escolher uma unidade ou moeda de origem e destino, e ver o resultado convertido.

O objetivo é demonstrar como combinar autenticação de usuário com funcionalidades de conversão no mesmo aplicativo Android.

---

## 🛠️ Tecnologias Utilizadas

| Categoria             | Ferramenta                         |
|-----------------------|------------------------------------|
| IDE                   | Android Studio                     |
| Linguagem             | Java                               |
| Layouts               | XML                                |
| Versão mínima Android | API 21 (Android 5.0)               |
| Estrutura de UI       | ConstraintLayout / LinearLayout    |

---

## 📱 Estrutura do Projeto

```
app/
 ├── java/
 │    └── br/ulbra/appautenticaconversor/
 │         ├── LoginActivity.java
 │         ├── ConverterActivity.java
 │         └── (outra(s) atividade(s) de suporte)
 ├── res/
 │    ├── layout/
 │    │     ├── activity_login.xml
 │    │     └── activity_converter.xml
 │    ├── drawable/
 │    │     └── (ícones ou imagens do app)
 │    └── values/
 │          └── strings.xml
 └── AndroidManifest.xml
```

---

## ⚙️ Lógica de Autenticação e Conversão

**Autenticação simples:**
```java
EditText edtUsuario = findViewById(R.id.edtUsuario);
EditText edtSenha   = findViewById(R.id.edtSenha);
Button   btnLogin   = findViewById(R.id.btnLogin);

btnLogin.setOnClickListener(v -> {
    String usuario = edtUsuario.getText().toString();
    String senha   = edtSenha.getText().toString();
    if (usuario.equals("admin") && senha.equals("1234")) {
        Intent intent = new Intent(LoginActivity.this, ConverterActivity.class);
        startActivity(intent);
    } else {
        Toast.makeText(LoginActivity.this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
    }
});
```

**Conversão de unidades/exemplo de moeda:**
```java
EditText edtValor   = findViewById(R.id.edtValor);
Spinner spnDe      = findViewById(R.id.spnDe);
Spinner spnPara    = findViewById(R.id.spnPara);
Button btnConverter = findViewById(R.id.btnConverter);
TextView txtResultado = findViewById(R.id.txtResultado);

btnConverter.setOnClickListener(v -> {
    double valor = Double.parseDouble(edtValor.getText().toString());
    // Exemplo simples de conversão
    if (spnDe.getSelectedItem().equals("USD") && spnPara.getSelectedItem().equals("BRL")) {
        double resultado = valor * 5.0; // taxa fixa exemplo
        txtResultado.setText("R$ " + String.format("%.2f", resultado));
    }
});
```

---

## 🏗️ Funcionalidades Implementadas

✅ Login com usuário e senha pré-definidos  
✅ Tela de conversão de unidades/moedas  
✅ Interface simples e funcional  
✅ Código comentado para entendimento  

---

## 💡 Possíveis Melhorias

- Implementar autenticação com banco de dados ou API externa  
- Permitir cadastro de novos usuários  
- Obter taxas de câmbio reais por API (ex: exchangeratesapi.io)  
- Suporte para múltiplas unidades (peso, comprimento, volume etc.)  
- Tema claro/escuro e internacionalização (multi-idioma)  

---

## 👩‍💻 Autor

**Nome:** *Laerte Ferraz da Silva Júnior*  
**Instituição:** *Curso Técnico em Informática – Escola Ulbra São Lucas*  
**Disciplina:** *Desenvolvimento Mobile Android*  
**Professor:** *Jeferson Leon*  

---

## 📚 Licença

Projeto desenvolvido para fins **educacionais**.  
Livre para uso e modificação, desde que mantidos os créditos ao autor.

---

> “A melhor forma de aprender é construindo. Então... bora codar!”
