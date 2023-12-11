package geradordecodigos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUIGerador extends JFrame {

    Container cp;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JTextArea taAtributos = new JTextArea();
    JTextArea taCodigo = new JTextArea();
    ScrollPane spNorte = new ScrollPane();
    ScrollPane spSul = new ScrollPane();
    JButton gerarEntidade = new JButton("Gerar Classe Entidade");
    JButton gerarControle = new JButton("Gerar Classe de Controle");
    JButton gerarGUI = new JButton("Gerar Classe GUI");
    ArrayList<String> texto = new ArrayList<>();
    String c;

    public String primeiraLetraMaiuscula(String s) {
        s = s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase();
        return s;
    }

    public GUIGerador() {
        setTitle("Gerador de Códigos");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(jPanel1, BorderLayout.NORTH);
        cp.add(jPanel3, BorderLayout.CENTER);
        cp.add(jPanel2, BorderLayout.SOUTH);
        jPanel1.setLayout(new GridLayout(1, 1));
        jPanel1.add(spNorte);
        spNorte.add(taAtributos);
        jPanel2.setLayout(new FlowLayout());
        jPanel2.setBackground(Color.DARK_GRAY);
        jPanel2.add(gerarEntidade);
        jPanel2.add(gerarControle);
        jPanel2.add(gerarGUI);
        jPanel3.setLayout(new GridLayout(1, 1));
        jPanel3.add(spSul);
        spSul.add(taCodigo);
        taAtributos.setText("Tema\n" + "String nomeChave\n"
                + "int nomeAtributo1\n"
                + "boolean nomeAtributo2\n"
                + "double nomeAtributo3\n"
                + "float nomeAtributo4\n"
                + "Date nomeAtributo5");

        //Gerar classe entidade
        gerarEntidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taCodigo.setText("");
                texto.clear();
                String[] s = taAtributos.getText().split("\n");
                for (int i = 0; i < s.length; i++) {
                    texto.add(s[i]);
                }
                String classe = texto.get(0);
                int conta = 0;
                for (int i = 0; i < texto.size(); i++) {
                    String[] ss = texto.get(i).split(" ");
                    if (ss[0].equals("Date")) {
                        conta = 1;
                    }
                }
                if (conta != 0) {
                    taCodigo.append("import java.util.Date;\n\n");;
                }
                taCodigo.append("public class " + classe + " implements java.io.Serializable {\n");
                //Atributos
                for (int i = 1; i < texto.size(); i++) {
                    taCodigo.append("\tprivate " + texto.get(i) + ";\n");
                }
                //Construtores
                taCodigo.append("\npublic void " + classe + " (){\n\t}\n");
                String parametros = "";
                for (int i = 1; i < texto.size(); i++) {
                    parametros += texto.get(i) + ", ";
                }
                parametros = parametros.substring(0, parametros.length() - 2);
                taCodigo.append("\npublic void " + classe + " ("
                        + parametros
                        + "){\n");
                for (int i = 1; i < texto.size(); i++) {
                    String[] ss = texto.get(i).split(" ");
                    taCodigo.append("\tthis." + ss[1] + " = " + ss[1] + ";\n");
                }
                taCodigo.append("\t}");
                //toString
                taCodigo.append("\n@Override");
                taCodigo.append("\npublic String toString(){\n");
                parametros = "";
                for (int i = 1; i < texto.size(); i++) {
                    String aux[] = texto.get(i).split(" ");
                    if (aux[0].equals("String")) {
                        parametros += aux[1] + " + \"; \"+ ";
                    } else {
                        parametros += "String.valueOf(" + aux[1] + ")+ \"; \"+ ";
                    }
                }
                parametros = parametros.substring(0, parametros.length() - 8);
                taCodigo.append("\t return " + parametros + ";\n");
                taCodigo.append("\t}\n\n");
                //Métodos get
                for (int i = 1; i < texto.size(); i++) {
                    String[] ss = texto.get(i).split(" ");
                    taCodigo.append("public " + ss[0] + " get" + primeiraLetraMaiuscula(ss[1]));
                    taCodigo.append("(){\n");
                    taCodigo.append("\t return this." + ss[1] + ";\n");
                    taCodigo.append("\t}\n\n");
                }
                //Métodos set
                for (int i = 1; i < texto.size(); i++) {
                    String[] ss = texto.get(i).split(" ");
                    taCodigo.append("public void set" + primeiraLetraMaiuscula(ss[1]));
                    taCodigo.append("(" + texto.get(i) + "){\n");
                    taCodigo.append("\tthis." + ss[1] + " = " + ss[1] + " ;\n");
                    taCodigo.append("\t}\n\n");
                }
                taCodigo.append("}");
            }
        });

        gerarControle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taCodigo.setText("");
                texto.clear();
                String[] s = taAtributos.getText().split("\n");
                for (int i = 0; i < s.length; i++) {
                    texto.add(s[i]);
                }
                taCodigo.append("import java.util.ArrayList;\n"
                        + "import java.util.List;\n"
                        + "import java.io.FileInputStream;\n"
                        + "import java.io.FileOutputStream;\n"
                        + "import java.io.IOException;\n"
                        + "import java.io.ObjectInputStream;\n"
                        + "import java.io.ObjectOutputStream;\n\n");
                taCodigo.append("public class ComandaLista {\n\n\t");
                taCodigo.append("private List <" + texto.get(0) + "> lista = new ArrayList<>();\n\n\t");
                //Adicionar
                taCodigo.append("public void adicionar(" + texto.get(0) + " " + texto.get(0).toLowerCase() + "){\n\t\t");
                taCodigo.append("this.lista.add(" + texto.get(0).toLowerCase() + ");\n\t}");
                //Remover
                taCodigo.append("\n\n\tpublic void remover(" + texto.get(0) + " " + texto.get(0).toLowerCase() + "){\n\t\t");
                taCodigo.append("lista.remove(" + texto.get(0).toLowerCase() + ");\n\t}\n");
                //Alterar
                taCodigo.append("\n\tpublic void alterar(" + texto.get(0) + " quem, " + texto.get(0) + " novoValor){\n\t\t");
                taCodigo.append("lista.set(lista.indexOf(quem), novoValor);\n\t}");
                //Procurar
                String aux[] = texto.get(1).split(" ");
                switch (aux[0]) {
                    case "String":
                        taCodigo.append("\n\n\tpublic " + texto.get(0) + " procurar(String " + aux[1] + "){\n\t\t");
                        taCodigo.append("for (int i = 0; i < lista.size(); i++){\n\t\t\t");
                        taCodigo.append("if (lista.get(i).get" + primeiraLetraMaiuscula(aux[1]) + "().equals(" + aux[1] + ")){\n\t\t\t");
                        break;
                    case "int":
                        taCodigo.append("\n\n\tpublic " + texto.get(0) + " procurar(int " + aux[1] + "){\n\t\t");
                        taCodigo.append("for (int i = 0; i < lista.size(); i++){\n\t\t\t");
                        taCodigo.append("if (lista.get(i).get" + primeiraLetraMaiuscula(aux[1]) + "() == " + aux[1] + "){\n\t\t\t");
                        break;
                }
                taCodigo.append("return lista.get(i);\n\t\t\t}");
                taCodigo.append("\n\t\t}\n\t\treturn null;\n\t}\n");
                //Listar
                taCodigo.append("\n\tpublic List<" + texto.get(0) + "> listagem(){\n\t\t");
                taCodigo.append("return lista;\n\t}");
                //Serialização
                taCodigo.append("\n\tpublic void desSerializaLista(String arquivo) {"
                        + "\n\t\tFileInputStream arqLeitura = null;"
                        + "\n\t\tObjectInputStream in = null;"
                        + "\n\t\tlista.clear();"
                        + "\n\t\ttry {"
                        + "\n\t\tarqLeitura = new FileInputStream(arquivo);"
                        + "\n\t\tin = new ObjectInputStream(arqLeitura);"
                        + "\n\t\tlista = (ArrayList<" + texto.get(0) + ">) in.readObject();"
                        + "\n\t\t} catch (ClassNotFoundException ex) {"
                        + "\n\t\tSystem.out.println(\"erro 1: \" + ex);"
                        + "\n\t\t} catch (IOException ex) {"
                        + "\n\t\tSystem.out.println(\"erro 2: \" + ex);"
                        + "\n\t\t} finally {"
                        + "\n\t\t\ttry {"
                        + "\n\t\t\tarqLeitura.close();"
                        + "\n\t\t\tin.close();"
                        + "\n\t\t\t} catch (IOException ex) {"
                        + "\n\t\t\tSystem.out.println(\"erro 3: \" + ex);"
                        + "\n\t\t\t}\n\t\t}\n\n}\n");
                taCodigo.append("\n\tpublic void serializaLista(String arquivo) {"
                        + "\n\t\tFileOutputStream arq = null;"
                        + "\n\t\tObjectOutputStream out = null;"
                        + "\n\t\ttry {"
                        + "\n\t\tarq = new FileOutputStream(arquivo);"
                        + "\n\t\tout = new ObjectOutputStream(arq);"
                        + "\n\t\tout.writeObject(lista);"
                        + "\n\t\t} catch (IOException ex) {"
                        + "\n\t\tSystem.out.println(\"erro: \" + ex);"
                        + "\n\t\t} finally {"
                        + "\n\t\t\ttry {"
                        + "\n\t\t\tarq.close();"
                        + "\n\t\t\tout.close();"
                        + "\n\t\t\t} catch (IOException ex) {"
                        + "\n\t\t\t}\n\t\t}\n\t}\n}");
            }
        });

        gerarGUI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taCodigo.setText("");
                texto.clear();
                String[] s = taAtributos.getText().split("\n");

                for (int i = 0; i < s.length; i++) {
                    texto.add(s[i]);
                }
                //Imports
                taCodigo.append("import java.awt.BorderLayout;\n"
                        + "import java.awt.Color;\n"
                        + "import java.awt.Container;\n"
                        + "import java.awt.FlowLayout;\n"
                        + "import java.awt.GridLayout;\n"
                        + "import java.awt.ScrollPane;\n"
                        + "import java.awt.event.ActionEvent;\n"
                        + "import java.awt.event.ActionListener;\n"
                        + "import java.util.List;\n"
                        + "import javax.swing.JButton;\n"
                        + "import javax.swing.JFrame;\n"
                        + "import javax.swing.JLabel;\n"
                        + "import javax.swing.JOptionPane;\n"
                        + "import javax.swing.JPanel;\n"
                        + "import javax.swing.JTextArea;\n"
                        + "import javax.swing.JTextField;\n"
                        + "import javax.swing.WindowConstants;\n"
                        + "import java.awt.event.WindowAdapter;\n"
                        + "import java.awt.event.WindowEvent;\n"
                        + "import java.io.File;\n");
                int contData = 0;
                int contBoolean = 0;
                for (int i = 0; i < texto.size(); i++) {
                    String[] ss = texto.get(i).split(" ");
                    if (ss[0].equals("Date")) {
                        contData = 1;
                    }
                    if (ss[0].equals("boolean")) {
                        contBoolean = 1;
                    }
                }
                if (contData != 0) {
                    taCodigo.append("import java.text.ParseException;\n"
                            + "import java.text.SimpleDateFormat;\n"
                            + "import javax.swing.JFormattedTextField;\n");
                }
                if (contBoolean != 0) {
                    taCodigo.append("import javax.swing.ButtonGroup;\n"
                            + "import javax.swing.JRadioButton;\n");
                }
                taCodigo.append("\npublic class GUI" + texto.get(0) + " extends JFrame {\n\n\t");
                taCodigo.append("Container cp;\n\n\t");
                //Painéis e Botões
                taCodigo.append("JPanel pnNorte = new JPanel();\n\tJPanel pnBotoes = new JPanel();\n\tJPanel pnCentro = new JPanel();");
                if (contBoolean != 0) {
                    taCodigo.append("\n\tJPanel pnBG = new JPanel();");
                }
                taCodigo.append("\n\n\tJButton btInserir = new JButton(\"Inserir\");\n\t"
                        + "JButton btAlterar = new JButton(\"Alterar\");\n\t"
                        + "JButton btRemover = new JButton(\"Remover\");\n\t"
                        + "JButton btProcurar = new JButton(\"Procurar\");\n\t"
                        + "JButton btSalvar = new JButton(\"Salvar\");\n\t"
                        + "JButton btCancelar = new JButton(\"Cancelar\");\n\t"
                        + "JButton btListar = new JButton(\"Listar\");\n\n\t");
                taCodigo.append("JTextArea jTextArea = new JTextArea();\n"
                        + "\tScrollPane scroll = new ScrollPane();\n\n\t");
                //JLabel
                for (int i = 1; i < texto.size(); i++) {
                    String[] ss = texto.get(i).split(" ");
                    taCodigo.append("JLabel lb" + primeiraLetraMaiuscula(ss[1]) + " = new JLabel (\"" + primeiraLetraMaiuscula(ss[1]) + "\");\n\t");
                }
                taCodigo.append("\n\t");
                //JTextField
                for (int i = 1; i < texto.size(); i++) {
                    String[] ss = texto.get(i).split(" ");
                    if (ss[0].equals("Date")) {
                        taCodigo.append("JFormattedTextField tf" + primeiraLetraMaiuscula(ss[1]) + ";\n\t");
                    } else if (ss[0].equals("boolean")) {
                        taCodigo.append("");
                    } else {
                        taCodigo.append("JTextField tf" + primeiraLetraMaiuscula(ss[1]) + " = new JTextField (10);\n\t");
                    }
                }
                if (contBoolean != 0) {
                    taCodigo.append("\n\tButtonGroup bg = new ButtonGroup();\n\t"
                            + "JRadioButton bTrue = new JRadioButton(\"True\");\n\t"
                            + "JRadioButton bFalse = new JRadioButton(\"False\");\n\t");
                }
                //Instancia das classes
                if (contData != 0) {
                    taCodigo.append("\n\tSimpleDateFormat sdf = new SimpleDateFormat(\"dd-MM-yyyy\");");
                }

                taCodigo.append("\n\tComandaLista comandaLista = new ComandaLista();\n\t");
                taCodigo.append(texto.get(0) + " " + texto.get(0).toLowerCase() + " = new " + texto.get(0) + "();\n");
                //Métodos
                taCodigo.append("\n\tpublic void zerarAtributos (){");
                for (int i = 1; i < texto.size(); i++) {
                    String[] ss = texto.get(i).split(" ");
                    if (ss[0].contains("boolean")) {
                        taCodigo.append("\n\t\tbg.clearSelection();\n\t\t");
                    } else {
                        taCodigo.append("\n\t\ttf" + primeiraLetraMaiuscula(ss[1]) + ".setText(\"\");"
                                + "\n\t\ttf" + primeiraLetraMaiuscula(ss[1]) + ".setBackground(Color.white);");
                    }
                }
                //Interface Gráfica
                taCodigo.append("}\n\n\tpublic GUI" + texto.get(0) + "(){"
                        + "\n\t\ttry {"
                        + "\n\t\t\tFile arq = new File(\"" + texto.get(0) + ".dat\");"
                        + "\n\t\t\tif (arq.exists()) {"
                        + "\n\t\t\tcomandaLista.desSerializaLista(\"" + texto.get(0) + ".dat\");"
                        + "\n\t\t\t}\n\t\t} catch (Exception e) {"
                        + "\n\t\t\tSystem.out.println(\"arquivo não encontrado\");"
                        + "\n\t\t}\n");
                taCodigo.append("\n\t\tsetTitle(\"" + texto.get(0) + "\");\n\t\tsetSize(800,600);");
                taCodigo.append("\n\t\tsetDefaultCloseOperation(DISPOSE_ON_CLOSE);\n\t\t");
                taCodigo.append("cp = getContentPane();\n\t\tcp.setLayout(new BorderLayout());\n\n");

                for (int i = 0; i < texto.size(); i++) {
                    String[] ss = texto.get(i).split(" ");
                    if (ss[0].equals("Date")) {
                        taCodigo.append("\t\ttf" + primeiraLetraMaiuscula(ss[1]) + " = new JFormattedTextField(sdf);\n"
                                + "\t\ttf" + primeiraLetraMaiuscula(ss[1]) + ".setColumns(7);\n");
                    }
                }
                taCodigo.append("\n\t\tcp.add(pnNorte, BorderLayout.NORTH);\n\t\tcp.add(pnCentro, BorderLayout.CENTER);"
                        + "\n\t\tcp.add(pnBotoes, BorderLayout.SOUTH);");
                if (contBoolean != 0) {
                    taCodigo.append("\n\n\t\tpnBG.setLayout(new GridLayout(1, 1));"
                            + "\n\t\tpnBG.add(bTrue);"
                            + "\n\t\tpnBG.add(bFalse);"
                            + "\n\t\tbg.add(bTrue);"
                            + "\n\t\tbg.add(bFalse);");
                }
                int cont = texto.size() - 1;
                taCodigo.append("\n\t\tpnNorte.setLayout(new GridLayout(" + cont + ",2));");
                for (int i = 1; i < texto.size(); i++) {
                    String[] ss = texto.get(i).split(" ");
                    if (ss[0].equals("boolean")) {
                        taCodigo.append("\n\t\tpnNorte.add(lb" + primeiraLetraMaiuscula(ss[1]) + ");");
                        taCodigo.append("\n\t\tpnNorte.add(pnBG);");
                    } else {
                        taCodigo.append("\n\t\tpnNorte.add(lb" + primeiraLetraMaiuscula(ss[1]) + ");");
                        taCodigo.append("\n\t\tpnNorte.add(tf" + primeiraLetraMaiuscula(ss[1]) + ");");
                    }
                }
                taCodigo.append("\n\n\t\tpnCentro.setLayout(new GridLayout(1, 1));\n"
                        + "\t\tscroll.add(jTextArea);\n"
                        + "\t\tpnCentro.add(scroll);");
                taCodigo.append("\n\n\t\tpnBotoes.setLayout(new FlowLayout());");
                taCodigo.append("\n\t\tpnBotoes.add(btInserir);"
                        + "\n\t\tpnBotoes.add(btAlterar);"
                        + "\n\t\tpnBotoes.add(btRemover);"
                        + "\n\t\tpnBotoes.add(btProcurar);"
                        + "\n\t\tpnBotoes.add(btSalvar);"
                        + "\n\t\tpnBotoes.add(btCancelar);"
                        + "\n\t\tpnBotoes.add(btListar);"
                        + "\n\n\t\tbtSalvar.setVisible(false);"
                        + "\n\t\tbtCancelar.setVisible(false);");
                //Botão Inserir
                taCodigo.append("\n\n\t\tbtInserir.addActionListener(new ActionListener() {\n\t\t\t@Override"
                        + "\n\t\t\tpublic void actionPerformed(ActionEvent ae) {");
                String aux[] = texto.get(1).split(" ");
                taCodigo.append("\n\t\t\t\t" + texto.get(0) + " " + texto.get(0).toLowerCase() + ";"
                        + "\n\t\t\t\ttry {");
                //Procurar a chave
                if (aux[0].equals("String")) {
                    taCodigo.append("\n\t\t\t\t\t" + texto.get(0).toLowerCase() + " = comandaLista.procurar("
                            + "tf" + primeiraLetraMaiuscula(aux[1]) + ".getText());");
                } else if (aux[0].equals("int")) {
                    taCodigo.append("\n\t\t\t\t\t" + texto.get(0).toLowerCase() + " = comandaLista.procurar("
                            + "Integer.valueOf(tf" + primeiraLetraMaiuscula(aux[1]) + ".getText()));");
                }
                taCodigo.append("\n\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux[1]) + ".setBackground(Color.white);");
                taCodigo.append("\n\t\t\t\t\tif(" + texto.get(0).toLowerCase() + " == null){"
                        + "\n\t\t\t\t\t\tboolean erro = false;"
                        + "\n\t\t\t\t\t\t" + texto.get(0).toLowerCase() + " = new " + texto.get(0)
                        + "();\n\t\t\t\t\t\t");
                if (aux[0].equals("String")) {
                    taCodigo.append(texto.get(0).toLowerCase() + ".set" + primeiraLetraMaiuscula(aux[1])
                            + "(tf" + primeiraLetraMaiuscula(aux[1]) + ".getText());");
                } else if (aux[0].equals("int")) {
                    taCodigo.append(texto.get(0).toLowerCase() + ".set" + primeiraLetraMaiuscula(aux[1])
                            + "(Integer.valueOf(tf" + primeiraLetraMaiuscula(aux[1]) + ".getText()));");
                }
                //Try catch e conversão
                for (int i = 2; i < texto.size(); i++) {
                    String aux1[] = texto.get(i).split(" ");
                    if (aux1[0].equals("String")) {
                        taCodigo.append("\n\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(tf"
                                + primeiraLetraMaiuscula(aux1[1])
                                + ".getText()" + ");");
                    } else if (aux1[0].equals("int")) {
                        taCodigo.append("\n\t\t\t\t\t\ttry {"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(Integer.valueOf(tf"
                                + primeiraLetraMaiuscula(aux1[1])
                                + ".getText())" + ");"
                                + "\n\t\t\t\t\t\t} catch (NumberFormatException ex) {"
                                + "\n\t\t\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".set"
                                + "Background(Color.red);"
                                + "\n\t\t\t\t\t\t\terro = true;\n\t\t\t\t\t\t}");
                    } else if (aux1[0].equals("float")) {
                        taCodigo.append("\n\t\t\t\t\t\ttry {"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(Float.valueOf(tf"
                                + primeiraLetraMaiuscula(aux1[1])
                                + ".getText())" + ");"
                                + "\n\t\t\t\t\t\t} catch (NumberFormatException ex) {"
                                + "\n\t\t\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".set"
                                + "Background(Color.red);"
                                + "\n\t\t\t\t\t\t\terro = true;\n\t\t\t\t\t\t}");
                    } else if (aux1[0].equals("boolean")) {
                        taCodigo.append("\n\t\t\t\t\t\tif (bTrue.isSelected()){"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(true);"
                                + "\n\t\t\t\t\t\t} else if (bFalse.isSelected()) {"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(false);}");
                    } else if (aux1[0].equals("double")) {
                        taCodigo.append("\n\t\t\t\t\t\ttry {"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(Double.valueOf(tf"
                                + primeiraLetraMaiuscula(aux1[1])
                                + ".getText())" + ");"
                                + "\n\t\t\t\t\t\t} catch (NumberFormatException ex) {"
                                + "\n\t\t\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".set"
                                + "Background(Color.red);"
                                + "\n\t\t\t\t\t\t\terro = true;\n\t\t\t\t\t\t}");
                    } else if (aux1[0].equals("Date")) {
                        taCodigo.append("\n\t\t\t\t\t\ttry {"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(sdf.parse(tf"
                                + primeiraLetraMaiuscula(aux1[1])
                                + ".getText())" + ");"
                                + "\n\t\t\t\t\t\t} catch (ParseException ex) {"
                                + "\n\t\t\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1])
                                + ".setBackground(Color.red);"
                                + "\n\t\t\t\t\t\t\terro = true;\n\t\t\t\t\t\t}");
                    }
                }
                taCodigo.append("\n\t\t\t\t\t\tif (!erro){"
                        + "\n\t\t\t\t\t\t\tcomandaLista.adicionar(" + texto.get(0).toLowerCase()
                        + ");\n\t\t\t\t\t\t\tzerarAtributos();"
                        + "\n\t\t\t\t\t\t} else {\n\t\t\t\t\t\t\t"
                        + "tf" + primeiraLetraMaiuscula(aux[1]) + ".requestFocus();\n\t\t\t\t\t\t}"
                        + "\n\t\t\t\t\t} else {\n\t\t\t\t\t\t"
                        + "JOptionPane.showMessageDialog(null, \"Já está cadrastado!\");"
                        + "\n\t\t\t\t\t}\n\t\t\t\t\t} catch (NumberFormatException ex) {"
                        + "\n\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux[1]) + ".setBackground(Color.red);"
                        + "\n\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux[1]) + ".requestFocus();"
                        + "\n\t\t\t\t\tJOptionPane.showMessageDialog(null, \"Erro no formato do ID!\");"
                        + "\n\t\t\t\t }\n\t\t\t}\n\t\t});");
                //Botão Alterar
                taCodigo.append("\n\n\t\tbtAlterar.addActionListener(new ActionListener() {\n\t\t\t@Override"
                        + "\n\t\t\tpublic void actionPerformed(ActionEvent ae) {\n\t\t\t\t");
                //Procurar chave
                if (aux[0].equals("int")) {
                    taCodigo.append(texto.get(0) + " x = comandaLista.procurar(Integer.valueOf(tf"
                            + primeiraLetraMaiuscula(aux[1]) + ".getText()));");
                } else if (aux[0].equals("String")) {
                    taCodigo.append(texto.get(0) + " x = comandaLista.procurar(tf"
                            + primeiraLetraMaiuscula(aux[1]) + ".getText());");
                }
                taCodigo.append("\n\t\t\t\tif(x == null){"
                        + "\n\t\t\t\t\tJOptionPane.showMessageDialog(null, \"Não encontrado!\");"
                        + "\n\t\t\t\t} else {");
                //Conversão
                for (int i = 2; i < texto.size(); i++) {
                    String aux1[] = texto.get(i).split(" ");
                    if (aux1[0].equals("String")) {
                        taCodigo.append("\n\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".setText(x.get"
                                + primeiraLetraMaiuscula(aux1[1]) + "());");
                    } else if (aux1[0].equals("Date")) {
                        taCodigo.append("\n\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".setText(sdf.format("
                                + "x.get" + primeiraLetraMaiuscula(aux1[1]) + "()));");
                    } else if (aux1[0].equals("boolean")) {
                        taCodigo.append("\n\t\t\t\t\tif (x.get" + primeiraLetraMaiuscula(aux1[1]) + "()) {"
                                + "\n\t\t\t\t\t\tbTrue.setSelected(true);"
                                + "\n\t\t\t\t\t} else {"
                                + "\n\t\t\t\t\t\tbFalse.setSelected(true);\n\t\t\t\t\t}");
                    } else {
                        taCodigo.append("\n\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".setText(String.valueOf("
                                + "x.get" + primeiraLetraMaiuscula(aux1[1]) + "()));");
                    }
                }
                taCodigo.append("\n\t\t\t\t\tbtSalvar.setVisible(true);"
                        + "\n\t\t\t\t\tbtCancelar.setVisible(true);\n\t\t\t\t}\n\t\t\t}\n\t\t});");
                //Botão Remover
                taCodigo.append("\n\n\t\tbtRemover.addActionListener(new ActionListener() {\n\t\t\t@Override"
                        + "\n\t\t\tpublic void actionPerformed(ActionEvent ae) {\n\t\t\t\t");
                //Procurar chave
                if (aux[0].equals("int")) {
                    taCodigo.append(texto.get(0) + " x = comandaLista.procurar(Integer.valueOf(tf"
                            + primeiraLetraMaiuscula(aux[1]) + ".getText()));");
                } else if (aux[0].equals("String")) {
                    taCodigo.append(texto.get(0) + " x = comandaLista.procurar(tf"
                            + primeiraLetraMaiuscula(aux[1]) + ".getText());");
                }
                taCodigo.append("\n\t\t\t\tif(x == null){"
                        + "\n\t\t\t\t\tJOptionPane.showMessageDialog(null, \"Digite um ID válido!\");"
                        + "\n\t\t\t\t} else {"
                        + "\n\t\t\t\t\tcomandaLista.remover(x);\n\t\t\t\t\tzerarAtributos();"
                        + "\n\t\t\t\t\tbtListar.doClick();"
                        + "\n\t\t\t\t\tJOptionPane.showMessageDialog(null, \"Removido!\");"
                        + "\n\t\t\t\t}\n\t\t\t}\n\t\t});");
                //Botão Procurar
                taCodigo.append("\n\n\t\tbtProcurar.addActionListener(new ActionListener() {\n\t\t\t@Override"
                        + "\n\t\t\tpublic void actionPerformed(ActionEvent ae) {\n\t\t\t\t");
                //Procura chave
                if (aux[0].equals("int")) {
                    taCodigo.append(texto.get(0) + " x = comandaLista.procurar(Integer.valueOf(tf"
                            + primeiraLetraMaiuscula(aux[1]) + ".getText()));");
                } else if (aux[0].equals("String")) {
                    taCodigo.append(texto.get(0) + " x = comandaLista.procurar(tf"
                            + primeiraLetraMaiuscula(aux[1]) + ".getText());");
                }
                taCodigo.append("\n\t\t\t\tif(x == null){"
                        + "\n\t\t\t\t\tJOptionPane.showMessageDialog(null, \"Não está na lista!\");"
                        + "\n\t\t\t\t\tjTextArea.setText(\"\");"
                        + "\n\t\t\t\t} else {");
                //Conversão
                for (int i = 2; i < texto.size(); i++) {
                    String aux1[] = texto.get(i).split(" ");
                    if (aux1[0].equals("String")) {
                        taCodigo.append("\n\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".setText(x.get"
                                + primeiraLetraMaiuscula(aux1[1]) + "());");
                    } else if (aux1[0].equals("Date")) {
                        taCodigo.append("\n\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".setText(sdf.format("
                                + "x.get" + primeiraLetraMaiuscula(aux1[1]) + "()));");
                    } else if (aux1[0].equals("boolean")) {
                        taCodigo.append("\n\t\t\t\t\tif (x.get" + primeiraLetraMaiuscula(aux1[1]) + "()) {"
                                + "\n\t\t\t\t\t\tbTrue.setSelected(true);"
                                + "\n\t\t\t\t\t} else {"
                                + "\n\t\t\t\t\t\tbFalse.setSelected(true);\n\t\t\t\t\t}");
                    } else {
                        taCodigo.append("\n\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".setText(String.valueOf("
                                + "x.get" + primeiraLetraMaiuscula(aux1[1]) + "()));");
                    }
                }
                taCodigo.append("\n\t\t\t\t}\n\t\t\t}\n\t\t}); ");
                //Botão Salvar
                taCodigo.append("\n\n\t\tbtSalvar.addActionListener(new ActionListener() {\n\t\t\t@Override"
                        + "\n\t\t\tpublic void actionPerformed(ActionEvent ae) {\n\t\t\t\t");
                //Procurar chave
                if (aux[0].equals("int")) {
                    taCodigo.append(texto.get(0) + " " + texto.get(0).toLowerCase()
                            + " = comandaLista.procurar(Integer.valueOf(tf"
                            + primeiraLetraMaiuscula(aux[1]) + ".getText()));");
                } else if (aux[0].equals("String")) {
                    taCodigo.append(texto.get(0) + " " + texto.get(0).toLowerCase()
                            + " = comandaLista.procurar(tf"
                            + primeiraLetraMaiuscula(aux[1]) + ".getText());");
                }
                taCodigo.append("\n\t\t\t\t" + texto.get(0) + " original = " + texto.get(0).toLowerCase() + ";"
                        + "\n\t\t\t\tboolean erro = false;");
                //Conversões e try catch
                for (int i = 2; i < texto.size(); i++) {
                    String aux1[] = texto.get(i).split(" ");
                    if (aux1[0].equals("String")) {
                        taCodigo.append("\n\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(tf"
                                + primeiraLetraMaiuscula(aux1[1])
                                + ".getText()" + ");");
                    } else if (aux1[0].equals("int")) {
                        taCodigo.append("\n\t\t\t\t\t\ttry {"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(Integer.valueOf(tf"
                                + primeiraLetraMaiuscula(aux1[1])
                                + ".getText())" + ");"
                                + "\n\t\t\t\t\t\t} catch (NumberFormatException ex) {"
                                + "\n\t\t\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".set"
                                + "Background(Color.red);"
                                + "\n\t\t\t\t\t\t\terro = true;\n\t\t\t\t\t\t}");
                    } else if (aux1[0].equals("float")) {
                        taCodigo.append("\n\t\t\t\t\t\ttry {"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(Float.valueOf(tf"
                                + primeiraLetraMaiuscula(aux1[1])
                                + ".getText())" + ");"
                                + "\n\t\t\t\t\t\t} catch (NumberFormatException ex) {"
                                + "\n\t\t\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".set"
                                + "Background(Color.red);"
                                + "\n\t\t\t\t\t\t\terro = true;\n\t\t\t\t\t\t}");
                    } else if (aux1[0].equals("boolean")) {
                        taCodigo.append("\n\t\t\t\t\t\tif (bTrue.isSelected()){"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(true);"
                                + "\n\t\t\t\t\t\t} else if (bFalse.isSelected()) {"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(false);\n\t\t\t\t\t\t}");
                    } else if (aux1[0].equals("double")) {
                        taCodigo.append("\n\t\t\t\t\t\ttry {"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(Double.valueOf(tf"
                                + primeiraLetraMaiuscula(aux1[1])
                                + ".getText())" + ");"
                                + "\n\t\t\t\t\t\t} catch (NumberFormatException ex) {"
                                + "\n\t\t\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1]) + ".set"
                                + "Background(Color.red);"
                                + "\n\t\t\t\t\t\t\terro = true;\n\t\t\t\t\t\t}");
                    } else if (aux1[0].equals("Date")) {
                        taCodigo.append("\n\t\t\t\t\t\ttry {"
                                + "\n\t\t\t\t\t\t\t" + texto.get(0).toLowerCase() + ".set"
                                + primeiraLetraMaiuscula(aux1[1]) + "(sdf.parse(tf"
                                + primeiraLetraMaiuscula(aux1[1])
                                + ".getText())" + ");"
                                + "\n\t\t\t\t\t\t} catch (ParseException ex) {"
                                + "\n\t\t\t\t\t\t\ttf" + primeiraLetraMaiuscula(aux1[1])
                                + ".setBackground(Color.red);"
                                + "\n\t\t\t\t\t\t\terro = true;\n\t\t\t\t\t\t}");
                    }
                }
                taCodigo.append("\n\t\t\t\tcomandaLista.alterar(original, " + texto.get(0).toLowerCase()
                        + ");\n\t\t\t\tzerarAtributos();"
                        + "\n\t\t\t\tbtListar.doClick();"
                        + "\n\t\t\t\tbtSalvar.setVisible(false);"
                        + "\n\t\t\t\tbtCancelar.setVisible(false);\n\t\t\t}\n\t\t});");
                //Botão Cancelar
                taCodigo.append("\n\n\t\tbtCancelar.addActionListener(new ActionListener() {\n\t\t\t@Override"
                        + "\n\t\t\tpublic void actionPerformed(ActionEvent ae) {"
                        + "\n\t\t\t\tbtSalvar.setVisible(false);\n\t\t\t\tbtCancelar.setVisible(false);"
                        + "\n\t\t\t}\n\t\t});");
                //Botão Listar
                taCodigo.append("\n\n\t\tbtListar.addActionListener(new ActionListener() {\n\t\t\t@Override"
                        + "\n\t\t\tpublic void actionPerformed(ActionEvent ae) {"
                        + "\n\t\t\t\tList<" + texto.get(0) + "> lista = comandaLista.listagem();"
                        + "\n\t\t\t\tjTextArea.setText(\"\");"
                        + "\n\t\t\t\tfor(" + texto.get(0) + " lista1 : lista) {"
                        + "\n\t\t\t\t\tjTextArea.append(lista1 + System.lineSeparator());"
                        + "\n\t\t\t\t}\n\t\t\t}\n\t\t});");
                taCodigo.append("\n\tsetDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);"
                        + "\n\taddWindowListener(new WindowAdapter() {"
                        + "\n\t\t@Override"
                        + "\n\t\tpublic void windowClosing(WindowEvent e) {"
                        + "\n\t\t\tcomandaLista.serializaLista(\"" + texto.get(0) + ".dat\");"
                        + "\n\t\t\tSystem.exit(0);\n\t\t}\n\t});"
                        + "\n\n\tsetLocationRelativeTo(null);\n\tsetVisible(true);"
                        + "\n\t}\n}");
            }
        }
        );
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
