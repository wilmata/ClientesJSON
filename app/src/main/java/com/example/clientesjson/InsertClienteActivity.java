package com.example.clientesjson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class InsertClienteActivity extends AppCompatActivity {

    EditText etNombre,  etTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_cliente);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
    }

    public void btAceptar_Click(View v)
    {
        //Validar
        Cliente cliente = new Cliente();
        cliente.setNombre(etNombre.getText().toString());
        cliente.setTelefono(etTelefono.getText().toString());

        //Insertar cliente
        new InsertCliente().execute(cliente);
    }

    public void btCancelar_Click(View v)
    {
        finish();
    }

    private class InsertCliente extends AsyncTask<Object, Object, Boolean>
    {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(InsertClienteActivity.this, "", "Enviando datos...", true);
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            Cliente cliente = (Cliente) params[0];
            if(cliente == null ) return false;
            try {
                return  WSJsonClientes.insertCliente(cliente);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if(result) {
                finish();
            }
            else{
                Toast.makeText(InsertClienteActivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}