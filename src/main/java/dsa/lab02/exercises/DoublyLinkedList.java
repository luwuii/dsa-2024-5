package dsa.lab02.exercises;

import dsa.lab02.base.LinkedList;
import dsa.lab02.base.LinkedNode;
import dsa.lab02.solutions.SinglyLinkedList;
import dsa.lib.TODO;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A doubly-linked list.
 * <p>
 * Nodes are directly-linked to both their predecessors and successors.
 * Holds references to both the first and last nodes (if non-empty).
 *
 * @param <Item> the item type
 */
public class DoublyLinkedList<Item>
  implements LinkedList<Item>
{
  private int size = 0;
  private Node<Item> first = null;
  private Node<Item> last = null;

  //<editor-fold defaultstate="collapsed" desc="Constructors">

  /**
   * Construct an empty doubly-linked list.
   */
  public DoublyLinkedList()
  {
  }

  /**
   * Construct a doubly-linked list containing the given items.
   *
   * @param items the items
   */
  public DoublyLinkedList(Iterable<Item> items)
  {
    for (Item item : items)
    {
      this.insertLast(item);
    }
  }

  /**
   * Construct a doubly-linked list containing the given items.
   *
   * @param items the items
   */
  @SafeVarargs
  public DoublyLinkedList(Item... items)
  {
    this(Arrays.asList(items));
  }

  //</editor-fold>

  @Override
  public int size()
  {
    return this.size;
  }

  @Override
  //more efficient implementation bc if index in first half of list start search from beginning
  // and if in 2nd half start search from the end
  public Node<Item> node(int index)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= this.size)
    {
      throw new IndexOutOfBoundsException();
    }
    // creates a new node variable
    Node<Item> node;
    //if index is in first half of list
    if (index < this.size / 2){
      //sets new node to first
      node = this.first;
      //set node to the node at the index
      for (int i = 0; i < index; i++)
      {
        node = node.next();
      }
    }
    else{
      // if index is in second half of list
      //set node to last item in list
      node = this.last;
      // get distance from last node to the given index
      int fromIndex = this.size - 1 - index;
      //loop through from last node until the node at the index
      for (int i = 0; i < fromIndex; i++)
      {
        node = node.previous();
      }
    }
    return node;
  }

  @Override
  public void insert(int index, Item item)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index > this.size)
    {
      throw new IndexOutOfBoundsException();
    }
    //checks if list is empty
    if (this.isEmpty())
    {
      //if the list is empty
      //create a new node with given item
      // since only node in list set it to first and last
      this.first = this.last = new Node<>(this, item);
      this.size = 1;
    }
    else if (index == this.size)
    {
      // if index is == to size of list
      // if we are inserting into end of list
      // creates a new node with given item and sets it to last
      this.last =  new Node<>(this, this.last, item);
      // updates the next reference for the old last node to the new last node;
      this.last.previous.next = this.last;
      //increase list size
      this.size++;
    }
    else{
      // if the index not in the 1st or last of list
      // call insert previous method
      this.node(index).insertPrevious(item);
    }
  }

  @Override
  public Item remove(int index)
    throws IndexOutOfBoundsException
  {
    if (index < 0 || index >= this.size)
    {
      throw new IndexOutOfBoundsException();
    }
    return this.node(index).remove();
  }

  /**
   * A node in a doubly-linked list.
   * <p>
   * Holds direct links to both the previous and next nodes.
   *
   * @param <Item> the item type
   */
  public static class Node<Item>
    implements LinkedNode<Item>
  {
    private DoublyLinkedList<Item> list;
    private Node<Item> previous;
    private Item item;
    private Node<Item> next;

    /**
     * Construct a node with the given item.
     *
     * @param list the containing linked list
     * @param item the contained item
     */
    public Node(DoublyLinkedList<Item> list, Item item)
    {
      this(list, null, item, null);
    }

    /**
     * Construct a node with the given predecessor and item.
     *
     * @param list     the containing linked list
     * @param previous the previous node
     * @param item     the contained item
     */
    public Node(DoublyLinkedList<Item> list, Node<Item> previous, Item item)
    {
      this(list, previous, item, null);
    }

    /**
     * Construct a node with the given item and successor.
     *
     * @param list the containing linked list
     * @param item the contained item
     * @param next the next node
     */
    public Node(DoublyLinkedList<Item> list, Item item, Node<Item> next)
    {
      this(list, null, item, next);
    }

    /**
     * Construct a node with the given predecessor, item and successor.
     *
     * @param list     the containing linked list
     * @param previous the previous node
     * @param item     the contained item
     * @param next     the next node
     */
    public Node(
      DoublyLinkedList<Item> list,
      Node<Item> previous,
      Item item,
      Node<Item> next)
    {
      this.list = list;
      this.previous = previous;
      this.item = item;
      this.next = next;
    }

    @Override
    public DoublyLinkedList<Item> list()
    {
      return this.list;
    }

    @Override
    public Item item()
    {
      return this.item;
    }

    @Override
    public void setItem(Item item)
    {
      this.item = item;
    }

    @Override
    public Node<Item> previous()
    {
      return this.previous;
    }

    @Override
    public Node<Item> next()
    {
      return this.next;
    }

    @Override
    public void insertPrevious(Item item)
    {
      //create a new node with given item
      //sets it as previous node of current node
      //passes the list
      //pass current nodes previous node,
      //pass old node
      // pass the current node
      this.previous = new Node<>(this.list, this.previous, item, this);
      //if current node is now first node update first reference to new node
      if (this.isFirst()){
        this.list.first = this.previous;
      }//if current nod is not first node
      else{
        //update next reference of node before new node to point to the new node
        this.previous.previous.next = this.previous;
      }
      //increment list size
      this.list.size++;

    }

    @Override
    public void insertNext(Item item)
    {
      //create new node with given item
      //set it as node after current node
      this.next = new Node<>(this.list, this, item , this.next);
      if (this.isLast()){
        // sets last item in list as new node
        this.list.last = this.next;
      }else{
        //sets the previous reference of one after new node to the new node
        this.next.next.previous = this.next;
      }
      this.list.size++;
    }

    @Override
    public Item remove()
    {
      // if the item to be removed is first in list set the first reference to the next node
      if (this.isFirst())
      {
        this.list.first = this.next;
      }
      else {
        //set the next reference of the previous node to the next reference of the current node
        this.previous.next = this.next;
      }
      // if last item is removed set the last reference to 2nd last node (now last node)
      if (this.isLast()){
        this.list.last = this.previous;
      }
      else {
        // set previous reference of the next node to previous reference of current node
        this.next.previous = this.previous;
      }
      this.list.size--;
      return this.item;
    }

    @Override
    public Item removePrevious()
      throws NoSuchElementException
    {
      if (this.isFirst())
      {
        throw new NoSuchElementException();
      }
      return this.previous.remove();
    }

    @Override
    public Item removeNext()
      throws NoSuchElementException
    {
      if (this.isLast())
      {
        throw new NoSuchElementException();
      }
      return this.next.remove();
    }
  }
}
