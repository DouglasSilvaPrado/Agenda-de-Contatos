package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	// Modulo de Conexão

	/** The driver. */
	// Parametros de Conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "";

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// Metodo de Conexão
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	/**
	 * Inseriri contato.
	 *
	 * @param contato the contato
	 */
	// CRUDE CREATE
	public void inseririContato(JavaBeans contato) {
		String create = "INSERT INTO contatos(nome,fone,email)VALUES(?,?,?)";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());

			pst.executeUpdate();

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	// CRUDE READ
	public ArrayList<JavaBeans> listarContatos() {
		// Criando um objeto para acessar a class JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();

		String read = "SELECT * FROM contatos order by nome;";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// enquanto houver dados
			while (rs.next()) {
				// variaveis de apoio que recebe dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// populando o ArrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	// CRUD UPDATE
	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	// selecionar o contato
	public void selecionarContato(JavaBeans contato) {
		String read2 = "SELECT * FROM contatos WHERE idcon = ?;";
		try {
			Connection con = conectar();
			PreparedStatement pst =  con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				// setar as variaves JavaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	// editar o contato
	public void alterarContato(JavaBeans contato) {
		String create = "UPDATE contatos SET nome =?, fone =?, email =? WHERE idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst =  con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			
			pst.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// CRUD DELETE
	
	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	public void deletarContato(JavaBeans contato) {
		String delete = "DELETE FROM contatos WHERE idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst =  con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			
			pst.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
