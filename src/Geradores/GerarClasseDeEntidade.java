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
public class GerarClasseDeEntidade {

    String projetoDestino;
    String nomeClasse;

    public GerarClasseDeEntidade(String projetoDestino, String nomeClasse) {
        this.projetoDestino = projetoDestino;
        this.nomeClasse = nomeClasse;
        gerarClasseEntidade();
    }

    private void gerarClasseEntidade() {
        Ferramentas ferramentas = new Ferramentas();

        List<String> arquivoBase = ferramentas.abrirArquivo("src/Main/" + nomeClasse + ".txt");

        List<String> codigoGerado = new ArrayList<>();

        codigoGerado.add("package Main;");

        //import java.util.Date;
        for (String s : arquivoBase) {
            String aux[] = s.split(";");
            if (aux[0].equals("Date")) {
                codigoGerado.add("import java.util.Date;");
                break;
            }
        }

        codigoGerado.add("public class " + nomeClasse + " {");

        String ns = "";
        String na = "";
        
        //atributos
        for (String s : arquivoBase) {
            String aux[] = s.split(";");
            codigoGerado.add("private " + aux[0] + " " + aux[1] + ";");
            ns+= aux[0]+" "+aux[1]+", "; 
            na+= aux[1]+", ";
        }
        
        
        ns = ns.substring(0, ns.length() - 2);
        na = na.substring(0, na.length() - 2);
        String[] nav = na.split(",");
        
        codigoGerado.add("    public "+nomeClasse+"("+ns+") {");
        for (int i = 0; i < nav.length; i++) {
            codigoGerado.add("this."+nav[i]+" = "+nav[i]+";");
        } codigoGerado.add("}");

//        for (int i = 0; i < codigoGerado.size(); i++) {
//            System.out.println(codigoGerado.get(i));
//        }
        codigoGerado.add("");

        //métodos get
        for (String s : arquivoBase) {
            String aux[] = s.split(";");
            codigoGerado.add(""
                    + "public " + aux[0] + " get" + ferramentas.plMaius(aux[1]) + "() {\n"
                    + "        return " + aux[1] + ";\n"
                    + "    }\n");
        }
        codigoGerado.add("");

        //métodos set
        for (String s : arquivoBase) {
            String aux[] = s.split(";");
            codigoGerado.add(" public void set" + ferramentas.plMaius(aux[1])
                    + "(" + aux[0] + "  " + aux[1] + ") {\n"
                    + "        this. " + aux[1] + " =  " + aux[1] + ";\n"
                    + "    }");
        }

        codigoGerado.add(""+nomeClasse+"() {\n"
                + "    }");

        //Método toString
        String ss = "";
        for (String s : arquivoBase) {
            String aux[] = s.split(";");
            ss += aux[1] + "+ \";\"+";

        }
        ss = ss.substring(0, ss.length() - 1);

        codigoGerado.add(" @Override\n"
                + "    public String toString() {\n"
                + "        return " + ss + " ;\n"
                + "    }");

        codigoGerado.add("");
        codigoGerado.add("}");

        String cc = projetoDestino + "/src/Main/" + nomeClasse + ".java";
        System.out.println("Vai criar a classe nesse caminho=> " + cc);
        ferramentas.salvarArquivo(cc, codigoGerado);

        //terminou a classe de entidade
    }

}
