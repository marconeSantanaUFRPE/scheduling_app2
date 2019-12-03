package com.example.scheduling_app.Infra;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface INodeJs {


    @POST("cadastrar")
    @FormUrlEncoded
    Call<JsonObject> cadastroUsuario(@Field("email") String email, @Field("senha") String senha);


    @POST("login")
    @FormUrlEncoded
    Call<JsonObject> loginUsuario(@Field("email") String email, @Field("senha") String senha);

    @POST("apagarConta")
    @FormUrlEncoded
    Call<JsonObject> apagarUsuario( @Field("usuarioId") int usuarioId);

    @POST("updateConta/Email")
    @FormUrlEncoded
    Call<JsonObject> updateUsuarioEmail(@Field("email") String email, @Field("usuarioId") int usuarioId);

    @POST("updateConta/Senha")
    @FormUrlEncoded
    Call<JsonObject> updateUsuarioSenha(@Field("Senha") String Senha, @Field("usuarioId") int usuarioId);

    @POST("cadastrarAtividade")
    @FormUrlEncoded
    Call<JsonObject> cadastrarAtividade(@Field("nome") String nome, @Field("descricao") String descricao, @Field("data_inicio")
            String data_inicio, @Field("data_termino") String data_termino, @Field("status") String status, @Field("usuarioId") int usuarioId);

    @POST("atividades")
    @FormUrlEncoded
    Call<JsonObject> gettodasAtividade(@Field("usuarioId") int usuarioId);

    @POST("apagarAtividades")
    @FormUrlEncoded
    Call<JsonObject> apagarAtividade(@Field("atividadeId") int idAtividade);

    @POST("updateAtividade/Status")
    @FormUrlEncoded
    Call<JsonObject> updateAtividadeStatus(@Field("atividadeId") int idAtividade , @Field("atualizacao") String status);

}
