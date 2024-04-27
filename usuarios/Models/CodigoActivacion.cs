namespace Usuarios.Modelo;

public class CodigoActivacion
{
    public Guid Valor {get; set;}
    
    public DateTime FechaCreacion {get; set;}

    public DateTime FechaCaducidad {get; set;}

    // El código será válido durante las siguentes 24 horas
    // desde su creación
    public CodigoActivacion(Guid valor)
    {
        this.Valor = valor;
        this.FechaCaducidad = DateTime.Now.AddDays(1);
        this.FechaCreacion = DateTime.Now;
    }
}