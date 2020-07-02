package com.example.clientesjson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ListView listClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listClientes = (ListView)findViewById(R.id.listClientes);

        new GetClientes().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuItemNuevo:
                Intent intent = new Intent(this, InsertClienteActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new GetClientes().execute();

    }

   private class GetClientes extends AsyncTask<Object, Object, Clientes> {

       ProgressDialog progressDialog;

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           progressDialog = ProgressDialog.show(MainActivity.this, "", "Cargando datos...", true);
       }

       @Override
       protected Clientes doInBackground(Object... objects) {

           try {
               return WSJsonClientes.getClientes();
           } catch (IOException e) {
               e.printStackTrace();
               return null;
           } catch (JSONException e) {
               e.printStackTrace();
               return null;
           }
       }

       @Override
       protected void onPostExecute(Clientes result) {
           super.onPostExecute(result);

           if(result != null) {
               ArrayAdapter<Cliente> adapter = new ArrayAdapter<Cliente>(MainActivity.this, android.R.layout.simple_list_item_1, result);
               listClientes.setAdapter(adapter);
           }
           else {
               Toast.makeText(MainActivity.this, "Error al cargar los datos..", Toast.LENGTH_SHORT).show();
               finish();
           }
           progressDialog.dismiss();
       }

   }
}