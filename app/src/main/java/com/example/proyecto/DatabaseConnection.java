package com.example.proyecto;

import android.os.AsyncTask;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection extends AsyncTask<Void, Void, Connection> {
    private static final String TAG = "DatabaseConnection";

    private static final String HOST = "server3.hostingfacil.co";
    private static final String PORT = "5432";
    private static final String DATABASE_NAME = "coffee_card"; // Nombre de tu base de datos
    private static final String USERNAME = "coffeadmin";
    private static final String PASSWORD = "f=TM,7%Zw68&";

    @Override
    protected Connection doInBackground(Void... voids) {
        Connection connection = null;
        String connectionString = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE_NAME;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Driver not found", e);
        } catch (SQLException e) {
            Log.e(TAG, "Error establishing connection", e);
        }

        return connection;
    }

    @Override
    protected void onPostExecute(Connection connection) {
        // Aquí puedes manejar la conexión establecida
        if (connection != null) {
            Log.d(TAG, "Connection successful");
            // Puedes ejecutar consultas, actualizaciones, etc. aquí
        } else {
            Log.e(TAG, "Connection failed");
        }
    }

    private <query> void Consultar(Connection connection, String query){
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
        }catch (SQLException e){
            Log.e(TAG, "Error exception query, e");
        }
    }
}

