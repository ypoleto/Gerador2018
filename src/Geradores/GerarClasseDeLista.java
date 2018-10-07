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
public class GerarClasseDeLista {

    String projetoDestino;
    String nomeClasse;

    public GerarClasseDeLista(String projetoDestino, String nomeClasse) {
        this.projetoDestino = projetoDestino;
        this.nomeClasse = nomeClasse;
        gerarClasseLista();
    }

    private void gerarClasseLista() {
        Ferramentas ferramentas = new Ferramentas();

        List<String> arquivoBase = ferramentas.abrirArquivo("src/Main/" + nomeClasse + ".txt");

        List<String> codigoGerado = new ArrayList<>();

        codigoGerado.clear();
        codigoGerado.add("package Main;"
                + "import java.awt.BorderLayout;\n"
                + "import java.awt.Color;\n"
                + "import java.awt.Container;\n"
                + "import java.awt.ScrollPane;\n"
                + "import java.util.List;\n"
                + "import javax.swing.JDialog;\n"
                + "import javax.swing.JPanel;\n"
                + "import javax.swing.JTextArea;\n"
                + "import javax.swing.JToolBar;\n"
                + "import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;\n");

        //import java.util.Date;
        for (String s : arquivoBase) {
            String aux[] = s.split(";");
            if (aux[0].equals("Date")) {
                codigoGerado.add("import java.util.Date;\n");
            }
        }

        codigoGerado.add("public class GUIListar extends JDialog {\n");

        codigoGerado.add("JPanel painelTa = new JPanel();\n"
                + "    ScrollPane scroll = new ScrollPane();\n"
                + "    JTextArea ta = new JTextArea();\n\n");

        codigoGerado.add("public GUIListar(List<String> texto) {\n"
                + "        setTitle(\"Listagem\");\n"
                + "        setSize(500, 180);//tamanho da janela\n"
                + "        setDefaultCloseOperation(DISPOSE_ON_CLOSE);//libera ao sair (tira da memória a classe\n"
                + "        setLayout(new BorderLayout());//informa qual gerenciador de layout será usado\n"
                + "        setBackground(Color.CYAN);//cor do fundo da janela\n"
                + "        Container cp = getContentPane();\n");

        codigoGerado.add("JToolBar toolBar = new JToolBar();\n"
                + "\n"
                + "        ta.setText(\"\");\n"
                + "        for (int i = 0; i < texto.size(); i++) {\n"
                + "            ta.append(texto.get(i).toString()+ System.lineSeparator());\n"
                + "        }\n"
                + "        ta.setEditable(false);\n"
                + "\n"
                + "        scroll.add(ta);\n"
                + "        painelTa.add(scroll);\n"
                + "\n"
                + "        cp.add(toolBar, BorderLayout.NORTH);\n"
                + "        cp.add(scroll, BorderLayout.CENTER);\n"
                + "        setLocationRelativeTo(null);\n"
                + "        setVisible(true);\n"
                + "        setModal(true);\n"
                + "}}\n");

        String cc = projetoDestino + "/src/Main/";
        System.out.println("Vai criar a classe nesse caminho=> " + cc);
        ferramentas.salvarArquivo(cc + "GUIListar.java", codigoGerado);
    }
}
