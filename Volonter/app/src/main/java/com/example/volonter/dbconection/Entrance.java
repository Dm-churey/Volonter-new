//package com.example.volonter.dbconection;
//
//import static com.example.volonter.dbconection.ConnectionSQL.getConnect;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//import com.example.volonter.Authorization;
//import com.example.volonter.MainActivity;
//import com.example.volonter.model.User;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class Entrance extends AsyncTask <Void, Void, User> {
//    private Boolean isEntrance = false;
//    private ProgressDialog pd;
//    private User user;
//    private Context context;
//
//    private String login, password;
//
//    public Entrance (Context context, String login, String password) {
//        this.context = context;
//        this.login = login;
//        this.password = password;
//    };
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        pd = new ProgressDialog(context);
//        pd.setMessage("Загрузка");
//        pd.setCancelable(false);
//        pd.show();
//    }
//
//    @Override
//    protected User doInBackground(Void... voids) {
//        String query = "SELECT * FROM users WHERE login = ? AND password = ?";
//        try {
//            PreparedStatement statement = getConnect().prepareStatement(query);
//            statement.setString(1, login);
//            statement.setString(2, password);
//            ResultSet result = statement.executeQuery();
//            if (result.next()){
//                isEntrance = true;
////                user.setName(result.getString("name"));
////                user.setLastname(result.getString("lastname"));
////                user.setPatronymic(result.getString("patronymic"));
////                user.setAge(result.getString("age"));
////                user.setPhone(result.getString("phone"));
////                user.setLogin(result.getString("login"));
////                user.setPost(result.getString("post"));
////                user.setDivision(result.getString("division"));
//            }
//            return user;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void onPostExecute(User user) {
//        super.onPostExecute(user);
//        pd.cancel();
//        if (isEntrance){
//            Intent intent = new Intent(context, MainActivity.class);
//            context.startActivity(intent);
//            Authorization.a.finish();
//        } else {
//            Toast.makeText(context, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
