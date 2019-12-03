package com.example.scheduling_app.Infra;

import com.example.scheduling_app.Atividades.Domain.Atividades;
import com.example.scheduling_app.User.Domain.User;

import java.util.ArrayList;

public class Sessao {

    private static User usuario;
    private static ArrayList<Atividades> listaDeAtividades;

    public static User getUsuario() {
        return usuario;
    }


    public static void setUsuario(User usuario) {
        Sessao.usuario = usuario;
    }

    public static ArrayList<Atividades> getListaDeAtividades() {
        return listaDeAtividades;
    }

    public static void setListaDeAtividades(ArrayList<Atividades> listaDeAtividades) {
        Sessao.listaDeAtividades = listaDeAtividades;
    }
}
