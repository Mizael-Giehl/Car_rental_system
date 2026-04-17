package locadoradecarros;

import DAO.CarroDAO;
import DAO.EmprestimoDAO;
import DAO.ModeloDAO;
import DAO.UsuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Carro;
import java.util.List;
import java.util.Scanner;
import model.Emprestimo;
import model.Modelo;
import model.Usuario;
import util.Exportador;
import util.Importador;
import util.Seguranca;

public class LocadoraDeCarros {

    public static void main(String[] args) throws SQLException, IOException {

        CarroDAO dao = new CarroDAO();
        ModeloDAO modelodao = new ModeloDAO();
        EmprestimoDAO edao = new EmprestimoDAO();
        Scanner teclado = new Scanner(System.in);
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        int opcao = 0;
        int opcao2 = 0;
        
        
        
        
        System.out.println(" ----------------------------- ");
        System.out.println(" ------- AREA DE LOGIN ------- ");
        System.out.println(" ----------------------------- ");
        
        UsuarioDAO userDao = new UsuarioDAO();
        Usuario usuarioNovo = new Usuario();
        
        System.out.println("Voce ja tem login? sim/nao");
        String TemLogin = teclado.nextLine();
        
        
        
        if (TemLogin.equalsIgnoreCase("nao")) {
        System.out.println("Qual sera o seu login: ");
        String LoginNovo = teclado.nextLine();
        
        System.out.println("Qual sera sua senha: ");
        String NovaSenha = teclado.nextLine();
        
        userDao.cadastrarUsuario(LoginNovo, NovaSenha);
        
            System.out.println("Login feito com sucesso!");
            System.out.println("Logue normalmente!");
        
        } else if(TemLogin.equalsIgnoreCase("sim")) {
            
        
        
        boolean logado = false;
        int tentativas = 0;
        
        while(!logado && tentativas < 3) {
            System.out.println("Login: ");
            String login = teclado.nextLine();
            
            System.out.println("Senha: ");
            String senha = teclado.nextLine();
            
            if ((login.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("admin123"))) {
                System.out.println("Acesso concedido! Bem-vindo, " + login + ".");
                logado = true;
                
                
                do {
            System.out.println("\n ----------  MENU ADMIN  ---------- ");
            System.out.println(" -- Sistema de Aluguel de Carros -- ");
            System.out.println("1 - Cadastrar Carro");
            System.out.println("2 - Cadastrar Modelo");
            System.out.println("3 - Listar Carros");
            System.out.println("4 - Deletar Carro");
            System.out.println("5 - Exportar");
            System.out.println("6 - Importar");
            System.out.println("0 - Sair do Programa");
            System.out.print("Escolha uma opcao: ");

            opcao = teclado.nextInt();
            teclado.nextLine(); 

            switch (opcao) {
                
                
                case 1:
           System.out.println("\n|| CADASTRAR CARRO ||");
    
           System.out.print("Digite o ano do veículo: ");
           int anoCadastro = teclado.nextInt();
           teclado.nextLine();

          System.out.print("Digite o status do veículo: ");
          String statusCadastro = teclado.nextLine();
          
          ModeloDAO mdao = new ModeloDAO();
          List<Modelo> modelos = mdao.listarModelos();

         for (Modelo m : modelos) { 
         System.out.println(m.getId_modelo() + " - " + m.getNome_modelo());
         }
   

         System.out.print("Digite o ID do Modelo escolhido acima: ");
         int idModelo = teclado.nextInt();

         System.out.println("Digite o valor do veiculo por semana: ");
         double valor = teclado.nextDouble();
        
   
          Carro carroNovo = new Carro();
          carroNovo.setAno(anoCadastro);
          carroNovo.setStatus(statusCadastro);
         
   
          model.Modelo mod = new model.Modelo();
          mod.setId_modelo(idModelo);

          carroNovo.setValor_carro(valor);
          
          try {
              dao.cadastrar(carroNovo, mod);
        
          } catch (Exception e) {
              System.out.println("Erro ao cadastrar: " + e.getMessage());
          }
    
          break;

          
                case 2:
          
           System.out.println("\n|| CADASTRAR MODELO ||");

                    System.out.println("Digite o nome do modelo que voce deseja cadastrar:EX volkswagen Gol G3");
                    String NomeModeloNovo = teclado.nextLine();
                    teclado.nextLine();

          Modelo modeloNovo = new Modelo();
          
          modeloNovo.setNome_modelo(NomeModeloNovo);
          
          try {
              modelodao.adicionarModelo(modeloNovo);
        
          } catch (Exception e) {
              System.out.println("Erro ao cadastrar modelo: " + e.getMessage());
          }
          
          break;
          
          
                case 3:
                    System.out.println("\n--- VEÍCULOS ---");
                    try {
                        List<Carro> lista = dao.listar();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum carro encontrado.");
                        } else {
                            for (Carro c : lista) {
                                System.out.println(c);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao buscar a lista: " + e.getMessage());
                    }
                    break; 

                    
                    
                    case 4:
                    System.out.print("Digite o ID do carro que deseja deletar: ");
                    int idDeletar = teclado.nextInt();
                    dao.deletar(idDeletar);
                    break;
                    
                    
                    
                    case 5:
                    
                    System.out.println(" --- EXPORTADOR ---");
                    System.out.println("Digite o nome do ficheiro : ");
                    String nomeArquivo = teclado.nextLine();
                    String exportacao = nomeArquivo + ".csv";
                    
                    Exportador ex = new Exportador();
                    ex.exportarInventario(exportacao);
                    
                        break;
                        
                    case 6:
                        
                    System.out.println(" --- IMPORTADOR ---");
                    System.out.println("Digite o nome do ficheiro (NÃO SE ESQUEÇA DE ESCREVER COMO NO EXEMPLO: exemplo.csv): ");
                    String importacao = teclado.nextLine();
                    
                    Importador im = new Importador();
                    im.importarCarros(importacao);
                    
                        break;
                        
                    
                case 0:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente inserir outra opção.");
                    break;
            }

        } while (opcao != 0);

        teclado.close();
                
                
            } else if(userDao.autenticar(login, senha)) {
                
                Usuario user = userDao.buscarPorLogin(login);
                
                System.out.println("Acesso concedido! Bem-vindo, " + login + ".");
                logado = true;
                
                
                do {
                System.out.println("\n ----------  MENU PRINCIPAL  ---------- ");
                System.out.println(" --- Seu saldo atual e: " + user.getSaldo() + " ---");
                System.out.println("1 - Alugar Carro");
                System.out.println("2 - Devolver Carro");
                System.out.println("3 - Adicionar Saldo");
                System.out.println("0 - Sair do sistema");
                System.out.println("-------------------------------------------");
                
                
                opcao2 = teclado.nextInt();
            teclado.nextLine(); 

            switch (opcao2) {
                
                
                case 1:
                    System.out.println("\n ------- Alugar -------");
                    try {
                        List<Carro> lista = dao.listar();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum carro encontrado.");
                            
                        } else {
                            
                            for (Carro c : lista) {
                                System.out.println(c);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao buscar a lista: " + e.getMessage());
                    }
                    
                System.out.println("Digite o id do carro que deseja Alugar:");
                int idEscolhido = lerInteirosSeguro(teclado);
                teclado.nextLine(); 

                String StatusAtual = dao.verificarStatus(idEscolhido);
                
                Double valorAluguel = dao.verificarValor(idEscolhido);

                if (StatusAtual == null) {
                    
                System.out.println("Erro o carro nao foi encontrado!");

                } else if(StatusAtual.equalsIgnoreCase("Alugado")) {
                    
                System.out.println("Este veioculo ja esta alugado!");     
      
                } else if(StatusAtual.equalsIgnoreCase("Disponivel")) {
        
               if(user.getSaldo() >= valorAluguel) {
            
            Emprestimo EmprestimoNovo = dao.alugarCarro(user.getId(), idEscolhido);
            emprestimos.add(EmprestimoNovo);
            
            userDao.addSaldo(user.getId(), - valorAluguel);
            user.setSaldo(user.getSaldo() - valorAluguel);
            
            System.out.println("-------------------------------------------");
            System.out.println("SUCESSO! O veículo foi reservado para você.");
            System.out.println("Valor debitado: R$ " + valorAluguel);
            System.out.println("Saldo atual: R$ " + user.getSaldo());
            System.out.println("-------------------------------------------");

        } else {
            System.out.println("Erro! Saldo insuficiente!");
        }
    }
    break;
                
                
                case 2:
                    System.out.println("\n ------- SEUS CARROS ALUGADOS -------");
    try {
        
        List<Carro> meusCarros = edao.CarrosPorUsuarios(user.getId());

        if (meusCarros.isEmpty()) {
            System.out.println("Você não possui carros alugados no momento.");
        } else {
            for (Carro c : meusCarros) {
                System.out.println("ID: " + c.getId_carro() + " | Ano: " + c.getAno() + " | Valor: " + c.getValor_carro());
            }
            
            System.out.println("\nDigite o ID do carro que deseja devolver:");
            int idDevolver = lerInteirosSeguro(teclado);
            teclado.nextLine(); 

            dao.devolverCarro(idDevolver);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar seus carros: " + e.getMessage());
    }
                    break;
                    
                
                case 3:
                    
                System.out.println("--------------------------");
                System.out.print("Quanto deseja adicionar? ");
                double saldoAdd = teclado.nextDouble();
                teclado.nextLine();
                
                System.out.println("Para confirmar a transacao digite sua senha: ");
                String SenhaS = teclado.nextLine();
                
                String senhaHashDigitada = Seguranca.gerarHash(SenhaS);
                
                if(senhaHashDigitada.equals(user.getSenhaHash())) {
                
                
                userDao.addSaldo(user.getId(), saldoAdd);
                
                user.setSaldo(user.getSaldo() + saldoAdd);
                
                System.out.println("Saldo adicionado com sucesso!");
                System.out.println("--------------------------");
                
                } else {
                    System.out.println("Senha errada!");
                }
                
                break;
                    
                    
                    
                case 0:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente inserir outra opção.");
                    break;
            }

        } while (opcao2 != 0);

        teclado.close();
                
                
                
            }else {
                tentativas++;
                System.out.println("Login ou senha incorretos. Tentativa " + tentativas + " de 3.");
            
            }
            
        }
        
        if (!logado) {
            System.out.println("Sistema bloqueado por excesso de tentativas.");
            System.exit(0);
        }
        
        } else {
            
            System.out.println("Responda somente sim ou nao!");
            System.out.println("Tente novamente!");
            
            System.exit(0);
        }
        
        
    }
    
    public static int lerInteirosSeguro(Scanner teclado) {
        boolean dadosValidos = false;
        int numero = 0;
        while (!dadosValidos) {
            try {
                numero = teclado.nextInt();
                dadosValidos = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("ERRO! Digite apenas números.");
                teclado.next();
            }
        }
        return numero;
    }
}