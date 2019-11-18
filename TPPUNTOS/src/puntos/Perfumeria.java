package puntos;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Perfumeria implements Vende {

	private static Perfumeria instancia;
	private static String nombre;
	private Set<Cliente> listaUsuarios = new TreeSet<Cliente>();
	private List<Venta> listaVentas = new LinkedList<Venta>();
	private List<Producto> listaProducto = new LinkedList<Producto>();
	private Boolean sesionAbierta;

	public Perfumeria(String nombre) {
		this.nombre = nombre;
		this.sesionAbierta = false;
	}

	public static Perfumeria getInstancia() {
		if (instancia == null)
			instancia = new Perfumeria(nombre);
		return instancia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Cliente> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(HashSet<Cliente> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<Venta> getListaVentas() {
		return listaVentas;
	}

	public void setListaVentas(LinkedList<Venta> listaVentas) {
		this.listaVentas = listaVentas;
	}

	public List<Producto> getListaProducto() {
		return listaProducto;
	}

	public void setListaProducto(LinkedList<Producto> listaProducto) {
		this.listaProducto = listaProducto;
	}

	public Boolean agregarUsuario(Cliente usuario) {

		return listaUsuarios.add(usuario);

	}

	public Boolean agregarProducto(Producto producto) {

		return listaProducto.add(producto);

	}

	public Boolean agregarVenta(Venta venta) {

		return listaVentas.add(venta);

	}

	public Boolean loguearUsuario(String email, String password) throws UsuarioIncorrectoException {

		for (Cliente lista : listaUsuarios) {

			if (lista.getEmail().equals(email) && lista.getPassword().equals(password)) {

				sesionAbierta = true;
				break;

			} else {

				sesionAbierta = false;
				throw new UsuarioIncorrectoException();
			}

		}

		return sesionAbierta;

	}

	public void cerrarSesion() {

		if (this.sesionAbierta = true) {
			this.sesionAbierta = false;
		}
	}

	public Boolean eliminarUsuario(Integer idU) throws UsuarioIncorrectoException {

		Boolean eliminado = false;

		Iterator<Cliente> it = listaUsuarios.iterator();

		while (it.hasNext()) {

			Cliente u = it.next();
			if (u.getId().equals(idU)) {
				it.remove();
				eliminado = true;
			} else {
				throw new UsuarioIncorrectoException();
			}

		}

		return eliminado;

	}

	

	public Producto buscarProductoPorId(Integer idProducto) throws ProductoNoEncontradoException {

		Producto productoEncontrado = null;

		for (Producto buscarProducto : listaProducto) {
			if (buscarProducto.getId().equals(idProducto)) {

				productoEncontrado = buscarProducto;
			} else {
				throw new ProductoNoEncontradoException();
			}

		}

		return productoEncontrado;

	}

	public Boolean anularCompra(Integer id) throws CompraNoEncontradaException {

		Boolean compraAnulada = false;

		Iterator<Venta> it = listaVentas.iterator();

		while (it.hasNext()) {

			Venta v = it.next();
			if (v.getIdVenta().equals(id)) {
				it.remove();
				compraAnulada = true;
			} else {
				throw new CompraNoEncontradaException();
			}

		}

		return compraAnulada;

	}

	@Override
	public Boolean venderConPuntos(Cliente c , Integer idP, Integer puntos)throws ProductoNoEncontradoException {

		Boolean ventaExitosa = false;
		Integer totalPuntos=0;
		Venta buscarVenta=null;
		
		Iterator<Venta>iterator=this.listaVentas.iterator();
		while(buscarVenta == null && iterator.hasNext()) {
			Venta venta=iterator.next();
			if (venta.getCliente().equals(c) && venta.getProducto().getId().equals(idP)) {
				if (venta.getProducto().getEstado() == true && ((Cliente) venta.getCliente()).getPuntos()>=venta.getProducto().getPunto()) {
					totalPuntos=((Cliente) venta.getCliente()).getPuntos()-puntos;
					ventaExitosa = true;
					buscarVenta=venta;
				}
			}
		}
		
		if(buscarVenta==null) {
			ventaExitosa=false;
			throw new ProductoNoEncontradoException();
		}
		return ventaExitosa;
	}

	

	@Override
	public Boolean venderConEfectivo(Cliente c , Integer idP) throws ProductoNoEncontradoException {
		Boolean ventaExitosa = false;

		for (Venta ventasAux : this.listaVentas) {
			if (ventasAux.getCliente().getEmail().equals(c.getEmail()) && ventasAux.getProducto().getId().equals(idP)) {
				if (ventasAux.getProducto().getEstado() == true) {

					ventaExitosa = true;

				}

			}else throw new ProductoNoEncontradoException();
		}
		return ventaExitosa;
	}

	public Integer obtenerPuntosDeUnCliente(Cliente c){
		Integer puntos=-1;
		
		Iterator<Cliente>iterator=this.listaUsuarios.iterator();
		while(iterator.hasNext()) {
			Cliente cliente=iterator.next();
			if (cliente.equals(c)) {
				puntos= cliente.getPuntos();
				}
			}
		return puntos;
	}
	}
	

