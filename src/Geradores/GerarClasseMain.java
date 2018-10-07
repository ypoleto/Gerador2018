/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Geradores;

import java.util.ArrayList;
import java.util.List;
import myTools.Ferramentas;

/**
 *
 * @author yas
 */
public class GerarClasseMain {

    String projetoDestino;
    String nomeClasse;

    public GerarClasseMain(String projetoDestino, String nomeClasse) {
        this.projetoDestino = projetoDestino;
        this.nomeClasse = nomeClasse;
        gerarClasseMain();

    }
private void gerarClasseMain() {
    Ferramentas ferramentas = new Ferramentas();

        List<String> codigoGerado = new ArrayList<>();

        codigoGerado.add("package Main;\n"
        +"      public class Main {\n"
        +"      public static void main(String[] args) {\n" 
        +"                " + nomeClasse +"GUI " + nomeClasse.toLowerCase()+"GUI = new " + nomeClasse +"GUI();"
                + "}"
                + "}");
        
        String cc = projetoDestino + "/src/Main/Main.java";
        System.out.println("Vai criar a classe nesse caminho=> " + cc);
        ferramentas.salvarArquivo(cc, codigoGerado);


        }
}