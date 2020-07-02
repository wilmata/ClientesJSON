package com.example.clientesjson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WSJsonClientes extends WSJson {

    private static String SERVER = "10.0.2.2";

    public static Clientes getClientes() throws IOException, JSONException {
        Clientes result = null;

        JSONObject jsonObj = getJson("http://" + SERVER + "/demo_json/get_clientes.php");

        if (jsonObj != null) {

            JSONArray clientes = jsonObj.getJSONArray("clientes");
            result = new Clientes();

            for (int i = 0; i < clientes.length(); i++) {
                JSONObject c = clientes.getJSONObject(i);

                Cliente temp = new Cliente();
                temp.setId(c.getInt("id"));
                temp.setNombre(c.getString("nombre"));
                temp.setTelefono(c.getString("telefono"));

                result.add(temp);
            }
        }
        return result;
    }

    public static boolean insertCliente(Cliente cliente) throws IOException, JSONException {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("nombre", cliente.getNombre());
        jsonParam.put("telefono", cliente.getTelefono());

        JSONObject jsonResult = sendJson("http://" + SERVER + "/demo_json/insertar_cliente.php", jsonParam);

        if(jsonResult == null) return false;

        String estado = jsonResult.getString("estado");

        if(estado.compareTo("1")==0) return true;
        else return  false;
    }
}

