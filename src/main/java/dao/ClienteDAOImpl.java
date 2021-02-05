package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidade.Cliente;
import entidade.Contato;
import util.JpaUtil;

public class ClienteDAOImpl implements ClienteDAO {

	@Override
	public boolean inserirCliente(Cliente cliente) {
		
		EntityManager ent = JpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		ent.merge(cliente);

		tx.commit();
		ent.close();

		return true;
	}

	@Override
	public List<Cliente> pesquisarCliente(Cliente cliente) {
		
		String sql = "from Cliente c where 1=1 ";
		
		if(cliente.getCpf() != null && !cliente.getCpf().isEmpty()) {
			sql += " and c.cpf = '" + cliente.getCpf() + "'";
		}

		EntityManager ent = JpaUtil.getEntityManager();

		Query query = ent.createQuery(sql);

		List<Cliente> listaClientes = query.getResultList();

		ent.close();

		return listaClientes;
	}

	@Override
	public boolean adicionarContatoCliente(Cliente cliente) {
		
		EntityManager ent = JpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		tx.begin();
		
		for (Contato contato : cliente.getListaContatos()) {
			ent.merge(contato);			
		}
		
		tx.commit();
		ent.close();

		return true;
	}

	@Override
	public boolean removerContatoCliente(Cliente cliente) {
		
		EntityManager ent = JpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		for (Contato contato : cliente.getListaContatos()) {
			Contato existe = ent.find(Contato.class, contato.getId());
			if(existe != null) {
				ent.remove(existe);
			}
		}
		
		tx.commit();
		ent.close();
		return true;
		
	}
	
	public Cliente existeCliente(Cliente cliente) {
		EntityManager ent = JpaUtil.getEntityManager();

		Cliente existe = ent.find(Cliente.class, cliente.getCpf());
		
		ent.close();
		return existe;
		
	}

}
