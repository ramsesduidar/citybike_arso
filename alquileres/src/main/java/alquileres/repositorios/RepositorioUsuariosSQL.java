package alquileres.repositorios;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import alquileres.dominio.Alquiler;
import alquileres.dominio.Reserva;
import alquileres.dominio.Usuario;
import alquileres.persistencia.jpa.AlquilerEntidad;
import alquileres.persistencia.jpa.ReservaEntidad;
import alquileres.persistencia.jpa.UsuarioEntidad;
import repositorios.EntidadNoEncontrada;
import repositorios.Repositorio;
import repositorios.RepositorioException;
import utils.EntityManagerHelper;

public class RepositorioUsuariosSQL implements Repositorio<Usuario, String>{

	public Class<UsuarioEntidad> getClase() {
		return UsuarioEntidad.class;
	}

	public String getNombre() {
		return UsuarioEntidad.class.getName().substring(UsuarioEntidad.class.getName().lastIndexOf(".") + 1);
	}

	@Override
	public String add(Usuario entity) throws RepositorioException {

		UsuarioEntidad usuarioEntidad = convertirAUsuarioEntidad(entity);
		
		System.out.println("Crear user con id:"+usuarioEntidad.getIdUsuario());

		EntityManager em = EntityManagerHelper.getEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(usuarioEntidad);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al guardar la entidad con id " + entity.getId(), e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
		return entity.getId();
	}

	@Override
	public void update(Usuario entity) throws RepositorioException, EntidadNoEncontrada {
		UsuarioEntidad usuarioEntidad = convertirAUsuarioEntidad(entity);
		
		System.out.println("entidad: " + usuarioEntidad);
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();
			UsuarioEntidad instance = em.find(getClase(), entity.getId());
			if (instance == null) {
				throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
			}
			em.merge(usuarioEntidad);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al actualizar la entidad con id " + entity.getId(), e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}

	@Override
	public void delete(Usuario entity) throws RepositorioException, EntidadNoEncontrada {
		UsuarioEntidad usuarioEntidad = convertirAUsuarioEntidad(entity);

		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			em.getTransaction().begin();
			UsuarioEntidad instance = em.find(UsuarioEntidad.class, entity.getId());
			if(instance == null) {
				throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
			}
			em.remove(instance);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al borrar la entidad con id "+entity.getId(),e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}

	@Override
	public Usuario getById(String id) throws EntidadNoEncontrada, RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			UsuarioEntidad usuarioEntidad = em.find(getClase(), id);
			if (usuarioEntidad == null) {
				throw new EntidadNoEncontrada("Usuario con id " + id + " no encontrado");
			}
			return convertirAUsuario(usuarioEntidad);
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new RepositorioException("Error al recuperar la entidad con id " + id, re);
		} finally {
			EntityManagerHelper.closeEntityManager();
		}
	}

	@Override
	public List<Usuario> getAll() throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			final String queryString = " SELECT model from " + getNombre() + " model ";

			Query query = em.createQuery(queryString);

			query.setHint(QueryHints.REFRESH, HintValues.TRUE);

			return query.getResultList();

		} catch (RuntimeException re) {

			throw new RepositorioException("Error buscando todas las entidades de "+getNombre(),re);

		}
	}

	@Override
	public List<String> getIds() throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			final String queryString = " SELECT model.id from " + getNombre() + " model ";

			Query query = em.createQuery(queryString);

			query.setHint(QueryHints.REFRESH, HintValues.TRUE);

			return query.getResultList();

		} catch (RuntimeException re) {

			throw new RepositorioException("Error buscando todos los ids de "+getNombre(),re);

		}
	}


	private Usuario convertirAUsuario(UsuarioEntidad entidad) {
		Usuario user = new Usuario();
		user.setId(entidad.getIdUsuario());
		
		user.setAlquileres(entidad.getAlquileres()
				.stream()
				.map(a -> this.convertirAlquiler(a))
				.collect(Collectors.toList()));
		
		user.setReservas(entidad.getReservas()
				.stream()
				.map(r -> this.convertirReserva(r))
				.collect(Collectors.toList()));
		
		
		return user;
		
	}
	
	private Alquiler convertirAlquiler(AlquilerEntidad alquiler) {
		
		Alquiler a = new Alquiler();
		a.setIdBicicleta(alquiler.getIdBici());
		a.setInicio(alquiler.getInicio());
		a.setFin(alquiler.getFin());
		
		return a;
		
	}
	
	private Reserva convertirReserva(ReservaEntidad reserva) {
		Reserva r = new Reserva();
		r.setIdBicicleta(reserva.getIdBici());
		r.setCreada(reserva.getCreada());
		r.setCaducidad(reserva.getCaducidad());
		return r;
	}
	
	private UsuarioEntidad convertirAUsuarioEntidad(Usuario user) {
		return new UsuarioEntidad(user);
	}

}