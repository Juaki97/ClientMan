package model;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by garci on 16/05/2017.
 */
public class ClientesRecibidos {
    private LinkedList<Cliente> clientesRecibidos;

    public ClientesRecibidos(){
        clientesRecibidos = new LinkedList<>();
    }

    /**
     * Recibe un cliente al que le ha llegado la máquina y comprueba si ya está en la lista
     * por una compra previa.
     *
     * Si está, devuelve mensaje y no lo vuelve a añadir.
     * En caso de no estar, avisa que añadirá y añade.
     * @param cliente
     */
    public void registarCliente(Cliente cliente){
        if (clientesRecibidos.contains(cliente)){
            System.out.println("El cliente con DNI " + cliente.getDni_nif() + ", ya había recibido una máquina. No se volverá a añadir a la lista.");
            cliente = null;
        }

        if (cliente != null){
            System.out.println("Un cliente ya ha recibido su Thermomix.");
            clientesRecibidos.add(cliente);
            guardarClientesRecibidos();
        }
    }

    /**
     * Devulve un cliente dado un índice para buscarlo en la lista
     * @param indice
     * @return
     */
    public Cliente devolverCliente(int indice){
        Cliente cliente = clientesRecibidos.get(indice);

        return cliente;
    }

    /**
     * Borra el cliente del identificador pasado
     * @param identificador
     */
    public void eliminarCliente(String identificador){
        String comprobador = null;
        Iterator<Cliente> itCliente = clientesRecibidos.iterator();
        while (itCliente.hasNext()) {
            Cliente cliente = itCliente.next();

            if (cliente.getDni_nif().equals(identificador)) {
                itCliente.remove();
                comprobador = "";
                guardarClientesRecibidos();
            }
        }
        if (comprobador == null){
            System.out.println("El Cliente no existe.");
        }
    }


    /**
     * Devuelve el tamaño de la lista
     * @return longitud de clientes
     */
    public int longitud(){
        return clientesRecibidos.size();
    }

    /**
     * Ordena los clientes por nombres y los muestra
     */
    public void mostrarClientes(){
        Collections.sort(clientesRecibidos,Cliente.comparadorPorNombre);

        for (Cliente cliente: clientesRecibidos){
            System.out.println(cliente);
        }
    }

    /**
     * Escribirá la lista actual de clientes en info/clientesRecibidos.dat
     */
    public void guardarClientesRecibidos() {
        try {
            ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream("info/clientesRecibidos.dat"));
            fos.writeObject(clientesRecibidos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cargará la lista guardada de clientes en info/clientesPendientes.dat
     */
    public void cargarClientesRecibidos(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("info/clientesRecibidos.dat"));

            clientesRecibidos = (LinkedList<Cliente>) ois.readObject();

            ois.close();
        } catch (IOException e) {
        } catch (ClassNotFoundException e){

        }
    }
}
