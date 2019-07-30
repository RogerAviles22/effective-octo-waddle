/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TDA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Rogencio
 */
public class BinaryTree<E> {
    private NodeBT<E> root;
    
    public BinaryTree(){
        root=null;
    }
    
    public boolean isEmpty(){
        return root==null;
    }
    
    /**
     * Agregamos un nodo en la hoja hija vacia Left o Right
     * @param child Elemento hija
     * @param parent Elemento padre
     * @return Boolean
     */
    public boolean add(E child, E parent){
        NodeBT<E> node = new NodeBT<>(child);
        if(child==null) return false; //Validar que el parametro hijo no sea null
        if(isEmpty() && parent==null){ //Si el nodo esta vacio, los hijos son distinto de null y el parent es null
            root=node; //Lo agregamos a la raiz principal
        }else if(searchNode(child)==null){ //Si no existe ese Element (hijo/child) en el arbol, entonces lo agregamos.
            NodeBT<E> p = searchNode(parent);
            if(p==null) return false; //Retorna false si el padre no existe
            else if(p.getLeft()==null) p.setLeft(node); //Agregamos el nodo en el lado Izq. en caso que no exista.
            else if(p.getRight()==null) p.setRight(node); //Agregamos el nodo en el lado Der. en caso que no exista.
            else return false; //En caso que este llenos los hijos
        }
        
        return true;
    }
    
    public BinaryTree<E> mirror() {
        if(size()==1) return this;        
        BinaryTree<E> tree = new BinaryTree<>();
        tree.root = mirror(this.root);        
        return tree;
    }
    
    public NodeBT<E> mirror(NodeBT<E> p) {
        if(p==null) return p;
        NodeBT<E> temp=p.getLeft(); //Almacenamos en un temporal el dato izquierdo
        p.setLeft(mirror(p.getRight())); //Seteamos el valor izquierdo
        p.setRight(mirror(temp)); //Seteamos el valor derecho por el izquierdo
        return p;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.root);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {   
        if (!(obj instanceof BinaryTree)  || getClass() != obj.getClass()) 
            return false;   
        else if(obj == this) return true;   
        BinaryTree<E> other = (BinaryTree<E>) obj;
        if(this.size()!=other.size()) return false;        
        return equals(this.root, other.root);
    }
    
    private boolean equals(NodeBT<E>p, NodeBT<E> p2){
        if(p==null && p2==null) return true;
        else if(p==null || p2==null) return false;
        else if(!p.getData().equals(p2.getData())) return false;
        return equals(p.getLeft(),p2.getLeft()) && equals(p.getRight(), p2.getRight());    
    }
    
    public boolean remove(E child){
        if(isEmpty() || child==null) return false;
        if(root.getData().equals(child)) root=null; //En caso que el dato agregado sea igual al root (Padre), borramos el arbol
        
        NodeBT<E> parent= searchParent(child); //Obtenemos el padre del hijo mencionado como parametro
        if(parent == null) return false; //Si no lo encuentra, retorna false
        
        if(parent.getLeft()!= null && parent.getLeft().getData().equals(child)) 
            parent.setLeft(null); //Seteamos el hijo Izq a null del padre
        else
            parent.setRight(null); //Seteamos el hijo Der. a null del padre
        return true;
    }
    
    /**
     * Buscamos al padre del Elemento que queremos buscar para setear su nodo _null_ (ya sea Izq o Der.) y eliminar todo su nodo. 
     * @param child
     * @return 
     */
    private NodeBT<E> searchParent(E child){
        return searchParent(child, root);
    }
    
    private NodeBT<E> searchParent(E child, NodeBT<E> node){
        if(node==null) return null;
        if(node.getLeft()!=null && node.getLeft().getData().equals(child)) return node;
        else if(node.getRight()!=null && node.getRight().getData().equals(child)) return node;
        NodeBT<E> n=searchParent(child, node.getLeft());
        return (n!=null) ? n: searchParent(child, node.getRight());
    }
    
