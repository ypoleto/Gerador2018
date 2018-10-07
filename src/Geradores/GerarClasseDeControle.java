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
 * @author radames
 */
public class GerarClasseDeControle {

    String projetoDestino;
    String nomeClasse;

    public GerarClasseDeControle(String projetoDestino, String nomeClasse) {
        this.projetoDestino = projetoDestino;
        this.nomeClasse = nomeClasse;
        gerarClasseControle();
    }

    private void gerarClasseControle() {
        Ferramentas ferramentas = new Ferramentas();

        List<String> arquivoBase = ferramentas.abrirArquivo("src/Main/" + nomeClasse + ".txt");

        List<String> codigoGerado = new ArrayList<>();

        //fazer a classe de controle de lista
        codigoGerado.clear();
        codigoGerado.add("package Main;\n"
                + "\n"
                + "import java.util.ArrayList;\n"
                + "import java.util.List;\n");

        //import java.util.Date;
        for (String s : arquivoBase) {
            String aux[] = s.split(";");
            if (aux[0].equals("Date")) {
                codigoGerado.add("import java.util.Date;\n");
            }
        }

        codigoGerado.add("public class " + nomeClasse + "Controle {\n");
        codigoGerado.add("  List<" + nomeClasse + "> lista = new ArrayList<>();\n\n");

        codigoGerado.add("public " + nomeClasse + " buscar(int chave){\n"
                + "        for (int i = 0; i < lista.size(); i++) {\n"
                + "            if (chave==lista.get(i).getId()) {\n"
                + "                return lista.get(i);\n"
                + "            }\n"
                + "        }\n"
                + "        return null;\n"
                + "    }\n");

        codigoGerado.add("public void inserir(" + nomeClasse + " " + nomeClasse.toLowerCase() + "){\n"
                + "    lista.add(" + nomeClasse.toLowerCase() + ");\n"
                + "    }\n");

        codigoGerado.add("void alterar(" + nomeClasse + " " + nomeClasse.toLowerCase() + "Original, " + nomeClasse + " " + nomeClasse.toLowerCase() + "Alterado){\n"
                + "    lista.set(lista.indexOf(" + nomeClasse.toLowerCase() + "Original), " + nomeClasse.toLowerCase() + "Alterado);\n"
                + "    }\n");

        codigoGerado.add("   public void excluir(" + nomeClasse + " " + nomeClasse.toLowerCase() + "){\n"
                + "        lista.remove(" + nomeClasse.toLowerCase() + ");\n"
                + "    }");

        codigoGerado.add("    public List<String> listar() {\n"
                + "        List<String> ls = new ArrayList<>();\n"
                + "        for (int i = 0; i < lista.size(); i++) {\n"
                + "            ls.add(\"\"");

        String na = "";
        for (String s : arquivoBase) {
            String aux[] = s.split(";");
            na += aux[1] + ", ";
        }
        na = na.substring(0, na.length() - 2);
        String[] nav = na.split(",");

        for (int i = 0; i < nav.length; i++) {
            if (i != (nav.length) - 1) {
                codigoGerado.add("+ lista.get(i).get" + ferramentas.plMaius(nav[i]) + "() + \";\"");
            }
            if (i == (nav.length) - 1) {
                codigoGerado.add("+ lista.get(i).get" + ferramentas.plMaius(nav[i]) + "()");
            }
        }
        codigoGerado.add(" \n"
                + "            );\n"
                + "        }\n"
                + "        return ls;\n"
                + "    }");

        codigoGerado.add("}");

        String cc = projetoDestino + "/src/Main/";
        System.out.println("Vai criar a classe nesse caminho=> " + cc);
        ferramentas.salvarArquivo(cc + nomeClasse + "Controle.java", codigoGerado);
    }
}
