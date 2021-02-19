package controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sun.media.sound.EmergencySoundbank;

import dao.ClienteDAO;
import dao.ClienteDAOImpl;
import entidade.Cliente;
import entidade.Contato;

@ManagedBean(name = "ClienteBean")
@SessionScoped
public class ClienteBean {

	private Cliente cliente; // Utilizado no cadastrogeral
	private Contato contato; // Utilizado no add contato
	private List<Cliente> listaClientes;

	private ClienteDAO clienteDAO;

	// Valores utilizados para regra de remover contato
	private String emailSel, telefoneSel;

	/**
	 * Construtor ClienteBean, onde inicializa o clienteDAO, dando acesso ao banco
	 * de dados
	 */
	public ClienteBean() {
		this.clienteDAO = new ClienteDAOImpl();
		this.inicializarCampos();
	}

	/**
	 * Metodo utilizado para zera e inicializar todos os campos e atributos
	 */
	public void inicializarCampos() {

		this.cliente = new Cliente();
		this.cliente.setListaContatos(new ArrayList<Contato>());

		// this.contato é o objeto referenciado para adicionar os contatos em tela
		this.contato = new Contato();

		this.listaClientes = new ArrayList<Cliente>();
	}

	/**
	 * Metodo utilizado para salvar o cliente com seus contatos
	 */
	public void salvar() {

		if (clienteDAO.existeCliente(cliente) == null) {
			this.clienteDAO.inserirCliente(cliente);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente inserido com sucesso"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Cliente já existe!"));
		}

	}

	/**
	 * Metodo utilizado para adicionar o contato
	 */
	public void adicionarContato() {

		contato.setCliente(cliente);
		this.cliente.getListaContatos().add(contato);

		// Necessario dar uma nova instancia, senão a mesma é afetado em uma nova
		// inserção
		this.contato = new Contato();
	}

	/**
	 * Metodo utilizado para remover contato
	 */
	public void removerContato() {
		
		Contato achou = null;
		for (Contato cont : cliente.getListaContatos()) {
			if(emailSel != null && !emailSel.isEmpty() && 
				telefoneSel != null && !telefoneSel.isEmpty()) {
				
				if(cont.getEmail().equals(emailSel) && 
					cont.getTelefone().equals(telefoneSel)) {
					achou = cont;
				}
				
			}else if(emailSel != null && !emailSel.isEmpty()) {
				if(cont.getEmail().equals(emailSel)) {
					achou = cont;
				}
			}else {
				if(cont.getTelefone().equals(telefoneSel)) {
					achou = cont;
				}
			}
		}
		
		if(achou != null) {
			cliente.getListaContatos().remove(achou);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Contato removido"));
		}
		
	}

	public void pesquisarCliente() {
		this.listaClientes = clienteDAO.pesquisarCliente(cliente, contato);
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

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public String getEmailSel() {
		return emailSel;
	}

	public void setEmailSel(String emailSel) {
		this.emailSel = emailSel;
	}

	public String getTelefoneSel() {
		return telefoneSel;
	}

	public void setTelefoneSel(String telefoneSel) {
		this.telefoneSel = telefoneSel;
	}

}