    /**
     * 
     * @param data Elemento a buscar en el arbol
     * @return Nodo que queremos buscar
     */
    private NodeBT<E> searchNode(E data){
        if(data==null) return null;
        return searchNode(data, root);
    }
    
    private NodeBT<E> searchNode(E data, NodeBT<E> p){
        if(p==null) return null;
        else if(p.getData().equals(data)) return p;
        NodeBT<E> l= searchNode(data,p.getLeft());
        return(l!=null)? l: searchNode(data,p.getRight());
    }
    
        /*if(l!=null) return l;
        return searchNode(data,p.getRight());*/
    
    public int size(){
        return size(root);
    }
    
    private int size(NodeBT<E> p){
        if(p==null) return 0;
        return 1+size(p.getLeft())+size(p.getRight());
    }
    
    /**
     * Obtenemos la altura del arbol
     * @return Valor de la altura máxima de ambos lados.
     */
    public int height(){
        return height(root);
    }
    
    private int height(NodeBT<E> p){
        if(p==null) return 0;
        else if(p.getLeft()==null && p.getRight()==null) return 1;        
        //más óptimo
        return 1+Math.max(height(p.getLeft()), height(p.getRight()));
    }
    /*int l= height(p.getLeft());
        int r= height(p.getRight());
        return 1+Math.max(l, r);*/
    
    /**
     * Cuenta las hojas(nodos sin hijos) del árbol
     * @return 
     */
    public int contarHoja(){
        return contarHoja(root);
    }
    
    private int contarHoja(NodeBT<E> h){
        if(h==null) return 0;
        else if(h.getLeft()== null && h.getRight()==null ) return 1;
        return contarHoja(h.getLeft())+contarHoja(h.getRight());
    }
    
    /**
     * Recorre recursivamente los nodos buscando el elemento pasado por párametro.
     * @param data Dato a comparar
     * @return True si el elemento esta en el árbol
     */
    public boolean contains(E data){
        if(data==null || isEmpty()) return false;
        return contains(data, root);         
    }
    
    private boolean contains(E data, NodeBT<E> p){
        if(p==null) return false;
        else if(data.equals( p.getData()))
            return true;
        return contains(data, p.getLeft()) || contains(data,p.getRight());
    }
    
    /**
     * True si el arbol esta Full, es decir, ó no tiene hijos ó los tiene completos.
     * @return True si los nodos no tienen hijos o son duos
     */
    public boolean isFull(){
        if(root!=null && height(root.getLeft())!=height(root.getRight())) return false;
        return isFull(root);
    }
    
    /**
     * Casos
     *     A           A
     * null null    B     C
     *             C D   E F
     * @param p Nodo inicio
     * @return True 
     */
    private boolean isFull(NodeBT<E> p){
        if(p==null) return true;
        else if((p.getLeft()==null && p.getRight()!=null) || (p.getLeft()!=null && p.getRight()==null)) return false;
        return isFull(p.getLeft()) && isFull(p.getRight());
    }
    
    /*
     *      A
     *   B     C
     * D  E      F
     */

    /**
     * Imprime de raiz-izquierda-derecha
     */
    public void preOrden(){
        preOrden(root);
    }
    
    private void preOrden(NodeBT<E> nodo){
        if(nodo!=null){
            System.out.print(nodo.getData());
            preOrden(nodo.getLeft());
            preOrden(nodo.getRight());
        }
        
    }
    
    /**
     * Empieza de izquierda - derecha - raiz
     */
    public void pos_orden(){
        pos_orden(root);
    }
    
    private void pos_orden(NodeBT<E> nodo){
        if(nodo!=null){
            pos_orden(nodo.getLeft());
            pos_orden(nodo.getRight());
            System.out.print(nodo.getData());
        }
    }
    
    /**
     * Empieza por izquierda- raiz - derecha
    */
    public void en_orden(){
        en_orden(root);
    }
    
    private void en_orden(NodeBT<E> nodo){
        if(nodo!=null){
            en_orden(nodo.getLeft());
            System.out.print(nodo.getData());
            en_orden(nodo.getRight());
        }
    }
    
     public NodeBT<E> getRoot() {
        return root;
    }  
    
    
}
