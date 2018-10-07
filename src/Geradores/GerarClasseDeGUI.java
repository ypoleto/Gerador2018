/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Geradores;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import myTools.Ferramentas;

/**
 *
 * @author radames
 */
public class GerarClasseDeGUI {

    String projetoDestino;
    String nomeClasse;

    public GerarClasseDeGUI(String projetoDestino, String nomeClasse) {
        this.projetoDestino = projetoDestino;
        this.nomeClasse = nomeClasse;
        gerarClasseGUI();
    }

    private void gerarClasseGUI() {
        Ferramentas fer = new Ferramentas();

        List<String> arquivoBase = fer.abrirArquivo("src/Main/" + nomeClasse + ".txt");

        List<String> codigoGerado = new ArrayList<>();

        //fazer a classe de controle de lista
        codigoGerado.clear();
        codigoGerado.add(
                "package Main;\n"
                + "\n"
                + "import java.awt.BorderLayout;\n"
                + "import java.awt.CardLayout;\n"
                + "import java.awt.Color;\n"
                + "import java.awt.Container;\n"
                + "import java.awt.FlowLayout;\n"
                + "import static java.awt.Frame.NORMAL;\n"
                + "import java.awt.GridLayout;\n"
                + "import java.awt.event.ActionEvent;\n"
                + "import java.awt.event.ActionListener;\n"
                + "import java.awt.event.WindowAdapter;\n"
                + "import java.awt.event.WindowEvent;\n"
                + "import java.util.List;\n"
                + "import javax.swing.JButton;\n"
                + "import javax.swing.JFrame;\n"
                + "import javax.swing.JLabel;\n"
                + "import javax.swing.JOptionPane;\n"
                + "import javax.swing.JPanel;\n"
                + "import javax.swing.JTextField;\n"
                + "import javax.swing.JToolBar;\n"
                + "import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;\n"
                + "import myTools.Ferramentas;\n"
                + "import java.text.DateFormat;\n"
                + "import java.text.SimpleDateFormat;");
        //import java.util.Date;

        for (String s : arquivoBase) {
            String aux[] = s.split(";");
            if (aux[0].equals("Date")) {
                codigoGerado.add("import java.util.Date;\n");
            }
        }

        codigoGerado.add("public class " + nomeClasse + "GUI extends JFrame{"
                + "private Container cp;\n"
                + "private Ferramentas fer = new Ferramentas();\n"
                +"SimpleDateFormat sdfr = new SimpleDateFormat(\"dd/MMM/yyyy\");\n"
                + "private " + nomeClasse + " " + nomeClasse.toLowerCase() + " = new " + nomeClasse + "();\n"
                + "private " + nomeClasse + "Controle " + nomeClasse.toLowerCase() + "controle = new " + nomeClasse + "Controle();"
                + "\n\n");

        codigoGerado.add("private JToolBar tools = new JToolBar();\n\n");

        codigoGerado.add("private JPanel pncentro = new JPanel();\n"
        +                "private JPanel pnNorte = new JPanel();\n"
        +"                private JLabel aviso = new JLabel();\n");

        codigoGerado.add("private JButton btAdicionar = new JButton(\"Adicionar\");\n"
                + "    private JButton btPesquisar = new JButton(\"Pesquisar\");\n"
                + "    private JButton btExcluir = new JButton(\"Excluir\");\n"
                + "    private JButton btAlterar = new JButton(\"Alterar\");\n"
                + "    private JButton btSalvar = new JButton(\"Salvar\");\n"
                + "    private JButton btCancelar = new JButton(\"Cancelar\");\n"
                + "    private JButton btBuscar = new JButton(\"Buscar\");\n"
                + "    private JButton btGravar = new JButton(\"Gravar\");\n"
                + "    private JButton btInserir = new JButton(\"Inserir\");\n"
                + "    private JButton btListar = new JButton(\"Listar\");\n\n");

        String nomes = "";
        int qt = 0;
        String ler = "";

        String lbnomes = "";
        String tfnomes = "";

        for (int i = 0; i < arquivoBase.size(); i++) {
            String tipo, nome;
            String[] t = arquivoBase.get(i).split(";");
            tipo = t[0];
            nome = t[1];
            switch (tipo) {
                case "int":
                    if (i != arquivoBase.size()) {
                        ler += "Integer.valueOf(aux[" + i + "]), ";
                    }
                    break;
                case "String":
                    if (i != arquivoBase.size()) {
                        ler += "aux[" + i + "], ";
                    }
                    break;
                case "double":
                    if (i != arquivoBase.size()) {
                        ler += "Double.valueOf(aux[" + i + "]), ";
                    }
                    break;
                case "Date":
                    if (i != arquivoBase.size()) {
                        ler += "Date(aux[" + i + "]), ";
                    }
                    break;
                default:
                    System.out.println("Erro: Tipo não encontrado");
            }
            qt += 1;
            nomes += "\"" + nome + "\"" + ",";
            if (tipo.equals("boolean")) {
                codigoGerado.add("private JLabel lb" + nome + " = new JLabel(\"" + nome + "\");\n"
                        + "private JCheckBox ck" + nome + " = new JCheckBox();\n");
            } else {
                codigoGerado.add("private JLabel lb" + nome + " = new JLabel(\"" + nome + "\");\n"
                        + "private JTextField tf" + nome + " = new JTextField(30);\n");
                lbnomes += "lb" + nome + ";";
                tfnomes += "tf" + nome + ";";
            }
        }

        nomes = nomes.substring(0, nomes.length() - 1);
        String nomesno = nomes.replace("\"", "");
        String[] nms = nomesno.split(",");
        ler = ler.substring(0, ler.length() - 2);
        tfnomes = tfnomes.substring(0, tfnomes.length() - 1);
        lbnomes = lbnomes.substring(0, lbnomes.length() - 1);
        String[] lbNm = lbnomes.split(";");
        String[] tfNm = tfnomes.split(";");

        codigoGerado.add("private String[] colunas = new String[]{" + nomes + "};\n"
                + "private String[][] dados = new String[0][" + qt + "];\n");

        codigoGerado.add("    private boolean inserindo;");

        codigoGerado.add("    private void zerarAtributos() {");
        for (int i = 0; i < tfNm.length; i++) {
            codigoGerado.add(tfNm[i] + ".setText(\"\");");
        }
        codigoGerado.add("}");


        codigoGerado.add("  public " + nomeClasse + "GUI() {\n"
                + "List<String> listaAuxiliar = fer.abrirArquivo(\"dados.txt\");\n"
                + "        if (listaAuxiliar != null&& listaAuxiliar.size() > 0) {\n"
                + "            for (int i = 0; i < listaAuxiliar.size(); i++) {\n"
                + "                String aux[] = listaAuxiliar.get(i).split(\";\");\n");


        
        
        ler = "";
        for (int i = 0; i < arquivoBase.size(); i++) {
            String tipo, nome;
            String[] t = arquivoBase.get(i).split(";");
            tipo = t[0];
            nome = t[1];
          
            switch (tipo) {
                case "int":
                    if (i != arquivoBase.size()) {
                        ler += "Integer.valueOf(aux["+i+"]), ";
                    }
                    break;
                case "String":
                    if (i != arquivoBase.size()) {
                        ler += "aux["+i+"], ";
                    }
                    break;
                case "double":
                    if (i != arquivoBase.size()) {
                        ler += "Double.valueOf(aux["+i+"]), ";
                    }
                    break;
                case "Date":
                    if (i != arquivoBase.size()) {
                        ler += "Date(aux["+i+"]), ";
                    }
                    break;
                default:
                    System.out.println("Erro: Tipo não encontrado");
            }
        }
        
        ler = ler.substring(0, ler.length() - 2);

        codigoGerado.add("               " + nomeClasse + " c = new " + nomeClasse + "(" + ler + ");\n"
                + "               " + nomeClasse.toLowerCase() + "controle" + ".inserir(c);\n"
                + "            }\n"
                + "        }");
        codigoGerado.add("cp = getContentPane();");
        codigoGerado.add("setSize(700, 550);\n"
                + "        cp.setLayout(new BorderLayout());\n"
                + "\n"
                + "        setDefaultCloseOperation(DISPOSE_ON_CLOSE);\n"
                + "        setTitle(\"Sistema\");\n"
                + "        \n"
                + "        cp.add(tools, BorderLayout.NORTH);\n"
                + "        cp.add(pncentro, BorderLayout.CENTER);\n"
                + "        cp.add(aviso, BorderLayout.SOUTH);\n"
                + "        \n"
                + "        pncentro.setLayout(new GridLayout(" + ((tfNm.length)*2) + ", 2));\n"
                + "        \n"
                + "        pnNorte.add(" + lbNm[0] + ");\n"
                + "        pnNorte.add(" + tfNm[0] + ");\n"
                + "        tools.add(btBuscar);\n"
                + "        tools.add(btListar);\n"
                + "        tools.add(btInserir);\n"
                + "        btInserir.setVisible(false);\n"
                + "        tools.add(btAlterar);\n"
                + "        btAlterar.setVisible(false);\n"
                + "        tools.add(btExcluir);\n"
                + "        btExcluir.setVisible(false);\n"
                + "        tools.add(btSalvar);\n"
                + "        btSalvar.setVisible(false);\n"
                + "        tools.add(btCancelar);\n"
                + "        btCancelar.setVisible(false);\n");

        codigoGerado.add("cp.add(pncentro, BorderLayout.CENTER);\n"
                + "        pncentro.setLayout(new GridLayout(" + ((tfNm.length)) + ", 1));");
        

        for (int i = 0; i < (tfNm.length); i++) {
            codigoGerado.add("JPanel pncentro" + (i) + " = new JPanel(new FlowLayout(FlowLayout.CENTER));\n"
                    + "        pncentro" + (i) + ".add(" + lbNm[i] + ");\n"
                    + "        pncentro" + (i) + ".add(" + tfNm[i] + ");\n"
                    + "pncentro.add(pncentro" + (i) + ");");
        }

        codigoGerado.add("btPesquisar.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent ae) {\n"
                + "                " + tfNm[0] + ".setEditable(true);\n"
                + "                btBuscar.setVisible(true);\n"
                + "\n"
                + "                btPesquisar.setVisible(false);\n"
                + "                btAdicionar.setVisible(false);\n"
                + "                btAlterar.setVisible(false);\n"
                + "                btExcluir.setVisible(false);\n"
                + "                btSalvar.setVisible(false);\n"
                + "                btCancelar.setVisible(false);\n"
                + "\n"
                + "            }\n"
                + "        });");

        codigoGerado.add("btBuscar.addActionListener((ActionEvent e) -> {\n"
                + "            if (" + tfNm[0] + ".getText().trim().equals(\"\")) {\n"
                + "                JOptionPane.showMessageDialog(cp, \"Nada informado\");\n"
                + "            } else {\n"
                + "                try {\n"
                + "                    " + tfNm[0] + ".setBackground(Color.GREEN);\n"
                + "                     " + nomeClasse.toLowerCase() + " = " + nomeClasse.toLowerCase() + "controle.buscar(Integer.valueOf(" + tfNm[0] + ".getText()));\n"
                + "                    if (" + nomeClasse.toLowerCase() + " == null) {\n"
                + "                        btInserir.setVisible(true);\n"
                + "                        btAlterar.setVisible(false);\n"
                + "                        btExcluir.setVisible(false);\n"
                + "                        aviso.setText(\"Não foi encontrado.\");\n"
                + "                    } else {\n"
                + "                        btAlterar.setVisible(true);\n"
                + "                        btInserir.setVisible(false);\n"
                + "                        btExcluir.setVisible(true);\n"
                + "                        " + tfNm[0] + ".setText(String.valueOf(" + nomeClasse.toLowerCase() + ".get" + fer.plMaius(nms[0]) + "()));");

        codigoGerado.add("\n"
                + "                        aviso.setText(\"Encontrou: \");\n" + nomeClasse.toLowerCase() + ".get" + fer.plMaius(nms[0]) + "();\n"
                + "                    }\n"
                + "                } catch (Exception x) {\n"
                + "                    " + tfNm[0] + ".selectAll();\n"
                + "                    " + tfNm[0] + ".requestFocus();\n"
                + "                    " + tfNm[0] + ".setBackground(Color.RED);\n"
                + "                    zerarAtributos();"
                + "                    aviso.setText(\"Erro nos tipos de dados\");;\n"
                + "                }\n"
                + "            }\n"
                + "            " + tfNm[0] + ".selectAll();\n"
                + "            " + tfNm[0] + ".requestFocus();\n"
                + "        });");


        codigoGerado.add("btInserir.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {\n"
                + "                " + tfNm[0] + ".requestFocus();\n"
                + "                btInserir.setVisible(false);\n"
                + "                btSalvar.setVisible(true);\n"
                + "                btCancelar.setVisible(true);\n"
                + "                btBuscar.setVisible(false);\n"
                + "                btListar.setVisible(false);\n"
                + "                inserindo = true;\n"
                + "                aviso.setText(\"Você pode alterar ou excluir o arquivo existente.\");\n"
                + "            }\n"
                + "        });");

        codigoGerado.add("btSalvar.addActionListener((ActionEvent e) -> {\n"
                + "            " + nomeClasse + " " + nomeClasse.toLowerCase() + "Original = " + nomeClasse.toLowerCase() + ";\n"
                + "            if (inserindo) {\n"
                + "                " + nomeClasse.toLowerCase() + " = new " + nomeClasse + "();\n"
                + "            }");

        
        
        
        for (int i = 0; i < tfNm.length; i++) {
            String lertipo = "";
            String tipo;
            String[] t = tfNm[i].split(";");
            tipo = t[0];
          
            switch (tfNm[i]) {
                case "int":
                    if (i != arquivoBase.size()) {
                        lertipo += "Integer.valueOf";
                    }
                    break;
                case "String":
                    if (i != arquivoBase.size()) {
                        lertipo += "";
                    }
                    break;
                case "double":
                    if (i != arquivoBase.size()) {
                        lertipo += "Double.valueOf";
                    }
                    break;
                case "Date":
                    if (i != arquivoBase.size()) {
                        lertipo += "Date";
                    }
                    break;
                default:
                    System.out.println("Erro: Tipo não encontrado");
            }
             codigoGerado.add(nomeClasse.toLowerCase() + ".set" + fer.plMaius(nms[i]) + "(" + lertipo + "(" + tfNm[i] + ".getText()));");
        }
        
        
        
        
        
               
            

            
        

        codigoGerado.add("if (inserindo) {\n"
                + "                " + nomeClasse.toLowerCase() + "controle.inserir(" + nomeClasse.toLowerCase() + ");\n"
                + "                aviso.setText(\"Inseriu (\" + " + nomeClasse.toLowerCase() + ".get" + fer.plMaius(nms[0]) + "());\n"
                + "            } else {\n"
                + "                " + nomeClasse.toLowerCase() + "controle.alterar(" + nomeClasse.toLowerCase() + "Original, " + nomeClasse.toLowerCase() + ");\n"
                + "                aviso.setText(\"Alterou (\" + " + nomeClasse.toLowerCase() + ".get" + fer.plMaius(nms[0]) + "());\n"
                + "            }");

        codigoGerado.add("" + tfNm[0] + ".requestFocus();\n"
                + "            " + tfNm[0] + ".selectAll();\n"
                + "            btSalvar.setVisible(false);\n"
                + "            btCancelar.setVisible(false);\n"
                + "            btBuscar.setVisible(true);\n"
                + "            btListar.setVisible(true);\n"
                + "            btGravar.doClick();\n"
                + "            zerarAtributos();\n"
                + "            " + tfNm[0] + ".setText(\"\");\n"
                + "            inserindo = false;\n"
                + "        });");

        codigoGerado.add("btAlterar.addActionListener((ActionEvent e) -> {\n"
                + "            " + tfNm[0] + ".requestFocus();\n"
                + "            btSalvar.setVisible(true);\n"
                + "            btCancelar.setVisible(true);\n"
                + "            btBuscar.setVisible(false);\n"
                + "            btAlterar.setVisible(false);\n"
                + "            btExcluir.setVisible(false);\n"
                + "            btListar.setVisible(false);\n"
                + "            aviso.setText(\"Alterando\");\n"
                + "        });");
        
                

        codigoGerado.add("btGravar.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {\n"
                + "                List<String> listaStr = "+ nomeClasse.toLowerCase() + "controle.listar();\n"
                + "                fer.salvarArquivo(\""+ nomeClasse +".txt\", listaStr);"
                        + "\n "
                        + "}"
                        + "});");         
                
                

        codigoGerado.add("btExcluir.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {\n"
                + "                int dialogResult = JOptionPane.showConfirmDialog(cp, \"Vai excluir (\"\n"
                + "                        + " + tfNm[1] + ".getText() + \")?\", \"Exclui da lista\", NORMAL);\n"
                + "                if (dialogResult == JOptionPane.YES_OPTION) {\n"
                + "                    " + nomeClasse.toLowerCase() + "controle.excluir(" + nomeClasse.toLowerCase() + ");\n"
               // + "                    CardLayout.show(pnSul, \"pnMsg\");\n"
                + "                    aviso.setText(\"Excluiu (\" + " + nomeClasse.toLowerCase() + ".get" + fer.plMaius(nms[0]) + "());\n"
                + "                    btGravar.doClick();\n"
                + "                    zerarAtributos();\n"
                + "                    " + tfNm[0] + ".setText(\"\");\n"
                + "                    " + tfNm[0] + ".requestFocus();\n"
                //+ "                    pintarNao();\n"
                + "                }\n"
                + "            }\n"
                + "        });");

        codigoGerado.add("btCancelar.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {\n"
                + "                " + tfNm[0] + ".requestFocus();\n"
                + "                " + tfNm[0] + ".selectAll();\n"
                + "                btInserir.setVisible(false);\n"
                + "                btSalvar.setVisible(false);\n"
                + "                btCancelar.setVisible(false);\n"
                + "                btBuscar.setVisible(true);\n"
                + "                btListar.setVisible(true);\n"
                + "                zerarAtributos();\n"
                + "                " + tfNm[0] + ".setText(\"\");\n"
                + "                aviso.setText(\"Cancelando.\");;\n"
                + "            }\n"
                + "        });");

        codigoGerado.add("btCancelar.addActionListener(new ActionListener() {\n"
                + "            @Override\n"
                + "            public void actionPerformed(ActionEvent e) {\n"
                + "                " + tfNm[0] + ".requestFocus();\n"
                + "                " + tfNm[0] + ".selectAll();\n"
                + "                btInserir.setVisible(false);\n"
                + "                btSalvar.setVisible(false);\n"
                + "                btCancelar.setVisible(false);\n"
                + "                btBuscar.setVisible(true);\n"
                + "                btListar.setVisible(true);\n"
                + "                zerarAtributos();\n"
                + "                " + tfNm[0] + ".setText(\"\");\n"
                + "                aviso.setText(\"Cancelado.\");\n"
                + "            }\n"
                + "        });");

        String auxss = "";
        for (int i = 0; i < qt; i++) {
            auxss += "aux[" + i + "], ";
        }
        auxss = auxss.substring(0, auxss.length() - 2);

        codigoGerado.add("btListar.addActionListener(new ActionListener() {\n" +
"            @Override\n" +
"            public void actionPerformed(ActionEvent ae) {\n" +
"                GUIListar guilistar = new GUIListar (" + nomeClasse.toLowerCase() + "controle.listar());\n" +
"            }\n" +
"         });");

        codigoGerado.add("addWindowListener(new WindowAdapter() {\n"
                + "            @Override\n"
                + "            public void windowClosing(WindowEvent e) {\n"
                + "                btGravar.doClick();\n"
                + "                dispose();\n"
                + "            }\n"
                + "        });\n"
                +"          setVisible(true);\n"
                +"          setLocationRelativeTo(null);");

        codigoGerado.add("} \n"
                + "}");
        String cc = projetoDestino + "/src/Main/";
        System.out.println("Vai criar a classe nesse caminho=> " + cc);
        fer.salvarArquivo(cc + nomeClasse + "GUI.java", codigoGerado);
    }
}
