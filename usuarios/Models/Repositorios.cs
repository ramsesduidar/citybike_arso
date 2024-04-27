namespace Repositorios;

public interface IRepositorio<T, K>
{
    K Add(T entity);
    void Update(T entity);
    void Delete(T entity);
    T GetById(K id);
    List<T> GetAll();
    List<K> GetIds();
}

public class EntidadNoEncontradaException : Exception{

    // Constructor que acepta un mensaje de error opcional
    public EntidadNoEncontradaException(string message) : base(message)
    {
    }

    // Constructor que acepta un mensaje de error y una excepción interna opcional
    public EntidadNoEncontradaException(string message, Exception innerException) : base(message, innerException)
    {
    }
}