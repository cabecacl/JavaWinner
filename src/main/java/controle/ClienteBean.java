package controle;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.ClienteDAO;
import dao.ClienteDAOImpl;
import entidade.Cliente;
import entidade.Contato;

@ManagedBean(name = "ClienteBean")
@SessionScoped
public class ClienteBean {

	private Cliente cliente; //Utilizado no cadastrogeral
	private Contato contato; //Utilizado no add contato
	
	private ClienteDAO clienteDAO; 

	/**
	 * Construtor ClienteBean, onde inicializa o clienteDAO,
	 * dando acesso ao banco de dados
	 */
	public ClienteBean() {
		this.clienteDAO = new ClienteDAOImpl();
		this.inicializarCampos();
	}
	
	/**
	 * Metodo utilizado para zera e inicializar todos os 
	 * campos e atributos
	 */
	public void inicializarCampos() {
		
		this.cliente = new Cliente();
		this.cliente.setListaContatos(new ArrayList<Contato>());

		//this.contato é o objeto referenciado para adicionar os contatos em tela
		this.contato = new Contato();
	}

	/**
	 * Metodo utilizado para salvar o cliente com seus
	 * contatos
	 */
	public void salvar() {
		this.clienteDAO.inserirCliente(cliente);
		
		FacesContext.getCurrentInstance().
    	addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente inserido com sucesso"));
	}
	/**
	 * Metodo utilizado para adicionar o contato
	 */
	public void adicionarContato() {
		this.cliente.getListaContatos().add(contato);
		
		//Necessario dar uma nova instancia, senão a mesma é afetado em uma nova inserção
		this.contato = new Contato();
	}
	
	/**
	 * Metodo utilizado para remover contato
	 */
	public void removerContato() {
		
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

}
